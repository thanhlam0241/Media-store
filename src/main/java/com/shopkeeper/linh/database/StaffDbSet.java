package com.shopkeeper.linh.database;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.models.StaffState;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class StaffDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Staff> list;
    public StaffDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Staff> staffs){
        this.conn = conn;
        this.list = staffs;
        this.readOnlyCache = cache;
    }
    //Return true if success, otherwise return false
    public boolean createTable() {
        String sql = "CREATE TABLE staffs ("
                +  "staffId     INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "isMale      BOOLEAN   NOT NULL,"
                +  "dateOfBirth DATETIME  NOT NULL,"
                +  "email       TEXT      NOT NULL,"
                +  "phoneNumber TEXT      NOT NULL,"
                +  "description TEXT      NOT NULL,"
                +  "state       TEXT (40) NOT NULL,"
                +  "latestPay   DATETIME NOT NULL"
                +  ");";
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_staffs_staffId ON staffs(staffId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT staffId, name, isMale, dateOfBirth, email, phoneNumber, description, state, latestPay FROM staffs";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Staff staff;
        while (rs.next()) {
            staff = new Staff();
            staff.setStaffId(rs.getLong("staffId"));
            staff.setName(rs.getString("name"));
            staff.setIsMale(rs.getBoolean("isMale"));
            //;
            staff.setDateOfBirth(LocalDate.parse(rs.getString("dateOfBirth")));
            staff.setEmail(rs.getString("email"));
            staff.setPhoneNumber(rs.getString("phoneNumber"));
            staff.setDescription(rs.getString("description"));
            staff.setState(StaffState.valueOf(rs.getString("state")));
            staff.setLatestPay(LocalDate.parse(rs.getString("latestPay")));
            list.add(staff);
        }

        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(Staff staff) {
        if(staff.getStaffId() != 0) return false;
        String sql = "INSERT INTO staffs(name, isMale, dateOfBirth, email, phoneNumber, description, state, latestPay) VALUES(?,?,DATE(?),?,?,?,?, DATE(?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, staff.getName());
            pstmt.setBoolean(2, staff.getIsMale());
            pstmt.setString(3, staff.getDateOfBirth().toString());
            pstmt.setString(4, staff.getEmail());
            pstmt.setString(5, staff.getPhoneNumber());
            pstmt.setString(6, staff.getDescription());
            pstmt.setString(7, staff.getState().toString());
            pstmt.setString(8, staff.getLatestPay().toString());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating staff failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                staff.setStaffId(generatedKeys.getLong(1));
            }
            else throw new Exception("Creating staff failed, no ID obtained.");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When insert success
        list.add(staff);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Staff staff) {
        if(!list.contains(staff)) {
            System.err.println("staff is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE staffs SET name=?,isMale=?,dateOfBirth=DATE(?),email=?,phoneNumber=?,description=?,state=?, latestPay=DATE(?) WHERE staffId=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, staff.getName());
            pstmt.setBoolean(2, staff.getIsMale());
            pstmt.setString(3, staff.getDateOfBirth().toString());
            pstmt.setString(4, staff.getEmail());
            pstmt.setString(5, staff.getPhoneNumber());
            pstmt.setString(6, staff.getDescription());
            pstmt.setString(7, staff.getState().toString());
            pstmt.setString(8, staff.getLatestPay().toString());
            pstmt.setLong(9, staff.getStaffId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Staff (ID = " + staff.getStaffId() + ") does not exist in \"staffs\" table.");


        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    //Return true if success, otherwise return false
    public boolean delete(Staff staff) {
        if(staff.countTimesToBeReferenced() != 0)  {
            System.err.println("Something have referenced to this staff.");
            return false;
        }
        int index = list.indexOf(staff);
        if(index < 0){
            System.err.println("staff is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM staffs WHERE staffId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, staff.getStaffId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Staff (ID = " + staff.getStaffId() + ") does not exist in \"staffs\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
