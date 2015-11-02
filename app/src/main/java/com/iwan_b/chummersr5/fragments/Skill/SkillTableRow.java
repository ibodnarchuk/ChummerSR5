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

public class SkillTableRow {
    private final View rootView;

    public SkillTableRow(final View rootView) {
        this.rootView = rootView;
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

        newTableRow.addView(titleTxtView);
        newTableRow.addView(subButton);
        newTableRow.addView(skillValueTxtView);
        newTableRow.addView(addButton);
        newTableRow.addView(extraInfo);
        newTableRow.addView(spinner);

        subButton.setOnClickListener(new SkillOnClickListener(currentSkill, skillValueTxtView, extraInfo, false));
        addButton.setOnClickListener(new SkillOnClickListener(currentSkill, skillValueTxtView, extraInfo, true));

        return newTableRow;
    }

    private class SkillOnClickListener implements View.OnClickListener {
        // Which textfield to modify
        private TextView skillValueTxtView;
        // Whether to add or subtract
        private boolean isAddition;

        // Limits on the attribute
        private int baseSkill = 0;
        // TODO raise this to 12 after chargen
        private int maxSkill = 6;

        private Skill skill;

        // Display all the modifiers that affect the attribute
        private LinearLayout extraInfo;

        private TableRow skillGroup;

        public SkillOnClickListener(final Skill skill, final TextView skillValueTxtView, final LinearLayout extraInfo,
                                    final boolean isAddition) {
            this.skill = skill;
            this.skillValueTxtView = skillValueTxtView;
            this.extraInfo = extraInfo;
            this.isAddition = isAddition;

            findSkillGroup();
        }

        private void findSkillGroup() {
            // Only find it once
            if (skill.getGroupName() != null && skillGroup == null) {
                // Grab and find all the skillsgroups
                TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillGroupsTableLayout);
                // Loop through all the skills and either disable or enable
                for (int i = 0; i < SkillsTableLayout.getChildCount(); i++) {
                    TableRow temp = (TableRow) SkillsTableLayout.getChildAt(i);

                    TextView name = (TextView) temp.getChildAt(0);

                    if (skill.getGroupName().toLowerCase().contains(name.getText().toString().toLowerCase())) {
                        skillGroup = temp;
                        // Break since we found the only skillgroup
                        break;
                    }
                }
            }
        }

        private int getGroupRating() {
            if (skillGroup != null) {
                TextView valueTxtView = (TextView) skillGroup.getChildAt(2);
                return Integer.valueOf(valueTxtView.getText().toString());
            } else {
                return 0;
            }
        }

        private void toggleSkillGroup(final int currentRating) {
            if (skillGroup != null) {
                // TODO find out how to disable only if the other skills are not at the same level...
                Button add = (Button) skillGroup.getChildAt(3);
                TextView valueTxtView = (TextView) skillGroup.getChildAt(2);
                Button sub = (Button) skillGroup.getChildAt(1);

                int iValue =  Integer.valueOf(valueTxtView.getText().toString());

//                valueTxtView.setText(String.valueOf(iValue+1));

                // TODO change this because this is only used for chargen
//                if (isAddition) {
//                    add.setEnabled(false);
//                    sub.setEnabled(false);
//                } else {
//                    if (currentRating == 0) {
//                        add.setEnabled(true);
//                        sub.setEnabled(true);
//                    }
//                }
            }
        }

        @Override
        public void onClick(View v) {
            findSkillGroup();
            int groupRating = getGroupRating();

            // Current rating of the skill
            int currentRating = Integer.valueOf(skillValueTxtView.getText().toString());

            // Current value left for attributes
            Integer skillCounter, groupSkillCounter;
            TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
            TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroups);
            skillCounter = Integer.valueOf(freeSkillsTxtView.getText().toString());
            groupSkillCounter = Integer.valueOf(freeSkillGroupTxt.getText().toString());

            // Current amount of karma left
            Integer karmaUnused = ShadowrunCharacter.getKarma();
            Integer karmaUsed = 0;

            // Get the karma count for this attribute
            if (skillValueTxtView.getTag() != null) {
                karmaUsed = (Integer) skillValueTxtView.getTag();
            }

            int max_skill_mod = 0;

            if (isAddition) {
                if (currentRating < maxSkill + max_skill_mod) {
                    // If the group rating is not 0 then they can only increase the skill with karma.
                    if(groupRating == 0) {
                        // Use the free skills first, then karma
                        if (skillCounter > 0) {
                            // Test if they used karma on this specific skill before
                            if (karmaUsed > 0) {
                                Toast toast = Toast.makeText(rootView.getContext(),
                                        "You already used karma on this. Can't use points afterwards.",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                currentRating++;
                                skillCounter--;
                            }
                        } else {
                            // Forumula for karma needed to advance to next level is: (New Rating x 2)
                            if ((currentRating + 1) * 2 <= karmaUnused) {
                                karmaUsed += (currentRating + 1) * 2;
                                karmaUnused -= (currentRating + 1) * 2;

                                // Set the karma count for the subtraction button to know
                                skillValueTxtView.setTag(karmaUsed);

                                currentRating++;
                            }
                        }
                    } else {
                        // Forumula for karma needed to advance to next level is: (New Rating x 2)
                        if ((currentRating + 1) * 2 <= karmaUnused) {
                            karmaUsed += (currentRating + 1) * 2;
                            karmaUnused -= (currentRating + 1) * 2;

                            // Set the karma count for the subtraction button to know
                            skillValueTxtView.setTag(karmaUsed);

                            currentRating++;
                            // Fine to toggle group

                            toggleSkillGroup(currentRating);
                        }
                    }
                }
                // Subtraction
            } else {
                // TODO maybe remove baseSkill. When will this var actually matter?
                if (currentRating > baseSkill + groupRating) {
                    // Forumula for karma needed to advance to next level is: (New Rating x 2)
                    if (karmaUsed > 0) {
                        karmaUsed -= (currentRating) * 2;
                        karmaUnused += (currentRating) * 2;
                        currentRating--;
                    } else {
                        // No karma was used
                        currentRating--;
                        skillCounter++;
                    }

                    skillValueTxtView.setTag(karmaUsed);
                }
            }

            // Update the displays with all the new stuff.
            skillValueTxtView.setText(String.valueOf(currentRating));

            ShadowrunCharacter.setKarma(karmaUnused);
            updateCounters(skillCounter, groupSkillCounter);
        }
    }

}