package com.shopkeeper.minh.windowfactories.shift;

import com.shopkeeper.minh.models.Shift;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ShiftListViewItemController {
    public static BorderPane getBorderPane(Shift shift){
        FXMLLoader fxmlLoader = new FXMLLoader(ShiftListViewItemController.class.getResource("shift-list-view-item.fxml"));

        Parent template;
        try {
            template = fxmlLoader.load();
            ShiftListViewItemController controller = fxmlLoader.getController();
            controller.setDataContext(shift);
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
    private Text dateOfWeek;

    public void setDataContext(Shift shift){
        String s = String.format("Ca %02d:%02d - %02d:%02d",
                shift.getStartTime().getHour(),
                shift.getStartTime().getMinute(),
                shift.getEndTime().getHour(),
                shift.getEndTime().getMinute());
        nameTxt.setText(s);
        idTxt.setText(String.format("ID: %d", shift.getShiftId()));

        switch (shift.getDateOfWeek()){
            case 1:
                dateOfWeek.setText("Thứ 2");
                break;
            case 2:
                dateOfWeek.setText("Thứ 3");
                break;
            case 3:
                dateOfWeek.setText("Thứ 4");
                break;
            case 5:
                dateOfWeek.setText("Thứ 6");
                break;
            case 4:
                dateOfWeek.setText("Thứ 5");
                break;
            case 6:
                dateOfWeek.setText("Thứ 7");
                break;
            case 7:
                dateOfWeek.setText("Chủ nhật");
                break;
        }
    }

}
