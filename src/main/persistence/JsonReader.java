package persistence;

import model.Category;
import model.Container;
import model.Exercise;
import model.Workout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

// represents a reader that reads a container from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a container JSON file, and returns the containing item
    public Container read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseContainer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses container from JSON object and returns it
    private Container parseContainer(JSONObject jsonObject) {
        Container container = new Container();
        List<Category> categories = readCategories(jsonObject);
        for (Category c: categories) {
            container.addCategory(c);
        }
        return container;
    }

    // MODIFIES: container
    // EFFECTS: reads a JSONArray of categories and returns a list of those categories
    private List<Category> readCategories(JSONObject jsonObject) {
        JSONArray categoryArray = jsonObject.getJSONArray("categories");
        List<Category> categories = new LinkedList<>();
        for (Object c: categoryArray) {
            JSONObject categoryObject = (JSONObject) c;
            Category parsedCategory = parseCategory(categoryObject);
            categories.add(parsedCategory);
        }
        return categories;
    }

    // MODIFIES: container
    // EFFECTS: parses a category JSONObject and returns a category
    private Category parseCategory(JSONObject categoryObject) {
        String name = categoryObject.getString("name");
        Category category = new Category(name);
        List<Workout> workouts = readWorkouts(categoryObject);
        for (Workout w: workouts) {
            category.addWorkout(w);
        }
        return category;
    }

    // MODIFIES: container, category
    // EFFECTS: reads a JSONArray of workouts and returns a list of those workouts
    private List<Workout> readWorkouts(JSONObject categoryObject) {
        JSONArray workoutArray = categoryObject.getJSONArray("workouts");
        List<Workout> workouts = new LinkedList<>();
        for (Object w: workoutArray) {
            JSONObject workoutObject = (JSONObject) w;
            Workout parsedWorkout = parseWorkout(workoutObject);
            workouts.add(parsedWorkout);
        }
        return workouts;
    }

    // MODIFIES: container, category
    // EFFECTS: parses a workout JSONObject and returns a workout object
    private Workout parseWorkout(JSONObject workoutObject) {
        String name = workoutObject.getString("name");
        Workout workout = new Workout(name);
        List<Exercise> exercises = readExercises(workoutObject);
        for (Exercise e: exercises) {
            workout.addExercise(e);
        }
        return workout;
    }

    // MODIFIES: container, category, workout
    // EFFECTS: reads a JSONArray of exercises and returns a list of those exercises
    private List<Exercise> readExercises(JSONObject workoutObject) {
        JSONArray exerciseArray = workoutObject.getJSONArray("exercises");
        List<Exercise> exercises = new LinkedList<>();
        for (Object e: exerciseArray) {
            JSONObject exerciseObject = (JSONObject) e;
            Exercise parsedExercise = parseExercise(exerciseObject);
            exercises.add(parsedExercise);
        }
        return exercises;
    }

    // MODIFIES: container, category, workout
    // EFFECTS: parses an exercise JSONObject and returns an exercise object
    private Exercise parseExercise(JSONObject exerciseObject) {
        String name = exerciseObject.getString("name");
        int time = exerciseObject.getInt("time");
        Exercise exercise = new Exercise(name, time);
        return exercise;
    }

}
