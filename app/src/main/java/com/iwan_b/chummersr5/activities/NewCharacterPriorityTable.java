package com.iwan_b.chummersr5.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.Attribute;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.PriorityTable;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerMethods;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewCharacterPriorityTable extends Activity {
    private ArrayList<PriorityTable> metaTable;
    private ArrayList<PriorityTable> attrTable;
    private ArrayList<PriorityTable> magicTable;
    private ArrayList<PriorityTable> skillTable;
    private ArrayList<PriorityTable> resTable;
    private boolean radioButtonLock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_table);

        metaTable = readXML("chargen/metachargen.xml");
        attrTable = readXML("chargen/attchargen.xml");
        magicTable = readXML("chargen/magicchargen.xml");
        skillTable = readXML("chargen/skillchargen.xml");
        resTable = readXML("chargen/reschargen.xml");
        // Done button
        final Button doneButton = (Button) findViewById(R.id.activity_priority_table_NewCharacter_Button_Done);

        // **********************************************************************************
        // Meta Section
        createSection(metaTable, R.id.activity_priority_table_MetaTypeRadioGroupA, R.id.activity_priority_table_MetaTypeRadioGroupB,
                R.id.activity_priority_table_MetaTypeRadioGroupC, R.id.activity_priority_table_MetaTypeRadioGroupD, R.id.activity_priority_table_MetaTypeRadioGroupE);

        createMetaOnClickListeners();
        // **********************************************************************************
        // Attribute section
        createSection(attrTable, R.id.activity_priority_table_AttributeRadioGroupA, R.id.activity_priority_table_AttributeRadioGroupB,
                R.id.activity_priority_table_AttributeRadioGroupC, R.id.activity_priority_table_AttributeRadioGroupD, R.id.activity_priority_table_AttributeRadioGroupE);

        createAttrOnClickListeners();

        // **********************************************************************************
        // Magic Selection
        createSection(magicTable, R.id.activity_priority_table_MagicRadioGroupA, R.id.activity_priority_table_MagicRadioGroupB,
                R.id.activity_priority_table_MagicRadioGroupC, R.id.activity_priority_table_MagicRadioGroupD, R.id.activity_priority_table_MagicRadioGroupE);

        createMagicOnClickListeners();

        // **********************************************************************************
        // Skill Selection
        createSection(skillTable, R.id.activity_priority_table_SkillRadioGroupA, R.id.activity_priority_table_SkillRadioGroupB,
                R.id.activity_priority_table_SkillRadioGroupC, R.id.activity_priority_table_SkillRadioGroupD, R.id.activity_priority_table_SkillRadioGroupE);

        createSkillOnClickListeners();

        // **********************************************************************************
        // Resource Selection
        createSection(resTable, R.id.activity_priority_table_ResourceRadioGroupA, R.id.activity_priority_table_ResourceRadioGroupB,
                R.id.activity_priority_table_ResourceRadioGroupC, R.id.activity_priority_table_ResourceRadioGroupD, R.id.activity_priority_table_ResourceRadioGroupE);

        createResourceOnClickListeners();

        // **********************************************************************************

        // TODO remove this from the default selection
        RadioGroup metaRadioGroupD = (RadioGroup) findViewById(R.id.activity_priority_table_MetaTypeRadioGroupD);
        ((RadioButton) metaRadioGroupD.getChildAt(0)).setChecked(true);

        RadioGroup attRadioGroupB = (RadioGroup) findViewById(R.id.activity_priority_table_AttributeRadioGroupB);
        ((RadioButton) attRadioGroupB.getChildAt(0)).setChecked(true);

        RadioGroup magRadioGroupC = (RadioGroup) findViewById(R.id.activity_priority_table_MagicRadioGroupC);
        ((RadioButton) magRadioGroupC.getChildAt(0)).setChecked(true);

        RadioGroup skillRadioGroupA = (RadioGroup) findViewById(R.id.activity_priority_table_SkillRadioGroupA);
        ((RadioButton) skillRadioGroupA.getChildAt(0)).setChecked(true);

        RadioGroup resourceRadioGroupE = (RadioGroup) findViewById(R.id.activity_priority_table_ResourceRadioGroupE);
        ((RadioButton) resourceRadioGroupE.getChildAt(0)).setChecked(true);

        doneButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean a = false;
                boolean b = false;
                boolean c = false;
                boolean d = false;
                boolean e = false;

                // Test to make sure that all priority items have been selected
                if (isRadioGroupSelected(R.id.activity_priority_table_MetaTypeRadioGroupA, R.id.activity_priority_table_AttributeRadioGroupA, R.id.activity_priority_table_MagicRadioGroupA, R.id.activity_priority_table_SkillRadioGroupA, R.id.activity_priority_table_ResourceRadioGroupA)) {
                    a = true;
                }

                if (isRadioGroupSelected(R.id.activity_priority_table_MetaTypeRadioGroupB, R.id.activity_priority_table_AttributeRadioGroupB, R.id.activity_priority_table_MagicRadioGroupB, R.id.activity_priority_table_SkillRadioGroupB, R.id.activity_priority_table_ResourceRadioGroupB)) {
                    b = true;
                }

                if (isRadioGroupSelected(R.id.activity_priority_table_MetaTypeRadioGroupC, R.id.activity_priority_table_AttributeRadioGroupC, R.id.activity_priority_table_MagicRadioGroupC, R.id.activity_priority_table_SkillRadioGroupC, R.id.activity_priority_table_ResourceRadioGroupC)) {
                    c = true;
                }

                if (isRadioGroupSelected(R.id.activity_priority_table_MetaTypeRadioGroupD, R.id.activity_priority_table_AttributeRadioGroupD, R.id.activity_priority_table_MagicRadioGroupD, R.id.activity_priority_table_SkillRadioGroupD, R.id.activity_priority_table_ResourceRadioGroupD)) {
                    d = true;
                }


                if (isRadioGroupSelected(R.id.activity_priority_table_MetaTypeRadioGroupE, R.id.activity_priority_table_AttributeRadioGroupE, R.id.activity_priority_table_MagicRadioGroupE, R.id.activity_priority_table_SkillRadioGroupE, R.id.activity_priority_table_ResourceRadioGroupE)) {
                    e = true;
                }


                // All the 5 priorities have been selected
                if (a && b && c && d && e) {
                    int metaIndex;
                    int attrIndex;
                    int magicIndex;
                    int skillIndex;
                    int resIndex;

                    metaIndex = getIndex(metaTable, R.id.activity_priority_table_MetaTypeRadioGroupA, R.id.activity_priority_table_MetaTypeRadioGroupB,
                            R.id.activity_priority_table_MetaTypeRadioGroupC, R.id.activity_priority_table_MetaTypeRadioGroupD, R.id.activity_priority_table_MetaTypeRadioGroupE);

                    attrIndex = getIndex(attrTable, R.id.activity_priority_table_AttributeRadioGroupA, R.id.activity_priority_table_AttributeRadioGroupB,
                            R.id.activity_priority_table_AttributeRadioGroupC, R.id.activity_priority_table_AttributeRadioGroupD, R.id.activity_priority_table_AttributeRadioGroupE);

                    magicIndex = getIndex(magicTable, R.id.activity_priority_table_MagicRadioGroupA, R.id.activity_priority_table_MagicRadioGroupB,
                            R.id.activity_priority_table_MagicRadioGroupC, R.id.activity_priority_table_MagicRadioGroupD, R.id.activity_priority_table_MagicRadioGroupE);

                    skillIndex = getIndex(skillTable, R.id.activity_priority_table_SkillRadioGroupA, R.id.activity_priority_table_SkillRadioGroupB,
                            R.id.activity_priority_table_SkillRadioGroupC, R.id.activity_priority_table_SkillRadioGroupD, R.id.activity_priority_table_SkillRadioGroupE);

                    resIndex = getIndex(resTable, R.id.activity_priority_table_ResourceRadioGroupA, R.id.activity_priority_table_ResourceRadioGroupB,
                            R.id.activity_priority_table_ResourceRadioGroupC, R.id.activity_priority_table_ResourceRadioGroupD, R.id.activity_priority_table_ResourceRadioGroupE);

                    Intent i = new Intent(NewCharacterPriorityTable.this, SwipeFragmentHolder.class);


                    ChummerMethods.addModstoChar(metaTable.get(metaIndex).getMods(), ShadowrunCharacter.getCharacter());
                    ChummerMethods.addModstoChar(attrTable.get(attrIndex).getMods(), ShadowrunCharacter.getCharacter());
                    ChummerMethods.addModstoChar(magicTable.get(magicIndex).getMods(), ShadowrunCharacter.getCharacter());
                    ChummerMethods.addModstoChar(skillTable.get(skillIndex).getMods(), ShadowrunCharacter.getCharacter());
                    ChummerMethods.addModstoChar(resTable.get(resIndex).getMods(), ShadowrunCharacter.getCharacter());

                    setUserType(magicIndex);

                    // Set the initial display counters
                    FreeCounters.getCounters().setFreeAttributes((int) attrTable.get(attrIndex).getStats());
                    FreeCounters.getCounters().setFreeSpecAttributes((int) metaTable.get(metaIndex).getStats());

                    if (ShadowrunCharacter.getCharacter().getUserType() >= ChummerConstants.userType.magician.ordinal()) {
                        FreeCounters.getCounters().setFreeSpells(0);
                        for (Modifier m : magicTable.get(magicIndex).getMods()) {
                            if (m.getName().equalsIgnoreCase("free_spells")) {
                                FreeCounters.getCounters().setFreeSpells((int) m.getAmount());
                            }
                        }
                    }

                    buildChar(metaIndex, magicIndex);

                    // TODO get the karma from an xml file.
                    ShadowrunCharacter.setKarma(ChummerConstants.startingKarma);

                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select all 5 priorities",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void setUserType(int magicIndex) {
        switch (magicTable.get(magicIndex).getUserType().toLowerCase()) {
            // TODO hardcoded
            case "mundane":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.mundane.ordinal());
                break;
            case "technomancer":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.technomancer.ordinal());
                break;
            case "adept":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.adept.ordinal());
                break;
            case "magician":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.magician.ordinal());
                break;
            case "aspected_magician":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.aspected_magician.ordinal());
                break;
            case "mystic_adept":
                ShadowrunCharacter.getCharacter().setUserType(ChummerConstants.userType.mystic_adept.ordinal());
                break;
        }

    }

    private int getIndex(final ArrayList<PriorityTable> priorityTables, final int radioGroupAID, final int radioGroupBID,
                         final int radioGroupCID, final int radioGroupDID, final int radioGroupEID) {
        RadioButton rb = null;

        final RadioGroup radioGroupA = (RadioGroup) findViewById(radioGroupAID);
        final RadioGroup radioGroupB = (RadioGroup) findViewById(radioGroupBID);
        final RadioGroup radioGroupC = (RadioGroup) findViewById(radioGroupCID);
        final RadioGroup radioGroupD = (RadioGroup) findViewById(radioGroupDID);
        final RadioGroup radioGroupE = (RadioGroup) findViewById(radioGroupEID);

        if (radioGroupA.getCheckedRadioButtonId() != -1) {
            int selectedId = radioGroupA.getCheckedRadioButtonId();
            rb = (RadioButton) radioGroupA.findViewById(selectedId);
        } else if (radioGroupB.getCheckedRadioButtonId() != -1) {
            int selectedId = radioGroupB.getCheckedRadioButtonId();
            rb = (RadioButton) radioGroupB.findViewById(selectedId);
        } else if (radioGroupC.getCheckedRadioButtonId() != -1) {
            int selectedId = radioGroupC.getCheckedRadioButtonId();
            rb = (RadioButton) radioGroupC.findViewById(selectedId);
        } else if (radioGroupD.getCheckedRadioButtonId() != -1) {
            int selectedId = radioGroupD.getCheckedRadioButtonId();
            rb = (RadioButton) radioGroupD.findViewById(selectedId);
        } else if (radioGroupE.getCheckedRadioButtonId() != -1) {
            int selectedId = radioGroupE.getCheckedRadioButtonId();
            rb = (RadioButton) radioGroupE.findViewById(selectedId);
        }

        return findPriorityIndex(rb, priorityTables);
    }

    private boolean isRadioGroupSelected(final int metaTypeRadioGroupID, final int attributeRadioGroupID,
                                         final int magicRadioGroupID, final int skillRadioGroupID, final int resourceRadioGroupID) {
        final RadioGroup metaRadioGroup = (RadioGroup) findViewById(metaTypeRadioGroupID);
        final RadioGroup attributeRadioGroup = (RadioGroup) findViewById(attributeRadioGroupID);
        final RadioGroup magicRadioGroup = (RadioGroup) findViewById(magicRadioGroupID);
        final RadioGroup skillRadioGroup = (RadioGroup) findViewById(skillRadioGroupID);
        final RadioGroup resourceRadioGroup = (RadioGroup) findViewById(resourceRadioGroupID);

        return metaRadioGroup.getCheckedRadioButtonId() != -1 || attributeRadioGroup.getCheckedRadioButtonId() != -1
                || magicRadioGroup.getCheckedRadioButtonId() != -1
                || skillRadioGroup.getCheckedRadioButtonId() != -1
                || resourceRadioGroup.getCheckedRadioButtonId() != -1;
    }

    private void createSection(ArrayList<PriorityTable> metaDataArray, final int groupAID, final int groupBID,
                               final int groupCID, final int groupDID, final int groupEID) {
        // All the columns and radio groups for the table
        final RadioGroup radioGroupA = (RadioGroup) findViewById(groupAID);
        final RadioGroup radioGroupB = (RadioGroup) findViewById(groupBID);
        final RadioGroup radioGroupC = (RadioGroup) findViewById(groupCID);
        final RadioGroup radioGroupD = (RadioGroup) findViewById(groupDID);
        final RadioGroup radioGroupE = (RadioGroup) findViewById(groupEID);

        // The priorities selection
        final String priorityA = getResources().getString(R.string.priorityA);
        final String priorityB = getResources().getString(R.string.priorityB);
        final String priorityC = getResources().getString(R.string.priorityC);
        final String priorityD = getResources().getString(R.string.priorityD);
        final String priorityE = getResources().getString(R.string.priorityE);


        RadioButton temp;
        // Metatype Section
        if (metaDataArray != null) {
            for (PriorityTable p : metaDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    radioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    radioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    radioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    radioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    radioGroupE.addView(temp);
                }
            }
        }
    }

    private void addOnClickListeners(final int mainRadioGroupID, ArrayList<Integer> sameRankGroupID, ArrayList<Integer> sameRadioGroupID) {
        final RadioGroup radioGroup = (RadioGroup) findViewById(mainRadioGroupID);

        final ArrayList<Integer> sameGroup = new ArrayList<>(sameRadioGroupID);
        final ArrayList<Integer> sameRankGroup = new ArrayList<>(sameRankGroupID);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    // Uncheck all the other checkboxes that are the same level.
                    for (int id : sameRankGroup) {
                        RadioGroup tempRadioGroup = (RadioGroup) findViewById(id);
                        tempRadioGroup.check(-1);
                    }

                    // Uncheck all the checkboxes that are the same group
                    for (int id : sameGroup) {
                        RadioGroup tempRadioGroup = (RadioGroup) findViewById(id);
                        tempRadioGroup.check(-1);
                    }

                    radioButtonLock = true;
                }
            }
        });
    }

    private void createMetaOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();

        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        addOnClickListeners(R.id.activity_priority_table_MetaTypeRadioGroupA, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        addOnClickListeners(R.id.activity_priority_table_MetaTypeRadioGroupB, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        addOnClickListeners(R.id.activity_priority_table_MetaTypeRadioGroupC, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        addOnClickListeners(R.id.activity_priority_table_MetaTypeRadioGroupD, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_MetaTypeRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createResourceOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_ResourceRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_ResourceRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_ResourceRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_ResourceRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        addOnClickListeners(R.id.activity_priority_table_ResourceRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createSkillOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_SkillRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_SkillRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_SkillRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_SkillRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        addOnClickListeners(R.id.activity_priority_table_SkillRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createMagicOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_MagicRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_MagicRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_MagicRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_MagicRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        addOnClickListeners(R.id.activity_priority_table_MagicRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createAttrOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupA);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_AttributeRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupB);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_AttributeRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupC);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_AttributeRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupD);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupE);
        addOnClickListeners(R.id.activity_priority_table_AttributeRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.activity_priority_table_MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_MagicRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_SkillRadioGroupE);
        sameRankGroupID.add(R.id.activity_priority_table_ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupA);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupB);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupC);
        sameGroupID.add(R.id.activity_priority_table_AttributeRadioGroupD);
        addOnClickListeners(R.id.activity_priority_table_AttributeRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private int findPriorityIndex(final Button radioButton, final ArrayList<PriorityTable> priorityList) {
        for (int x = 0; x < priorityList.size(); x++) {
            if (radioButton.getText().toString().compareToIgnoreCase(priorityList.get(x).getDisplayText()) == 0) {
                return x;
            }
        }
        return 0;
    }

    /**
     * @param fileLocation of the file to parse
     * @return an Array of PriorityTable data
     */
    private ArrayList<PriorityTable> readXML(final String fileLocation) {
        ArrayList<PriorityTable> priorityData = null;
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open(fileLocation);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            priorityData = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return priorityData;
    }

    private ArrayList<PriorityTable> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<PriorityTable> Attributes = new ArrayList<>();

        int eventType = parser.getEventType();
        PriorityTable currentAttribute = null;

        Modifier m = null;
        boolean mod = false;

        // TODO change all the hardcoded xml properties used further down
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    name = parser.getName();
                    // Log.i(ChummerConstants.TAG, "START_DOCUMENT " + name);
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("priority")) {
                        currentAttribute = new PriorityTable();

                        int attCount = parser.getAttributeCount();
                        String p = getString(R.string.type);
                        for (int i = 0; i < attCount; i++) {
                            String s = parser.getAttributeName(i);
                            String r = parser.getAttributeValue(i);
                            if (s.equalsIgnoreCase(p)) {
                                currentAttribute.setPriority(r);
                            }
                        }

                    } else if (currentAttribute != null) {
                        if (name.equalsIgnoreCase("mod")) {
                            m = new Modifier();
                            mod = true;
                        }

                        if (mod) {
                            switch (name.toLowerCase()) {
                                case "name":
                                    m.setName(parser.nextText());
                                    break;
                                case "amount":
                                    Integer i = Integer.valueOf(parser.nextText());
                                    m.setAmount(i);
                                    break;
                                case "displaytext":
                                    m.setDisplayText(parser.nextText());
                                    break;
                                case "summary":
                                    m.setSummary(parser.nextText());
                                    break;
                                case "book":
                                    m.setBook(parser.nextText());
                                    break;
                                case "page":
                                    m.setPage(parser.nextText());
                                    break;
                            }
                        } else {
                            switch (name.toLowerCase()) {
                                case "displaytext":
                                    currentAttribute.setDisplayText(parser.nextText());
                                    break;
                                case "stats":
                                    Integer i = Integer.valueOf(parser.nextText());
                                    currentAttribute.setStats(i);
                                    break;
                                case "summary":
                                    currentAttribute.setSummary(parser.nextText());
                                    break;
                                case "book":
                                    currentAttribute.setBook(parser.nextText());
                                    break;
                                case "page":
                                    currentAttribute.setPage(parser.nextText());
                                    break;
                                case "metatype":
                                    currentAttribute.setMetaTypeName(parser.nextText());
                                    break;
                                case "usertype":
                                    currentAttribute.setUserType(parser.nextText());
                                    break;
                            }
                        }

                    }
                    // Log.i(ChummerConstants.TAG, "START_TAG " + name);
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("mod")) {
                        if (currentAttribute.getMods() == null) {
                            ArrayList<Modifier> temp = new ArrayList<>();
                            temp.add(m);
                            currentAttribute.setMods(temp);
                            m = null;
                        } else {
                            currentAttribute.getMods().add(m);
                        }

                        mod = false;
                    }

                    if (name.equalsIgnoreCase("priority") && currentAttribute != null) {
                        Attributes.add(currentAttribute);
                    }

                    // Log.i(ChummerConstants.TAG, "END_TAG " + name);
            }

            eventType = parser.next();
        }

        return Attributes;
    }


    private void buildChar(int metaIndex, int magicIndex) {
        ShadowrunCharacter newCharacter = ShadowrunCharacter.getCharacter();

        String metastring = metaTable.get(metaIndex).getMetaTypeName();

        // TODO make a metatype class
        final Attribute attrs = readAttributeXML("metatypes/" + metastring + ".xml");

        attrs.setBaseMagic(0);
        attrs.setBaseRes(0);
        attrs.setMaxMagic(6);
        attrs.setMaxRes(6);

        // Test if they are mundane or not
        if (newCharacter.getUserType() >= ChummerConstants.userType.magician.ordinal()) {
            attrs.setBaseMagic((int) magicTable.get(magicIndex).getStats());
            attrs.setMagic((int) magicTable.get(magicIndex).getStats());
        } else if (newCharacter.getUserType() == ChummerConstants.userType.technomancer.ordinal()) {
            attrs.setBaseRes((int) magicTable.get(magicIndex).getStats());
            attrs.setRes((int) magicTable.get(magicIndex).getStats());
        }

        newCharacter.setAttributes(attrs);
    }

    private Attribute parseAttributeXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        Attribute attr = null;
        Modifier m = null;
        boolean mod = false;

        int eventType = parser.getEventType();
        // TODO change all the hardcoded xml properties used further down
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    name = parser.getName();
                    // Log.i(ChummerConstants.TAG, "START_DOCUMENT " + name);
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("metatype")) {
                        attr = new Attribute();

                        int attCount = parser.getAttributeCount();
                        // TODO example of how to get the string
                        String p = getString(R.string.type);
                    } else if (attr != null) {
                        if (name.equalsIgnoreCase("mod")) {
                            m = new Modifier();
                            mod = true;
                        }

                        if (mod) {
                            switch (name.toLowerCase()) {
                                case "name":
                                    m.setName(parser.nextText());
                                    break;
                                case "amount":
                                    Integer i;
                                    // TODO figure if this is the best place. Currently
                                    // throws an error if the user doesn't put anything
                                    // between the brackets in the xml file
                                    try {
                                        i = Integer.valueOf(parser.nextText());
                                    } catch (NumberFormatException e) {
                                        i = 0;
                                    }
                                    m.setAmount(i);
                                    break;
                                case "displaytext":
                                    m.setDisplayText(parser.nextText());
                                    break;
                                case "summary":
                                    m.setSummary(parser.nextText());
                                    break;
                                case "book":
                                    m.setBook(parser.nextText());
                                    break;
                                case "page":
                                    m.setPage(parser.nextText());
                                    break;
                            }

                        } else {
                            String s = parser.nextText();
                            Integer i;

                            switch (name.toLowerCase()) {
                                case "race":
                                    attr.setRace(s);
                                    break;
                            /* Base stats and starting stats */
                                case "basebody":
                                    i = Integer.valueOf(s);
                                    attr.setBaseBody(i);
                                    attr.setBody(i);
                                    break;
                                case "baseagi":
                                    i = Integer.valueOf(s);
                                    attr.setBaseAgi(i);
                                    attr.setAgi(i);
                                    break;
                                case "baserea":
                                    i = Integer.valueOf(s);
                                    attr.setBaseRea(i);
                                    attr.setRea(i);
                                    break;
                                case "basestr":
                                    i = Integer.valueOf(s);
                                    attr.setBaseStr(i);
                                    attr.setStr(i);
                                    break;
                                case "basewil":
                                    i = Integer.valueOf(s);
                                    attr.setBaseWil(i);
                                    attr.setWil(i);
                                    break;
                                case "baselog":
                                    i = Integer.valueOf(s);
                                    attr.setBaseLog(i);
                                    attr.setLog(i);
                                    break;
                                case "baseintu":
                                    i = Integer.valueOf(s);
                                    attr.setBaseInt(i);
                                    attr.setIntu(i);
                                    break;
                                case "basecha":
                                    i = Integer.valueOf(s);
                                    attr.setBaseCha(i);
                                    attr.setCha(i);
                                    break;
                                case "baseedge":
                                    i = Integer.valueOf(s);
                                    attr.setBaseEdge(i);
                                    attr.setEdge(i);
                                    break;
                            /* Max Stats here */
                                case "maxbody":
                                    i = Integer.valueOf(s);
                                    attr.setMaxBody(i);
                                    break;
                                case "maxagi":
                                    i = Integer.valueOf(s);
                                    attr.setMaxAgi(i);
                                    break;
                                case "maxrea":
                                    i = Integer.valueOf(s);
                                    attr.setMaxRea(i);
                                    break;
                                case "maxstr":
                                    i = Integer.valueOf(s);
                                    attr.setMaxStr(i);
                                    break;
                                case "maxwil":
                                    i = Integer.valueOf(s);
                                    attr.setMaxWil(i);
                                    break;
                                case "maxlog":
                                    i = Integer.valueOf(s);
                                    attr.setMaxLog(i);
                                    break;
                                case "maxintu":
                                    i = Integer.valueOf(s);
                                    attr.setMaxInt(i);
                                    break;
                                case "maxcha":
                                    i = Integer.valueOf(s);
                                    attr.setMaxCha(i);
                                    break;
                                case "maxedge":
                                    i = Integer.valueOf(s);
                                    attr.setMaxEdge(i);
                                    break;
                                case "ess":
                                    i = Integer.valueOf(s);
                                    attr.setEss(i);
                                    break;
                            }

                        }

                    }
                    // Log.i(ChummerConstants.TAG, "START_TAG " + name);
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("mod")) {
                        ChummerMethods.addModstoChar(m, ShadowrunCharacter.getCharacter());
                        m = null;
                        mod = false;
                    }
                    if (name.equalsIgnoreCase("metatype")) {
                        return attr;
                    }

                    // Log.i(ChummerConstants.TAG, "END_TAG " + name);
            }

            eventType = parser.next();
        }

        return attr;
    }

    private Attribute readAttributeXML(final String fileLocation) {
        Attribute attr = null;
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open(fileLocation);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            attr = parseAttributeXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return attr;
    }

}