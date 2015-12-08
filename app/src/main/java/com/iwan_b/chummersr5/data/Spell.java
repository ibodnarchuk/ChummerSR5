package com.iwan_b.chummersr5.data;

public class Spell {
    private String name;
    private String category;
    private String type;
    private String range;
    private String damage;
    private String duration;
    private int drain;
    // The string the user inputs
    private String userTextInputString;
    // Which book is it in
    private String book;
    // What page to find the reference
    private String page;
    // Summary of the skill
    private String summary;

    public Spell(Spell s) {
        this.name = s.name;
        this.category = s.category;
        this.type = s.type;
        this.range = s.range;
        this.damage = s.damage;
        this.duration = s.duration;
        this.drain = s.drain;
        this.book = s.book;
        this.page = s.page;
        this.summary = s.summary;
        this.userTextInputString = s.userTextInputString;
    }

    public Spell() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public int getDrain() {
        return drain;
    }

    public void setDrain(int drain) {
        this.drain = drain;
    }

    @Override
    public String toString() {
        return "Spell{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", range='" + range + '\'' +
                ", damage='" + damage + '\'' +
                ", duration='" + duration + '\'' +
                ", drain=" + drain +
                ", userTextInputString='" + userTextInputString + '\'' +
                ", book='" + book + '\'' +
                ", page='" + page + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    public String getUserTextInputString() {
        return userTextInputString;
    }

    public void setUserTextInputString(String userTextInputString) {
        this.userTextInputString = userTextInputString;
    }
}
