package com.iwan_b.chummersr5.data;


public class GeneralInfo {
    private String name;

    private String summary;
    private String book;
    private String page;

    GeneralInfo(GeneralInfo copy) {
        this.name = copy.name;
        this.summary = copy.summary;
        this.book = copy.book;
        this.page = copy.page;
    }

    public GeneralInfo() {
    }

    @Override
    public String toString() {
        return "GeneralInfo{" +
                "name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", book='" + book + '\'' +
                ", page='" + page + '\'' +
                "}\t";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneralInfo)) return false;

        GeneralInfo that = (GeneralInfo) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
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
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        return result;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

}