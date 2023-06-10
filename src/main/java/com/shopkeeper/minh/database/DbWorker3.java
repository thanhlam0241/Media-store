package com.shopkeeper.minh.database;

import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.minh.models.*;
import com.shopkeeper.linh.models.*;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DbWorker3 {
    private Connection conn;

    public DbWorker3(Connection conn){
        this.conn = conn;
    }

    public void initializeTables() throws Exception{
        if(createOtherBillsTable()) throw new Exception("DatabaseWorker3 created OtherBills tables false");
        if(createImportBillsTable()) throw new Exception("DatabaseWorker3 created ImportBills tables false");
        if(createStaffBillsTable()) throw new Exception("DatabaseWorker3 created StaffBills tables false");
        if (createAttendancesTable()) throw new Exception("DatabaseWorker3 created Attendances tables false");
        if (createShiftsTable()) throw new Exception("DatabaseWorker3 created Shifts tables false");
    }

    public void load1(DbAdapterCache cache) throws Exception{
        loadOtherBills(cache);
        loadImportBills(cache);
    }

    public void load2(DbAdapterCache cache) throws Exception{
        loadStaffBills(cache);
        loadAttendances(cache);
        loadShifts(cache);
    }

    public boolean createOtherBillsTable(){
        String sql = "CREATE TABLE otherbills ("
                +  "billId      INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "price       DOUBLE    NOT NULL,"
                +  "time        DATETIME  NOT NULL,"
                +  "effected    BOOLEAN   NOT NULL,"
                +  "note        TEXT      NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
            stmt.execute("CREATE UNIQUE INDEX idx_otherbills_billId ON otherbills(billId);");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void loadOtherBills(DbAdapterCache cache) throws Exception{
        String sql = "SELECT billId, name, price, time, effected, note FROM otherbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        OtherBill bill;

        while (rs.next()) {
            bill = new OtherBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));
            cache.getOtherBills().add(bill);
        }

        rs.close();
        stmt.close();
    }

    public boolean insertOtherBill(OtherBill bill) {
        if(bill.getBillId() != 0) return false;
        String sql = "INSERT INTO otherbills(name, price, time, effected, note) VALUES(?,?,DATE(?),?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating otherbill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating otherbill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateOtherBill(OtherBill bill) {
        String sql = "UPDATE otherbills SET name=?, price=?, time=DATE(?), effected=?, note=? WHERE billId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            pstmt.setInt(6, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("OtherBill (ID = " + bill.getBillId() + ") does not exist in \"otherbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOtherBill(OtherBill bill) {
        String sql = "DELETE FROM otherbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("OtherBill (ID = " + bill.getBillId() + ") does not exist in \"otherbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createImportBillsTable(){
        String sql = "CREATE TABLE importbills ("
                +  "billId      INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "distributor TEXT      NOT NULL,"
                +  "price       DOUBLE    NOT NULL,"
                +  "time        DATETIME  NOT NULL,"
                +  "effected    BOOLEAN   NOT NULL,"
                +  "note        TEXT      NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
            stmt.execute("CREATE UNIQUE INDEX idx_importbills_billId ON importbills(billId);");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void loadImportBills(DbAdapterCache cache) throws Exception{
        String sql = "SELECT billId, name, distributor, price, time, effected, note FROM importbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        ImportBill bill;

        while (rs.next()) {
            bill = new ImportBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setDistributor(rs.getString("distributor"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));
            cache.getImportBills().add(bill);
        }

        rs.close();
        stmt.close();
    }

    public boolean insertImportBill(ImportBill bill) {
        if(bill.getBillId() != 0) return false;
        String sql = "INSERT INTO importbills(name, distributor, price, time, effected, note) VALUES(?,?,?,DATE(?),?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setString(2, bill.getDistributor());
            pstmt.setDouble(3, bill.getPrice());
            pstmt.setString(4, bill.getTime().toString());
            pstmt.setBoolean(5, bill.getIsEffected());
            pstmt.setString(6, bill.getNote());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating importbill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating importbill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateImportBill(ImportBill bill) {
        String sql = "UPDATE importbills SET name=?, distributor=?, price=?, time=DATE(?), effected=?, note=? WHERE billId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getName());
            pstmt.setString(2, bill.getDistributor());
            pstmt.setDouble(3, bill.getPrice());
            pstmt.setString(4, bill.getTime().toString());
            pstmt.setBoolean(5, bill.getIsEffected());
            pstmt.setString(6, bill.getNote());
            pstmt.setInt(7, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("ImportBill (ID = " + bill.getBillId() + ") does not exist in \"importbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteImportBill(ImportBill bill) {
        String sql = "DELETE FROM importbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("ImportBill (ID = " + bill.getBillId() + ") does not exist in \"importbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createStaffBillsTable(){
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

    public void loadStaffBills(DbAdapterCache cache) throws Exception{
        String sql = "SELECT billId, name, price, time, effected, note, from_d, staffId, standardSalaryPerHour, workHours FROM staffbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        StaffBill bill;
        long staffId;
        ObservableList<Staff> staffs = cache.getStaffs();

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
            cache.getStaffBills().add(bill);
        }

        rs.close();
        stmt.close();
    }

    public boolean insertStaffBill(StaffBill bill) {
        if(bill.getBillId() != 0) return false;
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
        return true;
    }

    public boolean updateStaffBill(StaffBill bill, Staff[] staffs) {
        String sql = "UPDATE staffbills SET name=?, price=?, time=DATE(?), effected=?, note=?, from_d=DATE(?), staffId=?, standardSalaryPerHour=?, workHours=? WHERE billId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT staffId FROM staffbills WHERE billId=" + bill.getBillId());
            Staff old = null;

            if (rs.next()){
                int billId = rs.getInt(1);
                for (var x: staffs){
                    if (x.getStaffId() == billId){
                        old = x;
                        break;
                    }
                }
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
            if (old != null) old.decreaseTimesToBeReferenced();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStaffBill(StaffBill bill) {
        String sql = "DELETE FROM staffbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("StaffBill (ID = " + bill.getBillId() + ") does not exist in \"staffbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createAttendancesTable(){
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

    public void loadAttendances(DbAdapterCache cache) throws Exception{
        String sql = "SELECT time, duration, staffsWork, staffsAbsentee FROM attendances;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Attendance attendance;
        ArrayList<Staff> staffsWork;
        ArrayList<Staff> staffAbsentee;
        String[] staffsWorkId;
        String[] staffsAbsenteeId;
        long staffId;
        ObservableList<Staff> staffs = cache.getStaffs();

        while (rs.next()) {
            attendance = new Attendance();
            staffsWork = new ArrayList<Staff>();
            staffAbsentee = new ArrayList<Staff>();
            attendance.setTime(LocalDateTime.parse(rs.getString("time")));
            attendance.setDuration(Duration.parse(rs.getString("duration")));
            staffsWorkId = rs.getString("staffsWork").split("_");
            staffsAbsenteeId = rs.getString("staffsAbsentee").split("_");

            for (String staffIdString: staffsWorkId){
                boolean check = false;
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
            cache.getAttendances().add(attendance);
        }

        rs.close();
        stmt.close();
    }

    public boolean insertAttendance(Attendance attendance) {

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
        return true;
    }

    public boolean updateAttendance(Attendance attendance, Staff[] staffs) {
        String sql = "UPDATE attendances SET duration=?, staffsWork=?, staffsAbsentee=? WHERE time=DATETIME(?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {
            Staff old = null;
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
        return true;
    }

    public boolean deleteAttendance(Attendance attendance) {
        String sql = "DELETE FROM attendances WHERE time=DATETIME(?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getTime().toString());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Attendance (ID = " + attendance.getTime().toString() + ") does not exist in \"attendances\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createShiftsTable(){
        String sql = "CREATE TABLE shifts ("
                +  "startTime       DATETIME NOT NULL,"
                +  "endTime         DATETIME NOT NULL,"
                +  "staffs          TEXT     NOT NULL,"
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

    public void loadShifts(DbAdapterCache cache) throws Exception{
        String sql = "SELECT  startTime, endTime, staffs, dateOfWeek FROM shifts;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Shift shift;
        ArrayList<Staff> staffs;
        String[] staffsId;
        long staffId;
        ObservableList<Staff> staffsList = cache.getStaffs();

        while (rs.next()) {
            shift = new Shift();
            staffs = new ArrayList<Staff>();
            shift.setStartTime(LocalTime.parse(rs.getString("startTime")));
            shift.setEndTime(LocalTime.parse(rs.getString("endTime")));
            shift.setDateOfWeek(rs.getInt("dateOfWeek"));
            staffsId = rs.getString("staffs").split("_");


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
            cache.getShifts().add(shift);
        }

        rs.close();
        stmt.close();
    }

    public boolean insertShift(Shift shift) {

        String sql = "INSERT INTO shifts(startTime, endTime, staffs, dateOfWeek) VALUES(TIME(?),TIME(?),?,?);";

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
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateShift(Shift shift) {
        String sql = "UPDATE shifts SET staffs=?, dateOfWeek=? WHERE startTime=TIME(?), endTime=TIME(?); ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String staffsId = "";
            pstmt.setString(3, shift.getStartTime().toString());
            pstmt.setString(4, shift.getEndTime().toString());
            pstmt.setInt(2, shift.getDateOfWeek());

            for (Staff staff: shift.getStaffs()){
                staffsId += String.valueOf(staff.getStaffId()) + "_";
            }

            pstmt.setString(1, staffsId);

            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Shift (ID = " + shift.getStartTime().toString() + "-" + shift.getEndTime().toString() + ") does not exist in \"shifts\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteShift(Shift shift) {
        String sql = "DELETE FROM shifts WHERE startTime=TIME(?), endTime=TIME(?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shift.getStartTime().toString());
            pstmt.setString(2, shift.getEndTime().toString());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Shift (ID = " + shift.getStartTime().toString() + "-" + shift.getEndTime().toString() + ") does not exist in \"shifts\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

}
