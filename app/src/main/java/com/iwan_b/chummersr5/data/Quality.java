package com.iwan_b.chummersr5.data;

import java.util.ArrayList;

public class Quality extends GeneralInfo {
    // How much the quality costs
    private int cost = 0;
    // The list of all possible costs for the quality
    private String costList;
    // The name for each costList item.
    private String costListData;

    // What is the current rating of the quality
    private int rating = 1;
    // The max rating they can get in this quality
    private int maxRating = 1;

    // Whether to let the user input something
    private boolean userTextInput = false;
    // The string the user inputs
    private String userTextInputString;

    // Which list to display if they chose a list. e.g. Attributes.xml
    private String list;
    // Spinner Item data list
    private String spinnerListDataDisplay;
    // The spinner item they have selected
    private String spinnerItem;

    // The modifiers that are associated with this quality
    private ArrayList<Modifier> mods;

    // E.g. Exceptional Attribute (Body)
    private String additionalName;

    // Whether only magic can take this quality
    private Boolean magicOnly = false;
    // Whether only technomancers can take this quality
    private Boolean technomancerOnly = false;
    // Whether only mundane characters can take this quality
    private Boolean mundaneOnly = false;

    public Quality(final Quality copy) {
        super(copy);
        this.cost = copy.cost;
        this.costList = copy.costList;
        this.costListData = copy.costListData;
        this.rating = copy.rating;
        this.maxRating = copy.maxRating;
        this.userTextInput = copy.userTextInput;
        this.userTextInputString = copy.userTextInputString;
        this.list = copy.list;
        this.spinnerListDataDisplay = copy.spinnerListDataDisplay;
        this.spinnerItem = copy.spinnerItem;
        this.additionalName = copy.additionalName;
        this.magicOnly = copy.magicOnly;
        this.technomancerOnly = copy.technomancerOnly;
        this.mundaneOnly = copy.mundaneOnly;

        this.mods = new ArrayList<>(copy.mods.size());

        for (Modifier m : copy.mods) {
            this.mods.add(new Modifier(m));
        }

    }

    public Quality() {

    }

    public final String getAdditionalName() {
        return additionalName;
    }

    public final void setAdditionalName(final String additionalName) {
        this.additionalName = additionalName;
    }

    public final int getCost() {
        return cost;
    }

    public final void setCost(final int cost) {
        this.cost = cost;
    }

    public String getCostList() {
        return costList;
    }

    public void setCostList(final String costList) {
        this.costList = costList;
    }

    public String getCostListData() {
        return costListData;
    }

    public void setCostListData(final String costListData) {
        this.costListData = costListData;
    }

    public String getList() {
        return list;
    }

    public void setList(final String list) {
        this.list = list;
    }

    public final Boolean getMagicOnly() {
        return magicOnly;
    }

