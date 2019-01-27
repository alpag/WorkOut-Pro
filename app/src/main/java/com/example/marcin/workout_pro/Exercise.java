package com.example.marcin.workout_pro;
import java.util.ArrayList;

public class Exercise {
    private static ArrayList<Exercise> exercises;
    private String name;
    private String shortName;

    public String getName(){
        return name;
    }

    @androidx.annotation.NonNull
    @Override
    public String toString() {
        return this.name;
    }

    private Exercise(String name, String shortName){
        this.name = name;
        this.shortName = shortName;
    }

    public static ArrayList<Exercise> getExercises(){
        return exercises;
    }




    public static void initExercises(){
        exercises = new ArrayList<Exercise>();
        Exercise e = new Exercise("Bench press", "BP");
        exercises.add(e);
        Exercise e2 = new Exercise("Squat", "SQ");
        exercises.add(e2);

    }
}
