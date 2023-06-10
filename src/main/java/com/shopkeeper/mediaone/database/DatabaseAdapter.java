package com.shopkeeper.mediaone.database;

import com.shopkeeper.lam.database.*;
import com.shopkeeper.lam.models.*;
import com.shopkeeper.linh.database.*;
import com.shopkeeper.linh.models.*;
import com.shopkeeper.minh.database.*;
import com.shopkeeper.minh.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseAdapter {
    private static DatabaseAdapter _adapter;
    public static DatabaseAdapter getDbAdapter() {
        if(_adapter == null){
            try{
                _adapter = new DatabaseAdapter();
                _adapter.load();
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return _adapter;
    }
    private DbAdapterCache cache;
    private ReadOnlyDbAdapterCache readOnlyCache;
    //Linh
    private StaffDbSet staffDbSet;
    private SettingsDbSet settingsDbSet;
    private CustomerDbSet customerDbSet;
    private TextValueDbSet textValueDbSet;
    private SaleBillDbSet saleBillDbSet;
    private FeedbackDbSet feedbackDbSet;
    //minh
    private ImportBillDbSet importBillDbSet;
    private OtherBillDbSet otherBillDbSet;
    private AttendanceDbSet attendanceDbSet;
    private ShiftDbSet shiftDbSet;
    private  StaffBillDbSet staffBillDbSet;
    //lam
    private PersonDbSet personDbSet;
    private CategoryDbSet categoryDbSet;
    private PublisherDbSet publisherDbSet;
    private BookInfoDbSet bookInfoDbSet;
    private FilmInfoDbSet filmInfoDbSet;
    private MusicInfoDbSet musicInfoDbSet;
    private ProductDbSet productDbSet;
    private DatabaseAdapter() throws Exception {
        File dbFile = new File("mediaone.db");
        cache = new DbAdapterCache();
        readOnlyCache = new ReadOnlyDbAdapterCache(cache);
        if(dbFile.exists() && !dbFile.isDirectory()) {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:mediaone.db");
            //Lam
            personDbSet = new PersonDbSet(conn, readOnlyCache, cache.getPeople());
            categoryDbSet = new CategoryDbSet(conn, readOnlyCache, cache.getCategories());
            publisherDbSet = new PublisherDbSet(conn, readOnlyCache, cache.getPublishers());
            bookInfoDbSet = new BookInfoDbSet(conn, readOnlyCache, cache.getBookInfos());
            filmInfoDbSet = new FilmInfoDbSet(conn, readOnlyCache, cache.getFilmInfos());
            musicInfoDbSet = new MusicInfoDbSet(conn, readOnlyCache, cache.getMusicInfos());
            productDbSet = new ProductDbSet(conn, readOnlyCache, cache.getProducts());
            //Linh
            staffDbSet = new StaffDbSet(conn, readOnlyCache, cache.getStaffs());
            settingsDbSet = new SettingsDbSet(conn, readOnlyCache, cache.getSettings());
            customerDbSet = new CustomerDbSet(conn, readOnlyCache, cache.getCustomers());
            textValueDbSet = new TextValueDbSet(conn);
            saleBillDbSet = new SaleBillDbSet(conn, readOnlyCache, cache.getSaleBills());
            feedbackDbSet = new FeedbackDbSet(conn, readOnlyCache, cache.getFeedbacks());

            //Minh
            importBillDbSet = new ImportBillDbSet(conn, readOnlyCache, cache.getImportBills());
            otherBillDbSet = new OtherBillDbSet(conn, readOnlyCache, cache.getOtherBills());
            attendanceDbSet = new AttendanceDbSet(conn, readOnlyCache, cache.getAttendances());
            shiftDbSet = new ShiftDbSet(conn, readOnlyCache, cache.getShifts());
            staffBillDbSet = new StaffBillDbSet(conn, readOnlyCache, cache.getStaffBills());
        }
        else{
            //Create new database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:mediaone.db");
            //Lam
            personDbSet = new PersonDbSet(conn, readOnlyCache, cache.getPeople());
            categoryDbSet = new CategoryDbSet(conn, readOnlyCache, cache.getCategories());
            publisherDbSet = new PublisherDbSet(conn, readOnlyCache, cache.getPublishers());
            bookInfoDbSet = new BookInfoDbSet(conn, readOnlyCache, cache.getBookInfos());
            filmInfoDbSet = new FilmInfoDbSet(conn, readOnlyCache, cache.getFilmInfos());
            musicInfoDbSet = new MusicInfoDbSet(conn, readOnlyCache, cache.getMusicInfos());
            productDbSet = new ProductDbSet(conn, readOnlyCache, cache.getProducts());
            //Linh
            staffDbSet = new StaffDbSet(conn, readOnlyCache, cache.getStaffs());
            settingsDbSet = new SettingsDbSet(conn, readOnlyCache, cache.getSettings());
            customerDbSet = new CustomerDbSet(conn, readOnlyCache, cache.getCustomers());
            textValueDbSet = new TextValueDbSet(conn);
            saleBillDbSet = new SaleBillDbSet(conn, readOnlyCache, cache.getSaleBills());
            feedbackDbSet = new FeedbackDbSet(conn, readOnlyCache, cache.getFeedbacks());

            //Minh
            importBillDbSet = new ImportBillDbSet(conn, readOnlyCache, cache.getImportBills());
            otherBillDbSet = new OtherBillDbSet(conn, readOnlyCache, cache.getOtherBills());
            attendanceDbSet = new AttendanceDbSet(conn, readOnlyCache, cache.getAttendances());
            shiftDbSet = new ShiftDbSet(conn, readOnlyCache, cache.getShifts());
            staffBillDbSet = new StaffBillDbSet(conn, readOnlyCache, cache.getStaffBills());

            //initialize all tables
            //Lam
            if (!categoryDbSet.createTable()) throw new Exception("DatabaseWorker1 created Category tables false");
            if (!personDbSet.createTable()) throw new Exception("DatabaseWorker1 created People tables false");
            if(!productDbSet.createTable()) throw new Exception("DatabaseWorker1 created Products tables false");
            if(!publisherDbSet.createTable()) throw new Exception("DatabaseWorker1 created Publishers tables false");
            if(!musicInfoDbSet.createTable()) throw new Exception("DatabaseWorker1 created MusicInfos tables false");
            if(!filmInfoDbSet.createTable()) throw new Exception("DatabaseWorker1 created FilmInfos tables false");
            if(!bookInfoDbSet.createTable()) throw new Exception("DatabaseWorker1 created BookInfos tables false");
            //Linh
            if(!staffDbSet.createTable()) throw new Exception("DatabaseWorker2 created Staffs tables false");
            if(!settingsDbSet.createTable()) throw new Exception("DatabaseWorker2 created Settings tables false");
            if(!customerDbSet.createTable()) throw new Exception("DatabaseWorker2 created Customers tables false");
            if(!textValueDbSet.createTable()) throw new Exception("DatabaseWorker2 created TextValues tables false");
            if(!saleBillDbSet.createTable()) throw new Exception("DatabaseWorker2 created SaleBills tables false");
            if(!feedbackDbSet.createTable()) throw new Exception("DatabaseWorker2 created Feedbacks tables false");
            //Minh
            if(!otherBillDbSet.createTable()) throw new Exception("DatabaseWorker3 created OtherBills tables false");
            if(!importBillDbSet.createTable()) throw new Exception("DatabaseWorker3 created ImportBills tables false");
            if(!staffBillDbSet.createTable()) throw new Exception("DatabaseWorker3 created StaffBills tables false");
            if (!attendanceDbSet.createTable()) throw new Exception("DatabaseWorker3 created Attendances tables false");
            if (!shiftDbSet.createTable()) throw new Exception("DatabaseWorker3 created Shifts tables false");

        }
    }
    private void load() throws Exception{
        //Load 1
        staffDbSet.load();
        settingsDbSet.load();
        customerDbSet.load();
        otherBillDbSet.load();
        importBillDbSet.load();
        categoryDbSet.load();
        publisherDbSet.load();
        personDbSet.load();

        //Load 2
        saleBillDbSet.load();
        attendanceDbSet.load();
        staffBillDbSet.load();
        shiftDbSet.load();
        musicInfoDbSet.load();
        filmInfoDbSet.load();
        bookInfoDbSet.load();
        //Load 3
        productDbSet.load();
        //Load 4
        feedbackDbSet.load();

    }
    //-------------------Linh-------------------
    //region Staffs Database
    public ObservableList<Staff> getAllStaffs(){
        return readOnlyCache.getStaffs();
    }
    public boolean insertStaff(Staff staff){
        return staffDbSet.insert(staff);
    }
    public boolean updateStaff(Staff staff){
        return staffDbSet.update(staff);
    }
    public boolean deleteStaff(Staff staff){
        return staffDbSet.delete(staff);
    }
    //endregion
    //region Settings
    public Settings getSettings(){
        return readOnlyCache.getSettings();
    }
    public boolean updateSettings(Settings settings){
        return settingsDbSet.update(settings);
    }
    public boolean resetSettings(Settings settings){
        return settingsDbSet.reset(settings);

    }
    //endregion
    //region Customers
    public ObservableList<Customer> getAllCustomers(){
        return readOnlyCache.getCustomers();
    }
    public boolean insertCustomer(Customer customer){
        return customerDbSet.insert(customer);
    }
    public boolean updateCustomer(Customer customer){
        return  customerDbSet.update(customer);
    }
    public boolean deleteCustomer(Customer customer){
        return  customerDbSet.deleteCustomer(customer);
    }
    //endregion
    //region TextValue
    //Return true if success, otherwise return false
    public boolean setTextValue(String key, String value) {
        return textValueDbSet.set(key, value);
    }
    public String getTextValue(String key) {
        return textValueDbSet.get(key);
    }
    //endregion
    //region SaleBill
    public ObservableList<SaleBill> getAllSaleBills(){
        return readOnlyCache.getSaleBills();
    }
    public boolean insertSaleBill(SaleBill bill) {
        return saleBillDbSet.insert(bill);
    }
    public boolean updateSaleBill(SaleBill bill) {
        return saleBillDbSet.update(bill);
    }
    public boolean deleteSaleBill(SaleBill bill) {
        return saleBillDbSet.delete(bill);
    }
    public ArrayList<Product> getItems(SaleBill bill){
        ArrayList<Product> products = new ArrayList<>();
        cache.getProducts().forEach(product -> {
            if(product.getSaleBill() == bill) products.add(product);
        });
        return products;
    }
    //endregion
    //region Feedback
    public ObservableList<Feedback> getAllFeedbacks(){
        return readOnlyCache.getFeedbacks();
    }
    public boolean insertFeedback(Feedback feedback){
        return feedbackDbSet.insert(feedback);
    }
    public boolean updateFeedback(Feedback feedback) {
        return feedbackDbSet.update(feedback);
    }
    public boolean deleteFeedback(Feedback feedback) {
        return feedbackDbSet.delete(feedback);
    }
    public boolean updateIsUseful(Feedback feedback) {
        return feedbackDbSet.updateIsUseful(feedback);
    }
    public boolean markAsRead(Feedback feedback) {
        return feedbackDbSet.markAsRead(feedback);
    }
    //endregion
    //-------------------Lam-------------------
    //region Category
    public ObservableList<Category> getAllCategories(){
        return readOnlyCache.getCategories();
    }
    public boolean insertCategory(Category category){
        return categoryDbSet.insert(category);
    }
    public boolean updateCategory(Category category) {
        return categoryDbSet.update(category);
    }
    public boolean deleteCategory(Category category) {
        return categoryDbSet.deleteCategory(category);

    }
    //endregion
    //region Publisher
    public ObservableList<Publisher> getAllPublishers(){
        return readOnlyCache.getPublishers();
    }
    public boolean insertPublisher(Publisher publisher){
        return publisherDbSet.insert(publisher);
    }
    public boolean updatePublisher(Publisher publisher){
        return publisherDbSet.update(publisher);
    }
    public boolean deletePublisher(Publisher publisher){
        return publisherDbSet.deletePublisher(publisher);
    }
    //endregion
    //region Person
    public ObservableList<Person> getAllPeople(){
        return readOnlyCache.getPeople();
    }
    public boolean insertPerson(Person person){
        return personDbSet.insert(person);
    }
    public boolean updatePerson(Person person){
        return personDbSet.update(person);
    }
    public boolean deletePerson(Person person) {
        return personDbSet.deletePerson(person);

    }
    //endregion
    //region MusicInfo
    public ObservableList<MusicInfo> getAllMusicInfos(){
        return readOnlyCache.getMusicInfos();
    }
    public boolean insertMusicInfo(MusicInfo info) {
        return musicInfoDbSet.insert(info);
    }
    public boolean updateMusicInfo(MusicInfo info) {
        return musicInfoDbSet.update(info);
    }
    public boolean deleteMusicInfo(MusicInfo info) {
        return musicInfoDbSet.delete(info);
    }
    //endregion
    //region FilmInfo
    public ObservableList<FilmInfo> getAllFilmInfos(){
        return readOnlyCache.getFilmInfos();
    }
    public boolean insertFilmInfo(FilmInfo info) {
        return filmInfoDbSet.insert(info);
    }
    public boolean updateFilmInfo(FilmInfo info) {
        return filmInfoDbSet.update(info);
    }
    public boolean deleteFilmInfo(FilmInfo info) {
        return filmInfoDbSet.delete(info);
    }
    //endregion
    //region BookInfo
    public ObservableList<BookInfo> getAllBookInfos(){
        return readOnlyCache.getBookInfos();
    }
    public boolean insertBookInfo(BookInfo info) {
        return bookInfoDbSet.insert(info);
    }
    public boolean updateBookInfo(BookInfo info) {
        return bookInfoDbSet.update(info);
    }
    public boolean deleteBookInfo(BookInfo info) {
        return bookInfoDbSet.delete(info);
    }
    //endregion
    //region Product
    public ObservableList<Product> getAllProducts(){
        return readOnlyCache.getProducts();
    }
    public boolean insertProduct(Product product) {
        return productDbSet.insert(product);
    }
    public boolean updateProduct(Product product) {
        return productDbSet.update(product);
    }
    public boolean deleteProduct(Product product) {
        return productDbSet.deleteProduct(product);
    }
    //endregion
    //-------------------Minh-------------------
    //region ImportBill
    public ObservableList<ImportBill> getAllImportBills(){
        return readOnlyCache.getImportBills();
    }
    public boolean insertImportBill(ImportBill bill){
        return importBillDbSet.insert(bill);
    }
    public boolean updateImportBill(ImportBill bill) {
        return importBillDbSet.update(bill);
    }
    public boolean deleteImportBill(ImportBill bill){
        return importBillDbSet.delete(bill);

    }
    public ArrayList<Product> getItems(ImportBill bill){
        ArrayList<Product> products = new ArrayList<>();
        cache.getProducts().forEach(product -> {
            if(product.getImportBill() == bill) products.add(product);
        });
        return products;
    }
    //endregion
    //region OtherBill
    public ObservableList<OtherBill> getAllOtherBills(){
        return readOnlyCache.getOtherBills();
    }
    public boolean insertOtherBill(OtherBill bill){
        return otherBillDbSet.insert(bill);
    }
    public boolean updateOtherBill(OtherBill bill) {
        return otherBillDbSet.update(bill);
    }
    public boolean deleteOtherBill(OtherBill bill){
        return otherBillDbSet.delete(bill);

    }
    //endregion
    //region StaffBill
    public ObservableList<StaffBill> getAllStaffBills(){
        return readOnlyCache.getStaffBills();
    }
    public boolean insertStaffBill(StaffBill bill) {
        return staffBillDbSet.insert(bill);
    }
    public boolean updateStaffBill(StaffBill bill) {
        return staffBillDbSet.update(bill);
    }
    public boolean deleteStaffBill(StaffBill bill) {
        return staffBillDbSet.delete(bill);
    }
    //endregion
    //region Attendance
    public ObservableList<Attendance> getAllAttendances(){
        return readOnlyCache.getAttendances();
    }
    public boolean insertAttendance(Attendance attendance) {
        return attendanceDbSet.insert(attendance);
    }
    public boolean updateAttendance(Attendance attendance) {
        return attendanceDbSet.update(attendance);
    }
    public boolean deleteAttendance(Attendance attendance) {
        return attendanceDbSet.delete(attendance);
    }
    //endregion
    //region Shift
    public ObservableList<Shift> getAllShifts(){
        return readOnlyCache.getShifts();
    }
    public boolean insertShift(Shift shift) {
        return shiftDbSet.insert(shift);
    }
    public boolean updateShift(Shift shift) {
        return shiftDbSet.update(shift);
    }
    public boolean deleteShift(Shift shift) {
        return shiftDbSet.delete(shift);
    }
    //endregion
}
