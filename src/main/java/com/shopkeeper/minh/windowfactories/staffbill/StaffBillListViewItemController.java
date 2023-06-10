package com.shopkeeper.minh.windowfactories.staffbill;

import com.shopkeeper.minh.models.StaffBill;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class StaffBillListViewItemController {
    public static BorderPane getBorderPane(StaffBill staffBill){
        FXMLLoader fxmlLoader = new FXMLLoader(StaffBillListViewItemController.class.getResource("staffbill-list-view-item.fxml"));

        Parent template;
        try {
            template = fxmlLoader.load();
            StaffBillListViewItemController controller = fxmlLoader.getController();
            controller.setDataContext(staffBill);
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

    public void setDataContext(StaffBill staffBill){
        nameTxt.setText(staffBill.getName());
        idTxt.setText(String.format("ID: %d", staffBill.getBillId()));
    }
}
