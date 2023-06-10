package com.shopkeeper.linh.windowfactories.utilities;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.models.Staff;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class CustomerToStringCell extends ListCell<Customer> {
    private ChangeListener<String> titleChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setText(newValue);
        }
    };
    private Customer oldData = null;
    @Override
    public void updateItem(Customer data, boolean empty)
    {
        if (empty || data == null) {
            if(oldData != null) {
                oldData.nameProperty().removeListener(titleChangeListener);
                oldData = null;
            }
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        if(data == oldData) return;
        if(oldData != null) {
            oldData.nameProperty().removeListener(titleChangeListener);
        }
        data.nameProperty().addListener(titleChangeListener);
        setText(data.getName());
        oldData = data;

    }
}
