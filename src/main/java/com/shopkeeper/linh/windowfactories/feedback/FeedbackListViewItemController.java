package com.shopkeeper.linh.windowfactories.feedback;

import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Feedback;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;

public class FeedbackListViewItemController {
    public static FeedbackListViewItemController getController(Feedback feedback){
        FXMLLoader fxmlLoader = new FXMLLoader(FeedbackListViewItemController.class.getResource("feedback-list-view-item.fxml"));

        Parent template;
        try
        {
            template = fxmlLoader.load();
            FeedbackListViewItemController controller = fxmlLoader.getController();
            controller.setDataContext(feedback);
            return controller;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public BorderPane getContainer(){
        return container;
    }
    @FXML
    private BorderPane container;
    @FXML
    private Text titleTxt;
    @FXML
    private Text timeTxt;
    @FXML
    private Text aboutTxt;
    @FXML
    private Text typeTxt;
    @FXML
    private Text beUsefulTxt;
    @FXML
    private Text readMark;
    public void setIsUseful(boolean isUseful){
        if(isUseful){
            beUsefulTxt.setText("Hữu ích");
            beUsefulTxt.setFill(Paint.valueOf("#1ff4a2"));
        }
        else{
            beUsefulTxt.setText("Không hữu ích");
            beUsefulTxt.setFill(Paint.valueOf("#cfcfcf"));
        }
    }
    public void setIsRead(boolean isRead){
        if(isRead){
            readMark.setVisible(true);
        }
        else{
            readMark.setVisible(false);
        }
    }
    public void setDataContext(Feedback feedback){
        setIsRead(feedback.isRead());
        if(feedback.getTitle().length() > 49){
            titleTxt.setText(feedback.getTitle().substring(0, 49) + "...");
        }
        else{
            titleTxt.setText(feedback.getTitle());
        }
        timeTxt.setText(feedback.getTime().toString());
        switch (feedback.getFeedbackAbout()){
            case Staff:
                aboutTxt.setText("Nhân viên");
                break;
            case ProductInfo:
                try {
                    String type = "Sách";
                    ProductInfo pi = feedback.getProductInfoTarget();
                    if(pi instanceof MusicInfo) type = "Bản nhạc";
                    else if(pi instanceof FilmInfo) type = "Bộ phim";
                    aboutTxt.setText(type);
                }catch (Exception e){
                    e.printStackTrace();
                    aboutTxt.setText("???");
                }
                break;
            case  Product:
                aboutTxt.setText("Sản phẩm");
                break;
            case Service:
                aboutTxt.setText("Dịch vụ");
                break;
        }
        switch (feedback.getFeedbackType()){
            case Suggestions:
                typeTxt.setText("Đề xuất");
                typeTxt.setFill(Paint.valueOf("#ffffff"));
                break;
            case Unsatisfied:
                typeTxt.setText("Không hài lòng");
                typeTxt.setFill(Paint.valueOf("#ff4d4d"));
                break;
            case Satisfied:
                typeTxt.setText("Hài lòng");
                typeTxt.setFill(Paint.valueOf("#acff69"));
                break;
        }
        if(feedback.getIsUseful()){
            beUsefulTxt.setText("Hữu ích");
            beUsefulTxt.setFill(Paint.valueOf("#1ff4a2"));
        }
        else{
            beUsefulTxt.setText("Không hữu ích");
            beUsefulTxt.setFill(Paint.valueOf("#cfcfcf"));
        }
    }
}
