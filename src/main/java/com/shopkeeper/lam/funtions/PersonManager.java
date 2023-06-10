package com.shopkeeper.lam.funtions;
import com.shopkeeper.lam.database.PersonDbSet;
import com.shopkeeper.lam.models.*;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PersonManager {
    private static PersonManager manager;
    private PersonManager(){}

    public static PersonManager getManager() {
        if (manager == null){
            manager = new PersonManager();
        }
        return manager;
    }
    public ObservableList<Person> getAll() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        return adapter.getAllPeople();
    }
    public void add(Person person) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertPerson(person);
    }
    public void remove(Person person) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deletePerson(person);
    }
    public void update(Person person) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updatePerson(person);
    }

}
