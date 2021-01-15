package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    Exercise exercise;

    @BeforeEach
    public void setup() {
        exercise = new Exercise("pushups", 30);
    }

    @Test
    public void testConstructor() {
        assertEquals("pushups", exercise.getName());
        assertEquals(30, exercise.getTime());
    }

    @Test
    public void testSetName() {
        exercise.setName("squats");
        assertEquals("squats", exercise.getName());
    }

    @Test
    public void testSetTime() {
        exercise.setTime(60);
        assertEquals(60, exercise.getTime());
    }

}