    public final void setMagicOnly(final Boolean magicOnly) {
        this.magicOnly = magicOnly;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(final int maxRating) {
        this.maxRating = maxRating;
    }

    public ArrayList<Modifier> getMods() {
        return mods;
    }

    public void setMods(final ArrayList<Modifier> mods) {
        this.mods = mods;
    }

    public Boolean getMundaneOnly() {
        return mundaneOnly;
    }

    public void setMundaneOnly(final Boolean mundaneOnly) {
        this.mundaneOnly = mundaneOnly;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public String getSpinnerItem() {
        return spinnerItem;
    }

    public void setSpinnerItem(final String spinnerItem) {
        this.spinnerItem = spinnerItem;
    }

    public String getSpinnerListDataDisplay() {
        return spinnerListDataDisplay;
    }

    public void setSpinnerListDataDisplay(final String spinnerListData) {
        this.spinnerListDataDisplay = spinnerListData;
    }

    public Boolean getTechnomancerOnly() {
        return technomancerOnly;
    }

    public void setTechnomancerOnly(final Boolean technomancerOnly) {
        this.technomancerOnly = technomancerOnly;
    }

    public final String getUserTextInputString() {
        return userTextInputString;
    }

    public final void setUserTextInputString(final String userTextInputString) {
        this.userTextInputString = userTextInputString;
    }

    public final boolean isUserTextInput() {
        return userTextInput;
    }

    public final void setUserTextInput(final boolean userTextInput) {
        this.userTextInput = userTextInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quality)) return false;
        if (!super.equals(o)) return false;

        Quality quality = (Quality) o;

        if (getCost() != quality.getCost()) return false;
        if (getRating() != quality.getRating()) return false;
        if (getMaxRating() != quality.getMaxRating()) return false;
        if (isUserTextInput() != quality.isUserTextInput()) return false;
        if (getCostList() != null ? !getCostList().equals(quality.getCostList()) : quality.getCostList() != null)
            return false;
        if (getCostListData() != null ? !getCostListData().equals(quality.getCostListData()) : quality.getCostListData() != null)
            return false;
        if (getUserTextInputString() != null ? !getUserTextInputString().equals(quality.getUserTextInputString()) : quality.getUserTextInputString() != null)
            return false;
        if (getList() != null ? !getList().equals(quality.getList()) : quality.getList() != null)
            return false;
        if (getSpinnerListDataDisplay() != null ? !getSpinnerListDataDisplay().equals(quality.getSpinnerListDataDisplay()) : quality.getSpinnerListDataDisplay() != null)
            return false;
        if (getSpinnerItem() != null ? !getSpinnerItem().equals(quality.getSpinnerItem()) : quality.getSpinnerItem() != null)
            return false;
        if (getMods() != null ? !getMods().equals(quality.getMods()) : quality.getMods() != null)
            return false;
        if (getAdditionalName() != null ? !getAdditionalName().equals(quality.getAdditionalName()) : quality.getAdditionalName() != null)
            return false;
        if (getMagicOnly() != null ? !getMagicOnly().equals(quality.getMagicOnly()) : quality.getMagicOnly() != null)
            return false;
        if (getTechnomancerOnly() != null ? !getTechnomancerOnly().equals(quality.getTechnomancerOnly()) : quality.getTechnomancerOnly() != null)
            return false;
        return !(getMundaneOnly() != null ? !getMundaneOnly().equals(quality.getMundaneOnly()) : quality.getMundaneOnly() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getCost();
        result = 31 * result + (getCostList() != null ? getCostList().hashCode() : 0);
        result = 31 * result + (getCostListData() != null ? getCostListData().hashCode() : 0);
        result = 31 * result + getRating();
        result = 31 * result + getMaxRating();
        result = 31 * result + (isUserTextInput() ? 1 : 0);
        result = 31 * result + (getUserTextInputString() != null ? getUserTextInputString().hashCode() : 0);
        result = 31 * result + (getList() != null ? getList().hashCode() : 0);
        result = 31 * result + (getSpinnerListDataDisplay() != null ? getSpinnerListDataDisplay().hashCode() : 0);
        result = 31 * result + (getSpinnerItem() != null ? getSpinnerItem().hashCode() : 0);
        result = 31 * result + (getMods() != null ? getMods().hashCode() : 0);
        result = 31 * result + (getAdditionalName() != null ? getAdditionalName().hashCode() : 0);
        result = 31 * result + (getMagicOnly() != null ? getMagicOnly().hashCode() : 0);
        result = 31 * result + (getTechnomancerOnly() != null ? getTechnomancerOnly().hashCode() : 0);
        result = 31 * result + (getMundaneOnly() != null ? getMundaneOnly().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "Quality{" +
                "cost=" + cost +
                ", costList='" + costList + '\'' +
                ", costListData='" + costListData + '\'' +
                ", rating=" + rating +
                ", maxRating=" + maxRating +
                ", userTextInput=" + userTextInput +
                ", userTextInputString='" + userTextInputString + '\'' +
                ", list='" + list + '\'' +
                ", spinnerListDataDisplay='" + spinnerListDataDisplay + '\'' +
                ", spinnerItem='" + spinnerItem + '\'' +
                ", mods=" + mods +
                ", additionalName='" + additionalName + '\'' +
                ", magicOnly=" + magicOnly +
                ", technomancerOnly=" + technomancerOnly +
                ", mundaneOnly=" + mundaneOnly +
                '}';
    }
}