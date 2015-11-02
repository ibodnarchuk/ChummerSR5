package com.iwan_b.chummersr5.fragments.Skill;

import android.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.util.ArrayList;

public class SkillGroupTableRow {
    private final View rootView;
    private final ArrayList<Skill> skillsAvailable;

    public SkillGroupTableRow(final View rootView, ArrayList<Skill> skillsAvailable) {
        this.rootView = rootView;
        this.skillsAvailable = skillsAvailable;
    }

    public void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
            karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
        }
    }

    private void updateCounters(final Integer skillCounter, final Integer groupSkillCounter) {
        TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
        freeSkillsTxtView.setText(String.valueOf(skillCounter));

        TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroups);
        freeSkillGroupTxt.setText(String.valueOf(groupSkillCounter));

        updateKarma();
    }

    public TableRow createRow(final String sGroup) {
        TableRow newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);

        final TextView titleTxtView = new TextView(rootView.getContext());
        final Button subButton = new Button(rootView.getContext());
        final TextView skillGroupDisplayTxtView = new TextView(rootView.getContext());
        final Button addButton = new Button(rootView.getContext());
        final LinearLayout extraInfo = new LinearLayout(rootView.getContext());

        // Title of the attribute
        titleTxtView.setText(sGroup);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 5, 0);
        titleTxtView.setLayoutParams(lp);
        newTableRow.addView(titleTxtView);

        // Subtract Button
        subButton.setText("-");
        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        subButton.setLayoutParams(lp2);
        newTableRow.addView(subButton);

        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(20, 20, 20, 20);

        skillGroupDisplayTxtView.setLayoutParams(lp3);
        // TODO change the hardcoded values
        skillGroupDisplayTxtView.setText("0");
        skillGroupDisplayTxtView.setGravity(1);
        skillGroupDisplayTxtView.setMinWidth(50);

        newTableRow.addView(skillGroupDisplayTxtView);

        // Addition Button
        addButton.setText("+");
        TableRow.LayoutParams lp4 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        addButton.setLayoutParams(lp4);
        newTableRow.addView(addButton);

        // Extra Info
        newTableRow.addView(extraInfo);

        // TODO Make this into a scrollview.
        subButton.setOnClickListener(new SkillGroupOnClickListener(sGroup, skillGroupDisplayTxtView, extraInfo, false));
        addButton.setOnClickListener(new SkillGroupOnClickListener(sGroup, skillGroupDisplayTxtView, extraInfo, true));

        return newTableRow;
    }


    private class SkillGroupOnClickListener implements View.OnClickListener {
        // Limits on the attribute
        private int baseSkillGroupRating = 0;
        private int baseSkillRating = 0;
        // TODO change this after char gen its 12
        private int maxSkillRating = 6;

        // Which textfield to modify
        private TextView skillGroupDisplayTxtView;
        // Whether to add or subtract
        private boolean isAddition;

        // Name of the attribute being altered
        private String skillGroupName;

        // Display all the modifiers that affect the attribute
        private LinearLayout extraInfo;

        private ArrayList<TableRow> skillsTableRow;

        private int offset = 0;

        public SkillGroupOnClickListener(final String skillGroupName, final TextView skillGroupDisplayTxtView, final LinearLayout extraInfo,
                                         final boolean isAddition) {
            this.skillGroupName = skillGroupName;
            this.skillGroupDisplayTxtView = skillGroupDisplayTxtView;
            this.extraInfo = extraInfo;
            this.isAddition = isAddition;

            // Find all the skills affected by this group. Only needed to do once
            findSkillsByGroup(skillGroupName);
        }

        private void findSkillsByGroup(final String skillGroupName) {
            ArrayList<String> skillNameArray = new ArrayList<>();
            // Grab the name of the skill that has the same groupname
            for (final Skill s : skillsAvailable) {
                if (s.getGroupName() != null && s.getGroupName().compareToIgnoreCase(skillGroupName) == 0) {
                    skillNameArray.add(s.getSkillName().toLowerCase());
                }
            }

            // Find the skills highlighted earlier
            TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillsTableLayout);
            for (int i = 0; i < SkillsTableLayout.getChildCount(); i++) {
                TableRow temp = (TableRow) SkillsTableLayout.getChildAt(i);

                TextView skillName = (TextView) temp.getChildAt(0);

                if (skillNameArray.contains(skillName.getText().toString().toLowerCase())) {
                    if (skillsTableRow == null) {
                        skillsTableRow = new ArrayList<>();
                    }
                    skillsTableRow.add(temp);
                }
            }
        }

        private void getBaseRating() {
            if (skillsTableRow != null) {
                ArrayList<Integer> values = new ArrayList<>();
                for (TableRow temp : skillsTableRow) {
                    TextView skillValue = (TextView) temp.getChildAt(2);

                    int iSkillValue = Integer.valueOf(skillValue.getText().toString());

                    values.add(iSkillValue);
                }

                boolean isSame = true;
                for (int i = 0; (i < values.size()) && isSame; i++) {
                    if (values.get(0) != values.get(i)) {
                        isSame = false;
                    }
                }

                // If they are all the same then we can assume the base rating of the group is x
                if (isSame) {
                    baseSkillRating = values.get(0);
                }
            }
        }

        private int getBaseMinRating() {
            if (skillsTableRow != null) {
                TextView skillValue = (TextView) skillsTableRow.get(0).getChildAt(2);

                int cValue = Integer.valueOf(skillValue.getText().toString());

                int minIndex = 0;

                for (int i = 1; i < skillsTableRow.size(); i++) {
                    TextView temp = (TextView) skillsTableRow.get(i).getChildAt(2);

                    int iSkillValue = Integer.valueOf(temp.getText().toString());

                    if (iSkillValue < cValue) {
                        minIndex = i;
                    }
                }

                TextView minValue = (TextView) skillsTableRow.get(minIndex).getChildAt(2);
                return Integer.valueOf(minValue.getText().toString());
            }

            return 0;
        }

        private boolean areSkillRowsEqual() {
            if (skillsTableRow != null) {
                ArrayList<Integer> values = new ArrayList<>();
                for (TableRow temp : skillsTableRow) {
                    TextView skillValue = (TextView) temp.getChildAt(2);

                    int iSkillValue = Integer.valueOf(skillValue.getText().toString());

                    values.add(iSkillValue);
                }

                for (int i = 0; (i < values.size()); i++) {
                    if (values.get(0) != values.get(i)) {
                        return false;
                    }
                }

            }
            // TODO need to also test wether spec was used

            return true;
        }

        /**
         * Tests whether any of the skills used karma to increase the stats or not.
         *
         * @return True if any of the skills used karma
         */
        private boolean skillRowsUsedKarma() {
            if (skillsTableRow != null) {
                for (TableRow temp : skillsTableRow) {
                    TextView skillValue = (TextView) temp.getChildAt(2);
                    int karmaUsed = 0;
                    // Get the karma count for this attribute
                    if (skillValue.getTag() != null) {
                        karmaUsed = (Integer) skillValue.getTag();
                    }

                    if (karmaUsed > 0) {
                        return true;
                    }
                }

            }
            return false;
        }

        private void toggleSkillsByGroup(final int currentRating) {
            if (skillsTableRow != null) {
                for (TableRow temp : skillsTableRow) {
                    TextView skillName = (TextView) temp.getChildAt(0);
                    Button sub = (Button) temp.getChildAt(1);
                    TextView skillValue = (TextView) temp.getChildAt(2);
                    Button add = (Button) temp.getChildAt(3);

                    int iSkillValue = Integer.valueOf(skillValue.getText().toString());

                    // TODO change this because this is only used for chargen
                    if (isAddition) {
                        skillValue.setText(String.valueOf(iSkillValue + 1));
//						add.setEnabled(false);
//                      sub.setEnabled(false);
                    } else {
                        skillValue.setText(String.valueOf(iSkillValue - 1));
                        // When the skill group is 0 we enable all the regular buttons
//                        if (currentRating == 0) {
//                            add.setEnabled(true);
//                            sub.setEnabled(true);
//                        }
                    }

                }
            }
        }

        private void getOffset() {
            // Current rating of the skill
            int currentRating = Integer.valueOf(skillGroupDisplayTxtView.getText().toString());
            offset = currentRating - baseSkillRating;

            Log.i(ChummerConstants.TAG, "----------------------------------");
            Log.i(ChummerConstants.TAG, "currentRating: " + currentRating);
            Log.i(ChummerConstants.TAG, "baseSkillGroupRating: " + baseSkillGroupRating);
            Log.i(ChummerConstants.TAG, "baseSkillRating: " + baseSkillRating);
            Log.i(ChummerConstants.TAG, "offset: " + offset);
            Log.i(ChummerConstants.TAG, "----------------------------------");

        }

        @Override
        public void onClick(View v) {
            getBaseRating();
            getOffset();

            if (areSkillRowsEqual()) {
                // Current rating of the skill
                int currentRating = Integer.valueOf(skillGroupDisplayTxtView.getText().toString());

                // Current value left for attributes
                Integer skillCounter, groupSkillCounter;
                TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
                TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroups);
                skillCounter = Integer.valueOf(freeSkillsTxtView.getText().toString());
                groupSkillCounter = Integer.valueOf(freeSkillGroupTxt.getText().toString());

                // Current amount of karma left
                Integer karmaUnused = ShadowrunCharacter.getKarma();

                int karmaUsed = 0;
                // Get the karma count for this attribute
                if (skillGroupDisplayTxtView.getTag() != null) {
                    karmaUsed = (Integer) skillGroupDisplayTxtView.getTag();
                }

                // TODO look up if anything increases skill group limit
                int max_skill_mod = 0;

                if (isAddition) {
                    if (currentRating < maxSkillRating + max_skill_mod) {
                        // If the skills used karma then we can only increase by karma.
                        if (!skillRowsUsedKarma()) {
                            // Use the free skills first, then karma
                            if (groupSkillCounter > 0) {
                                // Test if they used karma on this specific item before or not
                                if (karmaUsed > 0) {
                                    Toast toast = Toast.makeText(rootView.getContext(),
                                            "You already used karma on this. Can't use points afterwards.",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                } else {
                                    currentRating++;
                                    groupSkillCounter--;
                                    toggleSkillsByGroup(currentRating);
                                }
                            } else {
                                // Forumula for karma needed to advance to next level is: (New Rating x 5)
                                if ((currentRating + 1) * 5 <= karmaUnused) {
                                    karmaUsed += (currentRating + 1) * 5;
                                    karmaUnused -= (currentRating + 1) * 5;

                                    // Set the karma count for the subtraction button to know
                                    skillGroupDisplayTxtView.setTag(karmaUsed);

                                    currentRating++;
                                    toggleSkillsByGroup(currentRating);
                                }
                            }
                        } else {
                            // Forumula for karma needed to advance to next level is: (New Rating x 5)
                            if ((currentRating + 1) * 5 <= karmaUnused) {
                                karmaUsed += (currentRating + 1) * 5;
                                karmaUnused -= (currentRating + 1) * 5;

                                // Set the karma count for the subtraction button to know
                                skillGroupDisplayTxtView.setTag(karmaUsed);

                                currentRating++;
                                toggleSkillsByGroup(currentRating);
                            }
                        }
                    }
                    // Subtraction
                } else {
                    if (currentRating > baseSkillGroupRating + baseSkillRating) {
                        // If the skills used Karma then the Group must have used karma
                        if (!skillRowsUsedKarma()) {
                            // Forumula for karma needed to advance to next level is: (New Rating x 5)
                            if (karmaUsed > 0) {
                                karmaUsed -= (currentRating) * 5;
                                karmaUnused += (currentRating) * 5;
                                currentRating--;
                            } else {
                                // No karma was used
                                currentRating--;
                                groupSkillCounter++;
                            }

                            toggleSkillsByGroup(currentRating);
                            skillGroupDisplayTxtView.setTag(karmaUsed);
                        } else {
                            // Forumula for karma needed to advance to next level is: (New Rating x 5)
                            if (karmaUsed > 0) {
                                karmaUsed -= (currentRating) * 5;
                                karmaUnused += (currentRating) * 5;
                                currentRating--;
                                toggleSkillsByGroup(currentRating);
                                skillGroupDisplayTxtView.setTag(karmaUsed);
                            }
                        }


                    }
                }

                // Update the displays with all the new stuff.
                skillGroupDisplayTxtView.setText(String.valueOf(currentRating));

                ShadowrunCharacter.setKarma(karmaUnused);
                updateCounters(skillCounter, groupSkillCounter);
            } else {
                Toast.makeText(rootView.getContext(), "Make the individual skills the same level.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}