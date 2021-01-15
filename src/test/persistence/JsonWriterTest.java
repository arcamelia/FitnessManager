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

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Container c = new Container();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyContainer() {
        try {
            Container container = new Container();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyContainer.json");
            writer.open();
            writer.write(container);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyContainer.json");
            container = reader.read();
            assertEquals(0, container.getCategories().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralContainer() {
        try {
            Container container = new Container();
            Category c1 = new Category("Core");
            Workout w1 = new Workout("Lots of planking");
            w1.addExercise(new Exercise("side plank R", 30));
            w1.addExercise(new Exercise("side plank L", 30));
            w1.addExercise(new Exercise("hand plank", 60));
            c1.addWorkout(w1);
            Workout w2 = new Workout("200 situps");
            w2.addExercise(new Exercise("situps", 200));
            c1.addWorkout(w2);
            Category c2 = new Category("Legs");
            container.addCategory(c1);
            container.addCategory(c2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralContainer.json");
            writer.open();
            writer.write(container);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralContainer.json");
            container = reader.read();
            List<Category> categories = container.getCategories();
            assertEquals(2, categories.size());

            Category c3 = categories.get(0);
            assertEquals("Core", c3.getCategoryName());
            List<Workout> workouts = c3.getWorkouts();
            assertEquals(2, workouts.size());
            for (Workout w: workouts) {
                checkWorkout(w.getWorkoutName(), w);
                List<Exercise> exercises = w.getExercises();
                for (Exercise e: exercises) {
                    checkExercise(e.getName(), e.getTime(), e);
                }
            }
            Category c4 = categories.get(1);
            assertEquals("Legs", c4.getCategoryName());
            assertEquals(0, c4.getWorkouts().size());

        } catch (IOException e) {
            fail("Exceptions should not have been thrown");
        }
    }

}
