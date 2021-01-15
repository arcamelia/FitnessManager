package ui;

import model.Category;
import model.Container;
import model.Exercise;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// represents a gui fitness manager application
public class FitnessManagerApp extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;

    private JPanel panelMain;
    private JPanel panelTopLeft;
    private JPanel panelTopRight;
    private JPanel panelMidLeft;
    private JPanel panelMidRight;
    private JPanel panelBotLeft;
    private JPanel panelBotRight;
    private JList listCategories;
    private JList listWorkouts;
    private JList listExercises;
    private JButton buttonExit;
    private JButton buttonAddWorkout;
    private JButton buttonAddExercise;
    private JButton buttonRemoveWorkout;
    private JButton buttonRemoveExercise;
    private JButton buttonLoadPrev;
    private JButton buttonSave;

    private DefaultListModel listCategoriesModel;
    private DefaultListModel listWorkoutsModel;
    private DefaultListModel listExercisesModel;

    private Container container;

    private JsonWriter writer = new JsonWriter("./data/container.json");
    private JsonReader reader = new JsonReader("./data/container.json");


    // EFFECTS: constructs a fitness manager application
    FitnessManagerApp() {
        super("My Fitness Manager");
        run();
    }

    // MODIFIES: this
    // EFFECTS: processes user input //EDIT
    public void run() {
        playSound("./data/wii sports.wav");
        initializeGraphics();
        initializeNewCategories();
        setDefaultListModels();
        displayCategories();
        addListSelectionListeners();
        addButtonActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: try loading previous session; if it can't be loaded, create new session
    private void getPreviousSession() {
        try {
            loadPreviousSession();
        } catch (IOException e) {
            initializeNewCategories();
        }
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

    // MODIFIES: this
    // EFFECTS: initialize a new, empty container with empty categories cardio, core, legs, arms, back
    public void initializeNewCategories() {
        container = new Container();
        container.addCategory(new Category("Cardio"));
        container.addCategory(new Category("Core"));
        container.addCategory(new Category("Legs"));
        container.addCategory(new Category("Arms/shoulders"));
        container.addCategory(new Category("Back"));
    }

    // MODIFIES: this
    // EFFECTS: initialize category, workout and exercise DefaultListModels, and assign them to their
    //          respective lists
    public void setDefaultListModels() {
        listCategoriesModel = new DefaultListModel();
        listCategories.setModel(listCategoriesModel);
        listWorkoutsModel = new DefaultListModel();
        listWorkouts.setModel(listWorkoutsModel);
        listExercisesModel = new DefaultListModel();
        listExercises.setModel(listExercisesModel);
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this FitnessManagerApp will operate
    public void initializeGraphics() {
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        panelTopLeft.setMinimumSize(new Dimension(WIDTH * 3 / 8, HEIGHT / 4));
        panelMidLeft.setMinimumSize(new Dimension(WIDTH * 3 / 8, HEIGHT * 5 / 16));
        panelBotLeft.setMinimumSize(new Dimension(WIDTH * 3 / 8, HEIGHT * 7 / 16));
        panelTopRight.setMinimumSize(new Dimension(WIDTH * 5 / 8, HEIGHT / 4));
        panelMidRight.setMinimumSize(new Dimension(WIDTH * 5 / 8, HEIGHT * 5 / 16));
        panelBotRight.setMinimumSize(new Dimension(WIDTH * 5 / 8, HEIGHT * 7 / 16));
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add all categories to the DefaultListModel of categories
    public void displayCategories() {
        for (Category c : container.getCategories()) {
            listCategoriesModel.addElement(c.getCategoryName());
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the DefaultListModel of workouts to be the workouts in the selected category
    public void refreshWorkoutList(Category c) {
        listWorkoutsModel.removeAllElements();
        for (Workout w : c.getWorkouts()) {
            listWorkoutsModel.addElement(w.getWorkoutName());
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the DefaultListModel of exercises to be the exercises in the selected workout
    public void refreshExerciseList(Workout w) {
        listExercisesModel.removeAllElements();
        for (Exercise e : w.getExercises()) {
            listExercisesModel.addElement(e.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds ListSelectionListeners for category and workout lists
    public void addListSelectionListeners() {
        listCategories.addListSelectionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                refreshWorkoutList(c);
                listExercisesModel.removeAllElements();
            }
        });
        listWorkouts.addListSelectionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                int workoutIndex = listWorkouts.getSelectedIndex();
                if (workoutIndex >= 0) {
                    Workout w = c.getWorkouts().get(workoutIndex);
                    refreshExerciseList(w);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds and initializes all button action listeners
    public void addButtonActionListeners() {
        getPreviousSessionButton();
        saveCurrentSessionButton();
        exitButton();
        addWorkoutButton();
        removeWorkoutButton();
        addExerciseButton();
        removeExerciseButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonLoadPrev
    public void getPreviousSessionButton() {
        buttonLoadPrev.addActionListener(e -> {
            getPreviousSession();
            playSound("./data/mushroom.wav");
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonSave
    public void saveCurrentSessionButton() {
        buttonSave.addActionListener(e -> {
            try {
                saveSession();
                playSound("./data/ds menu.wav");
            } catch (FileNotFoundException exp) {
                JOptionPane.showMessageDialog(null, "Error: file not found.");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonExit
    public void exitButton() {
        buttonExit.addActionListener(e -> System.exit(0));
    }

    // EFFECTS: plays a sound
    public static void playSound(String fileName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
            clip.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: could not play sound");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonAddWorkout
    public void addWorkoutButton() {
        buttonAddWorkout.addActionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                String name = JOptionPane.showInputDialog("Please enter your new workout's name:");
                Workout w = new Workout(name);
                addWorkout(c, w);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonRemoveWorkout
    public void removeWorkoutButton() {
        buttonRemoveWorkout.addActionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                List<String> workoutNamesList = convertWorkoutsToNames(c);
                Object[] workoutNames = workoutNamesList.toArray();
                String s = (String)JOptionPane.showInputDialog(null,
                        "Please select a workout to remove:", "Customized Dialog",
                        JOptionPane.QUESTION_MESSAGE, null, workoutNames, workoutNames[0]);
                Workout w = getWorkoutWithName(s, c);
                removeWorkout(c, w);
                listExercisesModel.removeAllElements();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonAddExercise
    public void addExerciseButton() {
        buttonAddExercise.addActionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                int workoutIndex = listWorkouts.getSelectedIndex();
                if (workoutIndex >= 0) {
                    Workout w = c.getWorkouts().get(workoutIndex);
                    String name = JOptionPane.showInputDialog("Please enter your new exercise's name:");
                    int time = Integer.parseInt(JOptionPane.showInputDialog(
                            "What would you like the duration of this exercise to be (in seconds)?"));
                    Exercise exercise = new Exercise(name, time);
                    addExercise(w, exercise);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes buttonRemoveExercise
    public void removeExerciseButton() {
        buttonRemoveExercise.addActionListener(e -> {
            int categoryIndex = listCategories.getSelectedIndex();
            if (categoryIndex >= 0) {
                Category c = container.getCategories().get(categoryIndex);
                int workoutIndex = listWorkouts.getSelectedIndex();
                if (workoutIndex >= 0) {
                    Workout w = c.getWorkouts().get(workoutIndex);
                    List<String> exerciseNamesList = convertExercisesToNames(w);
                    Object[] exerciseNames = exerciseNamesList.toArray();
                    String s = (String)JOptionPane.showInputDialog(null,
                            "Please select a workout to remove:", "Customized Dialog",
                            JOptionPane.QUESTION_MESSAGE, null, exerciseNames, exerciseNames[0]);
                    Exercise exercise = getExerciseWithName(s, w);
                    removeExercise(w, exercise);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds given workout to given category
    public void addWorkout(Category c, Workout w) {
        c.addWorkout(w);
        refreshWorkoutList(c);
    }

    // MODIFIES: this
    // EFFECTS: removes given workout from given category
    public void removeWorkout(Category c, Workout w) {
        c.removeWorkout(w);
        refreshWorkoutList(c);
    }

    // MODIFIES: this
    // EFFECTS: adds given exercise to given workout
    public void addExercise(Workout w, Exercise e) {
        w.addExercise(e);
        refreshExerciseList(w);
    }

    // MODIFIES: this
    // EFFECTS: removes given exercise from given workout
    public void removeExercise(Workout w, Exercise e) {
        w.removeExercise(e);
        refreshExerciseList(w);
    }

    // EFFECTS: convert a list of workouts in a category to a list of workout names
    public List<String> convertWorkoutsToNames(Category c) {
        List<Workout> workouts = c.getWorkouts();
        List<String> workoutNames = new ArrayList<>();
        for (Workout w : workouts) {
            workoutNames.add(w.getWorkoutName());
        }
        return workoutNames;
    }

    // EFFECTS: returns the workout with given name in given category
    public Workout getWorkoutWithName(String name, Category c) {
        for (Workout w : c.getWorkouts()) {
            if (w.getWorkoutName().equals(name)) {
                return w;
            }
        }
        return null;
    }

    // EFFECTS: convert a list of workouts in a category to a list of workout names
    public List<String> convertExercisesToNames(Workout w) {
        List<Exercise> exercises = w.getExercises();
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : exercises) {
            exerciseNames.add(e.getName());
        }
        return exerciseNames;
    }

    // EFFECTS: returns the exercise with given name in given workout
    public Exercise getExerciseWithName(String name, Workout w) {
        for (Exercise e : w.getExercises()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

}
