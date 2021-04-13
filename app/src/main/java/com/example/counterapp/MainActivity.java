package com.example.counterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Set id's and references of textviews
    //TextView resultTextView;
    private TextView resultTextView;

    //Create a variable called result to store value of result
    int result = 0;

    //variable responsible for vibration length
    static int vlength = 50;

    //Variable to know if fullscreen mode is currently in use
    boolean isFullScreen = false;

    //variable to know if we need to show fullscreen dialog
    boolean showDialog = true;

    static boolean toggleVibration = true;
    static boolean toggleTriple = false;
    static String interval_string = "10";
    static int interval;


    //initial delay, vibrate, delay, vibrate, delay, vibrate
    long[] mVibratePattern = new long[]{0, 200, 150, 200, 150, 200};







    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //the result text view
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        //Create a vibration object
        final Vibrator vibrateobj = (Vibrator)getSystemService(VIBRATOR_SERVICE);



        //Create an onTouch Listener (the onclick listener was not responsive enough)
        resultTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){


                    if (toggleVibration == true)
                    {
                        vibrateobj.vibrate(vlength);
                    }


                    //Increment the result value
                    result++;

                    if(result == interval)
                    {
                        vibrateobj.vibrate(mVibratePattern, -1);
                    }


                    //Display the result value
                    resultTextView.setText(result+"");

                    //save the data, incase the app is closed
                    SharedPrefConfig.saveTotalInPref(getApplicationContext(), result);
                }
                return false;
            }
        });


        //If we have a saved instance state, then we'll use that (in the case when ex: orientation changes)
        if (savedInstanceState != null) {
            //if savedInstanceState is not null then we'll take that value and set the text to that value
            result = savedInstanceState.getInt("result");
            resultTextView.setText(result+"");
        }


        //load data
        //If something was saved in shared preferences load it into the result
        result = SharedPrefConfig.loadTotalFromPref(this);
        //display this value
        resultTextView.setText(result+"");



        //Get any data from the settingsActivity page that was saved on that activity automatically
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        toggleVibration = prefs.getBoolean("toggle_vibration", true); //the key and the default value
        vlength = prefs.getInt("vibration_length", 50);
        toggleTriple = prefs.getBoolean("toggle_triple_key", false);
        interval_string = prefs.getString("interval", "10");

        //convert from string to integer
        interval = Integer.parseInt(interval_string);





        //for testing purposes
        Toast.makeText(this, "Current interval is "+interval_string, Toast.LENGTH_SHORT).show();



    }



    //When the user presses the back button on the android navigation bar
    @Override
    public void onBackPressed() {
        if(isFullScreen == true) {
            showSystemUI();
        }
        else {
            //Called from the parent class, this will do normal behaviour of back (exit app)
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.my_menu, menu);
        return true;
    }

    //What each button will do when pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        switch(item.getItemId()){

            //decrementing the counter
            case R.id.decrementItem:
                //if already 0 show message "Already 0"
                if(result == 0) {
                    Toast.makeText(this, "Already 0", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //else decrement the counter
                else {
                    Toast.makeText(this, "Minus 1", Toast.LENGTH_SHORT).show();
                    result--;
                    resultTextView.setText(result+"");
                    //Save result in shared preferences
                    SharedPrefConfig.saveTotalInPref(getApplicationContext(), result);
                    return true;
                }

            //Resetting the counter
            case R.id.resetItem:
                //if already 0 show message "Already 0"
                if(result == 0){
                    Toast.makeText(this, "Already 0", Toast.LENGTH_SHORT).show();
                    return true;
                }
                //else reset the counter
                else {
                    Toast.makeText(this, "Counter Reset", Toast.LENGTH_SHORT).show();
                    result = 0;
                    resultTextView.setText(result + "");
                    //Save result in shared preferences
                    SharedPrefConfig.saveTotalInPref(getApplicationContext(), result);
                    return true;
                }
            //Going fullscreen
            case R.id.fullScreenItem:
                //do something
                setFullScreen();
                return true;

            //Settings page
            case R.id.settingsItem:
                //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                openSettingActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Save values in the app when orientation changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Values we want to save when orientation changes
        //Result will be saved in the outside bundle
        outState.putInt("result", result);
    }

    //method to set full screen mode
    public void setFullScreen(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        isFullScreen = true;

        //load data
        //If something was saved in shared preferences load it into the showDialog
        showDialog = SharedPrefConfig.loadNevershowFromPref(this);

        //Show the dialog message
        if(showDialog == true){
            //I think we need to load first then check
            showDialog();
        }


    }

    //method to exit fullscreen mode
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        isFullScreen = false;
    }

    //method to show dialogue when you enter fullscreen mode
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder
                .setCancelable(false) //so that user cannot back out of the message without answering it

                .setMessage("Swipe up from bottom or swipe down from the top, then press back to exit fullscreen mode.")

                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();

                       }
                   })

                .setNeutralButton("NEVER SHOW", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //save the data, incase the app is closed
                        SharedPrefConfig.saveNevershowInPref(getApplicationContext(), showDialog = false);
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false); //dialog cannot be canceled by touching outside the window's bounds
        alertDialog.show();
    }

    //method the open the settings activity
    public void openSettingActivity(){
        //create an intent object, parameter (this context, the activity we want to open)
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


}
