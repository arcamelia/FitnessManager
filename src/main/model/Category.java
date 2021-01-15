package model;

import exception.EmptyCategoryException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// represents a muscle category that workouts can be grouped into
public class Category implements Writable {

    String categoryName;
    List<Workout> workouts;

    // Construct a workout category with given name
    public Category(String name) {
        categoryName = name;
        workouts = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add given workout to the end of the category
    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    // MODIFIES: this
    // EFFECTS: if category contains given workout, remove it from category
    public void removeWorkout(Workout w) {
        workouts.remove(w);
    }

    // EFFECTS: returns the number of workouts in category
    public int numberOfWorkouts() {
        return workouts.size();
    }

    // EFFECTS: return workout at pos
    public Workout getWorkoutAtPosition(int pos) {
        return workouts.get(pos);
    }

    // EFFECTS: return pos of workout
    public int getPositionOfWorkout(Workout w) {
        return workouts.indexOf(w);
    }

    // EFFECTS: if workout list in this category is empty throws an EmptyCategoryException
    //          otherwise returns a string of all workouts in category
    public String viewCategory() throws EmptyCategoryException {
        if (numberOfWorkouts() == 0) {
            throw new EmptyCategoryException();
        }
        String printout = "Your list of " + categoryName + " workouts:";
        for (Workout w : workouts) {
            printout = printout.concat("\n" + w.getWorkoutName());
        }
        return printout;
    }


    // getters
    public String getCategoryName() {
        return categoryName;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", categoryName);
        json.put("workouts", workoutsToJson());
        return json;
    }

    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Workout w: workouts) {
            jsonArray.put(w.toJson());
        }
        return jsonArray;
    }

}
