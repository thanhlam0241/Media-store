package com.shopkeeper.linh.models;

import com.shopkeeper.mediaone.models.BillType;
import com.shopkeeper.mediaone.models.Bill;
import javafx.beans.property.*;

import java.security.InvalidParameterException;
import java.time.*;



public class SaleBill extends Bill {
    @Override
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public BillType getBillType() {
        return BillType.Sale;
    }

//    public double getVAT() {
//        return VAT.get();
//    }
//
//    public DoubleProperty VATProperty() {
//        return VAT;
//    }
//
//    public void setVAT(double VAT) {
//        this.VAT.set(VAT);
//    }

    public boolean getIsPaid() {
        return isPaid.get();
    }

    public BooleanProperty isPaidProperty() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid.set(isPaid);
    }

    @Override
    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        if(this.billId > 0) throw new InvalidParameterException("feedbackId is able to be set only one time.");
        this.billId = billId;
    }

    @Override
    public LocalDate getTime() {
        return time.get();
    }

    public ObjectProperty<LocalDate> timeProperty() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time.set(time);
    }
    @Override
    public boolean getIsEffected() {
        return effected.get();
    }

    public BooleanProperty isEffectedProperty() {
        return effected;
    }

    public void setIsEffected(boolean effected) {
        this.effected.set(effected);
    }
    @Override
    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<Customer>();
    private final StringProperty location = new SimpleStringProperty();
    //private DoubleProperty VAT = new SimpleDoubleProperty();
    private final BooleanProperty isPaid = new SimpleBooleanProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private int billId;
    private final ObjectProperty<LocalDate> time = new SimpleObjectProperty<LocalDate>();

    private final BooleanProperty effected = new SimpleBooleanProperty();
    private final StringProperty note = new SimpleStringProperty();
    public SaleBill(){
        customer.set(null);
    }
    public SaleBill(String name, Customer customer, String location, boolean isPaid, double price, LocalDate time, boolean isEffected, String note){
        this.billId = 0;
        this.name.set(name);//filter
        this.customer.set(customer);//filter
        this.location.set(location);//filter
        this.isPaid.set(isPaid);
        this.price.set(price);//filter
        this.time.set(time);//filter
        this.effected.set(isEffected);
        this.setNote(note);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("    billId: "); sb.append(getBillId());sb.append(",\n");
        sb.append("    name: \""); sb.append(getName());sb.append("\",\n");
        sb.append("    customerId: "); sb.append(getCustomer().getCustomerId());sb.append(",\n");
        sb.append("    location: \""); sb.append(getLocation());sb.append("\",\n");
        sb.append("    isPaid: "); sb.append(getIsPaid());sb.append(",\n");
        sb.append("    time: \""); sb.append(getTime());sb.append("\",\n");
        sb.append("    effected: \""); sb.append(getIsEffected());sb.append("\",\n");
        sb.append("    note: \""); sb.append(getNote());sb.append("\"\n");
        sb.append('}');
        return sb.toString();
    }
}
