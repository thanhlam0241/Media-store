package com.shopkeeper.minh.models;

import java.security.InvalidParameterException;
import java.time.LocalDate;

import com.shopkeeper.mediaone.models.BillType;
import com.shopkeeper.mediaone.models.Bill;

public final class OtherBill extends Bill {
    private String name;
    private BillType billType;
    private double price;
    private int billId;
    private LocalDate time;
    private boolean isEffected;
    private String note;

    public OtherBill(){
        billId = 0;
        billType = BillType.Other;
    }

    public OtherBill(String name, double price, LocalDate time, boolean isEffected, String note){
        this.billType = BillType.Other;
        this.name = name;
        this.price = price;
        this.time = time;
        this.isEffected = isEffected;
        this.note = note;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBillType(BillType billType) {
        if(billType == BillType.Import || billType == BillType.Sale || billType == BillType.Staff) return;
        this.billType = billType;
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
        sb.append('}');
        return sb.toString();
    }
}
