package com.iwan_b.chummersr5.data;

public class StreetGear extends GeneralInfo {
    private String type;

    private int avail = 0;
    private boolean is_avail_rating = false;
    private int avail_mult_by = 1;
    private int avail_add_by = 0;
    private String avail_type;

    private int cost = 0;
    private boolean is_cost_rating_mult = false;

    private int rating = 0;
    private int minRating = 0;
    private int maxRating = 0;

    public StreetGear() {
    }

    public StreetGear(StreetGear copy) {
        super(copy);
        this.type = copy.type;
        this.avail = copy.avail;
        this.is_avail_rating = copy.is_avail_rating;
        this.avail_mult_by = copy.avail_mult_by;
        this.avail_add_by = copy.avail_add_by;
        this.avail_type = copy.avail_type;
        this.cost = copy.cost;
        this.is_cost_rating_mult = copy.is_cost_rating_mult;
        this.rating = copy.rating;
        this.minRating = copy.minRating;
        this.maxRating = copy.maxRating;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreetGear)) return false;
        if (!super.equals(o)) return false;

        StreetGear that = (StreetGear) o;

        if (getAvail() != that.getAvail()) return false;
        if (is_avail_rating != that.is_avail_rating) return false;
        if (getAvail_mult_by() != that.getAvail_mult_by()) return false;
        if (getAvail_add_by() != that.getAvail_add_by()) return false;
        if (getCost() != that.getCost()) return false;
        if (is_cost_rating_mult != that.is_cost_rating_mult) return false;
        if (getRating() != that.getRating()) return false;
        if (getMinRating() != that.getMinRating()) return false;
        if (getMaxRating() != that.getMaxRating()) return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null)
            return false;
        return !(getAvail_type() != null ? !getAvail_type().equals(that.getAvail_type()) : that.getAvail_type() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + getAvail();
        result = 31 * result + (is_avail_rating ? 1 : 0);
        result = 31 * result + getAvail_mult_by();
        result = 31 * result + getAvail_add_by();
        result = 31 * result + (getAvail_type() != null ? getAvail_type().hashCode() : 0);
        result = 31 * result + getCost();
        result = 31 * result + (is_cost_rating_mult ? 1 : 0);
        result = 31 * result + getRating();
        result = 31 * result + getMinRating();
        result = 31 * result + getMaxRating();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "StreetGear{" +
                "type='" + type + '\'' +
                ", avail=" + avail +
                ", is_avail_rating=" + is_avail_rating +
                ", avail_mult_by=" + avail_mult_by +
                ", avail_add_by=" + avail_add_by +
                ", avail_type='" + avail_type + '\'' +
                ", cost=" + cost +
                ", is_cost_rating_mult=" + is_cost_rating_mult +
                ", rating=" + rating +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                "} ";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public boolean is_avail_rating() {
        return is_avail_rating;
    }

    public void setIs_avail_rating(boolean is_avail_rating) {
        this.is_avail_rating = is_avail_rating;
    }

    public int getAvail_mult_by() {
        return avail_mult_by;
    }

    public void setAvail_mult_by(int avail_mult_by) {
        this.avail_mult_by = avail_mult_by;
    }

    public int getAvail_add_by() {
        return avail_add_by;
    }

    public void setAvail_add_by(int avail_add_by) {
        this.avail_add_by = avail_add_by;
    }

    public String getAvail_type() {
        return avail_type;
    }

    public void setAvail_type(String avail_type) {
        this.avail_type = avail_type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean is_cost_rating_mult() {
        return is_cost_rating_mult;
    }

    public void setIs_cost_rating_mult(boolean is_cost_rating_mult) {
        this.is_cost_rating_mult = is_cost_rating_mult;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }
}