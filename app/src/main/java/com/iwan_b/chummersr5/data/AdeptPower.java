package com.iwan_b.chummersr5.data;

import java.util.ArrayList;

public class AdeptPower extends GeneralInfo {
    private ArrayList<Float> costList;
    // Whether this power requires an activation
    private boolean activation = false;
    // Whether this power is activated
    private boolean activated;
    // Whether you can buy additional levels in a power
    private boolean singular = false;

    private float cost = 0;
    private int rating = 1;

    // The modifiers that are associated with this Adept Power
    private ArrayList<Modifier> mods;
    private String list;


    public AdeptPower() {
        super();
    }

    public AdeptPower(AdeptPower copy) {
        super(copy);

        this.costList = copy.costList;
        this.activation = copy.activation;
        this.activated = copy.activated;
        this.singular = copy.singular;
        this.mods = copy.mods;
        this.list = copy.list;
        this.rating = copy.rating;
        this.cost = copy.cost;
    }

    @Override
    public String toString() {
        return super.toString() + "AdeptPower{" +
                "costList=" + costList +
                ", activation=" + activation +
                ", activated=" + activated +
                ", singular=" + singular +
                ", cost=" + cost +
                ", rating=" + rating +
                ", mods=" + mods +
                ", list='" + list + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdeptPower that = (AdeptPower) o;

        if (isActivation() != that.isActivation()) return false;
        if (isActivated() != that.isActivated()) return false;
        if (isSingular() != that.isSingular()) return false;
        if (Float.compare(that.getCost(), getCost()) != 0) return false;
        if (getRating() != that.getRating()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getCostList() != null ? !getCostList().equals(that.getCostList()) : that.getCostList() != null)
            return false;
        if (getMods() != null ? !getMods().equals(that.getMods()) : that.getMods() != null)
            return false;
        if (getList() != null ? !getList().equals(that.getList()) : that.getList() != null)
            return false;
        if (getSummary() != null ? !getSummary().equals(that.getSummary()) : that.getSummary() != null)
            return false;
        if (getBook() != null ? !getBook().equals(that.getBook()) : that.getBook() != null)
            return false;
        return !(getPage() != null ? !getPage().equals(that.getPage()) : that.getPage() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCostList() != null ? getCostList().hashCode() : 0);
        result = 31 * result + (isActivation() ? 1 : 0);
        result = 31 * result + (isActivated() ? 1 : 0);
        result = 31 * result + (isSingular() ? 1 : 0);
        result = 31 * result + (getCost() != +0.0f ? Float.floatToIntBits(getCost()) : 0);
        result = 31 * result + getRating();
        result = 31 * result + (getMods() != null ? getMods().hashCode() : 0);
        result = 31 * result + (getList() != null ? getList().hashCode() : 0);
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        return result;
    }

    public ArrayList<Float> getCostList() {
        return costList;
    }

    public void setCostList(ArrayList<Float> costList) {
        this.costList = costList;
    }

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isSingular() {
        return singular;
    }

    public void setSingular(boolean singular) {
        this.singular = singular;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public ArrayList<Modifier> getMods() {
        return mods;
    }

    public void setMods(ArrayList<Modifier> mods) {
        this.mods = mods;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
