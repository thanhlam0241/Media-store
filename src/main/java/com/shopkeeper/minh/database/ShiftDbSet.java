package com.shopkeeper.minh.database;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import com.shopkeeper.minh.models.Shift;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class ShiftDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Shift> list;

    public ShiftDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Shift> shifts){
        this.conn = conn;
        this.list = shifts;
        this.readOnlyCache = cache;
    }

    public boolean createTable(){
        String sql = "CREATE TABLE shifts ("
                +  "shiftId         INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "startTime       DATETIME NOT NULL,"
                +  "endTime         DATETIME NOT NULL,"
                +  "staff           TEXT     NOT NULL,"
                +  "dateOfWeek      INTEGER  NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception{
        String sql = "SELECT shiftId, startTime, endTime, staff, dateOfWeek FROM shifts;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        Shift shift;
        ArrayList<Staff> staffs;
        String[] staffsId;
        long staffId;
        ObservableList<Staff> staffsList = readOnlyCache.getStaffs();

        while (rs.next()) {
            shift = new Shift();
            staffs = new ArrayList<Staff>();
            shift.setShiftId(rs.getInt("shiftId"));
            shift.setStartTime(LocalTime.parse(rs.getString("startTime")));
            shift.setEndTime(LocalTime.parse(rs.getString("endTime")));
            shift.setDateOfWeek(rs.getInt("dateOfWeek"));
            staffsId = rs.getString("staff").split("_");


            for (String staffIdString: staffsId){
                staffId = Long.parseLong(staffIdString);
                boolean check = false;
                for (Staff staff: staffsList){
                    if (staff.getStaffId() == staffId){
                        check = true;
                        staffs.add(staff);
                        staff.increaseTimesToBeReferenced();
                        break;
                    }
                }

                if (!check){
                    throw new Exception("Be careful when delete a staff, the action can affect to a shift whose staff is deleted.");
                }
            }

            shift.setStaffs(staffs);
            list.add(shift);
        }

        rs.close();
        stmt.close();
    }

    public boolean insert(Shift shift) {

        if (shift.getShiftId() != 0) return false;

        if (!readOnlyCache.getStaffs().containsAll(shift.getStaffs())){
            System.err.println("One Staff in which output of shift.getStaffs() is not in DbAdapter's cache");
            return false;
        }

        if (shift.getStartTime() == null){
            System.err.println("Set the shift's startTime first before inserting.");
            return false;
        }

        if (shift.getEndTime() == null){
            System.err.println("Set the shift's endTime first before inserting.");
            return false;
        }

        if (shift.getDateOfWeek() == 0){
            System.err.println("Set the shift's DateOfWeek first before inserting.");
            return false;
        }

        for (Shift x: readOnlyCache.getShifts()){
            if (x.getStartTime().equals(shift.getStartTime()) && x.getEndTime().equals(shift.getEndTime()) && x.getDateOfWeek() == shift.getDateOfWeek()){
                System.err.println("shift has already been in DbAdapter's cache or its time matches another shift");
                return false;
            }
        }

        if (!shift.getEndTime().isAfter(shift.getStartTime())){
            System.err.println("The endTime must be after the startTime");
            return false;
        }


        String sql = "INSERT INTO shifts(startTime, endTime, staff, dateOfWeek) VALUES(TIME(?),TIME(?),?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String staffsId = "";
            pstmt.setString(1, shift.getStartTime().toString());
            pstmt.setString(2, shift.getEndTime().toString());
            pstmt.setInt(4, shift.getDateOfWeek());

            for (Staff staff: shift.getStaffs()){
                staffsId += String.valueOf(staff.getStaffId()) + "_";
            }

            pstmt.setString(3, staffsId);
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating shift failed, no rows affected.");
            // Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) shift.setShiftId(generatedKeys.getInt(1));
            else throw new Exception("Creating shift failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: shift.getStaffs()) staff.increaseTimesToBeReferenced();
        list.add(shift);
        return true;
    }

    public boolean update(Shift shift) {

        if (!list.contains(shift)){
            System.err.println("Shift is not in DbAdapter's cache");
            return false;
        }

        if (!readOnlyCache.getStaffs().containsAll(shift.getStaffs())){
            System.err.println("One Staff in which output of shift.getStaffs() is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE shifts SET staff = ?, dateOfWeek = ? , startTime = TIME(?), endTime = TIME(?) WHERE shiftId = ?;";
        String oldSql = "SELECT staff FROM shifts WHERE shiftId = ?;";

        String[] oldStaffsId = {};

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement oldPstmt = conn.prepareStatement(oldSql)) {

            oldPstmt.setInt(1, shift.getShiftId());
            ResultSet rs = oldPstmt.executeQuery();

            if (rs.next()){
                oldStaffsId = rs.getString(1).split("_");
            }


            String staffsId = "";
            pstmt.setInt(5, shift.getShiftId());
            pstmt.setString(3, shift.getStartTime().toString());
            pstmt.setString(4, shift.getEndTime().toString());
            pstmt.setInt(2, shift.getDateOfWeek());

            for (Staff staff: shift.getStaffs()){
                staffsId += String.valueOf(staff.getStaffId()) + "_";
            }

            pstmt.setString(1, staffsId);

            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Shift (ID = " + shift.getShiftId() + ") does not exist in \"shifts\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: shift.getStaffs()) staff.increaseTimesToBeReferenced();

        for (String staffIdString: oldStaffsId){
            long staffId = Long.parseLong(staffIdString);

            for (Staff staff: readOnlyCache.getStaffs()){
                if (staff.getStaffId() == staffId){
                    try{
                        staff.decreaseTimesToBeReferenced();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }

    public boolean delete(Shift shift) {

        if (!readOnlyCache.getStaffs().containsAll(shift.getStaffs())){
            System.err.println("One Staff in which output of shift.getStaffs() is not in DbAdapter's cache");
            return false;
        }

        int index = list.indexOf(shift);
        if(index < 0){
            System.err.println("shift is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM shifts WHERE shiftId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shift.getShiftId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Shift (ID = " + shift.getShiftId() + ") does not exist in \"shifts\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: shift.getStaffs()){
            try{
                staff.decreaseTimesToBeReferenced();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        list.remove(index, index + 1);
        return true;
    }
}
