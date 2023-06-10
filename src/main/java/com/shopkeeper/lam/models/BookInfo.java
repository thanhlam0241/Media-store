package com.shopkeeper.lam.models;

import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public final class BookInfo extends ProductInfo{
    private ArrayList<Person> authors;//tac gia
    private int numberOfPage;//so trang cua sach

    public BookInfo(){

        super();
        this.authors= new ArrayList<>(Arrays.asList(DatabaseAdapter.getDbAdapter().getAllPeople().get(0)));
        this.numberOfPage=0;
    }

    public BookInfo(String title,  String description, Category category, LocalDate releaseDate, double currentSalePrice, Publisher publisher, double rating, ArrayList<String> award, ArrayList<Person> authors, int numberOfPage) {
        super(title,  description, category, releaseDate, currentSalePrice, publisher,rating, award);
        super.setProductInfoId(0);
        setNumberOfPage(numberOfPage);
        this.authors=authors;
    }

    public ArrayList<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Person> authors) {
        this.authors = authors;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
}
