package com.example.counterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);



            //This is so that interval only accepts a number input
            //----------------------------------------------------------------------
            EditTextPreference numberPreference = findPreference("interval_string_key");

            if (numberPreference != null) {
                numberPreference.setOnBindEditTextListener(
                        new EditTextPreference.OnBindEditTextListener() {
                            @Override
                            public void onBindEditText(@NonNull EditText editText) {
                                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                        });
            }
            //----------------------------------------------------------------------

        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        //I had to load the saved settings on back press because for some reason it only really carries over to the Main Activity if i press the top back button
        //Now it also works with the bottom back button
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        MainActivity.toggleVibration = prefs.getBoolean("toggle_vibration", true); //the key and the default value
        MainActivity.vlength = prefs.getInt("vibration_length", 50);
        MainActivity.toggleTriple = prefs.getBoolean("toggle_triple_key", false);

        MainActivity.interval_string = prefs.getString("interval_string_key", "10");
       
        //Toast.makeText(this, "Will triple vibrate every "+MainActivity.interval+" count intervals", Toast.LENGTH_SHORT).show();

        if(MainActivity.oldInterval != Integer.parseInt(MainActivity.interval_string))
        {
            //This interval will need to be stored
            MainActivity.intervalStored = false;
            //Update current interval with new interval
            MainActivity.interval = Integer.parseInt(MainActivity.interval_string);
            Toast.makeText(this, "Interval has changed, it is now "+MainActivity.interval, Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this, "Current triple state is "+MainActivity.toggleTriple, Toast.LENGTH_SHORT).show();

    }
}