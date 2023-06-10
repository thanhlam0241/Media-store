package com.shopkeeper.minh.models;

import com.shopkeeper.linh.models.Staff;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Shift {
    private ArrayList<Staff> staffs;
    private int dateOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int shiftId;

    public Shift(){
        startTime = null;
        endTime = null;
        dateOfWeek = 0;
        shiftId = 0;
    }

    public Shift(ArrayList<Staff> staffs, int dateOfWeek, LocalTime startTime, LocalTime endTime){
        shiftId = 0;
        this.staffs = staffs;
        this.dateOfWeek = dateOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftId() {
        return shiftId;
    }

    public ArrayList<Staff> getStaffs() {
        return staffs;
    }

    public int getDateOfWeek() {
        return dateOfWeek;
    }

    public void setDateOfWeek(int dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        if (this.startTime == null) {
            this.startTime = startTime;
            return;
        }
        if (startTime.isAfter(this.endTime)){
            throw new InvalidParameterException("startTime is invalid.");
        }
        this.startTime = startTime;
        return;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (this.endTime == null) {
            this.endTime = endTime;
            return;
        }
        if (endTime.isBefore(this.startTime)){
            throw new InvalidParameterException("endTime is invalid.");
        }
        this.endTime = endTime;
    }

    public void setStaffs(ArrayList<Staff> staffs) {
        ArrayList<Staff> addedStaffs = new ArrayList<>();
        for (Staff staff: staffs){
            if (!addedStaffs.contains(staff)) addedStaffs.add(staff);
        }
        this.staffs = addedStaffs;
    }

    public void setShiftId(int shiftId) {
        if (this.shiftId > 0){
            throw new InvalidParameterException("shiftId is able to be set only one time.");
        }
        this.shiftId = shiftId;
    }
}
