package com.shopkeeper.mediaone.database;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.linh.models.*;
import com.shopkeeper.minh.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadOnlyDbAdapterCache {
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

    public ReadOnlyDbAdapterCache(DbAdapterCache cache){
        //region linh
        staffs = FXCollections.unmodifiableObservableList(cache.getStaffs());
        settings = cache.getSettings();
        customers = FXCollections.unmodifiableObservableList(cache.getCustomers());
        saleBills = FXCollections.unmodifiableObservableList(cache.getSaleBills());
        feedbacks = FXCollections.unmodifiableObservableList(cache.getFeedbacks());
        //endregion
        //region lam
        publishers = FXCollections.unmodifiableObservableList(cache.getPublishers());
        categories = FXCollections.unmodifiableObservableList(cache.getCategories());
        people = FXCollections.unmodifiableObservableList(cache.getPeople());
        musicInfos = FXCollections.unmodifiableObservableList(cache.getMusicInfos());
        filmInfos = FXCollections.unmodifiableObservableList(cache.getFilmInfos());
        bookInfos = FXCollections.unmodifiableObservableList(cache.getBookInfos());
        products = FXCollections.unmodifiableObservableList(cache.getProducts());
        //endregion
        //region minh
        importBills = FXCollections.unmodifiableObservableList(cache.getImportBills());
        otherBills = FXCollections.unmodifiableObservableList(cache.getOtherBills());
        staffBills = FXCollections.unmodifiableObservableList(cache.getStaffBills());
        attendances = FXCollections.unmodifiableObservableList(cache.getAttendances());
        shifts = FXCollections.unmodifiableObservableList(cache.getShifts());
        //endregion
    }
}
