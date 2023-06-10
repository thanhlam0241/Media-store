package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListViewItemController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class SaleBillListItemController {
    public static SaleBillListItemController getController(){
        FXMLLoader fxmlLoader = new FXMLLoader(SaleBillListItemController.class.getResource("salebill-list-item.fxml"));

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
    public BorderPane getContainer(){
        return container;
    }
    @FXML
    private BorderPane container;
    @FXML
    private Text titleTxt;
    @FXML
    private Text timeTxt;
    @FXML
    private Text priceTxt;
    @FXML
    private Text locationTxt;
    @FXML
    private Text customerTxt;
    public void setTitle(String title){
        if(title.length() > 53){
            titleTxt.setText(title.substring(0, 53) + "...");
        }
        else{
            titleTxt.setText(title);
        }
    }
    public void setPrice(double price){
        StringBuilder sb = new StringBuilder(String.valueOf((long) price));
        if(sb.length() < 6){
            priceTxt.setFill(Paint.valueOf("#ffffff"));
        }
        else if (sb.length() < 7){
            priceTxt.setFill(Paint.valueOf("#67fff0"));
        }
        else if (sb.length() < 8){
            priceTxt.setFill(Paint.valueOf("#6ef588"));
        }
        else if (sb.length() < 9){
            priceTxt.setFill(Paint.valueOf("#d1f56e"));
        }
        else{
            priceTxt.setFill(Paint.valueOf("#f5ab6e"));
        }
        for(int i = sb.length() - 3; i > 0; i-=3){
            sb.insert(i, '.');
        }
        sb.append(" VND");
        priceTxt.setText(sb.toString());
    }
    public void setTime(LocalDate date){
        if(date == null) timeTxt.setText("NULL");
        timeTxt.setText(date.toString());
    }
    public void setCustomer(Customer customer){
        if (customer == null) customerTxt.setText("NULL");
        if(customer.getName().length() > 53){
            customerTxt.setText(customer.getName().substring(0, 53) + "...");
        }
        else{
            customerTxt.setText(customer.getName());
        }
    }
    public void setLocation(String location){
        if(location.length() > 53){
            locationTxt.setText(location.substring(0, 53) + "...");
        }
        else{
            locationTxt.setText(location);
        }
    }
    private ChangeListener<String> titleChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setTitle(newValue);
        }
    };
    private ChangeListener<Number> priceChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            setPrice(newValue.doubleValue());
        }
    };
    private ChangeListener<LocalDate> timeChangeListener = new ChangeListener<LocalDate>() {
        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
            setTime(newValue);
        }
    };
    private ChangeListener<Customer> customerChangeListener = new ChangeListener<Customer>() {
        @Override
        public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
            setCustomer(newValue);
        }
    };
    private ChangeListener<String> locationChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setLocation(newValue);
        }
    };
    private SaleBill oldSaleBill = null;
    public void setDataContext(SaleBill saleBill){
        if(saleBill == oldSaleBill) return;
        if(oldSaleBill != null){
            oldSaleBill.nameProperty().removeListener(titleChangeListener);
            oldSaleBill.priceProperty().removeListener(priceChangeListener);
            oldSaleBill.timeProperty().removeListener(timeChangeListener);
            oldSaleBill.customerProperty().removeListener(customerChangeListener);
            oldSaleBill.locationProperty().removeListener(locationChangeListener);
        }
        if(saleBill != null){
            saleBill.nameProperty().addListener(titleChangeListener);
            saleBill.priceProperty().addListener(priceChangeListener);
            saleBill.timeProperty().addListener(timeChangeListener);
            saleBill.customerProperty().addListener(customerChangeListener);
            saleBill.locationProperty().addListener(locationChangeListener);
            setTitle(saleBill.getName());
            setPrice(saleBill.getPrice());
            setTime(saleBill.getTime());
            setCustomer(saleBill.getCustomer());
            setLocation(saleBill.getLocation());
        }
        else{
            setTitle("NULL");
            setPrice(0);
            setTime(null);
            setCustomer(null);
            setLocation("NULL");
        }
    }
}
