package com.iwan_b.chummersr5.data;

public class Skill {
    private String name;
    private int rating;
    // The string the user inputs
    private String userTextInputString;

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Skill [name=" + name + ", rating=" + rating + ", userTextInputString=" + userTextInputString
                + ", book=" + book + ", page=" + page + ", summary=" + summary + ", magicOnly=" + magicOnly
                + ", technomancerOnly=" + technomancerOnly + "]";
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserTextInputString() {
        return userTextInputString;
    }

    public void setUserTextInputString(final String userTextInputString) {
        this.userTextInputString = userTextInputString;
    }

    public String getBook() {
        return book;
    }

    public void setBook(final String book) {
        this.book = book;
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

    public Boolean getMagicOnly() {
        return magicOnly;
    }

    public void setMagicOnly(final Boolean magicOnly) {
        this.magicOnly = magicOnly;
    }

    public Boolean getTechnomancerOnly() {
        return technomancerOnly;
    }

    public void setTechnomancerOnly(final Boolean technomancerOnly) {
        this.technomancerOnly = technomancerOnly;
    }

}