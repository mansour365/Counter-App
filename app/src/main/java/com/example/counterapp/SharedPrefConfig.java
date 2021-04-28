package com.example.counterapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefConfig {

    private static final String MY_PREFERENCE_NAME = "com.example.counterapp";
    private static final String PREF_TOTAL_KEY = "pref_total_key";

    private static final String PREF_INTERVAL_KEY="pref_interval_key";

    //shared preference variable for the fullscreen dialog
    private static final String PREF_DIALOGUE_KEY = "pref_dialogue_key";


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

    public static void saveIntervalInPref(Context context, int intervalTotal){

        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(PREF_INTERVAL_KEY, intervalTotal);

        editor.apply();
    }

    public static int loadIntervalFromPref(Context context){

        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getInt(PREF_INTERVAL_KEY, 0);
    }



    //method to save NEVERSHOW in the fullscreen dialogue
    public static void saveNevershowInPref(Context context, boolean showMessage){
        //create the shared preference object
        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        //create editor
        SharedPreferences.Editor editor = sp.edit();

        //Need to save a boolean type variable
        editor.putBoolean(PREF_DIALOGUE_KEY, showMessage);

        editor.apply();
    }

    //method to load the NEVERSHOW in fullscreen dialogue
    public static boolean loadNevershowFromPref(Context context){
        //create the shared preference object
        SharedPreferences sp = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(PREF_DIALOGUE_KEY, true);
    }





}
