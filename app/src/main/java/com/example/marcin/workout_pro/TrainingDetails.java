package com.example.marcin.workout_pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingDetails extends AppCompatActivity {
    public static int STATUS_MODE = 1;
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
                break;
            case 3:
                currentTraining = new Training(Training.getTrainings().get(Training.getTrainings().size()-1));
                break;
            case 4:
                currentTraining = (Training) getIntent().getSerializableExtra("contextTraining");
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

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
}
