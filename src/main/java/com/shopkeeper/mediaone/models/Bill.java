package com.shopkeeper.mediaone.models;

import java.time.LocalDate;

public abstract class Bill {
    public abstract String getName();
    public abstract BillType getBillType();
    public abstract double getPrice();
    public abstract int getBillId();
    public abstract LocalDate getTime();
    public abstract boolean getIsEffected();
    public abstract String getNote();
}
