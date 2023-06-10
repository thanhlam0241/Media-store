package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.FeedbackAbout;
import com.shopkeeper.linh.models.FeedbackType;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListCell;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListOrder;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackObservableList;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackAboutForFilter;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOption;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;

public class FeedbackWindowController {
    //region Feedback Filter Panel
    @FXML
    private TextField filterSubnameTxtBox;
    @FXML
    private TextField filterTargetNameTxt;
    @FXML
    private GridPane filterPanel;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackAboutForFilter>> feedbackAboutCombobox;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackType>> feedbackTypeCombobox;
    @FXML
    private ComboBox<ComboBoxOption<Boolean>> beUsefulCombobox;
    @FXML
    private ComboBox<ComboBoxOption<Boolean>> readCombobox;
    @FXML
    private DatePicker filterFromDateBox;
    @FXML
    private DatePicker filterToDateBox;
    @FXML
    private Button closeFilterPanelBtn;
    @FXML
    private void resetFilterPanel(){
        filterSubnameTxtBox.setText("");
        feedbackAboutCombobox.getSelectionModel().selectFirst();
        feedbackTypeCombobox.getSelectionModel().selectFirst();
        filterTargetNameTxt.setText("");
        beUsefulCombobox.getSelectionModel().selectFirst();
        filterFromDateBox.setValue(null);
        filterToDateBox.setValue(null);
    }
    @FXML
    private void filterList(){
//        FeedbackAbout feedbackAbout = feedbackAboutCombobox.getValue().getValue();
//        FeedbackType feedbackType = feedbackTypeCombobox.getValue().getValue();
//        boolean isUseful = beUsefulCombobox.getValue().getValue();
//        String subName = filterSubnameTxtBox.getText();
//        String targetName = filterTargetNameTxt.getText();
//        LocalDate fromDate = filterFromDateBox.getValue();
//        LocalDate toDate = filterToDateBox.getValue();
        FilteredList<Feedback> filteredList = new FilteredList<>(feedbackFullList);
        if(!ComboBoxOption.isSelectAllOption(readCombobox.getValue())){
            boolean isRead = readCombobox.getValue().getValue();
            filteredList = filteredList.filtered(feedback -> {
                return feedback.isRead() == isRead;
            });
        }
        if(!ComboBoxOption.isSelectAllOption(feedbackAboutCombobox.getValue())){
            switch (feedbackAboutCombobox.getValue().getValue()){
                case Staff -> filteredList = filteredList.filtered(feedback -> {
                    return feedback.getFeedbackAbout() == FeedbackAbout.Staff;
                });
                case Product -> filteredList = filteredList.filtered(feedback -> {
                    return feedback.getFeedbackAbout() == FeedbackAbout.Product;
                });
                case ProductInfo -> filteredList = filteredList.filtered(feedback -> {
                    return feedback.getFeedbackAbout() == FeedbackAbout.ProductInfo;
                });
                case MusicInfo -> filteredList = filteredList.filtered(feedback -> {
                    try{
                        return feedback.getFeedbackAbout() == FeedbackAbout.ProductInfo
                                && feedback.getProductInfoTarget() instanceof MusicInfo;
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });
                case FilmInfo -> filteredList = filteredList.filtered(feedback -> {
                    try{
                        return feedback.getFeedbackAbout() == FeedbackAbout.ProductInfo
                                && feedback.getProductInfoTarget() instanceof FilmInfo;
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });
                case BookInfo -> filteredList = filteredList.filtered(feedback -> {
                    try{
                        return feedback.getFeedbackAbout() == FeedbackAbout.ProductInfo
                                && feedback.getProductInfoTarget() instanceof BookInfo;
                    }
                    catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });
                case Service -> filteredList = filteredList.filtered(feedback -> {
                    return feedback.getFeedbackAbout() == FeedbackAbout.Service;
                });
            }

        }
        if(!ComboBoxOption.isSelectAllOption(feedbackTypeCombobox.getValue())){
            FeedbackType feedbackType = feedbackTypeCombobox.getValue().getValue();
            filteredList = filteredList.filtered(feedback -> {
                return feedback.getFeedbackType() == feedbackType;
            });
        }
        if(!ComboBoxOption.isSelectAllOption(beUsefulCombobox.getValue())){
            boolean isUseful = beUsefulCombobox.getValue().getValue();
            filteredList = filteredList.filtered(feedback -> {
                return feedback.getIsUseful() == isUseful;
            });
        }
        LocalDate fromDate = filterFromDateBox.getValue();
        LocalDate toDate = filterToDateBox.getValue();
        if(fromDate != null || toDate != null){
            filteredList = filteredList.filtered(feedback -> {
                if(fromDate != null && feedback.getTime().compareTo(fromDate) < 0) return false;
                if(toDate != null && feedback.getTime().compareTo(toDate) > 0) return false;
                return true;
            });
        }
        String subName = filterSubnameTxtBox.getText();
        if(subName != null && (subName = subName.trim()).length() != 0){
            final String _subName = subName.toLowerCase();
            filteredList = filteredList.filtered(feedback -> {
                return feedback.getTitle().toLowerCase().contains(_subName);
            });
        }
        String targetName = filterTargetNameTxt.getText().toLowerCase();
        if(targetName != null && (targetName = targetName.trim()).length() != 0){
            try {
                long id = Long.parseLong(targetName);
                filteredList = filteredList.filtered(feedback -> {
                    switch (feedback.getFeedbackAbout()){
                        case Staff:
                            try{
                                return feedback.getStaffTarget().getStaffId() == id;
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case ProductInfo:
                            try{
                                return feedback.getProductInfoTarget().getProductInfoId() == id;
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case  Product:
                            try{
                                return feedback.getProductTarget().getProductId() == id;
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case Service:
                            return false;
                    }
                    return false;
                });
            }catch (NumberFormatException inv){
                final String _targetName = targetName;
                filteredList = filteredList.filtered(feedback -> {
                    switch (feedback.getFeedbackAbout()){
                        case Staff:
                            try{
                                return feedback.getStaffTarget().getName().toLowerCase().contains(_targetName);
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case ProductInfo:
                            try{
                                return feedback.getProductInfoTarget().getTitle().toLowerCase().contains(_targetName);
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case  Product:
                            try{
                                return feedback.getProductTarget().getProductInfo().getTitle().toLowerCase().contains(_targetName);
                            }
                            catch (Exception e){
                                throw new RuntimeException(e);
                            }
                        case Service:
                            return feedback.getTitle().toLowerCase().contains(_targetName);
                    }
                    return false;
                });
            }
        }
        feedbackList = new FeedbackObservableList(filteredList);
        feedbackList.setOrder(orderCombobox.getValue().getValue());
        feedbackListView.setItems(feedbackList);
    }
    @FXML
    private void closeFilterPanel(){
        filterPanel.setVisible(false);
        feedbackList = feedbackFullList;
        feedbackList.setOrder(orderCombobox.getValue().getValue());
        feedbackListView.setItems(feedbackList);
    }
    @FXML
    private void openFilterPanel(){
        filterPanel.setVisible(true);
    }

    private void initializeFeedbackFilterPanel(){
        filterPanel.managedProperty().bind(filterPanel.visibleProperty());
        filterPanel.setVisible(false);
        //Initialize feedback about options
        feedbackAboutCombobox.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Chọn tất cả"),
                new ComboBoxOption<>(FeedbackAboutForFilter.Service, "Dịch vụ cửa hàng"),
                new ComboBoxOption<>(FeedbackAboutForFilter.Staff, "Nhân viên phục vụ"),
                new ComboBoxOption<>(FeedbackAboutForFilter.ProductInfo, "Bản nhạc/Bộ phim/Bộ sách"),
                new ComboBoxOption<>(FeedbackAboutForFilter.MusicInfo, "Bản nhạc"),
                new ComboBoxOption<>(FeedbackAboutForFilter.FilmInfo, "Bộ phim"),
                new ComboBoxOption<>(FeedbackAboutForFilter.BookInfo, "Bộ sách"),
                new ComboBoxOption<>(FeedbackAboutForFilter.Product, "Sản phẩm")
        ));
        feedbackAboutCombobox.getSelectionModel().selectFirst();
        //Initialize feedback type options
        feedbackTypeCombobox.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Chọn tất cả"),
                new ComboBoxOption<>(FeedbackType.Suggestions, "Gợi ý/Đề xuất"),
                new ComboBoxOption<>(FeedbackType.Unsatisfied, "Không hài lòng"),
                new ComboBoxOption<>(FeedbackType.Satisfied, "Hài lòng")
        ));
        feedbackTypeCombobox.getSelectionModel().selectFirst();
        //Initialize usefulness options
        beUsefulCombobox.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Chọn tất cả"),
                new ComboBoxOption<>(true, "Hữu ích"),
                new ComboBoxOption<>(false, "Không hữu ích")
        ));
        beUsefulCombobox.getSelectionModel().selectFirst();
        readCombobox.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Cả đã đọc và chưa đọc"),
                new ComboBoxOption<>(true, "Đã đọc"),
                new ComboBoxOption<>(false, "Chưa đọc")
        ));
        readCombobox.getSelectionModel().selectFirst();
    }
    //endregion
    //region Feedback Display
    @FXML
    private VBox feedbackDisplayer;
    @FXML
    private Text feedbackHeaderDisplayer;
    @FXML
    private Text feedbackTargetDisplayer;
    @FXML
    private Text feedbackDescriptionDisplayer;
    private void initializeFeedbackDisplayer(){
        feedbackDisplayer.setVisible(false);
    }
    private void displayFeedback(Feedback feedback){
        if(feedback == null) {
            feedbackDisplayer.setVisible(false);
            return;
        }
        feedbackDisplayer.setVisible(true);
        feedbackHeaderDisplayer.setText(feedback.getTitle());
        String s;
        switch (feedback.getFeedbackAbout()) {
            case Staff:
                try {
                    s = String.format("Về: Nhân viên %s (Mã nhân viên: %d)",
                            feedback.getStaffTarget().getName(),
                            feedback.getStaffTarget().getStaffId()
                            );
                }catch (Exception e){
                    e.printStackTrace();
                    s = "";
                }
                feedbackTargetDisplayer.setText(s);
                feedbackDescriptionDisplayer.setText(feedback.getDescription());
                break;
            case ProductInfo:
                try {
                    String type = "Sách";
                    ProductInfo pi = feedback.getProductInfoTarget();
                    if(pi instanceof MusicInfo) type = "Bản nhạc";
                    else if(pi instanceof FilmInfo) type = "Bộ phim";
                    s = String.format("Về: %s %s (%d)",
                            type,
                            pi.getTitle(),
                            pi.getProductInfoId()
                    );
                    feedbackDescriptionDisplayer.setText(
                            "Đánh giá: " + feedback.getProductInfoRating() + " sao.\n\n"
                            + feedback.getDescription());
                }catch (Exception e){
                    e.printStackTrace();
                    s = "";
                    feedbackDescriptionDisplayer.setText(feedback.getDescription());
                }
                feedbackTargetDisplayer.setText(s);
                break;
            case  Product:
                try {
                    String type = "Sách";
                    ProductInfo pi = feedback.getProductTarget().getProductInfo();
                    if(pi instanceof MusicInfo) type = "Bản nhạc";
                    else if(pi instanceof FilmInfo) type = "Bộ phim";
                    s = String.format("Về: Sản phẩm mã %d (Loại: %s %s (%d))",
                            feedback.getProductTarget().getProductId(),
                            type,
                            pi.getTitle(),
                            pi.getProductInfoId()
                    );
                }catch (Exception e){
                    e.printStackTrace();
                    s = "";
                }
                feedbackTargetDisplayer.setText(s);
                feedbackDescriptionDisplayer.setText(feedback.getDescription());
                break;
            case Service:
                feedbackTargetDisplayer.setText("Về: Dịch vụ");
                feedbackDescriptionDisplayer.setText(feedback.getDescription());
                break;
        }
    }
    //endregion
    //region Feedback List
    @FXML
    private HBox feedbackToolbar;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackListOrder>> orderCombobox;
    @FXML
    private ListView<Feedback> feedbackListView;
    private FeedbackObservableList feedbackList;
    private FeedbackObservableList feedbackFullList;
    @FXML
    private Button markAsUsefulBtn;
    @FXML
    private void markSelectedFeedbackAsUserful(){
        Feedback item = feedbackListView.getSelectionModel().getSelectedItem();
        if(item == null) return;
        item.setIsUseful(!item.getIsUseful());
        DatabaseAdapter.getDbAdapter().updateIsUseful(item);
        if(item.getIsUseful()){

            markAsUsefulBtn.setText("Đánh dấu KHÔNG hữu ích");

        }
        else{

            markAsUsefulBtn.setText("Đánh dấu hữu ích");
        }
    }
    private void initializeFeedbackList(){
        //Initialize feedback list order options
        orderCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(FeedbackListOrder.TimeAscending, "Cũ nhất"),
                new ComboBoxOption<>(FeedbackListOrder.TimeDescending, "Mới nhất"),
                new ComboBoxOption<>(FeedbackListOrder.TitleAscending, "A->z"),
                new ComboBoxOption<>(FeedbackListOrder.TitleDescending, "z->A")
        ));
        orderCombobox.getSelectionModel().selectFirst();
        feedbackListView.setCellFactory(new Callback<ListView<Feedback>, ListCell<Feedback>>()
        {
            @Override
            public ListCell<Feedback> call(ListView<Feedback> listView)
            {
                return new FeedbackListCell();
            }
        });
        try{
            DatabaseAdapter databaseAdapter = DatabaseAdapter.getDbAdapter();
            feedbackFullList = new FeedbackObservableList(databaseAdapter.getAllFeedbacks());
            feedbackList = feedbackFullList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        feedbackListView.setItems(feedbackList);
        feedbackListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayFeedback(newValue);
            if(newValue == null){
                feedbackToolbar.setVisible(false);
            }else{
                if(newValue.getIsUseful()){

                    markAsUsefulBtn.setText("Đánh dấu KHÔNG hữu ích");

                }
                else{

                    markAsUsefulBtn.setText("Đánh dấu hữu ích");
                }
                feedbackToolbar.setVisible(true);

            }
            DatabaseAdapter.getDbAdapter().markAsRead(newValue);
        });
        orderCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            feedbackList.setOrder(newValue.getValue());
        });
    }
    //endregion
    public FeedbackWindowController() {

    }


    public void initialize()
    {
        initializeFeedbackFilterPanel();
        initializeFeedbackList();
        initializeFeedbackDisplayer();
    }
}
