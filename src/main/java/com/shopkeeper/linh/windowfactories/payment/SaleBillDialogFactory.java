package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.FeedbackWindowFactory;
import com.shopkeeper.mediaone.windowfactories.WindowFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class SaleBillDialogFactory {


    public static boolean openWindow(SaleBill saleBill, ObservableList<SaleBillItem> saleBillItems) {
        Stage stage = new Stage();
        URL location = SaleBillDialogFactory.class.getResource("salebill-dialog.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Scene scene = null;
        stage.setTitle("Thanh toán hoá đơn");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        SaleBillDialogController controller = null;
        try {
            scene = new Scene(fxmlLoader.load());
            controller = fxmlLoader.getController();
        }
        catch (IOException ioException){
            System.err.println(ioException.getMessage());
            ioException.printStackTrace();
        }
        if(scene == null) {
            stage.close();
            return false;
        }
        controller.InitializeBill(saleBill, saleBillItems, stage);
        stage.setScene(scene);
        stage.showAndWait();
        return controller.accept;
    }
}
