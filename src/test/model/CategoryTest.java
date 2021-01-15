package model;

import exception.EmptyCategoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    Category legs;
    Workout w1;
    Workout w2;

    @BeforeEach
    public void setup() {
        legs = new Category("Legs");
        w1 = new Workout("Leg day");
        w2 = new Workout("Lots of squatting");
    }

    @Test
    public void testConstructor() {
        List<Workout> testList = new LinkedList<>();
        assertEquals("Legs", legs.getCategoryName());
        assertEquals(testList, legs.getWorkouts());
    }

    @Test
    public void testAddWorkout() {
        legs.addWorkout(w1);
        assertEquals(1, legs.numberOfWorkouts());
    }

    @Test
    public void testRemoveWorkout() {
        legs.addWorkout(w1);
        legs.removeWorkout(w2);
        assertEquals(1, legs.numberOfWorkouts());

        legs.removeWorkout(w1);
        assertEquals(0, legs.numberOfWorkouts());
    }

    @Test
    public void testNumberOfWorkouts() {
        legs.addWorkout(w1);
        legs.addWorkout(w2);
        assertEquals(2, legs.numberOfWorkouts());
    }

    @Test
    public void testGetWorkoutAtPosition() {
        legs.addWorkout(w1);
        legs.addWorkout(w2);
        assertEquals(w2, legs.getWorkoutAtPosition(1));
    }

    @Test
    public void testGetPositionOfWorkout() {
        legs.addWorkout(w1);
        legs.addWorkout(w2);
        assertEquals(1, legs.getPositionOfWorkout(w2));
    }

    @Test
    public void testViewCategoryEmptyCategory() {
        try {
            legs.viewCategory();
            fail("EmptyCategoryException expected but not thrown");
        } catch (EmptyCategoryException e) {
            // expected
        }
    }


    @Test
    public void testViewCategoryNotEmpty() {
        legs.addWorkout(w1);
        legs.addWorkout(w2);
        try {
            assertEquals("Your list of Legs workouts:\nLeg day\nLots of squatting",
                    legs.viewCategory());
            // expected
        } catch (EmptyCategoryException e) {
            fail("EmptyCategoryException thrown but not expected");
        }
    }

    @Test
    public void testGetWorkouts() {
        legs.addWorkout(w1);
        legs.addWorkout(w2);

        List<Workout> workoutList = new LinkedList<>();
        workoutList.add(w1);
        workoutList.add(w2);

        assertEquals(workoutList, legs.getWorkouts());
    }

}
