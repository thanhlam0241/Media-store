package com.shopkeeper.linh.windowfactories.feedback;

import com.shopkeeper.linh.models.Feedback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.util.EventListener;

public class FeedbackListCell extends ListCell<Feedback> {
    private Feedback oldData = null;
    @Override
    public void updateItem(Feedback data, boolean empty)
    {
        super.updateItem(data, empty);
        if (empty || data == null) {
            if(oldData != null){
                oldData.propertyChangeListener = null;
                oldData = null;
            }
            setText(null);
            setGraphic(null);
            return;
        }
        if(oldData != data){
            oldData = data;
            var controller = FeedbackListViewItemController.getController(data);
            BorderPane container = controller.getContainer();
            setGraphic(container);
            oldData.propertyChangeListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    switch (newValue){
                        case "isRead":
                            controller.setIsRead(data.isRead());
                            break;
                        case "isUseful":
                            controller.setIsUseful(data.getIsUseful());
                            break;
                    }
                }
            };
        }

    }
}
