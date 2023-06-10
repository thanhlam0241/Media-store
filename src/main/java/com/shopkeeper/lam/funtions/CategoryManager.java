package com.shopkeeper.lam.funtions;

import com.shopkeeper.lam.models.Category;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryManager {
    private static CategoryManager manager;
    private CategoryManager(){}
    public static CategoryManager getManager() {
        if (manager == null){
            manager = new CategoryManager();
        }
        return manager;
    }

    public ObservableList<Category> getAll() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        return adapter.getAllCategories();
    }
    public void add(Category category) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertCategory(category);
    }
    public void update(Category category) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateCategory(category);
    }

}
