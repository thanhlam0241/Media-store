package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.lam.funtions.CategoryManager;
import com.shopkeeper.lam.funtions.PersonManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.funtions.PublisherManager;
import com.shopkeeper.lam.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ProductPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor;
    @FXML
    AnchorPane showProductPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField searchName;
    @FXML
    ComboBox<String> categoryComboBox, publisherComboBox, personComboBox;
    @FXML
    DatePicker toDate, fromDate;

    private final ObservableList<ProductInfo> showedProducts =FXCollections.observableArrayList();
    private final ObservableList<ProductInfo> tempProduct =FXCollections.observableArrayList();

    public ObservableList<ProductInfo> getShowedProducts(){
        return this.showedProducts;
    }
    public ObservableList<ProductInfo> getTempProduct(){
        return this.tempProduct;
    }

    public void addProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-product-page.fxml"));
        loader.load();
        AddProductPage controller = loader.getController();
        this.add(controller);
    }

    private void initInfo(){
        try{
            showedProducts.clear();
            tempProduct.clear();
            tempProduct.addAll(ProductInfoManager.getManager().getAllMusicInfo());
            tempProduct.addAll(ProductInfoManager.getManager().getAllBookInfo());
            tempProduct.addAll(ProductInfoManager.getManager().getAllFilmInfo());
//            showedProducts.addAll(tempProduct); // add theo mode
            setShowedProducts();
            System.out.println(showedProducts.size());

        }catch(Exception e){
            System.out.println("bug initInfo: " +e.getMessage());
        }
    }
