package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.Product;

import java.util.Comparator;

public class ProductComparatorForSortedSet implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return Long.compare(o1.getProductId(), o2.getProductId());
    }
}
