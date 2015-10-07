package com.iwan_b.chummersr5.data;

import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShadowrunCharacter implements Serializable {
    private static final long serialVersionUID = 6986907913132497369L;

    private static ShadowrunCharacter charInstance;

    private static int karma = ChummerConstants.startingKarma;

    private Attribute attributes;
    private ArrayList<Quality> positiveQualities;
    private ArrayList<Quality> negativeQualities;

    private Map<String, ArrayList<Modifier>> modifiers;

    private ShadowrunCharacter() {
        modifiers = new HashMap<String, ArrayList<Modifier>>();
        setPositiveQualities(new ArrayList<Quality>());
        setNegativeQualities(new ArrayList<Quality>());
    }

    public static ShadowrunCharacter getCharacter() {
        if (charInstance == null) {
            charInstance = new ShadowrunCharacter();
        }

        return charInstance;
    }

    public static int getKarma() {
        return karma;
    }

    public static void setKarma(final int karma) {
        ShadowrunCharacter.karma = karma;
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(final Attribute attributes) {
        this.attributes = attributes;
    }

    public Map<String, ArrayList<Modifier>> getModifiers() {
        return modifiers;
    }

    public void setModifiers(final Map<String, ArrayList<Modifier>> modifiers) {
        this.modifiers = modifiers;
    }

    public ArrayList<Quality> getNegativeQualities() {
        return negativeQualities;
    }

    public void setNegativeQualities(final ArrayList<Quality> negativeQualities) {
        this.negativeQualities = negativeQualities;
    }

    public ArrayList<Quality> getPositiveQualities() {
        return positiveQualities;
    }

    public void setPositiveQualities(final ArrayList<Quality> positiveQualities) {
        this.positiveQualities = positiveQualities;
    }

    @Override
    public String toString() {
        return "ShadowrunCharacter [attributes=" + attributes + ", positiveQualities=" + positiveQualities
                + ", negativeQualities=" + negativeQualities + ", modifiers=" + modifiers + "]";
    }

}