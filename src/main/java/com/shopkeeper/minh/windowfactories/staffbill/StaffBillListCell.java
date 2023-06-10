package com.shopkeeper.minh.windowfactories.staffbill;

import com.shopkeeper.minh.models.StaffBill;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class StaffBillListCell extends ListCell<StaffBill> {

    @Override
    public void updateItem(StaffBill data, boolean empty){
        if(empty || data == null){
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        if (data != null){
            BorderPane container = StaffBillListViewItemController.getBorderPane(data);
            setGraphic(container);
        }
    }
}
