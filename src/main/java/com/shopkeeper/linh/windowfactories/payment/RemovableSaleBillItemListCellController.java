package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.Product;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class RemovableSaleBillItemListCellController {
    public static RemovableSaleBillItemListCellController getController(SaleBillItemRemoveListener listener, BooleanProperty removable){
        FXMLLoader fxmlLoader = new FXMLLoader(RemovableSaleBillItemListCellController.class.getResource("removable-salebill-item-list-item.fxml"));

        Parent template;
        try
        {
            template = fxmlLoader.load();
            RemovableSaleBillItemListCellController controller = fxmlLoader.getController();
            controller.listener = listener;
            controller.bindRemovable(removable);
            return controller;
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
    private final ListChangeListener<Product> listChangeListener = new ListChangeListener<>() {
        @Override
        public void onChanged(Change c) {
            amountTxt.setText(String.valueOf(oldItem.getAmount()));
            priceTxt.setText(priceToString(oldItem.getPrice()));
        }
    };
    private SaleBillItem oldItem = null;
    public void setDataContext(SaleBillItem item){
        if(oldItem == item) return;
        if(oldItem != null){
            oldItem.getProducts().removeListener(listChangeListener);
        }
        if(item != null) {
            productInfoNameTxt.setText(item.getProductInfo().getTitle());
            pricePerProductTxt.setText(priceToString(item.getPricePerProduct()));
            amountTxt.setText(String.valueOf(item.getAmount()));
            priceTxt.setText(priceToString(item.getPrice()));
            item.getProducts().addListener(listChangeListener);
        }
        oldItem = item;
    }
    public SaleBillItemRemoveListener listener = null;
    public void remove(){
        listener.remove(oldItem);
    }
    @FXML
    private Button removeBtn;
    public void bindRemovable(BooleanProperty removable){
        removeBtn.visibleProperty().bind(removable);
    }
}
