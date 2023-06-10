package com.shopkeeper.linh.database;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class SaleBillDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<SaleBill> list;
    public SaleBillDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<SaleBill> saleBills){
        this.conn = conn;
        this.list = saleBills;
        this.readOnlyCache = cache;
    }
    //Return true if success, otherwise return false
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE salebills (");
        sqlBuilder.append("billId  INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("name          TEXT      NOT NULL,");
        sqlBuilder.append("location      TEXT      NOT NULL,");
        sqlBuilder.append("customerId    INTEGER   NOT NULL,");
        sqlBuilder.append("isPaid        BOOLEAN   NOT NULL,");
        sqlBuilder.append("price         DOUBLE    NOT NULL,");
        sqlBuilder.append("time          DATETIME  NOT NULL,");
        sqlBuilder.append("effected      BOOLEAN   NOT NULL,");
        sqlBuilder.append("note          TEXT      NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_salebills_billId ON salebills(billId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT billId, name, customerId, location ,isPaid, price, time, effected, note FROM salebills";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        SaleBill bill;
        long customerId;
        var customers = readOnlyCache.getCustomers();
        while (rs.next()) {
            bill = new SaleBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setLocation(rs.getString("location"));
            customerId = rs.getLong("customerId");
            for(int i = 0; i < customers.size(); i++){
                var customer = customers.get(i);
                if(customer.getCustomerId() == customerId){
                    bill.setCustomer(customer);
                    customer.increaseTimesToBeReferenced();
                    break;
                }
            }
            if(bill.getCustomer() == null){
                throw new Exception("Be careful when delete a customer, the action can affect to a salebill whose customer is deleted.");
            }
            bill.setIsPaid(rs.getBoolean("isPaid"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));
            list.add(bill);
        }

        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(SaleBill bill) {
        if(bill.getBillId() != 0) return false;
        if(!readOnlyCache.getCustomers().contains(bill.getCustomer())){
            System.err.println("Customer which is output of bill.getCustomer() is not in DbAdapter's cache");
            return false;
        }
        String sql = "INSERT INTO salebills(name, customerId, location ,isPaid, price, time, effected, note) VALUES(?,?,?,?,?,DATE(?),?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setLong(2, bill.getCustomer().getCustomerId());
            pstmt.setString(3, bill.getLocation());
            pstmt.setBoolean(4, bill.getIsPaid());
            pstmt.setDouble(5, bill.getPrice());
            pstmt.setString(6, bill.getTime().toString());
            pstmt.setBoolean(7, bill.getIsEffected());
            pstmt.setString(8, bill.getNote());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating salebill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating salebill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        bill.getCustomer().increaseTimesToBeReferenced();
        list.add(bill);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(SaleBill bill) {
        if(!readOnlyCache.getCustomers().contains(bill.getCustomer())){
            System.err.println("Customer which is output of bill.getCustomer() is not in DbAdapter's cache");
            return false;
        }
        if(!list.contains(bill)){
            System.err.println("bill:SaleBill is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE salebills SET name=?, customerId=?, location=?, isPaid=?, price=?, time=DATE(?), effected=?, note=? WHERE billId=?";
        int oldCustomerId = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT customerId FROM salebills WHERE billId=" + bill.getBillId());
            if(rs.next()){
                oldCustomerId = rs.getInt(1);
            }
            pstmt.setString(1, bill.getName());
            pstmt.setLong(2, bill.getCustomer().getCustomerId());
            pstmt.setString(3, bill.getLocation());
            pstmt.setBoolean(4, bill.getIsPaid());
            pstmt.setDouble(5, bill.getPrice());
            pstmt.setString(6, bill.getTime().toString());
            pstmt.setBoolean(7, bill.getIsEffected());
            pstmt.setString(8, bill.getNote());
            pstmt.setInt(9, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("SaleBill (ID = " + bill.getBillId() + ") does not exist in \"salebills\" table.");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        bill.getCustomer().increaseTimesToBeReferenced();
        for (var x: readOnlyCache.getCustomers()) {
            if(x.getCustomerId() == oldCustomerId){
                try {
                    x.decreaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            }
        }
        return true;
    }
    //Return true if success, otherwise return false
    public boolean delete(SaleBill bill) {
        if(!readOnlyCache.getCustomers().contains(bill.getCustomer())){
            System.err.println("Customer which is output of bill.getCustomer() is not in DbAdapter's cache");
            return false;
        }
        int index = list.indexOf(bill);
        if(index < 0){
            System.err.println("bill:SaleBill is not in DbAdapter's cache");
            return false;
        }


        String sql = "DELETE FROM salebills WHERE billId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("SaleBill (ID = " + bill.getBillId() + ") does not exist in \"salebills\" table.");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        try {
            bill.getCustomer().decreaseTimesToBeReferenced();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        list.remove(index, index + 1);
        return true;
    }
}
