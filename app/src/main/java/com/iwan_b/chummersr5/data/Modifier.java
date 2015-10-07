package com.iwan_b.chummersr5.data;

import java.io.Serializable;

public class Modifier implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6680199387371957916L;

    // Amount to change. E.g. +2 for off_Hand
    private float amount;

    // Name of the modifier to find internally
    private String name;

    // The text that will be displayed
    private String displayText;

    // Which book is it in
    private String book;

    // What page to find the reference
    private String page;

    // Summary of what the modifier is or does
    private String summary;

    public Modifier() {
        super();
    }

    public Modifier(final Modifier copyModifier) {
        super();
        this.amount = copyModifier.amount;
        this.name = copyModifier.name;
        this.displayText = copyModifier.displayText;
        this.book = copyModifier.book;
        this.page = copyModifier.page;
        this.summary = copyModifier.summary;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(final float amount) {
        this.amount = amount;
    }

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Modifiers [amount=" + amount + ", name=" + name + ", displayText=" + displayText + ", book=" + book
                + ", page=" + page + ", summary=" + summary + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(amount);
        result = prime * result + ((book == null) ? 0 : book.hashCode());
        result = prime * result + ((displayText == null) ? 0 : displayText.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
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
        Modifier other = (Modifier) obj;
        if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
            return false;
        if (book == null) {
            if (other.book != null)
                return false;
        } else if (!book.equals(other.book))
            return false;
        if (displayText == null) {
            if (other.displayText != null)
                return false;
        } else if (!displayText.equals(other.displayText))
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
        if (summary == null) {
            if (other.summary != null)
                return false;
        } else if (!summary.equals(other.summary))
            return false;
        return true;
    }
}