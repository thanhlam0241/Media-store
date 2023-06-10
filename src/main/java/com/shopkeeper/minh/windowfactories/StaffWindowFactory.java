package com.shopkeeper.minh.windowfactories;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffWindowFactory extends WindowFactory {
    public static StaffWindowFactory factory = new StaffWindowFactory();
    public static StaffWindowFactory getFactory(){
        return factory;
    }
    public StaffWindowFactory(){
        currentWindow = null;
    }

    @Override
    protected Stage createWindow() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StaffWindowFactory.class.getResource("staff-window.fxml"));
        Scene scene = null;
        stage.setTitle("Quan ly nhan vien");
        stage.setResizable(false);
        try {
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException ioException){
            System.err.println(ioException.getMessage());
            ioException.printStackTrace();
        }
        if(scene == null) {
            stage.close();
            return null;
        }
        stage.setScene(scene);
        return stage;
    }

    @Override
    public Stage openWindow() {
        if(currentWindow == null) currentWindow = createWindow();
        currentWindow.show();
        return null;
    }

    @Override
    public Stage closeWindow() {
        currentWindow.hide();
        return currentWindow;
    }
}
