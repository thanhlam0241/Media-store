package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.lam.funtions.CategoryManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.models.Category;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor, productAnchorPane, addCategoryAnchorPane;
    @FXML
    Label nameLabel, descriptionLabel;
    @FXML
    ListView<Label> categoryListView;
    @FXML
    TextField nameTextField;
    @FXML
    TextArea descriptionTextArea;

    private Category category;


    EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
        String nameCate= ((Label)mouseEvent.getSource()).getText();
        try {
            for( var cat : CategoryManager.getManager().getAll()){
                if(cat.getName().equals(nameCate)){
                    category = cat;
                    showCategory(cat);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
    public void initItem(ProductInfo productInfo, int index){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-brief-info-page.fxml"));
            loader.load();
            ProductBriefInfoPage controller = loader.getController();
            controller.setProductInfo(productInfo);
            productAnchorPane.getChildren().add(controller.getRoot());
            controller.setLocation((int)index/2, index%2);
            controller.setParent(this);
        }catch(Exception e){
            System.out.println("bug initItem: "+e);
        }
    }
    public void initShow(ObservableList<ProductInfo> productInfos){
        productAnchorPane.getChildren().clear();
        for(int i=0; i<productInfos.size();i++){
            initItem(productInfos.get(i), i);
        }
    }
    public void showCategory(Category category) throws Exception {
        nameLabel.setText(category.getName());
        descriptionLabel.setText(category.getDescription());
        initInfo(category);
        initShow(showedProducts);
    }

    public Label categoryLabel(Category category){
        Label label = new Label();
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
        label.setPrefHeight(30);
        label.setPrefWidth(250);
        label.setText(category.getName());
        label.setTextFill(Color.web("#1c87b9"));
        label.setOnMouseClicked(mouseEventEventHandler);
        return label;
    }

    private void addListOfCategory() throws Exception {
        categoryListView.getItems().clear();
        ObservableList<Label> labels = FXCollections.observableArrayList();
        for(var x: CategoryManager.getManager().getAll()){
            Label label = categoryLabel(x);
            labels.add(label);
        }
        labels.sort((o1,o2)->
            Integer.compare(o1.getText().toLowerCase().compareTo(o2.getText().toLowerCase()), 0));
        categoryListView.setItems(labels);
    }
    private final ObservableList<ProductInfo> showedProducts = FXCollections.observableArrayList();
    public void initInfo(Category category) throws Exception {
        showedProducts.clear();
        ObservableList<ProductInfo> temp = FXCollections.observableArrayList();
        temp.addAll(ProductInfoManager.getManager().getAllMusicInfo());
        temp.addAll(ProductInfoManager.getManager().getAllBookInfo());
        temp.addAll(ProductInfoManager.getManager().getAllFilmInfo());
        for(var product: temp){
            if(product.getCategory().getName().equals(category.getName()))
                showedProducts.add(product);

        }
    }

    public String mode="";

    public void setAddButton(){
        nameTextField.setText("");
        descriptionTextArea.setText("");
        addCategoryAnchorPane.toFront();
        mode="add";
    }
    public void setCloseButton(){
        nameTextField.setText("");
        descriptionTextArea.setText("");
        addCategoryAnchorPane.toBack();
        mode="";
    }
    public void setDoneButton() throws Exception {
        if(mode.equals(""))
            return;
        if(nameTextField.getText().equals("")||descriptionTextArea.getText().equals(""))
            return;
        if(mode.equals("add")){
            Category category = new Category();
            category.setName(nameTextField.getText());
            category.setDescription(descriptionTextArea.getText());
            CategoryManager.getManager().add(category);
            this.category= category;
        }
        if(mode.equals("edit")){
            this.category.setName(nameTextField.getText());
            this.category.setDescription(descriptionTextArea.getText());
            CategoryManager.getManager().update(category);
        }
        showCategory(this.category);
        addListOfCategory();
        setCloseButton();
        mode="";
    }

    public void setEditButton(){
        nameTextField.setText(category.getName());
        descriptionTextArea.setText(category.getDescription());
        addCategoryAnchorPane.toFront();
        mode="edit";
    }

    public void setReloadButton() throws Exception {
        if(category!=null)
            showCategory(this.category);
        addListOfCategory();
    }

    public void initialize(URL url , ResourceBundle resourceBundle){
        setRoot(ancestor);
        try {
            addListOfCategory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
