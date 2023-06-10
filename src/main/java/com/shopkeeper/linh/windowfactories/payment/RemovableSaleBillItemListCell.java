package com.shopkeeper.linh.windowfactories.payment;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.ListCell;

public class RemovableSaleBillItemListCell  extends ListCell<SaleBillItem> {
    public SaleBillItemRemoveListener listener = null;
    public RemovableSaleBillItemListCell(SaleBillItemRemoveListener listener, BooleanProperty removable){
        super();
        this.listener = listener;
        this.removable = removable;
    }
    private BooleanProperty removable;
    private RemovableSaleBillItemListCellController controller;
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
            controller = RemovableSaleBillItemListCellController.getController(listener, removable);
        }
        controller.setDataContext(data);
        setGraphic(controller.getContainer());

    }
}
