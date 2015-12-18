package com.iwan_b.chummersr5.utility;

import android.app.Activity;
import android.widget.Toast;

import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;

import java.util.ArrayList;

public class ChummerMethods {

    public final static void displayToast(final Activity activity, final String message) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public final static int formulaAttrCost(final int rating) {
        return rating * 5;
    }

    public final static int formulaActiveSkillCost(final int rating) {
        return rating * 2;
    }

    public final static int formulaActiveSkillGroupCost(final int rating) {
        return rating * 5;
    }

    public final static int formulaKnowledgeCost(final int rating) {
        return rating;
    }

    public final static int formulaLanguageCost(final int rating) {
        return rating;
    }

    public final static int formulaFreeKnowledgeSkill(final int intu, final int log) {
        return (intu + log) * 2;
    }

    public final static boolean isCharAdept(ShadowrunCharacter character) {
        return character.getUserType() == ChummerConstants.userType.adept || character.getUserType() == ChummerConstants.userType.mystic_adept;
    }

    public final static boolean isCharMage(ShadowrunCharacter character) {
        return character.getUserType() == ChummerConstants.userType.mystic_adept || character.getUserType() == ChummerConstants.userType.magician ||
                character.getUserType() == ChummerConstants.userType.aspected_magician;
    }

    public final static boolean isCharMagic(ShadowrunCharacter character) {
        return character.getUserType().ordinal() >= ChummerConstants.userType.adept.ordinal();
    }

    public static boolean isCharTechnomancer(ShadowrunCharacter character) {
        return character.getUserType() == ChummerConstants.userType.technomancer;
    }

    /**
     * Adds modifiers to the ShadowrunCharacter. Any duplicate modifiers are
     * added instead of overwritten.
     *
     * @param mods The Arraylist of mods to add
     */
    public final static void addModstoChar(final ArrayList<Modifier> mods, final ShadowrunCharacter character) {
        if (mods != null) {
            for (Modifier m : mods) {
                addModstoChar(m, character);
            }
        }
    }

    public final static void addModstoChar(final Modifier m, final ShadowrunCharacter character) {
        if (character.getModifiers().containsKey(m.getName())) {
            character.getModifiers().get(m.getName()).add(m);
        } else {
            ArrayList<Modifier> temp = new ArrayList<>();
            temp.add(m);
            character.getModifiers().put(m.getName(), temp);
        }
    }
}