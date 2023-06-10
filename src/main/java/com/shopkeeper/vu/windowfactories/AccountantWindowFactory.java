package com.shopkeeper.vu.windowfactories;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AccountantWindowFactory extends WindowFactory {

    public   static AccountantWindowFactory factory = new AccountantWindowFactory();
    public static AccountantWindowFactory getFactory(){
        return  factory;
    }
    @Override
    protected Stage createWindow() {
        return null;
    }

    @Override
    public Stage openWindow() {
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(AccountantWindowFactory.class.getResource("AWF.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1350, 700);
            stage.setTitle("Thống kê của hàng media");
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