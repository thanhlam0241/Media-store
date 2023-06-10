package com.shopkeeper.hung.windowfactories.fxml;

import com.shopkeeper.lam.funtions.CategoryManager;
import com.shopkeeper.lam.funtions.PersonManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.funtions.PublisherManager;
import com.shopkeeper.lam.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EditProductPage extends Controller implements Initializable {
@FXML
AnchorPane ancestor, bookAnchorPane, musicAnchorPane, filmAnchorPane;
    @FXML
    TextField name,price, rating, pageNumber, timeMusic, timeFilm;
    @FXML
    TextArea award, description;
    @FXML
    ComboBox<String>  publisherComboBox, categoryComboBox, musicianComboBox, actorsComboBox, directorComboBox
            ,authorsComboBox ;
    @FXML
    DatePicker releaseDate;
    @FXML
    ListView<String> musiciansList, actorsListView, authorsListView;
    private ProductInfo productInfo ;
    private ProductInfo oldProductInfo;

    private final ObservableList<String> authorsName = FXCollections.observableArrayList();
    private final ObservableList<String> musicianName = FXCollections.observableArrayList();
    private final ObservableList<String> actorsName = FXCollections.observableArrayList();
    public boolean check(){
        if(name.getText().equals("") || price.getText().equals("")
                ||rating.getText().equals("") ||categoryComboBox.getValue().equals("")||award.getText().equals("")||description.getText().equals("") )
            return false;
        var x = productInfo;
        return(x.getPublisher()!= null &&x.getTitle()!=null &&x.getReleaseDate()!=null
                &&x.getAward()!=null && x.getCategory()!=null);
    }
    public void getInfo(){
        if(!name.getText().equals("")) productInfo.setTitle(name.getText());
        if(!price.getText().equals(""))productInfo.setCurrentSalePrice(Integer.parseInt(price.getText()));
        if(!description.getText().equals(""))productInfo.setDescription(description.getText());
        if(!rating.getText().equals(""))productInfo.setRating(Integer.parseInt(rating.getText()));
        if(!award.getText().equals(""))productInfo.setAward(new ArrayList<>(Arrays.asList(award.getText().split("\n")))   );
        productInfo.setReleaseDate(releaseDate.getValue());
    }

    public void setAddAuthorButton(){
        if(authorsComboBox.getValue()==null)
            return;
        authorsName.add(authorsComboBox.getValue());
    }

    public void setAddActorButton(){
        if(actorsComboBox.getValue()==null)
            return;
        actorsName.add(actorsComboBox.getValue());
    }

    public void setAddMusicianButton(){
        if(musicianComboBox.getValue()==null)
            return;
        musicianName.add(musicianComboBox.getValue());
    }
    public void removeAuthorButton(){
        if(authorsName.size()==0)
            return;
        authorsName.remove(authorsName.get(authorsName.size()-1));
    }

    public void removeActorButton(){
        if(actorsName.size()==0)
            return;
        actorsName.remove(actorsName.get(actorsName.size()-1));
    }

    public void removeMusicianButton(){
        if(musicianName.size()==0)
            return;
        musicianName.remove(musicianName.get(musicianName.size()-1));
    }

    public void setDoneButton() throws Exception {
        getInfo();
        if(!check()) return;


        if(this.productInfo instanceof  BookInfo){
            ArrayList<Person> authors = new ArrayList<>();
            try{
                var dbMusicians = PersonManager.getManager().getAll();
                for (var x : authorsName) {
                    int index = 0;
                    while (index < dbMusicians.size() && !dbMusicians.get(index).getName().equals(x))
                        index++;
                    if (index >= dbMusicians.size())
                        return;
                    authors.add(dbMusicians.get(index));
                }
                ((BookInfo) productInfo).setAuthors(authors);
                ((BookInfo) productInfo).setNumberOfPage(Integer.parseInt(pageNumber.getText()));

                ProductInfoManager.getManager().update(this.productInfo);
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Maybe at time format, minutes : seconds");
                alert.setHeaderText("Error in input for product information");
//            alert.setContentText("Connect to the database successfully!");
                alert.showAndWait();
                return;
            }

        }
        if(productInfo instanceof  FilmInfo){
            try{
                ArrayList<Person> actors = new ArrayList<>();
                var dbMusicians = PersonManager.getManager().getAll();
                Person director = new Person();
                for (var x : actorsName) {
                    int index = 0;
                    while (index < dbMusicians.size() && !dbMusicians.get(index).getName().equals(x))
                        index++;
                    if (index >= dbMusicians.size())
                        return;
                    actors.add(dbMusicians.get(index));
                }
                for (var x : dbMusicians)
                    if (x.getName().equals(directorComboBox.getValue()))
                        director = x;
                if (director.getName() == null)
                    return;
                ((FilmInfo) productInfo).setTimeLimit(LocalTime.parse(timeFilm.getText(), DateTimeFormatter.ofPattern("H:mm:ss")));
                ((FilmInfo) productInfo).setActors(actors);
                ((FilmInfo) productInfo).setDirector(director);
                ProductInfoManager.getManager().update(productInfo);
            }
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Maybe at time format, minutes : seconds");
                alert.setHeaderText("Error in input for product information");
//            alert.setContentText("Connect to the database successfully!");
                alert.showAndWait();
                return;
            }
        }
        if(productInfo instanceof MusicInfo){
            try{
                ArrayList<Person> musicians = new ArrayList<>();
                var dbMusicians = PersonManager.getManager().getAll();
                for (var x : musicianName) {
                    int index = 0;
                    while (index < dbMusicians.size() && !dbMusicians.get(index).getName().equals(x))
                        index++;
                    if (index >= dbMusicians.size())
                        return;
                    musicians.add(dbMusicians.get(index));
                }

                ((MusicInfo) productInfo).setMusicians(musicians);
                ((MusicInfo) productInfo).setTimeLimit(LocalTime.parse(timeMusic.getText(),DateTimeFormatter.ofPattern("H:mm:ss")));
                ProductInfoManager.getManager().update(productInfo);
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Maybe at time format, minutes : seconds");
                alert.setHeaderText("Error in input for product information");
//            alert.setContentText("Connect to the database successfully!");
                alert.showAndWait();
                return;
            }
        }
        ProductDetailPage parent =(ProductDetailPage) this.getParent();
        parent.setProductInfo(this.productInfo);
        ProductPage productPage= (ProductPage) parent.getParent();
//        productPage.reload();
        productPage.getTempProduct().set(productPage.getTempProduct().indexOf(oldProductInfo), productInfo);
        productPage.getShowedProducts().set(productPage.getShowedProducts().indexOf(oldProductInfo), productInfo);
//        productPage.getTempProduct().remove(oldProductInfo);
//        productPage.getTempProduct().add(productInfo);
//        productPage.getShowedProducts().remove(oldProductInfo);
//        productPage.getShowedProducts().add(productInfo);
        productPage.initShow(productPage.getShowedProducts());
        System.out.println("finish edit productInfo");
        this.getParent().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            setRoot(this.ancestor);

            var categories= CategoryManager.getManager().getAll();
            ObservableList<String> nameCategory = FXCollections.observableArrayList();
            for(var x: categories) nameCategory.add(x.getName());
            categoryComboBox.setItems(nameCategory);
            categoryComboBox.setStyle("-fx-font-size: 20");
            categoryComboBox.setStyle("-fx-font-family: 'Times New Roman'");

            ObservableList<String> namePublisher = FXCollections.observableArrayList();
            var publishers = PublisherManager.getManager().getAll();
            for(var x: publishers) namePublisher.add(x.getName());
            publisherComboBox.setItems(namePublisher);
            publisherComboBox.setStyle("-fx-font-size: 20");
            publisherComboBox.setStyle("-fx-font-family: 'Times New Roman'");

            ObservableList<String> nameMusician = FXCollections.observableArrayList();
            var musicians = PersonManager.getManager().getAll();
            for(var x: musicians) {
                if(x.getJob() == JobOfPerson.MUSICIAN || x.getJob() ==JobOfPerson.CEO)
                    nameMusician.add(x.getName());
            }
            musicianComboBox.setItems(nameMusician);
            musicianComboBox.setStyle("-fx-font-size: 20");
            musicianComboBox.setStyle("-fx-font-family: 'Times New Roman'");

            ObservableList<String> nameActors =FXCollections.observableArrayList();
            for(var x: musicians){
                if(x.getJob() == JobOfPerson.ACTOR  || x.getJob() ==JobOfPerson.CEO)
                    nameActors.add(x.getName());
            }
            actorsComboBox.setItems(nameActors);

            ObservableList<String> nameDirectors =FXCollections.observableArrayList();
            for(var x: musicians){
                if(x.getJob() == JobOfPerson.FILM_DIRECTOR  || x.getJob() ==JobOfPerson.CEO)
                    nameDirectors.add(x.getName());
            }
            directorComboBox.setItems(nameDirectors);

            ObservableList<String> nameAuthors =FXCollections.observableArrayList();
            for(var x: musicians){
                if(x.getJob() == JobOfPerson.MOTIVATIONAL_AUTHOR  || x.getJob() ==JobOfPerson.CEO)
                    nameAuthors.add(x.getName());
            }
            authorsComboBox.setItems(nameDirectors);

            actorsListView.setItems(actorsName);
            authorsListView.setItems(authorsName);
            musiciansList.setItems(musicianName);


        }catch (Exception e){
            System.out.println("bug init addProduct: " + e);
        }
    }


    public void setProductInfo(ProductInfo productInfo){
        this.oldProductInfo= productInfo;
        this.productInfo = productInfo;
        name.setText(productInfo.getTitle());
        price.setText(""+(int)productInfo.getCurrentSalePrice());
        releaseDate.setValue(productInfo.getReleaseDate());
        rating.setText((int)productInfo.getRating()+"");
        categoryComboBox.setValue(productInfo.getCategory().getName());

        StringBuilder s= new StringBuilder();
        for(var x: productInfo.getAward())
            s.append(x).append("\n");
        award.setText(s.toString());

        description.setText(productInfo.getDescription());
        publisherComboBox.setValue(productInfo.getPublisher().getName());

        if(productInfo instanceof  BookInfo){
            for(var x: ((BookInfo) productInfo).getAuthors())
                authorsName.add(x.getName());
            pageNumber.setText(((BookInfo) productInfo).getNumberOfPage()+"");
            bookAnchorPane.toFront();
        }
        if(productInfo instanceof MusicInfo){
            musicAnchorPane.toFront();
            timeMusic.setText(((MusicInfo) productInfo).getTimeLimit().format(DateTimeFormatter.ofPattern("H:mm:ss")));
            for( var x: ((MusicInfo) productInfo).getMusicians())
                musicianName.add(x.getName());
        }
        if(productInfo instanceof FilmInfo){
            filmAnchorPane.toFront();
            timeFilm.setText(((FilmInfo) productInfo).getTimeLimit().format(DateTimeFormatter.ofPattern("H:mm:ss")));
            for( var x: ((FilmInfo) productInfo).getActors())
                actorsName.add(x.getName());
            directorComboBox.setValue(((FilmInfo) productInfo).getDirector().getName());
        }
    }
}
