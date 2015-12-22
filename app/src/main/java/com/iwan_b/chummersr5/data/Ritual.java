package com.iwan_b.chummersr5.data;


public class Ritual extends GeneralInfo {
    private String category;

    public Ritual() {
        super();
    }

    public Ritual(Ritual copy) {
        super(copy);
        this.category = copy.category;
    }

    @Override
    public String toString() {
        return super.toString() + "Ritual{" +
                "category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ritual)) return false;
        if (!super.equals(o)) return false;

        Ritual ritual = (Ritual) o;

        return !(getCategory() != null ? !getCategory().equals(ritual.getCategory()) : ritual.getCategory() != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}