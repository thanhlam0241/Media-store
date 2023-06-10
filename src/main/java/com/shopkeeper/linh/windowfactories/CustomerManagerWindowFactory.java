package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CustomerManagerWindowFactory extends WindowFactory {
    private static CustomerManagerWindowFactory factory = new CustomerManagerWindowFactory();
    public static CustomerManagerWindowFactory getFactory(){
        return factory;
    }
    private CustomerManagerWindowFactory(){
        currentWindow = null;
    }
    @Override
    protected Stage createWindow() {
        Stage stage = new Stage();
        URL location = FeedbackWindowFactory.class.getResource("customer-manager-window.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Scene scene = null;
        stage.setTitle("Quản lý khách hàng");
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
        return currentWindow;
    }

    @Override
    public Stage closeWindow() {
        if(currentWindow == null) return null;
        currentWindow.hide();
        return currentWindow;
    }
}
