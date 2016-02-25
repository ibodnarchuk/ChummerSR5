package com.iwan_b.chummersr5.fragments.Weapons;

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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.ParentItem;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Weapon;
import com.iwan_b.chummersr5.data.WeaponAccessory;
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
import java.util.Collections;
import java.util.List;

public class MainContainer extends Fragment implements UpdateInterface, FactoryMethodInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();

    private ArrayList<Weapon> allWeapons = new ArrayList<>();
    private ArrayList<Weapon> displayedWeapons = new ArrayList<>();
    private WeaponArrayAdapter displayedWeaponArrayAdapter;

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

    public void updateNyuenCounter() {
        if (rootView != null) {
            TextView nyuenCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_weapon_main_nyuen_counter);
            nyuenCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getCharacter().getNyuen()));
        }
    }

    public void updateCounters() {
        updateKarma();
        updateNyuenCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weapons_main, container, false);
        updateCounters();

        createKaramConverterContaienr();

        allWeapons = readWeaponXML("streetgear/weapons.xml");

        Collections.sort(allWeapons);

        ListView listViewOfWeapons = (ListView) rootView.findViewById(R.id.fragment_weapon_main_weapon_listview);

        displayedWeapons = new ArrayList<>();

        displayedWeaponArrayAdapter = new WeaponArrayAdapter(rootView.getContext(),
                android.R.layout.simple_list_item_1, displayedWeapons);

        // Set the footerview to be the purchase weapons
        listViewOfWeapons.setFooterDividersEnabled(true);

        final TextView footerView = new TextView(rootView.getContext());
        footerView.setText("Purchase Weapon");

        footerView.setTextSize(24);
        footerView.setGravity(Gravity.CENTER_HORIZONTAL);

        listViewOfWeapons.addFooterView(footerView);

        listViewOfWeapons.setAdapter(displayedWeaponArrayAdapter);

        // When the user clicks on the purchase weapon list of available weapons to purchase
        footerView.setOnClickListener(new WeaponListener());

        // What to do when each weapon is clicked.
        listViewOfWeapons.setOnItemClickListener(new WeaponViewInfoDialog());

        return rootView;
    }

    private ArrayList<Weapon> readWeaponXML(final String fileLocation) {
        ArrayList<Weapon> weapons = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = rootView.getContext().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            weapons = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return weapons;
    }

    private ArrayList<Weapon> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Weapon> weapons = new ArrayList<>();
        Weapon tempWeapon = null;

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
                    if (name.equalsIgnoreCase("weapon")) {
                        tempWeapon = new Weapon();
                    } else if (tempWeapon != null) {
                        String data = parser.nextText();
                        tempWeapon.parseXML(name.toLowerCase(), data);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (tempWeapon != null) {
                        weapons.add(tempWeapon);
                        tempWeapon = null;
                    }
            }
            eventType = parser.next();
        }
        return weapons;
    }

    private void createKaramConverterContaienr() {
        final LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragment_weapon_main_karma_converter_container);

        final TextView label = ChummerMethods.genTxtView(rootView.getContext(), "Convert karma to nuyen: ");
        final Button subButton = ChummerMethods.genButton(rootView.getContext(), "-");
        final TextView rating = ChummerMethods.genTxtView(rootView.getContext(), String.valueOf(FreeCounters.getCounters().getKarmaUsedForNuyen()));
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
                int currentNuyen = ShadowrunCharacter.getCharacter().getNyuen();

                int currentCount = FreeCounters.getCounters().getKarmaUsedForNuyen();

                int karmaUnused = ShadowrunCharacter.getKarma();
                if (currentCount - 1 >= 0 && currentNuyen - 2000 >= 0) {
                    karmaUnused += 1;
                    currentCount--;

                    currentNuyen -= 2000;

                    rating.setText(String.valueOf(currentCount));

                    ShadowrunCharacter.getCharacter().setNyuen(currentNuyen);
                    FreeCounters.getCounters().setKarmaUsedForNuyen(currentCount);
                    ShadowrunCharacter.setKarma(karmaUnused);
                    updateCounters();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNuyen = ShadowrunCharacter.getCharacter().getNyuen();

                int currentCount = FreeCounters.getCounters().getKarmaUsedForNuyen();

                int karmaUnused = ShadowrunCharacter.getKarma();
                if (currentCount + 1 <= 10) {
                    karmaUnused -= 1;
                    currentCount++;

                    currentNuyen += 2000;

                    rating.setText(String.valueOf(currentCount));

                    FreeCounters.getCounters().setKarmaUsedForNuyen(currentCount);
                    ShadowrunCharacter.getCharacter().setNyuen(currentNuyen);
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

    /**
     * Sort the arrays by type
     */
    private ArrayList<ArrayList<Weapon>> sortArrayOfWeaponsByType() {
        // TODO optomize this method.

        ArrayList<ArrayList<Weapon>> childItems = new ArrayList<>();

        ArrayList<Weapon> tempWeapons = new ArrayList<>();

        for (Weapon w : allWeapons) {
            tempWeapons.add(new Weapon(w));
        }

        for (int outerLoop = 0; outerLoop < tempWeapons.size(); ) {
            ArrayList<Weapon> subList = new ArrayList<>();
            Weapon outerLoopWeapon = tempWeapons.get(outerLoop);
            subList.add(outerLoopWeapon);

            outerLoop++;
            for (int j = outerLoop; j < tempWeapons.size(); j++) {
                Weapon innerLoopWeapon = tempWeapons.get(j);
                if (outerLoopWeapon.getType().equalsIgnoreCase(innerLoopWeapon.getType())) {
                    subList.add(innerLoopWeapon);
                    if (j + 1 == tempWeapons.size()) {
                        outerLoop = j + 1;
                    }
                } else {
                    outerLoop = j;
                    break;
                }
            }

            childItems.add(subList);
        }

        return childItems;
    }

    private ArrayList<WeaponAccessory> readWeaponAccessoriesXML(final String fileLocation) {
        ArrayList<WeaponAccessory> weaponAccessories = new ArrayList<>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = rootView.getContext().getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            weaponAccessories = parseWeaponAccessoryXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return weaponAccessories;
    }

    private ArrayList<WeaponAccessory> parseWeaponAccessoryXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<WeaponAccessory> weaponAccessory = new ArrayList<>();
        WeaponAccessory tempWeaponAccessory = null;

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
                    if (name.equalsIgnoreCase("accessory")) {
                        tempWeaponAccessory = new WeaponAccessory();
                    } else if (tempWeaponAccessory != null) {
                        String data = parser.nextText();
                        tempWeaponAccessory.parseXML(name.toLowerCase(), data);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (tempWeaponAccessory != null) {
                        weaponAccessory.add(tempWeaponAccessory);
                        tempWeaponAccessory = null;
                    }
            }
            eventType = parser.next();
        }

        return weaponAccessory;
    }

    private class WeaponListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialogVar = new Dialog(rootView.getContext());

            ExpandableListView expandableList = new ExpandableListView(rootView.getContext());
            expandableList.setDividerHeight(2);
            expandableList.setGroupIndicator(null);
            expandableList.setClickable(true);

            ArrayList<ArrayList<Weapon>> childItems = sortArrayOfWeaponsByType();

            List<ParentItem> itemList = new ArrayList<>();

            for (ArrayList<Weapon> arrayW : childItems) {
                ParentItem parent = new ParentItem(arrayW.get(0).getType());

                for (Weapon w : arrayW) {
                    parent.getChildItemList().add(w);
                }

                itemList.add(parent);
            }

            WeaponExpandableAdapter adapter = new WeaponExpandableAdapter(rootView.getContext(), itemList);

            expandableList.setAdapter(adapter);

            expandableList.setOnChildClickListener(new WeaponInfoDialog(dialogVar));

            dialogVar.setContentView(expandableList);

            dialogVar.setCancelable(true);
            dialogVar.setTitle("Weapons");
            dialogVar.show();
        }
    }

    private class WeaponViewInfoDialog implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Weapon weapon = displayedWeapons.get(position);

            new WeaponDialog().createView(weapon, parent, null);


        }
    }

    private class WeaponInfoDialog implements ExpandableListView.OnChildClickListener {
        private Dialog dialogVar;

        public WeaponInfoDialog(final Dialog dialogVar) {
            this.dialogVar = dialogVar;
        }

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            final Weapon weapon = (Weapon) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);

            new WeaponDialog().createView(weapon, parent, dialogVar);

            return false;
        }
    }

    private class WeaponDialog {
        public void createView(final Weapon weapon, AdapterView<?> parent, final Dialog dialogVar) {
            if (weapon != null) {
                // Build the dialog to display the spell
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                LayoutInflater inflater = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView = weapon.displayView(inflater, parent);

                // Top Barrel Mod
                WeaponMod topBarrel = new WeaponMod(weapon, ChummerConstants.weaponAccessoriesMount.topBarrel);
                topBarrel.createView(dialogView);

                // Barrel Mod
                WeaponMod barrel = new WeaponMod(weapon, ChummerConstants.weaponAccessoriesMount.barrel);
                barrel.createView(dialogView);

                // Bottom Barrel Mod
                WeaponMod bottomBarrel = new WeaponMod(weapon, ChummerConstants.weaponAccessoriesMount.underBarrel);
                bottomBarrel.createView(dialogView);

                // Other Weapon Mode
                WeaponMod otherAccessories = new WeaponMod(weapon, ChummerConstants.weaponAccessoriesMount.other);
                otherAccessories.createView(dialogView);

                builder.setTitle("Weapon: " + weapon.getName());
                builder.setView(dialogView);

                if (dialogVar != null) {
                    builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int cost = weapon.getCost();

                            int currentNuyen = ShadowrunCharacter.getCharacter().getNyuen();

                            if (cost <= currentNuyen) {
                                currentNuyen -= cost;

                                ShadowrunCharacter.getCharacter().setNyuen(currentNuyen);

                                updateCounters();

                                displayedWeapons.add(new Weapon(weapon));
                                displayedWeaponArrayAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(rootView.getContext(), "Not enough money to purchase this.", Toast.LENGTH_SHORT).show();
                            }


                            if (dialogVar != null) {
                                dialogVar.dismiss();
                            }
                        }
                    });
                } else {
                    builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int cost = weapon.getCost();

                            int currentNuyen = ShadowrunCharacter.getCharacter().getNyuen();

                            ShadowrunCharacter.getCharacter().setNyuen(currentNuyen + cost);

                            updateCounters();

                            displayedWeapons.remove(weapon);
                            displayedWeaponArrayAdapter.notifyDataSetChanged();

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

    private class WeaponMod {
        private ChummerConstants.weaponAccessoriesMount weaponAccessoriesMount;
        private Weapon currentWeapon;

        public WeaponMod(Weapon currentWeapon, ChummerConstants.weaponAccessoriesMount weaponAccessoriesMount) {
            this.currentWeapon = currentWeapon;
            this.weaponAccessoriesMount = weaponAccessoriesMount;

        }

        private LinearLayout getBarrelLayout(final View dialogView) {
            switch (weaponAccessoriesMount) {
                case topBarrel:
                    return (LinearLayout) dialogView.findViewById(R.id.fragment_weapons_weapon_display_top_barrel_linearLayout);
                case barrel:
                    return (LinearLayout) dialogView.findViewById(R.id.fragment_weapons_weapon_display_barrel_linearLayout);
                case underBarrel:
                    return (LinearLayout) dialogView.findViewById(R.id.fragment_weapons_weapon_display_bottom_barrel_linearLayout);
                case other:
                    return (LinearLayout) dialogView.findViewById(R.id.fragment_weapons_weapon_display_other_accessory_linearLayout);
            }

            return null;
        }

        private void setBarrelMod(final WeaponAccessory weaponAccessory) {
            switch (weaponAccessoriesMount) {
                case topBarrel:
                    currentWeapon.setTopWeaponAccessory(weaponAccessory);
                    break;
                case barrel:
                    currentWeapon.setBarrelWeaponAccessory(weaponAccessory);
                    break;
                case underBarrel:
                    currentWeapon.setBottomWeaponAccessory(weaponAccessory);
                    break;
                case other:
                    // TODO implement this
//                    currentWeapon.set
                    break;
            }
        }

        public LinearLayout createView(final View dialogView) {
            final LinearLayout barrelModLinearLayout = getBarrelLayout(dialogView);

            final ArrayList<WeaponAccessory> weaponAccessories = readWeaponAccessoriesXML("streetgear/weapon_accessories.xml");

            Button b = ChummerMethods.genButton(dialogView.getContext(), "Add/Change Mod");

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                    final Dialog dialogVar = new Dialog(getActivity());

                    final ListView listOfWeaponAccessories = new ListView(getActivity().getBaseContext());

                    final ArrayList<String> listData = new ArrayList<>();

                    for (final WeaponAccessory w : weaponAccessories) {
                        if (w.getWeaponAccessoriesMount() == weaponAccessoriesMount) {
                            listData.add(w.getName());
                        }
                    }

                    // TODO make a custom Adapter
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(),
                            android.R.layout.simple_list_item_1, listData);

                    listOfWeaponAccessories.setAdapter(adapter);

                    listOfWeaponAccessories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            WeaponAccessory itemSelected = null;

                            for (final WeaponAccessory w : weaponAccessories) {
                                if (w.getName().equalsIgnoreCase(parent.getAdapter().getItem(position).toString())) {
                                    itemSelected = w;
                                    break;
                                }
                            }

                            if (itemSelected != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                                LayoutInflater inflater = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                builder.setView(itemSelected.displayView(inflater, parent));
                                builder.setCancelable(true);


                                // TODO finish implementing this
                                final WeaponAccessory finalItemSelected = itemSelected;
                                builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TextView tempTextView = new TextView(rootView.getContext());

                                        for (int i = 0; i < barrelModLinearLayout.getChildCount(); i++) {
                                            // Can't use instanceof here because button is a subclass of textview
                                            if (barrelModLinearLayout.getChildAt(i).getClass() == TextView.class) {
                                                tempTextView = (TextView) barrelModLinearLayout.getChildAt(i);
                                            }
                                        }

                                        tempTextView.setText(finalItemSelected.getName());

                                        setBarrelMod(finalItemSelected);

                                        if (tempTextView.getParent() == null) {
                                            barrelModLinearLayout.addView(tempTextView);
                                        }
                                        dialogVar.dismiss();
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });

                                builder.setTitle("Weapon Accessory");
                                builder.show();
                            }
                        }
                    });


                    dialogVar.setCancelable(true);

                    dialogVar.setContentView(listOfWeaponAccessories);

                    dialogVar.setTitle("Weapon Accessories");
                    dialogVar.show();

                }
            });

            barrelModLinearLayout.addView(b);

            return barrelModLinearLayout;
        }


    }

}