package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListViewItemController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class SaleBillListCell extends ListCell<SaleBill> {
    private SaleBillListItemController controller = null;
    @Override
    public void updateItem(SaleBill data, boolean empty)
    {
        super.updateItem(data, empty);
        if (empty || data == null) {
            if(this.controller != null) controller.setDataContext(null);
            setText(null);
            setGraphic(null);
            return;
        }
        if(controller == null){
            controller = SaleBillListItemController.getController();
        }
        controller.setDataContext(data);
        setGraphic(controller.getContainer());

    }
}
