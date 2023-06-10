package com.shopkeeper.linh.windowfactories;

import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.FeedbackAbout;
import com.shopkeeper.linh.models.FeedbackType;
import com.shopkeeper.linh.windowfactories.customer.CustomerEditingPanelController;
import com.shopkeeper.linh.windowfactories.customer.CustomerListCell;
import com.shopkeeper.linh.windowfactories.customer.CustomerListOrder;
import com.shopkeeper.linh.windowfactories.customer.CustomerObservableList;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackAboutForFilter;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListCell;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListOrder;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackObservableList;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOption;
import com.shopkeeper.linh.windowfactories.utilities.CustomerToStringCell;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;

public class CustomerManagerWindowController {
    //region Customer Filter Panel
    @FXML
    private TextField filterSubnameTxtBox;
    @FXML
    private TextField filterLocationTxt;
    @FXML
    private TextField filterPhoneNumberTxt;
    @FXML
    private GridPane filterPanel;

    @FXML
    private void resetFilterPanel(){
        filterSubnameTxtBox.setText("");
        filterLocationTxt.setText("");
        filterPhoneNumberTxt.setText("");
    }
    @FXML
    private void filterList(){

        FilteredList<Customer> filteredList = new FilteredList<>(customerFullList);

        String subName = filterSubnameTxtBox.getText();
        if(subName != null && (subName = subName.trim()).length() != 0){
            final String _subName = subName.toLowerCase();
            filteredList = filteredList.filtered(customer -> {
                return customer.getName().toLowerCase().contains(_subName);
            });
        }
        String location = filterLocationTxt.getText();
        if(location != null && (location = location.trim()).length() != 0){
            final String _location = location.toLowerCase();
            filteredList = filteredList.filtered(customer -> {
                return customer.getDefaultLocation().toLowerCase().contains(_location);
            });
        }
        String phoneNumber = filterPhoneNumberTxt.getText();
        if(phoneNumber != null && (phoneNumber = phoneNumber.trim()).length() != 0){
            final String _phoneNumber = phoneNumber;
            filteredList = filteredList.filtered(customer -> {
                return customer.getPhoneNumber().contains(_phoneNumber);
            });
        }
        customerList = new CustomerObservableList(filteredList);
        customerList.setOrder(orderCombobox.getValue().getValue());
        customerListView.setItems(customerList);
    }
    @FXML
    private void closeFilterPanel(){
        filterPanel.setVisible(false);
        customerList = customerFullList;
        customerList.setOrder(orderCombobox.getValue().getValue());
        customerListView.setItems(customerList);
    }
    @FXML
    private void openFilterPanel(){
        filterPanel.setVisible(true);
    }

    private void initializeCustomerFilterPanel(){
        filterPanel.managedProperty().bind(filterPanel.visibleProperty());
        filterPanel.setVisible(false);

    }
    //endregion
    //region Customer Display
    @FXML
    private VBox customerDisplayer;
    @FXML
    private Text customerHeaderDisplayer;
    @FXML
    private Text customerPhoneNumberDisplayer;
    @FXML
    private Text customerLocationDisplayer;
    private void initializeCustomerDisplayer(){
        customerDisplayer.setVisible(false);
    }
    private void displayCustomer(Customer customer){
        if(customer == null) {
            customerDisplayer.setVisible(false);
            customerHeaderDisplayer.textProperty().unbind();
            customerPhoneNumberDisplayer.textProperty().unbind();
            customerLocationDisplayer.textProperty().unbind();
            return;
        }
        customerHeaderDisplayer.textProperty().bind(customer.nameProperty());
        customerPhoneNumberDisplayer.textProperty().bind(customer.phoneNumberProperty());
        customerLocationDisplayer.textProperty().bind(customer.defaultLocationProperty());
        customerDisplayer.setVisible(true);
    }
    //endregion
    //region Customer List
    @FXML
    private HBox customerToolbar;
    @FXML
    private ComboBox<ComboBoxOption<CustomerListOrder>> orderCombobox;
    @FXML
    private ListView<Customer> customerListView;
    private CustomerObservableList customerList;
    private CustomerObservableList customerFullList;
    @FXML
    private void createNewCustomer(){
        editingPanelController.openToEdit(null);
    }
    @FXML
    private void deleteCustomer(){
        var selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if(selectedCustomer.countTimesToBeReferenced() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Không thể xoá phần tử này");
            alert.setHeaderText("Không thể xoá phần tử này");
            alert.setContentText("Phần tử này đã được sử dụng trong một hoá đơn nào đó.");
            alert.showAndWait();
        }
        else {
            DatabaseAdapter.getDbAdapter().deleteCustomer(selectedCustomer);
        }
    }
    @FXML
    private void editCustomer(){
        editingPanelController.openToEdit(customerListView.getSelectionModel().getSelectedItem());
    }
    private void initializeCustomerList(){
        //Initialize feedback list order options
        orderCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(CustomerListOrder.NameAscending, "Tên A->z"),
                new ComboBoxOption<>(CustomerListOrder.NameDescending, "Tên z->A"),
                new ComboBoxOption<>(CustomerListOrder.LocationAscending, "Địa chỉ A->z"),
                new ComboBoxOption<>(CustomerListOrder.LocationDescending, "Địa chỉ z->A")
        ));
        orderCombobox.getSelectionModel().selectFirst();
        customerListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new CustomerListCell();
            }
        });
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getDbAdapter();
        customerFullList = new CustomerObservableList(databaseAdapter.getAllCustomers());
        customerList = customerFullList;
        customerListView.setItems(customerList);
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayCustomer(newValue);
            if(newValue == null){
                customerToolbar.setVisible(false);
            }else{
                customerToolbar.setVisible(true);
            }
        });
        orderCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            customerList.setOrder(newValue.getValue());
        });
        customerToolbar.setVisible(false);
    }
    //endregion
    @FXML
    private AnchorPane mainContainer;
    private CustomerEditingPanelController editingPanelController;
    public CustomerManagerWindowController() {

    }


    public void initialize()
    {
        initializeCustomerDisplayer();
        initializeCustomerList();
        initializeCustomerFilterPanel();
        editingPanelController = CustomerEditingPanelController.getController();
        var editingPanel = editingPanelController.getContainer();
        AnchorPane.setBottomAnchor(editingPanel, 0.0);
        AnchorPane.setLeftAnchor(editingPanel, 0.0);
        AnchorPane.setRightAnchor(editingPanel, 0.0);
        AnchorPane.setTopAnchor(editingPanel, 0.0);
        mainContainer.getChildren().add(editingPanel);
    }
}
