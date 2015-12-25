package com.iwan_b.chummersr5.data;

import java.util.ArrayList;

public class Weapon extends StreetGear {
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
    private int ap_divide_by = 1;

    private ArrayList<String> mode_list = new ArrayList<>();

    private ArrayList<Integer> recoil_list = new ArrayList<>();

    private ArrayList<Integer> ammo_list = new ArrayList<>();
    private ArrayList<String> ammo_type_list = new ArrayList<>();

    public Weapon() {
    }

    public Weapon(Weapon copy) {
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
        this.ap_divide_by = copy.ap_divide_by;
        this.mode_list = copy.mode_list;
        this.recoil_list = copy.recoil_list;
        this.ammo_list = copy.ammo_list;
        this.ammo_type_list = copy.ammo_type_list;
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
        if (getAp_divide_by() != weapon.getAp_divide_by()) return false;
        if (getAccuracy_listIntegers() != null ? !getAccuracy_listIntegers().equals(weapon.getAccuracy_listIntegers()) : weapon.getAccuracy_listIntegers() != null)
            return false;
        if (getDamage_list() != null ? !getDamage_list().equals(weapon.getDamage_list()) : weapon.getDamage_list() != null)
            return false;
        if (getDamage_type_list() != null ? !getDamage_type_list().equals(weapon.getDamage_type_list()) : weapon.getDamage_type_list() != null)
            return false;
        if (getAp_list() != null ? !getAp_list().equals(weapon.getAp_list()) : weapon.getAp_list() != null)
            return false;
        if (getMode_list() != null ? !getMode_list().equals(weapon.getMode_list()) : weapon.getMode_list() != null)
            return false;
        if (getRecoil_list() != null ? !getRecoil_list().equals(weapon.getRecoil_list()) : weapon.getRecoil_list() != null)
            return false;
        if (getAmmo_list() != null ? !getAmmo_list().equals(weapon.getAmmo_list()) : weapon.getAmmo_list() != null)
            return false;
        return !(getAmmo_type_list() != null ? !getAmmo_type_list().equals(weapon.getAmmo_type_list()) : weapon.getAmmo_type_list() != null);

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
        result = 31 * result + getAp_divide_by();
        result = 31 * result + (getMode_list() != null ? getMode_list().hashCode() : 0);
        result = 31 * result + (getRecoil_list() != null ? getRecoil_list().hashCode() : 0);
        result = 31 * result + (getAmmo_list() != null ? getAmmo_list().hashCode() : 0);
        result = 31 * result + (getAmmo_type_list() != null ? getAmmo_type_list().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "Weapon{" +
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
                ", ap_divide_by=" + ap_divide_by +
                ", mode_list=" + mode_list +
                ", recoil_list=" + recoil_list +
                ", ammo_list=" + ammo_list +
                ", ammo_type_list=" + ammo_type_list +
                "} ";
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

    public int getAp_divide_by() {
        return ap_divide_by;
    }

    public void setAp_divide_by(int ap_divide_by) {
        this.ap_divide_by = ap_divide_by;
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
}