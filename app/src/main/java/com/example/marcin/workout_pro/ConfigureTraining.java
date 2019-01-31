package com.example.marcin.workout_pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;
import java.util.HashMap;

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
            if(exercises.size() == 0) {
                Toast.makeText(ConfigureTraining.this, "You have to put any exercise", Toast.LENGTH_SHORT).show();
                return;
            }
            LinearLayout layout = findViewById(R.id.lay);
            for (Exercise e: exercises) {
                EditText textEdit = new EditText(getApplicationContext()); // Pass it an Activity or Context
                textEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                textEdit.setHint("Start weight of " + e);
                textEdit.setId(2137 + exercises.indexOf(e));
                layout.addView(textEdit);
            }
            Button confirm = new Button(getApplicationContext());
            confirm.setText("I am ready, lets lift some weights!");
            layout.addView(confirm);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<Exercise, Integer> weights = new HashMap<>();
                    for (Exercise e: exercises) {
                        int index = exercises.indexOf(e) + 2137;
                        EditText datafield = findViewById(index);
                        weights.put(e, Integer.parseInt(datafield.getText().toString()));
                    }
                    TrainingDetails.STATUS_MODE = 2;
                    Intent intent = new Intent();
                    intent.putExtra("weights", weights);
                    intent.putExtra("exercises", exercises);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
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
        finish();
    }
}
