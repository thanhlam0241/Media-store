package com.shopkeeper.lam.database;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;
import com.shopkeeper.lam.models.*;
import java.sql.*;
public class PublisherDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Publisher> list;
    public PublisherDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Publisher> publishers){
        this.conn = conn;
        this.list = publishers;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE publishers (");
        sqlBuilder.append(  "publisherId    INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append(  "name           TEXT    NOT NULL,");
        sqlBuilder.append(  "address        TEXT    NOT NULL,");
        sqlBuilder.append(  "description    TEXT    NOT NULL");
        sqlBuilder.append(  ");");

        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_publishers_publisherId ON publishers(publisherId );");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT publisherId, name, address, description  FROM publishers";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Publisher publisher;
        while (rs.next()) {
            publisher = new Publisher();
            publisher.setPublisherId(rs.getInt("publisherId"));
            publisher.setName(rs.getString("name"));
            publisher.setAddress(rs.getString("address"));
            publisher.setDescription(rs.getString("description"));
            list.add(publisher);
        }

        rs.close();
        stmt.close();
    }

    public boolean insert(Publisher publisher) {
        if(publisher.getPublisherId() != 0) return false;
        String sql = "INSERT INTO publishers(name, address, description) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, publisher.getName());
            pstmt.setString(2, publisher.getAddress());
            pstmt.setString(3, publisher.getDescription());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating publisher failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                publisher.setPublisherId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating publisher failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.add(publisher);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Publisher publisher) {
        if(!list.contains(publisher))
        {
            System.err.println("publisher is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE publishers SET name=?,address=?,description=? WHERE publisherId=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, publisher.getName());
            pstmt.setString(2, publisher.getAddress());
            pstmt.setString(3, publisher.getDescription());
            pstmt.setInt(4, publisher.getPublisherId());

            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Publisher (ID = " + publisher.getPublisherId() + ") does not exist in \"publishers\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    //Return true if success, otherwise return false
    public boolean deletePublisher(Publisher publisher) {
        if(publisher.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this publisher.");
            return false;
        }
        int index = list.indexOf(publisher);
        if(index < 0){
            System.err.println("publisher is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM publishers WHERE publisherId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, publisher.getPublisherId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Publisher (ID = " + publisher.getPublisherId() + ") does not exist in \"publishers\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }

}
