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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spell)) return false;

        Spell spell = (Spell) o;

        if (getDrain() != spell.getDrain()) return false;
        if (getName() != null ? !getName().equals(spell.getName()) : spell.getName() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(spell.getCategory()) : spell.getCategory() != null)
            return false;
        if (getType() != null ? !getType().equals(spell.getType()) : spell.getType() != null)
            return false;
        if (getRange() != null ? !getRange().equals(spell.getRange()) : spell.getRange() != null)
            return false;
        if (getDamage() != null ? !getDamage().equals(spell.getDamage()) : spell.getDamage() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(spell.getDuration()) : spell.getDuration() != null)
            return false;
        if (getUserTextInputString() != null ? !getUserTextInputString().equals(spell.getUserTextInputString()) : spell.getUserTextInputString() != null)
            return false;
        if (getBook() != null ? !getBook().equals(spell.getBook()) : spell.getBook() != null)
            return false;
        if (getPage() != null ? !getPage().equals(spell.getPage()) : spell.getPage() != null)
            return false;
        return !(getSummary() != null ? !getSummary().equals(spell.getSummary()) : spell.getSummary() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getRange() != null ? getRange().hashCode() : 0);
        result = 31 * result + (getDamage() != null ? getDamage().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + getDrain();
        result = 31 * result + (getUserTextInputString() != null ? getUserTextInputString().hashCode() : 0);
        result = 31 * result + (getBook() != null ? getBook().hashCode() : 0);
        result = 31 * result + (getPage() != null ? getPage().hashCode() : 0);
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        return result;
    }

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
