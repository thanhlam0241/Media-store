package com.shopkeeper.vu.windowfactories;
import com.shopkeeper.lam.models.*;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.models.ImportBill;
import com.shopkeeper.minh.models.OtherBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerWarehouseManagerWindowFactory implements Initializable {
    @FXML
    private DatePicker date_other;
    @FXML
    private Button bt_timkiemother;
    @FXML
    private TableColumn<OtherBill, String> cl_nameother;
    @FXML
    private  TableColumn<OtherBill, Double> cl_priceother;
    @FXML
    private  TableColumn<OtherBill, String> cl_noteother;
    @FXML
    private  TableColumn<OtherBill, Boolean> cl_tfother;
    @FXML
    private  TableColumn<OtherBill, LocalDate> cl_timeother;
    @FXML
    private TableView tableviewother;
    @FXML
    private TextField textField_nameother;
    @FXML
    private TextField textField_priceother;
    @FXML
    private TextField textField_noteother;
    @FXML
    private ComboBox comboBox_tfother;
    @FXML
    private Button bt_resetother;
    @FXML
    private Button bt_addother;
    @FXML
    private Button bt_removeother;
    @FXML
    private Button bt_fixother;
// ImportBill
    @FXML
    private Button bt_resetimport;
    @FXML
    private Button bt_addimport;
    @FXML
    private Button bt_removeimport;
    @FXML
    private Button bt_fiximport;
    @FXML
    private Button bt_timkiemimport;
    @FXML
    private DatePicker date_import;
    @FXML
    private TableColumn<ImportBill, String> cl_nameimport;
    @FXML
    private  TableColumn<ImportBill, Double> cl_priceimport;
    @FXML
    private  TableColumn<ImportBill, String> cl_noteimport;
    @FXML
    private  TableColumn<ImportBill, Boolean> cl_tfimport;
    @FXML
    private  TableColumn<ImportBill, LocalDate> cl_timeimport;
    @FXML
    private  TableColumn<ImportBill, String> cl_rsimport;

    @FXML
    private TableView tableviewimport;
    @FXML
    private TextField textField_nameimport;
    @FXML
    private TextField textField_priceimport;
    @FXML
    private TextField textField_noteimport;
    @FXML
    private TextField textField_rsimport;
    @FXML
    private ComboBox comboBox_tfimport;
    //Product
    @FXML
    private TextField textField_nameoproduct;
    @FXML
    private TextField textField_idproduct;
    @FXML
    private ComboBox comboBox_kieuproduct;
    @FXML
    private TextField textField_priceproduct;
    @FXML
    private Button bt_timkiemproduct;
    @FXML
    private Button bt_updateproduct;
    @FXML
    private TableView tableView_product;
    @FXML
    private TableColumn<Product, Integer> cl_idproduct;
    @FXML
    private TableColumn<Product, ProductInfo> cl_typeproduct;
    @FXML
    private TableColumn<Product, Double> cl_priceproduct;
    @FXML
    private TableColumn<Product, LocalDate> cl_timeproduct;
    @FXML
    private  TableColumn<Product, ProductState> cl_stateproduct;
    @FXML
    private  TableColumn<Product, Double> cl_importproduct;
    @FXML
    private TableColumn<Product, String> cl_placementproduct;
    @FXML
    private Button bt_delete;



    private ObservableList<OtherBill> listOtherBill ;
    private ObservableList<ImportBill> listImportBill ;
    private ObservableList<Product> listProduct;
    private ObservableList<Product> listProductVip;
    private ObservableList<ImportBill> listimport;
    private ObservableList<OtherBill> list;
    private ObservableList<OtherBill> list1;
    private String[] hieuluc = {"true","flase"};
    private String[] type = {"Music","Book","Film"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        comboBox_kieuproduct.getItems().addAll(type);
        comboBox_tfother.getItems().addAll(hieuluc);
        comboBox_tfimport.getItems().addAll(hieuluc);
        comboBox_kieuproduct.getSelectionModel().selectFirst();
        comboBox_tfimport.getSelectionModel().selectFirst();
        comboBox_tfother.getSelectionModel().selectFirst();

    }
    // Other Bill

    public  void findOtherBill() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        try{
            listOtherBill = FXCollections.observableArrayList();
            list = adapter.getAllOtherBills();
            for (OtherBill c:list) {
                String v= c.getName();
                String s = textField_nameother.getText();
                if(v.indexOf(s)>=0){
                    listOtherBill.add(c);
                }
            }
            LocalDate a = date_other.getValue();
            cl_nameother.setCellValueFactory(new PropertyValueFactory<OtherBill, String>("name"));
            cl_priceother.setCellValueFactory(new PropertyValueFactory<OtherBill, Double>("price"));
            cl_timeother.setCellValueFactory(new PropertyValueFactory<OtherBill, LocalDate>("time"));
            cl_tfother.setCellValueFactory(new PropertyValueFactory<OtherBill, Boolean>("isEffected"));
            cl_noteother.setCellValueFactory(new PropertyValueFactory<OtherBill, String>("note"));
            tableviewother.setItems(listOtherBill);
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }

    }
    public void resetOtherBill(){
        textField_nameother.setText(null);
        textField_noteother.setText(null);
        textField_priceother.setText(null);
        listOtherBill.clear();
        tableviewother.setItems(listOtherBill);
        System.out.println(listOtherBill);
    }
    public void addOtherBill() throws Exception {
        try{
            var adapter = DatabaseAdapter.getDbAdapter();
            OtherBill a = new OtherBill();
            a.setName(textField_nameother.getText());
            a.setPrice(Double.parseDouble(textField_priceother.getText()));
            a.setIsEffected(Boolean.valueOf(String.valueOf(comboBox_tfother.getValue())));
            a.setNote(textField_noteother.getText());
            a.setTime(date_other.getValue());
            listOtherBill.add(a);
            adapter.insertOtherBill(a);
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }
    }
    public void removeOtherBill() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        OtherBill a =(OtherBill) tableviewother.getSelectionModel().getSelectedItem();
        listOtherBill.removeAll(a);
        adapter.deleteOtherBill(a);
    }
    public void fixOtherBill() throws Exception {
        try{
            var adapter = DatabaseAdapter.getDbAdapter();
            OtherBill a = (OtherBill) tableviewother.getSelectionModel().getSelectedItem();
            a.setName(textField_nameother.getText());
            a.setPrice(Double.parseDouble(textField_priceother.getText()));
            a.setIsEffected(Boolean.valueOf(String.valueOf(comboBox_tfother.getValue())));
            a.setNote(textField_noteother.getText());
            a.setTime(date_other.getValue());
            adapter.updateOtherBill(a);
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }
    }
    // Import Bill
    public  void findImportBill() throws Exception {
        try{
            listImportBill = FXCollections.observableArrayList();
            var adapter = DatabaseAdapter.getDbAdapter();
            listimport = adapter.getAllImportBills();
            for (ImportBill c:listimport) {
                String d = c.getName();
                String e = textField_nameimport.getText();
                if(d.indexOf(e)>=0){
                    listImportBill.add(c);
                }
            }
            LocalDate a = date_import.getValue();
            cl_nameimport.setCellValueFactory(new PropertyValueFactory<ImportBill, String>("name"));
            cl_priceimport.setCellValueFactory(new PropertyValueFactory<ImportBill, Double>("price"));
            cl_timeimport.setCellValueFactory(new PropertyValueFactory<ImportBill, LocalDate>("time"));
            cl_tfimport.setCellValueFactory(new PropertyValueFactory<ImportBill, Boolean>("isEffected"));
            cl_rsimport.setCellValueFactory(new PropertyValueFactory<ImportBill, String>("distributor"));
            cl_noteimport.setCellValueFactory(new PropertyValueFactory<ImportBill, String>("note"));
            tableviewimport.setItems(listImportBill);
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu", ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();

        }

    }
    public void resetImportBill(){
        textField_nameimport.setText(null);
        textField_noteimport.setText(null);
        textField_priceimport.setText(null);
        textField_rsimport.setText(null);
        listImportBill.clear();
        tableviewimport.setItems(listImportBill);
        try {
            this.findImportBill();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }
    public void addImportBill() throws Exception {
        try{
            var adapter = DatabaseAdapter.getDbAdapter();
            ImportBill a = new ImportBill();
            a.setName(textField_nameimport.getText());
            a.setPrice(Double.parseDouble(textField_priceimport.getText()));
            a.setIsEffected(Boolean.valueOf(String.valueOf(comboBox_tfimport.getValue())));
            a.setDistributor(textField_rsimport.getText());
            a.setNote(textField_noteimport.getText());
            a.setTime(date_import.getValue());
            listImportBill.add(a);
            adapter.insertImportBill(a);
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }
    }
    public void removeImportBill() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ImportBill a =(ImportBill) tableviewimport.getSelectionModel().getSelectedItem();
        listImportBill.removeAll(a);
        adapter.deleteImportBill(a);
    }
    public void fixImportBill() throws Exception {
        try{
            var adapter = DatabaseAdapter.getDbAdapter();
            ImportBill a = (ImportBill) tableviewimport.getSelectionModel().getSelectedItem();
            a.setName(textField_nameimport.getText());
            a.setPrice(Double.parseDouble(textField_priceimport.getText()));
            a.setIsEffected(Boolean.valueOf(String.valueOf(comboBox_tfimport.getValue())));
            a.setDistributor(textField_rsimport.getText());
            a.setNote(textField_noteimport.getText());
            a.setTime(date_import.getValue());
            adapter.updateImportBill(a);
            try {
                this.resetImportBill();
            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }
    }
    // QUan ly Product
    public void findProduct() throws Exception {
        String b = String.valueOf(comboBox_kieuproduct.getValue());
        if(b.equals("Book")){
            try{
                var adapter = DatabaseAdapter.getDbAdapter();
                listProductVip = FXCollections.observableArrayList();
                listProduct = adapter.getAllProducts();
                for (Product a: listProduct) {
                    if(a.getProductInfo() instanceof  BookInfo){
                        listProductVip.add(a);
                    }
                }
                cl_idproduct.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
                cl_priceproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("saleValue"));
                cl_stateproduct.setCellValueFactory(new PropertyValueFactory<Product, ProductState>("state"));
                cl_importproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("importCost"));
                cl_placementproduct.setCellValueFactory(new PropertyValueFactory<Product, String>("placement"));
                tableView_product.setItems(listProductVip);
            }
            catch (Exception e){
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
                a.setTitle("Thông báo khẩn");
                a.setHeaderText(null);
                a.show();
            }
        }
        else if(b.equals("Music")){
            var adapter =DatabaseAdapter.getDbAdapter();
            listProductVip = FXCollections.observableArrayList();
            listProduct = adapter.getAllProducts();
            ObservableList<MusicInfo> listy = adapter.getAllMusicInfos();
            System.out.println(listProduct);
            System.out.println(listy);
            try{
                for (Product a: listProduct) {
                    if(a.getProductInfo() instanceof MusicInfo){
                        listProductVip.add(a);
                        a.getProductInfo().getTitle();
                    }
                }
                cl_idproduct.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
                cl_priceproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("saleValue"));
                cl_stateproduct.setCellValueFactory(new PropertyValueFactory<Product, ProductState>("state"));
                cl_importproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("importCost"));
                cl_placementproduct.setCellValueFactory(new PropertyValueFactory<Product, String>("placement"));
                tableView_product.setItems(listProductVip);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
                a.setTitle("Thông báo khẩn");
                a.setHeaderText(null);
                a.show();
            }
        }
        else if(b.equals("Film")){
            try{
                var adapter =DatabaseAdapter.getDbAdapter();
                listProductVip = FXCollections.observableArrayList();
                listProduct = adapter.getAllProducts();
                for (Product a: listProduct) {
                    if(a.getProductInfo() instanceof FilmInfo){
                        listProductVip.add(a);
                    }
                }
                cl_idproduct.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
                cl_priceproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("saleValue"));
                cl_stateproduct.setCellValueFactory(new PropertyValueFactory<Product, ProductState>("state"));
                cl_importproduct.setCellValueFactory(new PropertyValueFactory<Product, Double>("importCost"));
                cl_placementproduct.setCellValueFactory(new PropertyValueFactory<Product, String>("placement"));
                tableView_product.setItems(listProductVip);
            }
            catch (Exception e){
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
                a.setTitle("Thông báo khẩn");
                a.setHeaderText(null);
                a.show();
            }
        }
    }
    public void updateProduct() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();

        try{
            Product a = (Product) tableView_product.getSelectionModel().getSelectedItem();
            a.setSaleValue(Double.parseDouble(textField_priceproduct.getText()));
            adapter.updateProduct(a);
            this.findProduct();
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng nhập lại dữ liệu",ButtonType.APPLY);
            a.setTitle("Thông báo khẩn");
            a.setHeaderText(null);
            a.show();
        }
    }

    public void removeProduct() throws Exception {
        try{
            var adapter  =DatabaseAdapter.getDbAdapter();
            Product a = (Product) tableView_product.getSelectionModel().getSelectedItem();
            adapter.deleteProduct(a);
            this.findProduct();
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Vui lòng chọn để xóa",ButtonType.APPLY);
            a.setTitle("Thông báo");
            a.setHeaderText(null);
            a.show();
        }
        comboBox_kieuproduct.setValue(null);
    }
}
