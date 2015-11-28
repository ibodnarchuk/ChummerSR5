package com.iwan_b.chummersr5.utility;

import android.app.Activity;
import android.widget.Toast;

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


}