//    public ObservableList<ProductInfo> getSearchProduct(){
//        ObservableList<ProductInfo> res = FXCollections.observableArrayList();
//        if(!searchName.equals("")){
//
//        }
//        return res;
//    }

    private void initItem(ProductInfo productInfo, int index){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-brief-info-page.fxml"));
            loader.load();
            ProductBriefInfoPage controller = loader.getController();
            controller.setProductInfo(productInfo);
            showProductPane.getChildren().add(controller.getRoot());
            controller.setLocation(index);
            controller.setParent(this);
        }catch(Exception e){
            System.out.println("bug initItem: "+e);
        }
    }
    public void searchByTitle(){
        setShowedProducts();
        initShow(showedProducts);
    }

    public void initShow(ObservableList<ProductInfo> productInfos){
//        showProductPane.getChildren().clear();
//        for(int i=0; i<productInfos.size();i++){
////            initItem(productInfos.get(i), i);
//        }
        pagination.setPageFactory(this::setPage);
    }

    public void reload(){
        publisherComboBox.setValue(null);
        categoryComboBox.setValue(null);
        searchName.setText("");
        personComboBox.setValue(null);
        categoryComboBox.setValue(null);
        initInfo();
        initShow(showedProducts);
    }

    public void sortByDate(){
        showedProducts.sort((o1, o2) -> {
            if (o1.getReleaseDate().isBefore(o2.getReleaseDate())) {
                return -1;
            }
            if (o1.getReleaseDate().isAfter(o2.getReleaseDate())) {
                return 1;
            }
            return 0;
        });
        initShow(showedProducts);
    }

    public void sortByRating(){
        showedProducts.sort(Comparator.comparingDouble(ProductInfo::getRating));
        initShow(showedProducts);
    }

    public void sortByName(){
        showedProducts.sort((o1, o2) -> Integer.compare(o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()), 0));
        initShow(showedProducts);
    }

    public void sortByPrice(){
        showedProducts.sort(Comparator.comparingDouble(ProductInfo::getCurrentSalePrice));
        initShow(showedProducts);
    }

    private boolean checkDate(ProductInfo productInfo){
        if(toDate.getValue()==null){
            if(fromDate.getValue()==null)
                return true;
            else
                return productInfo.getReleaseDate().isAfter(fromDate.getValue());
        }
        if(fromDate.getValue()==null)
            return productInfo.getReleaseDate().isBefore(toDate.getValue());
        return productInfo.getReleaseDate().isAfter(fromDate.getValue()) && productInfo.getReleaseDate().isBefore(toDate.getValue());
    }

    public void setShowedProducts(){
        showedProducts.clear();
        for(var productInfo: tempProduct){
            if(!searchName.getText().equals(""))
                if(!productInfo.getTitle().toLowerCase().contains(searchName.getText().toLowerCase()))
                    continue;
            if(!checkDate(productInfo))
                continue;
            String category = categoryComboBox.getValue();
            if(category!=null) if(!category.equals("All Categories") && !category.equals("" ) && !category.equals(productInfo.getCategory().getName()))
                continue;
            String publisher = publisherComboBox.getValue();
            if(publisher!=null)if(!publisher.equals("All Publishers") && !publisher.equals("" ) && !publisher.equals(productInfo.getPublisher().getName()))
                continue;
            String person = personComboBox.getValue();
            if(person!=null && !person.equals("")){
                if (person.equals("All People")) {
                        showedProducts.add(productInfo);
                        continue;
                    }
                boolean stop = false;

                if (productInfo instanceof BookInfo) {
                    ArrayList<Person> temp = ((BookInfo) productInfo).getAuthors();
                    for(Person x: temp){
                        if(x.getName().equals(person))
                        {
                            showedProducts.add(productInfo);
                            stop = true;
                            break;
                        }
                    }
                }
                if(stop )
                    continue;
                if(productInfo instanceof MusicInfo){
                    ArrayList<Person> temp = ((MusicInfo) productInfo).getMusicians();
                    for(Person x: temp){
                        if(x.getName().equals(person))
                        {
                            showedProducts.add(productInfo);
                            stop = true;
                            break;
                        }
                    }
                }
                if(stop ) continue;
                if(productInfo instanceof FilmInfo){
                    ArrayList<Person> temp = ((FilmInfo) productInfo).getActors();
                    for(Person x: temp){
                        if(x.getName().equals(person))
                        {
                            showedProducts.add(productInfo);
                            stop = true;
                            break;
                        }
                    }
                    if(stop)
                        continue;
                    if(((FilmInfo) productInfo).getDirector().getName().equals(person)) {
                        {
                            showedProducts.add(productInfo);
                        }
                    }
                }
                continue;
            }
            showedProducts.add(productInfo);
            initShow(showedProducts);
        }
    }

    private void setComboBox() throws Exception {
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("All Categories");
        for(var x: CategoryManager.getManager().getAll())
            categories.add(x.getName());
        categoryComboBox.setItems(categories);
        ObservableList<String> publishers = FXCollections.observableArrayList();
        publishers.add("All Publishers");
        for(var x: PublisherManager.getManager().getAll())
            publishers.add(x.getName());
        publisherComboBox.setItems(publishers);
        ObservableList<String> persons = FXCollections.observableArrayList();
        persons.add("All People");
        for(var x: PersonManager.getManager().getAll())
            persons.add(x.getName());
        personComboBox.setItems(persons);
    }

    @FXML
    Pagination pagination;
    public AnchorPane setPage(Integer page){
        pagination.setPageCount((int)Math.ceil(showedProducts.size()/3.0));
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(720,360);
        for(int i=0;i<3;i++){
            if(page*3+i>=showedProducts.size())
                break;
            ProductInfo productInfo = showedProducts.get(page*3+i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-brief-info-page.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductBriefInfoPage controller = loader.getController();
            controller.setProductInfo(productInfo);
            anchorPane.getChildren().add(controller.getRoot());
            controller.setLocation(0, i);
            controller.setParent(this);
        }
        return anchorPane;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.setRoot(ancestor);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setContent(showProductPane);

        pagination.setPageFactory(this::setPage);
        try {
            setComboBox();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        creatTest();
        reload();
    }
}
