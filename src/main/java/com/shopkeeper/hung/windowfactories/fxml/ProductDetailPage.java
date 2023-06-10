package com.shopkeeper.hung.windowfactories.fxml;

import com.jfoenix.controls.JFXButton;
import com.shopkeeper.hung.windowfactories.icons.Icon;
import com.shopkeeper.lam.funtions.CategoryManager;
import com.shopkeeper.lam.funtions.PersonManager;
import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.funtions.PublisherManager;
import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.ProductInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ProductDetailPage extends Controller implements Initializable {
    @FXML
    AnchorPane ancestor, showBookAnchorPane, showMusicAnchorPane, showFilmAnchorPane;
    @FXML
    JFXButton closeButton, editButton, deleteButton;
    @FXML
    Label name, award, rating, description,price, date, publisher, authors, pageNumber, category, timeMusic, timeFilm,musicians,
    actors, director, left  ;
    @FXML
    ImageView icon;
    private ProductInfo productInfo;

    public void setProductInfo(ProductInfo productInfo) throws Exception {
        this.productInfo=productInfo;
        if(productInfo.getNumberOfProduct() ==0)
            left.setText("Out of stock");
        else
            left.setText(productInfo.getNumberOfProduct()+" left");
        name.setText("Title: " +productInfo.getTitle());
        StringBuilder tmp= new StringBuilder();
        for(var x: productInfo.getAward())
            tmp.append(x).append('\n');
        award.setText(tmp.toString());
        rating.setText("Rating: "+(int)productInfo.getRating()+" / 5");
        description.setText(productInfo.getDescription());
        if(productInfo.getCategory()==null)
            productInfo.setCategory(CategoryManager.getManager().getAll().get(0));
        category.setText(productInfo.getCategory().getName());
        price.setText((int)productInfo.getCurrentSalePrice()/1000+",000 VND");
        LocalDate curDate= productInfo.getReleaseDate();
        date.setText(curDate.getDayOfMonth()+"/"+curDate.getMonthValue()+"/"+curDate.getYear());
        if(productInfo.getPublisher()==null)
            productInfo.setPublisher(PublisherManager.getManager().getAll().get(0));
        publisher.setText(productInfo.getPublisher().getName());
        if(productInfo instanceof BookInfo){
            icon.setImage(Icon.getBookIcon());
            pageNumber.setText(((BookInfo) productInfo).getNumberOfPage()+" pages");
            StringBuilder s= new StringBuilder();
            for(var temp : ((BookInfo) productInfo).getAuthors()){
                if(temp==null)
                    temp= PersonManager.getManager().getAll().get(0);
                s.append(temp.getName()).append("\n");
            }
            if(s.toString().equals(""))
                authors.setText("[không có thông tin]");
            else
                authors.setText(s.toString());
            showBookAnchorPane.toFront();
        }
        if(productInfo instanceof MusicInfo){
            icon.setImage( Icon.getMusicIcon());
            timeMusic.setText(((MusicInfo) productInfo).getTimeLimit().format(DateTimeFormatter.ofPattern("H:mm:ss")));
            StringBuilder s= new StringBuilder();
            for(var temp : ((MusicInfo) productInfo).getMusicians()){
                if(temp==null)
                    temp=PersonManager.getManager().getAll().get(0);
                s.append(temp.getName()).append("\n");
            }
            if(s.toString().equals(""))
                musicians.setText("[không có thông tin]");
            else
                musicians.setText(s.toString());
            showMusicAnchorPane.toFront();
        }
        if(productInfo instanceof FilmInfo){
            icon.setImage( Icon.getFilmIcon());
            timeFilm.setText(((FilmInfo) productInfo).getTimeLimit().format(DateTimeFormatter.ofPattern("H:mm:ss"))+"");
            StringBuilder s= new StringBuilder();
            for(var temp : ((FilmInfo) productInfo).getActors()){
                if(temp==null)
                    temp=PersonManager.getManager().getAll().get(0);
                s.append(temp.getName()).append("\n");
            }
            if(s.toString().equals(""))
                actors.setText("[không có thông tin]");
            else
                actors.setText(s.toString());
            if(((FilmInfo) productInfo).getDirector() ==null)
                ((FilmInfo) productInfo).setDirector(PersonManager.getManager().getAll().get(0));
            director.setText(((FilmInfo) productInfo).getDirector().getName());
            showFilmAnchorPane.toFront();
        }
        //set các thành phần của detail về productInfo
    }

    public void setDeleteButton() throws Exception {
        ProductInfoManager.getManager().remove(productInfo);
        ProductPage parent =(ProductPage) this.getParent();
        parent.getShowedProducts().remove(productInfo);
        parent.initShow(parent.getShowedProducts());
        parent.getTempProduct().remove(productInfo);
        parent.getTempProduct().remove(productInfo);
        close();
    }

    public void setEditButton() throws IOException {
        System.out.println("đang thực hiện chỉnh sửa thông tin s phẩm ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-product-page.fxml"));
        loader.load();
        EditProductPage controller= loader.getController();
        this.add(controller);
        controller.setProductInfo(productInfo);
    }

    public void setRedCloseButton(){
        closeButton.setStyle("-fx-background-color: #ffcccb");
    }
    public void setNormalCloseButton(){
        closeButton.setStyle("-fx-background-color: transparent");
    }

    public void setType2(){
        getRoot().getChildren().remove(deleteButton);
        getRoot().getChildren().remove(editButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.setRoot(ancestor);

    }

}
