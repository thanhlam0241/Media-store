package com.shopkeeper.minh.database;

import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import com.shopkeeper.minh.models.OtherBill;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class OtherBillDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<OtherBill> list;

    public OtherBillDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<OtherBill> otherBills){
        this.conn = conn;
        this.list = otherBills;
        this.readOnlyCache = cache;
    }

    public boolean createTable(){
        String sql = "CREATE TABLE otherbills ("
                +  "billId      INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "price       DOUBLE    NOT NULL,"
                +  "time        DATETIME  NOT NULL,"
                +  "effected    BOOLEAN   NOT NULL,"
                +  "note        TEXT      NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
            stmt.execute("CREATE UNIQUE INDEX idx_otherbills_billId ON otherbills(billId);");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception{
        String sql = "SELECT billId, name, price, time, effected, note FROM otherbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        OtherBill bill;

        while (rs.next()) {
            bill = new OtherBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));

            list.add(bill);
        }

        rs.close();
        stmt.close();
    }

    public boolean insert(OtherBill bill) {
        if(bill.getBillId() != 0) return false;
        String sql = "INSERT INTO otherbills(name, price, time, effected, note) VALUES(?,?,DATE(?),?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating otherbill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating otherbill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.add(bill);
        return true;
    }

    public boolean update(OtherBill bill) {
        if (!list.contains(bill)){
            System.err.println("OtherBill is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE otherbills SET name=?, price=?, time=DATE(?), effected=?, note=? WHERE billId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getName());
            pstmt.setDouble(2, bill.getPrice());
            pstmt.setString(3, bill.getTime().toString());
            pstmt.setBoolean(4, bill.getIsEffected());
            pstmt.setString(5, bill.getNote());
            pstmt.setInt(6, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("OtherBill (ID = " + bill.getBillId() + ") does not exist in \"otherbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(OtherBill bill) {
        int index = list.indexOf(bill);
        if(index < 0){
            System.err.println("OtherBill is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM otherbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("OtherBill (ID = " + bill.getBillId() + ") does not exist in \"otherbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
