package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.lam.funtions.PersonManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.models.*;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class PersonPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor, productAnchorPane, addPersonAnchorPane;
    @FXML
    Label nameLabel, descriptionLabel, jobLabel, birthdayLabel, ageLabel;
    @FXML
    ListView<Label> personListView;
    @FXML
    TextField nameTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    DatePicker birthdayDatePicker;
    @FXML
    ComboBox<String> jobComboBox;
    private Person person;


    EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
        String namePer= ((Label)mouseEvent.getSource()).getText();
        try {
            for( var per : PersonManager.getManager().getAll()){
                if(per.getName().equals(namePer)){
                    person = per;
                    showPerson(per);
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
    public void showPerson(Person person) throws Exception {
        nameLabel.setText(person.getName());
        descriptionLabel.setText(person.getDescription());
        ageLabel.setText(person.getAge()+" years old");
        LocalDate date= person.getDateOfBirth();
        birthdayLabel.setText(date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear());
        jobLabel.setText(person.getJob().toString());
        initInfo(person);
        initShow(showedProducts);
    }

    public Label personLabel(Person person){
        Label label = new Label();
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
        label.setPrefHeight(30);
        label.setPrefWidth(250);
        label.setText(person.getName());
        label.setTextFill(Color.web("#1c87b9"));
        label.setOnMouseClicked(mouseEventEventHandler);
        return label;
    }

    public void addListOfPerson() throws Exception {
        personListView.getItems().clear();
        ObservableList<Label> labels = FXCollections.observableArrayList();
        for(var x: PersonManager.getManager().getAll()){
            Label label = personLabel(x);
            labels.add(label);
        }
        labels.sort((o1,o2)->
                Integer.compare(o1.getText().toLowerCase().compareTo(o2.getText().toLowerCase()), 0));

        personListView.setItems(labels);
    }
    private final ObservableList<ProductInfo> showedProducts = FXCollections.observableArrayList();
    public void initInfo(Person person) throws Exception {
        showedProducts.clear();
        ObservableList<ProductInfo> temp = FXCollections.observableArrayList();
        temp.addAll(ProductInfoManager.getManager().getAllMusicInfo());
        temp.addAll(ProductInfoManager.getManager().getAllBookInfo());
        temp.addAll(ProductInfoManager.getManager().getAllFilmInfo());
        for(var product: temp){
            if(product instanceof BookInfo){
                for(Person x : ((BookInfo) product).getAuthors()) {
                    if (x == null) {
                        Person zero = DatabaseAdapter.getDbAdapter().getAllPeople().get(0);
                        ((BookInfo) product).setAuthors(new ArrayList<>(Collections.singletonList(zero)));
                        ProductInfoManager.getManager().update(product);
                        break;
                    }
                    if (x.getName().equals(person.getName()))
                        if(!showedProducts.contains(product))
                            showedProducts.add(product);
                }
            }
            if(product instanceof MusicInfo){
                for(Person x : ((MusicInfo) product).getMusicians()) {
                    if (x == null) {
                        Person zero = DatabaseAdapter.getDbAdapter().getAllPeople().get(0);
                        ((MusicInfo) product).setMusicians(new ArrayList<>(Collections.singletonList(zero)));
                        ProductInfoManager.getManager().update(product);
                        break;
                    }
                    if (x.getName().equals(person.getName()))
                        if(!showedProducts.contains(product))
                            showedProducts.add(product);
                }
            }
            if(product instanceof FilmInfo){
                for(Person x : ((FilmInfo) product).getActors()) {
                    if (x == null) {
                        Person zero = DatabaseAdapter.getDbAdapter().getAllPeople().get(0);
                        ((FilmInfo) product).setActors(new ArrayList<>(Collections.singletonList(zero)));
                        ProductInfoManager.getManager().update(product);
                        break;
                    }
                    if (x.getName().equals(person.getName()))
                        if(!showedProducts.contains(product))
                           showedProducts.add(product);
                }
                var x= ((FilmInfo) product).getDirector();
                if (x == null) {
                    Person zero = DatabaseAdapter.getDbAdapter().getAllPeople().get(0);
                    ((FilmInfo) product).setDirector(zero);
                    ProductInfoManager.getManager().update(product);
                    break;
                }
                if(x.getName().equals(person.getName()))
                    if(!showedProducts.contains(product))
                        showedProducts.add(product);
            }
        }
    }

    public String mode="";

    public void setAddButton(){
        nameTextField.setText("");
        jobComboBox.setValue("");
        descriptionTextArea.setText("");
        birthdayDatePicker.setValue(LocalDate.now());
        addPersonAnchorPane.toFront();
        mode="add";
    }
    public void setCloseButton(){
        nameTextField.setText("");
        jobComboBox.setValue("");
        descriptionTextArea.setText("");
        birthdayDatePicker.setValue(LocalDate.now());
        addPersonAnchorPane.toBack();
        mode="";
    }
    public void setDoneButton() throws Exception {
        if(mode.equals(""))
            return;
        if(nameTextField.getText().equals("")||descriptionTextArea.getText().equals("")||jobComboBox.getValue().equals(""))
            return;
        if(mode.equals("add")){
            Person person= new Person();
            person.setName(nameTextField.getText());
            person.setDescription(descriptionTextArea.getText());
            person.setDateOfBirth(birthdayDatePicker.getValue());
            person.setJob(JobOfPerson.valueOf(jobComboBox.getValue()));
            this.person = person;
            PersonManager.getManager().add(person);
        }
        if(mode.equals("edit")){
            this.person.setName(nameTextField.getText());
            this.person.setDescription(descriptionTextArea.getText());
            this.person.setDateOfBirth(birthdayDatePicker.getValue());
            this.person.setJob(JobOfPerson.valueOf(jobComboBox.getValue()));

            PersonManager.getManager().update(person);
        }
        showPerson(this.person);
        addListOfPerson();
        setCloseButton();
        mode="";
    }

    public void setEditButton(){
        nameTextField.setText(person.getName());
        descriptionTextArea.setText(person.getDescription());
        birthdayDatePicker.setValue(person.getDateOfBirth());
        jobComboBox.setValue(person.getJob().name());
        addPersonAnchorPane.toFront();
        mode="edit";
    }

    public void setReloadButton() throws Exception {
        if(person!=null)
            showPerson(this.person);
        addListOfPerson();
    }

    public void setJobComboBox(){
        ObservableList<String> job= FXCollections.observableArrayList();
        job.addAll("SINGER","ACTOR", "MUSICIAN", "WRITER" ,
                "FILM_DIRECTOR", "FOOTBALLER", "CEO", "TEACHER", "POET", "LEGEND",  "MOTIVATIONAL_SPEAKER", "MOTIVATIONAL_AUTHOR");
        jobComboBox.setItems(job);
    }


    public void initialize(URL url , ResourceBundle resourceBundle){
        setRoot(ancestor);
        try {
            addListOfPerson();
            setJobComboBox();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
