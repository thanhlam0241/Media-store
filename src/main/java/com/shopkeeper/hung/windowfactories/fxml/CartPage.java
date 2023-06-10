package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.hung.windowfactories.CustomerPage;
import com.shopkeeper.hung.windowfactories.ManagerPage;
import com.shopkeeper.hung.windowfactories.icons.Icon;
import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Pair;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class CartPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor;
    @FXML
    ListView<Label> boughtProducts;
    private ProductInfo productInfo;
    private Label cartLabel(ProductInfo productInfo, Integer number){
        Label label = new Label();
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 18));
        label.setLayoutX(0);
        label.setPrefHeight(30);
        label.setPrefWidth(450);
        label.setText(String.format("%1$-60s",productInfo.getTitle())+ (int)number);
        label.setTextFill(Color.web("#7b1616"));
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setInfo(productInfo, number);
            }
        });
        return label;
    }

    public void reload() throws Exception {
        numberLabel.setText(0+"");
        nameLabel.setText("");
        priceLabel.setText("");
        dateLabel.setText("");
        ratingLabel.setText("");
        addListOfCart();
    }

    private void addListOfCart() throws Exception {
        boughtProducts.getItems().clear();
        ObservableList<Label> labels =FXCollections.observableArrayList();
        for(Pair<ProductInfo, Integer> x: CustomerPage.getMain().getChosenProduct()){
            labels.add(cartLabel(x.getKey(),x. getValue()));
        }
        labels.sort(((o1, o2) -> Integer.compare(o1.getText().toLowerCase().compareTo(o2.getText().toLowerCase()),0)));
        boughtProducts.setItems(labels);
    }
    public void setPayFunction() throws Exception {
        CustomerPage.getMain().pay();
        this.reload();
    }

    @FXML
    Label nameLabel, priceLabel, ratingLabel, dateLabel, numberLabel;
    @FXML
    ImageView imageView;

    private void setInfo(ProductInfo productInfo, Integer number){
        this.productInfo = productInfo;
        if(productInfo instanceof BookInfo)
            imageView.setImage(Icon.getBookIcon());
        if(productInfo instanceof MusicInfo)
            imageView.setImage(Icon.getMusicIcon());
        if(productInfo instanceof FilmInfo)
            imageView.setImage(Icon.getFilmIcon());
        numberLabel.setText(number+"");
        nameLabel.setText(productInfo.getTitle());
        priceLabel.setText((int)productInfo.getCurrentSalePrice()/1000+",000 VND");
        dateLabel.setText(productInfo.getReleaseDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        ratingLabel.setText((int)productInfo.getRating()+" / 5");
    }

    public void setRemoveFunction() throws Exception {
        for(Pair<ProductInfo, Integer> x: CustomerPage.getMain().getChosenProduct()){
            if(x.getKey().getProductInfoId() == productInfo.getProductInfoId()){
                CustomerPage.getMain().getChosenProduct().remove(x);
                reload();
                return;
            }
        }
        System.out.println("bug o dau do roi");

    }

    public void initialize(URL url , ResourceBundle resourceBundle){
        setRoot(ancestor);
        try {
            addListOfCart();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
