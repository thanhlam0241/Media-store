package com.shopkeeper.lam.models;

import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public final class MusicInfo extends ProductInfo {
    private ArrayList<Person> musicians;//danh sach ca si/nhac si
    private LocalTime timeLimit ;//thoi luong dia nhac
    public MusicInfo(){
        super();
        this.musicians= new ArrayList<>(Arrays.asList(DatabaseAdapter.getDbAdapter().getAllPeople().get(0)));
        this.timeLimit=LocalTime.of(0,4,0);
    }
    public MusicInfo(String title, String description, Category category, LocalDate releaseDate, double currentSalePrice, Publisher publisher, double rating, ArrayList<String> award,ArrayList<Person> musicians, LocalTime timeLimit) {
        super(title, description, category, releaseDate, currentSalePrice, publisher, rating, award);
        this.setProductInfoId(0);
        this.musicians=musicians;
        this.timeLimit=timeLimit;
    }

    public ArrayList<Person> getMusicians() {
        return musicians;
    }

    public void setMusicians(ArrayList<Person> musicians) {
        this.musicians = musicians;
    }

    public LocalTime getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(LocalTime timeLimit) {
        this.timeLimit = timeLimit;
    }
}
