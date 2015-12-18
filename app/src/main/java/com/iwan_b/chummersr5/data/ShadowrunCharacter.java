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
    private ChummerConstants.userType userType;
    private ArrayList<Spell> spells;
    private ArrayList<Ritual> rituals;
    private ArrayList<Spell> alchemyFormulas;
    private ArrayList<AdeptPower> adeptPowers;
    private Map<String, ArrayList<Modifier>> modifiers;

    private ShadowrunCharacter() {
        modifiers = new HashMap<>();
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

    public ArrayList<AdeptPower> getAdeptPowers() {
        return adeptPowers;
    }

    public void setAdeptPowers(ArrayList<AdeptPower> adeptPowers) {
        this.adeptPowers = adeptPowers;
    }

    public ArrayList<Spell> getAlchemyFormulas() {
        return alchemyFormulas;
    }

    public void setAlchemyFormulas(ArrayList<Spell> alchemyFormulas) {
        this.alchemyFormulas = alchemyFormulas;
    }

    public ArrayList<Ritual> getRituals() {
        return rituals;
    }

    public void setRituals(ArrayList<Ritual> rituals) {
        this.rituals = rituals;
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

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(final ArrayList<Spell> spells) {
        this.spells = spells;
    }

    @Override
    public String toString() {
        return "ShadowrunCharacter{" +
                "attributes=" + attributes +
                ", positiveQualities=" + positiveQualities +
                ", negativeQualities=" + negativeQualities +
                ", userType=" + userType +
                ", spells=" + spells +
                ", rituals=" + rituals +
                ", alchemyFormulas=" + alchemyFormulas +
                ", adeptPowers=" + adeptPowers +
                ", modifiers=" + modifiers +
                '}';
    }

    public ChummerConstants.userType getUserType() {
        return userType;
    }

    public void setUserType(ChummerConstants.userType userType) {
        this.userType = userType;
    }
}