package com.shopkeeper.hung.windowfactories.fxml;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerPageController extends Controller implements Initializable{
    public JFXButton customerButton1;
    @FXML
    AnchorPane ancestor;
    @FXML
    ImageView background;

    ProductPage productPage;
    CategoryPage categoryPage;
    PublisherPage publisherPage;
    PersonPage personPage;
    CustomerProductPage cProductPage;
    CartPage cartPage;

    public void setProductButton(){
        try{
            if(productPage!=null) {
//                productPage.reload();
                background.toFront();
                productPage.toFront();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-page.fxml"));
            loader.load();
            ProductPage controller = loader.getController();
            this.add(controller,250,100);
            productPage= controller;
        }catch(Exception e){
            System.out.println("bug setProductButton(): "+e);
        }
    }
    public void setCategoryButton(){
        try{
            if(categoryPage!=null) {
                categoryPage.setReloadButton();
                background.toFront();
                categoryPage.toFront();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("category-page.fxml"));
            loader.load();
            CategoryPage controller = loader.getController();
            this.add(controller,250,100);
            categoryPage= controller;
        }catch(Exception e){
            System.out.println("bug setProductButton(): "+e);
        }
    }
    public void setPublisherButton(){
        try{
            if(publisherPage!=null) {
//                publisherPage.setReloadButton();
                background.toFront();
                publisherPage.toFront();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("publisher-page.fxml"));
            loader.load();
            PublisherPage controller = loader.getController();
            this.add(controller,250,100);
            publisherPage= controller;
        }catch(Exception e){
            System.out.println("bug setProductButton(): "+e);
        }
    }
    public void setPersonButton(){
        try{
            if(personPage!=null) {
//                personPage.setReloadButton();
                background.toFront();
                personPage.toFront();
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("person-page.fxml"));
            loader.load();
            PersonPage controller = loader.getController();
            this.add(controller,250,100);
            personPage= controller;
        }catch(Exception e){
            System.out.println("bug setProductButton(): "+e);
        }
    }

    public void setCartPage(){
        try{
            if(cartPage!=null) {
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
        setRoot(ancestor);setProductButton();
//        background.toFront();
        setProductButton();
    }
}
