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

    private boolean radioButtonLock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcharacterselection);

        // Get all the table data
        final ArrayList<PriorityTable> metaDataArray = readXML("chargen/metachargen.xml");
        final ArrayList<PriorityTable> attDataArray = readXML("chargen/attchargen.xml");
        final ArrayList<PriorityTable> magDataArray = readXML("chargen/magicchargen.xml");
        final ArrayList<PriorityTable> skillDataArray = readXML("chargen/skillchargen.xml");
        final ArrayList<PriorityTable> resourceDataArray = readXML("chargen/reschargen.xml");

        RadioButton temp;

        // All the columns and radio groups for the table
        final RadioGroup metaRadioGroupA = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupA);
        final RadioGroup metaRadioGroupB = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupB);
        final RadioGroup metaRadioGroupC = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupC);
        final RadioGroup metaRadioGroupD = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupD);
        final RadioGroup metaRadioGroupE = (RadioGroup) findViewById(R.id.MetaTypeRadioGroupE);

        final RadioGroup attRadioGroupA = (RadioGroup) findViewById(R.id.AttributeRadioGroupA);
        final RadioGroup attRadioGroupB = (RadioGroup) findViewById(R.id.AttributeRadioGroupB);
        final RadioGroup attRadioGroupC = (RadioGroup) findViewById(R.id.AttributeRadioGroupC);
        final RadioGroup attRadioGroupD = (RadioGroup) findViewById(R.id.AttributeRadioGroupD);
        final RadioGroup attRadioGroupE = (RadioGroup) findViewById(R.id.AttributeRadioGroupE);

        final RadioGroup magRadioGroupA = (RadioGroup) findViewById(R.id.MagicRadioGroupA);
        final RadioGroup magRadioGroupB = (RadioGroup) findViewById(R.id.MagicRadioGroupB);
        final RadioGroup magRadioGroupC = (RadioGroup) findViewById(R.id.MagicRadioGroupC);
        final RadioGroup magRadioGroupD = (RadioGroup) findViewById(R.id.MagicRadioGroupD);
        final RadioGroup magRadioGroupE = (RadioGroup) findViewById(R.id.MagicRadioGroupE);

        final RadioGroup skillRadioGroupA = (RadioGroup) findViewById(R.id.SkillRadioGroupA);
        final RadioGroup skillRadioGroupB = (RadioGroup) findViewById(R.id.SkillRadioGroupB);
        final RadioGroup skillRadioGroupC = (RadioGroup) findViewById(R.id.SkillRadioGroupC);
        final RadioGroup skillRadioGroupD = (RadioGroup) findViewById(R.id.SkillRadioGroupD);
        final RadioGroup skillRadioGroupE = (RadioGroup) findViewById(R.id.SkillRadioGroupE);

        final RadioGroup resourceRadioGroupA = (RadioGroup) findViewById(R.id.ResourceRadioGroupA);
        final RadioGroup resourceRadioGroupB = (RadioGroup) findViewById(R.id.ResourceRadioGroupB);
        final RadioGroup resourceRadioGroupC = (RadioGroup) findViewById(R.id.ResourceRadioGroupC);
        final RadioGroup resourceRadioGroupD = (RadioGroup) findViewById(R.id.ResourceRadioGroupD);
        final RadioGroup resourceRadioGroupE = (RadioGroup) findViewById(R.id.ResourceRadioGroupE);

        // The priorities selection
        final String priorityA = getResources().getString(R.string.priorityA);
        final String priorityB = getResources().getString(R.string.priorityB);
        final String priorityC = getResources().getString(R.string.priorityC);
        final String priorityD = getResources().getString(R.string.priorityD);
        final String priorityE = getResources().getString(R.string.priorityE);

        // Done button
        final Button doneButton = (Button) findViewById(R.id.NewCharacterButtonDone);

        // **********************************************************************************
        // Metatype Section
        if (metaDataArray != null) {
            for (PriorityTable p : metaDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    metaRadioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    metaRadioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    metaRadioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    metaRadioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    metaRadioGroupE.addView(temp);
                }

            }
        }

        metaRadioGroupA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    attRadioGroupA.check(-1);
                    magRadioGroupA.check(-1);
                    skillRadioGroupA.check(-1);
                    resourceRadioGroupA.check(-1);

                    metaRadioGroupB.check(-1);
                    metaRadioGroupC.check(-1);
                    metaRadioGroupD.check(-1);
                    metaRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        metaRadioGroupB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    metaRadioGroupA.check(-1);

                    attRadioGroupB.check(-1);
                    magRadioGroupB.check(-1);
                    skillRadioGroupB.check(-1);
                    resourceRadioGroupB.check(-1);

                    metaRadioGroupC.check(-1);
                    metaRadioGroupD.check(-1);
                    metaRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        metaRadioGroupC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    metaRadioGroupA.check(-1);
                    metaRadioGroupB.check(-1);

                    attRadioGroupC.check(-1);
                    magRadioGroupC.check(-1);
                    skillRadioGroupC.check(-1);
                    resourceRadioGroupC.check(-1);

                    metaRadioGroupD.check(-1);
                    metaRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        metaRadioGroupD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    metaRadioGroupA.check(-1);
                    metaRadioGroupB.check(-1);
                    metaRadioGroupC.check(-1);

                    attRadioGroupD.check(-1);
                    magRadioGroupD.check(-1);
                    skillRadioGroupD.check(-1);
                    resourceRadioGroupD.check(-1);

                    metaRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        metaRadioGroupE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    metaRadioGroupA.check(-1);
                    metaRadioGroupB.check(-1);
                    metaRadioGroupC.check(-1);
                    metaRadioGroupD.check(-1);

                    attRadioGroupE.check(-1);
                    magRadioGroupE.check(-1);
                    skillRadioGroupE.check(-1);
                    resourceRadioGroupE.check(-1);

                    radioButtonLock = true;
                }
            }
        });

        // **********************************************************************************
        // Attribute section

        if (attDataArray != null) {
            for (PriorityTable p : attDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    attRadioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    attRadioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    attRadioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    attRadioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    attRadioGroupE.addView(temp);
                }

            }
        }

        attRadioGroupA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    metaRadioGroupA.check(-1);
                    magRadioGroupA.check(-1);
                    skillRadioGroupA.check(-1);
                    resourceRadioGroupA.check(-1);

                    attRadioGroupB.check(-1);
                    attRadioGroupC.check(-1);
                    attRadioGroupD.check(-1);
                    attRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        attRadioGroupB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    attRadioGroupA.check(-1);

                    metaRadioGroupB.check(-1);
                    magRadioGroupB.check(-1);
                    skillRadioGroupB.check(-1);
                    resourceRadioGroupB.check(-1);

                    attRadioGroupC.check(-1);
                    attRadioGroupD.check(-1);
                    attRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        attRadioGroupC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    attRadioGroupA.check(-1);
                    attRadioGroupB.check(-1);

                    metaRadioGroupC.check(-1);
                    magRadioGroupC.check(-1);
                    skillRadioGroupC.check(-1);
                    resourceRadioGroupC.check(-1);

                    attRadioGroupD.check(-1);
                    attRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        attRadioGroupD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    attRadioGroupA.check(-1);
                    attRadioGroupB.check(-1);
                    attRadioGroupC.check(-1);

                    metaRadioGroupD.check(-1);
                    magRadioGroupD.check(-1);
                    skillRadioGroupD.check(-1);
                    resourceRadioGroupD.check(-1);

                    attRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        attRadioGroupE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    attRadioGroupA.check(-1);
                    attRadioGroupB.check(-1);
                    attRadioGroupC.check(-1);
                    attRadioGroupD.check(-1);

                    metaRadioGroupE.check(-1);
                    magRadioGroupE.check(-1);
                    skillRadioGroupE.check(-1);
                    resourceRadioGroupE.check(-1);

                    radioButtonLock = true;
                }
            }
        });

        // **********************************************************************************
        // Magic Selection

        if (magDataArray != null) {
            for (PriorityTable p : magDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    magRadioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    magRadioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    magRadioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    magRadioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    magRadioGroupE.addView(temp);
                }

            }
        }

        magRadioGroupA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    metaRadioGroupA.check(-1);
                    attRadioGroupA.check(-1);
                    skillRadioGroupA.check(-1);
                    resourceRadioGroupA.check(-1);

                    magRadioGroupB.check(-1);
                    magRadioGroupC.check(-1);
                    magRadioGroupD.check(-1);
                    magRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        magRadioGroupB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    magRadioGroupA.check(-1);

                    metaRadioGroupB.check(-1);
                    attRadioGroupB.check(-1);
                    skillRadioGroupB.check(-1);
                    resourceRadioGroupB.check(-1);

                    magRadioGroupC.check(-1);
                    magRadioGroupD.check(-1);
                    magRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        magRadioGroupC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    magRadioGroupA.check(-1);
                    magRadioGroupB.check(-1);

                    metaRadioGroupC.check(-1);
                    attRadioGroupC.check(-1);
                    skillRadioGroupC.check(-1);
                    resourceRadioGroupC.check(-1);

                    magRadioGroupD.check(-1);
                    magRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        magRadioGroupD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    magRadioGroupA.check(-1);
                    magRadioGroupB.check(-1);
                    magRadioGroupC.check(-1);

                    metaRadioGroupD.check(-1);
                    attRadioGroupD.check(-1);
                    skillRadioGroupD.check(-1);
                    resourceRadioGroupD.check(-1);

                    magRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        magRadioGroupE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    magRadioGroupA.check(-1);
                    magRadioGroupB.check(-1);
                    magRadioGroupC.check(-1);
                    magRadioGroupD.check(-1);

                    metaRadioGroupE.check(-1);
                    attRadioGroupE.check(-1);
                    skillRadioGroupE.check(-1);
                    resourceRadioGroupE.check(-1);

                    radioButtonLock = true;
                }
            }
        });
        // **********************************************************************************
        // skill Selection

        if (skillDataArray != null) {
            for (PriorityTable p : skillDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    skillRadioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    skillRadioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    skillRadioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    skillRadioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    skillRadioGroupE.addView(temp);
                }

            }
        }

        skillRadioGroupA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    metaRadioGroupA.check(-1);
                    attRadioGroupA.check(-1);
                    magRadioGroupA.check(-1);
                    resourceRadioGroupA.check(-1);

                    skillRadioGroupB.check(-1);
                    skillRadioGroupC.check(-1);
                    skillRadioGroupD.check(-1);
                    skillRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        skillRadioGroupB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    skillRadioGroupA.check(-1);

                    metaRadioGroupB.check(-1);
                    attRadioGroupB.check(-1);
                    magRadioGroupB.check(-1);
                    resourceRadioGroupB.check(-1);

                    skillRadioGroupC.check(-1);
                    skillRadioGroupD.check(-1);
                    skillRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        skillRadioGroupC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    skillRadioGroupA.check(-1);
                    skillRadioGroupB.check(-1);

                    metaRadioGroupC.check(-1);
                    attRadioGroupC.check(-1);
                    magRadioGroupC.check(-1);
                    resourceRadioGroupC.check(-1);

                    skillRadioGroupD.check(-1);
                    skillRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        skillRadioGroupD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    skillRadioGroupA.check(-1);
                    skillRadioGroupB.check(-1);
                    skillRadioGroupC.check(-1);

                    metaRadioGroupD.check(-1);
                    attRadioGroupD.check(-1);
                    magRadioGroupD.check(-1);
                    resourceRadioGroupD.check(-1);

                    skillRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        skillRadioGroupE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    skillRadioGroupA.check(-1);
                    skillRadioGroupB.check(-1);
                    skillRadioGroupC.check(-1);
                    skillRadioGroupD.check(-1);

                    metaRadioGroupE.check(-1);
                    attRadioGroupE.check(-1);
                    magRadioGroupE.check(-1);
                    resourceRadioGroupE.check(-1);

                    radioButtonLock = true;
                }
            }
        });
        // **********************************************************************************

        if (resourceDataArray != null) {
            for (PriorityTable p : resourceDataArray) {
                temp = new RadioButton(this);
                temp.setText(p.getDisplayText());

                if (p.getPriority().equalsIgnoreCase(priorityA)) {
                    resourceRadioGroupA.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityB)) {
                    resourceRadioGroupB.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityC)) {
                    resourceRadioGroupC.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityD)) {
                    resourceRadioGroupD.addView(temp);
                } else if (p.getPriority().equalsIgnoreCase(priorityE)) {
                    resourceRadioGroupE.addView(temp);
                }

            }
        }

        resourceRadioGroupA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;

                    metaRadioGroupA.check(-1);
                    attRadioGroupA.check(-1);
                    magRadioGroupA.check(-1);
                    skillRadioGroupA.check(-1);

                    resourceRadioGroupB.check(-1);
                    resourceRadioGroupC.check(-1);
                    resourceRadioGroupD.check(-1);
                    resourceRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        resourceRadioGroupB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    resourceRadioGroupA.check(-1);

                    metaRadioGroupB.check(-1);
                    attRadioGroupB.check(-1);
                    magRadioGroupB.check(-1);
                    skillRadioGroupB.check(-1);

                    resourceRadioGroupC.check(-1);
                    resourceRadioGroupD.check(-1);
                    resourceRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        resourceRadioGroupC.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    resourceRadioGroupA.check(-1);
                    resourceRadioGroupB.check(-1);

                    metaRadioGroupC.check(-1);
                    attRadioGroupC.check(-1);
                    magRadioGroupC.check(-1);
                    skillRadioGroupC.check(-1);

                    resourceRadioGroupD.check(-1);
                    resourceRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        resourceRadioGroupD.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    resourceRadioGroupA.check(-1);
                    resourceRadioGroupB.check(-1);
                    resourceRadioGroupC.check(-1);

                    metaRadioGroupD.check(-1);
                    attRadioGroupD.check(-1);
                    magRadioGroupD.check(-1);
                    skillRadioGroupD.check(-1);

                    resourceRadioGroupE.check(-1);
                    radioButtonLock = true;
                }
            }
        });

        resourceRadioGroupE.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonLock) {
                    // isFirst acts as a lock making sure no-one else
                    // thinks
                    // they are the first button being changed
                    radioButtonLock = false;
                    resourceRadioGroupA.check(-1);
                    resourceRadioGroupB.check(-1);
                    resourceRadioGroupC.check(-1);
                    resourceRadioGroupD.check(-1);

                    metaRadioGroupE.check(-1);
                    attRadioGroupE.check(-1);
                    magRadioGroupE.check(-1);
                    skillRadioGroupE.check(-1);

                    radioButtonLock = true;
                }
            }
        });
        // **********************************************************************************

        // TODO remove this from the default selection
        ((RadioButton) metaRadioGroupA.getChildAt(0)).setChecked(true);
        ((RadioButton) attRadioGroupB.getChildAt(0)).setChecked(true);
        ((RadioButton) magRadioGroupC.getChildAt(0)).setChecked(true);
        ((RadioButton) skillRadioGroupD.getChildAt(0)).setChecked(true);
        ((RadioButton) resourceRadioGroupE.getChildAt(0)).setChecked(true);

        doneButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean a = false;
                boolean b = false;
                boolean c = false;
                boolean d = false;
                boolean e = false;

                if (metaRadioGroupA.getCheckedRadioButtonId() != -1 || attRadioGroupA.getCheckedRadioButtonId() != -1
                        || skillRadioGroupA.getCheckedRadioButtonId() != -1
                        || magRadioGroupA.getCheckedRadioButtonId() != -1
                        || resourceRadioGroupA.getCheckedRadioButtonId() != -1) {
                    a = true;
                }

                if (metaRadioGroupB.getCheckedRadioButtonId() != -1 || attRadioGroupB.getCheckedRadioButtonId() != -1
                        || skillRadioGroupB.getCheckedRadioButtonId() != -1
                        || magRadioGroupB.getCheckedRadioButtonId() != -1
                        || resourceRadioGroupB.getCheckedRadioButtonId() != -1) {
                    b = true;
                }

                if (metaRadioGroupC.getCheckedRadioButtonId() != -1 || attRadioGroupC.getCheckedRadioButtonId() != -1
                        || skillRadioGroupC.getCheckedRadioButtonId() != -1
                        || magRadioGroupC.getCheckedRadioButtonId() != -1
                        || resourceRadioGroupC.getCheckedRadioButtonId() != -1) {
                    c = true;
                }

                if (metaRadioGroupD.getCheckedRadioButtonId() != -1 || attRadioGroupD.getCheckedRadioButtonId() != -1
                        || skillRadioGroupD.getCheckedRadioButtonId() != -1
                        || magRadioGroupD.getCheckedRadioButtonId() != -1
                        || resourceRadioGroupD.getCheckedRadioButtonId() != -1) {
                    d = true;
                }

                if (metaRadioGroupE.getCheckedRadioButtonId() != -1 || attRadioGroupE.getCheckedRadioButtonId() != -1
                        || skillRadioGroupE.getCheckedRadioButtonId() != -1
                        || magRadioGroupE.getCheckedRadioButtonId() != -1
                        || resourceRadioGroupE.getCheckedRadioButtonId() != -1) {
                    e = true;
                }

                // All the 5 priorities have been selected
                if (a && b && c && d && e) {
					int metaIndex;
					int attrIndex;
					int magicIndex;
					int skillIndex;
					int resIndex;

					RadioButton rb = null;
					// get the selected metatype
					if (metaRadioGroupA.getCheckedRadioButtonId() != -1) {
						int selectedId = metaRadioGroupA.getCheckedRadioButtonId();
						rb = (RadioButton) metaRadioGroupA.findViewById(selectedId);
					} else if (metaRadioGroupB.getCheckedRadioButtonId() != -1) {
						int selectedId = metaRadioGroupB.getCheckedRadioButtonId();
						rb = (RadioButton) metaRadioGroupB.findViewById(selectedId);
					} else if (metaRadioGroupC.getCheckedRadioButtonId() != -1) {
						int selectedId = metaRadioGroupC.getCheckedRadioButtonId();
						rb = (RadioButton) metaRadioGroupC.findViewById(selectedId);
					} else if (metaRadioGroupD.getCheckedRadioButtonId() != -1) {
						int selectedId = metaRadioGroupD.getCheckedRadioButtonId();
						rb = (RadioButton) metaRadioGroupD.findViewById(selectedId);
					} else if (metaRadioGroupE.getCheckedRadioButtonId() != -1) {
						int selectedId = metaRadioGroupE.getCheckedRadioButtonId();
						rb = (RadioButton) metaRadioGroupE.findViewById(selectedId);
					}

					metaIndex = findPriorityIndex(rb, metaDataArray);
					// get the selected attribute
					rb = null;
					if (attRadioGroupA.getCheckedRadioButtonId() != -1) {
						int selectedId = attRadioGroupA.getCheckedRadioButtonId();
						rb = (RadioButton) attRadioGroupA.findViewById(selectedId);
					} else if (attRadioGroupB.getCheckedRadioButtonId() != -1) {
						int selectedId = attRadioGroupB.getCheckedRadioButtonId();
						rb = (RadioButton) attRadioGroupB.findViewById(selectedId);
					} else if (attRadioGroupC.getCheckedRadioButtonId() != -1) {
						int selectedId = attRadioGroupC.getCheckedRadioButtonId();
						rb = (RadioButton) attRadioGroupC.findViewById(selectedId);
					} else if (attRadioGroupD.getCheckedRadioButtonId() != -1) {
						int selectedId = attRadioGroupD.getCheckedRadioButtonId();
						rb = (RadioButton) attRadioGroupD.findViewById(selectedId);
					} else if (attRadioGroupE.getCheckedRadioButtonId() != -1) {
						int selectedId = attRadioGroupE.getCheckedRadioButtonId();
						rb = (RadioButton) attRadioGroupE.findViewById(selectedId);
					}

					attrIndex = findPriorityIndex(rb, attDataArray);
					// get the selected magic
					rb = null;
					if (magRadioGroupA.getCheckedRadioButtonId() != -1) {
						int selectedId = magRadioGroupA.getCheckedRadioButtonId();
						rb = (RadioButton) magRadioGroupA.findViewById(selectedId);
					} else if (magRadioGroupB.getCheckedRadioButtonId() != -1) {
						int selectedId = magRadioGroupB.getCheckedRadioButtonId();
						rb = (RadioButton) magRadioGroupB.findViewById(selectedId);
					} else if (magRadioGroupC.getCheckedRadioButtonId() != -1) {
						int selectedId = magRadioGroupC.getCheckedRadioButtonId();
						rb = (RadioButton) magRadioGroupC.findViewById(selectedId);
					} else if (magRadioGroupD.getCheckedRadioButtonId() != -1) {
						int selectedId = magRadioGroupD.getCheckedRadioButtonId();
						rb = (RadioButton) magRadioGroupD.findViewById(selectedId);
					} else if (magRadioGroupE.getCheckedRadioButtonId() != -1) {
						int selectedId = magRadioGroupE.getCheckedRadioButtonId();
						rb = (RadioButton) magRadioGroupE.findViewById(selectedId);
					}

					magicIndex = findPriorityIndex(rb, magDataArray);
					// get the selected skill
					rb = null;
					if (skillRadioGroupA.getCheckedRadioButtonId() != -1) {
						int selectedId = skillRadioGroupA.getCheckedRadioButtonId();
						rb = (RadioButton) skillRadioGroupA.findViewById(selectedId);
					} else if (skillRadioGroupB.getCheckedRadioButtonId() != -1) {
						int selectedId = skillRadioGroupB.getCheckedRadioButtonId();
						rb = (RadioButton) skillRadioGroupB.findViewById(selectedId);
					} else if (skillRadioGroupC.getCheckedRadioButtonId() != -1) {
						int selectedId = skillRadioGroupC.getCheckedRadioButtonId();
						rb = (RadioButton) skillRadioGroupC.findViewById(selectedId);
					} else if (skillRadioGroupD.getCheckedRadioButtonId() != -1) {
						int selectedId = skillRadioGroupD.getCheckedRadioButtonId();
						rb = (RadioButton) skillRadioGroupD.findViewById(selectedId);
					} else if (skillRadioGroupE.getCheckedRadioButtonId() != -1) {
						int selectedId = skillRadioGroupE.getCheckedRadioButtonId();
						rb = (RadioButton) skillRadioGroupE.findViewById(selectedId);
					}

					skillIndex = findPriorityIndex(rb, skillDataArray);

					// Get the selected resource
					rb = null;
					if (resourceRadioGroupA.getCheckedRadioButtonId() != -1) {
						int selectedId = resourceRadioGroupA.getCheckedRadioButtonId();
						rb = (RadioButton) resourceRadioGroupA.findViewById(selectedId);
					} else if (resourceRadioGroupB.getCheckedRadioButtonId() != -1) {
						int selectedId = resourceRadioGroupB.getCheckedRadioButtonId();
						rb = (RadioButton) resourceRadioGroupB.findViewById(selectedId);
					} else if (resourceRadioGroupC.getCheckedRadioButtonId() != -1) {
						int selectedId = resourceRadioGroupC.getCheckedRadioButtonId();
						rb = (RadioButton) resourceRadioGroupC.findViewById(selectedId);
					} else if (resourceRadioGroupD.getCheckedRadioButtonId() != -1) {
						int selectedId = resourceRadioGroupD.getCheckedRadioButtonId();
						rb = (RadioButton) resourceRadioGroupD.findViewById(selectedId);
					} else if (resourceRadioGroupE.getCheckedRadioButtonId() != -1) {
						int selectedId = resourceRadioGroupE.getCheckedRadioButtonId();
						rb = (RadioButton) resourceRadioGroupE.findViewById(selectedId);
					}

					resIndex = findPriorityIndex(rb, resourceDataArray);

                    Intent i = new Intent(NewCharacterPriorityTable.this, SwipeFragmentHolder.class);
					// add all the different data
					Bundle mBundle = new Bundle();

					mBundle.putSerializable("meta", metaDataArray.get(metaIndex));
					mBundle.putSerializable("attr", attDataArray.get(attrIndex));
					mBundle.putSerializable("magic", magDataArray.get(magicIndex));
					mBundle.putSerializable("skill", skillDataArray.get(skillIndex));
					mBundle.putSerializable("res", resourceDataArray.get(resIndex));
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

    private int findPriorityIndex(final Button rb, final ArrayList<PriorityTable> priorityList) {
        for (int x = 0; x < priorityList.size(); x++) {
            if (rb.getText().toString().compareToIgnoreCase(priorityList.get(x).getDisplayText()) == 0) {
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
                            switch(name.toLowerCase()) {
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
                            switch(name.toLowerCase()){
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