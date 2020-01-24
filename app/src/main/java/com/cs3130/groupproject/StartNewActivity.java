package com.cs3130.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class StartNewActivity extends AppCompatActivity {

    //Member variables
    private String mTimeInterval;
    private String mPhoneNum;
    private String PHONE_KEY = "phone num";
    private String TIME_INT_KEY = "time";

    EditText phoneNumView;
    NumberPicker hours, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new);

        //TODO Someone make this toolbar have a back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        minutes = findViewById(R.id.minutePicker);
        hours = findViewById(R.id.hourPicker);
        createPicker();

        if (savedInstanceState != null) {
            mPhoneNum = savedInstanceState.getString(PHONE_KEY);
            mTimeInterval = savedInstanceState.getString(TIME_INT_KEY);
        }
    }

    /**
     * Saves variables for when device is rotated
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(TIME_INT_KEY, mTimeInterval);
        savedInstanceState.putString(PHONE_KEY, mPhoneNum);
    }

    /**
     * TODO: write this
     *
     * @param some Menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu some) {
        getMenuInflater().inflate(R.menu.menu_main, some);
        return super.onCreateOptionsMenu(some);
    }

    /**
     * Creates the time pickers
     */
    public void createPicker() {
        minutes.setMinValue(0);
        minutes.setMaxValue(59);

        hours.setMinValue(0);
        hours.setMaxValue(23);
    }

    /**
     * When start button is pressed
     */
    public void onStartClick(View view) {

        phoneNumView = findViewById(R.id.editPhone);
        //Phone number field cannot be left empty
        if (TextUtils.isEmpty(phoneNumView.getText())) {
            Toast.makeText(StartNewActivity.this, "Phone Number is Required!",
                    Toast.LENGTH_LONG).show();
        } else if (phoneNumView.getText().length() < 10) {
            //Phone number cannot be less than 10 characters
            Toast.makeText(StartNewActivity.this, "Please enter a valid phone number! " +
                            "(Maybe you need to add an area code)",
                    Toast.LENGTH_LONG).show();
        } else if (hours.getValue() == 0 && minutes.getValue() == 0) {
            //Time interval cannot be zero
            Toast.makeText(StartNewActivity.this, "Time interval cannot be zero!",
                    Toast.LENGTH_LONG).show();
        } else {
            mTimeInterval = (hours.getValue() * 60) + minutes.getValue() + "";
            mPhoneNum = phoneNumView.getText().toString();

            Intent intentStart = new Intent(this, DuringActivity.class);
            intentStart.putExtra("tInterval", mTimeInterval);
            intentStart.putExtra("pNum", mPhoneNum);
            startActivity(intentStart);
        }
    }
}
