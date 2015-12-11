package com.iwan_b.chummersr5.fragments.Magic;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Spell;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainContainer extends Fragment implements UpdateInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();
    private ArrayList<Spell> allSpells = new ArrayList<>();
    private ArrayList<String> displayedSpells = new ArrayList<>();
    private ArrayList<Integer> pointHistory = new ArrayList<>();
    private ArrayAdapter<String> spellAdapter;

    public static Fragment newInstance() {
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

    public void updateFreeSpellCounter() {
        if (rootView != null) {
            TextView freeSpellCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_magic_main_free_spells_counter);
            freeSpellCounterTxtView.setText(String.valueOf(FreeCounters.getCounters().getFreeSpells()));
        }
    }

    public void updateCounters() {
        updateKarma();
        updateFreeSpellCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_magic_main, container, false);

        allSpells = readSpellsXML("magic/spells.xml");

        updateCounters();

        ListView listViewOfSpells = (ListView) rootView.findViewById(R.id.fragment_magic_main_spells_listview);

        displayedSpells = new ArrayList<>();

        spellAdapter = new ArrayAdapter<>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, displayedSpells);

        // Set the footerview to be the add qualities button
        listViewOfSpells.setFooterDividersEnabled(true);

        final TextView footerView = new TextView(rootView.getContext());
        footerView.setText("Buy Spell");

        footerView.setTextSize(24);
        footerView.setGravity(Gravity.CENTER_HORIZONTAL);

        listViewOfSpells.addFooterView(footerView);

        listViewOfSpells.setAdapter(spellAdapter);

        // When the user clicks on the add quality button display a list of available qualities
        footerView.setOnClickListener(new SpellListListener());

        // What to do when each quality is clicked.
        listViewOfSpells.setOnItemClickListener(new SpellInfoDialog(null, false));

        return rootView;
    }

    private void addSpell(Spell newSpell, int pointsUsed) {
        if (newSpell != null) {
            ArrayList<Spell> spells = ShadowrunCharacter.getCharacter().getSpells();

            if (spells == null) {
                spells = new ArrayList<>();
            }

            spells.add(newSpell);
            ShadowrunCharacter.getCharacter().setSpells(spells);

            pointHistory.add(pointsUsed);

            displayedSpells.add(newSpell.getName());
            spellAdapter.notifyDataSetChanged();
        }
    }

    private void removeSpell(Spell spellSelected) {
        if (spellSelected != null) {
            ArrayList<Spell> spells = ShadowrunCharacter.getCharacter().getSpells();

            if (spells == null) {
                spells = new ArrayList<>();
            }

            spells.remove(spellSelected);
            ShadowrunCharacter.getCharacter().setSpells(spells);

            for (int i = 0; i < displayedSpells.size(); i++) {
                if (displayedSpells.get(i) == spellSelected.getName()) {
                    int pHistory = pointHistory.get(i);

                    if (pHistory == ChummerConstants.freeSpellUsed) {
                        int counter = FreeCounters.getCounters().getFreeSpells();
                        FreeCounters.getCounters().setFreeSpells(++counter);
                    } else {
                        int unusedKarma = ShadowrunCharacter.getKarma();
                        ShadowrunCharacter.setKarma(unusedKarma + pHistory);
                    }

                    displayedSpells.remove(i);
                    pointHistory.remove(i);
                    break;
                }
            }

            spellAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param fileLocation of the file to parse
     * @return an Array of PriorityTable data
     */
    private ArrayList<Spell> readSpellsXML(final String fileLocation) {
        ArrayList<Spell> spells = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getActivity().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            spells = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return spells;
    }

    private ArrayList<Spell> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Spell> spells = new ArrayList<>();
        Spell tempSpell = null;

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
                    if (name.equalsIgnoreCase("spell")) {
                        tempSpell = new Spell();
                    } else if (tempSpell != null) {
                        String s = parser.nextText();
                        switch (name.toLowerCase()) {
                            case "name":
                                tempSpell.setName(s);
                                break;
                            case "book":
                                tempSpell.setBook(s);
                                break;
                            case "page":
                                tempSpell.setPage(s);
                                break;
                            case "summary":
                                tempSpell.setSummary(s);
                                break;
                            case "categories":
                                tempSpell.setCategory(s);
                                break;
                            case "type":
                                tempSpell.setType(s);
                                break;
                            case "range":
                                tempSpell.setRange(s);
                                break;
                            case "damage":
                                tempSpell.setDamage(s);
                                break;
                            case "duration":
                                tempSpell.setDuration(s);
                                break;
                            case "drain":
                                tempSpell.setDrain(Integer.valueOf(s));
                                break;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (tempSpell != null) {
                        spells.add(tempSpell);
                        tempSpell = null;
                    }
            }
            eventType = parser.next();
        }
        return spells;
    }

    private Spell getNewSpell(final String spellName) {
        for (final Spell s : allSpells) {
            if (s.getName().equalsIgnoreCase(spellName)) {
                return new Spell(s);
            }
        }
        return null;
    }

    private Spell getCurrentSpell(final String spellName) {
        ArrayList<Spell> spells;

        spells = ShadowrunCharacter.getCharacter().getSpells();

        for (final Spell s : spells) {
            if (s.getName().equalsIgnoreCase(spellName)) {
                return new Spell(s);
            }
        }
        return null;
    }

    private class SpellListListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialogVar = new Dialog(getActivity());

            final ListView listOfSpells = new ListView(getActivity().getBaseContext());

            final ArrayList<String> listData = new ArrayList<>();

            // Remove duplicates from the list of qualities they can take.
            for (final Spell s : allSpells) {
                // TODO make this more dynamic
                if (!displayedSpells.contains(s.getName())) {
                    listData.add(s.getName());
                }

            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(),
                    android.R.layout.simple_list_item_1, listData);

            listOfSpells.setAdapter(adapter);

            // When they click on the quality display another dialog...
            listOfSpells.setOnItemClickListener(new SpellInfoDialog(dialogVar, true));

            dialogVar.setCancelable(true);

            dialogVar.setContentView(listOfSpells);

            dialogVar.setTitle("Spells");
            dialogVar.show();
        }
    }

    /**
     * Display the info for the quality in a dialog
     */
    private class SpellInfoDialog implements AdapterView.OnItemClickListener {
        // Parent Dialog to dismiss
        private Dialog dialogVar;
        // Whether to add or delete the spell
        private boolean add;

        private Spell spellSelected;

        private EditText userInputEditText;

        public SpellInfoDialog(final Dialog dialogVar, final boolean add) {
            this.dialogVar = dialogVar;
            this.add = add;
        }


        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            final String item = (String) parent.getItemAtPosition(position);

            Log.i(ChummerConstants.TAG, "onItemClick was: " + item);

            spellSelected = null;
            if (add) {
                spellSelected = getNewSpell(item);
            } else {
                spellSelected = getCurrentSpell(item);
            }

            if (spellSelected == null) {
                Log.i(ChummerConstants.TAG, "The spell can't be found: " + item);
            }


            // See if we have anything to display
            if (spellSelected != null) {
                // Build the dialog to display the quality
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.fragment_magic_main_spell_display, parent, false);
                final TextView infoname = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_name_textView);
                final TextView infocategories = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_categories_textView);
                final TextView infotype = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_type_textView);
                final TextView inforange = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_range_textView);
                final TextView infodamage = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_damage_textView);
                final TextView infoduration = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_duration_textView);
                final TextView infodrain = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_drain_textView);

                final TextView infosummary = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_summary_textView);
                final TextView infobook = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_book_textView);
                final TextView infopage = (TextView) dialogView.findViewById(R.id.fragment_magic_main_spell_display_page_textView);

                infoname.setText(spellSelected.getName());
                infocategories.setText(spellSelected.getCategory());
                infotype.setText(spellSelected.getType());
                inforange.setText(spellSelected.getRange());
                infodamage.setText(spellSelected.getDamage());
                if (spellSelected.getDamage() == null) {
                    final TextView infodamageTitle = (TextView) dialogView.findViewById(R.id.textView3);
                    infodamageTitle.setText("");
                }
                infoduration.setText(spellSelected.getDuration());
                infodrain.setText(String.valueOf(spellSelected.getDrain()));

                infosummary.setText(spellSelected.getSummary());
                infobook.setText(spellSelected.getBook());
                infopage.setText(spellSelected.getPage());

                // Extra info goes here
                LinearLayout extraInfoLayout = (LinearLayout) dialogView.findViewById(R.id.fragment_magic_main_spell_display_extrainfo);

                // If the quality allows for user input
                if (item.split("\\[").length > 1) {
                    userInputEditText = new EditText(view.getContext());
                    if (!add) {
                        userInputEditText.setText(spellSelected.getUserTextInputString());
                        userInputEditText.setEnabled(false);
                    }
                    extraInfoLayout.addView(userInputEditText);
                }


                builder.setTitle("Spell: " + spellSelected.getName());
                builder.setView(dialogView);

                if (add) {
                    builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Get the most currentKarmaCounter
                            int currentKarmaCounter = ShadowrunCharacter.getKarma();
                            int freeSpellsCounter = FreeCounters.getCounters().getFreeSpells();

                            if (userInputEditText != null) {
                                spellSelected.setUserTextInputString(userInputEditText.getText().toString());
                            }

                            if (freeSpellsCounter > 0) {
                                freeSpellsCounter--;
                                FreeCounters.getCounters().setFreeSpells(freeSpellsCounter);
                                addSpell(spellSelected, ChummerConstants.freeSpellUsed);
                            } else if (currentKarmaCounter - 5 >= 0) {
                                currentKarmaCounter -= 5;
                                ShadowrunCharacter.setKarma(currentKarmaCounter);
                                addSpell(spellSelected, 5);
                            } else {
                                Toast.makeText(dialogView.getContext(), "You don't have enough to purchase this", Toast.LENGTH_SHORT).show();
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
                            removeSpell(spellSelected);

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

}