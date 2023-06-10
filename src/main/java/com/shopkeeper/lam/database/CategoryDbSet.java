package com.shopkeeper.lam.database;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;
import com.shopkeeper.lam.models.*;
import java.sql.*;
public class CategoryDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Category> list;
    public CategoryDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Category> categories){
        this.conn = conn;
        this.list = categories;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE categories (");
        sqlBuilder.append("categoryId     INTEGER   PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("name           TEXT      NOT NULL,");
        sqlBuilder.append("description    TEXT      NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_categories_categoryId ON categories(categoryId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception {
        String sql = "SELECT categoryId, name, description FROM categories";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        Category category;
        while (rs.next()) {
            category = new Category();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));

            list.add(category);
        }

        rs.close();
        stmt.close();
    }


    public boolean insert(Category category) {
        if(category.getCategoryId() != 0) return false;
        String sql = "INSERT INTO categories(name, description) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());

            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Creating category failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setCategoryId(generatedKeys.getInt(1));
            } else throw new Exception("Creating category failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.add(category);
        return true;
    }

    //Return true if success, otherwise return false
    public boolean update(Category category) {
        if(!list.contains(category))
        {
            System.err.println("category is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE categories SET name=?,description=? WHERE categoryId=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setInt(3, category.getCategoryId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("Category (ID = " + category.getCategoryId() + ") does not exist in \"categories\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    //Return true if success, otherwise return false
    public boolean deleteCategory(Category category) {
        if(category.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this category.");
            return false;
        }
        int index = list.indexOf(category);
        if(index < 0){
            System.err.println("category is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM categories WHERE categoryId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, category.getCategoryId());
            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Category (ID = " + category.getCategoryId() + ") does not exist in \"categories\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        list.remove(index, index + 1);
        return true;
    }
}
