package com.example.marcin.workout_pro;
import java.util.ArrayList;

public class Exercise {
    private static ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    private String exerciseName;

    public String getName(){
        return exerciseName;
    }

    private Exercise(String name){
        this.exerciseName = name;
    }

    public static ArrayList<Exercise> getExercises(){
        return exercises;
    }




    public static void initExercises(){
        Exercise e = new Exercise("Bench press");
        exercises.add(e);
        Exercise e2 = new Exercise("Squat");
        exercises.add(e2);

    }
}
