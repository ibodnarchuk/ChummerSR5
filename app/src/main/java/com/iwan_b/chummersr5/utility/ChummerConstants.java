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

    public enum tableLayout{
        title,attr,sub,lvl,add,extra,spinner
    }

    public enum extra{
        spec
    }

    public enum counters{
        activeSkills,activeGroupSkills,knowledgeSkills,languageSkills
    }
}