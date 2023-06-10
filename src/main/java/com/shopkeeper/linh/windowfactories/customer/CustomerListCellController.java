package com.shopkeeper.linh.windowfactories.customer;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListViewItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class CustomerListCellController {
    public static CustomerListCellController getController(){
        FXMLLoader fxmlLoader = new FXMLLoader(CustomerListCellController.class.getResource("customer-list-cell.fxml"));

        Parent template;
        try
        {
            template = fxmlLoader.load();
            return fxmlLoader.getController();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private AnchorPane container;
    public AnchorPane getContainer(){
        return container;
    }
    @FXML
    private Text nameTxt;
    @FXML
    private Text locationTxt;
    @FXML
    private Text phoneNumberTxt;
    private Customer oldData;

    public void setDataContext(Customer data){
        if(data == oldData) return;
        if(oldData != null){
            nameTxt.textProperty().unbind();
            phoneNumberTxt.textProperty().unbind();
            locationTxt.textProperty().unbind();
        }
        if(data != null){
            nameTxt.textProperty().bind(data.nameProperty());
            phoneNumberTxt.textProperty().bind(data.phoneNumberProperty());
            locationTxt.textProperty().bind(data.defaultLocationProperty());
        }
        oldData = data;
    }
}
