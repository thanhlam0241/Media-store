package com.shopkeeper.linh.windowfactories.utilities;

import javafx.scene.control.ListCell;

public class DefaultListCell<T> extends ListCell<T> {
    @Override
    public void updateItem(T data, boolean empty)
    {
        if (empty || data == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);

        if(data != null)
        {
            setText(data.toString());
        }
    }
}
