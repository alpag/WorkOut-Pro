package com.example.marcin.workout_pro;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class Timer extends Fragment implements View.OnClickListener {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);


        startButton = view.findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        pauseButton = view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(this);
        resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);


        chronometer = view.findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 100000) //100s
                    Toast.makeText(getActivity(), "Czas mija", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.startButton:
                startChronometer(v);
                break;
            case R.id.pauseButton:
                pauseChronometer(v);
                break;
            case R.id.resetButton:
                resetChronometer(v);
                break;
        }
    }

    public void startChronometer (View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer (View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer (View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
