package com.shopkeeper.lam.models;

import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public final class FilmInfo extends ProductInfo{
    private Person director;//đạo diễn
    private ArrayList<Person> actors;//danh sách diễn viên
    private LocalTime timeLimit;//thoi luong phim

    public FilmInfo(){

        super();
        this.director = DatabaseAdapter.getDbAdapter().getAllPeople().get(0);
        this.actors = new ArrayList<>(Arrays.asList(DatabaseAdapter.getDbAdapter().getAllPeople().get(0)));
        this.timeLimit = LocalTime.of(2,0,20);
    }


    public FilmInfo(String title, String description, Category category,  LocalDate releaseDate, double currentSalePrice, Publisher publisher,  double rating, ArrayList<String> award,Person director,ArrayList<Person> actors,LocalTime timeLimit) {
        super(title, description, category, releaseDate, currentSalePrice, publisher, rating, award);
        super.setProductInfoId(0);
        this.director=director;
        this.actors=actors;
        this.timeLimit=timeLimit;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public ArrayList<Person> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Person> actors) {
        this.actors = actors;
    }

    public LocalTime getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(LocalTime timeLimit) {
        this.timeLimit = timeLimit;
    }
}
