package com.shopkeeper.vu.windowfactories;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WarehouseManagerWindowFactory extends WindowFactory {
    private static  WarehouseManagerWindowFactory factory = new WarehouseManagerWindowFactory();
    public  static WarehouseManagerWindowFactory getFactory(){
        return factory;
    }
    public WarehouseManagerWindowFactory(){
        currentWindow = null;
    }
    @Override
    protected Stage createWindow() {
        return null;
    }

    @Override
    public Stage openWindow() {
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(AccountantWindowFactory.class.getResource("WMWF.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setTitle("Quản lý kho");
            stage.getIcons().add(new Image(String.valueOf(AccountantWindowFactory.class.getResource("img.png"))));
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Stage closeWindow() {
        return null;
    }
}
