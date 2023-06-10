package com.shopkeeper.minh.windowfactories.staff;

import com.shopkeeper.linh.models.Staff;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class StaffListCell extends ListCell<Staff> {
    @Override
    public void updateItem(Staff data, boolean empty){
        if(empty || data == null){
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        if (data != null){
            BorderPane container = StaffListViewItemController.getBorderPane(data);
            setGraphic(container);
        }
    }
}
