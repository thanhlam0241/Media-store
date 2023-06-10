package com.shopkeeper.minh.database;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import com.shopkeeper.minh.models.Attendance;
import com.shopkeeper.minh.models.StaffBill;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AttendanceDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Attendance> list;

    public AttendanceDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Attendance> attendances){
        this.conn = conn;
        this.list = attendances;
        this.readOnlyCache = cache;
    }

    public boolean createTable(){
        String sql = "CREATE TABLE attendances ("
                +  "time            DATETIME  PRIMARY KEY,"
                +  "duration        TEXT      NOT NULL,"
                +  "staffsWork      TEXT      NOT NULL,"
                +  "staffsAbsentee  TEXT      NOT NULL"
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
        String sql = "SELECT time, duration, staffsWork, staffsAbsentee FROM attendances;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        Attendance attendance;
        ArrayList<Staff> staffsWork;
        ArrayList<Staff> staffAbsentee;
        String[] staffsWorkId;
        String[] staffsAbsenteeId;
        long staffId;
        ObservableList<Staff> staffs = readOnlyCache.getStaffs();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next()) {
            attendance = new Attendance();
            staffsWork = new ArrayList<Staff>();
            staffAbsentee = new ArrayList<Staff>();
            attendance.setTime(LocalDateTime.parse(rs.getString("time"), formatter));
            attendance.setDuration(Duration.parse(rs.getString("duration")));
            staffsWorkId = rs.getString("staffsWork").split("_");
            staffsAbsenteeId = rs.getString("staffsAbsentee").split("_");

            for (String staffIdString: staffsWorkId){
                boolean check = false;
                if (staffIdString == null || staffIdString.isEmpty()) break;
                staffId = Long.parseLong(staffIdString);
                for (Staff staff: staffs){
                    if (staff.getStaffId() == staffId){
                        check = true;
                        staffsWork.add(staff);
                        staff.increaseTimesToBeReferenced();
                        break;
                    }
                }

                if (!check){
                    throw new Exception("Be careful when delete a staff, the action can affect to a attendance whose staff is deleted.");
                }
            }

            for (String staffIdString: staffsAbsenteeId){
                boolean check = false;
                if (staffIdString == null || staffIdString.isEmpty()) break;
                staffId = Long.parseLong(staffIdString);
                for (Staff staff: staffs){
                    if (staff.getStaffId() == staffId){
                        check = true;
                        staffAbsentee.add(staff);
                        staff.increaseTimesToBeReferenced();
                        break;
                    }
                }

                if (!check){
                    throw new Exception("Be careful when delete a staff, the action can affect to a attendance whose staff is deleted.");
                }
            }

            attendance.setStaffsWork(staffsWork);
            attendance.setStaffsAbsentee(staffAbsentee);
            list.add(attendance);
        }

        rs.close();
        stmt.close();
    }

    public boolean insert(Attendance attendance) {

        if (attendance.getTime().equals(LocalDateTime.of(1000, 1, 1, 1, 1, 1))){
            System.err.println("Set the attendance's time first before inserting.");
            return false;
        }

        for (Attendance x: readOnlyCache.getAttendances()){
            if (x.getTime().isEqual(attendance.getTime())){
                return false;
            }
        }

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsWork())){
            System.err.println("One Staff in which output of attendance.getStaffsWork() is not in DbAdapter's cache");
            return false;
        }

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsAbsentee())){
            System.err.println("One Staff in which output of attendance.getStaffsAbsentee() is not in DbAdapter's cache");
            return false;
        }

        for (Staff staffWork: attendance.getStaffsWork()){
            for (Staff staffAbsentee: attendance.getStaffsAbsentee()){
                if (staffAbsentee == staffWork){
                    System.err.println("At least one Staff lies in both staffsWork and staffsAbsentee");
                    return false;
                }
            }
        }

        String sql = "INSERT INTO attendances(time, duration, staffsWork, staffsAbsentee) VALUES(DATETIME(?),?,?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String staffsWorkId = "";
            String staffsAbsenteeId = "";
            pstmt.setString(1, attendance.getTime().toString());
            pstmt.setString(2, attendance.getDuration().toString());

            for (Staff staff: attendance.getStaffsWork()){
                staffsWorkId += String.valueOf(staff.getStaffId()) + "_";
            }

            for (Staff staff: attendance.getStaffsAbsentee()){
                staffsAbsenteeId += String.valueOf(staff.getStaffId()) + "_";
            }

            pstmt.setString(3, staffsWorkId);
            pstmt.setString(4, staffsAbsenteeId);
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating attendance failed, no rows affected.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: attendance.getStaffsWork()) staff.increaseTimesToBeReferenced();
        for (Staff staff: attendance.getStaffsAbsentee()) staff.increaseTimesToBeReferenced();
        list.add(attendance);
        return true;
    }

    public boolean update(Attendance attendance) {

        if (!list.contains(attendance)){
            System.err.println("Attendance is not in DbAdapter's cache");
            return false;
        }

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsWork())){
            System.err.println("One Staff in which output of attendance.getStaffsWork() is not in DbAdapter's cache");
            return false;
        }

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsAbsentee())){
            System.err.println("One Staff in which output of attendance.getStaffsAbsentee() is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE attendances SET duration=?, staffsWork=?, staffsAbsentee=? WHERE time=DATETIME(?);";
        String oldSql = "SELECT staffsWork, staffsAbsentee FROM attendances WHERE time=DATETIME(?);";
        String[] oldStaffsWorkId = {};
        String[] oldStaffsAbsenteeId = {};

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement oldPstmt = conn.prepareStatement(oldSql)) {

            oldPstmt.setString(1, attendance.getTime().toString());
            ResultSet rs = oldPstmt.executeQuery();

            if (rs.next()){
                oldStaffsWorkId = rs.getString(1).split("_");
                oldStaffsAbsenteeId = rs.getString(2).split("_");
            }

            String staffsWorkId = "";
            String staffsAbsenteeId = "";
            pstmt.setString(4, attendance.getTime().toString());
            pstmt.setString(1, attendance.getDuration().toString());

            for (Staff staff: attendance.getStaffsWork()){
                staffsWorkId += String.valueOf(staff.getStaffId()) + "_";
            }

            for (Staff staff: attendance.getStaffsAbsentee()){
                staffsAbsenteeId += String.valueOf(staff.getStaffId()) + "_";
            }

            pstmt.setString(2, staffsWorkId);
            pstmt.setString(3, staffsAbsenteeId);

            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Attendance (ID = " + attendance.getTime().toString() + ") does not exist in \"attendances\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: attendance.getStaffsWork()) staff.increaseTimesToBeReferenced();
        for (Staff staff: attendance.getStaffsAbsentee()) staff.increaseTimesToBeReferenced();

        for (String staffIdString: oldStaffsWorkId){
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

        for (String staffIdString: oldStaffsAbsenteeId){
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

    public boolean delete(Attendance attendance) {

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsWork())){
            System.err.println("One Staff in which output of attendance.getStaffsWork() is not in DbAdapter's cache");
            return false;
        }

        if (!readOnlyCache.getStaffs().containsAll(attendance.getStaffsAbsentee())){
            System.err.println("One Staff in which output of attendance.getStaffsAbsentee() is not in DbAdapter's cache");
            return false;
        }

        int index = list.indexOf(attendance);
        if(index < 0){
            System.err.println("attendance is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM attendances WHERE time=DATETIME(?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getTime().toString());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Attendance (ID = " + attendance.getTime().toString() + ") does not exist in \"attendances\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        for (Staff staff: attendance.getStaffsWork()){
            try{
                staff.decreaseTimesToBeReferenced();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        for (Staff staff: attendance.getStaffsAbsentee()){
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
