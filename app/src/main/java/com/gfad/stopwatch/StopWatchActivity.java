package com.gfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopWatchActivity extends Activity {

    TextView timerText;
    boolean timerRunning;
    boolean wasRunning;
    int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        // Get the previous state of the stowatch
        // if the activity's been destroyed and recreated
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds", seconds);
            timerRunning = savedInstanceState.getBoolean("timerRunning", timerRunning);
            wasRunning = savedInstanceState.getBoolean("wasRunning", wasRunning);
        }

        timerText = findViewById(R.id.timerText);

        runTimer();
    }

    // save the state of the stopwatch if it's about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("timerRunning", timerRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // If the activity is paused, stop the watch
    @Override
    protected void onPause() {
        super.onPause();

        // record if the stopWatch was running
        wasRunning = timerRunning;

        timerRunning = false;
    }

    // If the activity is resumed, start the stopwatch again
    // if it was running previously
    @Override
    protected void onResume() {
        super.onResume();

        if (wasRunning) {

            timerRunning = true;

        }
    }

    public void onClickStart(View view) {

        timerRunning = true;
    }

    public void onClickStop(View view) {

        timerRunning = false;
    }

    public void onClickReset(View view) {

        timerRunning = false;

        seconds = 0;
    }

    //set the number of seconds on the timer
    private void runTimer() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timerText.setText(time);

                if (timerRunning) {

                    seconds++;

                }

                // post the code again with a delay of 1 second
                handler.postDelayed(this, 1000);
            }
        });
    }
}
