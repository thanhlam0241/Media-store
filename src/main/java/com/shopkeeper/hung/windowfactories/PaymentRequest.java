package com.shopkeeper.hung.windowfactories;

import com.shopkeeper.lam.models.ProductInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class PaymentRequest {
    private ObservableList<Pair<ProductInfo, Integer>> chosenProduct = FXCollections.observableArrayList();
    public void addProductToCart(ProductInfo productInfo , Integer amount){
        for( var x: chosenProduct){
            ProductInfo temp= x.getKey();
            if(temp.getProductInfoId() == productInfo.getProductInfoId()) {
                if(x.getValue()+amount>productInfo.getNumberOfProduct())
                    return;
                chosenProduct.add(new Pair<>(productInfo,x.getValue()+amount));
                chosenProduct.remove(x);
                chosenProduct.sort((o1, o2) -> Integer.compare(o1.getKey().getTitle().compareTo(o2.getKey().getTitle()), 0));
                return;
            }
        }
        chosenProduct.add(new Pair<>(productInfo, amount));
        chosenProduct.sort((o1, o2) -> Integer.compare(o1.getKey().getTitle().compareTo(o2.getKey().getTitle()), 0));
    }
    public void remove(ProductInfo productInfo){

    }
    public ObservableList<Pair<ProductInfo, Integer>> getChosenProduct(){
        return chosenProduct;
    }
}
