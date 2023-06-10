package com.shopkeeper.linh.windowfactories.utilities;

import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListViewItemController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

public class StaffToStringCell extends ListCell<Staff> {
    private ChangeListener<String> titleChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            setText(String.format("%s [%d]",
                    newValue,
                    oldData.getStaffId()
            ));
        }
    };
    private Staff oldData = null;
    @Override
    public void updateItem(Staff data, boolean empty)
    {

        if (empty || data == null) {
            if(oldData != null) {
                oldData.nameProperty().removeListener(titleChangeListener);
                oldData = null;
            }
            setText(null);
            setGraphic(null);
            return;
        }
        super.updateItem(data, empty);
        if(data == oldData) return;
        if(oldData != null) {
            oldData.nameProperty().removeListener(titleChangeListener);
        }
        data.nameProperty().addListener(titleChangeListener);
        setText(String.format("%s [%d]",
                data.getName(),
                data.getStaffId()
        ));
        oldData = data;
    }
}
