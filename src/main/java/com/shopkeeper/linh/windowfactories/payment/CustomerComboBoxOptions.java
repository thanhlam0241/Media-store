package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOptionList;
import javafx.collections.ObservableList;

public class CustomerComboBoxOptions extends ComboBoxOptionList<Customer> {
    public CustomerComboBoxOptions(ObservableList<Customer> list) {
        super(list);
    }

    @Override
    protected String getTitle(Customer obj) {
        return obj.getName();
    }
}
