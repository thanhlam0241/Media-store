package com.shopkeeper.linh.windowfactories.customer;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

public class CustomerEditingPanelController {
    public static CustomerEditingPanelController getController(){
        FXMLLoader fxmlLoader = new FXMLLoader(CustomerEditingPanelController.class.getResource("customer-editing-panel.fxml"));

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

    public CustomerEditingPanelController(){}
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField phoneNumberTxt;
    private Customer customer;
    public void openToEdit(Customer customer){
        this.customer = customer;
        resetPanel();
        container.setVisible(true);

    }
    public void initialize(){
        container.setVisible(false);
    }
    @FXML
    private void closePanel(){
        container.setVisible(false);
    }
    @FXML
    private void resetPanel(){
        if(this.customer == null) {
            customerNameTxt.setText("");
            locationTxt.setText("");
            phoneNumberTxt.setText("");
        }
        else{
            customerNameTxt.setText(this.customer.getName());
            locationTxt.setText(this.customer.getDefaultLocation());
            phoneNumberTxt.setText(this.customer.getPhoneNumber());
        }
    }
    @FXML
    private void saveChange(){
        StringBuilder errorsStringBuilder = new StringBuilder();

        String customerName = customerNameTxt.getText() == null ? "" : customerNameTxt.getText().trim();
        if(customerName.length() == 0){
            errorsStringBuilder.append("- Không được bỏ trống tên khách hàng.\n");
        }
        try{
            String s = phoneNumberTxt.getText().trim();
            if(s.length() == 0) throw new NullPointerException();
            if(s.length() < 10 || s.length() > 12) throw new NumberFormatException();
            if(s.charAt(0) != '0' && s.charAt(0) != '8' && s.charAt(1) != '4'
                    && s.charAt(0) != '+' && s.charAt(1) != '8' && s.charAt(2) != '4') throw new NumberFormatException();
            long l = Long.parseLong(s);
        }
        catch (NumberFormatException e){
            errorsStringBuilder.append("- Số điện thoại không hợp lệ.\n");
        }
        catch (NullPointerException e){
            errorsStringBuilder.append("- Không được bỏ trống số điện thoại.\n");
        }
        String location = locationTxt.getText() == null ? "" : locationTxt.getText().trim();
        if(location.length() == 0){
            errorsStringBuilder.append("- Không được bỏ trống địa chỉ.\n");
        }
        if(this.customer == null){
            if(errorsStringBuilder.length() == 0){
                var adapter = DatabaseAdapter.getDbAdapter();
                customer = new Customer(customerName, location, phoneNumberTxt.getText().trim());
                adapter.insertCustomer(customer);
            }

        }
        else {
            if(errorsStringBuilder.length() == 0){
                var adapter = DatabaseAdapter.getDbAdapter();
                customer.setName(customerName);
                customer.setDefaultLocation(location.trim());
                customer.setPhoneNumber(phoneNumberTxt.getText().trim());
                adapter.updateCustomer(customer);
            }

        }
        if(errorsStringBuilder.length() == 0){
            closePanel();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông tin khách hàng không hợp lệ");
            alert.setHeaderText("Thông tin khách hàng không hợp lệ do vi phạm điều sau:");
            alert.setContentText(errorsStringBuilder.toString());
            alert.showAndWait();
        }
    }
}
