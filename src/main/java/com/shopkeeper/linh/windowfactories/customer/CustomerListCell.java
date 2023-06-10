package com.shopkeeper.linh.windowfactories.customer;

import com.shopkeeper.linh.models.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;

public class CustomerListCell  extends ListCell<Customer> {
    private CustomerListCellController controller = null;
    @Override
    public void updateItem(Customer data, boolean empty) {
        super.updateItem(data, empty);
        if (empty || data == null) {
            if(controller != null) controller.setDataContext(null);
            setText(null);
            setGraphic(null);
            return;
        }
        if (controller == null) {
            controller = CustomerListCellController.getController();
        }
        controller.setDataContext(data);
        setGraphic(controller.getContainer());

    }
}