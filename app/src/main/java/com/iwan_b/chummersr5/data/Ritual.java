package com.iwan_b.chummersr5.data;


public class Ritual {
    private String name;
    private String category;
    // Which book is it in
    private String book;
    // What page to find the reference
    private String page;
    // Summary of the skill
    private String summary;

    public Ritual() {
    }

    public Ritual(Ritual copy) {
        this.name = copy.name;
        this.category = copy.category;
        this.book = copy.book;
        this.page = copy.page;
        this.summary = copy.summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ritual ritual = (Ritual) o;

        if (getName() != null ? !getName().equals(ritual.getName()) : ritual.getName() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(ritual.getCategory()) : ritual.getCategory() != null)
            return false;
        if (getBook() != null ? !getBook().equals(ritual.getBook()) : ritual.getBook() != null)
            return false;
        if (getPage() != null ? !getPage().equals(ritual.getPage()) : ritual.getPage() != null)
            return false;
        return !(getSummary() != null ? !getSummary().equals(ritual.getSummary()) : ritual.getSummary() != null);

    }

    @Override
    public String toString() {
        return "Ritual{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", book='" + book + '\'' +
                ", page='" + page + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}