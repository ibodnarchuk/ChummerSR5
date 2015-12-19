package com.iwan_b.chummersr5.fragments.Adept;


import android.app.ActionBar;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.AdeptPower;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.FragmentUtil.FactoryMethodInterface;
import com.iwan_b.chummersr5.fragments.FragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerMethods;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainContainer extends Fragment implements UpdateInterface, FactoryMethodInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();

    private ArrayList<AdeptPower> allAdaptPowers = new ArrayList<>();
    private ArrayList<AdeptPower> displayedAdeptPowers = new ArrayList<>();
    private AdeptPowerArrayAdapter AdeptPowerArrayAdapter;

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

    public void updatePowerPointCounter() {
        if (rootView != null) {
            TextView powerCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_adept_main_free_power_point_counter);
            powerCounterTxtView.setText(String.valueOf(FreeCounters.getCounters().getPowerPoints()));
        }
    }

    public void updateCounters() {
        updateKarma();
        updatePowerPointCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_adept_main, container, false);

        allAdaptPowers = readAdeptPowersXML("magic/adeptPowers.xml");

        updateCounters();

        toggleBuyPowerPoints();

        ListView listViewOfAdeptPowers = (ListView) rootView.findViewById(R.id.fragment_adept_main_adept_listview);

        displayedAdeptPowers = new ArrayList<>();

        AdeptPowerArrayAdapter = new AdeptPowerArrayAdapter(rootView.getContext(),
                android.R.layout.simple_list_item_1, displayedAdeptPowers);

        // Set the footerview to be the add spell button
        listViewOfAdeptPowers.setFooterDividersEnabled(true);

        final TextView footerView = new TextView(rootView.getContext());
        footerView.setText("Buy Adept Powers");

        footerView.setTextSize(24);
        footerView.setGravity(Gravity.CENTER_HORIZONTAL);

        listViewOfAdeptPowers.addFooterView(footerView);

        listViewOfAdeptPowers.setAdapter(AdeptPowerArrayAdapter);

        // When the user clicks on the add spell button display a list of available spells
        footerView.setOnClickListener(new AdeptPowerListener());

        // What to do when each spell is clicked.
        listViewOfAdeptPowers.setOnItemClickListener(new AdeptPowerInfoDialog(null, false));

        return rootView;
    }

    private void toggleBuyPowerPoints(){
        if(ShadowrunCharacter.getCharacter().getUserType() == ChummerConstants.userType.mystic_adept) {
            final LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragment_adept_main_mystic_adept_container);

            final TextView label = ChummerMethods.genTxtView(rootView.getContext(),"Power Points: ");
            final Button subButton = ChummerMethods.genButton(rootView.getContext(), "-");
            final TextView rating = ChummerMethods.genTxtView(rootView.getContext(), String.valueOf(FreeCounters.getCounters().getPowerPoints()));
            final Button addButton = ChummerMethods.genButton(rootView.getContext(), "+");

            TableRow.LayoutParams labelRatingLayoutParams = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            labelRatingLayoutParams.setMargins(0, 0, 5, 0);
            label.setLayoutParams(labelRatingLayoutParams);

            TableRow.LayoutParams ratingLayoutParams = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            ratingLayoutParams.setMargins(20, 0, 20, 0);
            rating.setLayoutParams(ratingLayoutParams);
            rating.setGravity(1);
            rating.setMinWidth(50);

            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float currentRating = Float.valueOf(rating.getText().toString());

                    Float powerPoints = FreeCounters.getCounters().getPowerPoints();
                    int karmaUnused = ShadowrunCharacter.getKarma();
                    if (currentRating - 1 >= 0 && powerPoints - 1 >= 0) {
                        powerPoints--;
                        karmaUnused += 2;
                        currentRating--;

                        rating.setText(String.valueOf(currentRating));

                        FreeCounters.getCounters().setPowerPoints(powerPoints);
                        ShadowrunCharacter.setKarma(karmaUnused);
                        updateCounters();
                    }
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float currentRating = Float.valueOf(rating.getText().toString());

                    Float powerPoints = FreeCounters.getCounters().getPowerPoints();
                    int karmaUnused = ShadowrunCharacter.getKarma();

                    if (powerPoints + 1 <= ShadowrunCharacter.getCharacter().getAttributes().getMagic() &&
                            currentRating + 1 <= ShadowrunCharacter.getCharacter().getAttributes().getMagic()) {
                        powerPoints++;
                        karmaUnused -= 2;
                        currentRating++;
                        rating.setText(String.valueOf(currentRating));

                        FreeCounters.getCounters().setPowerPoints(powerPoints);
                        ShadowrunCharacter.setKarma(karmaUnused);
                        updateCounters();
                    }
                }
            });

            layout.addView(label);
            layout.addView(subButton);
            layout.addView(rating);
            layout.addView(addButton);
        }
    }

    private AdeptPower getNewAdeptPower(final String adeptName) {
        for (final AdeptPower p : allAdaptPowers) {
            if (p.getName().equalsIgnoreCase(adeptName)) {
                return new AdeptPower(p);
            }
        }
        return null;
    }

    private AdeptPower getCurrentAdeptPower(final String adeptName) {
        ArrayList<AdeptPower> currentAdeptPowers;

        currentAdeptPowers = ShadowrunCharacter.getCharacter().getAdeptPowers();

        for (final AdeptPower p : currentAdeptPowers) {
            if (p.getName().equalsIgnoreCase(adeptName)) {
                return new AdeptPower(p);
            }
        }
        return null;
    }

    private ArrayList<AdeptPower> readAdeptPowersXML(final String fileLocation) {
        ArrayList<AdeptPower> adeptPowers = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = rootView.getContext().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            adeptPowers = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return adeptPowers;
    }

    private ArrayList<AdeptPower> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<AdeptPower> adeptPowers = new ArrayList<>();
        AdeptPower tempAdeptPower = null;
        Modifier m = null;
        boolean mod = false;

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
                    if (name.equalsIgnoreCase("power")) {
                        tempAdeptPower = new AdeptPower();
                    } else if (tempAdeptPower != null) {
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
                            }
                        } else {
                            String s = parser.nextText();
                            switch (name.toLowerCase()) {
                                case "name":
                                    tempAdeptPower.setName(s);
                                    break;
                                case "book":
                                    tempAdeptPower.setBook(s);
                                    break;
                                case "page":
                                    tempAdeptPower.setPage(s);
                                    break;
                                case "summary":
                                    tempAdeptPower.setSummary(s);
                                    break;
                                case "costlist":
                                    ArrayList<Float> costListTemp = new ArrayList<>();
                                    for (String costListSplit : s.split(",")) {
                                        costListTemp.add(Float.valueOf(costListSplit.trim()));
                                    }
                                    tempAdeptPower.setCostList(costListTemp);
                                    break;
                                case "activation":
                                    tempAdeptPower.setActivation(Boolean.valueOf(s));
                                    break;
                                case "list":
                                    tempAdeptPower.setList(s);
                                    break;
                                case "singular":
                                    tempAdeptPower.setSingular(Boolean.valueOf(s));
                                    break;
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    switch (name.toLowerCase()) {
                        case "mod":
                            if (tempAdeptPower != null) {
                                if (tempAdeptPower.getMods() == null) {
                                    ArrayList<Modifier> temp = new ArrayList<>();
                                    temp.add(m);
                                    tempAdeptPower.setMods(temp);
                                    m = null;
                                } else {
                                    tempAdeptPower.getMods().add(m);
                                }
                            }
                            mod = false;
                            break;
                        case "power":
                            adeptPowers.add(tempAdeptPower);
                            tempAdeptPower = null;
                            break;
                    }
            }
            eventType = parser.next();
        }
        return adeptPowers;
    }


    private void addAdeptPower(AdeptPower newAdeptPower) {
        if (newAdeptPower != null) {
            ArrayList<AdeptPower> adeptPowers = ShadowrunCharacter.getCharacter().getAdeptPowers();

            if (adeptPowers == null) {
                adeptPowers = new ArrayList<>();
            }

            adeptPowers.add(newAdeptPower);
            ShadowrunCharacter.getCharacter().setAdeptPowers(adeptPowers);

            displayedAdeptPowers.add(newAdeptPower);
            AdeptPowerArrayAdapter.notifyDataSetChanged();
        }
    }

    private void removeAdeptPower(AdeptPower adeptPowerSelected) {
        if (adeptPowerSelected != null) {
            ArrayList<AdeptPower> adeptPowers = ShadowrunCharacter.getCharacter().getAdeptPowers();

            if (adeptPowers == null) {
                adeptPowers = new ArrayList<>();
            }

            adeptPowers.remove(adeptPowerSelected);
            ShadowrunCharacter.getCharacter().setAdeptPowers(adeptPowers);

            for (int i = 0; i < displayedAdeptPowers.size(); i++) {
                if (displayedAdeptPowers.get(i).getName() == adeptPowerSelected.getName()) {

                    float counter = FreeCounters.getCounters().getPowerPoints();

                    counter += adeptPowerSelected.getCost() * adeptPowerSelected.getRating();

                    FreeCounters.getCounters().setPowerPoints(counter);

                    displayedAdeptPowers.remove(i);
                    break;
                }
            }

            AdeptPowerArrayAdapter.notifyDataSetChanged();
        }
    }

    private class AdeptPowerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialogVar = new Dialog(rootView.getContext());

            final ListView listOfAdeptPowers = new ListView(rootView.getContext());

            final ArrayList<AdeptPower> listData = new ArrayList<>();

            // Remove duplicates from the list of spells they can take.
            for (final AdeptPower p : allAdaptPowers) {
                // TODO make this more dynamic
                if (!displayedAdeptPowers.contains(p)) {
                    listData.add(p);
                }

            }

            final ArrayAdapter<AdeptPower> adapter = new AdeptPowerArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, listData);

            listOfAdeptPowers.setAdapter(adapter);

            // When they click on the spell display another dialog...
            listOfAdeptPowers.setOnItemClickListener(new AdeptPowerInfoDialog(dialogVar, true));

            dialogVar.setCancelable(true);

            dialogVar.setContentView(listOfAdeptPowers);

            dialogVar.setTitle("Spells");
            dialogVar.show();
        }
    }

    /**
     * Display the info for the adept power in a dialog
     */
    private class AdeptPowerInfoDialog implements AdapterView.OnItemClickListener {
        // Parent Dialog to dismiss
        private Dialog dialogVar;
        // Whether to add or delete the Adept Power
        private boolean isAdd;

        private AdeptPower adeptPowerSelected;

        public AdeptPowerInfoDialog(final Dialog dialogVar, final boolean isAdd) {
            this.dialogVar = dialogVar;
            this.isAdd = isAdd;
        }


        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            final AdeptPower item = (AdeptPower) parent.getItemAtPosition(position);

            Log.i(ChummerConstants.TAG, "onItemClick was: " + item);

            adeptPowerSelected = null;
            if (isAdd) {
                adeptPowerSelected = getNewAdeptPower(item.getName());
            } else {
                adeptPowerSelected = getCurrentAdeptPower(item.getName());
            }

            if (adeptPowerSelected == null) {
                Log.i(ChummerConstants.TAG, "The adept power can't be found: " + item);
            }


            // See if we have anything to display
            if (adeptPowerSelected != null) {
                // Build the dialog to display the spell
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                LayoutInflater inflater = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView = inflater.inflate(R.layout.fragment_adept_power_display, parent, false);

                final TextView infoname = (TextView) dialogView.findViewById(R.id.fragment_adept_power_display_name_textView);
                final RadioGroup infoCostList = (RadioGroup) dialogView.findViewById(R.id.fragment_adept_power_display_costList_radiogroup);

                final TextView ratingTxtView = new TextView(rootView.getContext());

                final TextView infosummary = (TextView) dialogView.findViewById(R.id.fragment_adept_power_display_summary_textView);
                final TextView infobook = (TextView) dialogView.findViewById(R.id.fragment_adept_power_display_infobook);
                final TextView infopage = (TextView) dialogView.findViewById(R.id.fragment_adept_power_display_infopage);


                if (!adeptPowerSelected.isSingular()) {
                    final LinearLayout extraLayout = (LinearLayout) dialogView.findViewById(R.id.fragment_adept_power_display_extra_linearLayout);

                    final TextView ratingTxtViewDisplay = new TextView(rootView.getContext());
                    final Button subButton = new Button(rootView.getContext());

                    final Button addButton = new Button(rootView.getContext());

                    ratingTxtViewDisplay.setText("Rating: ");
                    subButton.setText("-");
                    ratingTxtView.setText(String.valueOf(adeptPowerSelected.getRating()));
                    addButton.setText("+");

                    TableRow.LayoutParams lp3 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    lp3.setMargins(20, 0, 20, 0);
                    ratingTxtView.setLayoutParams(lp3);
                    ratingTxtView.setGravity(1);
                    ratingTxtView.setMinWidth(50);

                    extraLayout.addView(ratingTxtViewDisplay);
                    extraLayout.addView(subButton);
                    extraLayout.addView(ratingTxtView);
                    extraLayout.addView(addButton);

                    subButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int currentLvl = Integer.valueOf(ratingTxtView.getText().toString());
                            currentLvl--;
                            if (currentLvl < 1) {
                                currentLvl = 1;
                            }
                            ratingTxtView.setText(String.valueOf(currentLvl));
                        }
                    });

                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int currentLvl = Integer.valueOf(ratingTxtView.getText().toString());
                            if (currentLvl + 1 <= ShadowrunCharacter.getCharacter().getAttributes().getMagic()) {
                                currentLvl++;
                            }
                            ratingTxtView.setText(String.valueOf(currentLvl));
                        }
                    });

                    if (!isAdd) {
                        subButton.setEnabled(false);
                        addButton.setEnabled(false);
                    }
                }

                if (adeptPowerSelected.getCostList().size() == 1) {
                    final TextView infoCost = (TextView) dialogView.findViewById(R.id.fragment_adept_power_display_costList_txtview);

                    String endingOutput = " power points";
                    if (!adeptPowerSelected.isSingular()) {
                        endingOutput += " per rating";
                    }

                    infoCost.setText(String.valueOf(adeptPowerSelected.getCostList().get(0)) + endingOutput);
                } else {
                    for (float f : adeptPowerSelected.getCostList()) {
                        RadioButton radioButtonTemp = new RadioButton(rootView.getContext());
                        radioButtonTemp.setText(String.valueOf(f) + " Power Points");

                        if (!isAdd) {
                            if (adeptPowerSelected.getCost() == f) {
                                radioButtonTemp.setChecked(true);
                            }
                            radioButtonTemp.setEnabled(false);
                        }

                        infoCostList.addView(radioButtonTemp);
                    }
                }

                infoname.setText(adeptPowerSelected.getName());
                infosummary.setText(adeptPowerSelected.getSummary());
                infobook.setText(adeptPowerSelected.getBook());
                infopage.setText(adeptPowerSelected.getPage());

                builder.setTitle("Adept Power: " + adeptPowerSelected.getName());
                builder.setView(dialogView);

                if (isAdd) {
                    builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Get the most currentKarmaCounter
                            float freeAdeptPowerCounter = FreeCounters.getCounters().getPowerPoints();

                            float powerCost = 0;

                            // Get the base power cost for this power
                            if (adeptPowerSelected.getCostList().size() == 1) {
                                powerCost = adeptPowerSelected.getCostList().get(0);
                            } else {
                                int selectedId = infoCostList.getCheckedRadioButtonId();
                                RadioButton selectedRadioButton = (RadioButton) dialogView.findViewById(selectedId);

                                String temp = selectedRadioButton.getText().toString();
                                powerCost = Float.valueOf(temp.split(" ")[0]);
                            }

                            adeptPowerSelected.setCost(powerCost);

                            // Multiply the base power cost with the currentLvl to get the totalPowerCost.
                            if (!adeptPowerSelected.isSingular()) {
                                int currentLvl = Integer.valueOf(ratingTxtView.getText().toString());
                                adeptPowerSelected.setRating(currentLvl);
                                powerCost *= currentLvl;
                            }

                            if (freeAdeptPowerCounter >= powerCost) {
                                freeAdeptPowerCounter -= powerCost;
                                FreeCounters.getCounters().setPowerPoints(freeAdeptPowerCounter);
                                addAdeptPower(adeptPowerSelected);
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
                            removeAdeptPower(adeptPowerSelected);

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