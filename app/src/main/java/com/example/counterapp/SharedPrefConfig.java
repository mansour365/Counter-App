package com.example.counterapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefConfig {

    private static final String MY_PREFERENCE_NAME = "com.example.counterapp";
    private static final String PREF_TOTAL_KEY = "pref_total_key";

    //method to save the total
    public static void saveTotalInPref(Context context, int total){
        //create the shared preference object
        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        //create the editor
        SharedPreferences.Editor editor = sp.edit();

        //Need to save an int type variable
        editor.putInt(PREF_TOTAL_KEY, total);

        editor.apply();
    }

    //method to load the total
    public static int loadTotalFromPref(Context context){
        //create the shared preference object
        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getInt(PREF_TOTAL_KEY, 0);

    }


}
