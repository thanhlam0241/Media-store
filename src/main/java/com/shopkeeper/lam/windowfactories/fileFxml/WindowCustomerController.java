package com.shopkeeper.lam.windowfactories.fileFxml;

import com.shopkeeper.hung.windowfactories.CustomerPage;
import com.shopkeeper.lam.models.Person;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.windowfactories.CustomerFeedbackWindowFactory;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowCustomerController  {
    @FXML
    AnchorPane anchorPane1;
    @FXML
    Button button1;
    @FXML
    Button button2;

    public void openViewProduct() {
        CustomerPage.getMain().start(new Stage());
    }
    public void openSendFeedBack() {
        CustomerFeedbackWindowFactory.getFactory().openWindow();
    }

}
