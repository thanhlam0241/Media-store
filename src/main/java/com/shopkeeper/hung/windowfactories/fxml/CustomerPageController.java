package com.shopkeeper.hung.windowfactories.fxml;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPageController extends Controller implements Initializable{
    public JFXButton customerButton1;
    @FXML
    ImageView background;
    @FXML
    AnchorPane ancestor;
    CustomerProductPage cProductPage;
    CartPage cartPage;
    public void setCProductButton(){
        try{
            if(cProductPage!=null) {
//                cProductPage.reload();
                background.toFront();
                cProductPage.toFront();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("c-product-page.fxml"));
            loader.load();
            CustomerProductPage controller = loader.getController();
            this.add(controller,250,100);
            cProductPage= controller;
        }catch(Exception e){
            System.out.println("bug setCProductButton(): "+e);
        }
    }

    public void setCartPage(){
        try{
            if(cartPage!=null) {
                background.toFront();
                cartPage.toFront();
                cartPage.reload();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cart-page.fxml"));
            loader.load();
            CartPage controller = loader.getController();
            this.add(controller,250,100);
            cartPage= controller;
        }catch(Exception e){
            System.out.println("bug setCartPage(): "+e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setRoot(ancestor);
        setCProductButton();
    }
}
