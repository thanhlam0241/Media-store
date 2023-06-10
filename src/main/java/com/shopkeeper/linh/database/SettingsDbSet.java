package com.shopkeeper.linh.database;

import com.shopkeeper.linh.models.Settings;
import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private Settings item;
    public SettingsDbSet(Connection conn, ReadOnlyDbAdapterCache cache, Settings settings){
        this.conn = conn;
        this.item = settings;
        this.readOnlyCache = cache;
    }
    //Return true if success, otherwise return false
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE settings(");
        sqlBuilder.append("    name         TEXT PRIMARY KEY NOT NULL UNIQUE,");
        sqlBuilder.append("    value        TEXT      NOT NULL,");
        sqlBuilder.append("    defaultValue TEXT      NOT NULL");
        sqlBuilder.append(");");
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sqlBuilder.toString());
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_settings_name ON settings(name);");
            //Initialize settings
            stmt.execute("INSERT INTO settings(name, value, defaultValue) VALUES('standardSalaryPerHour','0','0');");
            stmt.execute("INSERT INTO settings(name, value, defaultValue) VALUES('clearingFeedbackDuration','30','30');");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
    private boolean isStandardSalaryPerHourChanged;
    private boolean isClearingFeedbackDurationChanged;
    public void load() throws Exception{
        String sql = "SELECT name, value FROM settings";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Settings settings = item;
        // loop through the result set
        while (rs.next()) {
            switch (rs.getString("name")){
                case "standardSalaryPerHour":
                    settings.setStandardSalaryPerHour(rs.getDouble("value"));
                    break;
                case "clearingFeedbackDuration":
                    settings.setClearingFeedbackDuration(rs.getInt("value"));
                    break;
            }
        }
        stmt.close();
        rs.close();
        isStandardSalaryPerHourChanged = false;
        isClearingFeedbackDurationChanged = false;
        settings.standardSalaryPerHourProperty().addListener(((observable, oldValue, newValue) -> {
            isStandardSalaryPerHourChanged = true;
        }));
        settings.clearingFeedbackDurationProperty().addListener(((observable, oldValue, newValue) -> {
            isClearingFeedbackDurationChanged = true;
        }));
    }

    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean update(Settings settings) {
        if(item != settings){
            System.err.println("settings is not in DbAdapter's cache");
            return false;
        }
        try (Statement stmt = conn.createStatement()) {
            if(isStandardSalaryPerHourChanged){
                int affected = stmt.executeUpdate(
                        "UPDATE settings SET value='" + settings.getStandardSalaryPerHour() + "' WHERE name='standardSalaryPerHour';");
                if(affected == 0) throw new Exception("Updating settings' property (standardSalaryPerHour) failed.");
                //System.out.println("Updating settings' property (standardSalaryPerHour) successful.");
            }
            if(isClearingFeedbackDurationChanged){
                int affected = stmt.executeUpdate(
                        "UPDATE settings SET value='" + settings.getClearingFeedbackDuration() + "' WHERE name='clearingFeedbackDuration';");
                if(affected == 0) throw new Exception("Updating settings' property (clearingFeedbackDuration) failed.");
                //System.out.println("Updating settings' property (clearingFeedbackDuration) successful.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        isStandardSalaryPerHourChanged = false;
        isClearingFeedbackDurationChanged = false;
        return true;
    }
    //Return true if success, otherwise return false
    public boolean reset(Settings settings) {
        if(item != settings){
            System.err.println("settings is not in DbAdapter's cache");
            return false;
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name, defaultValue FROM settings");
            while (rs.next()) {
                switch (rs.getString("name")){
                    case "standardSalaryPerHour":
                        settings.setStandardSalaryPerHour(rs.getDouble("defaultValue"));
                        break;
                    case "clearingFeedbackDuration":
                        settings.setClearingFeedbackDuration(rs.getInt("defaultValue"));
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        return update(settings);
    }
}
