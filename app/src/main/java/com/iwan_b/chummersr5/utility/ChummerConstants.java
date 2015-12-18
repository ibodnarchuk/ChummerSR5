package com.iwan_b.chummersr5.utility;

public class ChummerConstants {
    public final static String TAG = "SR5Chummer";
    public final static String attributeFileLocation = "data/Attributes.xml";
    public final static String skillFileLocation = "data/Skills.xml";

    public final static int startingKarma = 25;
    public final static int maxKarmaUsed = 25;

    public final static int baseStat = 1;
    public final static int maxStat = 6;

    public final static int skillPointUsed = -1;
    public final static int skillGroupPointUsed = -2;
    public final static int freeSkillLevel = -3;
    public final static int freeSkillGroupLevel = -4;
    public final static int attrPointUsed = -5;
    public final static int freeSpellUsed = -6;

    /**
     * What type of character the user has. This is in the specific order to group types.
     * 0 = Mundane
     * 1 = Technomancer
     * 2-3 = Adept powers are accessible
     * 3+ = Spells are available.
     */
    public enum userType {
        mundane, technomancer, adept, mystic_adept, magician, aspected_magician
    }

    public enum tableLayout {
        title, attr, sub, lvl, add, spinner, extra
    }

    public enum extra {
        spec
    }

    public enum counters {
        activeSkills, activeGroupSkills, knowledgeSkills, languageSkills
    }
}