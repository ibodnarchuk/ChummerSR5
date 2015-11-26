package com.iwan_b.chummersr5.fragments.ActiveSkill;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerXML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainContainer extends Fragment {
    private static View rootView;

    public static void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    private void updateFreeSkillCounter(final Integer skillCounter) {
        TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkillsCounter);
        freeSkillsTxtView.setText(String.valueOf(skillCounter));
    }

    private void updateFreeSkillGroupCounter(final Integer groupSkillCounter) {
        TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroupsCounter);
        freeSkillGroupTxt.setText(String.valueOf(groupSkillCounter));
    }

    private void updateCounters(final Integer skillCounter, final Integer groupSkillCounter) {
        updateFreeSkillCounter(skillCounter);
        updateFreeSkillGroupCounter(groupSkillCounter);

        updateKarma();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activeskillsfragment, container, false);

        int freeSkill = 0;
        int freeSkillGroup = 0;

        // TODO change the hardcode containskey
        if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill")) {

            for (Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill")) {
                freeSkill += m.getAmount();
            }
        }

        // TODO change the hardcode containskey
        if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill_group")) {
            for (Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill_group")) {
                freeSkillGroup += m.getAmount();
            }
        }

        updateCounters(freeSkill, freeSkillGroup);

        ArrayList<Skill> allSkills = readSkillsXML("skills/skills.xml");

        Collections.sort(allSkills);

        TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillsTableLayout);
        SkillTableRow genSkillTableRow = new SkillTableRow(SkillsTableLayout.getRootView(), allSkills, R.id.freeSkillsCounter, true);

        ArrayList<String> skillGroup = new ArrayList<>();


        for (final Skill currentSkill : allSkills) {
            // Add the skill group listed
            if (currentSkill.getGroupName() != null && !currentSkill.getGroupName().isEmpty()) {
                // Don't allow duplicates
                if (!skillGroup.contains(currentSkill.getGroupName())) {
                    if (currentSkill.getMagicOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic() > 0) {
                        skillGroup.add(currentSkill.getGroupName());
                    } else if (currentSkill.getTechnomancerOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseRes() > 0) {
                        skillGroup.add(currentSkill.getGroupName());
                    } else if (!currentSkill.getMagicOnly() && !currentSkill.getTechnomancerOnly()) {
                        skillGroup.add(currentSkill.getGroupName());
                    }
                }
            }

            if (currentSkill.getMagicOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic() > 0) {
                SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
            } else if (currentSkill.getTechnomancerOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseRes() > 0) {
                SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
            } else if (!currentSkill.getMagicOnly() && !currentSkill.getTechnomancerOnly()) {
                SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
            }
        }

        // Add Custom skill Button
        TableRow newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);
        final Button addCustomButtonListener = new Button(rootView.getContext());

        addCustomButtonListener.setText("Add Custom Skill");
        TableRow.LayoutParams params = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        // Span the entire Row
        params.span = ChummerConstants.tableLayout.values().length;
        addCustomButtonListener.setLayoutParams(params);
        addCustomButtonListener.setOnClickListener(new addCustomSkillButtonListener(SkillsTableLayout));
        newTableRow.addView(addCustomButtonListener);
        SkillsTableLayout.addView(newTableRow);

        // Create the skill group now since we have the data from all the skills
        TableLayout SkillsGroupTableLayout = (TableLayout) rootView.findViewById(R.id.SkillGroupsTableLayout);

        SkillGroupTableRow genSkillGroupTableRow = new SkillGroupTableRow(SkillsGroupTableLayout.getRootView(), allSkills);
        // Add skill groups
        for (final String sGroup : skillGroup) {
            SkillsGroupTableLayout.addView(genSkillGroupTableRow.createRow(sGroup));
        }


        return rootView;
    }

    private ArrayList<Skill> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Skill> Skills = new ArrayList<>();
        Skill tempSkill = null;
        boolean spec = false;

        int eventType = parser.getEventType();
        // TODO change all the hardcoded xml properties used further down
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    name = parser.getName();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
//				Log.i(ChummerConstants.TAG, "START_TAG " + name);
                    if (name.equalsIgnoreCase("skill")) {
                        tempSkill = new Skill();
                    } else if (tempSkill != null) {
                        if (name.equalsIgnoreCase("spec")) {
                            spec = true;
                        }

                        if (spec) {
                            switch (name.toLowerCase()) {
                                case "item":
                                    if (tempSkill.getSpec() == null) {
                                        tempSkill.setSpec(new ArrayList<String>());
                                    }
                                    tempSkill.getSpec().add(parser.nextText());
                                    break;
                            }
                        } else {
                            String s = parser.nextText();
                            switch (name.toLowerCase()) {
                                case "name":
                                    tempSkill.setSkillName(s);
                                    break;
                                case "book":
                                    tempSkill.setBook(s);
                                    break;
                                case "page":
                                    tempSkill.setPage(s);
                                    break;
                                case "summary":
                                    tempSkill.setSummary(s);
                                    break;
                                case "magiconly":
                                    if (s.equalsIgnoreCase("true")) {
                                        tempSkill.setMagicOnly(true);
                                    }
                                    break;
                                case "techonly":
                                    if (s.equalsIgnoreCase("true")) {
                                        tempSkill.setTechnomancerOnly(true);
                                    }
                                    break;
                                case "attr":
                                    tempSkill.setAttrName(s);
                                    break;
                                case "defaultable":
                                    tempSkill.setIsDefaultable(Boolean.getBoolean(s));
                                    break;
                                case "group":
                                    tempSkill.setGroupName(s);
                                    break;
                            }
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
//				Log.i(ChummerConstants.TAG, "END_TAG " + name);

                    switch (name.toLowerCase()) {
                        case "spec":
                            spec = false;
                            break;
                        case "skill":
                            Skills.add(tempSkill);
                            tempSkill = null;
                            break;
                    }
            }

            eventType = parser.next();
        }

        return Skills;
    }

    /**
     * @param fileLocation of the file to parse
     * @return an Array of PriorityTable data
     */
    private ArrayList<Skill> readSkillsXML(final String fileLocation) {
        ArrayList<Skill> skills = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getActivity().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            skills = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return skills;
    }



    private class addCustomSkillButtonListener implements View.OnClickListener {
        private TableLayout skillsTableLayout;

        public addCustomSkillButtonListener(TableLayout skillsTableLayout) {
            this.skillsTableLayout = skillsTableLayout;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Buy new custom skill");

            LinearLayout linearLayoutContainer = new LinearLayout(v.getContext());

            final EditText userNameInput = new EditText(v.getContext());
            userNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
            userNameInput.setHint("Enter your skill");
            TableRow.LayoutParams usernameParams = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            usernameParams.weight = 1;
            userNameInput.setLayoutParams(usernameParams);
            linearLayoutContainer.addView(userNameInput);

            ArrayList<String> attributes = ChummerXML.readStringXML(getActivity(), "data/AttributesFull.xml");

            final Spinner attrSpinner = new Spinner(v.getContext());
            //selected item will look like a spinner set from XML
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, attributes);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            attrSpinner.setAdapter(spinnerArrayAdapter);
            TableRow.LayoutParams attrParams = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            attrParams.weight = 1;
            attrSpinner.setLayoutParams(attrParams);

            linearLayoutContainer.addView(attrSpinner);

            builder.setView(linearLayoutContainer);

            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Current amount of karma left
                    Integer karmaUnused = ShadowrunCharacter.getKarma();

                    // TODO change after chargen.
                    if (karmaUnused > 2) {
                        karmaUnused -= 2;

                        Skill newSkill = new Skill();

                        newSkill.setSkillName("Custom: " + userNameInput.getText().toString());
                        newSkill.setAttrName(attrSpinner.getSelectedItem().toString());
                        newSkill.setIsDefaultable(false);
                        newSkill.setRating(1);

                        SkillTableRow genSkillTableRow = new SkillTableRow(rootView, null, R.id.freeSkillsCounter, true);

                        TableRow output = genSkillTableRow.createRow(newSkill);

                        // Prevent the user from entering a value lower than 1 for buying this skill.
                        TextView skillValueTxtView = (TextView) output.getChildAt(ChummerConstants.tableLayout.lvl.ordinal());
                        ArrayList<Integer> pointHistory = new ArrayList<>();
                        pointHistory.add(2);
                        pointHistory.add(ChummerConstants.freeSkillLevel);
                        skillValueTxtView.setTag(pointHistory);

                        skillsTableLayout.addView(output, skillsTableLayout.getChildCount() - 1);

                        ShadowrunCharacter.setKarma(karmaUnused);
                        updateKarma();
                    } else {
                        Toast.makeText(rootView.getContext(), "You don't have enough karma to buy this", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            // Clicked outside the dialog
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            builder.show();
//            TableRow newTableRow = new TableRow(rootView.getContext());
//            newTableRow.setGravity(Gravity.CENTER_VERTICAL);
//            skillsTableLayout.addView(newTableRow);
        }
    }
}