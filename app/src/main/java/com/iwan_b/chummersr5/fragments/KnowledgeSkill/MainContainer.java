package com.iwan_b.chummersr5.fragments.KnowledgeSkill;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
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

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.fragments.ActiveSkill.SkillTableRow;
import com.iwan_b.chummersr5.utility.ChummerMethods;

import java.util.ArrayList;


public class MainContainer  extends Fragment {
    private static View rootView;

    private void updateFreeKnowledgeCounter(final Integer knowledgeCounter) {
        TextView freeKnowledgeSkillTxt = (TextView) rootView.findViewById(R.id.freeKnowledgeCounter);
        freeKnowledgeSkillTxt.setText(String.valueOf(knowledgeCounter));
    }

    public static void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    private void updateCounters(final Integer knowledgeSkillCounter) {
        updateFreeKnowledgeCounter(knowledgeSkillCounter);
        updateKarma();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.knowledgeskillsfragment, container, false);

        int intu = ShadowrunCharacter.getCharacter().getAttributes().getIntu();
        int log = ShadowrunCharacter.getCharacter().getAttributes().getLog();

        int freeKnowledge = ChummerMethods.formulaFreeKnowledgeSkill(intu, log);

        updateCounters(freeKnowledge);


        TableLayout knowledgeSkillLayout = (TableLayout) rootView.findViewById(R.id.knowledgeSkillsTableLayout);

        TableRow newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);
        newTableRow = new TableRow(rootView.getContext());
        newTableRow.setGravity(Gravity.CENTER_VERTICAL);

        final Button addKnowledgeSkillButtonListener = new Button(rootView.getContext());
        addKnowledgeSkillButtonListener.setText("Add Knowledge Skill");
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        // Span the entire Row
//        params2.span = ChummerConstants.tableLayout.values().length;
        addKnowledgeSkillButtonListener.setLayoutParams(params2);
        addKnowledgeSkillButtonListener.setOnClickListener(new addKnowledgeSkillButtonListener());
        newTableRow.addView(addKnowledgeSkillButtonListener);
        knowledgeSkillLayout.addView(newTableRow);

        return rootView;
    }
    private class addKnowledgeSkillButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Knowledge skill");

            LinearLayout linearLayoutContainer = new LinearLayout(v.getContext());

            final EditText knowledgeName = new EditText(v.getContext());
            knowledgeName.setInputType(InputType.TYPE_CLASS_TEXT);
            knowledgeName.setHint("Enter your knowledge skill");
            TableRow.LayoutParams usernameParams = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            usernameParams.weight = 1;
            knowledgeName.setLayoutParams(usernameParams);
            linearLayoutContainer.addView(knowledgeName);

            ArrayList<String> knowledgeType = new ArrayList<>();
            // TODO change this from being hardcoded
            knowledgeType.add("Academic");
            knowledgeType.add("Interest");
            knowledgeType.add("Professional");
            knowledgeType.add("Street");

            final Spinner knowSpinner = new Spinner(v.getContext());
            //selected item will look like a spinner set from XML
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, knowledgeType);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            knowSpinner.setAdapter(spinnerArrayAdapter);
            TableRow.LayoutParams attrParams = new TableRow.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            attrParams.weight = 1;
            knowSpinner.setLayoutParams(attrParams);
            linearLayoutContainer.addView(knowSpinner);

            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Skill newSkill = new Skill();

                    newSkill.setSkillName(knowledgeName.getText().toString());

                    switch (knowSpinner.getSelectedItem().toString().toLowerCase()) {
                        case "academic":
                        case "professional":
                            newSkill.setAttrName("Logic");
                            break;
                        case "interest":
                        case "street":
                            newSkill.setAttrName("Intuition");
                            break;
                    }


                    newSkill.setIsDefaultable(false);
                    newSkill.setRating(0);

                    SkillTableRow genSkillTableRow = new SkillTableRow(rootView, null, R.id.freeKnowledgeCounter, false);

                    TableLayout knowledgeSkillLayout = (TableLayout) rootView.findViewById(R.id.knowledgeSkillsTableLayout);

                    knowledgeSkillLayout.addView(genSkillTableRow.createRow(newSkill), knowledgeSkillLayout.getChildCount() - 1);
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

            builder.setView(linearLayoutContainer);
            builder.show();
        }
    }
}