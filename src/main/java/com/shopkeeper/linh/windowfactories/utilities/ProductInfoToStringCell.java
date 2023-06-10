package com.shopkeeper.linh.windowfactories.utilities;

import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Staff;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;

public class ProductInfoToStringCell<T extends ProductInfo> extends ListCell<T> {

    @Override
    public void updateItem(T data, boolean empty)
    {

        if (empty || data == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        setText(String.format("%s [%d]",
                data.getTitle(),
                data.getProductInfoId()
        ));
    }
}
