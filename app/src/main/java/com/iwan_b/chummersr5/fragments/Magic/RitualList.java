package com.iwan_b.chummersr5.fragments.Magic;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Ritual;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RitualList implements UpdateInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();

    private ArrayList<Ritual> allRituals = new ArrayList<>();
    private ArrayList<Ritual> displayedRituals = new ArrayList<>();
    private ArrayList<Integer> pointHistoryForRituals = new ArrayList<>();
    private RitualArrayAdapter ritualAdapter;

    public RitualList(View rootView) {
        RitualList.rootView = rootView;

        allRituals = readRitualsXML("magic/rituals.xml");

        updateCounters();

        ListView listViewOfRituals = (ListView) rootView.findViewById(R.id.fragment_magic_main_rituals_listview);

        displayedRituals = new ArrayList<>();

        ritualAdapter = new RitualArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, displayedRituals);

        // Set the footerview to be the add rituals button
        listViewOfRituals.setFooterDividersEnabled(true);

        final TextView footerView = new TextView(rootView.getContext());
        footerView.setText("Buy Rituals");

        footerView.setTextSize(24);
        footerView.setGravity(Gravity.CENTER_HORIZONTAL);

        listViewOfRituals.addFooterView(footerView);

        listViewOfRituals.setAdapter(ritualAdapter);

        // When the user clicks on the add ritual button display a list of available rituals
        footerView.setOnClickListener(new RitualListListener());

        // What to do when each ritual is clicked.
        listViewOfRituals.setOnItemClickListener(new RitualInfoDialog(null, false));
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
        TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
        karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
    }

    public void updateFreeSpellCounter() {
        TextView freeSpellCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_magic_main_free_spells_counter);
        freeSpellCounterTxtView.setText(String.valueOf(FreeCounters.getCounters().getFreeSpells()));
    }

    public void updateCounters() {
        updateKarma();
        updateFreeSpellCounter();
    }

    private void addRitual(Ritual newRitual, int pointsUsed) {
        if (newRitual != null) {
            ArrayList<Ritual> rituals = ShadowrunCharacter.getCharacter().getRituals();

            if (rituals == null) {
                rituals = new ArrayList<>();
            }

            rituals.add(newRitual);

            ShadowrunCharacter.getCharacter().setRituals(rituals);

            pointHistoryForRituals.add(pointsUsed);

            displayedRituals.add(newRitual);
            ritualAdapter.notifyDataSetChanged();
        }
    }

    private void removeRitual(Ritual ritualSelected) {
        if (ritualSelected != null) {
            ArrayList<Ritual> rituals = ShadowrunCharacter.getCharacter().getRituals();

            if (rituals == null) {
                rituals = new ArrayList<>();
            }

            rituals.remove(ritualSelected);
            ShadowrunCharacter.getCharacter().setRituals(rituals);

            for (int i = 0; i < displayedRituals.size(); i++) {
                // TODO implement this
                if (displayedRituals.get(i).getName() == ritualSelected.getName()) {
                    int pHistory = pointHistoryForRituals.get(i);

                    if (pHistory == ChummerConstants.freeSpellUsed) {
                        int counter = FreeCounters.getCounters().getFreeSpells();
                        FreeCounters.getCounters().setFreeSpells(++counter);
                    } else {
                        int unusedKarma = ShadowrunCharacter.getKarma();
                        ShadowrunCharacter.setKarma(unusedKarma + pHistory);
                    }

                    displayedRituals.remove(i);
                    pointHistoryForRituals.remove(i);
                    break;
                }
            }

            ritualAdapter.notifyDataSetChanged();
        }
    }

    private Ritual getNewRitual(final String ritualName) {
        for (final Ritual r : allRituals) {
            if (r.getName().equalsIgnoreCase(ritualName)) {
                return new Ritual(r);
            }
        }
        return null;
    }

    private Ritual getCurrentRitual(final String ritualName) {
        ArrayList<Ritual> rituals;

        rituals = ShadowrunCharacter.getCharacter().getRituals();

        for (final Ritual r : rituals) {
            if (r.getName().equalsIgnoreCase(ritualName)) {
                return new Ritual(r);
            }
        }
        return null;
    }

    private ArrayList<Ritual> readRitualsXML(final String fileLocation) {
        ArrayList<Ritual> rituals = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = rootView.getContext().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            rituals = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return rituals;
    }

    private ArrayList<Ritual> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Ritual> rituals = new ArrayList<>();
        Ritual tempRitual = null;

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
                    if (name.equalsIgnoreCase("ritual")) {
                        tempRitual = new Ritual();
                    } else if (tempRitual != null) {
                        String s = parser.nextText();
                        switch (name.toLowerCase()) {
                            case "name":
                                tempRitual.setName(s);
                                break;
                            case "book":
                                tempRitual.setBook(s);
                                break;
                            case "page":
                                tempRitual.setPage(s);
                                break;
                            case "summary":
                                tempRitual.setSummary(s);
                                break;
                            case "categories":
                                tempRitual.setCategory(s);
                                break;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (tempRitual != null) {
                        rituals.add(tempRitual);
                        tempRitual = null;
                    }
            }
            eventType = parser.next();
        }
        return rituals;
    }

    private class RitualListListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialogVar = new Dialog(rootView.getContext());

            final ListView listOfRituals = new ListView(rootView.getContext());

            final ArrayList<Ritual> listData = new ArrayList<>();

            // Remove duplicates from the list of rituals they can take.
            for (final Ritual r : allRituals) {
                // TODO make this more dynamic
                if (!displayedRituals.contains(r)) {
                    listData.add(r);
                }

            }

            final ArrayAdapter<Ritual> adapter = new RitualArrayAdapter(rootView.getContext(),
                    android.R.layout.simple_list_item_1, listData);


            listOfRituals.setAdapter(adapter);

            // When they click on the ritual display another dialog...
            listOfRituals.setOnItemClickListener(new RitualInfoDialog(dialogVar, true));

            dialogVar.setCancelable(true);

            dialogVar.setContentView(listOfRituals);

            dialogVar.setTitle("Rituals");
            dialogVar.show();
        }
    }

    /**
     * Display the info for the ritual in a dialog
     */
    private class RitualInfoDialog implements AdapterView.OnItemClickListener {
        // Parent Dialog to dismiss
        private Dialog dialogVar;
        // Whether to add or delete the spell
        private boolean add;

        private Ritual ritualSelected;

        public RitualInfoDialog(final Dialog dialogVar, final boolean add) {
            this.dialogVar = dialogVar;
            this.add = add;
        }


        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            final Ritual item = (Ritual) parent.getItemAtPosition(position);

            Log.i(ChummerConstants.TAG, "onItemClick was: " + item);

            ritualSelected = null;
            if (add) {
                ritualSelected = getNewRitual(item.getName());
            } else {
                ritualSelected = getCurrentRitual(item.getName());
            }

            if (ritualSelected == null) {
                Log.i(ChummerConstants.TAG, "The ritual can't be found: " + item);
            }


            // See if we have anything to display
            if (ritualSelected != null) {
                // Build the dialog to display the ritual
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                LayoutInflater inflater = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView = inflater.inflate(R.layout.fragment_magic_main_ritual_display, parent, false);
                final TextView infoname = (TextView) dialogView.findViewById(R.id.fragment_magic_main_ritual_display_name_textView);
                final TextView infocategories = (TextView) dialogView.findViewById(R.id.fragment_magic_main_ritual_display_categories_textView);

                final TextView infosummary = (TextView) dialogView.findViewById(R.id.fragment_magic_main_ritual_display_summary_textView);
                final TextView infobook = (TextView) dialogView.findViewById(R.id.fragment_magic_main_ritual_display_book_textView);
                final TextView infopage = (TextView) dialogView.findViewById(R.id.fragment_magic_main_ritual_display_page_textView);

                infoname.setText(ritualSelected.getName());
                infocategories.setText(ritualSelected.getCategory());

                infosummary.setText(ritualSelected.getSummary());
                infobook.setText(ritualSelected.getBook());
                infopage.setText(ritualSelected.getPage());

                builder.setTitle("Ritual: " + ritualSelected.getName());
                builder.setView(dialogView);

                if (add) {
                    builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (displayedRituals.size() < ShadowrunCharacter.getCharacter().getAttributes().getMagic() * 2) {
                                // Get the most currentKarmaCounter
                                int currentKarmaCounter = ShadowrunCharacter.getKarma();
                                int freeSpellsCounter = FreeCounters.getCounters().getFreeSpells();

                                if (freeSpellsCounter > 0) {
                                    freeSpellsCounter--;
                                    FreeCounters.getCounters().setFreeSpells(freeSpellsCounter);
                                    addRitual(ritualSelected, ChummerConstants.freeSpellUsed);
                                } else if (currentKarmaCounter - 5 >= 0) {
                                    currentKarmaCounter -= 5;
                                    ShadowrunCharacter.setKarma(currentKarmaCounter);
                                    addRitual(ritualSelected, 5);
                                } else {
                                    Toast.makeText(dialogView.getContext(), "You don't have enough to purchase this", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(dialogView.getContext(), "You are at the maximum number of formulae for your magic level", Toast.LENGTH_SHORT).show();
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
                            removeRitual(ritualSelected);

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