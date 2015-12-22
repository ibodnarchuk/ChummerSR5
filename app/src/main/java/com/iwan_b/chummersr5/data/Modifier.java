package com.iwan_b.chummersr5.data;

import java.io.Serializable;

public class Modifier extends GeneralInfo implements Serializable {
    private static final long serialVersionUID = 6680199387371957916L;

    // Amount to change. E.g. +2 for off_Hand
    private float amount;

    // The text that will be displayed
    private String displayText;

    public Modifier(final Modifier copy) {
        super(copy);
        this.amount = copy.amount;
        this.displayText = copy.displayText;
    }

    public Modifier() {
        super();
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(final float amount) {
        this.amount = amount;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(final String displayText) {
        this.displayText = displayText;
    }

    @Override
    public String toString() {
        return super.toString() + "Modifier{" +
                "amount=" + amount +
                ", displayText='" + displayText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modifier)) return false;
        if (!super.equals(o)) return false;

        Modifier modifier = (Modifier) o;

        if (Float.compare(modifier.getAmount(), getAmount()) != 0) return false;
        return !(getDisplayText() != null ? !getDisplayText().equals(modifier.getDisplayText()) : modifier.getDisplayText() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAmount() != +0.0f ? Float.floatToIntBits(getAmount()) : 0);
        result = 31 * result + (getDisplayText() != null ? getDisplayText().hashCode() : 0);
        return result;
    }
}