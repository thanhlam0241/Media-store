package com.shopkeeper.minh.database;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import com.shopkeeper.minh.models.StaffBill;

import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class StaffBillDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<StaffBill> list;

    public StaffBillDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<StaffBill> staffBills){
        this.conn = conn;
        this.list = staffBills;
        this.readOnlyCache = cache;
    }

    public boolean createTable(){
        String sql = "CREATE TABLE staffbills ("
                +  "billId      INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "price       DOUBLE    NOT NULL,"
                +  "time        DATETIME  NOT NULL,"
                +  "effected    BOOLEAN   NOT NULL,"
                +  "note        TEXT      NOT NULL,"
                +  "from_d      DATETIME  NOT NULL,"
                +  "staffId     INTEGER   NOT NULL,"
                +  "standardSalaryPerHour DOUBLE  NOT NULL,"
                +  "workHours   DOUBLE  NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
            stmt.execute("CREATE UNIQUE INDEX idx_staffbills_billId ON staffbills(billId);");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception{
        String sql = "SELECT billId, name, price, time, effected, note, from_d, staffId, standardSalaryPerHour, workHours FROM staffbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        StaffBill bill;
        long staffId;
        ObservableList<Staff> staffs = readOnlyCache.getStaffs();

        while (rs.next()) {
            bill = new StaffBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));
            bill.setFrom(LocalDate.parse(rs.getString("from_d")));
            bill.setStandardSalaryPerHour(rs.getDouble("standardSalaryPerHour"));
            bill.setWorkHours(rs.getDouble("workHours"));

            staffId = rs.getLong("staffId");
            for (Staff staff: staffs){
                if (staff.getStaffId() == staffId){
                    bill.setStaff(staff);
                    staff.increaseTimesToBeReferenced();
                    break;
                }
            }

            if (bill.getStaff() == null){
                throw new Exception("Be careful when delete a staff, it can affect to a staffbill whose staff is deleted.");
            }
            list.add(bill);
        }

        rs.close();
        stmt.close();
    }


    public boolean insert(StaffBill bill) {
        if(bill.getBillId() != 0) return false;
        if (!readOnlyCache.getStaffs().contains(bill.getStaff())){
            System.err.println("Staff which is output of bill.getStaff() is not in DbAdapter's cache");
            return false;
        }

        String sql = "INSERT INTO staffbills(name, price, time, effected, note, from_d, staffId, standardSalaryPerHour, workHours) VALUES(?,?,DATE(?),?,?, DATE(?), ?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            pstmt.setString(6, bill.getFrom().toString());
            pstmt.setLong(7, bill.getStaff().getStaffId());
            pstmt.setDouble(8, bill.getStandardSalaryPerHour());
            pstmt.setDouble(9, bill.getWorkHours());

            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating staffbill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating staffbill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        bill.getStaff().increaseTimesToBeReferenced();
        list.add(bill);
        return true;
    }

    public boolean update(StaffBill bill) {

        if (!readOnlyCache.getStaffs().contains(bill.getStaff())){
            System.err.println("Staff which is output of bill.getStaff() is not in DbAdapter's cache");
            return false;
        }

        if (!list.contains(bill)){
            System.err.println("bill:StaffBill is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE staffbills SET name=?, price=?, time=DATE(?), effected=?, note=?, from_d=DATE(?), staffId=?, standardSalaryPerHour=?, workHours=? WHERE billId=?;";
        long oldStaffId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT staffId FROM staffbills WHERE billId=" + bill.getBillId());
            if(rs.next()){
                oldStaffId = rs.getLong(1);
            }

            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            pstmt.setString(6, bill.getFrom().toString());
            pstmt.setLong(7, bill.getStaff().getStaffId());
            pstmt.setDouble(8, bill.getStandardSalaryPerHour());
            pstmt.setDouble(9, bill.getWorkHours());
            pstmt.setInt(10, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("StaffBill (ID = " + bill.getBillId() + ") does not exist in \"staffbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        bill.getStaff().increaseTimesToBeReferenced();
        for (Staff old: readOnlyCache.getStaffs()){
            if (old.getStaffId() == oldStaffId){
                try {
                    old.decreaseTimesToBeReferenced();
                } catch (Exception e){
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            }
        }

        return true;
    }

    public boolean delete(StaffBill bill) {
        if (!readOnlyCache.getStaffs().contains(bill.getStaff())){
            System.err.println("Staff which is output of bill.getStaff() is not in DbAdapter's cache");
            return false;
        }
        int index = list.indexOf(bill);
        if(index < 0){
            System.err.println("bill:StaffBill is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM staffbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("StaffBill (ID = " + bill.getBillId() + ") does not exist in \"staffbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        try {
            bill.getStaff().decreaseTimesToBeReferenced();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        list.remove(index, index + 1);
        return true;
    }
}
