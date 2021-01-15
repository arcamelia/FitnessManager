package persistence;


import model.Exercise;
import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkWorkout(String name, Workout workout) {
        assertEquals(name, workout.getWorkoutName());
    }

    protected void checkExercise(String name, int time, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(time, exercise.getTime());
    }

}
