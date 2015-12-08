package com.iwan_b.chummersr5.fragments.ActiveSkill;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.Quality;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerMethods;

import java.util.ArrayList;

public class SkillTableRow implements UpdateInterface{
    // TODO maybe make this into a method that they can call and get...
    private static ArrayList<Skill> skillsAvailable;
    private final View rootView;
    private final ChummerConstants.counters counterID;
    private final boolean isActiveSkill;
    private ArrayList<SkillOnClickListener> childrenToUpdate = new ArrayList<>();

    public SkillTableRow(final View rootView, final ArrayList<Skill> skillsAvailable, ChummerConstants.counters counterID, boolean isActiveSkill) {
        this.rootView = rootView;
        SkillTableRow.skillsAvailable = skillsAvailable;
        this.counterID = counterID;
        this.isActiveSkill = isActiveSkill;
    }

    public void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    private void updateSkillCounter() {
        if (rootView != null) {
            TextView freeSkillsTxtView = null;
            if (counterID == ChummerConstants.counters.activeSkills) {
                freeSkillsTxtView = (TextView) rootView.findViewById(R.id.fragment_activeskill_freeSkills_Counter);
            } else if (counterID == ChummerConstants.counters.knowledgeSkills) {
                freeSkillsTxtView = (TextView) rootView.findViewById(R.id.fragment_knowledgeskill_freeKnowledge_Counter);
            } else if (counterID == ChummerConstants.counters.languageSkills) {
                freeSkillsTxtView = (TextView) rootView.findViewById(R.id.fragment_knowledgeskill_freeLanguage_Counter);
            }

            freeSkillsTxtView.setText(String.valueOf(getSkillCounter()));
        }
    }

    public int getSkillCounter() {
        if (counterID == ChummerConstants.counters.activeSkills) {
            return FreeCounters.getCounters().getFreeActiveSkills();
        } else if (counterID == ChummerConstants.counters.knowledgeSkills) {
            return FreeCounters.getCounters().getFreeKnowledgeSkills();
        } else if (counterID == ChummerConstants.counters.languageSkills) {
            return FreeCounters.getCounters().getFreeLanguageSkills();
        }
        return 0;
    }

    public void setSkillCounter(Integer skillCounter) {
        if (counterID == ChummerConstants.counters.activeSkills) {
            FreeCounters.getCounters().setFreeActiveSkills(skillCounter);
        } else if (counterID == ChummerConstants.counters.knowledgeSkills) {
            FreeCounters.getCounters().setFreeKnowledgeSkills(skillCounter);
        } else if (counterID == ChummerConstants.counters.languageSkills) {
            FreeCounters.getCounters().setFreeLanguageSkills(skillCounter);
        }
    }

    public TableRow createRow(final Skill skill) {
        TableRow newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);

        // TODO maybe change this to a layout xml file
        final TextView titleTxtView = new TextView(rootView.getContext());
        final TextView attrTxtView = new TextView(rootView.getContext());
        final Button subButton = new Button(rootView.getContext());
        final TextView skillValueTxtView = new TextView(rootView.getContext());
        final Button addButton = new Button(rootView.getContext());
        final LinearLayout extraInfo = new LinearLayout(rootView.getContext());
        final Spinner spinner = new Spinner(rootView.getContext());

        // Title of the attribute
        titleTxtView.setText(skill.getSkillName());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 5, 0);
        titleTxtView.setLayoutParams(lp);

        // Title of the attribute
        attrTxtView.setText(skill.getAttrName());
