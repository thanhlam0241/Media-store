package com.shopkeeper.mediaone;


import com.shopkeeper.lam.database.*;
import com.shopkeeper.lam.models.*;
import com.shopkeeper.lam.windowfactories.fileFxml.CustomerWindowFactory;
import com.shopkeeper.lam.windowfactories.fileFxml.ManagerWindowFactory;
import com.shopkeeper.linh.models.*;
import com.shopkeeper.linh.windowfactories.FeedbackWindowFactory;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.functions.StaffManager;
import com.shopkeeper.minh.models.*;
import com.shopkeeper.minh.test;
import com.shopkeeper.minh.windowfactories.StaffWindowFactory;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        ManagerWindowFactory.getFactory().openWindow();
        CustomerWindowFactory.getFactory().openWindow();

        //javafx.application.Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}