package com.shopkeeper.lam.database;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;
import com.shopkeeper.lam.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class BookInfoDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<BookInfo> list;
    public BookInfoDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<BookInfo> bookInfos){
        this.conn = conn;
        this.list = bookInfos;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE bookInfos (");
        sqlBuilder.append("productInfoId     INTEGER  PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("title             TEXT     NOT NULL,");
        sqlBuilder.append("description       TEXT     NOT NULL,");
        sqlBuilder.append("categoryId        INTEGER  NOT NULL,");
        sqlBuilder.append("releaseDate       TEXT     NOT NULL,");
        sqlBuilder.append("currentSalePrice  DOUBLE   NOT NULL,");
        sqlBuilder.append("publisherId       INTEGER  NOT NULL,");
        sqlBuilder.append("rating            DOUBLE   NOT NULL,");
        sqlBuilder.append("awards             TEXT     NOT NULL,");
        sqlBuilder.append("authorsId         TEXT     NOT NULL,");
        sqlBuilder.append("numberOfPage      TEXT     NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_bookInfos_productInfoId ON bookInfos(productInfoId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception {
        String sql = "SELECT productInfoId, title, description,categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,authorsId,numberOfPage FROM bookInfos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        BookInfo productInfo = null;
        ArrayList<Integer> authorsId = new ArrayList<>();
        int publisherId,categoryId;
        Person[] authors;
        int index;
        while (rs.next()) {
            productInfo = new BookInfo();
            productInfo.setNumberOfProduct(0);
            productInfo.setProductInfoId(rs.getInt("productInfoId"));
            productInfo.setTitle(rs.getString("title"));
            productInfo.setDescription(rs.getString("description"));
            productInfo.setReleaseDate(LocalDate.parse(rs.getString("releaseDate")));
            productInfo.setCurrentSalePrice(rs.getDouble("currentSalePrice"));
            productInfo.setRating(rs.getDouble("rating"));
            productInfo.setNumberOfPage(rs.getInt("numberOfPage"));

            //gan cho authors
            authorsId.clear();
            for (String idString: rs.getString("authorsId").split("_")){
                authorsId.add(Integer.parseInt(idString));
            }
            int count = authorsId.size();
            authors = new Person[count];

            for (Person person: readOnlyCache.getPeople()){
                index = authorsId.indexOf(person.getPersonId());
                if (index > -1){
                    authors[index] = person;
                    person.increaseTimesToBeReferenced();
                    count--;
                    if(count == 0) break;
                }
            }
            productInfo.setAuthors(new ArrayList<>(Arrays.asList(authors)));

            //gan cho publisher
            publisherId = rs.getInt("publisherId");
            for (var p: readOnlyCache.getPublishers()){
                if (p.getPublisherId() == publisherId){
                    productInfo.setPublisher(p);
                    p.increaseTimesToBeReferenced();
                    break;
                }
            }

            //gan cho category
            categoryId = rs.getInt("categoryId");
            for (var c: readOnlyCache.getCategories()){
                if (c.getCategoryId() == categoryId){
                    productInfo.setCategory(c);
                    c.increaseTimesToBeReferenced();
                    break;
                }
            }

            //gan cho awards
            productInfo.setAward(new ArrayList<>(Arrays.asList(rs.getString("awards").split("_"))));

            list.add(productInfo);
        }
        rs.close();
        stmt.close();
    }

    public boolean insert(BookInfo bookInfo) {
        if(bookInfo.getProductInfoId() != 0) return false;
        String sql = "INSERT INTO bookInfos(title, description, categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,authorsId,numberOfPage ) VALUES(?,?,?,DATE(?),?,?,?,?,?,?)";
        StringBuilder stringBuilder = new StringBuilder();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bookInfo.getTitle());
            pstmt.setString(2, bookInfo.getDescription());
            pstmt.setInt(3,bookInfo.getCategory().getCategoryId());
            pstmt.setString(4, bookInfo.getReleaseDate().toString());
            pstmt.setDouble(5,bookInfo.getCurrentSalePrice());
            pstmt.setInt(6, bookInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, bookInfo.getRating());

            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : bookInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //authors
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : bookInfo.getAuthors()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(9, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _


            pstmt.setInt(10, bookInfo.getNumberOfPage());
            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Creating bookInfo failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookInfo.setProductInfoId(generatedKeys.getInt(1));
            } else throw new Exception("Creating bookInfo failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        bookInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : bookInfo.getAuthors()){
            m.increaseTimesToBeReferenced();
        }
        bookInfo.getCategory().increaseTimesToBeReferenced();
        list.add(bookInfo);
        return true;
    }
    public boolean update(BookInfo bookInfo) {
        for (var x : bookInfo.getAuthors()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in bookInfo.getAuthors() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(bookInfo.getCategory())){
            System.err.println("Category which is output of bookInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(bookInfo.getPublisher())){
            System.err.println("Publisher which is output of bookInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }
        if(!list.contains(bookInfo))
        {
            System.err.println("bookInfo is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE bookInfos SET title=?, description=?, categoryId=?,releaseDate=DATE(?),currentSalePrice=?,publisherId=?,rating=?,awards=?,authorsId=?,numberOfPage=?  WHERE productInfoId=?";
        StringBuilder stringBuilder = new StringBuilder();
        int oldPublisherId = 0, oldCategoryId = 0;
        ArrayList<Integer> oldAuthorsId = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {
            //Get old value
            ResultSet rs = stmt.executeQuery("SELECT categoryId, publisherId, authorsId FROM bookInfos WHERE productInfoId=" + bookInfo.getProductInfoId());
            if(rs.next()){
                oldCategoryId = rs.getInt(1);
                oldPublisherId = rs.getInt(2);
                for (String idString: rs.getString(3).split("_")){
                    oldAuthorsId.add(Integer.parseInt(idString));
                }
            }
            //Update
            pstmt.setString(1, bookInfo.getTitle());
            pstmt.setString(2, bookInfo.getDescription());
            pstmt.setInt(3,bookInfo.getCategory().getCategoryId());
            pstmt.setString(4, bookInfo.getReleaseDate().toString());
            pstmt.setDouble(5,bookInfo.getCurrentSalePrice());
            pstmt.setInt(6, bookInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, bookInfo.getRating());
            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : bookInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //authors
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : bookInfo.getAuthors()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(9, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _


            pstmt.setInt(10, bookInfo.getNumberOfPage());

            pstmt.setInt(11,bookInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("bookInfo (ID = " + bookInfo.getProductInfoId() + ") does not exist in \"bookInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        //Decrease reference count

        //authors
        int count = oldAuthorsId.size();
        for (Person person: readOnlyCache.getPeople()){
            if (oldAuthorsId.contains(person.getPersonId())){
                try{person.decreaseTimesToBeReferenced();}
                catch (Exception e){
                    System.err.println(e.getMessage());
                    return false;
                }
                count--;
                if(count == 0) break;
            }
        }

        //publisher
        for (var p: readOnlyCache.getPublishers()){
            if (p.getPublisherId() == oldPublisherId){
                try{p.decreaseTimesToBeReferenced();}
                catch (Exception e){
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            }
        }

        //category
        for (var c: readOnlyCache.getCategories()){
            if (c.getCategoryId() == oldCategoryId){
                try{c.decreaseTimesToBeReferenced();}
                catch (Exception e){
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            }
        }
        //Increase reference count
        bookInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : bookInfo.getAuthors()){
            m.increaseTimesToBeReferenced();
        }
        bookInfo.getCategory().increaseTimesToBeReferenced();
        return true;
    }
    public boolean delete(BookInfo bookInfo) {
        if(bookInfo.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this bookInfo.");
            return false;
        }
        for (var x : bookInfo.getAuthors()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in bookInfo.getAuthors() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(bookInfo.getCategory())){
            System.err.println("Category which is output of bookInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(bookInfo.getPublisher())){
            System.err.println("Publisher which is output of bookInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }

        int index = list.indexOf(bookInfo);
        if(index < 0){
            System.err.println("bookInfo is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM bookInfos WHERE productInfoId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("bookInfo (ID = " + bookInfo.getProductInfoId() + ") does not exist in \"bookInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        try {
            bookInfo.getPublisher().decreaseTimesToBeReferenced();
            for(var m : bookInfo.getAuthors()){
                m.decreaseTimesToBeReferenced();
            }
            bookInfo.getCategory().decreaseTimesToBeReferenced();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
