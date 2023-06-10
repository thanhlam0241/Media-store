package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.SaleBill;
import javafx.scene.control.ListCell;

public class SaleBillItemListCell extends ListCell<SaleBillItem> {
    private SaleBillItemListCellController controller;
    @Override
    public void updateItem(SaleBillItem data, boolean empty)
    {
        super.updateItem(data, empty);
        if (empty || data == null) {
            if(this.controller != null) controller.setDataContext(null);
            setText(null);
            setGraphic(null);
            return;
        }
        if(controller == null){
            controller = SaleBillItemListCellController.getController();
        }
        controller.setDataContext(data);
        setGraphic(controller.getContainer());

    }
}
