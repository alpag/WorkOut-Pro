package com.example.marcin.workout_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NEXT_TRAINING_REQUEST = 2;
    private static final int EDIT_TRAINING_REQUEST = 1;
    private FirebaseAuth mAuth;
    Training currentTraining;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Exercise.initExercises();

        if(Training.getTrainings().isEmpty())
            TrainingDetails.STATUS_MODE = 1;

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home()).commit();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void createNewTraining(){
        if(!Training.getTrainings().isEmpty())
            currentTraining = Training.getTrainings().get(Training.getTrainings().size()-1);
        Intent intent = new Intent(this, TrainingDetails.class);
        intent.putExtra("contextTraining", currentTraining);
        startActivityForResult(intent, NEXT_TRAINING_REQUEST);
    }

    public void previewTrainingDetails(Training contextTraining){
        currentTraining = contextTraining;
        Intent intent = new Intent(this, TrainingDetails.class);
        intent.putExtra("contextTraining", contextTraining);
        startActivityForResult(intent, EDIT_TRAINING_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: w ogole wsszedl we aktywnosc");
        Log.d(TAG, "onActivityResult: wszedl i " + requestCode);
        Log.d(TAG, "onActivityResult: wszedl i " + resultCode);

        switch(requestCode) {
            case EDIT_TRAINING_REQUEST:
                if (resultCode == RESULT_OK) {
                    HashMap<Exercise, Boolean> isDone = (HashMap<Exercise, Boolean>) data.getSerializableExtra("status");
                    HashMap<Exercise, Integer> weight = (HashMap<Exercise, Integer>) data.getSerializableExtra("weight");
                    currentTraining.setExerciseWeight(weight);
                    currentTraining.setExerciseDone(isDone);
                }
                break;
            case NEXT_TRAINING_REQUEST:
                if (resultCode == RESULT_OK) {
                    HashMap<Exercise, Boolean> isDone = (HashMap<Exercise, Boolean>) data.getSerializableExtra("status");
                    HashMap<Exercise, Integer> weight = (HashMap<Exercise, Integer>) data.getSerializableExtra("weight");
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) data.getSerializableExtra("exercises");
                    Training t = new Training();
                    t.setExerciseWeight(weight);
                    t.setExerciseDone(isDone);
                    t.setChosenExercises(exercises);
                    Training.getTrainings().add(t);
                }
                break;
        }
        onResume();

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new Home();
                            break;
                        case R.id.nav_timer:
                            selectedFragment = new Timer();
                            break;
                        case R.id.nav_chart:
                            selectedFragment = new Chart();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
