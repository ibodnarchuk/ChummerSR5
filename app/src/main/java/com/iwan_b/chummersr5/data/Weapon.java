package com.iwan_b.chummersr5.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class Weapon extends StreetGear implements Comparable<Weapon> {
    private ArrayList<Integer> accuracy_listIntegers = new ArrayList<>();
    private boolean is_accuracy_physical = false;

    private int reach = 0;

    private boolean is_damage_str = false;
    private boolean is_damage_rating = false;

    private ArrayList<Integer> damage_list = new ArrayList<>();
    private ArrayList<Character> damage_type_list = new ArrayList<>();
    private boolean is_damage_electric = false;
    private boolean is_damage_flechette = false;

    private ArrayList<Integer> ap_list = new ArrayList<>();
    private Boolean is_ap_divide_by_rating = false;

    private ArrayList<String> mode_list = new ArrayList<>();

    private ArrayList<Integer> recoil_list = new ArrayList<>();

    private ArrayList<Integer> ammo_list = new ArrayList<>();
    private ArrayList<String> ammo_type_list = new ArrayList<>();
    private String ammo_selected;

    private WeaponAccessory topWeaponAccessory, barrelWeaponAccessory, bottomWeaponAccessory;
    private ArrayList<WeaponAccessory> otherWeaponAccessories;

    public Weapon() {
    }

    public Weapon(Weapon copy) {
        super(copy);
        this.accuracy_listIntegers = copy.accuracy_listIntegers;
        this.is_accuracy_physical = copy.is_accuracy_physical;
        this.reach = copy.reach;
        this.is_damage_str = copy.is_damage_str;
        this.is_damage_rating = copy.is_damage_rating;
        this.damage_list = copy.damage_list;
        this.damage_type_list = copy.damage_type_list;
        this.is_damage_electric = copy.is_damage_electric;
        this.is_damage_flechette = copy.is_damage_flechette;
        this.ap_list = copy.ap_list;
        this.is_ap_divide_by_rating = copy.is_ap_divide_by_rating;
        this.mode_list = copy.mode_list;
        this.recoil_list = copy.recoil_list;
        this.ammo_list = copy.ammo_list;
        this.ammo_type_list = copy.ammo_type_list;
        this.ammo_selected = copy.ammo_selected;
        this.topWeaponAccessory = copy.topWeaponAccessory;
        this.barrelWeaponAccessory = copy.barrelWeaponAccessory;
        this.bottomWeaponAccessory = copy.bottomWeaponAccessory;
        this.otherWeaponAccessories = copy.otherWeaponAccessories;
    }

    public View displayView(LayoutInflater inflater, AdapterView<?> parent) {
        final View view = inflater.inflate(R.layout.fragment_weapons_weapon_display, parent, false);

        super.displayView(view);

        final TextView accuracyTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_accuracy_textview);
        final TextView reachTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_reach_textview);
        final TextView damageTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_damage_textview);
        final TextView APTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_ap_textview);
        final TextView modeTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_mode_textview);
        final TextView recoilTxtView = (TextView) view.findViewById(R.id.fragment_weapons_weapon_display_recoil_textview);
        final LinearLayout ammoLinearLayoutView = (LinearLayout) view.findViewById(R.id.fragment_weapons_weapon_display_ammo_linearview);

        final LinearLayout topBarrelAccessoryLinearLayoutView = (LinearLayout) view.findViewById(R.id.fragment_weapons_weapon_display_top_barrel_linearLayout);
        final LinearLayout barrelAccessoryLinearLayoutView = (LinearLayout) view.findViewById(R.id.fragment_weapons_weapon_display_barrel_linearLayout);
        final LinearLayout bottomBarrelAccessoryLinearLayoutView = (LinearLayout) view.findViewById(R.id.fragment_weapons_weapon_display_bottom_barrel_linearLayout);
        final LinearLayout otherAccessoryLinearLayoutView = (LinearLayout) view.findViewById(R.id.fragment_weapons_weapon_display_other_accessory_linearLayout);

        // Accuracy
        String temp = "";
        if (accuracy_listIntegers.size() == 0) {
            if (is_accuracy_physical) {
                accuracyTxtView.setText("Physical");
            } else {
                accuracyTxtView.setText("-");
            }
        } else {
            for (Integer i : accuracy_listIntegers) {
                temp += i + ", ";
            }
            if (temp.endsWith(", ")) {
                temp = temp.substring(0, temp.length() - 2);
            }
            accuracyTxtView.setText(temp);
        }

        // Reach
        if (reach == 0) {
            reachTxtView.setText("-");
        } else {
            reachTxtView.setText(String.valueOf(reach));
        }

        // Damage
        temp = "";
        String weaponPreAppend = "";
        String weaponDelim = "";
        if (is_damage_str) {
            weaponPreAppend = "(STR + ";
            weaponDelim = ")";
        }

        if (is_damage_rating) {
            weaponPreAppend = "(Rating + ";
            weaponDelim = ")";
        }

        for (int i = 0; i < damage_list.size(); i++) {
            temp += weaponPreAppend;
            temp += damage_list.get(i) + weaponDelim;

            // Test to see if there is a specfic damage type, otherwise use the last one.
            if (damage_type_list.size() > i) {
                temp += damage_type_list.get(i);
            } else {
                temp += damage_type_list.get(damage_type_list.size() - 1);
            }
            temp += ", ";
        }
        if (temp.endsWith(", ")) {
            temp = temp.substring(0, temp.length() - 2);
        }

        if (is_damage_electric) {
            temp += "(e)";
        }

        if (is_damage_flechette) {
            temp += "(f)";
        }
        damageTxtView.setText(temp);

        // AP
        temp = "";
        if (is_ap_divide_by_rating) {
            for (Integer i : ap_list) {
                temp += "-(Rating / " + i + "), ";
            }
            if (temp.endsWith(", ")) {
                temp = temp.substring(0, temp.length() - 2);
            }
        } else {
            for (Integer i : ap_list) {
                temp += i + ", ";
            }
            if (temp.endsWith(", ")) {
                temp = temp.substring(0, temp.length() - 2);
            }
        }

        if (temp.isEmpty()) {
            temp = "-";
        }

        APTxtView.setText(temp);

        // Mode
        temp = "";
        for (String s : mode_list) {
            temp += s + "/";
        }
        if (temp.endsWith("/")) {
            temp = temp.substring(0, temp.length() - 1);
        }
        if (temp.isEmpty()) {
            temp = "-";
        }
        modeTxtView.setText(temp);

        // Recoil
        temp = "";
        for (Integer i : recoil_list) {
            temp += i + ", ";
        }
        if (temp.endsWith(", ")) {
            temp = temp.substring(0, temp.length() - 2);
        }
        if (temp.isEmpty()) {
            temp = "-";
        }
        recoilTxtView.setText(temp);

        // Ammo
        if (ammoLinearLayoutView != null) {
            if (!ammo_list.isEmpty() && !ammo_type_list.isEmpty()) {
                if (ammo_type_list.size() == 1) {
                    TextView ammoTxtView = new TextView(view.getContext());

                    ammoTxtView.setText(ammo_list.get(0) + "(" + ammo_type_list.get(0) + ")");

                    ammoLinearLayoutView.addView(ammoTxtView);
                } else {
                    final RadioGroup radioGroup = new RadioGroup(view.getContext());

                    for (int i = 0; i < ammo_list.size(); i++) {
                        temp = "";
                        RadioButton rb = new RadioButton(view.getContext());

                        temp += ammo_list.get(i);

                        if (ammo_type_list.size() > i) {
                            temp += "(" + ammo_type_list.get(i) + ")";
                        } else {
                            temp += "(" + ammo_type_list.get(ammo_type_list.size() - 1) + ")";
                        }
                        rb.setText(temp);

                        radioGroup.addView(rb);

                        // Check if the checkbox was previously selected
                        if (ammo_selected != null && !ammo_selected.isEmpty()) {
                            if (ammo_selected.equalsIgnoreCase(rb.getText().toString().replaceAll("\\(|\\)", ""))) {
                                radioGroup.check(rb.getId());
                            }
                        }
                    }

                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton checked = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
                            ammo_selected = checked.getText().toString().replaceAll("\\(|\\)", "");
                        }
                    });

                    // Disable all the radio buttons since the ammo was already selected
                    if (ammo_selected != null && !ammo_selected.isEmpty()) {
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }
                    }

                    ammoLinearLayoutView.addView(radioGroup);
                }
            } else {
                TextView ammoTxtView = new TextView(view.getContext());

                ammoTxtView.setText("-");

                ammoLinearLayoutView.addView(ammoTxtView);
            }
        }

        if (topBarrelAccessoryLinearLayoutView != null && topWeaponAccessory != null) {
            TextView topBarrelAccessoryTxtView = new TextView(view.getContext());

            topBarrelAccessoryTxtView.setText(topWeaponAccessory.getName());

            topBarrelAccessoryLinearLayoutView.addView(topBarrelAccessoryTxtView);
        }

        if (barrelAccessoryLinearLayoutView != null && barrelWeaponAccessory != null) {
            TextView barrelAccessoryTxtView = new TextView(view.getContext());

            barrelAccessoryTxtView.setText(barrelWeaponAccessory.getName());

            barrelAccessoryLinearLayoutView.addView(barrelAccessoryTxtView);
        }

        if (bottomBarrelAccessoryLinearLayoutView != null && bottomWeaponAccessory != null) {
            TextView bottomBarrelAccessoryTxtView = new TextView(view.getContext());

            bottomBarrelAccessoryTxtView.setText(bottomWeaponAccessory.getName());

            bottomBarrelAccessoryLinearLayoutView.addView(bottomBarrelAccessoryTxtView);
        }

        if (otherAccessoryLinearLayoutView != null && otherWeaponAccessories != null && !otherWeaponAccessories.isEmpty()) {
            for (WeaponAccessory weaponAccessoryTemp : otherWeaponAccessories) {
                TextView otherAccessoryTxtView = new TextView(view.getContext());
                otherAccessoryTxtView.setText(weaponAccessoryTemp.getName());
                otherAccessoryLinearLayoutView.addView(otherAccessoryTxtView);
            }
        }

        return view;
    }

    public Boolean getIs_ap_divide_by_rating() {
        return is_ap_divide_by_rating;
    }

    public void setIs_ap_divide_by_rating(Boolean is_ap_divide_by_rating) {
        this.is_ap_divide_by_rating = is_ap_divide_by_rating;
    }

    public WeaponAccessory getTopWeaponAccessory() {
        return topWeaponAccessory;
    }

    public void setTopWeaponAccessory(WeaponAccessory topWeaponAccessory) {
        this.topWeaponAccessory = topWeaponAccessory;
    }

    public WeaponAccessory getBarrelWeaponAccessory() {
        return barrelWeaponAccessory;
    }

    public void setBarrelWeaponAccessory(WeaponAccessory barrelWeaponAccessory) {
        this.barrelWeaponAccessory = barrelWeaponAccessory;
    }

    public WeaponAccessory getBottomWeaponAccessory() {
        return bottomWeaponAccessory;
    }

    public void setBottomWeaponAccessory(WeaponAccessory bottomWeaponAccessory) {
        this.bottomWeaponAccessory = bottomWeaponAccessory;
    }

    public ArrayList<WeaponAccessory> getOtherWeaponAccessories() {
        return otherWeaponAccessories;
    }

    public void setOtherWeaponAccessories(ArrayList<WeaponAccessory> otherWeaponAccessories) {
        this.otherWeaponAccessories = otherWeaponAccessories;
    }

    public boolean parseXML(final String switchId, String data) {
        // Attempt to parse the parent first
        if (super.parseXML(switchId, data)) {
            return true;
        }

        switch (switchId.toLowerCase()) {
            case "accuracy_list":
                if (!data.isEmpty()) {
                    String[] temp = data.split(",");
                    ArrayList<Integer> intTemp = new ArrayList<>();
                    for (String varToConvert : temp) {
                        intTemp.add(Integer.valueOf(varToConvert));
                    }

                    this.setAccuracy_listIntegers(intTemp);
                }
                break;
            case "is_accuracy_physical":
                this.setIs_accuracy_physical(Boolean.valueOf(data));
                break;
            case "reach":
                try {
                    this.setReach(Integer.valueOf(data));
                } catch (NumberFormatException e) {
                    Log.i(ChummerConstants.TAG, "Number Format Exception at reach for: " + this.toString());
                }
                break;
            case "is_damage_str":
                this.setIs_damage_str(Boolean.valueOf(data));
                break;
            case "is_damage_rating":
                this.setIs_damage_rating(Boolean.valueOf(data));
                break;
            case "damage_list":
                if (!data.isEmpty()) {
                    String[] temp = data.split(",");
                    ArrayList<Integer> intTemp = new ArrayList<>();
                    for (String varToConvert : temp) {
                        intTemp.add(Integer.valueOf(varToConvert));
                    }

                    this.setDamage_list(intTemp);
                }
                break;
            case "damage_type_list":
                // Remove all the deliminators
                data = data.replaceAll(",", "");
                ArrayList<Character> damage_type_list = new ArrayList<>();

                for (Character c : data.toCharArray()) {
                    damage_type_list.add(c);
                }

                this.setDamage_type_list(damage_type_list);
                break;
            case "is_damage_electric":
                this.setIs_damage_electric(Boolean.valueOf(data));
                break;
            case "is_damage_flechette":
                this.setIs_damage_flechette(Boolean.valueOf(data));
                break;
            case "ap_list":
                if (!data.isEmpty()) {
                    String[] temp = data.split(",");
                    ArrayList<Integer> intTemp = new ArrayList<>();
                    for (String varToConvert : temp) {
                        intTemp.add(Integer.valueOf(varToConvert));
                    }
                    this.setAp_list(intTemp);
                }
                break;
            case "ap_divide_by":
                try {
                    this.setIs_ap_divide_by_rating(Boolean.valueOf(data));
                } catch (NullPointerException e) {
                    Log.i(ChummerConstants.TAG, "Null Pointer Exception at ap_divide_by for: " + this.toString());
                }
                break;
            case "mode_list":
                ArrayList<String> mode_list = new ArrayList<>(Arrays.asList(data.split(",")));
                this.setMode_list(mode_list);
                break;
            case "recoil_list":
                if (!data.isEmpty()) {
                    String[] temp = data.split(",");
                    ArrayList<Integer> intTemp = new ArrayList<>();
                    for (String varToConvert : temp) {
                        intTemp.add(Integer.valueOf(varToConvert));
                    }

                    this.setRecoil_list(intTemp);
                }
                break;
            case "ammo_list":
                if (!data.isEmpty()) {
                    String[] temp = data.split(",");
                    ArrayList<Integer> intTemp = new ArrayList<>();
                    for (String varToConvert : temp) {
                        intTemp.add(Integer.valueOf(varToConvert));
                    }

                    this.setAmmo_list(intTemp);
                }
                break;
            case "ammo_type":
                this.setAmmo_type_list(new ArrayList<String>(Arrays.asList(data.split(","))));
                break;
            default:
                return false;
        }
        return true;
    }

    public Boolean is_ap_divide_by_rating() {
        return is_ap_divide_by_rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weapon)) return false;
        if (!super.equals(o)) return false;

        Weapon weapon = (Weapon) o;

        if (is_accuracy_physical != weapon.is_accuracy_physical) return false;
        if (getReach() != weapon.getReach()) return false;
        if (is_damage_str != weapon.is_damage_str) return false;
        if (is_damage_rating != weapon.is_damage_rating) return false;
        if (is_damage_electric != weapon.is_damage_electric) return false;
        if (is_damage_flechette != weapon.is_damage_flechette) return false;
        if (getAccuracy_listIntegers() != null ? !getAccuracy_listIntegers().equals(weapon.getAccuracy_listIntegers()) : weapon.getAccuracy_listIntegers() != null)
            return false;
        if (getDamage_list() != null ? !getDamage_list().equals(weapon.getDamage_list()) : weapon.getDamage_list() != null)
            return false;
        if (getDamage_type_list() != null ? !getDamage_type_list().equals(weapon.getDamage_type_list()) : weapon.getDamage_type_list() != null)
            return false;
        if (getAp_list() != null ? !getAp_list().equals(weapon.getAp_list()) : weapon.getAp_list() != null)
            return false;
        if (getIs_ap_divide_by_rating() != null ? !getIs_ap_divide_by_rating().equals(weapon.getIs_ap_divide_by_rating()) : weapon.getIs_ap_divide_by_rating() != null)
            return false;
        if (getMode_list() != null ? !getMode_list().equals(weapon.getMode_list()) : weapon.getMode_list() != null)
            return false;
        if (getRecoil_list() != null ? !getRecoil_list().equals(weapon.getRecoil_list()) : weapon.getRecoil_list() != null)
            return false;
        if (getAmmo_list() != null ? !getAmmo_list().equals(weapon.getAmmo_list()) : weapon.getAmmo_list() != null)
            return false;
        if (getAmmo_type_list() != null ? !getAmmo_type_list().equals(weapon.getAmmo_type_list()) : weapon.getAmmo_type_list() != null)
            return false;
        if (getAmmo_selected() != null ? !getAmmo_selected().equals(weapon.getAmmo_selected()) : weapon.getAmmo_selected() != null)
            return false;
        if (getTopWeaponAccessory() != null ? !getTopWeaponAccessory().equals(weapon.getTopWeaponAccessory()) : weapon.getTopWeaponAccessory() != null)
            return false;
        if (getBarrelWeaponAccessory() != null ? !getBarrelWeaponAccessory().equals(weapon.getBarrelWeaponAccessory()) : weapon.getBarrelWeaponAccessory() != null)
            return false;
        if (getBottomWeaponAccessory() != null ? !getBottomWeaponAccessory().equals(weapon.getBottomWeaponAccessory()) : weapon.getBottomWeaponAccessory() != null)
            return false;
        return !(getOtherWeaponAccessories() != null ? !getOtherWeaponAccessories().equals(weapon.getOtherWeaponAccessories()) : weapon.getOtherWeaponAccessories() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAccuracy_listIntegers() != null ? getAccuracy_listIntegers().hashCode() : 0);
        result = 31 * result + (is_accuracy_physical ? 1 : 0);
        result = 31 * result + getReach();
        result = 31 * result + (is_damage_str ? 1 : 0);
        result = 31 * result + (is_damage_rating ? 1 : 0);
        result = 31 * result + (getDamage_list() != null ? getDamage_list().hashCode() : 0);
        result = 31 * result + (getDamage_type_list() != null ? getDamage_type_list().hashCode() : 0);
        result = 31 * result + (is_damage_electric ? 1 : 0);
        result = 31 * result + (is_damage_flechette ? 1 : 0);
        result = 31 * result + (getAp_list() != null ? getAp_list().hashCode() : 0);
        result = 31 * result + (getIs_ap_divide_by_rating() != null ? getIs_ap_divide_by_rating().hashCode() : 0);
        result = 31 * result + (getMode_list() != null ? getMode_list().hashCode() : 0);
        result = 31 * result + (getRecoil_list() != null ? getRecoil_list().hashCode() : 0);
        result = 31 * result + (getAmmo_list() != null ? getAmmo_list().hashCode() : 0);
        result = 31 * result + (getAmmo_type_list() != null ? getAmmo_type_list().hashCode() : 0);
        result = 31 * result + (getAmmo_selected() != null ? getAmmo_selected().hashCode() : 0);
        result = 31 * result + (getTopWeaponAccessory() != null ? getTopWeaponAccessory().hashCode() : 0);
        result = 31 * result + (getBarrelWeaponAccessory() != null ? getBarrelWeaponAccessory().hashCode() : 0);
        result = 31 * result + (getBottomWeaponAccessory() != null ? getBottomWeaponAccessory().hashCode() : 0);
        result = 31 * result + (getOtherWeaponAccessories() != null ? getOtherWeaponAccessories().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " Weapon{" +
                "accuracy_listIntegers=" + accuracy_listIntegers +
                ", is_accuracy_physical=" + is_accuracy_physical +
                ", reach=" + reach +
                ", is_damage_str=" + is_damage_str +
                ", is_damage_rating=" + is_damage_rating +
                ", damage_list=" + damage_list +
                ", damage_type_list=" + damage_type_list +
                ", is_damage_electric=" + is_damage_electric +
                ", is_damage_flechette=" + is_damage_flechette +
                ", ap_list=" + ap_list +
                ", is_ap_divide_by_rating=" + is_ap_divide_by_rating +
                ", mode_list=" + mode_list +
                ", recoil_list=" + recoil_list +
                ", ammo_list=" + ammo_list +
                ", ammo_type_list=" + ammo_type_list +
                ", ammo_selected='" + ammo_selected + '\'' +
                ", topWeaponAccessory=" + topWeaponAccessory +
                ", barrelWeaponAccessory=" + barrelWeaponAccessory +
                ", bottomWeaponAccessory=" + bottomWeaponAccessory +
                ", otherWeaponAccessories=" + otherWeaponAccessories +
                '}';
    }

    public ArrayList<Integer> getAccuracy_listIntegers() {
        return accuracy_listIntegers;
    }

    public void setAccuracy_listIntegers(ArrayList<Integer> accuracy_listIntegers) {
        this.accuracy_listIntegers = accuracy_listIntegers;
    }

    public boolean is_accuracy_physical() {
        return is_accuracy_physical;
    }

    public void setIs_accuracy_physical(boolean is_accuracy_physical) {
        this.is_accuracy_physical = is_accuracy_physical;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public boolean is_damage_str() {
        return is_damage_str;
    }

    public void setIs_damage_str(boolean is_damage_str) {
        this.is_damage_str = is_damage_str;
    }

    public boolean is_damage_rating() {
        return is_damage_rating;
    }

    public void setIs_damage_rating(boolean is_damage_rating) {
        this.is_damage_rating = is_damage_rating;
    }

    public ArrayList<Integer> getDamage_list() {
        return damage_list;
    }

    public void setDamage_list(ArrayList<Integer> damage_list) {
        this.damage_list = damage_list;
    }

    public ArrayList<Character> getDamage_type_list() {
        return damage_type_list;
    }

    public void setDamage_type_list(ArrayList<Character> damage_type_list) {
        this.damage_type_list = damage_type_list;
    }

    public boolean is_damage_electric() {
        return is_damage_electric;
    }

    public void setIs_damage_electric(boolean is_damage_electric) {
        this.is_damage_electric = is_damage_electric;
    }

    public boolean is_damage_flechette() {
        return is_damage_flechette;
    }

    public void setIs_damage_flechette(boolean is_damage_flechette) {
        this.is_damage_flechette = is_damage_flechette;
    }

    public ArrayList<Integer> getAp_list() {
        return ap_list;
    }

    public void setAp_list(ArrayList<Integer> ap_list) {
        this.ap_list = ap_list;
    }

    public ArrayList<String> getMode_list() {
        return mode_list;
    }

    public void setMode_list(ArrayList<String> mode_list) {
        this.mode_list = mode_list;
    }

    public ArrayList<Integer> getRecoil_list() {
        return recoil_list;
    }

    public void setRecoil_list(ArrayList<Integer> recoil_list) {
        this.recoil_list = recoil_list;
    }

    public ArrayList<Integer> getAmmo_list() {
        return ammo_list;
    }

    public void setAmmo_list(ArrayList<Integer> ammo_list) {
        this.ammo_list = ammo_list;
    }

    public ArrayList<String> getAmmo_type_list() {
        return ammo_type_list;
    }

    public void setAmmo_type_list(ArrayList<String> ammo_type_list) {
        this.ammo_type_list = ammo_type_list;
    }

    @Override
    public int compareTo(Weapon another) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        //this optimization is usually worthwhile, and can always be added
        if (this == another) return EQUAL;

        if (this.getType() == null && another.getType() != null) {
            return BEFORE;
        } else if (this.getType() != null && another.getType() == null) {
            return AFTER;
        } else if (this.getType() != null && another.getType() != null) {
            return this.getType().compareTo(another.getType());
        }

        return this.getType().compareTo(another.getType());
    }

    public String getAmmo_selected() {
        return ammo_selected;
    }

    public void setAmmo_selected(String ammo_selected) {
        this.ammo_selected = ammo_selected;
    }

    public int getCost() {
        int totalCost = 0;

        if (topWeaponAccessory != null) {
            totalCost += topWeaponAccessory.getCost();
        }

        if (barrelWeaponAccessory != null) {
            totalCost += barrelWeaponAccessory.getCost();
        }

        if (bottomWeaponAccessory != null) {
            totalCost += bottomWeaponAccessory.getCost();
        }

        if (otherWeaponAccessories != null) {
            for (WeaponAccessory temp : otherWeaponAccessories) {
                totalCost += temp.getCost();
            }
        }

        return super.getCost() + totalCost;
    }
}