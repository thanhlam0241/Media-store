package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.payment.*;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOption;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOptionList;
import com.shopkeeper.linh.windowfactories.utilities.CustomerToStringCell;
import com.shopkeeper.linh.windowfactories.utilities.DefaultListCell;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;

public class PaymentWindowController {
    @FXML
    private AnchorPane mainContainer;
    //region SaleBill Filter Panel
    @FXML
    private TextField filterSubnameTxtBox;
    @FXML
    private TextField filterFromValueTxt;
    @FXML
    private TextField filterToValueTxt;
    @FXML
    private GridPane filterPanel;
    @FXML
    private ComboBox<ComboBoxOption<Customer>> customerCombobox;
    @FXML
    private TextField filterLocationTxt;
    @FXML
    private DatePicker filterFromDateBox;
    @FXML
    private DatePicker filterToDateBox;
    @FXML
    private void resetFilterPanel(){
        filterSubnameTxtBox.setText("");
        filterFromValueTxt.setText("");
        filterToValueTxt.setText("");
        filterLocationTxt.setText("");
        customerCombobox.getSelectionModel().selectFirst();
        filterFromDateBox.setValue(null);
        filterToDateBox.setValue(null);
    }
    @FXML
    private void filterList(){
        String fromValueString = filterFromValueTxt.getText();
        String toValueString = filterToValueTxt.getText();
        fromValueString = fromValueString == null?"":fromValueString.trim();
        toValueString = toValueString == null?"":toValueString.trim();
        StringBuilder valueRangeErrorsSb = new StringBuilder();
        double fromValue = 0;
        double toValue = Double.MAX_VALUE;
        if(fromValueString.length() > 0){
            try{
                fromValue = Double.parseDouble(filterFromValueTxt.getText());
            }
            catch (NumberFormatException e){
                valueRangeErrorsSb.append("- Giá trị " + filterFromValueTxt.getText() + " không phải là số.\n");
            }
        }
        if(toValueString.length() > 0){
            try{
                toValue = Double.parseDouble(filterToValueTxt.getText());
            }
            catch (NumberFormatException e){
                valueRangeErrorsSb.append("- Giá trị " + filterToValueTxt.getText() + " không phải là số.\n");
            }
        }
        if(valueRangeErrorsSb.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Phạm vi giá trị hóa đơn không hợp lệ");
            alert.setHeaderText("Phạm vi giá trị hóa đơn không hợp lệ do vi phạm những điều sau:");
            alert.setContentText(valueRangeErrorsSb.toString());
            alert.showAndWait();
            return;
        }
        FilteredList<SaleBill> filteredList = new FilteredList<>(saleBillFullList);
        if(fromValue != 0 || toValue != Double.MAX_VALUE){
            final double _fromValue = fromValue;
            final double _toValue = toValue;
            filteredList = filteredList.filtered(saleBill -> {
                if(saleBill.getPrice() < _fromValue) return false;
                if(saleBill.getPrice() > _toValue) return false;
                return true;
            });
        }
        if(!ComboBoxOption.isSelectAllOption(customerCombobox.getValue())){
            Customer customer = customerCombobox.getValue().getValue();
            filteredList = filteredList.filtered(saleBill -> {
                return saleBill.getCustomer() == customer;
            });
        }
        LocalDate fromDate = filterFromDateBox.getValue();
        LocalDate toDate = filterToDateBox.getValue();
        if(fromDate != null || toDate != null){
            filteredList = filteredList.filtered(saleBill -> {
                if(fromDate != null && saleBill.getTime().compareTo(fromDate) < 0) return false;
                if(toDate != null && saleBill.getTime().compareTo(toDate) > 0) return false;
                return true;
            });
        }

        String subName = filterSubnameTxtBox.getText();
        if(subName != null && (subName = subName.trim()).length() != 0){
            final String _subName = subName.toLowerCase();
            filteredList = filteredList.filtered(saleBill -> {
                return saleBill.getName().toLowerCase().contains(_subName);
            });
        }
        String subLocationString = filterLocationTxt.getText();
        if(subLocationString != null && (subLocationString = subLocationString.trim()).length() != 0){
            final String _subLocationString = subLocationString.toLowerCase();
            filteredList = filteredList.filtered(saleBill -> {
                return saleBill.getLocation().toLowerCase().contains(_subLocationString);
            });
        }
        saleBillList = new SaleBillObservableList(filteredList);
        saleBillList.setOrder(orderCombobox.getValue().getValue());
        saleBillListView.setItems(saleBillList);
    }
    @FXML
    private void closeFilterPanel(){
        filterPanel.setVisible(false);
        saleBillList = saleBillFullList;
        saleBillList.setOrder(orderCombobox.getValue().getValue());
        saleBillListView.setItems(saleBillList);
    }
    @FXML
    private void openFilterPanel(){
        filterPanel.setVisible(true);
    }

