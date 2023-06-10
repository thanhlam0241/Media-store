package com.shopkeeper.hung.windowfactories;

import com.shopkeeper.hung.windowfactories.fxml.ManagerPageController;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class ManagerPage extends Application {
    private static final ManagerPage mainPage  = new ManagerPage();

    public static ManagerPage getMain(){
        return mainPage;
    }


    @Override
    public void start(Stage stage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main-page.fxml"));
            Scene scene = new Scene(loader.load());
            ManagerPageController controller = loader.getController();
            controller.setRoot(loader.getRoot());


            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Manager");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("manager.png"))));
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }catch(Exception e){
            System.out.println("bug main: " +e);
        }
    }
    public static void main(String... args){
        try{
            launch();
        }
        catch(Exception e){
            System.out.println("bug main: "+e);
        }
    }
}
