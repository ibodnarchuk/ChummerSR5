package com.iwan_b.chummersr5.data;

/**
 * Used to track free levels that the user can use at chargen
 */
public class FreeCounters {
    private static FreeCounters instance;

    private int freeKnowledgeSkills;
    private int freeActiveSkills;
    private int freeActiveGroupSkills;
    private int freeLanguageSkills;
    private int freeAttributes;
    private int freeSpecAttributes;
    private int freeSpells;
    private float powerPoints;
    private int freeComplexForms;
    private int KarmaUsedForNuyen;

    private FreeCounters() {
    }

    public static FreeCounters getCounters() {
        if (instance == null) {
            instance = new FreeCounters();
        }
        return instance;
    }

    public int getFreeComplexForms() {
        return freeComplexForms;
    }

    public void setFreeComplexForms(int freeComplexForms) {
        this.freeComplexForms = freeComplexForms;
    }

    public int getFreeKnowledgeSkills() {
        return freeKnowledgeSkills;
    }

    public void setFreeKnowledgeSkills(int freeKnowledgeSkills) {
        this.freeKnowledgeSkills = freeKnowledgeSkills;
    }

    public int getFreeActiveSkills() {
        return freeActiveSkills;
    }

    public void setFreeActiveSkills(int freeActiveSkills) {
        this.freeActiveSkills = freeActiveSkills;
    }

    public int getFreeActiveGroupSkills() {
        return freeActiveGroupSkills;
    }

    public void setFreeActiveGroupSkills(int freeActiveGroupSkills) {
        this.freeActiveGroupSkills = freeActiveGroupSkills;
    }

    public int getFreeLanguageSkills() {
        return freeLanguageSkills;
    }

    public void setFreeLanguageSkills(int freeLanguageSkills) {
        this.freeLanguageSkills = freeLanguageSkills;
    }

    public int getFreeAttributes() {
        return freeAttributes;
    }

    public void setFreeAttributes(int freeAttributes) {
        this.freeAttributes = freeAttributes;
    }

    public int getFreeSpecAttributes() {
        return freeSpecAttributes;
    }

    public void setFreeSpecAttributes(int freeSpecAttributes) {
        this.freeSpecAttributes = freeSpecAttributes;
    }

    public int getFreeSpells() {
        return freeSpells;
    }

    public void setFreeSpells(int freeSpells) {
        this.freeSpells = freeSpells;
    }

    public float getPowerPoints() {
        return powerPoints;
    }

    public void setPowerPoints(float powerPoints) {
        this.powerPoints = powerPoints;
    }

    public int getKarmaUsedForNuyen() {
        return KarmaUsedForNuyen;
    }

    public void setKarmaUsedForNuyen(int karmaUsedForNuyen) {
        KarmaUsedForNuyen = karmaUsedForNuyen;
    }
}
