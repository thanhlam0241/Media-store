package com.shopkeeper.minh.windowfactories.shift;

import com.shopkeeper.minh.models.Shift;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class ShiftListCell extends ListCell<Shift> {
    @Override
    public void updateItem(Shift data, boolean empty){
        if(empty || data == null){
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        if (data != null){
            BorderPane container = ShiftListViewItemController.getBorderPane(data);
            setGraphic(container);
        }
    }
}
