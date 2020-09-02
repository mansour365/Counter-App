package com.example.counterapp;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        resultTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Make phone vibrate
                vibrateobj.vibrate(vlength);

                //Increment the result value
                result++;

                //Display the result value
                resultTextView.setText(result+"");

            }
        });

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
            case R.id.decrementItem:
                if(result == 0) {
                    Toast.makeText(this, "Already 0", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else{
                    Toast.makeText(this, "Minus 1", Toast.LENGTH_SHORT).show();
                    result--;
                    resultTextView.setText(result+"");
                    return true;
                }
            case R.id.resetItem:
                Toast.makeText(this, "Resetting", Toast.LENGTH_SHORT).show();
                result = 0;
                resultTextView.setText(result+"");
                return true;
            case R.id.settingsItem:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutItem:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
