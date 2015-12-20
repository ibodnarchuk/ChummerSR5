package com.iwan_b.chummersr5.fragments.Technomancer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ComplexForm;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.FragmentUtil.FactoryMethodInterface;
import com.iwan_b.chummersr5.fragments.FragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainContainer extends Fragment implements UpdateInterface, FactoryMethodInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();

    private ArrayList<ComplexForm> allComplexForms = new ArrayList<>();
    private ArrayList<ComplexForm> displayedComplexForms = new ArrayList<>();
    private ArrayList<Integer> pointHistory = new ArrayList<>();
    private ComplexFormArrayAdapter complexFormArrayAdapter;

    @Override
    public Fragment newInstance() {
        MainContainer main = new MainContainer();
        return main;
    }

    @Override
    public void update() {
        updateCounters();
        for (UpdateInterface child : childrenToUpdate) {
            child.update();
        }
    }

    @Override
    public void updateParent() {
    }

    public void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    public void updateFreeComplexFormCounter() {
        if (rootView != null) {
            TextView freeComplexFormCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_technomancer_main_free_complex_forms_counter);
            freeComplexFormCounterTxtView.setText(String.valueOf(FreeCounters.getCounters().getFreeComplexForms()));
        }
    }

    public void updateCounters() {
        updateKarma();
        updateFreeComplexFormCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_technomancer_main, container, false);
        updateCounters();

        allComplexForms = readComplexFormXML("technomancer/complex_forms.xml");

        ListView listViewOfComplexForms = (ListView) rootView.findViewById(R.id.fragment_technomancer_main_listview);

        displayedComplexForms = new ArrayList<>();

        complexFormArrayAdapter = new ComplexFormArrayAdapter(rootView.getContext(),
               android.R.layout.simple_list_item_1, displayedComplexForms);

        // Set the footerview to be the add spell button
        listViewOfComplexForms.setFooterDividersEnabled(true);

        final TextView footerView = new TextView(rootView.getContext());
        footerView.setText("Buy Complex Form");

        footerView.setTextSize(24);
        footerView.setGravity(Gravity.CENTER_HORIZONTAL);

        listViewOfComplexForms.addFooterView(footerView);

        listViewOfComplexForms.setAdapter(complexFormArrayAdapter);

        // When the user clicks on the add spell button display a list of available spells
        footerView.setOnClickListener(new ComplexFormListener());

        // What to do when each spell is clicked.
        listViewOfComplexForms.setOnItemClickListener(new ComplexFormInfoDialog(null, false));

        return rootView;
    }

    private class ComplexFormListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialogVar = new Dialog(rootView.getContext());

            final ListView listOfComplexForms = new ListView(rootView.getContext());

            final ArrayList<ComplexForm> listData = new ArrayList<>();

            // Remove duplicates from the list of spells they can take.
            for (final ComplexForm c : allComplexForms) {
                // TODO make this more dynamic
                if (!displayedComplexForms.contains(c)) {
                    listData.add(c);
                }

            }

            final ArrayAdapter<ComplexForm> adapter = new ComplexFormArrayAdapter(rootView.getContext(),
                    android.R.layout.simple_list_item_1, listData);


            listOfComplexForms.setAdapter(adapter);

            // When they click on the spell display another dialog...
            listOfComplexForms.setOnItemClickListener(new ComplexFormInfoDialog(dialogVar, true));

            dialogVar.setCancelable(true);

            dialogVar.setContentView(listOfComplexForms);

            dialogVar.setTitle("Spells");
            dialogVar.show();
        }
    }


    private ComplexForm getNewComplexForm(final String spellName) {
        for (final ComplexForm c : allComplexForms) {
            if (c.getName().equalsIgnoreCase(spellName)) {
                return new ComplexForm(c);
            }
        }
        return null;
    }

    private ComplexForm getCurrentComplexForm(final String spellName) {
        ArrayList<ComplexForm> complexForms;

        complexForms = ShadowrunCharacter.getCharacter().getComplexForms();

        for (final ComplexForm c : complexForms) {
            if (c.getName().equalsIgnoreCase(spellName)) {
                return new ComplexForm(c);
            }
        }
        return null;
    }

    private void addComplexForm(ComplexForm newComplexForm, int pointsUsed) {
        if (newComplexForm != null) {
            ArrayList<ComplexForm> complexForms = ShadowrunCharacter.getCharacter().getComplexForms();

            if (complexForms == null) {
                complexForms = new ArrayList<>();
            }

            complexForms.add(newComplexForm);
            ShadowrunCharacter.getCharacter().setComplexForms(complexForms);

            pointHistory.add(pointsUsed);

            displayedComplexForms.add(newComplexForm);
            complexFormArrayAdapter.notifyDataSetChanged();
        }
    }

    private void removeComplexForm(ComplexForm complexFormSelected) {
        if (complexFormSelected != null) {
            ArrayList<ComplexForm> complexForms = ShadowrunCharacter.getCharacter().getComplexForms();

            if (complexForms == null) {
                complexForms = new ArrayList<>();
            }

            complexForms.remove(complexFormSelected);
            ShadowrunCharacter.getCharacter().setComplexForms(complexForms);

            for (int i = 0; i < displayedComplexForms.size(); i++) {
                // TODO implement this
                if (displayedComplexForms.get(i).getName() == complexFormSelected.getName()) {
                    int pHistory = pointHistory.get(i);

                    if (pHistory == ChummerConstants.freeSpellUsed) {
                        int counter = FreeCounters.getCounters().getFreeComplexForms();
                        FreeCounters.getCounters().setFreeComplexForms(++counter);
                    } else {
                        int unusedKarma = ShadowrunCharacter.getKarma();
                        ShadowrunCharacter.setKarma(unusedKarma + pHistory);
                    }

                    displayedComplexForms.remove(i);
                    pointHistory.remove(i);
                    break;
                }
            }

            complexFormArrayAdapter.notifyDataSetChanged();
        }
    }

    private class ComplexFormInfoDialog implements AdapterView.OnItemClickListener {
        // Parent Dialog to dismiss
        private Dialog dialogVar;
        // Whether to add or delete the spell
        private boolean add;

        private ComplexForm complexFormSelected;

        private EditText userInputEditText;

        public ComplexFormInfoDialog(final Dialog dialogVar, final boolean add) {
            this.dialogVar = dialogVar;
            this.add = add;
        }


        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            final ComplexForm item = (ComplexForm) parent.getItemAtPosition(position);

            Log.i(ChummerConstants.TAG, "onItemClick was: " + item);

            complexFormSelected = null;
            if (add) {
                complexFormSelected = getNewComplexForm(item.getName());
            } else {
                complexFormSelected = getCurrentComplexForm(item.getName());
            }

            if (complexFormSelected == null) {
                Log.i(ChummerConstants.TAG, "The complex form can't be found: " + item);
            }


            // See if we have anything to display
            if (complexFormSelected != null) {
                // Build the dialog to display the spell
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                LayoutInflater inflater = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView = inflater.inflate(R.layout.fragment_technomancer_complex_form_display, parent, false);
                final TextView name = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_name_textview);
                final TextView summary = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_summary_textView);
                final TextView book = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_book_textView);
                final TextView page = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_page_textView);
                final TextView target = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_target_textview);
                final TextView duration = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_duration_textview);
                final TextView fading = (TextView) dialogView.findViewById(R.id.fragment_technomancer_complex_form_display_fading_textview);

                name.setText(complexFormSelected.getName());
                summary.setText(complexFormSelected.getSummary());
                book.setText(complexFormSelected.getBook());
                page.setText(complexFormSelected.getPage());
                target.setText(complexFormSelected.getTarget());
                duration.setText(complexFormSelected.getDuration());
                fading.setText(String.valueOf(complexFormSelected.getFading()));

                builder.setTitle("Complex Form: " + complexFormSelected.getName());
                builder.setView(dialogView);

                if (add) {
                    builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (displayedComplexForms.size() < ShadowrunCharacter.getCharacter().getAttributes().getLog()) {
                                // Get the most currentKarmaCounter
                                int currentKarmaCounter = ShadowrunCharacter.getKarma();
                                int freeComplexForms = FreeCounters.getCounters().getFreeComplexForms();

                                if (freeComplexForms > 0) {
                                    freeComplexForms--;
                                    FreeCounters.getCounters().setFreeComplexForms(freeComplexForms);
                                    addComplexForm(complexFormSelected, ChummerConstants.freeSpellUsed);
                                } else if (currentKarmaCounter - 4 >= 0) {
                                    currentKarmaCounter -= 4;
                                    ShadowrunCharacter.setKarma(currentKarmaCounter);
                                    addComplexForm(complexFormSelected, 4);
                                } else {
                                    Toast.makeText(dialogView.getContext(), "You don't have enough to purchase this", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(dialogView.getContext(), "You are at the maximum number of complex forms at character creation", Toast.LENGTH_SHORT).show();
                            }
                            updateCounters();
                            // Only dismiss it if the user enters something valid
                            if (dialogVar != null) {
                                dialogVar.dismiss();
                            }
                        }
                    });
                } else {
                    builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Get the most currentKarmaCounter
                            removeComplexForm(complexFormSelected);

                            updateCounters();
                            // Only dismiss it if the user enters something valid
                            if (dialogVar != null) {
                                dialogVar.dismiss();
                            }
                        }
                    });
                }
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create();
                builder.show();
            }
        }
    }

    private ArrayList<ComplexForm> readComplexFormXML(final String fileLocation) {
        ArrayList<ComplexForm> complexForms = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = rootView.getContext().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            complexForms = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return complexForms;
    }

    private ArrayList<ComplexForm> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<ComplexForm> complexForms = new ArrayList<>();
        ComplexForm tempComplexForm = null;

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
                    if (name.equalsIgnoreCase("complexform")) {
                        tempComplexForm = new ComplexForm();
                    } else if (tempComplexForm != null) {
                        String s = parser.nextText();
                        switch (name.toLowerCase()) {
                            case "name":
                                tempComplexForm.setName(s);
                                break;
                            case "book":
                                tempComplexForm.setBook(s);
                                break;
                            case "page":
                                tempComplexForm.setPage(s);
                                break;
                            case "summary":
                                tempComplexForm.setSummary(s);
                                break;
                            case "target":
                                tempComplexForm.setTarget(s);
                                break;
                            case "duration":
                                tempComplexForm.setDuration(s);
                                break;
                            case "fading":
                                try {
                                    tempComplexForm.setFading(Integer.valueOf(s));
                                }catch(NumberFormatException e){
                                    tempComplexForm.setFading(0);
                                }
                                break;
                            case "list":
                                tempComplexForm.setList(s);
                                break;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (tempComplexForm != null) {
                        complexForms.add(tempComplexForm);
                        tempComplexForm = null;
                    }
            }
            eventType = parser.next();
        }
        return complexForms;
    }

}