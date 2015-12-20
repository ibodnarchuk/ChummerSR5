package com.iwan_b.chummersr5.data;

public class ComplexForm {
    private String name;
    private String target;
    private String duration;
    private int fading = 0;
    private String list;
    private String summary;
    private String book;
    private String page;

    public ComplexForm() {
    }

    public ComplexForm(ComplexForm copy) {
        this.name = copy.name;
        this.target = copy.target;
        this.duration = copy.duration;
        this.fading = copy.fading;
        this.list = copy.list;
        this.summary = copy.summary;
        this.book = copy.book;
        this.page = copy.page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexForm that = (ComplexForm) o;

        if (getFading() != that.getFading()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getTarget() != null ? !getTarget().equals(that.getTarget()) : that.getTarget() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(that.getDuration()) : that.getDuration() != null)
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
        result = 31 * result + (getTarget() != null ? getTarget().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + getFading();
        result = 31 * result + (getList() != null ? getList().hashCode() : 0);
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComplexForm{" +
                "name='" + name + '\'' +
                ", target='" + target + '\'' +
                ", duration='" + duration + '\'' +
                ", fading=" + fading +
                ", list='" + list + '\'' +
                ", summary='" + summary + '\'' +
                ", book='" + book + '\'' +
                ", page='" + page + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getFading() {
        return fading;
    }

    public void setFading(int fading) {
        this.fading = fading;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}