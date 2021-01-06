package com.example.counterapp;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Set id's and references of textviews
    TextView resultTextView;

    //Create a variable called result to store value of result
    int result = 0;

    //variable responsible for vibration length
    int vlength = 50;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Create a addBtn Button object
        //final Button addBtn = (Button) findViewById(R.id.addBtn);

        //Create a resultTextView object
        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        //Create a vibration object
        final Vibrator vibrateobj = (Vibrator)getSystemService(VIBRATOR_SERVICE);


        //Create an onclick listener
//        resultTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Make phone vibrate
//                vibrateobj.vibrate(vlength);
//
//                //Increment the result value
//                result++;
//
//                //Display the result value
//                resultTextView.setText(result+"");
//
//            }
//        });

        //Experimental
        //////////////////////////////////////////////////////////////////////////////////////////
        //Create an onTouch Listener
        resultTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    //Make phone vibrate
                    vibrateobj.vibrate(vlength);

                    //Increment the result value
                    result++;

                    //Display the result value
                    resultTextView.setText(result+"");
                }
                return false;
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////


        //If we have a saved instance state, then we'll use that (in the case when ex: orientation changes)
        if (savedInstanceState != null) {
            //if savedInstanceState is not null then we'll take that value and set the text to that value
            result = savedInstanceState.getInt("result");
            resultTextView.setText(result+"");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.my_menu, menu);
        return true;
    }

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
                    return true;
                }

            //Settings page
            case R.id.settingsItem:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            //About page
            case R.id.aboutItem:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
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


}
