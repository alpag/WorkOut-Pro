package com.example.marcin.workout_pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrainingDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Training.getTrainings().isEmpty())
            configureTraining();

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
