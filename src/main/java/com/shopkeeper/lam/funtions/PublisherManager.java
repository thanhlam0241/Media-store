package com.shopkeeper.lam.funtions;

import com.shopkeeper.lam.models.Publisher;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Locale;

public class PublisherManager {
    private static PublisherManager manager;
    private PublisherManager(){}
    public static PublisherManager getManager() {

        if (manager == null){
            manager = new PublisherManager();
        }
        return manager;
    }

    public ObservableList<Publisher> getAll() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        return adapter.getAllPublishers();
    }
    public void add(Publisher publisher) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertPublisher(publisher);
    }
    public void update(Publisher publisher) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updatePublisher(publisher);
    }


}
