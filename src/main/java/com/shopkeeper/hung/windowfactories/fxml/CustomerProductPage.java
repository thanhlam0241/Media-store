package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.hung.windowfactories.CustomerPage;
import com.shopkeeper.hung.windowfactories.ManagerPage;
import com.shopkeeper.lam.funtions.CategoryManager;
import com.shopkeeper.lam.funtions.PersonManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.funtions.PublisherManager;
import com.shopkeeper.lam.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class CustomerProductPage extends Controller implements Initializable {
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
    private ProductInfo productInfo;

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


    private void initItem(ProductInfo productInfo, int index){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-brief-info-page.fxml"));
            loader.load();
            ProductBriefInfoPage controller = loader.getController();
            controller.setProductInfo(productInfo);
            showProductPane.getChildren().add(controller.getRoot());
            controller.setLocation((int)index/2,index%2);
            controller.setType2();
            controller.getButton().setOnMousePressed(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("product-detail-page.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ProductDetailPage detailController = loader.getController();
                        try {
                            detailController.setProductInfo(productInfo);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        detailController.setRoot(loader.getRoot());
                        detailController.setType2();
                        Controller parentController = controller.getParent();
                        parentController.add(detailController);
                    } else {
                        setInfo(productInfo, controller);
                    }
                }
            });
            controller.setParent(this);
        }catch(Exception e){
            System.out.println("bug initItem: "+e);
        }
    }

    @FXML
    Label nameLabel, priceLabel, ratingLabel, dateLabel;
    @FXML
    ImageView imageView;
    @FXML
    TextField numberTextField;
    public void setInfo(ProductInfo productInfo, ProductBriefInfoPage briefInfoPage){
        this.productInfo = productInfo;
        if(productInfo.getNumberOfProduct()!=0)
            ratingLabel.setText("remaining: " + productInfo.getNumberOfProduct() +" left");
        else
            ratingLabel.setText("Out of stock");
        imageView.setImage(briefInfoPage.getImage());
        nameLabel.setText(productInfo.getTitle());
        priceLabel.setText((int)productInfo.getCurrentSalePrice()/1000+",000 VND");
        dateLabel.setText(productInfo.getReleaseDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }
    public void setCartFunction(){
        if(numberTextField.getText().equals(""))
            return;
        int num =Integer.parseInt(numberTextField.getText());
        if(num<=productInfo.getNumberOfProduct())
            CustomerPage.getMain().addProductToCart(productInfo, Integer.parseInt(numberTextField.getText()));
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not enough amount of this product");
            alert.setHeaderText("Results: Cant this product to cart with this amount");
//            alert.setContentText("Connect to the database successfully!");
            alert.showAndWait();
        }
    }
    public void searchByTitle(){
        setShowedProducts();
        initShow(showedProducts);
    }

    public void initShow(ObservableList<ProductInfo> productInfos){
//        showProductPane.getChildren().clear();
//        for(int i=0; i<productInfos.size();i++){
//            initItem(productInfos.get(i), i);
//        }
        pagination.setPageFactory(this::setPage);
    }

    public void reload(){
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
        showedProducts.sort((o1, o2) -> Integer.compare(o1.getTitle().compareTo(o2.getTitle()), 0));
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

        pagination.setPageCount((int)Math.ceil(showedProducts.size()/2.0));
        AnchorPane anchorPane = new AnchorPane();
        if(showedProducts.size()==0){
            pagination.setPageCount(0);
            return anchorPane;
        }
        anchorPane.setPrefSize(720,360);
        for(int i=0;i<2;i++){
            if(page*2+i>=showedProducts.size())
                break;
            ProductInfo productInfo = showedProducts.get(page*2+i);
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
            controller.setType2();
            controller.getButton().setOnMousePressed(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("product-detail-page.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ProductDetailPage detailController = loader.getController();
                        try {
                            detailController.setProductInfo(productInfo);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        detailController.setRoot(loader.getRoot());
                        detailController.setType2();
                        Controller parentController = controller.getParent();
                        parentController.add(detailController);
                    } else {
                        setInfo(productInfo, controller);
                    }
                }
            });
        }
        return anchorPane;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.setRoot(ancestor);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setContent(showProductPane);
        pagination.setPageFactory(this::setPage);

        try {
            setComboBox();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        reload();
    }
}
