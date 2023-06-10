package com.shopkeeper.linh.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Settings {
    public DoubleProperty standardSalaryPerHourProperty() {
        return standardSalaryPerHourProperty;
    }

    public IntegerProperty clearingFeedbackDurationProperty() {
        return clearingFeedbackDurationProperty;
    }

    private DoubleProperty standardSalaryPerHourProperty = new SimpleDoubleProperty();
    private IntegerProperty clearingFeedbackDurationProperty = new SimpleIntegerProperty();

    public double getStandardSalaryPerHour() {
        return standardSalaryPerHourProperty.get();
    }

    public int getClearingFeedbackDuration() {
        return clearingFeedbackDurationProperty.get();
    }

    public Settings setStandardSalaryPerHour(double standardSalaryPerHour) {
        this.standardSalaryPerHourProperty.set(standardSalaryPerHour);
        return this;
    }

    public Settings setClearingFeedbackDuration(int clearingFeedbackDuration) {
        this.clearingFeedbackDurationProperty.set(clearingFeedbackDuration);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('{'); sb.append('\n');
        sb.append("    standardSalaryPerHour: "); sb.append(getStandardSalaryPerHour());sb.append(" (VND),\n");
        sb.append("    clearingFeedbackDuration: "); sb.append(getClearingFeedbackDuration());sb.append(" (days)\n");
        sb.append('}');
        return sb.toString();
    }
}
