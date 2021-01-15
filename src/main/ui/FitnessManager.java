package ui;

import exception.EmptyCategoryException;
import exception.EmptyWorkoutException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// represents a console-based fitness manager application
public class FitnessManager {

    private Container container;
    private Scanner scanner = new Scanner(System.in);
    private JsonWriter writer = new JsonWriter("./data/container.json");
    private JsonReader reader = new JsonReader("./data/container.json");

    // construct app where previous session is automatically loaded
    public FitnessManager() {
        getPreviousSession();
        run();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    void run() {
        System.out.println("Welcome to your personal fitness manager!\n");
        String response;
        do {
            System.out.println("Please select a muscle group:");
            showMenu("1. Cardio", "2. Core", "3. Legs", "4. Arms/shoulders", "5. Back", "6. Exit app\n");
            response = scanner.nextLine();

            mainMenuResponse(response);

        } while (!response.equals("6"));
        try {
            saveSession();
            System.out.println("\nSession saved successfully!\n");
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found.");
        }

    }

    // EFFECTS: directs user to appropriate menu given response
    private void mainMenuResponse(String response) {
        if (response.equals("1")) {
            workoutMenu(container.getCategories().get(0));
        } else if (response.equals("2")) {
            workoutMenu(container.getCategories().get(1));
        } else if (response.equals("3")) {
            workoutMenu(container.getCategories().get(2));
        } else if (response.equals("4")) {
            workoutMenu(container.getCategories().get(3));
        } else if (response.equals("5")) {
            workoutMenu(container.getCategories().get(4));
        }
    }

    // MODIFIES: this
    // EFFECTS: try loading previous session; if it can't be loaded, create new session
    private void getPreviousSession() {
        try {
            loadPreviousSession();
            System.out.println("Previous session loaded successfully!\n");
        } catch (IOException e) {
            System.out.println("Error when loading previous session. Creating empty app with preset muscle groups.\n");
            createNewSession();
        }
    }

    // MODIFIES: this
    // EFFECTS: create a new session with empty categories
    private void createNewSession() {
        container = new Container();
        container.addCategory(new Category("Cardio"));
        container.addCategory(new Category("Core"));
        container.addCategory(new Category("Legs"));
        container.addCategory(new Category("Arms/shoulders"));
        container.addCategory(new Category("Back"));
    }

    // MODIFIES: this
    // EFFECTS: loads previous session's workouts into appropriate categories
    private void loadPreviousSession() throws IOException {
        container = reader.read();
    }

    // EFFECTS: saves current session
    private void saveSession() throws FileNotFoundException {
        writer.open();
        writer.write(container);
        writer.close();
    }

    // EFFECTS: shows a menu of options to user
    private void showMenu(String s1, String s2, String s3, String s4, String s5, String s6) {
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);
        System.out.println(s6);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select an option from the workout menu
    private void workoutMenu(Category c) {
        String response;
        do {
            showMenu("\nPlease select an option:", "1. View a list of all your workouts in this category",
                    "2. View a specific workout", "3. Add a workout", "4. Remove a workout",
                    "5. Return to muscle group categories menu\n");
            response = scanner.nextLine();

            if (response.equals("1")) {
                viewWorkouts(c);
            } else if (response.equals("2")) {
                editWorkout(c);
            } else if (response.equals("3")) {
                addWorkout(c);
            } else if (response.equals("4")) {
                removeWorkout(c);
            }

        } while (!response.equals("5"));
    }

    // EFFECTS: displays a list of all workouts in given category
    private void viewWorkouts(Category c) {
        try {
            System.out.println(c.viewCategory());
        } catch (EmptyCategoryException e) {
            System.out.println("\nThe category '" + c.getCategoryName() + "' doesn't have any workouts yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new workout to given category
    private void addWorkout(Category c) {
        System.out.println("Please enter your new workout's name:");
        String workoutName = scanner.nextLine();
        Workout w = new Workout(workoutName);
        c.addWorkout(w);
    }

    // MODIFIES: this
    // EFFECTS: removes an existing workout from given category
    private void removeWorkout(Category c) {
        if (c.numberOfWorkouts() == 0) {
            return;
        }
        Workout w = selectWorkout(c);
        c.removeWorkout(w);
    }

    // EFFECTS: prompts user to select a workout from all workouts in given category, then returns that workout
    private Workout selectWorkout(Category c) {
        int selectedWorkoutNum;
        System.out.println("Please select a workout:");
        int i = 1;
        for (Workout w : c.getWorkouts()) {
            System.out.println(i + ". " + w.getWorkoutName());
            i++;
        }
        selectedWorkoutNum = scanner.nextInt();
        return c.getWorkoutAtPosition(selectedWorkoutNum - 1);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select an attribute of workout to edit
    private void editWorkout(Category c) {
        if (c.numberOfWorkouts() == 0) {
            return;
        }
        Workout w = selectWorkout(c);
        System.out.println("\nPlease select an option:");
        System.out.println("1. View complete list of exercises in this workout");
        showMenu("2. View length of workout", "3. Change workout name", "4. Add an exercise",
                "5. Remove an exercise", "6. Edit an exercise", "7. Return to category menu");
        int response = scanner.nextInt();
        scanner.nextLine();

        if (editWorkoutResponses(w, response)) {
            return;
        }
        editWorkout(c);
    }

    // MODIFIES: this
    // EFFECTS: executes an edit to given workout based on user's response
    private boolean editWorkoutResponses(Workout w, int response) {
        if (response == 1) {
            viewExercises(w);
        } else if (response == 2) {
            System.out.println("This workout will take you " + w.timeLengthOfWorkout() + "s to complete");
        } else if (response == 3) {
            System.out.println("What would you like to change this workout's name to?");
            String s = scanner.nextLine();
            w.setWorkoutName(s);
        } else if (response == 4) {
            addExercise(w);
        } else if (response == 5) {
            removeExercise(w);
        } else if (response == 6) {
            editExercise(w);
        } else if (response == 7) {
            return true;
        }
        return false;
    }

    // EFFECTS: displays a list of all exercises in given workout
    private void viewExercises(Workout w) {
        try {
            System.out.println("\n" + w.viewWorkout() + "\n");
        } catch (EmptyWorkoutException e) {
            System.out.println("\nYour workout '" + w.getWorkoutName() + "' doesn't have any exercises yet!\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new exercise to given workout
    private void addExercise(Workout w) {
        System.out.println("\nPlease enter your new exercise's name:");
        String exerciseName = scanner.nextLine();
        System.out.println("What would you like the duration of this exercise to be (in seconds)?");
        int exerciseTime = scanner.nextInt();
        scanner.nextLine();
        Exercise e = new Exercise(exerciseName, exerciseTime);
        w.addExercise(e);
    }

    // MODIFIES: this
    // EFFECTS: removes an exercise from given workout
    private void removeExercise(Workout w) {
        if (w.numberOfExercises() == 0) {
            return;
        }
        Exercise e = selectExercise(w);
        w.removeExercise(e);
    }

    // EFFECTS: prompts user to select an exercise from given workout, then returns that exercise
    private Exercise selectExercise(Workout w) {
        int selectedExerciseNum;
        System.out.println("Please select an exercise:");
        int i = 1;
        for (Exercise e : w.getExercises()) {
            System.out.println(i + ". " + e.getName());
            i++;
        }
        selectedExerciseNum = scanner.nextInt();
        return w.getExerciseInPosition(selectedExerciseNum - 1);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select an attribute of exercise to edit, then executes the edit
    private void editExercise(Workout w) {
        if (w.numberOfExercises() == 0) {
            return;
        }
        Exercise e = selectExercise(w);
        int response;
        showMenu("Choose what you would like to edit:", "1. Exercise name", "2. Exercise duration", "", "", "");
        response = scanner.nextInt();

        if (response == 1) {
            System.out.println("What would you like to change this exercise's name to?");
            String s = scanner.nextLine();
            e.setName(s);
        } else if (response == 2) {
            System.out.println("What would you like to change the duration of this exercise to (in seconds)?");
            int i = scanner.nextInt();
            e.setTime(i);
        }
        viewExercises(w);
    }

}

















