package com.example.marcin.workout_pro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Training implements Serializable {
    private static ArrayList<Training> trainings = new ArrayList<>();
    private Date date;
    private ArrayList<Exercise> chosenExercises;
    private HashMap<Exercise, Integer> exerciseWeight;
    private HashMap<Exercise, Boolean> exerciseDone;

    public Training(ArrayList<Exercise> chosenExercises, HashMap<Exercise, Integer> chosenWeight){
        this.chosenExercises = chosenExercises;
        this.exerciseWeight = chosenWeight;
        this.date = Calendar.getInstance().getTime();
    }

    public ArrayList<Exercise> getChosenExercises(){
        return chosenExercises;
    }

    public HashMap<Exercise, Integer> getExerciseWeight(){
        return exerciseWeight;
    }

    public Training(Training pastTraining){
        this.chosenExercises = pastTraining.getChosenExercises();
        this.exerciseWeight = pastTraining.getExerciseWeight();
        this.date = Calendar.getInstance().getTime();
    }

    public static ArrayList<Training> getTrainings(){
        return trainings;
    }
}
