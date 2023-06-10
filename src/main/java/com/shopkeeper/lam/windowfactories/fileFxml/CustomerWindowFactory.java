package com.shopkeeper.lam.windowfactories.fileFxml;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomerWindowFactory extends WindowFactory {
    private static CustomerWindowFactory factory = new CustomerWindowFactory();

    public static CustomerWindowFactory getFactory() {
        return factory;
    }
    public CustomerWindowFactory(){
        currentWindow = null;
    }
    @Override
    public Stage createWindow()  {
        Stage stage = new Stage();
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("Customer.fxml"));
            stage.setTitle("Customer");
            stage.setResizable(false);
            stage.getIcons().add(new Image(String.valueOf(this.getClass().getResource("edf5fa51c4cb365f054223f8b1a991f4.jpg"))));
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
        stage.setY(200);
        stage.setX(800);
        stage.setScene(scene);
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
