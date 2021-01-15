package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an exercise with a name, and an amount of time to do it for, in a workout
public class Exercise implements Writable {

    String name;  //the name of the exercise
    int time;  //the amount of time you do it for (in seconds)

    // Construct an exercise with a name and an amount of time you do it for
    public Exercise(String name, int time) {
        this.name = name;
        this.time = time;
    }


    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }


    // getters
    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        return json;
    }

}
