package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.ProductInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class  SaleBillItemListCellController {
    public static SaleBillItemListCellController getController(){
        FXMLLoader fxmlLoader = new FXMLLoader(SaleBillItemListCellController.class.getResource("salebill-item-list-item.fxml"));

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
    public AnchorPane getContainer(){
        return container;
    }
    @FXML
    private AnchorPane container;
    @FXML
    private Text productInfoNameTxt;
    @FXML
    private Text pricePerProductTxt;
    @FXML
    private Text amountTxt;
    @FXML
    private Text priceTxt;
    public String priceToString(double price){
        StringBuilder sb = new StringBuilder(String.valueOf((long) price));
        for(int i = sb.length() - 3; i > 0; i-=3){
            sb.insert(i, '.');
        }
        return sb.toString();
    }
    public void setDataContext(SaleBillItem item){
        if(item == null) return;
        productInfoNameTxt.setText(item.getProductInfo().getTitle());
        pricePerProductTxt.setText(priceToString(item.getPricePerProduct()));
        amountTxt.setText(String.valueOf(item.getAmount()));
        priceTxt.setText(priceToString(item.getPrice()));
    }
}
