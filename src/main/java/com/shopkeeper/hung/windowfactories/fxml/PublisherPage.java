package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.funtions.PublisherManager;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.lam.models.Publisher;
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

public class PublisherPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor, productAnchorPane, addPublisherAnchorPane;
    @FXML
    Label nameLabel, descriptionLabel, addressLabel;
    @FXML
    ListView<Label> publisherListView;
    @FXML
    TextField nameTextField, addressTextField;
    @FXML
    TextArea descriptionTextArea;

    private Publisher publisher;


    EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
        String namePub= ((Label)mouseEvent.getSource()).getText();
        try {
            for( var pub : PublisherManager.getManager().getAll()){
                if(pub.getName().equals(namePub)){
                    publisher = pub;
                    showPublisher(pub);
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
    public void showPublisher(Publisher publisher) throws Exception {
        nameLabel.setText(publisher.getName());
        addressLabel.setText(publisher.getAddress());
        descriptionLabel.setText(publisher.getDescription());
        initInfo(publisher);
        initShow(showedProducts);
    }

    public Label publisherLabel(Publisher publisher){
        Label label = new Label();
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
        label.setPrefHeight(30);
        label.setPrefWidth(250);
        label.setText(publisher.getName());
        label.setTextFill(Color.web("#1c87b9"));
        label.setOnMouseClicked(mouseEventEventHandler);
        return label;
    }

    public void addListOfPublisher() throws Exception {
        publisherListView.getItems().clear();
        ObservableList<Label> labels = FXCollections.observableArrayList();
        for(var x: PublisherManager.getManager().getAll()){
            Label label = publisherLabel(x);
            labels.add(label);
        }
        labels.sort((o1,o2)->
                Integer.compare(o1.getText().toLowerCase().compareTo(o2.getText().toLowerCase()), 0));
        publisherListView.setItems(labels);
    }
    private final ObservableList<ProductInfo> showedProducts = FXCollections.observableArrayList();
    public void initInfo(Publisher publisher) throws Exception {
        showedProducts.clear();
        ObservableList<ProductInfo> temp = FXCollections.observableArrayList();
        temp.addAll(ProductInfoManager.getManager().getAllMusicInfo());
        temp.addAll(ProductInfoManager.getManager().getAllBookInfo());
        temp.addAll(ProductInfoManager.getManager().getAllFilmInfo());
        for(var product: temp){
            if(product.getPublisher().getName().equals(publisher.getName()))
                showedProducts.add(product);

        }
    }

    public String mode="";

    public void setAddButton(){
        nameTextField.setText("");
        addressTextField.setText("");
        descriptionTextArea.setText("");
        addPublisherAnchorPane.toFront();
        mode="add";
    }
    public void setCloseButton(){
        nameTextField.setText("");
        addressTextField.setText("");
        descriptionTextArea.setText("");
        addPublisherAnchorPane.toBack();
        mode="";
    }
    public void setDoneButton() throws Exception {
        if(mode.equals(""))
            return;
        if(nameTextField.getText().equals("")||descriptionTextArea.getText().equals("")||addressTextField.getText().equals(""))
            return;
        if(mode.equals("add")){
            Publisher publisher = new Publisher();
            publisher.setName(nameTextField.getText());
            publisher.setDescription(descriptionTextArea.getText());
            publisher.setAddress(addressTextField.getText());
            this.publisher= publisher;
            PublisherManager.getManager().add(publisher);
        }
        if(mode.equals("edit")){
            this.publisher.setName(nameTextField.getText());
            this.publisher.setDescription(descriptionTextArea.getText());
            this.publisher.setAddress(addressTextField.getText());
            PublisherManager.getManager().update(publisher);
        }
        showPublisher(this.publisher);
        addListOfPublisher();
        setCloseButton();
        mode="";
    }

    public void setEditButton(){
        nameTextField.setText(publisher.getName());
        descriptionTextArea.setText(publisher.getDescription());
        addressTextField.setText(publisher.getAddress());
        addPublisherAnchorPane.toFront();
        mode="edit";
    }

    public void setReloadButton() throws Exception {
        if(publisher!=null)
            showPublisher(this.publisher);
        addListOfPublisher();
    }



    public void initialize(URL url , ResourceBundle resourceBundle){
        setRoot(ancestor);
        try {
            addListOfPublisher();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
