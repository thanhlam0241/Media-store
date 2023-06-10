package com.shopkeeper.mediaone;

import com.shopkeeper.linh.windowfactories.FeedbackWindowFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    public static void Run(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
        Scene scene = null;
        stage.setTitle("Mên Guyn Đâu");
        try {
            scene = new Scene(fxmlLoader.load(), 200, 200);
        }
        catch (IOException ioException){
            System.err.println(ioException.getMessage());
            ioException.printStackTrace();
        }
        if(scene == null) {
            stage.close();
        }
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Button openWndBtn;
    @FXML
    private Button closeWndBtn;

    @FXML
    protected void onOpenWindowButtonClick() {
        FeedbackWindowFactory.getFactory().openWindow();

    }
    @FXML
    protected void onCloseWindowButtonClick() {
        FeedbackWindowFactory.getFactory().closeWindow();

    }
}