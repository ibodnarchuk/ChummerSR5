package com.iwan_b.chummersr5.data;

import java.io.Serializable;
import java.util.ArrayList;

public class PriorityTable implements Serializable {
    // Used to keep the versions of this class the same
    private static final long serialVersionUID = 6513345960972050093L;

    // The priority level that the information is from.
    private String priority;
    // The text that will be displayed
    private String displayText;
    // The stats for the priority table
    private float stats = 0;
    // The modifiers of the stat
    private ArrayList<Modifier> mods;
    // Summary of the text
    private String summary;
    // What book the info is in
    private String book;
    // what page it is in
    private String page;

    private String metaTypeName;
    private String magicType;

    public String getBook() {
        return book;
    }

    public void setBook(final String book) {
        this.book = book;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(final String displayText) {
        this.displayText = displayText;
    }

    public String getMagicType() {
        return magicType;
    }

    public void setMagicType(String magicType) {
        this.magicType = magicType;
    }

    public String getMetaTypeName() {
        return metaTypeName;
    }

    public void setMetaTypeName(String metaTypeName) {
        this.metaTypeName = metaTypeName;
    }

    public ArrayList<Modifier> getMods() {
        return mods;
    }

    public void setMods(final ArrayList<Modifier> mods) {
        this.mods = mods;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public final String getPriority() {
        return priority;
    }

    public final void setPriority(final String priority) {
        this.priority = priority;
    }

    public float getStats() {
        return stats;
    }

    public void setStats(final float stats) {
        this.stats = stats;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "PriorityTable [priority=" + priority + ", displayText="
                + displayText + ", stats=" + stats + ", mods=" + mods
                + ", summary=" + summary + ", book=" + book + ", page=" + page
                + ", metaTypeName=" + metaTypeName + ", magicType=" + magicType
                + "]";
    }

}