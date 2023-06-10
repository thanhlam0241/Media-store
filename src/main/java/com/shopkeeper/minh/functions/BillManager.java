package com.shopkeeper.minh.functions;

import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.mediaone.models.Bill;
import com.shopkeeper.minh.models.ImportBill;

import com.shopkeeper.minh.models.OtherBill;
import com.shopkeeper.minh.models.StaffBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.LocalDate;
import java.util.*;


public class BillManager {
    public ArrayList<DeletedInfo> deletedBills;
    private static BillManager manager;

    private BillManager() {
        deletedBills = new ArrayList<>();
    }

    public static BillManager getManager(){
        if (manager == null){
            manager = new BillManager();
        }
        return manager;
    }

    public ObservableList<ImportBill> getImportBills(LocalDate fromDate, LocalDate toDate)
            throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<ImportBill> importBills = adapter.getAllImportBills();
        ObservableList<ImportBill> bills = FXCollections.observableArrayList();

        for (ImportBill bill: importBills){
            if (bill.getTime().isAfter(fromDate) && bill.getTime().isBefore(toDate)){
                bills.add(bill);
            }
        }
        return FXCollections.unmodifiableObservableList(bills);
    }

    public ObservableList<StaffBill> getStaffBills(LocalDate fromDate, LocalDate toDate)
            throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<StaffBill> staffBills = adapter.getAllStaffBills();
        ObservableList<StaffBill> bills = FXCollections.observableArrayList();

        for (StaffBill bill: staffBills){
            if (bill.getTime().isAfter(fromDate) && bill.getTime().isBefore(toDate)){
                bills.add(bill);
            }
        }
        return FXCollections.unmodifiableObservableList(bills);
    }

    public ObservableList<OtherBill> getOtherBills(LocalDate fromDate, LocalDate toDate)
            throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> otherBills = adapter.getAllOtherBills();
        ObservableList<OtherBill> bills = FXCollections.observableArrayList();

        for (OtherBill bill: otherBills){
            if (bill.getTime().isAfter(fromDate) && bill.getTime().isBefore(toDate)){
                bills.add(bill);
            }
        }
        return FXCollections.unmodifiableObservableList(bills);
    }

    public ObservableList<SaleBill> getSaleBills(LocalDate fromDate, LocalDate toDate)
            throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<SaleBill> saleBills = adapter.getAllSaleBills();
        ObservableList<SaleBill> bills = FXCollections.observableArrayList();

        for (SaleBill bill: saleBills){
            if (bill.getTime().isAfter(fromDate) && bill.getTime().isBefore(toDate)){
                bills.add(bill);
            }
        }
        return FXCollections.unmodifiableObservableList(bills);
    }

    public ObservableList<Bill> getBills(LocalDate fromDate, LocalDate toDate) throws Exception{
        ObservableList<Bill> bills = FXCollections.observableArrayList();

        ObservableList<SaleBill> saleBills = getSaleBills(fromDate, toDate);
        ObservableList<OtherBill> otherBills = getOtherBills(fromDate, toDate);
        ObservableList<StaffBill> staffBills = getStaffBills(fromDate, toDate);
        ObservableList<ImportBill> importBills = getImportBills(fromDate, toDate);

        bills.addAll(saleBills);
        bills.addAll(staffBills);
        bills.addAll(otherBills);
        bills.addAll(importBills);

        return FXCollections.unmodifiableObservableList(bills);
    }

    public void add(ImportBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertImportBill(bill);
    }

    public void add(OtherBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertOtherBill(bill);
    }

    public void add(StaffBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertStaffBill(bill);
    }

    public void add(SaleBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertSaleBill(bill);
    }

    public void remove(ImportBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteImportBill(bill);
    }

    public void remove(OtherBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteOtherBill(bill);
    }

    public void remove(StaffBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteStaffBill(bill);
    }

    public void remove(SaleBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteSaleBill(bill);
    }

    public void update(SaleBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateSaleBill(bill);
    }

    public void update(StaffBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateStaffBill(bill);
    }

    public void update(OtherBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateOtherBill(bill);
    }

    public void update(ImportBill bill) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateImportBill(bill);
    }

    public SaleBill findSaleBillById(int billId) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<SaleBill> saleBills = adapter.getAllSaleBills();
        for (SaleBill bill: saleBills){
            if (bill.getBillId() == billId) return bill;
        }

        return null;
    }

    public StaffBill findStaffBillById(int billId) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<StaffBill> staffBills = adapter.getAllStaffBills();
        for (StaffBill bill: staffBills){
            if (bill.getBillId() == billId) return bill;
        }

        return null;
    }

    public OtherBill findOtherBillById(int billId) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> otherBills = adapter.getAllOtherBills();
        for (OtherBill bill: otherBills){
            if (bill.getBillId() == billId) return bill;
        }

        return null;
    }

    public ImportBill findImportBillById(int billId) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<ImportBill> importBills = adapter.getAllImportBills();
        for (ImportBill bill: importBills){
            if (bill.getBillId() == billId) return bill;
        }

        return null;
    }

    public ArrayList<Bill> findByTitle(String s) throws Exception{
        ArrayList<Bill> bills = new ArrayList<>();
        var adapter = DatabaseAdapter.getDbAdapter();

        ObservableList<SaleBill> saleBills = adapter.getAllSaleBills();
        ObservableList<OtherBill> otherBills = adapter.getAllOtherBills();
        ObservableList<StaffBill> staffBills = adapter.getAllStaffBills();
        ObservableList<ImportBill> importBills = adapter.getAllImportBills();

        for (SaleBill bill: saleBills){
            if (bill.getName().contains(s)) bills.add(bill);
        }

        for (ImportBill bill: importBills){
            if (bill.getName().contains(s)) bills.add(bill);
        }

        for (StaffBill bill: staffBills){
            if (bill.getName().contains(s)) bills.add(bill);
        }

        for (OtherBill bill: otherBills){
            if (bill.getName().contains(s)) bills.add(bill);
        }

        return bills;
    }

}


