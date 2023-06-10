package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.FeedbackAbout;
import com.shopkeeper.linh.models.FeedbackType;
import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackAboutForFilter;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOption;
import com.shopkeeper.linh.windowfactories.utilities.DefaultListCell;
import com.shopkeeper.linh.windowfactories.utilities.ProductInfoToStringCell;
import com.shopkeeper.linh.windowfactories.utilities.StaffToStringCell;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomerFeedbackWindowController {
    @FXML
    private AnchorPane addingFeedbackSuccessMessagePane;
    @FXML
    private VBox addingFeedbackPane;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackAboutForFilter>> feedbackAboutCombobox;
    private ComboBox<MusicInfo> musicInfosCombobox;
    private ComboBox<FilmInfo> filmInfosCombobox;
    private ComboBox<BookInfo> bookInfosCombobox;
    private ComboBox<Staff> staffsComboBox;
    private TextField otherTargetTxt;
    @FXML
    private Pane targetPane;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackType>> feedbackTypeCombobox;
    @FXML
    private TextField feedbackTitleTxt;
    @FXML
    private TextArea feedbackDescriptionTxt;
    @FXML
    private HBox feedbackRatePane;
    @FXML
    private Text feedbackRateDisplay;
    @FXML
    private Slider feedbackRateSlider;
    private void displayAddingFeedbackSuccessMessagePane(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setFromY(-34);
        translateTransition.setToY(0);
        //translateTransition.setAutoReverse(true);
        translateTransition.setNode(addingFeedbackSuccessMessagePane);
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.play();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            translateTransition.setFromY(0);
            translateTransition.setToY(-34);
            translateTransition.play();
        }, 3, TimeUnit.SECONDS);
    }
    @FXML
    private void createNewFeedback(){
        Feedback feedback = new Feedback();
        StringBuilder sb = new StringBuilder();
        feedback.setTitle("");
        if(feedbackAboutCombobox.getValue() == null){
            sb.append("- Chưa chọn loại phản hồi\n");
        }
        else switch (feedbackAboutCombobox.getValue().getValue()){

            case MusicInfo -> {
                if(musicInfosCombobox.getValue() == null){
                    sb.append("- Chưa chọn bản nhạc.\n");
                }
                else{
                    try{
                        feedback.setFeedbackAbout(FeedbackAbout.ProductInfo);
                        feedback.setProductInfoTarget(musicInfosCombobox.getValue());
                        feedback.setProductInfoRating(((int) feedbackRateSlider.getValue()));
                        feedback.setTitle("");
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }

                }
            }
            case FilmInfo -> {
                if(filmInfosCombobox.getValue() == null){
                    sb.append("- Chưa chọn bộ phim.\n");
                }
                else{
                    try{
                        feedback.setFeedbackAbout(FeedbackAbout.ProductInfo);
                        feedback.setProductInfoTarget(filmInfosCombobox.getValue());
                        feedback.setProductInfoRating(((int) feedbackRateSlider.getValue()));
                        feedback.setTitle("");
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }

                }
            }
            case BookInfo -> {
                if(bookInfosCombobox.getValue() == null){
                    sb.append("- Chưa chọn bộ sách.\n");
                }
                else{
                    try{
                        feedback.setFeedbackAbout(FeedbackAbout.ProductInfo);
                        feedback.setProductInfoTarget(bookInfosCombobox.getValue());
                        feedback.setProductInfoRating(((int) feedbackRateSlider.getValue()));
                        feedback.setTitle("");
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }

                }
            }
            case Staff -> {
                if(staffsComboBox.getValue() == null){
                    sb.append("- Chưa chọn nhân viên.\n");
                }
                else{
                    try{
                        feedback.setFeedbackAbout(FeedbackAbout.Staff);
                        feedback.setStaffTarget(staffsComboBox.getValue());
                        feedback.setTitle("");
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }

                }
            }
            case Product -> {
                long id = 0;
                try{
                    id = Long.parseLong(otherTargetTxt.getText());
                    if(id <= 0) throw new NumberFormatException();
                }catch(NumberFormatException e){
                    sb.append("- Id sản phẩm nhập vào không hợp lệ (Id sản phẩm là một số lớn hơn 0).\n");
                }
                if(id > 0){
                    var list = DatabaseAdapter.getDbAdapter().getAllProducts();
                    int i = 0;
                    for(; i < list.size(); i++){
                        if(list.get(i).getProductId() == id){
                            try{
                                feedback.setFeedbackAbout(FeedbackAbout.Product);
                                feedback.setProductTarget(list.get(i));
                                feedback.setTitle("");
                            }catch(Exception e){
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                    if(i == list.size()) {
                        sb.append("- Id sản phẩm không tồn tại.\n");
                    }
                }
            }
            case Service -> {
                try{
                    feedback.setFeedbackAbout(FeedbackAbout.Service);
                    String serviceName = otherTargetTxt.getText();
                    if(serviceName != null && (serviceName = serviceName.trim()).length() > 0){
                        feedback.setTitle(serviceName + ": ");
                    }
                }catch(Exception e){
                    throw new RuntimeException(e);
                }

            }
        }
        if(feedbackTypeCombobox.getValue() == null){
            sb.append("- Chưa chọn trạng thái.\n");
        }
        else{
            try{
                feedback.setFeedbackType(feedbackTypeCombobox.getValue().getValue());
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        String title = feedbackTitleTxt.getText();
        if(title != null && (title = title.trim()).length() > 0){
            feedback.setTitle(feedback.getTitle() + title);
        }
        else {
            sb.append("- Chưa có tiêu đề.\n");
        }
        String desciption = feedbackDescriptionTxt.getText();
        if(desciption != null && (desciption = desciption.trim()).length() > 0){
            feedback.setDescription(desciption);
        }
        else {
            sb.append("- Chưa có miêu tả.\n");
        }
        if(sb.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Phản hồi không hợp lệ");
            alert.setHeaderText("Phản hồi không hợp lệ do thiếu những điều sau:");
            alert.setContentText(sb.toString());
            alert.showAndWait();
            return;
        }
        feedback.setIsUseful(false);
        feedback.setTime(LocalDate.now());
        feedback.setRead(false);
        DatabaseAdapter.getDbAdapter().insertFeedback(feedback);
        closeAddingFeedbackPane();
        displayAddingFeedbackSuccessMessagePane();
    }
    @FXML
    private void resetAddingFeedbackPane(){
        feedbackAboutCombobox.getSelectionModel().clearSelection();
        feedbackTypeCombobox.getSelectionModel().clearSelection();
        feedbackTitleTxt.setText("");
        feedbackDescriptionTxt.setText("");
        feedbackRateSlider.setValue(1);
    }
    @FXML
    private void openAddingFeedbackPane(){
        resetAddingFeedbackPane();
        addingFeedbackPane.setVisible(true);
    }
    @FXML
    private void closeAddingFeedbackPane(){
        addingFeedbackPane.setVisible(false);
    }
    public void initialize(){
        addingFeedbackSuccessMessagePane.setTranslateY(-34);
        musicInfosCombobox = new ComboBox<>(DatabaseAdapter.getDbAdapter().getAllMusicInfos());
        musicInfosCombobox.setButtonCell(new ProductInfoToStringCell<>());
        musicInfosCombobox.setCellFactory(new Callback<ListView<MusicInfo>, ListCell<MusicInfo>>() {
            @Override
            public ListCell<MusicInfo> call(ListView<MusicInfo> param) {
                return new ProductInfoToStringCell<>();
            }
        });
        filmInfosCombobox = new ComboBox<>(DatabaseAdapter.getDbAdapter().getAllFilmInfos());
        filmInfosCombobox.setButtonCell(new ProductInfoToStringCell<>());
        filmInfosCombobox.setCellFactory(new Callback<ListView<FilmInfo>, ListCell<FilmInfo>>() {
            @Override
            public ListCell<FilmInfo> call(ListView<FilmInfo> param) {
                return new ProductInfoToStringCell<>();
            }
        });
        bookInfosCombobox = new ComboBox<>(DatabaseAdapter.getDbAdapter().getAllBookInfos());
        bookInfosCombobox.setButtonCell(new ProductInfoToStringCell<>());
        bookInfosCombobox.setCellFactory(new Callback<ListView<BookInfo>, ListCell<BookInfo>>() {
            @Override
            public ListCell<BookInfo> call(ListView<BookInfo> param) {
                return new ProductInfoToStringCell<>();
            }
        });
        staffsComboBox = new ComboBox<>(DatabaseAdapter.getDbAdapter().getAllStaffs());
        staffsComboBox.setButtonCell(new StaffToStringCell());
        staffsComboBox.setCellFactory(new Callback<ListView<Staff>, ListCell<Staff>>() {
            @Override
            public ListCell<Staff> call(ListView<Staff> param) {
                return new StaffToStringCell();
            }
        });
        otherTargetTxt = new TextField();
        feedbackAboutCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(FeedbackAboutForFilter.Service, "Dịch vụ cửa hàng"),
                new ComboBoxOption<>(FeedbackAboutForFilter.Staff, "Nhân viên phục vụ"),
                new ComboBoxOption<>(FeedbackAboutForFilter.MusicInfo, "Bản nhạc"),
                new ComboBoxOption<>(FeedbackAboutForFilter.FilmInfo, "Bộ phim"),
                new ComboBoxOption<>(FeedbackAboutForFilter.BookInfo, "Bộ sách"),
                new ComboBoxOption<>(FeedbackAboutForFilter.Product, "Sản phẩm")
        ));
        feedbackAboutCombobox.setButtonCell(new DefaultListCell<>());
        feedbackAboutCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) {
                targetPane.getChildren().clear();
                return;
            }
            switch (newValue.getValue()){
                case MusicInfo -> {
                    //musicInfosCombobox.setValue(null);
                    musicInfosCombobox.getSelectionModel().clearSelection();
                    musicInfosCombobox.setPromptText("Chọn bản nhạc");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(musicInfosCombobox);
                    feedbackRatePane.setVisible(true);
                    musicInfosCombobox.setMaxWidth(280);
                }
                case FilmInfo -> {
                    filmInfosCombobox.getSelectionModel().clearSelection();
                    filmInfosCombobox.setPromptText("Chọn bộ phim");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(filmInfosCombobox);
                    feedbackRatePane.setVisible(true);
                    filmInfosCombobox.setMaxWidth(280);
                }
                case BookInfo -> {
                    bookInfosCombobox.getSelectionModel().clearSelection();
                    bookInfosCombobox.setPromptText("Chọn bộ sách");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(bookInfosCombobox);
                    feedbackRatePane.setVisible(true);
                    bookInfosCombobox.setMaxWidth(280);
                }
                case Staff -> {
                    staffsComboBox.getSelectionModel().clearSelection();
                    staffsComboBox.setPromptText("Chọn nhân viên");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(staffsComboBox);
                    feedbackRatePane.setVisible(false);
                    staffsComboBox.setMaxWidth(280);
                }
                case Product -> {
                    otherTargetTxt.setText("");
                    otherTargetTxt.setPromptText("Nhập mã sản phẩm");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(otherTargetTxt);
                    feedbackRatePane.setVisible(false);
                    otherTargetTxt.setMaxWidth(280);
                }
                case Service -> {
                    otherTargetTxt.setText("");
                    otherTargetTxt.setPromptText("Nhập tên dịch vụ");
                    targetPane.getChildren().clear();
                    targetPane.getChildren().add(otherTargetTxt);
                    feedbackRatePane.setVisible(false);
                    otherTargetTxt.setMaxWidth(280);
                }
                default -> {
                    targetPane.getChildren().clear();
                    feedbackRatePane.setVisible(false);
                }
            }
        });
        feedbackRateDisplay.setText("1 sao");
        feedbackRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            feedbackRateDisplay.setText(newValue.intValue() + " sao");
        });
        feedbackTypeCombobox.setButtonCell(new DefaultListCell<>());
        feedbackTypeCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(FeedbackType.Suggestions, "Bình thường"),
                new ComboBoxOption<>(FeedbackType.Unsatisfied, "Không hài lòng"),
                new ComboBoxOption<>(FeedbackType.Satisfied, "Hài lòng")
        ));
        feedbackRatePane.setVisible(false);
        addingFeedbackPane.setVisible(false);
    }
}
