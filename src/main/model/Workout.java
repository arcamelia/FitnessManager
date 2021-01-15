package model;

import exception.EmptyWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// represents a workout that has a name and has a list of exercises
public class Workout implements Writable {

    List<Exercise> exercises;
    String workoutName;

    // construct a new workout with given name and category
    public Workout(String name) {
        exercises = new LinkedList<>();
        workoutName = name;
    }

    // MODIFIES: this
    // EFFECTS: add exercise to the end of the workout
    public void addExercise(Exercise e) {
        exercises.add(e);
    }

    // MODIFIES: this
    // EFFECTS: if workout contains given exercise, remove it from workout
    public void removeExercise(Exercise e) {
        exercises.remove(e);
    }

    // MODIFIES: this
    // EFFECTS: move given element to given position (in index basing) in workout
    public void moveExercise(Exercise e, int pos) {
        exercises.remove(e);
        exercises.add(pos, e);
    }

    // EFFECTS: returns the total amount of time workout will take
    public int timeLengthOfWorkout() {
        int elapsedTime = 0;
        for (Exercise e : exercises) {
            int t = e.getTime();
            elapsedTime = elapsedTime + t;
        }
        return elapsedTime;
    }

    // EFFECTS: returns the number of exercises in workout
    public int numberOfExercises() {
        return exercises.size();
    }

    // EFFECTS: if workout is empty throw EmptyWorkoutException, else print out the workout
    public String viewWorkout() throws EmptyWorkoutException {
        if (numberOfExercises() == 0) {
            throw new EmptyWorkoutException();
        }
        String printout = "Your workout '" + workoutName + "':";
        for (Exercise e : exercises) {
            printout = printout.concat("\n" + e.getName() + ", " + e.getTime() + "s");
        }
        return printout;
    }

    // EFFECT: return exercise at pos
    public Exercise getExerciseInPosition(int pos) {
        return exercises.get(pos);
    }

    // EFFECTS: return pos of exercise
    public int getPositionOfExercise(Exercise e) {
        return exercises.indexOf(e);
    }


    // setter
    public void setWorkoutName(String name) {
        workoutName = name;
    }


    // getters
    public String getWorkoutName() {
        return workoutName;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", workoutName);
        json.put("exercises", exercisesToJson());
        return json;
    }

    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e: exercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

}
