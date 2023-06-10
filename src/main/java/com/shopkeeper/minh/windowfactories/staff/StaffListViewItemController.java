package com.shopkeeper.minh.windowfactories.staff;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.models.StaffState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class StaffListViewItemController {
    public static BorderPane getBorderPane(Staff staff){
        FXMLLoader fxmlLoader = new FXMLLoader(StaffListViewItemController.class.getResource("staff-list-view-item.fxml"));

        Parent template;
        try {
            template = fxmlLoader.load();
            StaffListViewItemController controller = fxmlLoader.getController();
            controller.setDataContext(staff);
            return controller.container;
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private BorderPane container;

    @FXML
    private Text nameTxt;

    @FXML
    private Text idTxt;

    @FXML
    private Text stateTxt;

    public void setDataContext(Staff staff){
        nameTxt.setText(staff.getName());
        idTxt.setText(String.format("ID: %d", staff.getStaffId()));

        StaffState state = staff.getState();

        if (state == StaffState.Working){
            stateTxt.setText("Working");
        } else if (state == StaffState.Demited) {
            stateTxt.setText("Demited");
        } else if (state == StaffState.Interviewing) {
            stateTxt.setText("Interviewing");
        } else if (state == StaffState.NotEmployed) {
            stateTxt.setText("Not employed");
        } else if (state == StaffState.Unreliable) {
            stateTxt.setText("Unreliable");
        }
        else stateTxt.setText("?");
    }
}
