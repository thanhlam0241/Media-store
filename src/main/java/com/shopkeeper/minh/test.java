package com.shopkeeper.minh;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.models.StaffState;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.functions.StaffManager;
import com.shopkeeper.minh.models.*;

import com.shopkeeper.mediaone.database.DatabaseAdapter;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class test {
    public void insertOtherBills() throws Exception {
        //Tạo 5 đối tượng mẫu với đầy đủ thuộc tính (trừ ID)
        OtherBill x1 = new OtherBill("Vận chuyển", 1000, LocalDate.of(2022, 6, 2), true, "Không có ghi chú");
        OtherBill x2 = new OtherBill("Vận chuyển", 2000, LocalDate.of(2022, 6, 3), false, "Không có ghi chú");
        OtherBill x3 = new OtherBill("Chi phí mặt bằng", 3000, LocalDate.of(2022, 6, 4), true, "Không có ghi chú");
        OtherBill x4 = new OtherBill("Chi phí mặt bằng", 4000, LocalDate.of(2022, 6, 5), false, "Không có ghi chú");
        OtherBill x5 = new OtherBill("Chi phí mặt bằng", 5000, LocalDate.of(2022, 6, 6), true, "Không có ghi chú");
        OtherBill x6 = new OtherBill("Vận chuyển", 1000, LocalDate.of(2022, 6, 2), true, "Không có ghi chú");
        OtherBill x7 = new OtherBill("Vận chuyển", 2000, LocalDate.of(2022, 6, 3), false, "Không có ghi chú");
        OtherBill x8 = new OtherBill("Chi phí mặt bằng", 3000, LocalDate.of(2022, 6, 4), true, "Không có ghi chú");
        OtherBill x9 = new OtherBill("Chi phí mặt bằng", 4000, LocalDate.of(2022, 6, 5), false, "Không có ghi chú");
        OtherBill x10 = new OtherBill("Chi phí mặt bằng", 5000, LocalDate.of(2022, 6, 6), true, "Không có ghi chú");
        OtherBill x11 = new OtherBill("Vận chuyển", 1000, LocalDate.of(2022, 6, 2), true, "Không có ghi chú");
        OtherBill x12 = new OtherBill("Vận chuyển", 2000, LocalDate.of(2022, 6, 3), false, "Không có ghi chú");
        OtherBill x13 = new OtherBill("Vận chuyển", 3000, LocalDate.of(2022, 6, 4), true, "Không có ghi chú");
        OtherBill x14 = new OtherBill("Vận chuyển", 4000, LocalDate.of(2022, 6, 5), false, "Không có ghi chú");
        OtherBill x15 = new OtherBill("Chi phí mặt bằng", 5000, LocalDate.of(2022, 6, 6), true, "Không có ghi chú");

        var adapter = DatabaseAdapter.getDbAdapter();
        for (var x : adapter.getAllOtherBills()) {
            //Nếu như các ô đã triển khai override thuộc tính toString() cho
            // của ô rồi thì viết thế này
            System.out.println(x);
            //Còn nếu không các ô phải in từng thuộc tính 1 ra
        }
        adapter.insertOtherBill(x1);
        adapter.insertOtherBill(x2);
        adapter.insertOtherBill(x3);
        adapter.insertOtherBill(x4);
        adapter.insertOtherBill(x5);
        adapter.insertOtherBill(x6);
        adapter.insertOtherBill(x7);
        adapter.insertOtherBill(x8);
        adapter.insertOtherBill(x9);
        adapter.insertOtherBill(x10);
        adapter.insertOtherBill(x11);
        adapter.insertOtherBill(x12);
        adapter.insertOtherBill(x13);
        adapter.insertOtherBill(x14);
        adapter.insertOtherBill(x15);


    }

    public void insertImportBills() throws Exception {
        //Tạo 5 đối tượng mẫu với đầy đủ thuộc tính (trừ ID)
        ImportBill x1 = new ImportBill("Hoa don 1", 1000, LocalDate.of(2022, 6, 2), true, "day la hoa don 1", "Japan");
        ImportBill x2 = new ImportBill("Hoa don 2", 2000, LocalDate.of(2022, 6, 3), false, "day la hoa don 2", "China");
        ImportBill x3 = new ImportBill("Hoa don 3", 3000, LocalDate.of(2022, 6, 4), true, "day la hoa don 3", "USA");
        ImportBill x4 = new ImportBill("Hoa don 4", 4000, LocalDate.of(2022, 6, 5), false, "day la hoa don 4", "Vietnam");
        ImportBill x5 = new ImportBill("Hoa don 5", 5000, LocalDate.of(2022, 6, 6), true, "day la hoa don 5", "Thailand");

        var adapter = DatabaseAdapter.getDbAdapter();
        for (var x : adapter.getAllImportBills()) {
            //Nếu như các ô đã triển khai override thuộc tính toString() cho
            // của ô rồi thì viết thế này
            System.out.println(x);
            //Còn nếu không các ô phải in từng thuộc tính 1 ra
        }

        adapter.insertImportBill(x1);
        adapter.insertImportBill(x2);
        adapter.insertImportBill(x3);
        adapter.insertImportBill(x4);
        adapter.insertImportBill(x5);

        System.out.println("----------<><><><><>----------");
        for (var x : adapter.getAllImportBills()) {
            //Nếu như các ô đã triển khai override thuộc tính toString() cho
            // của ô rồi thì viết thế này
            System.out.println(x);
            //Còn nếu không các ô phải in từng thuộc tính 1 ra
        }
    }

    public void insertStaffBills() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Staff> staffs = adapter.getAllStaffs();
        StaffBill staffBill = null;
        Random random = new Random(0);
        double workHours;
        LocalDate from;
        for (Staff staff: staffs){
            from = StaffManager.getManager().getFrom(staff);
            workHours = StaffManager.getManager().getWorkHours(staff);
            staffBill = new StaffBill("Lương tháng 4 cho " + staff.getName(), 20 * workHours, LocalDate.of(2022, 4, 30), true, "Không có ghi chú",
                    from, staff, 20, workHours);
            staff.setLatestPay(LocalDate.of(2022, 4, 30));
            adapter.updateStaff(staff);
            adapter.insertStaffBill(staffBill);

        }
    }

    public void insertAttendances(int month) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        Random random = new Random(0);
        int numOfStaffs;
        int staffIndex;
        LocalDateTime now;
        Attendance attendance;
        ArrayList<Staff> staffs;
        Shift shift;
        Staff staff;

        for (int i = 1; i <= 30; i++){
            now = LocalDateTime.of(2022, month, i, 8, 0);
            shift = StaffManager.getManager().getCurrentShift(now);
            numOfStaffs = random.nextInt(4);

            staffs = new ArrayList<>();

            for (int j = 1; j <= numOfStaffs; j++){
                staffIndex = random.nextInt(2);
                staff = shift.getStaffs().get(staffIndex);
                if (!staffs.contains(staff)) staffs.add(staff);
            }

            attendance = StaffManager.getManager().getAttendace(now, staffs);
            if (attendance != null) adapter.insertAttendance(attendance);

            now = LocalDateTime.of(2022, month, i, 13, 0);
            shift = StaffManager.getManager().getCurrentShift(now);
            numOfStaffs = random.nextInt(4);

            staffs = new ArrayList<>();

            for (int j = 1; j <= numOfStaffs; j++){
                staffIndex = random.nextInt(2);
                staff = shift.getStaffs().get(staffIndex);
                if (!staffs.contains(staff)) staffs.add(staff);
            }

            attendance = StaffManager.getManager().getAttendace(now, staffs);
            if (attendance != null) adapter.insertAttendance(attendance);
            now = LocalDateTime.of(2022, month, i, 20, 0);
            shift = StaffManager.getManager().getCurrentShift(now);
            numOfStaffs = random.nextInt(4);

            staffs = new ArrayList<>();

            for (int j = 1; j <= numOfStaffs; j++){
                staffIndex = random.nextInt(2);
                staff = shift.getStaffs().get(staffIndex);
                if (!staffs.contains(staff)) staffs.add(staff);
            }

            attendance = StaffManager.getManager().getAttendace(now, staffs);
            if (attendance != null) adapter.insertAttendance(attendance);
        }

    }

    public void insertShift() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();

        ArrayList<Staff> staffs;
        Shift shift;
        ObservableList<Staff> staffObservableList = adapter.getAllStaffs();
        Random random = new Random(0);
        Staff staff = null;

        for (int i = 1; i <= 7; i++){
            staffs = new ArrayList<>();
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            shift = new Shift(staffs, i, LocalTime.of(7, 0), LocalTime.of(12, 0));
            adapter.insertShift(shift);
            staffs = new ArrayList<>();
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            shift = new Shift(staffs, i, LocalTime.of(12, 0), LocalTime.of(17, 0));
            adapter.insertShift(shift);
            staffs = new ArrayList<>();
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            staff = staffObservableList.get(random.nextInt(30));
            if (!staffs.contains(staff)) staffs.add(staff);
            shift = new Shift(staffs, i, LocalTime.of(17, 0), LocalTime.of(22, 0));
            adapter.insertShift(shift);
        }

    }

}

