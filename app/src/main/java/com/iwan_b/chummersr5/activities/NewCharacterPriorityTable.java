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
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.PriorityTable;
import com.iwan_b.chummersr5.utility.ChummerConstants;

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
        setContentView(R.layout.newcharacterselection);

        metaTable = readXML("chargen/metachargen.xml");
        attrTable = readXML("chargen/attchargen.xml");
        magicTable = readXML("chargen/magicchargen.xml");
        skillTable = readXML("chargen/skillchargen.xml");
        resTable = readXML("chargen/reschargen.xml");
        // Done button
        final Button doneButton = (Button) findViewById(R.id.NewCharacterButtonDone);

        // **********************************************************************************
        // Meta Section
        createSection(metaTable, R.id.MetaTypeRadioGroupA, R.id.MetaTypeRadioGroupB,
                R.id.MetaTypeRadioGroupC, R.id.MetaTypeRadioGroupD, R.id.MetaTypeRadioGroupE);

        createMetaOnClickListeners();
        // **********************************************************************************
        // Attribute section
        createSection(attrTable, R.id.AttributeRadioGroupA, R.id.AttributeRadioGroupB,
                R.id.AttributeRadioGroupC, R.id.AttributeRadioGroupD, R.id.AttributeRadioGroupE);

        createAttrOnClickListeners();

        // **********************************************************************************
        // Magic Selection
        createSection(magicTable, R.id.MagicRadioGroupA, R.id.MagicRadioGroupB,
                R.id.MagicRadioGroupC, R.id.MagicRadioGroupD, R.id.MagicRadioGroupE);

        createMagicOnClickListeners();

        // **********************************************************************************
        // Skill Selection
        createSection(skillTable, R.id.SkillRadioGroupA, R.id.SkillRadioGroupB,
                R.id.SkillRadioGroupC, R.id.SkillRadioGroupD, R.id.SkillRadioGroupE);

        createSkillOnClickListeners();

        // **********************************************************************************
        // Resource Selection
        createSection(resTable, R.id.ResourceRadioGroupA, R.id.ResourceRadioGroupB,
                R.id.ResourceRadioGroupC, R.id.ResourceRadioGroupD, R.id.ResourceRadioGroupE);

        createResourceOnClickListeners();

        // **********************************************************************************

        // TODO remove this from the default selection
        RadioGroup metaRadioGroupD = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupD);
        ((RadioButton) metaRadioGroupD.getChildAt(0)).setChecked(true);

        RadioGroup attRadioGroupB = (RadioGroup) findViewById(R.id.AttributeRadioGroupB);
        ((RadioButton) attRadioGroupB.getChildAt(0)).setChecked(true);

        RadioGroup magRadioGroupC = (RadioGroup) findViewById(R.id.MagicRadioGroupC);
        ((RadioButton) magRadioGroupC.getChildAt(0)).setChecked(true);

        RadioGroup skillRadioGroupA = (RadioGroup) findViewById(R.id.SkillRadioGroupA);
        ((RadioButton) skillRadioGroupA.getChildAt(0)).setChecked(true);

        RadioGroup resourceRadioGroupE = (RadioGroup) findViewById(R.id.ResourceRadioGroupE);
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
                if (isRadioGroupSelected(R.id.MetaTypeRadioGroupA, R.id.AttributeRadioGroupA, R.id.MagicRadioGroupA, R.id.SkillRadioGroupA, R.id.ResourceRadioGroupA)) {
                    a = true;
                }

                if (isRadioGroupSelected(R.id.MetaTypeRadioGroupB, R.id.AttributeRadioGroupB, R.id.MagicRadioGroupB, R.id.SkillRadioGroupB, R.id.ResourceRadioGroupB)) {
                    b = true;
                }

                if (isRadioGroupSelected(R.id.MetaTypeRadioGroupC, R.id.AttributeRadioGroupC, R.id.MagicRadioGroupC, R.id.SkillRadioGroupC, R.id.ResourceRadioGroupC)) {
                    c = true;
                }

                if (isRadioGroupSelected(R.id.MetaTypeRadioGroupD, R.id.AttributeRadioGroupD, R.id.MagicRadioGroupD, R.id.SkillRadioGroupD, R.id.ResourceRadioGroupD)) {
                    d = true;
                }


                if (isRadioGroupSelected(R.id.MetaTypeRadioGroupE, R.id.AttributeRadioGroupE, R.id.MagicRadioGroupE, R.id.SkillRadioGroupE, R.id.ResourceRadioGroupE)) {
                    e = true;
                }


                // All the 5 priorities have been selected
                if (a && b && c && d && e) {
                    int metaIndex;
                    int attrIndex;
                    int magicIndex;
                    int skillIndex;
                    int resIndex;

                    metaIndex = getIndex(metaTable, R.id.MetaTypeRadioGroupA, R.id.MetaTypeRadioGroupB,
                            R.id.MetaTypeRadioGroupC, R.id.MetaTypeRadioGroupD, R.id.MetaTypeRadioGroupE);

                    attrIndex = getIndex(attrTable, R.id.AttributeRadioGroupA, R.id.AttributeRadioGroupB,
                            R.id.AttributeRadioGroupC, R.id.AttributeRadioGroupD, R.id.AttributeRadioGroupE);

                    magicIndex = getIndex(magicTable, R.id.MagicRadioGroupA, R.id.MagicRadioGroupB,
                            R.id.MagicRadioGroupC, R.id.MagicRadioGroupD, R.id.MagicRadioGroupE);

                    skillIndex = getIndex(skillTable, R.id.SkillRadioGroupA, R.id.SkillRadioGroupB,
                            R.id.SkillRadioGroupC, R.id.SkillRadioGroupD, R.id.SkillRadioGroupE);

                    resIndex = getIndex(resTable, R.id.ResourceRadioGroupA, R.id.ResourceRadioGroupB,
                            R.id.ResourceRadioGroupC, R.id.ResourceRadioGroupD, R.id.ResourceRadioGroupE);

                    Intent i = new Intent(NewCharacterPriorityTable.this, SwipeFragmentHolder.class);
                    // add all the different data
                    Bundle mBundle = new Bundle();

                    mBundle.putSerializable("meta", metaTable.get(metaIndex));
                    mBundle.putSerializable("attr", attrTable.get(attrIndex));
                    mBundle.putSerializable("magic", magicTable.get(magicIndex));
                    mBundle.putSerializable("skill", skillTable.get(skillIndex));
                    mBundle.putSerializable("res", resTable.get(resIndex));
                    // TODO get the karma from an xml file.
                    mBundle.putInt("karma", ChummerConstants.startingKarma);

                    i.putExtras(mBundle);

                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select all 5 priorities",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

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

        final ArrayList<Integer> sameGroup = (ArrayList<Integer>) sameRadioGroupID.clone();
        final ArrayList<Integer> sameRankGroup = (ArrayList<Integer>) sameRankGroupID.clone();

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

        sameGroupID.add(R.id.MetaTypeRadioGroupB);
        sameGroupID.add(R.id.MetaTypeRadioGroupC);
        sameGroupID.add(R.id.MetaTypeRadioGroupD);
        sameGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.AttributeRadioGroupA);
        sameRankGroupID.add(R.id.MagicRadioGroupA);
        sameRankGroupID.add(R.id.SkillRadioGroupA);
        sameRankGroupID.add(R.id.ResourceRadioGroupA);
        addOnClickListeners(R.id.MetaTypeRadioGroupA, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.MetaTypeRadioGroupA);
        sameGroupID.add(R.id.MetaTypeRadioGroupC);
        sameGroupID.add(R.id.MetaTypeRadioGroupD);
        sameGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.AttributeRadioGroupB);
        sameRankGroupID.add(R.id.MagicRadioGroupB);
        sameRankGroupID.add(R.id.SkillRadioGroupB);
        sameRankGroupID.add(R.id.ResourceRadioGroupB);
        addOnClickListeners(R.id.MetaTypeRadioGroupB, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.MetaTypeRadioGroupA);
        sameGroupID.add(R.id.MetaTypeRadioGroupB);
        sameGroupID.add(R.id.MetaTypeRadioGroupD);
        sameGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.AttributeRadioGroupC);
        sameRankGroupID.add(R.id.MagicRadioGroupC);
        sameRankGroupID.add(R.id.SkillRadioGroupC);
        sameRankGroupID.add(R.id.ResourceRadioGroupC);
        addOnClickListeners(R.id.MetaTypeRadioGroupC, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.MetaTypeRadioGroupA);
        sameGroupID.add(R.id.MetaTypeRadioGroupB);
        sameGroupID.add(R.id.MetaTypeRadioGroupC);
        sameGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.AttributeRadioGroupD);
        sameRankGroupID.add(R.id.MagicRadioGroupD);
        sameRankGroupID.add(R.id.SkillRadioGroupD);
        sameRankGroupID.add(R.id.ResourceRadioGroupD);
        addOnClickListeners(R.id.MetaTypeRadioGroupD, sameRankGroupID, sameGroupID);

        sameGroupID.clear();
        sameGroupID.add(R.id.MetaTypeRadioGroupA);
        sameGroupID.add(R.id.MetaTypeRadioGroupB);
        sameGroupID.add(R.id.MetaTypeRadioGroupC);
        sameGroupID.add(R.id.MetaTypeRadioGroupD);
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.AttributeRadioGroupE);
        sameRankGroupID.add(R.id.MagicRadioGroupE);
        sameRankGroupID.add(R.id.SkillRadioGroupE);
        sameRankGroupID.add(R.id.ResourceRadioGroupE);
        addOnClickListeners(R.id.MetaTypeRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createResourceOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.AttributeRadioGroupA);
        sameRankGroupID.add(R.id.MagicRadioGroupA);
        sameRankGroupID.add(R.id.SkillRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.add(R.id.ResourceRadioGroupE);
        addOnClickListeners(R.id.ResourceRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.AttributeRadioGroupB);
        sameRankGroupID.add(R.id.MagicRadioGroupB);
        sameRankGroupID.add(R.id.SkillRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.add(R.id.ResourceRadioGroupE);
        addOnClickListeners(R.id.ResourceRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.AttributeRadioGroupC);
        sameRankGroupID.add(R.id.MagicRadioGroupC);
        sameRankGroupID.add(R.id.SkillRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.add(R.id.ResourceRadioGroupE);
        addOnClickListeners(R.id.ResourceRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.AttributeRadioGroupD);
        sameRankGroupID.add(R.id.MagicRadioGroupD);
        sameRankGroupID.add(R.id.SkillRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.add(R.id.ResourceRadioGroupE);
        addOnClickListeners(R.id.ResourceRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.AttributeRadioGroupE);
        sameRankGroupID.add(R.id.MagicRadioGroupE);
        sameRankGroupID.add(R.id.SkillRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.add(R.id.ResourceRadioGroupD);
        addOnClickListeners(R.id.ResourceRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createSkillOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.AttributeRadioGroupA);
        sameRankGroupID.add(R.id.MagicRadioGroupA);
        sameRankGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.SkillRadioGroupB);
        sameGroupID.add(R.id.SkillRadioGroupC);
        sameGroupID.add(R.id.SkillRadioGroupD);
        sameGroupID.add(R.id.SkillRadioGroupE);
        addOnClickListeners(R.id.SkillRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.AttributeRadioGroupB);
        sameRankGroupID.add(R.id.MagicRadioGroupB);
        sameRankGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.SkillRadioGroupA);
        sameGroupID.add(R.id.SkillRadioGroupC);
        sameGroupID.add(R.id.SkillRadioGroupD);
        sameGroupID.add(R.id.SkillRadioGroupE);
        addOnClickListeners(R.id.SkillRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.AttributeRadioGroupC);
        sameRankGroupID.add(R.id.MagicRadioGroupC);
        sameRankGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.SkillRadioGroupA);
        sameGroupID.add(R.id.SkillRadioGroupB);
        sameGroupID.add(R.id.SkillRadioGroupD);
        sameGroupID.add(R.id.SkillRadioGroupE);
        addOnClickListeners(R.id.SkillRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.AttributeRadioGroupD);
        sameRankGroupID.add(R.id.MagicRadioGroupD);
        sameRankGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.SkillRadioGroupA);
        sameGroupID.add(R.id.SkillRadioGroupB);
        sameGroupID.add(R.id.SkillRadioGroupC);
        sameGroupID.add(R.id.SkillRadioGroupE);
        addOnClickListeners(R.id.SkillRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.AttributeRadioGroupE);
        sameRankGroupID.add(R.id.MagicRadioGroupE);
        sameRankGroupID.add(R.id.ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.SkillRadioGroupA);
        sameGroupID.add(R.id.SkillRadioGroupB);
        sameGroupID.add(R.id.SkillRadioGroupC);
        sameGroupID.add(R.id.SkillRadioGroupD);
        addOnClickListeners(R.id.SkillRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createMagicOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.AttributeRadioGroupA);
        sameRankGroupID.add(R.id.SkillRadioGroupA);
        sameRankGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.MagicRadioGroupB);
        sameGroupID.add(R.id.MagicRadioGroupC);
        sameGroupID.add(R.id.MagicRadioGroupD);
        sameGroupID.add(R.id.MagicRadioGroupE);
        addOnClickListeners(R.id.MagicRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.AttributeRadioGroupB);
        sameRankGroupID.add(R.id.SkillRadioGroupB);
        sameRankGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.MagicRadioGroupA);
        sameGroupID.add(R.id.MagicRadioGroupC);
        sameGroupID.add(R.id.MagicRadioGroupD);
        sameGroupID.add(R.id.MagicRadioGroupE);
        addOnClickListeners(R.id.MagicRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.AttributeRadioGroupC);
        sameRankGroupID.add(R.id.SkillRadioGroupC);
        sameRankGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.MagicRadioGroupA);
        sameGroupID.add(R.id.MagicRadioGroupB);
        sameGroupID.add(R.id.MagicRadioGroupD);
        sameGroupID.add(R.id.MagicRadioGroupE);
        addOnClickListeners(R.id.MagicRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.AttributeRadioGroupD);
        sameRankGroupID.add(R.id.SkillRadioGroupD);
        sameRankGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.MagicRadioGroupA);
        sameGroupID.add(R.id.MagicRadioGroupB);
        sameGroupID.add(R.id.MagicRadioGroupC);
        sameGroupID.add(R.id.MagicRadioGroupE);
        addOnClickListeners(R.id.MagicRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.AttributeRadioGroupE);
        sameRankGroupID.add(R.id.SkillRadioGroupE);
        sameRankGroupID.add(R.id.ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.MagicRadioGroupA);
        sameGroupID.add(R.id.MagicRadioGroupB);
        sameGroupID.add(R.id.MagicRadioGroupC);
        sameGroupID.add(R.id.MagicRadioGroupD);
        addOnClickListeners(R.id.MagicRadioGroupE, sameRankGroupID, sameGroupID);
    }

    private void createAttrOnClickListeners() {
        ArrayList<Integer> sameRankGroupID = new ArrayList<>();
        ArrayList<Integer> sameGroupID = new ArrayList<>();
        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupA);
        sameRankGroupID.add(R.id.MagicRadioGroupA);
        sameRankGroupID.add(R.id.SkillRadioGroupA);
        sameRankGroupID.add(R.id.ResourceRadioGroupA);
        sameGroupID.clear();
        sameGroupID.add(R.id.AttributeRadioGroupB);
        sameGroupID.add(R.id.AttributeRadioGroupC);
        sameGroupID.add(R.id.AttributeRadioGroupD);
        sameGroupID.add(R.id.AttributeRadioGroupE);
        addOnClickListeners(R.id.AttributeRadioGroupA, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupB);
        sameRankGroupID.add(R.id.MagicRadioGroupB);
        sameRankGroupID.add(R.id.SkillRadioGroupB);
        sameRankGroupID.add(R.id.ResourceRadioGroupB);
        sameGroupID.clear();
        sameGroupID.add(R.id.AttributeRadioGroupA);
        sameGroupID.add(R.id.AttributeRadioGroupC);
        sameGroupID.add(R.id.AttributeRadioGroupD);
        sameGroupID.add(R.id.AttributeRadioGroupE);
        addOnClickListeners(R.id.AttributeRadioGroupB, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupC);
        sameRankGroupID.add(R.id.MagicRadioGroupC);
        sameRankGroupID.add(R.id.SkillRadioGroupC);
        sameRankGroupID.add(R.id.ResourceRadioGroupC);
        sameGroupID.clear();
        sameGroupID.add(R.id.AttributeRadioGroupA);
        sameGroupID.add(R.id.AttributeRadioGroupB);
        sameGroupID.add(R.id.AttributeRadioGroupD);
        sameGroupID.add(R.id.AttributeRadioGroupE);
        addOnClickListeners(R.id.AttributeRadioGroupC, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupD);
        sameRankGroupID.add(R.id.MagicRadioGroupD);
        sameRankGroupID.add(R.id.SkillRadioGroupD);
        sameRankGroupID.add(R.id.ResourceRadioGroupD);
        sameGroupID.clear();
        sameGroupID.add(R.id.AttributeRadioGroupA);
        sameGroupID.add(R.id.AttributeRadioGroupB);
        sameGroupID.add(R.id.AttributeRadioGroupC);
        sameGroupID.add(R.id.AttributeRadioGroupE);
        addOnClickListeners(R.id.AttributeRadioGroupD, sameRankGroupID, sameGroupID);

        sameRankGroupID.clear();
        sameRankGroupID.add(R.id.MetaTypeRadioGroupE);
        sameRankGroupID.add(R.id.MagicRadioGroupE);
        sameRankGroupID.add(R.id.SkillRadioGroupE);
        sameRankGroupID.add(R.id.ResourceRadioGroupE);
        sameGroupID.clear();
        sameGroupID.add(R.id.AttributeRadioGroupA);
        sameGroupID.add(R.id.AttributeRadioGroupB);
        sameGroupID.add(R.id.AttributeRadioGroupC);
        sameGroupID.add(R.id.AttributeRadioGroupD);
        addOnClickListeners(R.id.AttributeRadioGroupE, sameRankGroupID, sameGroupID);
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
                                case "magictype":
                                    currentAttribute.setMagicType(parser.nextText());
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
}