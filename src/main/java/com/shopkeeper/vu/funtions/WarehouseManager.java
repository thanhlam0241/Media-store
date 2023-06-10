package com.shopkeeper.vu.funtions;

import com.shopkeeper.lam.models.Product;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class WarehouseManager {
    public WarehouseManager getManager() {
        return manager;
    }
    private WarehouseManager manager;
    public void initialize() {

    }

    public int count(ProductInfo productInfo) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        int count = 0;
        ObservableList<Product> list = adapter.getAllProducts();
        for (Product product : list) {
            if (product.getProductInfo().equals(productInfo)) {
                count++;
            }
        }
        return count;
    }

    public void add(Product product) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertProduct(product);
    }

    public void remove(Product product) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteProduct(product);
    }

    public void update(Product product) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateProduct(product);
    }

    public Product findById(int productId) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        for (Product t : list) {
            if(t.getProductId()==productId){
                return t;
            }
        }
        return null;
    }
}