//        attrTxtView.setLayoutParams(lp);

        // Subtract Button
        subButton.setText("-");
        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        subButton.setLayoutParams(lp2);

        // Value of skill
        TableRow.LayoutParams lp3 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(20, 20, 20, 20);
        skillValueTxtView.setLayoutParams(lp3);
        // TODO change the hardcoded values
        skillValueTxtView.setText(String.valueOf(skill.getRating()));
        skillValueTxtView.setGravity(1);
        skillValueTxtView.setMinWidth(50);

        // Addition Button
        addButton.setText("+");
        TableRow.LayoutParams lp4 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        addButton.setLayoutParams(lp4);


        //selected item will look like a spinner set from XML
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_item);
        if (skill.getSpec() != null) {
            spinnerArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_item, skill.getSpec());
        }

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // TODO change this hardcode
        spinnerArrayAdapter.insert("Specialization", 0);
        // TODO change this hardcode
        // TODO figure out how to add user input as well
        spinnerArrayAdapter.add("Custom Spec");

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new SpecOnClickListener(extraInfo));

        TableRow.LayoutParams lp5 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        spinner.setLayoutParams(lp5);

        newTableRow.addView(titleTxtView, ChummerConstants.tableLayout.title.ordinal());
        newTableRow.addView(attrTxtView, ChummerConstants.tableLayout.attr.ordinal());
        newTableRow.addView(subButton, ChummerConstants.tableLayout.sub.ordinal());
        newTableRow.addView(skillValueTxtView, ChummerConstants.tableLayout.lvl.ordinal());
        newTableRow.addView(addButton, ChummerConstants.tableLayout.add.ordinal());
        newTableRow.addView(spinner, ChummerConstants.tableLayout.spinner.ordinal());
        newTableRow.addView(extraInfo, ChummerConstants.tableLayout.extra.ordinal());

        SkillOnClickListener sub = new SkillOnClickListener(skill, skillValueTxtView, extraInfo, false);
        SkillOnClickListener add = new SkillOnClickListener(skill, skillValueTxtView, extraInfo, true);


        childrenToUpdate.add(sub);
        childrenToUpdate.add(add);

        subButton.setOnClickListener(sub);
        addButton.setOnClickListener(add);

        return newTableRow;
    }

    @Override
    public void update() {
        for(SkillOnClickListener temp : childrenToUpdate){
            temp.update();
        }
    }

    @Override
    public void updateParent() {}

    private class SpecOnClickListener implements AdapterView.OnItemSelectedListener {
        private LinearLayout extraInfo;
        private AlertDialog dialog;

        public SpecOnClickListener(LinearLayout extraInfo) {
            this.extraInfo = extraInfo;
        }

        private void buildRemoveButton(final AlertDialog.Builder builder, final AdapterView<ArrayAdapter<String>> parent, final int position) {
            final String customStringOutput = parent.getItemAtPosition(position).toString();

            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Current amount of karma left
                    Integer karmaUnused = ShadowrunCharacter.getKarma();

                    Integer skillCounter = getSkillCounter();

                    TextView specTxtView = (TextView) extraInfo.getChildAt(0);

                    TextView skillMod = (TextView) extraInfo.getChildAt(1);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 0, 0, 0);
                    skillMod.setLayoutParams(lp);

                    String allStrings = specTxtView.getText().toString();

                    ArrayList<Integer> pointHistory = new ArrayList<>();
                    // Get the history of the skills
                    if (specTxtView.getTag() != null) {
                        pointHistory = (ArrayList<Integer>) specTxtView.getTag();
                    }

                    int i = 0;
                    for (final String eachSpec : allStrings.split(System.getProperty("line.separator"))) {
                        if (eachSpec.equalsIgnoreCase(customStringOutput)) {
                            int cost = pointHistory.get(i);
                            if (cost == ChummerConstants.freeSkillLevel) {
                                skillCounter++;
                            } else {
                                karmaUnused += cost;
                            }
                            pointHistory.remove(i);
                            break;
                        }
                        i++;
                    }

                    // Remove the string. It can either have a line separator + string, or just the string by itself
                    allStrings = allStrings.replace(customStringOutput + System.getProperty("line.separator"), "").replace(customStringOutput, "");
                    allStrings = allStrings.trim();

                    specTxtView.setTag(pointHistory);
                    extraInfo.removeAllViews();
                    specTxtView.setText(allStrings);
                    extraInfo.addView(specTxtView);
                    extraInfo.addView(skillMod);

                    if (customStringOutput.contains("Custom:")) {
                        // Add the custom string to the list of possible outputs
                        final ArrayAdapter<String> spinnerArrayAdapter = parent.getAdapter();
                        spinnerArrayAdapter.remove(customStringOutput);
                        parent.setAdapter(spinnerArrayAdapter);
                    }

                    ShadowrunCharacter.setKarma(karmaUnused);
                    updateKarma();
                    setSkillCounter(skillCounter);
                    updateSkillCounter();
                    parent.setSelection(0);
                }
            });
        }

        public void buildAddButton(final AlertDialog.Builder builder, final AdapterView<ArrayAdapter<String>> parent, final int position, final EditText userInput) {
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String customStringOutput;
                    if (userInput != null) {
                        customStringOutput = "Custom: " + userInput.getText().toString();
                    } else {
                        customStringOutput = parent.getItemAtPosition(position).toString();
                    }

                    // Current amount of karma left
                    Integer karmaUnused = ShadowrunCharacter.getKarma();

                    Integer skillCounter = getSkillCounter();

                    TextView specTxtView = new TextView(rootView.getContext());
                    TextView skillMod = new TextView(rootView.getContext());

                    if (extraInfo.getChildCount() != 0) {
                        specTxtView = (TextView) extraInfo.getChildAt(0);
                        if(extraInfo.getChildCount() == 2){
                            skillMod = (TextView) extraInfo.getChildAt(1);
                        }
                    }

                    TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 0, 0, 0);
                    skillMod.setLayoutParams(lp);

                    TextView newOutput = new TextView(rootView.getContext());

                    ArrayList<Integer> pointHistory = new ArrayList<>();
                    // Get the history of the skills
                    if (specTxtView.getTag() != null) {
                        pointHistory = (ArrayList<Integer>) specTxtView.getTag();
                    }

                    if (!specTxtView.getText().toString().contains(customStringOutput)) {
                        // Use the free skills first, then karma
                        if (skillCounter > 0 && !wasKarmaUsed(specTxtView) && !werePointUsed(specTxtView)) {
                            skillCounter--;
                            pointHistory.add(ChummerConstants.freeSkillLevel);

                            if (extraInfo.getChildCount() == 0) {
                                newOutput.setText(customStringOutput);
                            } else {
                                newOutput.setText(specTxtView.getText().toString() + System.getProperty("line.separator") + customStringOutput);
                            }

                            if (userInput != null) {
                                // Add the custom string to the list of possible outputs
                                final ArrayAdapter<String> spinnerArrayAdapter = parent.getAdapter();
                                spinnerArrayAdapter.insert(customStringOutput, spinnerArrayAdapter.getCount() - 1);
                                parent.setAdapter(spinnerArrayAdapter);
                            }

                            extraInfo.removeAllViews();
                            extraInfo.addView(newOutput);
                            extraInfo.addView(skillMod);
                        } else {
                            if (karmaUnused >= 7) {
                                karmaUnused -= 7;
                                pointHistory.add(7);
                                if (extraInfo.getChildCount() == 0) {
                                    newOutput.setText(customStringOutput);
                                } else {
                                    newOutput.setText(specTxtView.getText().toString() + System.getProperty("line.separator") + customStringOutput);
                                }

                                if (userInput != null) {
                                    // Add the custom string to the list of possible outputs
                                    final ArrayAdapter<String> spinnerArrayAdapter = parent.getAdapter();
                                    spinnerArrayAdapter.insert(customStringOutput, spinnerArrayAdapter.getCount() - 1);
                                    parent.setAdapter(spinnerArrayAdapter);
                                }

                            } else {
                                Toast.makeText(rootView.getContext(), "You don't have enough karma to purchase this spec", Toast.LENGTH_SHORT).show();
                            }
                        }

                        newOutput.setTag(pointHistory);
                        extraInfo.removeAllViews();
                        extraInfo.addView(newOutput);
                        extraInfo.addView(skillMod);
                        ShadowrunCharacter.setKarma(karmaUnused);
                        updateKarma();
                        setSkillCounter(skillCounter);
                        updateSkillCounter();
                    } else {
                        Toast.makeText(rootView.getContext(), "You already bought this", Toast.LENGTH_SHORT).show();
                    }
                    parent.setSelection(0);
                }
            });
        }

        private boolean werePointUsed(TextView txtView) {
            if (txtView != null && txtView.getTag() != null) {
                ArrayList<Integer> pointHistory = (ArrayList<Integer>) txtView.getTag();
                for (int i : pointHistory) {
                    if (i == ChummerConstants.skillPointUsed) {
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

        @Override
        public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
            // Skip the first one, and the last one.
            if (position != 0 && position != parent.getCount() - 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());

                final String customStringOutput = parent.getItemAtPosition(position).toString();

                TextView temp2 = null;
                if (extraInfo.getChildCount() != 0) {
                    temp2 = (TextView) extraInfo.getChildAt(0);
                }

                if (temp2 != null && temp2.getText().toString().contains(customStringOutput)) {
                    // Remove/Delete
                    builder.setTitle("Remove Specialization");
                    builder.setMessage("Do you want to remove: " + customStringOutput.replace("Custom: ", ""));
                    buildRemoveButton(builder, (AdapterView<ArrayAdapter<String>>) parent, position);
                } else {
                    // Add
                    builder.setTitle("Buy Specialization");
                    builder.setMessage("Do you want to purchase: " + customStringOutput);
                    buildAddButton(builder, (AdapterView<ArrayAdapter<String>>) parent, position, null);
                }

                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parent.setSelection(0);
                    }
                });

                // Clicked outside the dialog
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        parent.setSelection(0);
                    }
                });

                builder.show();
            } else if (position == parent.getCount() - 1) {
                // Custom Specialization
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Buy Custom Specialization");

                final EditText userInput = new EditText(parent.getContext());
                userInput.setInputType(InputType.TYPE_CLASS_TEXT);
                userInput.setHint("Enter your specialization");
                userInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        } else {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                builder.setView(userInput);

                // Add
                buildAddButton(builder, (AdapterView<ArrayAdapter<String>>) parent, position, userInput);

                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parent.setSelection(0);
                    }
                });

                // Clicked outside the dialog
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        parent.setSelection(0);
                    }
                });

                dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
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

        public void update() {
            int max_attr_mod = 0;

            TextView skillMods;
            TextView skillSpec;
            // Set the current extraInfo blank
            if(extraInfo.getChildCount() < 2){
                skillMods = new TextView(rootView.getContext());
                if(extraInfo.getChildCount() != 0){
                    skillSpec = (TextView) extraInfo.getChildAt(0);
                } else{
                    skillSpec = new TextView(rootView.getContext());
                }

            } else {
                skillSpec = (TextView) extraInfo.getChildAt(0);
                skillMods = (TextView) extraInfo.getChildAt(1);
            }

            TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 0, 0, 0);
            skillMods.setLayoutParams(lp);
            skillMods.setText("");

            for (Quality q : ShadowrunCharacter.getCharacter().getPositiveQualities()) {
                for (Modifier m : q.getMods()) {
                    if (m.getName().equalsIgnoreCase("max_skill_" + skillData.getSkillName())) {
                        skillMods.setText(q.getName());
                    }
                }
            }

            int currentRating = Integer.valueOf(skillLvlTxtView.getText().toString());
            skillLvlTxtView.setText(String.valueOf(currentRating));
            extraInfo.removeAllViews();
            extraInfo.addView(skillSpec);
            extraInfo.addView(skillMods);
        }

        public int getMods() {
            int max_attr_mod = 0;

            if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("max_skill_" + skillData.getSkillName())) {
                for (Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("max_skill_" + skillData.getSkillName())) {
                    max_attr_mod += m.getAmount();
                }
            }

            for (Quality q : ShadowrunCharacter.getCharacter().getPositiveQualities()) {
                for (Modifier m : q.getMods()) {
                    if (m.getName().equalsIgnoreCase("max_skill_" + skillData.getSkillName())) {
                        max_attr_mod += m.getAmount();
                    }
                }
            }

            return max_attr_mod;
        }

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
                TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.fragment_activeskill_SkillGroups_TableLayout);
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
            if (skillsAvailable != null) {
                ArrayList<String> skillNameArray = new ArrayList<>();
                // Grab the name of the skill that has the same groupname
                for (final Skill s : skillsAvailable) {
                    if (s.getGroupName() != null && s.getGroupName().compareToIgnoreCase(skillGroupName) == 0 && s.getSkillName() != skillData.getSkillName()) {
                        skillNameArray.add(s.getSkillName().toLowerCase());
                    }
                }

                // Find the skills highlighted earlier
                TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.fragment_activeskill_Skills_TableLayout);
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
        }

        @Override
        public void onClick(View v) {
            findSkillGroup();

            // Current rating of the skill
            int currentRating = Integer.valueOf(skillLvlTxtView.getText().toString());

            // Current value left for attributes
            Integer skillCounter = getSkillCounter();

            // Current amount of karma left
            Integer karmaUnused = ShadowrunCharacter.getKarma();
            ArrayList<Integer> pointHistory = new ArrayList<>();

            // Get the history of the skills
            if (skillLvlTxtView.getTag() != null) {
                pointHistory = (ArrayList<Integer>) skillLvlTxtView.getTag();
            }

            int max_skill_mod = getMods();

            if (isAddition) {
                if (currentRating < maxSkill + max_skill_mod) {
                    // Use the free skills first, then karma

                    TextView skillGroupTxtView = null;
                    if (skillGroup != null) {
                        skillGroupTxtView = (TextView) skillGroup.getChildAt(ChummerConstants.tableLayout.lvl.ordinal());
                    }

                    // Use the free skills first, then karma
                    if (skillCounter > 0 && !wasKarmaUsed(skillGroupTxtView) && !werePointUsed(skillGroupTxtView) && !wasKarmaUsed(skillLvlTxtView)) {
                        currentRating++;
                        skillCounter--;
                        pointHistory.add(ChummerConstants.skillPointUsed);
                        toggleSkillGroup(currentRating);
                    } else {
                        int karmaCost;
                        if (isActiveSkill) {
                            karmaCost = ChummerMethods.formulaActiveSkillCost(currentRating + 1);
                        } else {
                            karmaCost = ChummerMethods.formulaKnowledgeCost(currentRating + 1);
                        }

                        if (karmaCost <= karmaUnused) {
                            pointHistory.add(karmaCost);
                            karmaUnused -= karmaCost;
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
                            int karmaCost;
                            if (isActiveSkill) {
                                karmaCost = ChummerMethods.formulaActiveSkillCost(currentRating);
                            } else {
                                karmaCost = ChummerMethods.formulaKnowledgeCost(currentRating);
                            }

                            karmaUnused += karmaCost;
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
            setSkillCounter(skillCounter);
            updateSkillCounter();
            updateKarma();
        }
    }

}