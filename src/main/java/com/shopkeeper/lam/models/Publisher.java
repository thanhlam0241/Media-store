package com.shopkeeper.lam.models;

import com.shopkeeper.mediaone.models.IReferencedCounter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.security.InvalidParameterException;
import java.util.*;
import java.time.*;

public class Publisher implements IReferencedCounter {
    private String name;
    private String address;
    private int publisherId;
    private String description;

    public Publisher() {
        publisherId=0;
    }

    public Publisher(String name, String address, String description) {
        this.name = name;
        this.address = address;
        publisherId = 0;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) throws InvalidParameterException {
        if(this.publisherId > 0) throw new InvalidParameterException("publisherId is able to be set only one time.");
        this.publisherId = publisherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("    publisherId: \""); sb.append(getPublisherId());sb.append("\",\n");
        sb.append("    address: \""); sb.append(getName());sb.append("\",\n");
        sb.append("    isMale: "); sb.append(getAddress());sb.append(",\n");
        sb.append("    description: \""); sb.append(getDescription());sb.append("\",\n");
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
