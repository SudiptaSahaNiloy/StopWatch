package com.example.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {
    private int miliseconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            miliseconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", miliseconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //Start the stopwatch running when the Start Button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop Button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch running when the Reset Button is clicked
    public void onClickReset(View view) {
        running = false;
        miliseconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = miliseconds / (3600 * 100);
                int minutes = (miliseconds % (3600 * 100) ) / (60*100);
                int secs = (miliseconds / 100) % 60;
                int msecs = miliseconds % 100;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d:%02d", hours, minutes, secs, msecs);
                timeView.setText(time);
                if (running) {
                    miliseconds++;
                }
                handler.postDelayed(this, 1);
            }
        });
    }
}