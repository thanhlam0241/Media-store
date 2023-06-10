package com.shopkeeper.linh.database;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Customer> list;
    public CustomerDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Customer> customers){
        this.conn = conn;
        this.list = customers;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE customers (");
        sqlBuilder.append("customerId  INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("name               TEXT      NOT NULL,");
        sqlBuilder.append("defaultLocation    TEXT      NOT NULL,");
        sqlBuilder.append("phoneNumber        TEXT      NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_customers_customerId ON customers(customerId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT customerId, name, defaultLocation, phoneNumber FROM customers";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Customer customer;
        while (rs.next()) {
            customer = new Customer();
            customer.setCustomerId(rs.getLong("customerId"));
            customer.setName(rs.getString("name"));
            customer.setDefaultLocation(rs.getString("defaultLocation"));
            customer.setPhoneNumber(rs.getString("phoneNumber"));
            list.add(customer);
        }

        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(Customer customer) {
        if(customer.getCustomerId() != 0) return false;
        String sql = "INSERT INTO customers(name, defaultLocation, phoneNumber) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getDefaultLocation());
            pstmt.setString(3, customer.getPhoneNumber());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating customer failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setCustomerId(generatedKeys.getLong(1));
            }
            else throw new Exception("Creating customer failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Sucess
        list.add(customer);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Customer customer) {
        if(!list.contains(customer))
        {
            System.err.println("customer is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE customers SET name=?,defaultLocation=?,phoneNumber=? WHERE customerId=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getDefaultLocation());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setLong(4, customer.getCustomerId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Customer (ID = " + customer.getCustomerId() + ") does not exist in \"customers\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Sucess

        return true;
    }
    //Return true if success, otherwise return false
    public boolean deleteCustomer(Customer customer) {
        if(customer.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this customer.");
            return false;
        }
        int index = list.indexOf(customer);
        if(index < 0){
            System.err.println("customer is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM customers WHERE customerId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, customer.getCustomerId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Customer (ID = " + customer.getCustomerId() + ") does not exist in \"customers\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        list.remove(index, index + 1);
        return true;
    }
}
