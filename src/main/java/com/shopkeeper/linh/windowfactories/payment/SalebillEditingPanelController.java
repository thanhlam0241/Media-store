package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.lam.models.ProductState;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackAboutForFilter;
import com.shopkeeper.linh.windowfactories.utilities.*;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;

public class SalebillEditingPanelController {
    public static SalebillEditingPanelController getController(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalebillEditingPanelController.class.getResource("salebill-editing-panel.fxml"));

        Parent template;
        try
        {
            template = fxmlLoader.load();
            return fxmlLoader.getController();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public SalebillEditingPanelController(){
        this.saleBillItems = FXCollections.observableArrayList();
    }
    public AnchorPane getContainer(){
        return container;
    }
    @FXML
    private AnchorPane container;
    @FXML
    private RadioButton chooseOldCustomerBtn;
    @FXML
    private RadioButton chooseNewCustomerBtn;
    @FXML
    private TextField titleTxt;
    @FXML
    private ComboBox<Customer> customerCombobox;
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private TextArea noteTxt;
    @FXML
    private Text totalPriceTxt;
    @FXML
    private Button saveBtn;
    @FXML
    private Text saveBtnIconTxt;
    @FXML
    private ComboBox<ComboBoxOption<FeedbackAboutForFilter>> feedbackAboutCombobox;
    private SaleBill saleBill = null;
    private ObservableList<SaleBillItem> saleBillItems;
    private ObservableList<SaleBillItem> saleBillItemsDataContext;

    private double totalPrice;
    public void openPanel(SaleBill saleBill, ObservableList<SaleBillItem> saleBillItems){
        this.saleBillItemsDataContext = saleBillItems;
        this.saleBill = saleBill;
        ableToChangeImportantInfos.set(saleBill == null);
        resetPanel();
        container.setVisible(true);
    }

    @FXML
    private void closePanel(){
        container.setVisible(false);
    }
    public String priceToString(double price){
        StringBuilder sb = new StringBuilder(String.valueOf((long) price));
        for(int i = sb.length() - 3; i > 0; i-=3){
            sb.insert(i, '.');
        }
        return sb.toString();
    }
    @FXML
    private void resetPanel(){
        if(saleBill == null){
            titleTxt.setText("");
            customerNameTxt.setText("");
            locationTxt.setText("");
            noteTxt.setText("");
            phoneNumberTxt.setText("");
            customerCombobox.getSelectionModel().clearSelection();
            chooseOldCustomerBtn.setSelected(false);
            chooseNewCustomerBtn.setSelected(false);
            saveBtn.setText("Xuất hoá đơn");
            saveBtnIconTxt.setText("\uef63");
            saveBtnIconTxt.setFill(Paint.valueOf("#076db0"));
            totalPrice = 0;
            totalPriceTxt.setText("0");

        }
        else{
            titleTxt.setText(saleBill.getName());
            customerNameTxt.setText("");
            locationTxt.setText(saleBill.getLocation());
            noteTxt.setText(saleBill.getNote());
            customerCombobox.setValue(saleBill.getCustomer());
            chooseOldCustomerBtn.setSelected(true);
            chooseNewCustomerBtn.setSelected(false);
            phoneNumberTxt.setText(saleBill.getCustomer().getPhoneNumber());
            totalPriceTxt.setText(priceToString(saleBill.getPrice()));

            saveBtn.setText("Lưu");
            saveBtnIconTxt.setText("\uE161");
            saveBtnIconTxt.setFill(Paint.valueOf("#1db107"));
        }
        saleBillItems.clear();
        if(saleBillItemsDataContext != null){
            for(var x : saleBillItemsDataContext){
                saleBillItems.add(x.clone());
            }
        }
    }
    @FXML
    private void saveChange(){
        StringBuilder errorsStringBuilder = new StringBuilder();

        Customer customer = null;
        if(chooseOldCustomerBtn.isSelected()){
            customer = customerCombobox.getValue();
            if(customer == null){
                errorsStringBuilder.append("- Chưa chọn khách hàng\n");
            }
        }
        else {
            String customerName = customerNameTxt.getText() == null? "" : customerNameTxt.getText().trim();
            if(customerName.length() == 0){
                errorsStringBuilder.append("- Không được bỏ trống tên khách hàng\n");
            }
        }
        try{
            String s = phoneNumberTxt.getText().trim();
            if(s.length() == 0) throw new NullPointerException();
            if(s.length() < 10 || s.length() > 12) throw new NumberFormatException();
            if(s.charAt(0) != '0' && s.charAt(0) != '8' && s.charAt(1) != '4'
                    && s.charAt(0) != '+' && s.charAt(1) != '8' && s.charAt(2) != '4') throw new NumberFormatException();
            long l = Long.parseLong(s);
        }
        catch (NumberFormatException e){
            errorsStringBuilder.append("- Số điện thoại không hợp lệ.\n");
        }
        catch (NullPointerException e){
            errorsStringBuilder.append("- Không được bỏ trống số điện thoại.\n");
        }
        String location = locationTxt.getText() == null ? "" : locationTxt.getText().trim();
        if(location.length() == 0){
            errorsStringBuilder.append("- Không được bỏ trống địa chỉ.\n");
        }
        if(this.saleBill == null){
            
            if(errorsStringBuilder.length() == 0){
                var adapter = DatabaseAdapter.getDbAdapter();
                SaleBill newSaleBill = new SaleBill();
                newSaleBill.setName(titleTxt.getText().trim());
                newSaleBill.setLocation(location);
                newSaleBill.setNote(noteTxt.getText().trim());
                newSaleBill.setTime(LocalDate.now());
                Customer newCustomer = null;
                if(chooseOldCustomerBtn.isSelected()){
                    newCustomer = new Customer(customer.getName(), location, phoneNumberTxt.getText().trim());
                }
                else {
                    newCustomer = new Customer(customerNameTxt.getText().trim(), location, phoneNumberTxt.getText().trim());
                }
                newSaleBill.setCustomer(newCustomer);
                newSaleBill.setPrice(totalPrice);
                if(SaleBillDialogFactory.openWindow(newSaleBill, saleBillItems)){
                    if(chooseOldCustomerBtn.isSelected()){
                        if(!customer.getPhoneNumber().equals(newCustomer.getPhoneNumber())
                                || !location.equals(customer.getDefaultLocation())){
                            customer.setDefaultLocation(location);
                            customer.setPhoneNumber(newCustomer.getPhoneNumber());

                        }
                        adapter.updateCustomer(customer);
                        newSaleBill.setCustomer(customer);
                    }
                    else {
                        adapter.insertCustomer(newCustomer);
                    }
                    adapter.insertSaleBill(newSaleBill);
                    for(var item : saleBillItems){
                        for(var product : item.getProducts()){
                            product.setSaleValue(item.getProductInfo().getCurrentSalePrice());
                            product.setSaleBill(newSaleBill);
                            product.setState(ProductState.SOLD);
                            adapter.updateProduct(product);
                        }
                        var pi = item.getProductInfo();
                        pi.setNumberOfProduct(pi.getNumberOfProduct() - item.getAmount());
                    }
                    closePanel();
                }
                return;
            }
        }
        else if(errorsStringBuilder.length() == 0){
            var adapter = DatabaseAdapter.getDbAdapter();
            SaleBill newSaleBill = saleBill;
//            newSaleBill.setName(titleTxt.getText().trim());
//            newSaleBill.setLocation(location);
            newSaleBill.setNote(noteTxt.getText().trim());
//            newSaleBill.setTime(LocalDate.now());
//            if(chooseOldCustomerBtn.isSelected()){
//                String pn = phoneNumberTxt.getText().trim();
//                newSaleBill.setCustomer(customer);
//                if(!customer.getPhoneNumber().equals(pn) || !location.equals(customer.getDefaultLocation())){
//                    customer.setDefaultLocation(location);
//                    customer.setPhoneNumber(pn);
//                    adapter.updateCustomer(customer);
//                }
//            }
//            else {
//                customer = new Customer(customerNameTxt.getText().trim(), location, phoneNumberTxt.getText().trim());
//                adapter.insertCustomer(customer);
//            }
//            newSaleBill.setCustomer(customer);
            //salebill.price??????

            adapter.updateSaleBill(newSaleBill);
            closePanel();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hoá đơn không hợp lệ");
        alert.setHeaderText("Hoá đơn không hợp lệ do vi phạm những điều sau:");
        alert.setContentText(errorsStringBuilder.toString());
        alert.showAndWait();
    }
    @FXML
    private ComboBox<ProductInfo> productInfoComboBox;
    @FXML
    private TextField amountTxtBox;
    @FXML
    private Text maxAmountTxtBox;
    private SaleBillItem saleBillItemOfCurrentProduct = null;
    @FXML
    private void addSaleBillItem(){
        ProductInfo productInfo = productInfoComboBox.getValue();

        if(productInfo == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa chọn loại sản phẩm");
            alert.setContentText("Chọn danh mục > Chọn loại sản phẩm.\nLưu ý: Nếu không chọn loại danh mục thì không chọn được\nloại sản phẩm.");
            alert.showAndWait();
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(amountTxtBox.getText());
        }catch (NumberFormatException e){
            amount = 0;
        }
        if(amount < 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Số lượng không hợp lệ");
            alert.setHeaderText("Số lượng không hợp lệ");
            alert.setContentText("Số lượng phải là một số nguyên dương (>0)");
            alert.showAndWait();
            return;
        }
        if((saleBillItemOfCurrentProduct != null && amount > productInfo.getNumberOfProduct() - saleBillItemOfCurrentProduct.getAmount())
        || (saleBillItemOfCurrentProduct == null && amount > productInfo.getNumberOfProduct())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Số lượng không hợp lệ");
            alert.setHeaderText("Số lượng lớn hơn giới hạn cho phép");
            alert.setContentText("Số lượng lớn hơn số sản phẩm của loại hàng này còn lại trong kho.");
            alert.showAndWait();
            return;
        }
        boolean hasProductInfo = saleBillItemOfCurrentProduct != null;
        SaleBillItem saleBillItem = saleBillItemOfCurrentProduct;

        if(!hasProductInfo){
            saleBillItem = new SaleBillItem(productInfo);
        }
        amount += saleBillItem.getProducts().size();
        for (var product : DatabaseAdapter.getDbAdapter().getAllProducts()){
            if(saleBillItem.getProducts().size() > amount)
                throw new RuntimeException("Algorithm mistake.");
            if(saleBillItem.getProducts().size() == amount) break;
            if(product.getProductInfo() == productInfo && product.getSaleBill() == null){
                saleBillItem.addProduct(product);
            }
        }
        if(saleBillItem.getProducts().size() < amount)
            throw new RuntimeException("Algorithm mistake: productInfo.getNumberOfProduct() is not correct.");
        if(!hasProductInfo){
            saleBillItems.add(saleBillItem);
            saleBillItemOfCurrentProduct = saleBillItem;
        }
        totalPrice += saleBillItem.getPrice();

        totalPriceTxt.setText(priceToString(totalPrice));
        maxAmountTxtBox.setText(String.valueOf(productInfo.getNumberOfProduct() - saleBillItem.getAmount()));
    }
    @FXML
    private ListView<SaleBillItem> saleBillItemListView;
    private final SaleBillItemRemoveListener listener = new SaleBillItemRemoveListener() {
        @Override
        public void remove(SaleBillItem item) {
            saleBillItems.remove(item);
            totalPrice -= item.getPrice();
            totalPriceTxt.setText(priceToString(totalPrice));
        }
    };
    private BooleanProperty ableToChangeImportantInfos;
    @FXML
    private AnchorPane addSaleBillItemPanel;
    public void initialize()
    {
        ableToChangeImportantInfos = new SimpleBooleanProperty(true);

        customerCombobox.setButtonCell(new CustomerToStringCell());
        customerCombobox.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                return new CustomerToStringCell();
            }
        });
        customerCombobox.setItems(DatabaseAdapter.getDbAdapter().getAllCustomers());
        customerCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                locationTxt.setText(newValue.getDefaultLocation());
                phoneNumberTxt.setText(newValue.getPhoneNumber());
            }
        });
        chooseOldCustomerBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                if(customerCombobox.getValue() != null){
                    locationTxt.setText(customerCombobox.getValue().getDefaultLocation());
                    phoneNumberTxt.setText(customerCombobox.getValue().getPhoneNumber());
                }
            }
        });
        chooseNewCustomerBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && customerCombobox.getValue() != null){
                locationTxt.setText("");
                phoneNumberTxt.setText("");
            }
        });
        saleBillItemListView.setCellFactory(new Callback<ListView<SaleBillItem>, ListCell<SaleBillItem>>() {
            @Override
            public ListCell<SaleBillItem> call(ListView<SaleBillItem> param) {
                return new RemovableSaleBillItemListCell(listener, ableToChangeImportantInfos);
            }
        });

        saleBillItemListView.setItems(saleBillItems);

        productInfoComboBox.setCellFactory(new Callback<ListView<ProductInfo>, ListCell<ProductInfo>>() {
            @Override
            public ListCell<ProductInfo> call(ListView<ProductInfo> param) {
                return new ProductInfoToStringCell<>();
            }
        });
        productInfoComboBox.setButtonCell(new ProductInfoToStringCell<>());
        productInfoComboBox.setDisable(true);
        productInfoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) {
                maxAmountTxtBox.setText("0");
            }
            else {
                saleBillItemOfCurrentProduct = null;
                for (var item : saleBillItems){
                    if(item.getProductInfo() == newValue){
                        saleBillItemOfCurrentProduct = item;
                        break;
                    }
                }
                if(saleBillItemOfCurrentProduct == null)
                    maxAmountTxtBox.setText(String.valueOf(newValue.getNumberOfProduct()));
                else
                    maxAmountTxtBox.setText(String.valueOf(
                            newValue.getNumberOfProduct() - saleBillItemOfCurrentProduct.getAmount()));
            }
        });
        feedbackAboutCombobox.setButtonCell(new DefaultListCell<>());
        feedbackAboutCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(FeedbackAboutForFilter.MusicInfo, "Bản nhạc"),
                new ComboBoxOption<>(FeedbackAboutForFilter.FilmInfo, "Bộ phim"),
                new ComboBoxOption<>(FeedbackAboutForFilter.BookInfo, "Bộ sách")
        ));
        var adapter = DatabaseAdapter.getDbAdapter();
        var musicInfos = new ProductInfoObservableListWrapper<ProductInfo>(adapter.getAllMusicInfos());
        var filmInfos = new ProductInfoObservableListWrapper<ProductInfo>(adapter.getAllFilmInfos());
        var bookInfos = new ProductInfoObservableListWrapper<ProductInfo>(adapter.getAllBookInfos());
        feedbackAboutCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                productInfoComboBox.setDisable(true);
                return;
            }
            productInfoComboBox.setDisable(false);
            switch (newValue.getValue()){
                case MusicInfo ->{
                    productInfoComboBox.getSelectionModel().clearSelection();
                    productInfoComboBox.setItems(musicInfos);
                    //productInfoComboBox.getSelectionModel().clearSelection();
                }
                case BookInfo ->{
                    productInfoComboBox.getSelectionModel().clearSelection();
                    productInfoComboBox.setItems(bookInfos);
                    //productInfoComboBox.getSelectionModel().clearSelection();
                }
                case FilmInfo ->{
                    productInfoComboBox.getSelectionModel().clearSelection();
                    productInfoComboBox.setItems(filmInfos);
                    //productInfoComboBox.getSelectionModel().clearSelection();
                }
            }
        });
        //able To Change Important Infos
        addSaleBillItemPanel.visibleProperty().bind(ableToChangeImportantInfos);
        BooleanBinding notAbleToChangeImportantInfos = ableToChangeImportantInfos.not();
        titleTxt.disableProperty().bind(notAbleToChangeImportantInfos);
        chooseOldCustomerBtn.disableProperty().bind(notAbleToChangeImportantInfos);
        chooseNewCustomerBtn.disableProperty().bind(notAbleToChangeImportantInfos);
        customerCombobox.disableProperty().bind(chooseOldCustomerBtn.selectedProperty().not().or(
                notAbleToChangeImportantInfos
        ));
        customerNameTxt.disableProperty().bind(chooseNewCustomerBtn.selectedProperty().not().or(
                notAbleToChangeImportantInfos
        ));
        phoneNumberTxt.disableProperty().bind(notAbleToChangeImportantInfos);
        locationTxt.disableProperty().bind(notAbleToChangeImportantInfos);

    }
}
