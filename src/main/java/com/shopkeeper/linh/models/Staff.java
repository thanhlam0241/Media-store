package com.shopkeeper.linh.models;


import com.shopkeeper.mediaone.models.IReferencedCounter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.security.InvalidParameterException;
import java.util.*;
import java.time.*;


public class Staff implements IReferencedCounter {

    private LocalDate latestPay;

    public LocalDate getLatestPay(){
        return latestPay;
    }

    public void setLatestPay(LocalDate latestPay) {
        this.latestPay = latestPay;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long id) throws InvalidParameterException {
        if(staffId > 0) throw new InvalidParameterException("staffId is able to be set only one time.");
        staffId = id;
    }
    public String getName() {
        return nameProperty.get();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void setName(String name) {
        this.nameProperty.set(name);
    }

    public boolean getIsMale() {
        return isMaleProperty.get();
    }

    public BooleanProperty isMaleProperty() {
        return isMaleProperty;
    }

    public void setIsMale(boolean isMale) {
        this.isMaleProperty.set(isMale);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirthProperty.get();
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirthProperty;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirthProperty.set(dateOfBirth);
    }

    public String getEmail() {
        return emailProperty.get();
    }

    public StringProperty emailProperty() {
        return emailProperty;
    }

    public void setEmail(String email) {
        this.emailProperty.set(email);
    }

    public String getPhoneNumber() {
        return phoneNumberProperty.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumberProperty;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty.set(phoneNumber);
    }

    public String getDescription() {
        return descriptionProperty.get();
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public void setDescription(String description) {
        this.descriptionProperty.set(description);
    }

    public StaffState getState() {
        return stateProperty.get();
    }

    public ObjectProperty<StaffState> stateProperty() {
        return stateProperty;
    }

    public void setState(StaffState state) {
        this.stateProperty.set(state);
    }

    private long staffId;
    private StringProperty nameProperty = new SimpleStringProperty();
    private BooleanProperty isMaleProperty = new SimpleBooleanProperty();
    private ObjectProperty<LocalDate> dateOfBirthProperty = new SimpleObjectProperty<LocalDate>();
    private StringProperty emailProperty = new SimpleStringProperty();
    private StringProperty phoneNumberProperty = new SimpleStringProperty();
    private StringProperty descriptionProperty = new SimpleStringProperty();
    private ObjectProperty<StaffState> stateProperty = new SimpleObjectProperty<StaffState>();

    public int getAge() {
        var d = Period.between(LocalDate.now(), getDateOfBirth());
        return d.getYears();
    }
    public Staff(){
        staffId = 0;
        latestPay = LocalDate.of(2000, 1, 1);
    }
    public Staff(String name, boolean isMale, LocalDate dateOfBirth, String email, String phoneNumber, String description, StaffState state){
        staffId = 0;
        setName(name);
        setIsMale(isMale);
        setDateOfBirth(dateOfBirth);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setDescription(description);
        setState(state);
        latestPay = LocalDate.of(2000, 1, 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("    staffId: \""); sb.append(getStaffId());sb.append("\",\n");
        sb.append("    name: \""); sb.append(getName());sb.append("\",\n");
        sb.append("    isMale: "); sb.append(getIsMale());sb.append(",\n");
        sb.append("    dateOfBirth: "); sb.append(getDateOfBirth());sb.append(",\n");
        sb.append("    email: \""); sb.append(getEmail());sb.append("\",\n");
        sb.append("    phoneNumber: \""); sb.append(getPhoneNumber());sb.append("\",\n");
        sb.append("    description: \""); sb.append(getDescription());sb.append("\",\n");
        sb.append("    state: "); sb.append(getState());sb.append("\n");
        sb.append('}');
        return sb.toString();
    }
    private int timesToBeReferenced = 0;
    @Override
    public int countTimesToBeReferenced() {
        return timesToBeReferenced;
    }

    @Override
    public void increaseTimesToBeReferenced() {
        timesToBeReferenced++;
    }

    @Override
    public void decreaseTimesToBeReferenced() throws Exception {
        if(timesToBeReferenced == 0) throw new Exception("cannot decreaseTimesToBeReferenced() when countTimesToBeReferenced() = 0");
        timesToBeReferenced--;
    }
}