    private void initializeSaleBillFilterPanel(){
        var adapter = DatabaseAdapter.getDbAdapter();
        filterPanel.managedProperty().bind(filterPanel.visibleProperty());
        filterPanel.setVisible(false);
        customerCombobox.setItems(new CustomerComboBoxOptions(adapter.getAllCustomers()));
        customerCombobox.setButtonCell(new DefaultListCell<>());
        customerCombobox.setCellFactory(new Callback<ListView<ComboBoxOption<Customer>>, ListCell<ComboBoxOption<Customer>>>() {
            @Override
            public ListCell<ComboBoxOption<Customer>> call(ListView<ComboBoxOption<Customer>> param) {
                return new DefaultListCell<>();
            }
        });
        customerCombobox.getSelectionModel().selectFirst();
    }
    //endregion
    //region SaleBill Viewing Panel
    @FXML
    private Text saleBillNoteTxt;
    @FXML
    private Text saleBillCustomerNameTxt;
    @FXML
    private Text saleBillCustomerPhoneNumberTxt;
    @FXML
    private ToggleButton saleBillNoteBtn;
    @FXML
    private AnchorPane saleBillViewingPane;
    @FXML
    private ListView<SaleBillItem> saleBillItemListView;
    @FXML
    private Text totalPriceTxt;
    private  SalebillEditingPanelController editingPanelController;
    private void initializeSaleBillDisplayer(){
        saleBillViewingPane.setVisible(false);
        saleBillNoteBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                saleBillNoteTxt.setText(currentNote);
                saleBillNoteBtn.setText("Thu gọn");
            }
            else{
                saleBillNoteTxt.setText(currentNote.substring(0, 70) + "...");
                saleBillNoteBtn.setText("Xem thêm");
            }
        });
        saleBillItemListView.setCellFactory(new Callback<ListView<SaleBillItem>, ListCell<SaleBillItem>>() {
            @Override
            public ListCell<SaleBillItem> call(ListView<SaleBillItem> param) {
                return new SaleBillItemListCell();
            }
        });

    }
    private String currentNote;
    private void displaySaleBillNote(String note){
        currentNote = note;
        if(note.length() > 70) {
            saleBillNoteTxt.setText(note.substring(0, 70) + "...");
            saleBillNoteBtn.setVisible(true);
            saleBillNoteBtn.setSelected(false);
            saleBillNoteBtn.setText("Xem thêm");

            return;
        }
        else{
            saleBillNoteTxt.setText(note);
            saleBillNoteBtn.setVisible(false);
        }
    }
    private SaleBill oldSaleBill = null;
    private ChangeListener<String> saleBillNoteChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            displaySaleBillNote(newValue);
        }
    };
    public String priceToString(double price){
        StringBuilder sb = new StringBuilder(String.valueOf((long) price));
        for(int i = sb.length() - 3; i > 0; i-=3){
            sb.insert(i, '.');
        }
        return sb.toString();
    }
    public ObservableList<SaleBillItem> getSaleBillItem(SaleBill saleBill){
        ObservableList<SaleBillItem> output = FXCollections.observableArrayList();
        for (var p: DatabaseAdapter.getDbAdapter().getItems(saleBill)) {
            SaleBillItem saleBillItem = null;
            for (var s: output) {
                if(s.getProductInfo() == p.getProductInfo()){
                    saleBillItem = s;
                    break;
                }
            }
            if(saleBillItem == null){
                saleBillItem = new SaleBillItem(p.getProductInfo());
                saleBillItem.getProducts().add(p);
                output.add(saleBillItem);
            }
            else{
                saleBillItem.getProducts().add(p);
            }
        }
        return output;
    }
    private ObservableList<SaleBillItem> currentSaleBillItems;
    private void displaySaleBill(SaleBill saleBill){
        if(saleBill == oldSaleBill) return;
        if(oldSaleBill != null){
            oldSaleBill.noteProperty().removeListener(saleBillNoteChangeListener);
            saleBillCustomerNameTxt.textProperty().unbind();
            saleBillCustomerPhoneNumberTxt.textProperty().unbind();
        }
        if(saleBill == null) {
            saleBillViewingPane.setVisible(false);
            return;
        }
        displaySaleBillNote(saleBill.noteProperty().get());
        saleBill.noteProperty().addListener(saleBillNoteChangeListener);
        saleBillViewingPane.setVisible(true);
        currentSaleBillItems = getSaleBillItem(saleBill);
        saleBillItemListView.setItems(currentSaleBillItems);
        totalPriceTxt.setText(priceToString(saleBill.getPrice()));
        saleBillCustomerNameTxt.textProperty().bind(saleBill.getCustomer().nameProperty());
        saleBillCustomerPhoneNumberTxt.textProperty().bind(saleBill.getCustomer().phoneNumberProperty());
        oldSaleBill = saleBill;
    }
    //endregion
    //region SaleBill List
    @FXML
    private HBox saleBillToolbar;
    @FXML
    private ComboBox<ComboBoxOption<SaleBillListOrder>> orderCombobox;
    @FXML
    private ListView<SaleBill> saleBillListView;
    private SaleBillObservableList saleBillList;
    private SaleBillObservableList saleBillFullList;
    @FXML
    private Button deleteSaleBillBtn;
    @FXML
    private void deleteSaleBill(){
        var selectedItem = saleBillListView.getSelectionModel().getSelectedItem();
        var adapter = DatabaseAdapter.getDbAdapter();
        if(adapter.getItems(selectedItem).size() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không thể xoá hoá đơn");
            alert.setHeaderText("Không thể xoá hoá đơn do hoá đơn đã được thanh toán.");
            alert.setContentText("Chỉ xoá được hoá đơn rỗng");
            alert.showAndWait();
            return;
        }
        adapter.deleteSaleBill(selectedItem);
        saleBillListView.getSelectionModel().clearSelection();
        deleteSaleBillBtn.setVisible(false);
    }
    private void adjustSaleBills(){
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getDbAdapter();
        for (var x: databaseAdapter.getAllSaleBills()){
            x.setPrice(0);
        }
        for (var x: databaseAdapter.getAllProducts()){
            var salebill = x.getSaleBill();
            if(salebill != null){
                salebill.setPrice(salebill.getPrice() + x.getSaleValue());
            }
        }
    }

    @FXML
    private void createNewSaleBill(){
        editingPanelController.openPanel(null, null);
    }
    @FXML
    private void editSaleBill(){
        editingPanelController.openPanel(saleBillListView.getSelectionModel().getSelectedItem(), currentSaleBillItems);
    }
    private void initializeSaleBillList(){
        deleteSaleBillBtn.setVisible(false);
        //Initialize saleBill list order options
        orderCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(SaleBillListOrder.TimeAscending, "Cũ nhất"),
                new ComboBoxOption<>(SaleBillListOrder.TimeDescending, "Mới nhất"),
                new ComboBoxOption<>(SaleBillListOrder.TitleAscending, "A->z"),
                new ComboBoxOption<>(SaleBillListOrder.TitleDescending, "z->A"),
                new ComboBoxOption<>(SaleBillListOrder.PriceAscending, "Giá trị tăng dần"),
                new ComboBoxOption<>(SaleBillListOrder.PriceDescending, "Giá trị giảm dần"),
                new ComboBoxOption<>(SaleBillListOrder.CustomerNameAscending, "Tên khách hàng A->z"),
                new ComboBoxOption<>(SaleBillListOrder.CustomerNameDescending, "Tên khách hàng z->A"),
                new ComboBoxOption<>(SaleBillListOrder.LocationAscending, "Địa chỉ A->z"),
                new ComboBoxOption<>(SaleBillListOrder.LocationDescending, "Địa chỉ z->A")
        ));
        orderCombobox.getSelectionModel().selectFirst();
        saleBillListView.setCellFactory(new Callback<ListView<SaleBill>, ListCell<SaleBill>>()
        {
            @Override
            public ListCell<SaleBill> call(ListView<SaleBill> listView)
            {
                return new SaleBillListCell();
            }
        });
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getDbAdapter();
        var saleBills = databaseAdapter.getAllSaleBills();
        //adjustSaleBills();
        saleBillFullList = new SaleBillObservableList(saleBills);
        saleBillList = saleBillFullList;
        saleBillListView.setItems(saleBillList);
        saleBillListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteSaleBillBtn.setVisible(newValue != null);
            displaySaleBill(newValue);
            saleBillToolbar.setVisible(newValue != null);
        });
        orderCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            saleBillList.setOrder(newValue.getValue());
        });
        saleBillToolbar.setVisible(false);

        editingPanelController = SalebillEditingPanelController.getController();
        var editingPanel = editingPanelController.getContainer();
        AnchorPane.setBottomAnchor(editingPanel, 0.0);
        AnchorPane.setLeftAnchor(editingPanel, 0.0);
        AnchorPane.setRightAnchor(editingPanel, 0.0);
        AnchorPane.setTopAnchor(editingPanel, 0.0);
        mainContainer.getChildren().add(editingPanel);
        editingPanel.setVisible(false);
        //
    }

    //endregion
    public PaymentWindowController() {

    }


    public void initialize()
    {
        initializeSaleBillFilterPanel();
        initializeSaleBillList();
        initializeSaleBillDisplayer();
    }
}
