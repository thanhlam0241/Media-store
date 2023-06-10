package com.shopkeeper.minh.models;

import java.security.InvalidParameterException;
import java.time.LocalDate;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.models.BillType;
import com.shopkeeper.mediaone.models.Bill;

public final class StaffBill extends Bill {
    private String name;
    private BillType billType;
    private double price;
    private int billId;
    private LocalDate time;
    private boolean isEffected;
    private String note;
    private LocalDate from;
    private Staff staff;
    public double standardSalaryPerHour;
    public double workHours;

    public StaffBill(String name, double price, LocalDate time, boolean isEffected, String note, LocalDate from, Staff staff, double standardSalaryPerHour, double workHours)
    {
        this.billId = 0;
        this.staff = staff;
        this.billType = BillType.Staff;
        this.name = name;
        this.price = price;
        this.time = time;
        this.isEffected = isEffected;
        this.note = note;
        this.from = from;
        this.standardSalaryPerHour = standardSalaryPerHour;
        this.workHours = workHours;
    }

    public StaffBill(){
        billId = 0;
        billType = BillType.Staff;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public BillType getBillType() {
        return billType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setBillId(int billId) {
        if (this.billId > 0){
            throw new InvalidParameterException("billId is able to be set only one time.");
        }
        this.billId = billId;
    }

    public int getBillId() {
        return billId;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    @Override
    public boolean getIsEffected() {
        return isEffected;
    }

    public void setIsEffected(boolean isEffected) {
        this.isEffected = isEffected;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public double getStandardSalaryPerHour() {
        return standardSalaryPerHour;
    }

    public void setStandardSalaryPerHour(double standardSalaryPerHour) {
        this.standardSalaryPerHour = standardSalaryPerHour;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double calculateSalary(){
        return standardSalaryPerHour * workHours;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("    billId: \""); sb.append(getBillId());sb.append("\",\n");
        sb.append("    name: \""); sb.append(getName());sb.append("\",\n");
        sb.append("    isEffected: "); sb.append(getIsEffected());sb.append(",\n");
        sb.append("    billType: "); sb.append(getBillType());sb.append(",\n");
        sb.append("    Time: "); sb.append(getTime());sb.append(",\n");
        sb.append("    price: \""); sb.append(getPrice());sb.append("\",\n");
        sb.append("    note: \""); sb.append(getNote());sb.append("\",\n");
        sb.append("    from: \""); sb.append(getFrom().toString());sb.append("\",\n");
        sb.append("    standardSalary: \""); sb.append(getStandardSalaryPerHour());sb.append("\",\n");
        sb.append("    working hours: \""); sb.append(getWorkHours());sb.append("\",\n");
        sb.append("    staffId: \""); sb.append(getStaff().getStaffId());sb.append("\",\n");
        sb.append('}');
        return sb.toString();
    }
}
