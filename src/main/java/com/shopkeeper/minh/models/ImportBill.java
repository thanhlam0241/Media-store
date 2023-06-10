package com.shopkeeper.minh.models;

import com.shopkeeper.mediaone.models.BillType;
import com.shopkeeper.mediaone.models.Bill;

import java.security.InvalidParameterException;
import java.time.LocalDate;

public final class ImportBill extends Bill{
    private String name;
    private double price;
    private int billId;
    private LocalDate time;
    private boolean isEffected;
    private String note;
    private String distributor;
    private BillType billType;

    public ImportBill(){
        billId = 0;
        billType = BillType.Import;
    }


    public ImportBill(String name, double price, LocalDate time, boolean isEffected, String note, String distributor){
        this.billType = BillType.Import;
        this.name = name;
        this.price = price;
        this.time = time;
        this.isEffected = isEffected;
        this.note = note;
        this.distributor = distributor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public BillType getBillType() {
        return BillType.Import;
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

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
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
        sb.append("    distributor: \""); sb.append(getDistributor());sb.append("\",\n");
        sb.append("    note: \""); sb.append(getNote());sb.append("\",\n");
        sb.append('}');
        return sb.toString();
    }

}
