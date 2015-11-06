package com.iwan_b.chummersr5.fragments.Skill;

import android.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.util.ArrayList;

public class SkillTableRow {
    // TODO maybe make this into a method that they can call and get...
    private static ArrayList<Skill> skillsAvailable;
    private final View rootView;

    public SkillTableRow(final View rootView, final ArrayList<Skill> skillsAvailable) {
        this.rootView = rootView;
        SkillTableRow.skillsAvailable = skillsAvailable;
    }

    public void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    private void updateSkillCounter(final Integer skillCounter) {
        if (rootView != null) {
            TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
            freeSkillsTxtView.setText(String.valueOf(skillCounter));
        }
    }

    public TableRow createRow(final Skill currentSkill) {
        TableRow newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);

        final TextView titleTxtView = new TextView(rootView.getContext());
        final Button subButton = new Button(rootView.getContext());
        final TextView skillValueTxtView = new TextView(rootView.getContext());
        final Button addButton = new Button(rootView.getContext());
        final LinearLayout extraInfo = new LinearLayout(rootView.getContext());
        final Spinner spinner = new Spinner(rootView.getContext());

        // Title of the attribute
        titleTxtView.setText(currentSkill.getSkillName());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 5, 0);
        titleTxtView.setLayoutParams(lp);

        // Subtract Button
        subButton.setText("-");
        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        subButton.setLayoutParams(lp2);

        // Value of skill
        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(20, 20, 20, 20);
        skillValueTxtView.setLayoutParams(lp3);
        // TODO change the hardcoded values
        skillValueTxtView.setText("0");
        skillValueTxtView.setGravity(1);
        skillValueTxtView.setMinWidth(50);

        // Addition Button
        addButton.setText("+");
        TableRow.LayoutParams lp4 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        addButton.setLayoutParams(lp4);

        // TODO figure out the spec
        if (currentSkill.getSpec() != null) {
            //selected item will look like a spinner set from XML
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_item, currentSkill.getSpec());
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // TODO decide if the empty tag should be here or in the XML
            spinnerArrayAdapter.insert("Specialization", 0);
            // TODO figure out how to add user input as well
            spinnerArrayAdapter.add("Custom Spec");

            spinner.setAdapter(spinnerArrayAdapter);


