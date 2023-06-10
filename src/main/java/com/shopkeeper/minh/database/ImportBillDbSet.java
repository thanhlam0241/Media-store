package com.shopkeeper.minh.database;

import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import com.shopkeeper.minh.models.ImportBill;

import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class ImportBillDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<ImportBill> list;

    public ImportBillDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<ImportBill> importBills){
        this.conn = conn;
        this.list = importBills;
        this.readOnlyCache = cache;
    }

    public boolean createTable(){
        String sql = "CREATE TABLE importbills ("
                +  "billId      INTEGER PRIMARY KEY AUTOINCREMENT,"
                +  "name        TEXT      NOT NULL,"
                +  "distributor TEXT      NOT NULL,"
                +  "price       DOUBLE    NOT NULL,"
                +  "time        DATETIME  NOT NULL,"
                +  "effected    BOOLEAN   NOT NULL,"
                +  "note        TEXT      NOT NULL"
                +  ");";

        try (Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
            // Indices
            stmt.execute("CREATE UNIQUE INDEX idx_importbills_billId ON importbills(billId);");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception{
        String sql = "SELECT billId, name, distributor, price, time, effected, note FROM importbills;";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        ImportBill bill;

        while (rs.next()) {
            bill = new ImportBill();
            bill.setBillId(rs.getInt("billId"));
            bill.setName(rs.getString("name"));
            bill.setDistributor(rs.getString("distributor"));
            bill.setPrice(rs.getDouble("price"));
            bill.setTime(LocalDate.parse(rs.getString("time")));
            bill.setIsEffected(rs.getBoolean("effected"));
            bill.setNote(rs.getString("note"));
            list.add(bill);
        }

        rs.close();
        stmt.close();
    }

    public boolean insert(ImportBill bill) {
        if(bill.getBillId() != 0) return false;
        String sql = "INSERT INTO importbills(name, distributor, price, time, effected, note) VALUES(?,?,?,DATE(?),?,?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bill.getName());
            pstmt.setString(2, bill.getDistributor());
            pstmt.setDouble(3, bill.getPrice());
            pstmt.setString(4, bill.getTime().toString());
            pstmt.setBoolean(5, bill.getIsEffected());
            pstmt.setString(6, bill.getNote());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating importbill failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating importbill failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.add(bill);
        return true;
    }

    public boolean update(ImportBill bill) {
        if (!list.contains(bill)){
            System.err.println("ImportBill is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE importbills SET name=?, distributor=?, price=?, time=DATE(?), effected=?, note=? WHERE billId=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getName());
            pstmt.setString(2, bill.getDistributor());
            pstmt.setDouble(3, bill.getPrice());
            pstmt.setString(4, bill.getTime().toString());
            pstmt.setBoolean(5, bill.getIsEffected());
            pstmt.setString(6, bill.getNote());
            pstmt.setInt(7, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("ImportBill (ID = " + bill.getBillId() + ") does not exist in \"importbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(ImportBill bill) {
        int index = list.indexOf(bill);
        if(index < 0){
            System.err.println("ImportBill is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM importbills WHERE billId = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, bill.getBillId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("ImportBill (ID = " + bill.getBillId() + ") does not exist in \"importbills\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
