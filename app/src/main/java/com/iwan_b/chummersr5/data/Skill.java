package com.iwan_b.chummersr5.data;

import java.util.ArrayList;

public class Skill implements Comparable<Skill>{
    private String skillName;
    private int rating;
    // The string the user inputs
    private String userTextInputString;

    // What attribute is used
    private String attrName;

    // What group the skill is a part of
    private String groupName;

    // A list of specializations
    private ArrayList<String> spec;

    // Selected specialization
    private ArrayList<String> selectedSpec;

    // Defaultable
    private boolean isDefaultable;

    // Which book is it in
    private String book;

    // What page to find the reference
    private String page;

    // Summary of the skill
    private String summary;

    // Whether only magic can take this quality
    private Boolean magicOnly = false;
    // Whether only technomancers can take this quality
    private Boolean technomancerOnly = false;

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + skillName + '\'' +
                ", rating=" + rating +
                ", userTextInputString='" + userTextInputString + '\'' +
                ", attrName='" + attrName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", spec=" + spec +
                ", selectedSpec=" + selectedSpec +
                ", isDefaultable=" + isDefaultable +
                ", book='" + book + '\'' +
                ", page='" + page + '\'' +
                ", summary='" + summary + '\'' +
                ", magicOnly=" + magicOnly +
                ", technomancerOnly=" + technomancerOnly +
                '}';
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(final String skillName) {
        this.skillName = skillName;
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

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public ArrayList<String> getSpec() {
        return spec;
    }

    public void setSpec(ArrayList<String> spec) {
        this.spec = spec;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getSelectedSpec() {
        return selectedSpec;
    }

    public void setSelectedSpec(ArrayList<String> selectedSpec) {
        this.selectedSpec = selectedSpec;
    }

    public boolean isDefaultable() {
        return isDefaultable;
    }

    public void setIsDefaultable(boolean isDefaultable) {
        this.isDefaultable = isDefaultable;
    }

    // TODO make this compareTo More thorough
    @Override
    public int compareTo(Skill another) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        //this optimization is usually worthwhile, and can always be added
        if (this == another) return EQUAL;

        if(this.groupName == null && another.getGroupName() != null){
            return BEFORE;
        } else if(this.groupName != null && another.getGroupName() == null){
            return AFTER;
        } else if(this.groupName != null && another.getGroupName() != null){
            return this.groupName.compareTo(another.getGroupName());
        }

        return this.skillName.compareTo(another.getSkillName());
    }
}