//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    final int selected = position;
//
//
//                    if (position != 0 && position != parent.getCount() - 1) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
//
//                        builder.setTitle("Buy Specialization");
//
//                        builder.setMessage("Do you want to purchase: " + parent.getItemAtPosition(position).toString());
//
//                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Current amount of karma left
//                                Integer karmaUnused = ShadowrunCharacter.getKarma();
//
//                                // TODO change the hardcoded 7 value for karma expenditure
//                                // 1 at char gen, 7 after
//                                if (karmaUnused >= 1) {
//                                    karmaUnused -= 1;
//
//                                    ShadowrunCharacter.setKarma(karmaUnused);
//                                    updateCounters();
//
//
//                                    TextView temp = new TextView(rootView.getContext());
//                                    Log.i(ChummerConstants.TAG, String.valueOf(extraInfo.getChildCount()));
//                                    if (extraInfo.getChildCount() == 0) {
//                                        temp.setText(currentSkill.getSpec().get(selected));
//                                    } else {
//                                        temp.setText(((TextView) extraInfo.getChildAt(0)).getText() + ", " + currentSkill.getSpec().get(selected));
//                                        extraInfo.removeAllViews();
//                                    }
//
//                                    extraInfo.addView(temp);
//
//                                    spinner.setSelection(0);
//                                } else {
//                                    Toast.makeText(rootView.getContext(), "You don't have enough karma to purchase this spec", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(rootView.getContext(), "Negative button called", Toast.LENGTH_SHORT).show();
//                                spinner.setSelection(0);
//                            }
//                        });
//
//                        builder.show();
//
//                    } else if (position == parent.getCount() - 1) {
//                        // Unique Specialization
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
//
//                        builder.setTitle("Buy Specialization");
//
//                        // Set up the input
//                        final EditText userInput = new EditText(parent.getContext());
//                        userInput.setHint("Enter your specialization");
//                        // Specify the type of input expected
//                        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
//                        builder.setView(userInput);
//
//                        // Set up the buttons
//                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Current amount of karma left
//                                Integer karmaUnused = ShadowrunCharacter.getKarma();
//
//                                // TODO change the hardcoded 7 value for karma expenditure
//                                // 1 at char gen, 7 after
//                                if (karmaUnused >= 1) {
//                                    karmaUnused -= 1;
//
//                                    ShadowrunCharacter.setKarma(karmaUnused);
//                                    updateCounters();
//
//                                    TextView temp = new TextView(rootView.getContext());
//
//                                    // Insert at the second to last. The last should always be adding custom spec
//                                    spinnerArrayAdapter.insert(userInput.getText().toString(), spinnerArrayAdapter.getCount() - 1);
//                                    spinnerArrayAdapter.notifyDataSetChanged();
//
//                                    if (extraInfo.getChildCount() == 0) {
//                                        temp.setText(userInput.getText().toString());
//                                    } else {
//                                        temp.setText(((TextView) extraInfo.getChildAt(0)).getText() + ", " + userInput.getText().toString());
//                                        extraInfo.removeAllViews();
//                                    }
//                                    extraInfo.addView(temp);
//                                }
//                            }
//                        });
//
//                        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
//
//                                {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                }
//
//                        );
//
//                        builder.show();
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

            TableRow.LayoutParams lp5 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            spinner.setLayoutParams(lp5);
        }


        newTableRow.addView(titleTxtView, ChummerConstants.tableLayout.title.ordinal());
        newTableRow.addView(subButton, ChummerConstants.tableLayout.sub.ordinal());
        newTableRow.addView(skillValueTxtView, ChummerConstants.tableLayout.lvl.ordinal());
        newTableRow.addView(addButton, ChummerConstants.tableLayout.add.ordinal());
        newTableRow.addView(extraInfo, ChummerConstants.tableLayout.extra.ordinal());
        newTableRow.addView(spinner, ChummerConstants.tableLayout.spinner.ordinal());

        subButton.setOnClickListener(new SkillOnClickListener(currentSkill, skillValueTxtView, extraInfo, false));
        addButton.setOnClickListener(new SkillOnClickListener(currentSkill, skillValueTxtView, extraInfo, true));

        return newTableRow;
    }

    private class SkillOnClickListener implements View.OnClickListener {
        // Which textfield to modify
        private TextView skillLvlTxtView;
        // Whether to add or subtract
        private boolean isAddition;

        // Limits on the attribute
        private int baseSkill = 0;
        // TODO raise this to 12 after chargen
        private int maxSkill = 6;

        private Skill skillData;

        // Display all the modifiers that affect the attribute
        private LinearLayout extraInfo;

        private TableRow skillGroup;
        private ArrayList<TableRow> skillsTableRowGroup;


        public SkillOnClickListener(final Skill skillData, final TextView skillLvlTxtView, final LinearLayout extraInfo,
                                    final boolean isAddition) {
            this.skillData = skillData;
            this.skillLvlTxtView = skillLvlTxtView;
            this.extraInfo = extraInfo;
            this.isAddition = isAddition;

            findSkillGroup();
        }

        private void findSkillGroup() {
            // Only find it once
            if (skillData.getGroupName() != null && skillGroup == null) {
                // Grab and find all the skillsgroups
                TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillGroupsTableLayout);
                // Loop through all the skills and either disable or enable
                for (int i = 0; i < SkillsTableLayout.getChildCount(); i++) {
                    TableRow temp = (TableRow) SkillsTableLayout.getChildAt(i);

                    TextView name = (TextView) temp.getChildAt(ChummerConstants.tableLayout.title.ordinal());

                    if (skillData.getGroupName().toLowerCase().contains(name.getText().toString().toLowerCase())) {
                        skillGroup = temp;
                        // Break since we found the only skillgroup
                        break;
                    }
                }
            }
        }

        private boolean werePointUsed(TextView txtView) {
            if (txtView != null && txtView.getTag() != null) {
                ArrayList<Integer> pointHistory = (ArrayList<Integer>) txtView.getTag();
                for (int i : pointHistory) {
                    if (i == ChummerConstants.skillGroupPointUsed || i == ChummerConstants.skillPointUsed) {
                        return true;
                    }
                }
            }
            return false;
        }


        private boolean wasKarmaUsed(TextView txtView) {
            if (txtView != null && txtView.getTag() != null) {
                ArrayList<Integer> pointHistory = (ArrayList<Integer>) txtView.getTag();

                for (int i : pointHistory) {
                    if (i > 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void toggleSkillGroup(int currentRating) {
            if (skillsTableRowGroup == null && skillData.getGroupName() != null) {
                findSkillsByGroup(skillData.getGroupName());
            }

            if (skillGroup != null && skillsTableRowGroup != null) {
                // Loop through and find the min value of this group. Then if the skillgroup is less then the min value increase skill group
                int lowestValue = currentRating;

                for (int i = 0; i < skillsTableRowGroup.size(); i++) {
                    TextView temp = (TextView) skillsTableRowGroup.get(i).getChildAt(ChummerConstants.tableLayout.lvl.ordinal());

                    int iSkillValue = Integer.valueOf(temp.getText().toString());

                    if (iSkillValue < lowestValue) {
                        lowestValue = iSkillValue;
                    }
                }
                TextView groupLevelTxtView = (TextView) skillGroup.getChildAt(ChummerConstants.tableLayout.lvl.ordinal());
                int groupLevel = Integer.valueOf(groupLevelTxtView.getText().toString());

                ArrayList<Integer> pointHistory = new ArrayList<>();
                // Get the history of the skills
                if (groupLevelTxtView.getTag() != null) {
                    pointHistory = (ArrayList<Integer>) groupLevelTxtView.getTag();
                }

                if (isAddition) {
                    while (groupLevel < lowestValue) {
                        pointHistory.add(ChummerConstants.freeSkillGroupLevel);
                        groupLevelTxtView.setText(String.valueOf(groupLevel + 1));
                        groupLevel++;
                    }
                } else {
                    // Subtraction
                    while (groupLevel > lowestValue && !pointHistory.isEmpty() && pointHistory.get(pointHistory.size() - 1) == ChummerConstants.freeSkillGroupLevel) {
                        groupLevelTxtView.setText(String.valueOf(groupLevel - 1));
                        pointHistory.remove(pointHistory.size() - 1);
                        groupLevel--;
                    }
                }

                groupLevelTxtView.setTag(pointHistory);
            }
        }

        private void findSkillsByGroup(final String skillGroupName) {
            ArrayList<String> skillNameArray = new ArrayList<>();
            // Grab the name of the skill that has the same groupname
            for (final Skill s : skillsAvailable) {
                if (s.getGroupName() != null && s.getGroupName().compareToIgnoreCase(skillGroupName) == 0 && s.getSkillName() != skillData.getSkillName()) {
                    skillNameArray.add(s.getSkillName().toLowerCase());
                }
            }

            // Find the skills highlighted earlier
            TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillsTableLayout);
            for (int i = 0; i < SkillsTableLayout.getChildCount(); i++) {
                TableRow temp = (TableRow) SkillsTableLayout.getChildAt(i);

                TextView skillName = (TextView) temp.getChildAt(ChummerConstants.tableLayout.title.ordinal());

                if (skillNameArray.contains(skillName.getText().toString().toLowerCase())) {
                    if (skillsTableRowGroup == null) {
                        skillsTableRowGroup = new ArrayList<>();
                    }
                    skillsTableRowGroup.add(temp);
                }
            }
        }

        @Override
        public void onClick(View v) {
            findSkillGroup();

            // Current rating of the skill
            int currentRating = Integer.valueOf(skillLvlTxtView.getText().toString());

            // Current value left for attributes
            TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
            Integer skillCounter = Integer.valueOf(freeSkillsTxtView.getText().toString());

            // Current amount of karma left
            Integer karmaUnused = ShadowrunCharacter.getKarma();
            ArrayList<Integer> pointHistory = new ArrayList<>();

            // Get the history of the skills
            if (skillLvlTxtView.getTag() != null) {
                pointHistory = (ArrayList<Integer>) skillLvlTxtView.getTag();
            }

            int max_skill_mod = 0;

            if (isAddition) {
                if (currentRating < maxSkill + max_skill_mod) {
                    // Use the free skills first, then karma

                    TextView skillGroupTxtView = null;
                    if (skillGroup != null) {
                        skillGroupTxtView = (TextView) skillGroup.getChildAt(ChummerConstants.tableLayout.lvl.ordinal());
                    }

                    if (skillCounter > 0 && !wasKarmaUsed(skillGroupTxtView) && !werePointUsed(skillGroupTxtView)) {
                        // Test if they used karma on this specific skill before
                        if (wasKarmaUsed(skillLvlTxtView)) {
                            Toast toast = Toast.makeText(rootView.getContext(),
                                    "You already used karma on this. Can't use points afterwards.",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            currentRating++;
                            skillCounter--;
                            pointHistory.add(ChummerConstants.skillPointUsed);
                            toggleSkillGroup(currentRating);
                        }
                    } else {
                        // Forumula for karma needed to advance to next level is: (New Rating x 2)
                        if ((currentRating + 1) * 2 <= karmaUnused) {
                            pointHistory.add((currentRating + 1) * 2);
                            karmaUnused -= (currentRating + 1) * 2;
                            currentRating++;
                            toggleSkillGroup(currentRating);
                        }
                    }
                }
                // Subtraction
            } else {
                if (currentRating > baseSkill) {
                    if (!pointHistory.isEmpty() && pointHistory.get(pointHistory.size() - 1) != ChummerConstants.freeSkillLevel) {
                        if (wasKarmaUsed(skillLvlTxtView)) {
                            // Forumula for karma needed to advance to next level is: (New Rating x 2)
                            karmaUnused += (currentRating) * 2;
                        } else {
                            // No karma was used
                            skillCounter++;
                        }
                        currentRating--;
                        toggleSkillGroup(currentRating);
                        pointHistory.remove(pointHistory.size() - 1);
                    }
                }
            }

//            if (pointHistory != null) {
//                Log.i(ChummerConstants.TAG, pointHistory.toString());
//            }

            // Set the karma count for the subtraction button to know
            skillLvlTxtView.setTag(pointHistory);

            // Update the displays with all the new stuff.
            skillLvlTxtView.setText(String.valueOf(currentRating));

            ShadowrunCharacter.setKarma(karmaUnused);
            updateSkillCounter(skillCounter);
            updateKarma();
        }
    }

}