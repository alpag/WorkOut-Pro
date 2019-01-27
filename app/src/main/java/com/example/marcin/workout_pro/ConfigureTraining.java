package com.example.marcin.workout_pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;

public class ConfigureTraining extends AppCompatActivity {
    private static final String TAG = "ConfigureTraining";
    private ArrayList<Exercise> exercises;
    private MultiSpinner spinner;
    private ArrayAdapter<Exercise> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_training);

        // create spinner list elements
        adapter = new ArrayAdapter<Exercise>(this, android.R.layout.simple_spinner_item);

        for(Exercise exercise : Exercise.getExercises())
            adapter.add(exercise);
        spinner = (MultiSpinner) findViewById(R.id.spinnerMulti);
        spinner.setAdapter(adapter, false, onSelectedListener);
        spinner.setHint("Choose exercises");

        // set initial selection
        boolean[] selectedItems = new boolean[adapter.getCount()];
//        selectedItems[1] = true; // select second item
//        spinner.setSelected(selectedItems);
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {
             exercises = createExerciseSet(selected);

            if(exercises.size() == 0)
                Toast.makeText(ConfigureTraining.this, "You have to put any exercise", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onItemsSelected: " + exercises.size());
        }
    };

    private ArrayList<Exercise> createExerciseSet(boolean[] selected){
        ArrayList<Exercise> chosenExercises = new ArrayList<>();
        for(int i=0; i<selected.length; i++){
            if(selected[i] == true)
                chosenExercises.add(Exercise.getExercises().get(i));
        }
        return chosenExercises;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
