package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.Product;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class SaleBillItem {
    public SaleBillItem(ProductInfo productInfo){
        this.productInfo = productInfo;
        products = FXCollections.observableArrayList();
        productSet = new TreeSet<>(new ProductComparatorForSortedSet());
        //unmodifiedProducts = FXCollections.unmodifiableObservableList(products);
    }
    private ProductInfo productInfo;
    private ObservableList<Product> products;
    private SortedSet<Product> productSet;
    //private ObservableList<Product> unmodifiedProducts;
    public ObservableList<Product> getProducts() {
        return products;
    }
    public boolean addProduct(Product product) {
        if(productSet.add(product)){
            products.add(product);
            return true;
        }
        return false;
    }
//    private IntegerProperty index = new SimpleIntegerProperty();
//
//    public int getIndex() {
//        return index.get();
//    }

//    public IntegerProperty indexProperty() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index.set(index);
//    }
//
    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public int getAmount() {
        return products.size();
    }
    public double getPrice() {
        return products.size() *  getPricePerProduct();
    }
    public double getPricePerProduct() {
        if(products.size() == 0) return 0;
        if(products.get(0).getSaleBill() != null) return products.get(0).getSaleValue();
        else return productInfo.getCurrentSalePrice();
    }
    public SaleBillItem clone(){
        var x = new SaleBillItem(productInfo);
        x.products.addAll(this.products);
        x.productSet.addAll(this.products);
        return x;
    }
}
