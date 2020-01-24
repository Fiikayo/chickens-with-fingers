package com.cs3130.groupproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class DuringActivity extends AppCompatActivity {

    static Toolbar mToolBar;
    private CountDownTimer aCountDownTimer;

    private boolean timerFinished;
    private int timerNum;
    private long timeLeft = 0;
    private String phoneNum;
    private String timer;
    private TextView timerTextview;

    private static final int MY_REQUEST_INT = 177;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during);

        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        if (savedInstanceState != null) {
            timeLeft = savedInstanceState.getLong("timeLeft");
            timerNum = savedInstanceState.getInt("timerNum");
            timerFinished = savedInstanceState.getBoolean("timerFinished");
        }

        timerTextview = findViewById(R.id.countDownTimer);
        if (getIntent().getExtras() != null) //Here sort of as an error detector
        {
            Intent myintent = getIntent();
            timer = myintent.getExtras().getString("tInterval");
            phoneNum = myintent.getExtras().getString("pNum");
            TextView pNumberTextview = (TextView) findViewById(R.id.phoneNumberDisplay);
            String message = "Sending to " + phoneNum;
            pNumberTextview.setText(message);
            timerNum = Integer.parseInt(timer);
            timeLeft = timerNum * 60000;
        }
        runTimer();
    }

    /**
     *
     */
    private void runTimer() {
        aCountDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                int sec = (int) l / 1000;
                int min = sec / 60;
                sec = sec - min * 60;
                timerTextview.setText("Remaining Time: " + min + ":" + sec);
                if (sec < 10) {
                    timerTextview.setText("Remaining Time: " + min + ":0" + sec);
                }
            }

            /**
             * When the timer ends, make a toast to inform the user its done
             * Send a message to the present phone number
             */
            @Override
            public void onFinish() {
                timerFinished = true;
                sendMessage();
                runTimer();
            }
        }.start();
    }

    /**
     * @param some
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu some) {
        getMenuInflater().inflate(R.menu.menu_main, some);
        return super.onCreateOptionsMenu(some);
    }

    /**
     * Stop timer
     * @param view
     */
    public void onStop(View view) {
        aCountDownTimer.cancel();
    }

    /**
     * Restart timer
     * @param view
     */
    public void onRestart(View view) {
        aCountDownTimer.start();
    }

    /**
     * End timer and return to home screen
     * @param view
     */
    public void onEnd(View view) {
        aCountDownTimer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("timerNum", timerNum);
        outState.putLong("timeLeft", timeLeft);
        outState.putBoolean("timerFinished", timerFinished);
    }

    /**
     * Method controlling the sending of messages
     *
     * Checks that the user has given permission for the device to access location, if not, asks again
     *
     * Pulls device location and appends it to a text message to be sent to a pre-decided phone number
     * as given by the user
     */
    private void sendMessage() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //If the user has not granted permission to use location, ask again
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_REQUEST_INT);
            }
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null,
                    "Follow this link to see my location: " +
                            "https://www.google.ca/maps/search/" + latitude + "," + longitude,
                    null, null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Toast.makeText(DuringActivity.this, "Timer Done, Sending Message", Toast.LENGTH_LONG).show();
    }
}
