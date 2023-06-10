package com.shopkeeper.mediaone.database;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.linh.models.*;
import com.shopkeeper.minh.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbAdapterCache {
    //region linh
    private ObservableList<Staff> staffs;
    private Settings settings;
    private ObservableList<Customer> customers;
    private ObservableList<SaleBill> saleBills;
    private ObservableList<Feedback> feedbacks;
    //endregion
    //region lam
    private ObservableList<Publisher> publishers;
    private ObservableList<Category> categories;
    private ObservableList<Person> people;
    private ObservableList<BookInfo> bookInfos;
    private ObservableList<FilmInfo> filmInfos;
    private ObservableList<MusicInfo> musicInfos;
    private ObservableList<Product> products;
    //endregion
    //region minh
    private ObservableList<ImportBill> importBills;
    private ObservableList<OtherBill> otherBills;
    private ObservableList<StaffBill> staffBills;
    private ObservableList<Attendance> attendances;
    private ObservableList<Shift> shifts;
    //endregion
    public ObservableList<Staff> getStaffs(){
        return staffs;
    }
    public Settings getSettings(){
        return settings;
    }
    public ObservableList<Customer> getCustomers(){
        return customers;
    }

    public ObservableList<SaleBill> getSaleBills() {
        return saleBills;
    }

    public ObservableList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public ObservableList<Publisher> getPublishers() {
        return publishers;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Person> getPeople() {
        return people;
    }

    public ObservableList<MusicInfo> getMusicInfos() {
        return musicInfos;
    }

    public ObservableList<FilmInfo> getFilmInfos() {
        return filmInfos;
    }
    public ObservableList<BookInfo> getBookInfos() {
        return bookInfos;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public ObservableList<ImportBill> getImportBills() {
        return importBills;
    }

    public ObservableList<OtherBill> getOtherBills() {
        return otherBills;
    }

    public ObservableList<StaffBill> getStaffBills() {
        return staffBills;
    }

    public ObservableList<Attendance> getAttendances() {
        return attendances;
    }

    public ObservableList<Shift> getShifts() {
        return shifts;
    }

    public DbAdapterCache(){
        //region linh
        staffs = FXCollections.observableArrayList();
        settings = new Settings();
        customers = FXCollections.observableArrayList();
        saleBills = FXCollections.observableArrayList();
        feedbacks = FXCollections.observableArrayList();
        //endregion
        //region lam
        publishers = FXCollections.observableArrayList();
        categories = FXCollections.observableArrayList();
        people = FXCollections.observableArrayList();
        musicInfos = FXCollections.observableArrayList();
        filmInfos = FXCollections.observableArrayList();
        bookInfos = FXCollections.observableArrayList();
        products = FXCollections.observableArrayList();
        //endregion
        //region minh
        importBills = FXCollections.observableArrayList();
        otherBills = FXCollections.observableArrayList();
        staffBills = FXCollections.observableArrayList();
        attendances = FXCollections.observableArrayList();
        shifts = FXCollections.observableArrayList();
        //endregion
    }
}
