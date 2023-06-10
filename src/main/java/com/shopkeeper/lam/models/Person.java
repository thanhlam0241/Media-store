package com.shopkeeper.lam.models;

import com.shopkeeper.mediaone.models.IReferencedCounter;
/*
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;*/

import java.security.InvalidParameterException;
import java.util.*;
import java.time.*;
public class Person implements IReferencedCounter {
    private String name;
    private LocalDate dateOfBirth;
    private String description;
    private int personId;
    private JobOfPerson job;
    public Person(){personId=0; }

    public Person(String name, LocalDate dateOfBirth, String description, JobOfPerson job) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        personId = 0;
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPersonId(int personId) throws InvalidParameterException {
        if(this.personId > 0) throw new InvalidParameterException("personId is able to be set only one time.");
        this.personId = personId;
    }

    public int getPersonId() {
        return personId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setJob(JobOfPerson job) {
        this.job = job;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public JobOfPerson getJob() {
        return job;
    }
    public int getAge(){
        LocalDate local  = LocalDate.now();

        return local.getYear()-getDateOfBirth().getYear();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("personId: \""); sb.append(getPersonId());sb.append("\",\n");
        sb.append("name: \""); sb.append(getName());sb.append("\",\n");
        sb.append("dateOfBirth: "); sb.append(getDateOfBirth().toString());sb.append(",\n");
        sb.append("description: \""); sb.append(getDescription());sb.append("\",\n");
        sb.append("job: "); sb.append(getJob().toString());sb.append("\n");
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
