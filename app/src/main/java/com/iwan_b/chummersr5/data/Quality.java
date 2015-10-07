package com.iwan_b.chummersr5.data;

import java.util.ArrayList;

public class Quality {
    // Name of the quality
    private String name;

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

    // Which book is it in
    private String book;

    // What page to find the reference
    private String page;

    // Summary of the quality
    private String summary;

    // Whether only magic can take this quality
    private Boolean magicOnly = false;
    // Whether only technomancers can take this quality
    private Boolean technomancerOnly = false;
    // Whether only mundane characters can take this quality
    private Boolean mundaneOnly = false;

    public Quality() {
        super();
    }

    public Quality(final Quality copyQuality) {
        super();
        this.name = copyQuality.name;
        this.cost = copyQuality.cost;
        this.costList = copyQuality.costList;
        this.costListData = copyQuality.costListData;
        this.rating = copyQuality.rating;
        this.maxRating = copyQuality.maxRating;
        this.userTextInput = copyQuality.userTextInput;
        this.userTextInputString = copyQuality.userTextInputString;
        this.list = copyQuality.list;
        this.spinnerListDataDisplay = copyQuality.spinnerListDataDisplay;
        this.spinnerItem = copyQuality.spinnerItem;
        this.additionalName = copyQuality.additionalName;
        this.book = copyQuality.book;
        this.page = copyQuality.page;
        this.summary = copyQuality.summary;
        this.magicOnly = copyQuality.magicOnly;
        this.technomancerOnly = copyQuality.technomancerOnly;
        this.mundaneOnly = copyQuality.mundaneOnly;

        this.mods = new ArrayList<Modifier>(copyQuality.mods.size());

        for (Modifier m : copyQuality.mods) {
            this.mods.add(new Modifier(m));
        }

    }

    public final String getAdditionalName() {
        return additionalName;
    }

    public final void setAdditionalName(final String additionalName) {
        this.additionalName = additionalName;
    }

    public final String getBook() {
        return book;
    }

    public final void setBook(final String book) {
        this.book = book;
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

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getPage() {
        return page;
    }

    public final void setPage(final String page) {
        this.page = page;
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

    public final String getSummary() {
        return summary;
    }

    public final void setSummary(final String summary) {
        this.summary = summary;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((additionalName == null) ? 0 : additionalName.hashCode());
        result = prime * result + ((book == null) ? 0 : book.hashCode());
        result = prime * result + cost;
        result = prime * result + ((costList == null) ? 0 : costList.hashCode());
        result = prime * result + ((costListData == null) ? 0 : costListData.hashCode());
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        result = prime * result + ((magicOnly == null) ? 0 : magicOnly.hashCode());
        result = prime * result + maxRating;
        result = prime * result + ((mods == null) ? 0 : mods.hashCode());
        result = prime * result + ((mundaneOnly == null) ? 0 : mundaneOnly.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + rating;
        result = prime * result + ((spinnerItem == null) ? 0 : spinnerItem.hashCode());
        result = prime * result + ((spinnerListDataDisplay == null) ? 0 : spinnerListDataDisplay.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + ((technomancerOnly == null) ? 0 : technomancerOnly.hashCode());
        result = prime * result + (userTextInput ? 1231 : 1237);
        result = prime * result + ((userTextInputString == null) ? 0 : userTextInputString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quality other = (Quality) obj;
        if (additionalName == null) {
            if (other.additionalName != null)
                return false;
        } else if (!additionalName.equals(other.additionalName))
            return false;
        if (book == null) {
            if (other.book != null)
                return false;
        } else if (!book.equals(other.book))
            return false;
        if (cost != other.cost)
            return false;
        if (costList == null) {
            if (other.costList != null)
                return false;
        } else if (!costList.equals(other.costList))
            return false;
        if (costListData == null) {
            if (other.costListData != null)
                return false;
        } else if (!costListData.equals(other.costListData))
            return false;
        if (list == null) {
            if (other.list != null)
                return false;
        } else if (!list.equals(other.list))
            return false;
        if (magicOnly == null) {
            if (other.magicOnly != null)
                return false;
        } else if (!magicOnly.equals(other.magicOnly))
            return false;
        if (maxRating != other.maxRating)
            return false;
        if (mods == null) {
            if (other.mods != null)
                return false;
        } else if (!mods.equals(other.mods))
            return false;
        if (mundaneOnly == null) {
            if (other.mundaneOnly != null)
                return false;
        } else if (!mundaneOnly.equals(other.mundaneOnly))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (rating != other.rating)
            return false;
        if (spinnerItem == null) {
            if (other.spinnerItem != null)
                return false;
        } else if (!spinnerItem.equals(other.spinnerItem))
            return false;
        if (spinnerListDataDisplay == null) {
            if (other.spinnerListDataDisplay != null)
                return false;
        } else if (!spinnerListDataDisplay.equals(other.spinnerListDataDisplay))
            return false;
        if (summary == null) {
            if (other.summary != null)
                return false;
        } else if (!summary.equals(other.summary))
            return false;
        if (technomancerOnly == null) {
            if (other.technomancerOnly != null)
                return false;
        } else if (!technomancerOnly.equals(other.technomancerOnly))
            return false;
        if (userTextInput != other.userTextInput)
            return false;
        if (userTextInputString == null) {
            if (other.userTextInputString != null)
                return false;
        } else if (!userTextInputString.equals(other.userTextInputString))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Quality [name=" + name + ", cost=" + cost + ", costList=" + costList + ", costListData=" + costListData
                + ", rating=" + rating + ", maxRating=" + maxRating + ", userTextInput=" + userTextInput
                + ", userTextInputString=" + userTextInputString + ", list=" + list + ", spinnerListDataDisplay="
                + spinnerListDataDisplay + ", spinnerItem=" + spinnerItem + ", mods=" + mods + ", additionalName="
                + additionalName + ", book=" + book + ", page=" + page + ", summary=" + summary + ", magicOnly=" + magicOnly
                + ", technomancerOnly=" + technomancerOnly + ", mundaneOnly=" + mundaneOnly + "]";
    }
}