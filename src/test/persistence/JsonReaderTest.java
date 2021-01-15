package persistence;

import model.Category;
import model.Container;
import model.Exercise;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Container c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyContainer() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyContainer.json");
        try {
            Container c = reader.read();
            assertEquals(0, c.getCategories().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCategory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralContainer.json");
        try {
            Container container = reader.read();
            assertEquals(2, container.getCategories().size());
            Category c1 = container.getCategories().get(0);
            Category c2 = container.getCategories().get(1);

            assertEquals("Legs", c1.getCategoryName());
            List<Workout> workouts = c1.getWorkouts();
            assertEquals(2, workouts.size());;
            for (Workout w: workouts) {
                List<Exercise> exercises = w.getExercises();
                String wName = w.getWorkoutName();
                checkWorkout(wName, w);
                for (Exercise e: exercises) {
                    String eName = e.getName();
                    int eTime = e.getTime();
                    checkExercise(eName, eTime, e);
                }
            }
            assertEquals("Cardio", c2.getCategoryName());
            assertEquals(0, c2.getWorkouts().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
