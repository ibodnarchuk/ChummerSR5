package com.iwan_b.chummersr5.data;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.util.ArrayList;

public class WeaponAccessory extends StreetGear implements Comparable<WeaponAccessory> {
    private ChummerConstants.weaponAccessoriesMount weaponAccessoriesMount;
    private ArrayList<Modifier> modifiers = new ArrayList<>();

    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(ArrayList<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public ChummerConstants.weaponAccessoriesMount getWeaponAccessoriesMount() {
        return weaponAccessoriesMount;
    }

    public void setWeaponAccessoriesMount(ChummerConstants.weaponAccessoriesMount weaponAccessoriesMount) {
        this.weaponAccessoriesMount = weaponAccessoriesMount;
    }

    public boolean parseXML(final String switchId, final String data) {
        // Attempt to parse the parent first
        if (super.parseXML(switchId, data)) {
            return true;
        }

        switch (switchId.toLowerCase()) {
            case "mount":
                switch (data.toLowerCase()) {
                    case "top":
                        this.setWeaponAccessoriesMount(ChummerConstants.weaponAccessoriesMount.topBarrel);
                        break;
                    case "barrel":
                        this.setWeaponAccessoriesMount(ChummerConstants.weaponAccessoriesMount.barrel);
                        break;
                    case "under":
                        this.setWeaponAccessoriesMount(ChummerConstants.weaponAccessoriesMount.underBarrel);
                        break;
                    default:
                        this.setWeaponAccessoriesMount(ChummerConstants.weaponAccessoriesMount.other);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public View displayView(LayoutInflater inflater, AdapterView<?> parent) {
        final View view = inflater.inflate(R.layout.fragment_weapons_weaponaccessory_display, parent, false);

        super.displayView(view);

        final TextView displayMountTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weaponaccessory_display_mount_textview);

        if (displayMountTxtView != null) {
            switch (weaponAccessoriesMount) {
                case topBarrel:
                    // TODO change the hardcoded values
                    displayMountTxtView.setText("Top Barrel");
                    break;
                case barrel:
                    displayMountTxtView.setText("Barrel");
                    break;
                case underBarrel:
                    displayMountTxtView.setText("Under Barrel");
                    break;
                case other:
                    displayMountTxtView.setVisibility(View.GONE);
                    break;
            }
        }

        return view;
    }

    @Override
    public int compareTo(WeaponAccessory another) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        //this optimization is usually worthwhile, and can always be added
        if (this == another) return EQUAL;

        if (this.weaponAccessoriesMount == null && another.weaponAccessoriesMount != null) {
            return BEFORE;
        } else if (this.weaponAccessoriesMount != null && another.weaponAccessoriesMount == null) {
            return AFTER;
        } else if (this.weaponAccessoriesMount != null && another.weaponAccessoriesMount != null) {
            return this.weaponAccessoriesMount.compareTo(another.weaponAccessoriesMount);
        }

        return this.getName().compareTo(another.getName());
    }

    @Override
    public String toString() {
        return super.toString() + " WeaponAccessory{" +
                "weaponAccessoriesMount=" + weaponAccessoriesMount +
                ", modifiers=" + modifiers +
                '}';
    }
}