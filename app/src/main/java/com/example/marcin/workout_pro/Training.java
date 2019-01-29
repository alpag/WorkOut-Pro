package com.example.marcin.workout_pro;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Training implements Serializable {
    private static ArrayList<Training> trainings;
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
    public void setChosenExercises(ArrayList<Exercise> _chosenExercises){ this.chosenExercises = _chosenExercises; }

    public HashMap<Exercise, Integer> getExerciseWeight(){
        return exerciseWeight;
    }
    public void setExerciseWeight(HashMap<Exercise, Integer> _exerciseWeight){this.exerciseWeight = _exerciseWeight; }


    public HashMap<Exercise, Boolean> getExerciseDone(){
        return exerciseDone;
    }
    public void setExerciseDone(HashMap<Exercise, Boolean> _exerciseDone){this.exerciseDone = _exerciseDone; }

    public Training(Training pastTraining){
        this.chosenExercises = pastTraining.getChosenExercises();
        this.exerciseWeight = pastTraining.getExerciseWeight();
        this.date = Calendar.getInstance().getTime();
    }

    public Training(){
        this.date = Calendar.getInstance().getTime();
    }

    public static ArrayList<Training> getTrainings(){
        return trainings;
    }

    public String getDate(){
        String dateToReturn = DateFormat.getDateInstance(DateFormat.SHORT).format(date);

        return dateToReturn;
    }

    public static void initTrainings(){
        trainings = new ArrayList<>();


    }


}
