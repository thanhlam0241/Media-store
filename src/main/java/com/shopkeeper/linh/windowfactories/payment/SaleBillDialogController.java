package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.utilities.CustomerToStringCell;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SaleBillDialogController {
    public boolean accept;
    public SaleBillDialogController(){
        accept = false;
    }

    public String priceToString(double price){
        StringBuilder sb = new StringBuilder(String.valueOf((long) price));
        for(int i = sb.length() - 3; i > 0; i-=3){
            sb.insert(i, '.');
        }
        return sb.toString();
    }
    private Stage window;
    @FXML
    private Text timeTxt;
    @FXML
    private Text customerNameTxt;
    @FXML
    private Text locationTxt;
    @FXML
    private Text phoneNumberTxt;
    @FXML
    private Text totalPriceTxt;
    @FXML
    private ListView<SaleBillItem> saleBillItemListView;
    public void initialize()
    {

        saleBillItemListView.setCellFactory(new Callback<ListView<SaleBillItem>, ListCell<SaleBillItem>>() {
            @Override
            public ListCell<SaleBillItem> call(ListView<SaleBillItem> param) {
                return new SaleBillItemListCell();
            }
        });

    }
    public void InitializeBill(SaleBill saleBill, ObservableList<SaleBillItem> saleBillItems, Stage window){
        this.window = window;
        timeTxt.setText(saleBill.getTime().toString());
        customerNameTxt.setText(saleBill.getCustomer().getName());
        locationTxt.setText(saleBill.getLocation());
        phoneNumberTxt.setText(saleBill.getCustomer().getPhoneNumber());
        totalPriceTxt.setText(priceToString(saleBill.getPrice()));
        saleBillItemListView.setItems(saleBillItems);
    }
    @FXML
    private void payWithMomo(){
        accept = true;
        window.close();
    }
    @FXML
    private void payWithCash(){
        accept = true;
        window.close();
    }
    @FXML
    private void payViaBankTransfer(){
        accept = true;
        window.close();
    }
    @FXML
    private void refuse(){
        accept = false;
        window.close();
    }
}
