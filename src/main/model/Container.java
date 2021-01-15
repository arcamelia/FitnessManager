package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// represents a container that keeps all categories in one place
public class Container implements Writable {

    private List<Category> categories;

    // create an empty container
    public Container() {
        categories = new LinkedList<>();
    }

    // EFFECTS: adds a category to end of list of categories
    public void addCategory(Category c) {
        categories.add(c);
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("categories", categoriesToJson());
        return json;
    }

    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Category c: categories) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
