package com.shopkeeper.linh.seeders;

import com.shopkeeper.lam.database.*;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.models.OtherBill;

public class DataSeeder {
    public static void SeedData() throws Exception{
        //load 1
        CustomerSeeder.SeedData();
        StaffSeeder.SeedData();

        new testCategory().insertCategory();
        new testPublisher().insertPublisher();
        new testPerson().insertPerson();
        new testImportBill().insert();

        new com.shopkeeper.minh.test().insertOtherBills();
        //load 2
        SaleBillSeeder.SeedData();
        new testBookInfo().insert();
        new testMusicInfo().insert();
        new testFilmInfo().insert();

        new com.shopkeeper.minh.test().insertShift();
        new com.shopkeeper.minh.test().insertAttendances(4);
        new com.shopkeeper.minh.test().insertStaffBills();
        new com.shopkeeper.minh.test().insertAttendances(5);

        //load 3
        new testProduct().insert();
        //load 4
        FeedbackSeeder.SeedData();

        var adapter = DatabaseAdapter.getDbAdapter();
        for(var importBill:adapter.getAllImportBills()){
            importBill.setPrice(0);
        }
        for(var saleBill:adapter.getAllSaleBills()){
            saleBill.setPrice(0);
        }
        for(var product:adapter.getAllProducts()){
            if(product.getImportBill() != null){
                product.getImportBill().setPrice(product.getImportBill().getPrice() + product.getImportCost());
            }
            if(product.getSaleBill() != null){
                product.getSaleBill().setPrice(product.getSaleBill().getPrice() + product.getSaleValue());
            }
        }
        for(var importBill:adapter.getAllImportBills()){
            adapter.updateImportBill(importBill);
        }
        for(var saleBill:adapter.getAllSaleBills()){
            adapter.updateSaleBill(saleBill);
        }
    }
}
