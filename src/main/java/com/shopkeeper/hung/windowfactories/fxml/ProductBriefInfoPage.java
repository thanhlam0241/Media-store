package com.shopkeeper.hung.windowfactories.fxml;

import com.jfoenix.controls.JFXButton;
import com.shopkeeper.hung.windowfactories.icons.Icon;
import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductBriefInfoPage extends Controller implements Initializable {
    @FXML
    Label title,price;
    @FXML
    AnchorPane ancestor;
    @FXML
    ImageView icon;
    @FXML
    JFXButton button;
    private ProductInfo productInfo;
    private int type = 1;

    public JFXButton getButton(){
        return this.button;
    }

    public ProductInfo getProductInfo(){
        return this.productInfo;
    }

    public void setProductInfo(ProductInfo productInfo){
        this.productInfo= productInfo;
        title.setText(productInfo.getTitle());
        price.setText(productInfo.getCurrentSalePrice()+" VND");
        if(productInfo instanceof BookInfo){
            String temp=ancestor.getStyle();
            ancestor.setStyle(temp+";"+"-fx-background-color: #90EE90");
            icon.setImage(Icon.getBookIcon());
        }
        if(productInfo instanceof MusicInfo){
            String temp=ancestor.getStyle();
            ancestor.setStyle(temp+";"+"-fx-background-color: #87CEFA");
            icon.setImage( Icon.getMusicIcon());
        }
        if(productInfo instanceof FilmInfo){
            String temp=ancestor.getStyle();
            ancestor.setStyle(temp+";"+"-fx-background-color: #FBBF77");
            icon.setImage( Icon.getFilmIcon());
        }
    }
    public void setLocation(int row, int column){
        ancestor.setLayoutY(10+row*250);
        ancestor.setLayoutX(30+column*250);
    }
    public void setLocation(int index){
        int row=index/3;
        int column=index%3;
        setLocation(row,column);
    }
    public void setBrightText(){
        title.setTextFill(Color.web("#7600a9"));
        price.setTextFill(Color.web("#7600a9"));
    }

    public void setNormalText(){
        title.setTextFill(Color.WHITE);
        price.setTextFill(Color.WHITE);
    }

    public void setDetailPage(){
        try{
            if(type==2){
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-detail-page.fxml"));
            loader.load();
            ProductDetailPage detailController = loader.getController();
            detailController.setProductInfo(productInfo);
            detailController.setRoot(loader.getRoot());
            Controller parentController = this.getParent();
            parentController.add(detailController);
        }catch(Exception e){
            System.out.println("bug setDetailPage: "+e);
        }
    }
    public Image getImage(){
        return icon.getImage();
    }
    public void setType2(){
        this.type=2;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setRoot(ancestor);
    }
}
