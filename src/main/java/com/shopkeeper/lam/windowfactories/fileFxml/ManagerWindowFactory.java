package com.shopkeeper.lam.windowfactories.fileFxml;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import com.shopkeeper.vu.windowfactories.AccountantWindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ManagerWindowFactory extends WindowFactory  {
    private static ManagerWindowFactory factory = new ManagerWindowFactory();

    public static ManagerWindowFactory getFactory() {
        return factory;
    }
    public ManagerWindowFactory(){
        {
            currentWindow = null;
        }
    }
    @Override
    public Stage createWindow()  {
        Stage stage = new Stage();
        Scene scene = null;
        try {
        Parent root = FXMLLoader.load(this.getClass().getResource("Manager.fxml"));
        stage.setTitle("Manager");
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(this.getClass().getResource("87247676_p0_master1200.jpg"))));
        scene  = new Scene(root);

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        if(scene == null) {
            stage.close();
            return null;
        }
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(200);
        return stage;
    }

    @Override
    public Stage openWindow() {
        if(currentWindow == null) currentWindow = createWindow();
        currentWindow.show();
        return currentWindow;
    }

    @Override
    public Stage closeWindow() {
        currentWindow.hide();
        return currentWindow;
    }
}
