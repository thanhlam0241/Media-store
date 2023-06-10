package com.shopkeeper.minh.models;

import com.shopkeeper.linh.models.Staff;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Attendance {
    private LocalDateTime time;
    private Duration duration;
    private ArrayList<Staff> staffsWork;
    private ArrayList<Staff> staffsAbsentee;

    public Attendance(){
        this.time = LocalDateTime.of(1000, 1, 1, 1, 1, 1);
    }

    public Attendance(LocalDateTime time, Duration duration, ArrayList<Staff> staffsWork, ArrayList<Staff> staffsAbsentee){
        this.time = time;
        this.duration = duration;
        this.staffsWork = staffsWork;
        this.staffsAbsentee = staffsAbsentee;
    }

    public void setTime(LocalDateTime time) {
        if (!this.time.equals(LocalDateTime.of(1000, 1, 1, 1, 1, 1))){
            throw new InvalidParameterException("Time is able to be set only one.");
        }
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public ArrayList<Staff> getStaffsAbsentee() {
        return staffsAbsentee;
    }

    public void setStaffsWork(ArrayList<Staff> staffsWork) {
        this.staffsWork = staffsWork;
    }

    public void setStaffsAbsentee(ArrayList<Staff> staffsAbsentee) {
        this.staffsAbsentee = staffsAbsentee;
    }

    public ArrayList<Staff> getStaffsWork() {
        return staffsWork;
    }
}
