package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentWindowFactory extends WindowFactory {
    private static PaymentWindowFactory factory = new PaymentWindowFactory();
    public static PaymentWindowFactory getFactory(){
        return factory;
    }
    private PaymentWindowFactory(){
        currentWindow = null;
    }
    @Override
    protected Stage createWindow() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PaymentWindowFactory.class.getResource("payment-window.fxml"));
        Scene scene = null;
        stage.setTitle("Quản lý hoá đơn bán hàng và thanh toán");
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
        //stage.getIcons().add(new Image("file:C:\\Users\\linh2\\Downloads\\New folder\\home.png"));
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
