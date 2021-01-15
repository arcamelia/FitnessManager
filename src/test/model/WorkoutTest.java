package model;

import exception.EmptyWorkoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WorkoutTest {

    Workout w;
    Exercise e1 = new Exercise("Side plank", 30);
    Exercise e2 = new Exercise("Mountain climbers", 30);
    Exercise e3 = new Exercise("Elbow plank hold", 60);

    @BeforeEach
    public void setup() {
        w = new Workout("Lots of planking");
    }

    @Test
    public void testConstructor() {
        assertEquals("Lots of planking", w.getWorkoutName());
    }

    @Test
    public void testAddExercise() {
        w.addExercise(e1);
        w.addExercise(e2);
        assertEquals(2, w.numberOfExercises());
    }

    @Test
    public void testRemoveExercise() {
        w.removeExercise(e1);
        assertEquals(0, w.numberOfExercises());

        w.addExercise(e1);
        w.addExercise(e2);
        w.removeExercise(e2);
        assertEquals(1, w.numberOfExercises());
    }

    @Test
    public void testMoveExercise() {
        w.addExercise(e1);
        w.addExercise(e2);
        w.addExercise(e3);
        w.moveExercise(e3,0);
        assertEquals(e3, w.getExerciseInPosition(0));
    }

    @Test
    public void testTimeLengthOfWorkout() {
        w.addExercise(e1);
        w.addExercise(e2);
        w.addExercise(e3);
        assertEquals(120, w.timeLengthOfWorkout());
    }

    @Test
    public void testSetWorkoutName() {
        w.setWorkoutName("No planking");
        assertEquals("No planking", w.getWorkoutName());
    }

    @Test
    public void testGetPositionOfExercise() {
        w.addExercise(e1);
        w.addExercise(e2);
        assertEquals(1, w.getPositionOfExercise(e2));
    }

    @Test
    public void testGetExerciseInPosition() {
        w.addExercise(e1);
        w.addExercise(e2);
        assertEquals(e2, w.getExerciseInPosition(1));
    }

    @Test
    public void testViewWorkoutEmptyWorkout() {
        try {
            w.viewWorkout();
            fail("EmptyWorkoutException expected but not thrown");
        } catch (EmptyWorkoutException e) {
            // expected
        }
    }

    @Test
    public void testViewWorkoutNotEmpty() {
        w.addExercise(e1);
        w.addExercise(e2);
        try {
            assertEquals("Your Core workout 'Lots of planking':\nSide plank, 30s\nMountain climbers, 30s",
                    w.viewWorkout());
            // expected
        } catch (EmptyWorkoutException e) {
            fail("EmptyWorkoutException thrown but not expected");
        }
    }

    @Test
    public void testGetExercises() {
        w.addExercise(e1);
        w.addExercise(e2);

        List<Exercise> exerciseList = new LinkedList<>();
        exerciseList.add(e1);
        exerciseList.add(e2);

        assertEquals(exerciseList, w.getExercises());
    }

}
