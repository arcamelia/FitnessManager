package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTest {

    @Test
    public void testConstructor() {
        Container container = new Container();
        assertEquals(0, container.getCategories().size());
    }

    @Test
    public void testAddCategory() {
        Container container = new Container();
        Category c1 = new Category("Core");
        Category c2 = new Category("Legs");
        container.addCategory(c1);
        container.addCategory(c2);
        assertEquals(2, container.getCategories().size());
        assertEquals(c1, container.getCategories().get(0));
        assertEquals(c2, container.getCategories().get(1));
    }

}
