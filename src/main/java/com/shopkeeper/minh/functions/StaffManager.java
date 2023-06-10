package com.shopkeeper.minh.functions;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.models.StaffState;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.models.Attendance;
import com.shopkeeper.minh.models.Shift;
import com.shopkeeper.minh.models.StaffBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StaffManager {
    private static StaffManager manager;

    private StaffManager(){}

    public static StaffManager getManager(){
        if (manager == null){
            manager = new StaffManager();
        }
        return manager;
    }

    public ObservableList<Staff> getAll() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        return FXCollections.unmodifiableObservableList(adapter.getAllStaffs());
    }

    public void add(Staff staff) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertStaff(staff);
    }

    public void remove(Staff staff) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteStaff(staff);
    }

    public void update(Staff staff) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateStaff(staff);
    }

    public Staff findById(long staffId) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Staff> staffs = adapter.getAllStaffs();

        for (Staff staff: staffs){
            if (staff.getStaffId() == staffId) return staff;
        }

        System.out.println("No staff has that Id");
        return null;
    }

    public ArrayList<Staff> findByName(String s, ArrayList<Staff> listOfStaffs) throws Exception{

        ArrayList<Staff> staffs = new ArrayList<>();

        for (Staff staff: listOfStaffs)
            if (staff.getName().contains(s)) staffs.add(staff);

        return staffs;
    }

    public ArrayList<Staff> findByEmail(String s, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getEmail().contains(s)) staffs.add(staff);
        return staffs;
    }

    public ArrayList<Staff> findByPhoneNumber(String s, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getPhoneNumber().contains(s)) staffs.add(staff);
        return staffs;
    }

    public ArrayList<Staff> findByDescription(String s, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getDescription().contains(s)) staffs.add(staff);
        return staffs;
    }

    public ArrayList<Staff> findBySex(Boolean isMale, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getIsMale() == isMale) staffs.add(staff);
        return staffs;
    }

    public ArrayList<Staff> findByBirthday(LocalDate birthday, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getDateOfBirth().isEqual(birthday)) staffs.add(staff);
        return staffs;
    }

    public ArrayList<Staff> findByState(StaffState state, ArrayList<Staff> listOfStaffs){
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Staff staff: listOfStaffs)
            if (staff.getState() == state) staffs.add(staff);
        return staffs;
    }

    public ObservableList<Shift> getShiftList() throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        return FXCollections.unmodifiableObservableList(adapter.getAllShifts());
    }

    public void setUpShiftList(Shift[] shifts) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Shift> shiftObservableList = adapter.getAllShifts();

        for (Shift shift: shiftObservableList) adapter.deleteShift(shift);
        for (Shift shift: shifts) adapter.insertShift(shift);
    }

    public void changeStaffsAtShift(LocalTime startTime, LocalTime endTime, int dateOfWeek, Staff[] staffs) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Shift> shiftObservableList = adapter.getAllShifts();
        ArrayList<Staff> staffArrayList = new ArrayList<>(Arrays.asList(staffs));
        Shift newShift = new Shift(staffArrayList, dateOfWeek, startTime, endTime);


        for (Shift shift: shiftObservableList){
            if (shift.getDateOfWeek() == dateOfWeek && shift.getStartTime().equals(startTime) && shift.getEndTime().equals(endTime)){
                adapter.deleteShift(shift);
                break;
            }
        }
        adapter.insertShift(newShift);
    }

    public Shift getCurrentShift(LocalDateTime now) throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        Shift currentShift = null;
        ObservableList<Shift> shifts = adapter.getAllShifts();
        for (Shift shift: shifts) {
            if (shift.getDateOfWeek() == now.getDayOfWeek().getValue() && now.toLocalTime().isAfter(shift.getStartTime()) && now.toLocalTime().isBefore(shift.getEndTime())) {
                currentShift = shift;
                break;
            }
        }
        return currentShift;
    }

    public Attendance punchIn(ArrayList<Staff> staffs) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        Shift currentShift = null;
        LocalDateTime now = LocalDateTime.now();
        Attendance attendance;
        ArrayList<Staff> workingStaffs = new ArrayList<>();
        ArrayList<Staff> absenteeStaffs = new ArrayList<>();
        ObservableList<Shift> shifts = adapter.getAllShifts();
        Duration duration;

        ObservableList<Attendance> attendances = adapter.getAllAttendances();

        for (Shift shift: shifts) {
            if (shift.getDateOfWeek() == now.getDayOfWeek().getValue() && now.toLocalTime().isAfter(shift.getStartTime()) && now.toLocalTime().isBefore(shift.getEndTime())) {
                currentShift = shift;
                break;
            }
        }

        if (currentShift == null) return null;

        for (Attendance attendance1: attendances){
            if (attendance1.getTime().getDayOfWeek().getValue() == currentShift.getDateOfWeek() && attendance1.getTime().toLocalTime().isAfter(currentShift.getStartTime())
            && attendance1.getTime().toLocalTime().isBefore(currentShift.getEndTime()) && attendance1.getTime().toLocalDate().isEqual(now.toLocalDate())){
                return null;
            }
        }


        for (Staff staff: staffs){
            if (staff.getState() == StaffState.Working && currentShift.getStaffs().contains(staff)) workingStaffs.add(staff);
        }


        duration = Duration.between(currentShift.getStartTime(), currentShift.getEndTime());

        for (Staff staff: currentShift.getStaffs()){
            if (!staffs.contains(staff)) absenteeStaffs.add(staff);
        }

        attendance = new Attendance(now, duration, workingStaffs, absenteeStaffs);
        adapter.insertAttendance(attendance);

        return attendance;
    }

    public Shift findShiftById(int shiftId) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Shift> shifts = adapter.getAllShifts();

        for (Shift shift: shifts){
            if (shift.getShiftId() == shiftId) return shift;
        }

        System.out.println("No shift has that Id");
        return null;
    }

    public ArrayList<Shift> findByStartTime(LocalTime startTime, ArrayList<Shift> shiftArrayList){
        ArrayList<Shift> shifts = new ArrayList<>();
        for (Shift shift: shiftArrayList)
            if (shift.getStartTime().equals(startTime) || shift.getStartTime().isAfter(startTime)) shifts.add(shift);
        return shifts;
    }

    public ArrayList<Shift> findByEndTime(LocalTime endTime, ArrayList<Shift> shiftArrayList){
        ArrayList<Shift> shifts = new ArrayList<>();
        for (Shift shift: shiftArrayList)
            if (shift.getStartTime().equals(endTime) || shift.getStartTime().isBefore(endTime)) shifts.add(shift);
        return shifts;
    }

    public ArrayList<Shift> findByDateOfWeek(int dateOfWeek, ArrayList<Shift> shiftArrayList){
        ArrayList<Shift> shifts = new ArrayList<>();
        for (Shift shift: shiftArrayList)
            if (shift.getDateOfWeek() == dateOfWeek) shifts.add(shift);
        return shifts;
    }

    public ArrayList<Shift> findByStaffs(ArrayList<Staff> staffs, ArrayList<Shift> shiftArrayList){
        ArrayList<Shift> shifts = new ArrayList<>();
        for (Shift shift: shiftArrayList)
            if (shift.getStaffs().containsAll(staffs)) shifts.add(shift);
        return shifts;
    }

    public ObservableList<StaffBill> getStaffBillList() throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        return FXCollections.unmodifiableObservableList(adapter.getAllStaffBills());
    }

    public StaffBill findBillById(int billId) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<StaffBill> staffBills = adapter.getAllStaffBills();

        for (StaffBill staffBill: staffBills){
            if (staffBill.getBillId() == billId) return staffBill;
        }

        System.out.println("No bill has that Id");
        return null;
    }

    public ArrayList<StaffBill> findBillByStaff(ArrayList<Staff> staffArrayList, ArrayList<StaffBill> staffBillArrayList){
        ArrayList<StaffBill> staffBills = new ArrayList<>();
        for (StaffBill staffBill: staffBillArrayList)
            for (Staff staff: staffArrayList)
                if (staffBill.getStaff().getStaffId() == staff.getStaffId())
                    staffBills.add(staffBill);
        return staffBills;
    }

    public ArrayList<StaffBill> findBillBySalary(double salary, ArrayList<StaffBill> staffBillArrayList){
        ArrayList<StaffBill> staffBills = new ArrayList<>();
        for (StaffBill staffBill: staffBillArrayList)
            if (staffBill.getStandardSalaryPerHour() == salary)
                staffBills.add(staffBill);
        return staffBills;
    }

    public LocalDate getFrom(Staff staff) throws Exception{
        if (staff.getLatestPay().isAfter(LocalDate.of(2000, 1, 1))) return staff.getLatestPay();
        LocalDate from = LocalDate.now();
        var adapter = DatabaseAdapter.getDbAdapter();
        LocalDate latestPay = null;

        ObservableList<StaffBill> staffBills = adapter.getAllStaffBills();
        ObservableList<Attendance> attendances = adapter.getAllAttendances();

        for (StaffBill staffBill: staffBills){
            if (staff.getStaffId() == staffBill.getStaff().getStaffId()){
                if (latestPay == null) latestPay = staffBill.getTime();
                else
                {
                    if (latestPay.isBefore(staffBill.getTime()))
                    latestPay = staffBill.getTime();
                }
            }
        }


        if (latestPay != null) return latestPay;

        for (Attendance attendance: attendances){
            if (attendance.getStaffsWork().contains(staff)){
                if (latestPay == null) {
                    from = attendance.getTime().toLocalDate();
                    latestPay = LocalDate.now();
                }
                else if (from.isAfter(attendance.getTime().toLocalDate())) from = attendance.getTime().toLocalDate();
            }
        }

        return from;
    }

    public double getWorkHours(Staff staff) throws Exception{
        double workHours = 0;
        LocalDate from = getFrom(staff);
        var adapter = DatabaseAdapter.getDbAdapter();

        ObservableList<Attendance> attendances = adapter.getAllAttendances();

        for (Attendance attendance: attendances){
            if (attendance.getStaffsWork().contains(staff)){
                if (attendance.getTime().toLocalDate().isEqual(from) || attendance.getTime().toLocalDate().isAfter(from)) {
                    workHours += attendance.getDuration().toMinutes() / 60;
                }
            }
        }

        return workHours;
    }

    public Attendance getAttendace(LocalDateTime now, ArrayList<Staff> staffs) throws Exception{

        var adapter = DatabaseAdapter.getDbAdapter();
        Shift currentShift = null;
        Attendance attendance;
        ArrayList<Staff> workingStaffs = new ArrayList<>();
        ArrayList<Staff> absenteeStaffs = new ArrayList<>();
        ObservableList<Shift> shifts = adapter.getAllShifts();
        Duration duration;

        ObservableList<Attendance> attendances = adapter.getAllAttendances();

        for (Shift shift: shifts) {
            if (shift.getDateOfWeek() == now.getDayOfWeek().getValue() && now.toLocalTime().isAfter(shift.getStartTime()) && now.toLocalTime().isBefore(shift.getEndTime())) {
                currentShift = shift;
                break;
            }
        }

        if (currentShift == null) return null;

        for (Attendance attendance1: attendances){
            if (attendance1.getTime().getDayOfWeek().getValue() == currentShift.getDateOfWeek() && attendance1.getTime().toLocalTime().isAfter(currentShift.getStartTime())
                    && attendance1.getTime().toLocalTime().isBefore(currentShift.getEndTime()) && attendance1.getTime().toLocalDate().isEqual(now.toLocalDate())){
                return null;
            }
        }


        for (Staff staff: staffs){
            if (staff.getState() == StaffState.Working && currentShift.getStaffs().contains(staff)) workingStaffs.add(staff);
        }


        duration = Duration.between(currentShift.getStartTime(), currentShift.getEndTime());

        for (Staff staff: currentShift.getStaffs()){
            if (!staffs.contains(staff)) absenteeStaffs.add(staff);
        }

        attendance = new Attendance(now, duration, workingStaffs, absenteeStaffs);
        adapter.insertAttendance(attendance);

        return attendance;
    }


}
