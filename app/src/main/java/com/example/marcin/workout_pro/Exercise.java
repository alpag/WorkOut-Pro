package com.example.marcin.workout_pro;
import java.io.Serializable;
import java.util.ArrayList;

public class Exercise implements Serializable {
    private static ArrayList<Exercise> exercises;
    private String name;
    private String shortName;

    public String getName(){
        return name;
    }

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

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Exercise other = (Exercise) obj;
        if (this.shortName == null ? other.shortName != null : !this.shortName.equals(other.shortName))
        {
            return false;
        }
        if (this.name == null ? other.name != null : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }

}
