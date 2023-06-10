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
import java.util.List;

public class MusicInfoDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<MusicInfo> list;
    public MusicInfoDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<MusicInfo> musicInfos){
        this.conn = conn;
        this.list = musicInfos;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE musicInfos (");
        sqlBuilder.append("productInfoId     INTEGER  PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("title             TEXT     NOT NULL,");
        sqlBuilder.append("description       TEXT     NOT NULL,");
        sqlBuilder.append("categoryId        INTEGER  NOT NULL,");
        sqlBuilder.append("releaseDate       TEXT     NOT NULL,");
        sqlBuilder.append("currentSalePrice  DOUBLE   NOT NULL,");
        sqlBuilder.append("publisherId       INTEGER  NOT NULL,");
        sqlBuilder.append("rating            DOUBLE   NOT NULL,");
        sqlBuilder.append("awards             TEXT     NOT NULL,");
        sqlBuilder.append("musiciansId       TEXT     NOT NULL,");
        sqlBuilder.append("timeLimit         TEXT     NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_musicInfos_productInfoId ON musicInfos(productInfoId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception {
        String sql = "SELECT productInfoId, title, description,categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,musiciansId,timeLimit FROM musicInfos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        MusicInfo productInfo;
        ArrayList<Integer> musiciansId = new ArrayList<>();
        int publisherId,categoryId;
        Person[] musicians;
        int index;
        while (rs.next()) {
            productInfo = new MusicInfo();
            productInfo.setNumberOfProduct(0);
            productInfo.setProductInfoId(rs.getInt("productInfoId"));
            productInfo.setTitle(rs.getString("title"));
            productInfo.setDescription(rs.getString("description"));
            productInfo.setReleaseDate(LocalDate.parse(rs.getString("releaseDate")));
            productInfo.setCurrentSalePrice(rs.getDouble("currentSalePrice"));
            productInfo.setRating(rs.getDouble("rating"));
            productInfo.setTimeLimit(LocalTime.parse(rs.getString("timeLimit")));

            //gan cho musicians
            musiciansId.clear();
            for (String idString: rs.getString("musiciansId").split("_")){
                musiciansId.add(Integer.parseInt(idString));
            }
            int count = musiciansId.size();
            musicians = new Person[count];

            for (Person person: readOnlyCache.getPeople()){
                index = musiciansId.indexOf(person.getPersonId());
                if (index > -1){
                    musicians[index] = person;
                    person.increaseTimesToBeReferenced();
                    count--;
                    if(count == 0) break;
                }
            }
            productInfo.setMusicians(new ArrayList<>(Arrays.asList(musicians)));

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

    public boolean insert(MusicInfo musicInfo) {
        if(musicInfo.getProductInfoId() != 0) return false;
        String sql = "INSERT INTO musicInfos(title, description, categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,musiciansId,timeLimit ) VALUES(?,?,?,DATE(?),?,?,?,?,?,?)";
        StringBuilder stringBuilder = new StringBuilder();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, musicInfo.getTitle());
            pstmt.setString(2, musicInfo.getDescription());
            pstmt.setInt(3,musicInfo.getCategory().getCategoryId());
            pstmt.setString(4, musicInfo.getReleaseDate().toString());
            pstmt.setDouble(5,musicInfo.getCurrentSalePrice());
            pstmt.setInt(6, musicInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, musicInfo.getRating());
            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : musicInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //musician
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : musicInfo.getMusicians()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(9, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _


            pstmt.setString(10, musicInfo.getTimeLimit().toString());

            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Creating musicInfo failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                musicInfo.setProductInfoId(generatedKeys.getInt(1));
            } else throw new Exception("Creating musicInfo failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        musicInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : musicInfo.getMusicians()){
            m.increaseTimesToBeReferenced();
        }
        musicInfo.getCategory().increaseTimesToBeReferenced();
        list.add(musicInfo);
        return true;
    }
    public boolean update(MusicInfo musicInfo) {
        for (var x : musicInfo.getMusicians()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in musicInfo.getMusicians() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(musicInfo.getCategory())){
            System.err.println("Category which is output of musicInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(musicInfo.getPublisher())){
            System.err.println("Publisher which is output of musicInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }
        if(!list.contains(musicInfo))
        {
            System.err.println("musicInfo is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE musicInfos SET title=?, description=?, categoryId=?,releaseDate=DATE(?),currentSalePrice=?,publisherId=?,rating=?,awards=?,musiciansId=?,timeLimit=?  WHERE productInfoId=?";
        StringBuilder stringBuilder = new StringBuilder();
        int oldPublisherId = 0, oldCategoryId = 0;
        ArrayList<Integer> oldMusiciansId = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt = conn.createStatement()) {
            //Get old value
            ResultSet rs = stmt.executeQuery("SELECT categoryId, publisherId, musiciansId FROM musicInfos WHERE productInfoId=" + musicInfo.getProductInfoId());
            if(rs.next()){
                oldCategoryId = rs.getInt(1);
                oldPublisherId = rs.getInt(2);
                for (String idString: rs.getString("musiciansId").split("_")){
                    oldMusiciansId.add(Integer.parseInt(idString));
                }
            }
            //Update
            pstmt.setString(1, musicInfo.getTitle());
            pstmt.setString(2, musicInfo.getDescription());
            pstmt.setInt(3,musicInfo.getCategory().getCategoryId());
            pstmt.setString(4, musicInfo.getReleaseDate().toString());
            pstmt.setDouble(5,musicInfo.getCurrentSalePrice());
            pstmt.setInt(6, musicInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, musicInfo.getRating());
            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : musicInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //musician
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : musicInfo.getMusicians()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(9, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _

            pstmt.setString(10, musicInfo.getTimeLimit().toString());
            pstmt.setInt(11,musicInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("musicInfo (ID = " + musicInfo.getProductInfoId() + ") does not exist in \"musicInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        //Decrease reference count

        //musicians
        int count = oldMusiciansId.size();
        for (Person person: readOnlyCache.getPeople()){
            if (oldMusiciansId.contains(person.getPersonId())){
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
        musicInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : musicInfo.getMusicians()){
            m.increaseTimesToBeReferenced();
        }
        musicInfo.getCategory().increaseTimesToBeReferenced();
        return true;
    }
    public boolean delete(MusicInfo musicInfo) {
        if(musicInfo.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this musicInfo.");
            return false;
        }
        for (var x : musicInfo.getMusicians()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in musicInfo.getMusicians() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(musicInfo.getCategory())){
            System.err.println("Category which is output of musicInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(musicInfo.getPublisher())){
            System.err.println("Publisher which is output of musicInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }

        int index = list.indexOf(musicInfo);
        if(index < 0){
            System.err.println("musicInfo is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM musicInfos WHERE productInfoId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, musicInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("MusicInfo (ID = " + musicInfo.getProductInfoId() + ") does not exist in \"musicInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        try {
            musicInfo.getPublisher().decreaseTimesToBeReferenced();
            for(var m : musicInfo.getMusicians()){
                m.decreaseTimesToBeReferenced();
            }
            musicInfo.getCategory().decreaseTimesToBeReferenced();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
