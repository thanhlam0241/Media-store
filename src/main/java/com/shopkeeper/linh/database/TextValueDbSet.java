package com.shopkeeper.linh.database;

import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;

public class TextValueDbSet {
    private Connection conn;
    public TextValueDbSet(Connection conn){
        this.conn = conn;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE textvalues(");
        sqlBuilder.append("    key    TEXT PRIMARY KEY NOT NULL UNIQUE,");
        sqlBuilder.append("    value  TEXT      NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_textvalues_key ON textvalues(key);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean set(String key, String value) {
        if(key == null) return false;
        if(value == null) value = "";
//        if(value == null || value.length() == 0){
//            String sql = "delete from textvalues where [key]=?;";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, key);
//                pstmt.executeUpdate();
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//                return false;
//            }
//            return true;
//        }
//        else {
        String sql = "insert or replace into textvalues([key], value) values (?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.setString(2, value);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
        //}
    }
    //Return true if success, otherwise return false
    public String get(String key) {
        String sql = "SELECT value FROM textvalues WHERE [key]=?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, key);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return "";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
