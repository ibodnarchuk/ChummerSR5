package com.iwan_b.chummersr5.utility;

import android.app.Activity;
import android.widget.Toast;

public class ChummerMethods {

    public final static void displayToast(final Activity activity, final String message) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}