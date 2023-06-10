package com.shopkeeper.minh.windowfactories;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.linh.models.StaffState;
import com.shopkeeper.linh.windowfactories.utilities.ComboBoxOption;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.functions.BillManager;
import com.shopkeeper.minh.functions.DeletedInfo;
import com.shopkeeper.minh.functions.StaffManager;
import com.shopkeeper.minh.models.Attendance;
import com.shopkeeper.minh.models.Shift;
import com.shopkeeper.minh.models.StaffBill;
import com.shopkeeper.minh.windowfactories.shift.ShiftListCell;
import com.shopkeeper.minh.windowfactories.staff.StaffListCell;
import com.shopkeeper.minh.windowfactories.staffbill.StaffBillListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class StaffWindowController {


    @FXML
    private ListView<Staff> staffListView;

    @FXML
    private VBox staffDisplayer;

    @FXML
    private Text staffHeaderDisplayer;

    @FXML
    private Text staffTargetDisplayer;

    @FXML
    private Text staffDescriptionDisplayer;

    @FXML
    private GridPane filterPanel;

    @FXML
    private Text result;

    @FXML
    private TextField filterTargetNameTxt;

    @FXML
    private TextField filterTargetIdTxt;

    @FXML
    private TextField filterTargetEmailTxt;

    @FXML
    private ComboBox<ComboBoxOption<StaffState>> filterTargetStateComboBox;

    @FXML
    private Button searchButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button insertButton;


    @FXML
    private Button resetButton;

    @FXML
    private TextField filterPhoneTxt;

    @FXML
    private TextField filterDescriptTxt;

    @FXML
    private DatePicker filterDateOfBirth;

    @FXML
    private ComboBox<ComboBoxOption<Boolean>> filterSex;

    @FXML
    private void resetFilterPanel(){
        filterTargetStateComboBox.getSelectionModel().selectFirst();
        filterTargetIdTxt.setText("");
        filterTargetNameTxt.setText("");
        filterTargetEmailTxt.setText("");
        filterDateOfBirth.setValue(null);
        filterSex.getSelectionModel().selectFirst();
        filterPhoneTxt.setText("");
        filterDescriptTxt.setText("");
        result.setText("");
    }

    @FXML
    public void filterList() throws Exception {
        String staffName = filterTargetNameTxt.getText();
        String staffIdTxt = filterTargetIdTxt.getText();
        String staffEmail = filterTargetEmailTxt.getText();
        StaffState staffState = filterTargetStateComboBox.getValue().getValue();
        String phoneNumber = filterPhoneTxt.getText();
        String descript = filterDescriptTxt.getText();
        Boolean isMale = filterSex.getValue().getValue();
        LocalDate dateOfBirth = filterDateOfBirth.getValue();
        long staffId = 0;

        ArrayList<Staff> oldStaffList;
        ArrayList<Staff> newStaffList = new ArrayList<Staff>(StaffManager.getManager().getAll());



       if (staffIdTxt != null && !staffIdTxt.isEmpty()){
           try {
               staffId = Long.parseLong(staffIdTxt);
           }
           catch (Exception e){
               result.setText("Chỉ nhập số nguyên.");
               initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
               return;
           }

           Staff staff = StaffManager.getManager().findById(staffId);
           if (staff == null){
               result.setText("Không tìm thấy");
               initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
               return;
           }
           newStaffList = new ArrayList<>();
           newStaffList.add(staff);

       }

       // Name search

       if (staffName != null && !staffName.isEmpty()){
           oldStaffList = newStaffList;
           newStaffList = StaffManager.getManager().findByName(staffName, oldStaffList);
           if (newStaffList == null || newStaffList.isEmpty()){
               result.setText("Không tìm thấy");
               initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
               return;
           }
       }

       // Email search
       if (staffEmail != null && !staffEmail.isEmpty()) {
           oldStaffList = newStaffList;
           newStaffList = StaffManager.getManager().findByEmail(staffEmail, oldStaffList);
           if (newStaffList == null || newStaffList.isEmpty()){
               result.setText("Không tìm thấy");
               initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
               return;
           }
       }

       // State search
        if (!ComboBoxOption.isSelectAllOption(filterTargetStateComboBox.getValue())){
            oldStaffList = newStaffList;
            newStaffList = StaffManager.getManager().findByState(staffState, oldStaffList);
            if (newStaffList == null || newStaffList.isEmpty()){
                result.setText("Không tìm thấy");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }

        // Phone search
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            oldStaffList = newStaffList;
            newStaffList = StaffManager.getManager().findByPhoneNumber(phoneNumber, oldStaffList);
            if (newStaffList == null || newStaffList.isEmpty()){
                result.setText("Không tìm thấy");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }

        // Description search
        if (descript != null && !descript.isEmpty()) {
            oldStaffList = newStaffList;
            newStaffList = StaffManager.getManager().findByDescription(descript, oldStaffList);
            if (newStaffList == null || newStaffList.isEmpty()){
                result.setText("Không tìm thấy");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }

        // Sex search
        if (!ComboBoxOption.isSelectAllOption(filterSex.getValue())){
            oldStaffList = newStaffList;
            newStaffList = StaffManager.getManager().findBySex(isMale, oldStaffList);
            if (newStaffList == null || newStaffList.isEmpty()){
                result.setText("Không tìm thấy");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }

        // Birthday search
        if (dateOfBirth != null){
            oldStaffList = newStaffList;
            newStaffList = StaffManager.getManager().findByBirthday(dateOfBirth, oldStaffList);
            if (newStaffList == null || newStaffList.isEmpty()){
                result.setText("Không tìm thấy");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }


        // Display
        if (newStaffList != null && !newStaffList.isEmpty())
        {
            result.setText("Tìm thấy");
            ObservableList<Staff> staffObservableList = FXCollections.observableList(newStaffList);
            initializeStaffList(staffObservableList);
            return;
        }

    }

    private void initializeStaffDisplayer(){
        staffDisplayer.setVisible(false);
    }

    private void displayStaff(Staff staff){
        if (staff != null) {
            staffDisplayer.setVisible(true);
            staffHeaderDisplayer.setText(staff.getName());
            String staffIdTxt = String.format("ID: %d", staff.getStaffId());
            staffTargetDisplayer.setText(staffIdTxt);
            StringBuilder sb = new StringBuilder();
            if (staff.getIsMale()) {
                sb.append("- Giới tính: ");
                sb.append("Nam");
                sb.append(",\n\n");
            } else {
                sb.append("- Giới tính: ");
                sb.append("Nữ");
                sb.append(",\n\n");
            }

            sb.append("- Ngày sinh: ");
            sb.append(staff.getDateOfBirth());
            sb.append(",\n\n");
            sb.append("- Email: \"");
            sb.append(staff.getEmail());
            sb.append("\",\n\n");
            sb.append("- Số điện thoại: \"");
            sb.append(staff.getPhoneNumber());
            sb.append("\",\n\n");
            sb.append("- Miêu tả: \"");
            sb.append(staff.getDescription());
            sb.append("\",\n\n");
            sb.append("- State: ");
            sb.append(staff.getState());
            sb.append("\n");
            staffDescriptionDisplayer.setText(sb.toString());
        }
    }

    private void initializeStaffList(ObservableList<Staff> staffs){
        staffListView.setCellFactory(new Callback<ListView<Staff>, ListCell<Staff>>() {
            @Override
            public ListCell<Staff> call(ListView<Staff> staffListView) {
                return new StaffListCell();
            }
        });
        try{
            staffListView.setItems(staffs);
        } catch (Exception e){
            e.printStackTrace();
        }
        staffListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayStaff(newValue);
        });
    }

    public StaffWindowController(){
    }

    public void initializeStaffFilterPanel(){
        filterPanel.managedProperty().bind(filterPanel.visibleProperty());
        filterTargetStateComboBox.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Chọn tất cả"),
                new ComboBoxOption<>(StaffState.Demited, "Demited"),
                new ComboBoxOption<>(StaffState.Working, "Working"),
                new ComboBoxOption<>(StaffState.Interviewing, "Interviewing"),
                new ComboBoxOption<>(StaffState.Unreliable, "Unreliable"),
                new ComboBoxOption<>(StaffState.NotEmployed, "Not employed")
        ));
        filterTargetStateComboBox.getSelectionModel().selectFirst();

        filterSex.setItems(FXCollections.observableArrayList(
                ComboBoxOption.SelectAllOption("Chọn tất cả"),
                new ComboBoxOption<>(true, "Nam"),
                new ComboBoxOption<>(false, "Nữ")
        ));
        filterSex.getSelectionModel().selectFirst();
    }


    public void initialize() throws Exception {
        initializeStaffFilterPanel();
        initializeStaffDisplayer();
        initializeStaffList(StaffManager.getManager().getAll());
        initializeShiftFilterPanel();
        initializeShiftDisplayer();
        initializeShiftList(StaffManager.getManager().getShiftList());
        initializeStaffBillFilterPanel();
        initializeStaffBillDisplayer();
        initializeStaffBillList(StaffManager.getManager().getStaffBillList());
    }

    @FXML
    public void update() throws Exception {
        String staffName = filterTargetNameTxt.getText();
        String staffIdTxt = filterTargetIdTxt.getText();
        String staffEmail = filterTargetEmailTxt.getText();
        StaffState staffState = filterTargetStateComboBox.getValue().getValue();
        String phoneNumber = filterPhoneTxt.getText();
        String descript = filterDescriptTxt.getText();
        Boolean isMale = filterSex.getValue().getValue();
        LocalDate dateOfBirth = filterDateOfBirth.getValue();
        long staffId = 0;

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            try {
                staffId = Long.parseLong(staffIdTxt);
            }
            catch (Exception e){
                result.setText("Chỉ nhập số nguyên.");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }
        else {
            result.setText("Nhập ID của nhân viên trước khi update.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        Staff staff = StaffManager.getManager().findById(staffId);
        if (staff == null){
            result.setText("Không tìm thấy");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Name search

        if (staffName != null && !staffName.isEmpty()) staff.setName(staffName);

        // Email search
        if (staffEmail != null && !staffEmail.isEmpty()) staff.setEmail(staffEmail);

        // State search
        if (!ComboBoxOption.isSelectAllOption(filterTargetStateComboBox.getValue())) staff.setState(staffState);

        // Phone search
        if (phoneNumber != null && !phoneNumber.isEmpty()) staff.setPhoneNumber(phoneNumber);

        // Description search
        if (descript != null && !descript.isEmpty()) staff.setDescription(descript);

        // Sex search
        if (!ComboBoxOption.isSelectAllOption(filterSex.getValue()))staff.setIsMale(isMale);

        // Birthday search
        if (dateOfBirth != null)staff.setDateOfBirth(dateOfBirth);
        StaffManager.getManager().update(staff);

        Staff newStaff = StaffManager.getManager().findById(staffId);
        if (newStaff == null){
            result.setText("Error");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }
        else {
            // Display
            ArrayList<Staff> newStaffList = new ArrayList<>();
            newStaffList.add(newStaff);
            result.setText("Update thành công");
            ObservableList<Staff> staffObservableList = FXCollections.observableList(newStaffList);
            initializeStaffList(staffObservableList);
            return;
        }
    }

    @FXML
    public void insert() throws Exception {
        String staffName = filterTargetNameTxt.getText();
        String staffIdTxt = filterTargetIdTxt.getText();
        String staffEmail = filterTargetEmailTxt.getText();
        StaffState staffState = filterTargetStateComboBox.getValue().getValue();
        String phoneNumber = filterPhoneTxt.getText();
        String descript = filterDescriptTxt.getText();
        Boolean isMale = filterSex.getValue().getValue();
        LocalDate dateOfBirth = filterDateOfBirth.getValue();

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            result.setText("Xóa ID của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        Staff staff = new Staff();

        // Name search
        if (staffName != null && !staffName.isEmpty()) staff.setName(staffName);
        else {
            result.setText("Nhập tên của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Email search
        if (staffEmail != null && !staffEmail.isEmpty()) staff.setEmail(staffEmail);
        else {
            result.setText("Nhập email của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // State search
        if (!ComboBoxOption.isSelectAllOption(filterTargetStateComboBox.getValue())) staff.setState(staffState);
        else {
            result.setText("Chọn state của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Phone search
        if (phoneNumber != null && !phoneNumber.isEmpty()) staff.setPhoneNumber(phoneNumber);
        else {
            result.setText("Nhập số điện thoại của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Description search
        if (descript != null && !descript.isEmpty()) staff.setDescription(descript);
        else {
            result.setText("Nhập mô tả về nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Sex search
        if (!ComboBoxOption.isSelectAllOption(filterSex.getValue()))staff.setIsMale(isMale);
        else {
            result.setText("Chọn giới tính của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Birthday search
        if (dateOfBirth != null)staff.setDateOfBirth(dateOfBirth);
        else {
            result.setText("Chọn sinh nhật của nhân viên trước khi thêm.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        StaffManager.getManager().add(staff);

        // Display

        Staff newStaff = StaffManager.getManager().findById(staff.getStaffId());
        if (newStaff == null){
            result.setText("Error");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }
        else {
            // Display
            ArrayList<Staff> newStaffList = new ArrayList<>();
            newStaffList.add(newStaff);
            result.setText(String.format("Thêm thành công, ID nhân viên mới là %d", staff.getStaffId()));
            ObservableList<Staff> staffObservableList = FXCollections.observableList(newStaffList);
            initializeStaffList(staffObservableList);
            return;
        }
    }

    @FXML
    public void delete() throws Exception {
        String staffName = filterTargetNameTxt.getText();
        String staffIdTxt = filterTargetIdTxt.getText();
        String staffEmail = filterTargetEmailTxt.getText();
        StaffState staffState = filterTargetStateComboBox.getValue().getValue();
        String phoneNumber = filterPhoneTxt.getText();
        String descript = filterDescriptTxt.getText();
        Boolean isMale = filterSex.getValue().getValue();
        LocalDate dateOfBirth = filterDateOfBirth.getValue();
        long staffId = 0;

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            try {
                staffId = Long.parseLong(staffIdTxt);
            }
            catch (Exception e){
                result.setText("Chỉ nhập số nguyên.");
                initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
                return;
            }
        }
        else {
            result.setText("Nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        Staff staff = StaffManager.getManager().findById(staffId);

        // Name search
        if (staffName != null && !staffName.isEmpty())
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Email search
        if (staffEmail != null && !staffEmail.isEmpty())
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // State search
        if (!ComboBoxOption.isSelectAllOption(filterTargetStateComboBox.getValue()))
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Phone search
        if (phoneNumber != null && !phoneNumber.isEmpty())
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Description search
        if (descript != null && !descript.isEmpty())
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Sex search
        if (!ComboBoxOption.isSelectAllOption(filterSex.getValue()))
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        // Birthday search
        if (dateOfBirth != null)
        {
            result.setText("Chỉ nhập ID của nhân viên trước khi xóa.");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        if (staff == null){
            result.setText("Nhân viên này không có trong danh sách");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        int count = 0;

        var adapter = DatabaseAdapter.getDbAdapter();
        for (Attendance attendance: adapter.getAllAttendances()){
            if (attendance.getStaffsWork().contains(staff) || attendance.getStaffsAbsentee().contains(staff))
                count += 1;
        }

        if (staff.countTimesToBeReferenced() > count){
            result.setText("Nhân viên này đang được một đối tượng khác tham chiếu tới, không thể xóa");
            initializeStaffList(FXCollections.observableArrayList(new ArrayList<Staff>()));
            return;
        }

        for (Attendance attendance: adapter.getAllAttendances()){
            if (attendance.getStaffsWork().contains(staff)) {
                ArrayList<Staff> staffs = new ArrayList<>();
                for (Staff workStaff: attendance.getStaffsWork()){
                    if (workStaff.getStaffId() != staff.getStaffId()) staffs.add(workStaff);
                }
                attendance.setStaffsWork(staffs);
                adapter.updateAttendance(attendance);
            }

            else if (attendance.getStaffsAbsentee().contains(staff)) {
                ArrayList<Staff> staffs = new ArrayList<>();
                for (Staff absentStaff: attendance.getStaffsAbsentee()){
                    if (absentStaff.getStaffId() != staff.getStaffId()) staffs.add(absentStaff);
                }
                attendance.setStaffsAbsentee(staffs);
                adapter.updateAttendance(attendance);
            }
        }


        StaffManager.getManager().remove(staff);

        // Display
        result.setText("Xóa thành công");
        ObservableList<Staff> staffObservableList = FXCollections.observableList(StaffManager.getManager().getAll());
        initializeStaffList(staffObservableList);
        return;

    }

    @FXML
    private ListView<Shift> shiftListView;

    @FXML
    private VBox shiftDisplayer;

    @FXML
    private Text shiftHeaderDisplayer;

    @FXML
    private Text shiftIdDisplayer;

    @FXML
    private Text shiftStaffDisplayer;

    @FXML
    private GridPane filterPanel1;

    @FXML
    private Text result1;

    @FXML
    private TextField filterShiftStartHour;

    @FXML
    private TextField filterShiftEndHour;

    @FXML
    private TextField filterShiftStartMinute;

    @FXML
    private TextField filterShiftEndMinute;

    @FXML
    private TextField filterShiftIdTxtBox;

    @FXML
    private TextField filterStaffIdTxtBox;

    @FXML
    private ComboBox<ComboBoxOption<Integer>> dateOfWeekCombobox;

    @FXML
    private void resetFilterPanel1(){
        filterShiftEndHour.setText("");
        filterShiftEndMinute.setText("");
        filterShiftStartHour.setText("");
        filterShiftStartMinute.setText("");
        filterShiftIdTxtBox.setText("");
        dateOfWeekCombobox.getSelectionModel().selectFirst();
        filterStaffIdTxtBox.setText("");
    }

    private void initializeShiftFilterPanel(){
        filterPanel1.managedProperty().bind(filterPanel1.visibleProperty());
        // Initialize date of week options
        dateOfWeekCombobox.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(0, "Không chọn"),
                new ComboBoxOption<>(1, "Thứ 2"),
                new ComboBoxOption<>(2, "Thứ 3"),
                new ComboBoxOption<>(3, "Thứ 4"),
                new ComboBoxOption<>(4, "Thứ 5"),
                new ComboBoxOption<>(5, "Thứ 6"),
                new ComboBoxOption<>(6, "Thứ 7"),
                new ComboBoxOption<>(7, "Chủ nhật")
        ));
        dateOfWeekCombobox.getSelectionModel().selectFirst();
    }

    private void initializeShiftDisplayer(){shiftDisplayer.setVisible(false);}

    private void displayShift(Shift shift){
        if (shift != null){
            shiftDisplayer.setVisible(true);
            String s = String.format("Ca %02d:%02d - %02d:%02d ",
                    shift.getStartTime().getHour(),
                    shift.getStartTime().getMinute(),
                    shift.getEndTime().getHour(),
                    shift.getEndTime().getMinute());
            switch (shift.getDateOfWeek()){
                case 1:
                    s = s + "thứ 2";
                    break;
                case 2:
                    s = s + "thứ 3";
                    break;
                case 3:
                    s = s + "thứ 4";
                    break;
                case 5:
                    s = s + "thứ 6";
                    break;
                case 4:
                    s = s + "thứ 5";
                    break;
                case 6:
                    s = s + "thứ 7";
                    break;
                case 7:
                    s = s + "chủ nhật";
                    break;
            }

            shiftHeaderDisplayer.setText(s);
            shiftIdDisplayer.setText(String.format("ID: %d", shift.getShiftId()));
            String listStaff = "Danh sách nhân viên:\n\n";
            for (Staff staff: shift.getStaffs()){
                listStaff += String.format("- %s (ID: %d)\n\n", staff.getName(),staff.getStaffId());
            }
            shiftStaffDisplayer.setText(listStaff);
        }
    }

    private void initializeShiftList(ObservableList<Shift> shifts){
        shiftListView.setCellFactory(new Callback<ListView<Shift>, ListCell<Shift>>() {
            @Override
            public ListCell<Shift> call(ListView<Shift> shiftListView) {
                return new ShiftListCell();
            }
        });

        try {
            shiftListView.setItems(shifts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        shiftListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayShift(newValue);
        });
    }

    @FXML
    public void filterShiftList() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();

        int startHour = 0;
        int startMinute = 0;
        int endHour = 23;
        int endMinute = 59;

        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        int shiftId;
        String staffIdStrings = filterStaffIdTxtBox.getText();


        ArrayList<Shift> oldShiftList;
        ArrayList<Shift> newShiftList = new ArrayList<Shift>(StaffManager.getManager().getShiftList());



        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            try {
                shiftId = Integer.parseInt(shiftIdTxt);
            }
            catch (Exception e){
                result1.setText("Chỉ nhập số nguyên vào ID.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }

            Shift shift = StaffManager.getManager().findShiftById(shiftId);
            if (shift == null){
                result1.setText("Không tìm thấy");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
            newShiftList = new ArrayList<>();
            newShiftList.add(shift);
        }

        // Starttime search
        if ((startHourTxt != null && !startHourTxt.isEmpty()) || (startMinuteTxt != null && !startMinuteTxt.isEmpty())) {
            if (startHourTxt != null && !startHourTxt.isEmpty()) {
                try {
                    startHour = Integer.parseInt(startHourTxt);
                } catch (Exception e) {
                    result1.setText("Chỉ nhập số nguyên vào giờ.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            if (startMinuteTxt != null && !startMinuteTxt.isEmpty()) {
                try {
                    startMinute = Integer.parseInt(startMinuteTxt);
                } catch (Exception e) {
                    result1.setText("Chỉ nhập số nguyên vào phút.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            LocalTime startTime = LocalTime.of(startHour, startMinute);

            oldShiftList = newShiftList;
            newShiftList = StaffManager.getManager().findByStartTime(startTime, oldShiftList);

            if (newShiftList.isEmpty() || newShiftList == null) {
                result1.setText("Không tìm thấy");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        // Endtime search
        if ((endHourTxt != null && !endHourTxt.isEmpty()) || (endMinuteTxt != null && !endMinuteTxt.isEmpty())) {
            if (endHourTxt != null && !endHourTxt.isEmpty()) {
                try {
                    endHour = Integer.parseInt(endHourTxt);
                } catch (Exception e) {
                    result1.setText("Chỉ nhập số nguyên vào giờ.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            if (endMinuteTxt != null && !endMinuteTxt.isEmpty()) {
                try {
                    endMinute = Integer.parseInt(endMinuteTxt);
                } catch (Exception e) {
                    result1.setText("Chỉ nhập số nguyên vào phút.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            LocalTime endTime = LocalTime.of(endHour, endMinute);

            oldShiftList = newShiftList;
            newShiftList = StaffManager.getManager().findByEndTime(endTime, oldShiftList);

            if (newShiftList.isEmpty() || newShiftList == null) {
                result1.setText("Không tìm thấy");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        // Date of week search
        if (dateOfWeek != 0){
            oldShiftList = newShiftList;
            newShiftList = StaffManager.getManager().findByDateOfWeek(dateOfWeek, oldShiftList);
            if (newShiftList.isEmpty() || newShiftList == null) {
                result1.setText("Không tìm thấy");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }


        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            String[] staffIdTxt = staffIdStrings.split(",");
            ArrayList<Staff> searchingStaffs = new ArrayList<>();


            for (String staffIdString: staffIdTxt){
                long staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                        searchingStaffs.add(staff);
                }
                else {
                    result1.setText("Nhân viên không có trong danh sách.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }
            oldShiftList = newShiftList;
            newShiftList = StaffManager.getManager().findByStaffs(searchingStaffs, oldShiftList);
            if (newShiftList.isEmpty() || newShiftList == null) {
                result1.setText("Không tìm thấy");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        // Display
        if (newShiftList != null && !newShiftList.isEmpty())
        {
            result1.setText("Tìm thấy");
            ObservableList<Shift> shiftObservableList = FXCollections.observableList(newShiftList);
            initializeShiftList(shiftObservableList);
            return;
        }
    }

    public boolean isOverlap(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2){
        if (startTime1.isAfter(startTime2) && endTime1.isBefore(endTime2)) return true;
        if (startTime2.isAfter(startTime1) && endTime2.isBefore(endTime1)) return true;
        return false;
    }

    @FXML
    public void updateShift() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();

        int startHour;
        int startMinute;
        int endHour;
        int endMinute;

        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        int shiftId = 0;
        String staffIdStrings = filterStaffIdTxtBox.getText();

        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            try {
                shiftId = Integer.parseInt(shiftIdTxt);
            }
            catch (Exception e){
                result1.setText("Chỉ nhập số nguyên vào ID.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }
        else {
            result1.setText("Nhập ID của ca làm trước khi update.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        Shift shift = StaffManager.getManager().findShiftById(shiftId);
        if (shift == null){
            result1.setText("Không tìm thấy");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // Date of week search
        if (dateOfWeek != 0) shift.setDateOfWeek(dateOfWeek);

        startHour = shift.getStartTime().getHour();
        startMinute = shift.getStartTime().getMinute();
        endHour = shift.getEndTime().getHour();
        endMinute = shift.getEndTime().getMinute();

        // Starttime search
        if (startHourTxt != null && !startHourTxt.isEmpty()) {
                try {
                    startHour = Integer.parseInt(startHourTxt);
                } catch (Exception e) {
                    result1.setText("Chỉ nhập số nguyên vào giờ hoặc nhập bất hợp lệ.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
        }

        if (startMinuteTxt != null && !startMinuteTxt.isEmpty()) {
            try {
                startMinute = Integer.parseInt(startMinuteTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào phút hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        // End time search
        if (endHourTxt != null && !endHourTxt.isEmpty()) {
            try {
                endHour = Integer.parseInt(endHourTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào giờ hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        if (endMinuteTxt != null && !endMinuteTxt.isEmpty()) {
            try {
                endMinute = Integer.parseInt(endMinuteTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào phút hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        if (startTime.isAfter(endTime)){
            result1.setText("Thời gian bắt đầu phải trước thời gian kết thúc.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();

        ObservableList<Shift> shiftObservableList = adapter.getAllShifts();

        for (Shift shift1: shiftObservableList){
            if (shift.getDateOfWeek() == shift1.getDateOfWeek() && isOverlap(startTime, endTime, shift1.getStartTime(), shift1.getEndTime())){
                result1.setText("Không thể cập nhật do thời gian ca làm không được chồng nhau");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }


        shift.setStartTime(startTime);
        shift.setEndTime(endTime);

        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            String[] staffIdTxt = staffIdStrings.split(",");
            ArrayList<Staff> searchingStaffs = new ArrayList<>();


            for (String staffIdString: staffIdTxt){
                long staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                        searchingStaffs.add(staff);
                }
                else {
                    result1.setText("Nhân viên không có trong danh sách.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            shift.setStaffs(searchingStaffs);
        }


        adapter.updateShift(shift);


        Shift newShift = StaffManager.getManager().findShiftById(shiftId);
        if (newShift == null){
            result1.setText("Error");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else {
            // Display
            ArrayList<Shift> newShiftList = new ArrayList<>();
            newShiftList.add(newShift);
            result1.setText("Update thành công");
            initializeShiftList(FXCollections.observableList(newShiftList));
            return;
        }
    }

    @FXML
    public void insertShift() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();

        int startHour ;
        int startMinute ;
        int endHour ;
        int endMinute ;

        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        String staffIdStrings = filterStaffIdTxtBox.getText();

        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            result1.setText("Xóa ID của ca làm trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        Shift shift = new Shift();

        // Starttime search
        if (startHourTxt == null || startHourTxt.isEmpty()) {
            result1.setText("Nhập giờ bắt đầu trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else
        {
            try {
                startHour = Integer.parseInt(startHourTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào giờ hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        if (startMinuteTxt == null || startMinuteTxt.isEmpty()) {
            result1.setText("Nhập phút bắt đầu trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else
        {
            try {
                startMinute = Integer.parseInt(startMinuteTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào phút hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        shift.setStartTime(LocalTime.of(startHour, startMinute));

        // Endtime search
        if (endHourTxt == null || endHourTxt.isEmpty()) {
            result1.setText("Nhập giờ kết thúc trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else
        {
            try {
                endHour = Integer.parseInt(endHourTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào giờ hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        if (endMinuteTxt == null || endMinuteTxt.isEmpty()) {
            result1.setText("Nhập phút kết thúc trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else
        {
            try {
                endMinute = Integer.parseInt(endMinuteTxt);
            } catch (Exception e) {
                result1.setText("Chỉ nhập số nguyên vào phút hoặc nhập bất hợp lệ.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }

        shift.setEndTime(LocalTime.of(endHour, endMinute));

        // Date of week search
        if (dateOfWeek != 0) shift.setDateOfWeek(dateOfWeek);
        else {
            result1.setText("Chọn thứ trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            String[] staffIdTxt = staffIdStrings.split(",");
            ArrayList<Staff> searchingStaffs = new ArrayList<>();


            for (String staffIdString: staffIdTxt){
                long staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                    searchingStaffs.add(staff);
                }
                else {
                    result1.setText("Nhân viên không có trong danh sách.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            shift.setStaffs(searchingStaffs);
        }
        else {
            result1.setText("Chọn nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Shift> shiftObservableList = adapter.getAllShifts();


        for (Shift shift1: shiftObservableList){
            if (shift.getDateOfWeek() == shift1.getDateOfWeek() && isOverlap(shift.getStartTime(), shift.getEndTime(), shift1.getStartTime(), shift1.getEndTime())){
                result1.setText("Không thể thêm do thời gian ca làm không được chồng nhau");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }



        adapter.insertShift(shift);

        // Display

        Shift newShift = StaffManager.getManager().findShiftById(shift.getShiftId());

        if (newShift == null){
            result1.setText("Error");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else {
            // Display
            ArrayList<Shift> newShiftList = new ArrayList<>();
            newShiftList.add(newShift);
            result1.setText(String.format("Thêm thành công, ID ca mới là %d", shift.getShiftId()));
            initializeShiftList(FXCollections.observableList(newShiftList));
            return;
        }
    }

    @FXML
    public void deleteShift() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();


        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();
        int shiftId;

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        String staffIdStrings = filterStaffIdTxtBox.getText();

        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            try {
                shiftId = Integer.parseInt(shiftIdTxt);
            }
            catch (Exception e){
                result1.setText("Chỉ nhập số nguyên vào ID.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }
        else {
            result1.setText("Nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        Shift shift = StaffManager.getManager().findShiftById(shiftId);


        // Start time search
        if (startHourTxt != null && !startHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (startMinuteTxt != null && !startMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // End time search
        if (endHourTxt != null && !endHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (endMinuteTxt != null && !endMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }


        // Date of week search
        if (dateOfWeek != 0) {
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            result1.setText("Chỉ nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }


        if (shift == null){
            result1.setText("Không tìm thấy");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.deleteShift(shift);

        // Display
        result1.setText("Xóa thành công");
        ObservableList<Shift> shiftObservableList = FXCollections.observableList(StaffManager.getManager().getShiftList());
        initializeShiftList(shiftObservableList);
        return;

    }

    @FXML
    public void insertStaffToShift() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();


        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();
        int shiftId;

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        String staffIdStrings = filterStaffIdTxtBox.getText();
        ArrayList<Staff> searchingStaffs = new ArrayList<>();

        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            try {
                shiftId = Integer.parseInt(shiftIdTxt);
            }
            catch (Exception e){
                result1.setText("Chỉ nhập số nguyên vào ID.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }
        else {
            result1.setText("Nhập ID của ca làm trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        Shift shift = StaffManager.getManager().findShiftById(shiftId);


        // Start time search
        if (startHourTxt != null && !startHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (startMinuteTxt != null && !startMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // End time search
        if (endHourTxt != null && !endHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (endMinuteTxt != null && !endMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }


        // Date of week search
        if (dateOfWeek != 0) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (shift == null){
            result1.setText("Không tìm thấy ca làm có ID đã nhập");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            String[] staffIdTxt = staffIdStrings.split(",");
            searchingStaffs = shift.getStaffs();

            for (String staffIdString: staffIdTxt){
                long staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                        searchingStaffs.add(staff);
                }
                else {
                    result1.setText("Nhân viên không có trong danh sách.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            shift.setStaffs(searchingStaffs);

        }
        else
        {
            result1.setText("Nhập danh sách nhân viên của ca làm trước khi thêm.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateShift(shift);


        Shift newShift = StaffManager.getManager().findShiftById(shiftId);
        if (newShift == null){
            result1.setText("Error");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else {
            // Display
            ArrayList<Shift> newShiftList = new ArrayList<>();
            newShiftList.add(newShift);
            result1.setText("Thêm nhân viên vào ca làm thành công");
            ObservableList<Shift> shiftObservableList = FXCollections.observableList(newShiftList);
            initializeShiftList(shiftObservableList);
            return;
        }
    }


    @FXML
    public void deleteStaffFromShift() throws Exception {
        String startHourTxt = filterShiftStartHour.getText();
        String startMinuteTxt = filterShiftStartMinute.getText();
        String endHourTxt = filterShiftEndHour.getText();
        String endMinuteTxt = filterShiftEndMinute.getText();


        int dateOfWeek = dateOfWeekCombobox.getValue().getValue();
        int shiftId;

        String shiftIdTxt = filterShiftIdTxtBox.getText();
        String staffIdStrings = filterStaffIdTxtBox.getText();

        if (shiftIdTxt != null && !shiftIdTxt.isEmpty()){
            try {
                shiftId = Integer.parseInt(shiftIdTxt);
            }
            catch (Exception e){
                result1.setText("Chỉ nhập số nguyên vào ID.");
                initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                return;
            }
        }
        else {
            result1.setText("Nhập ID của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        Shift shift = StaffManager.getManager().findShiftById(shiftId);


        // Start time search
        if (startHourTxt != null && !startHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (startMinuteTxt != null && !startMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // End time search
        if (endHourTxt != null && !endHourTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (endMinuteTxt != null && !endMinuteTxt.isEmpty()) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }


        // Date of week search
        if (dateOfWeek != 0) {
            result1.setText("Chỉ nhập ID của ca làm và danh sách nhân viên trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        if (shift == null){
            result1.setText("Không tìm thấy ca làm có ID đã nhập");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        // Find by staffs

        if (staffIdStrings != null && !staffIdStrings.isEmpty()){
            String[] staffIdTxt = staffIdStrings.split(",");
            ArrayList<Staff> searchingStaffs = new ArrayList<>();
            ArrayList<Staff> currentStaffs = shift.getStaffs();
            ArrayList<Staff> newStaffs = new ArrayList<>();

            for (String staffIdString: staffIdTxt){
                long staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff))
                        searchingStaffs.add(staff);
                }
                else {
                    result1.setText("Nhân viên không có trong danh sách.");
                    initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
                    return;
                }
            }

            for (Staff staff: currentStaffs)
                if (!searchingStaffs.contains(staff)) newStaffs.add(staff);

            shift.setStaffs(newStaffs);

        }
        else
        {
            result1.setText("Nhập danh sách nhân viên của ca làm trước khi xóa.");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateShift(shift);


        Shift newShift = StaffManager.getManager().findShiftById(shiftId);
        if (newShift == null){
            result1.setText("Error");
            initializeShiftList(FXCollections.observableArrayList(new ArrayList<Shift>()));
            return;
        }
        else {
            // Display
            ArrayList<Shift> newShiftList = new ArrayList<>();
            newShiftList.add(newShift);
            result1.setText("Xóa nhân viên khỏi ca làm thành công");
            ObservableList<Shift> shiftObservableList = FXCollections.observableList(newShiftList);
            initializeShiftList(shiftObservableList);
            return;
        }

    }

    @FXML
    private ListView<StaffBill> staffBillListView;

    @FXML
    private VBox staffBillDisplayer;

    @FXML
    private Text staffBillHeaderDisplayer;

    @FXML
    private Text staffBillIdDisplayer;

    @FXML
    private Text staffBillDescriptionDisplayer;

    @FXML
    private GridPane filterPanel2;

    @FXML
    private Text result2;

    @FXML
    private TextField filterNoteBill;

    @FXML
    private TextField filterSalary;

    @FXML
    private TextField filterBillStaffId;

    @FXML
    private TextField filterBillName;

    @FXML
    private TextField filterBillId;

    @FXML
    private ComboBox<ComboBoxOption<Boolean>> filterEffected;

    @FXML
    private void resetFilterPanel2(){
        filterNoteBill.setText("");
        filterSalary.setText("");
        filterBillStaffId.setText("");
        filterBillName.setText("");
        filterBillId.setText("");
        filterEffected.getSelectionModel().selectFirst();
    }

    private void initializeStaffBillFilterPanel(){
        filterPanel2.managedProperty().bind(filterPanel2.visibleProperty());
        // Initialize date of week options
        filterEffected.setItems(FXCollections.observableArrayList(
                new ComboBoxOption<>(true, "Còn hiệu lực"),
                new ComboBoxOption<>(false, "Hết hiệu lực")
        ));
        filterEffected.getSelectionModel().selectFirst();
    }

    private void initializeStaffBillDisplayer(){staffBillDisplayer.setVisible(false);}

    private void displayStaffBill(StaffBill staffBill){
        if (staffBill != null){
            staffBillDisplayer.setVisible(true);
            staffBillHeaderDisplayer.setText(staffBill.getName());
            staffBillIdDisplayer.setText(String.format("ID: %d", staffBill.getBillId()));
            String s = "";
            s += "Còn hiệu lực: ";
            if (staffBill.getIsEffected()){
                s += "true";
            }
            else {
                s += "false";
            }
            s += ",\n\n";

            s += "Thời gian: "; s += staffBill.getTime().toString(); s += ",\n\n";
            s += String.format("Lương: %.2f nghìn đồng", staffBill.getPrice()); s += ",\n\n";
            s += String.format("Note: %s", staffBill.getNote()); s += ",\n\n";
            s += "Tính từ: "; s += staffBill.getFrom().toString(); s += ",\n\n";
            s += String.format("Lương cơ bản 1 giờ: %.2f nghìn đồng", staffBill.getStandardSalaryPerHour()); s += ",\n\n";
            s += String.format("Số giờ làm việc: %.2f", staffBill.getWorkHours()); s += ",\n\n";
            s += String.format("ID nhân viên: %d", staffBill.getStaff().getStaffId());
            staffBillDescriptionDisplayer.setText(s);
        }
    }

    private void initializeStaffBillList(ObservableList<StaffBill> staffBills){
        staffBillListView.setCellFactory(new Callback<ListView<StaffBill>, ListCell<StaffBill>>() {
            @Override
            public ListCell<StaffBill> call(ListView<StaffBill> staffBillListView) {
                return new StaffBillListCell();
            }
        });

        try {
            staffBillListView.setItems(staffBills);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        staffBillListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayStaffBill(newValue);
        });
    }

    @FXML
    public void filterStaffBillList() throws Exception{
        String note = filterNoteBill.getText();
        String salaryTxt = filterSalary.getText();
        double salary;
        String staffIdTxt = filterBillStaffId.getText();
        long staffId;
        String name = filterBillName.getText();
        int billId;
        String billIdTxt = filterBillId.getText();
        boolean effected = filterEffected.getValue().getValue();

        ArrayList<StaffBill> oldStaffBillList;
        ArrayList<StaffBill> newStaffBillList = new ArrayList<StaffBill>(StaffManager.getManager().getStaffBillList());

        if (billIdTxt != null && !billIdTxt.isEmpty()){
            try{
                billId = Integer.parseInt(billIdTxt);
            }
            catch (Exception e){
                result2.setText("Chỉ nhập số nguyên vào ID.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }

            StaffBill staffBill = StaffManager.getManager().findBillById(billId);
            if (staffBill == null){
                result2.setText("Không tìm thấy");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
            newStaffBillList = new ArrayList<>();
            newStaffBillList.add(staffBill);
        }

        // Find by staffs

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            String[] staffIdStrings = staffIdTxt.split(",");
            ArrayList<Staff> searchingStaffs = new ArrayList<>();

            for (String staffIdString: staffIdStrings){
                staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff))
                        searchingStaffs.add(staff);
                }
                else {
                    result2.setText("Nhân viên không có trong danh sách.");
                    initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                    return;
                }
            }
            oldStaffBillList = newStaffBillList;
            newStaffBillList = StaffManager.getManager().findBillByStaff(searchingStaffs, oldStaffBillList);

            if (newStaffBillList.isEmpty() || newStaffBillList == null){
                result2.setText("Không tìm thấy");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }

        // Find By Salary
        if (salaryTxt != null && !salaryTxt.isEmpty()){
            try {
                salary = Double.parseDouble(salaryTxt);
            }
            catch (Exception e){
                result2.setText("Chỉ nhập số thực vào lương.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
            oldStaffBillList = newStaffBillList;
            newStaffBillList = StaffManager.getManager().findBillBySalary(salary, oldStaffBillList);

            if (newStaffBillList.isEmpty() || newStaffBillList == null){
                result2.setText("Không tìm thấy");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }

        // Find by name
        if (name != null && !name.isEmpty()){
            oldStaffBillList = newStaffBillList;
            newStaffBillList = new ArrayList<>();
            for (StaffBill staffBill: oldStaffBillList)
                if (staffBill.getName().contains(name))
                    newStaffBillList.add(staffBill);

            if (newStaffBillList.isEmpty() || newStaffBillList == null){
                result2.setText("Không tìm thấy");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }

        // Find by note
        if (note != null && !note.isEmpty()){
            oldStaffBillList = newStaffBillList;
            newStaffBillList = new ArrayList<>();
            for (StaffBill staffBill: oldStaffBillList)
                if (staffBill.getNote().contains(note))
                    newStaffBillList.add(staffBill);

            if (newStaffBillList.isEmpty() || newStaffBillList == null){
                result2.setText("Không tìm thấy");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }

        // Find by effected
        oldStaffBillList = newStaffBillList;
        newStaffBillList = new ArrayList<>();
        for (StaffBill staffBill: oldStaffBillList)
            if (staffBill.getIsEffected() == effected)
                newStaffBillList.add(staffBill);

        if (newStaffBillList.isEmpty() || newStaffBillList == null){
            result2.setText("Không tìm thấy");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        // Display
        if (!newStaffBillList.isEmpty() && newStaffBillList != null){
            result2.setText("Tìm thấy");
            initializeStaffBillList(FXCollections.observableList(newStaffBillList));
            return;
        }
    }

    @FXML
    public void updateStaffBill() throws Exception{
        String note = filterNoteBill.getText();
        String salaryTxt = filterSalary.getText();
        double salary;
        String staffIdTxt = filterBillStaffId.getText();
        long staffId;
        String name = filterBillName.getText();
        int billId;
        String billIdTxt = filterBillId.getText();
        boolean effected = filterEffected.getValue().getValue();

        if (billIdTxt != null && !billIdTxt.isEmpty()){
            try{
                billId = Integer.parseInt(billIdTxt);
            }
            catch (Exception e){
                result2.setText("Chỉ nhập số nguyên vào ID.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }
        else {
            result2.setText("Nhập ID của hóa đơn trước khi update.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        StaffBill staffBill = StaffManager.getManager().findBillById(billId);
        if (staffBill == null){
            result2.setText("Không tìm thấy.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            result2.setText("Không được nhập ID nhân viên khi update.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (salaryTxt != null && !salaryTxt.isEmpty()){
            result2.setText("Không được nhập lương cơ bản khi update.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (name != null && !name.isEmpty()) staffBill.setName(name);
        if (note != null && !note.isEmpty()) staffBill.setNote(note);

        staffBill.setIsEffected(effected);

        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.updateStaffBill(staffBill);

        StaffBill newStaffBill = StaffManager.getManager().findBillById(billId);
        if (newStaffBill == null){
            result2.setText("Error");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }
        else {
            // Display
            ArrayList<StaffBill> newStaffBillList = new ArrayList<>();
            newStaffBillList.add(newStaffBill);
            result2.setText("Update thành công");
            initializeStaffBillList(FXCollections.observableList(newStaffBillList));
            return;
        }
    }

    @FXML
    public void deleteStaffBill() throws Exception{
        String note = filterNoteBill.getText();
        String salaryTxt = filterSalary.getText();
        String staffIdTxt = filterBillStaffId.getText();
        String name = filterBillName.getText();
        int billId;
        String billIdTxt = filterBillId.getText();
        boolean effected = filterEffected.getValue().getValue();

        if (billIdTxt != null && !billIdTxt.isEmpty()){
            try{
                billId = Integer.parseInt(billIdTxt);
            }
            catch (Exception e){
                result2.setText("Chỉ nhập số nguyên vào ID.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }
        else {
            result2.setText("Nhập ID của hóa đơn trước khi xóa.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        StaffBill staffBill = StaffManager.getManager().findBillById(billId);

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            result2.setText("Chỉ nhập ID hóa đơn trước khi xóa.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (salaryTxt != null && !salaryTxt.isEmpty()){
            result2.setText("Chỉ nhập ID hóa đơn trước khi xóa.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (name != null && !name.isEmpty()){
            result2.setText("Chỉ nhập ID hóa đơn trước khi xóa.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (note != null && !note.isEmpty()){
            result2.setText("Chỉ nhập ID hóa đơn trước khi xóa.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }


        if (staffBill == null){
            result2.setText("Không tìm thấy.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();


        if (staffBill.getIsEffected()) {
            result2.setText("Chỉ được xóa hóa đơn đã hết hiệu lực.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }



        adapter.deleteStaffBill(staffBill);

        // Display
        result2.setText("Xóa thành công.");
        initializeStaffBillList(FXCollections.observableList(StaffManager.getManager().getStaffBillList()));
        return;
    }
    @FXML
    public void punchIn() throws Exception{
        String note = filterNoteBill.getText();
        String salaryTxt = filterSalary.getText();
        String staffIdTxt = filterBillStaffId.getText();
        long staffId;
        String name = filterBillName.getText();
        String billIdTxt = filterBillId.getText();


        if (note != null && !note.isEmpty()){
            result2.setText("Chỉ nhập ID nhân viên khi chấm công");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (salaryTxt != null && !salaryTxt.isEmpty()){
            result2.setText("Chỉ nhập ID nhân viên khi chấm công");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (name != null && !name.isEmpty()){
            result2.setText("Chỉ nhập ID nhân viên khi chấm công");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (billIdTxt != null && !billIdTxt.isEmpty()){
            result2.setText("Chỉ nhập ID nhân viên khi chấm công");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        if (staffIdTxt == null || staffIdTxt.isEmpty()){
            result2.setText("Nhập ID nhân viên trước khi chấm công");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        String[] staffIdStrings = staffIdTxt.split(",");
        ArrayList<Staff> searchingStaffs = new ArrayList<>();

        for (String staffIdString: staffIdStrings){
            staffId = Long.parseLong(staffIdString);
            Staff staff = StaffManager.getManager().findById(staffId);
            if (staff != null) {
                if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                    searchingStaffs.add(staff);
            }
            else {
                result2.setText("Nhân viên không có trong danh sách.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        }

        Attendance attendance = StaffManager.getManager().punchIn(searchingStaffs);

        if (attendance == null){
            result2.setText("Không có ca làm nào bây giờ hoặc không thể chấm công do mỗi ca làm chỉ cho chấm công một lần duy nhất.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        result2.setText("Chấm công thành công.");
        initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
        return;

    }

    @FXML
    public void paySalary() throws Exception{
        String note = filterNoteBill.getText();
        String salaryTxt = filterSalary.getText();
        double salary = 50.0;
        String staffIdTxt = filterBillStaffId.getText();
        long staffId;
        String name = filterBillName.getText();
        String billIdTxt = filterBillId.getText();

        if (billIdTxt != null && !billIdTxt.isEmpty()){
            result2.setText("Không nhập ID của hóa đơn trước khi trả lương");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }


        if (salaryTxt != null && !salaryTxt.isEmpty()){
            try {
                salary = Double.parseDouble(salaryTxt);
            }
            catch (Exception e){
                result2.setText("Chỉ nhập số thực vào lương.");
                initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                return;
            }
        } else {
            result2.setText("Nhập lương cơ bản một giờ trước khi trả lương.");
            initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
            return;
        }

        var adapter = DatabaseAdapter.getDbAdapter();


        ArrayList<Staff> searchingStaffs = new ArrayList<>();

        if (staffIdTxt != null && !staffIdTxt.isEmpty()){
            String[] staffIdStrings = staffIdTxt.split(",");
            for (String staffIdString: staffIdStrings){
                staffId = Long.parseLong(staffIdString);
                Staff staff = StaffManager.getManager().findById(staffId);
                if (staff != null) {
                    if (!searchingStaffs.contains(staff) && staff.getState() == StaffState.Working)
                        searchingStaffs.add(staff);
                }
                else {
                    result2.setText("Nhân viên không có trong danh sách.");
                    initializeStaffBillList(FXCollections.observableArrayList(new ArrayList<StaffBill>()));
                    return;
                }
            }
        }
        else {
            searchingStaffs = new ArrayList<>(StaffManager.getManager().getAll());
        }
        ArrayList<StaffBill> newStaffBills = new ArrayList<>();

        for (Staff staff: searchingStaffs){
            StaffBill staffBill = new StaffBill();

            if (name != null && !name.isEmpty()) staffBill.setName(name);
            else staffBill.setName(String.format("Hóa đơn trả lương cho %s (ID: %d)", staff.getName(), staff.getStaffId()));

            if (note != null && !note.isEmpty()) staffBill.setNote(note);
            else staffBill.setNote("Không có ghi chú");

            staffBill.setStaff(staff);

            staffBill.setIsEffected(true);
            staffBill.setTime(LocalDate.now());
            staffBill.setStandardSalaryPerHour(salary);
            staffBill.setFrom(StaffManager.getManager().getFrom(staff));
            staffBill.setWorkHours(StaffManager.getManager().getWorkHours(staff));
            staffBill.setPrice(salary * staffBill.getWorkHours());

            staff.setLatestPay(LocalDate.now());
            adapter.updateStaff(staff);

            adapter.insertStaffBill(staffBill);
            newStaffBills.add(staffBill);

        }

        // Display
        result2.setText("Trả lương thành công");
        initializeStaffBillList(FXCollections.observableArrayList(newStaffBills));
        return;
    }


}
