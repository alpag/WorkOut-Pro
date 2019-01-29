package com.example.marcin.workout_pro;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingDetails extends AppCompatActivity {
    public static int STATUS_MODE = 1;
    private static final String TAG = "TrainingDetails";
//    Statuses
//    0 - default
//    1 - noConf
//    2 - after Conf
//    3 - add training
//    4 - preview training

    private FirebaseAuth mAuth;
    private Training currentTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        switch (STATUS_MODE){
            case 1:
                configureTraining();
                break;
            case 2:
                ArrayList<Exercise> exercises= new ArrayList<Exercise>();
                exercises = (ArrayList<Exercise>) getIntent().getSerializableExtra("exercises");
                HashMap<Exercise, Integer> exerciseWeight= new HashMap<>();
                exerciseWeight = (HashMap<Exercise, Integer>) getIntent().getSerializableExtra("weights");

                currentTraining = new Training(exercises, exerciseWeight);
                generateUI();
                break;
            case 3:
                currentTraining = new Training(Training.getTrainings().get(Training.getTrainings().size()-1));
                break;
            case 4:
                currentTraining = (Training) getIntent().getSerializableExtra("contextTraining");
                break;
        }



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void configureTraining(){
        Intent intent = new Intent(getApplicationContext(), ConfigureTraining.class);
        startActivity(intent);
        finish();
    }

    private void generateUI(){
        LinearLayout layout = findViewById(R.id.layoutTraining);
        Log.d(TAG, "generateUI: " + layout);

        for (Map.Entry<Exercise, Integer> entry : currentTraining.getExerciseWeight().entrySet()) {
            Log.d(TAG, "generateUI: " + "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            generateSingleElement(layout, entry.getKey(), entry.getValue());
        }
        generateConfirmBtn(layout);
    }

    private void generateSingleElement(LinearLayout layout, Exercise e, Integer weight){
        RelativeLayout container = new RelativeLayout(this);
        container.setBackground(ContextCompat.getDrawable(this, R.drawable.message));
        final RelativeLayout.LayoutParams settings = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        settings.setMargins(20,30,20,30);
        container.setPadding(30, 30, 30, 30);

        TextView name = new TextView(this);
        name.setText(e.getName());
        name.setTextSize(20);
        name.setId(1488 + currentTraining.getChosenExercises().indexOf(e));

        EditText textEdit = new EditText(this);
        textEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        int index = 2137 + currentTraining.getChosenExercises().indexOf(e);
        textEdit.setId(index);
        Log.d(TAG, "statusik ustawiono  " + index);
        textEdit.setText(weight.toString() + "kg");
        textEdit.setTextSize(20);
        RelativeLayout.LayoutParams settingsEdit = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        settingsEdit.setMargins(600, 0 , 0 ,0 );
        textEdit.setPadding(0, 0, 50 ,10 );


        CheckBox isDone = new CheckBox(this);
        isDone.setId(4137 + currentTraining.getChosenExercises().indexOf(e));
        RelativeLayout.LayoutParams settingsCbx = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        settingsCbx.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        textEdit.setLayoutParams(settingsEdit);
        isDone.setLayoutParams(settingsCbx);

        container.addView(name);
        container.addView(textEdit);
        container.addView(isDone);
        layout.addView(container, settings);
    }

    private void generateConfirmBtn(LinearLayout layout){
        Button confirm = new Button(getApplicationContext());
        confirm.setText("That's enough for me today!");
        layout.addView(confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Exercise, Integer> weights = new HashMap<>();
                HashMap<Exercise, Boolean> status = new HashMap<>();
                for (Exercise e: currentTraining.getChosenExercises()) {
                    int index = 2137 + currentTraining.getChosenExercises().indexOf(e);
                    EditText datafield = findViewById(index);
                    CheckBox isDone = findViewById(index+2000);
                    String weight = datafield.getText().toString();
                    weight = weight.replaceAll("\\D+","");
                    weights.put(e, Integer.parseInt(weight));
                    status.put(e, isDone.isChecked());
                }
                currentTraining.setExerciseDone(status);
                currentTraining.setExerciseWeight(weights);
                Training.getTrainings().add(currentTraining);
                TrainingDetails.STATUS_MODE = 3;
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
