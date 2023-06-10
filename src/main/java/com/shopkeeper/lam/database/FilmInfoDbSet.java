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

public class FilmInfoDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<FilmInfo> list;
    public FilmInfoDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<FilmInfo> filmInfos){
        this.conn = conn;
        this.list = filmInfos;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE filmInfos (");
        sqlBuilder.append("productInfoId     INTEGER  PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("title             TEXT     NOT NULL,");
        sqlBuilder.append("description       TEXT     NOT NULL,");
        sqlBuilder.append("categoryId        INTEGER  NOT NULL,");
        sqlBuilder.append("releaseDate       TEXT     NOT NULL,");
        sqlBuilder.append("currentSalePrice  DOUBLE   NOT NULL,");
        sqlBuilder.append("publisherId       INTEGER  NOT NULL,");
        sqlBuilder.append("rating            DOUBLE   NOT NULL,");
        sqlBuilder.append("awards             TEXT     NOT NULL,");
        sqlBuilder.append("directorId        TEXT     NOT NULL,");
        sqlBuilder.append("actorsId          TEXT     NOT NULL,");
        sqlBuilder.append("timeLimit         TEXT     NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_filmInfos_productInfoId ON filmInfos(productInfoId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception {
        String sql = "SELECT productInfoId, title, description,categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,directorId,actorsId,timeLimit FROM filmInfos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        FilmInfo productInfo = null;
        ArrayList<Integer> actorsId = new ArrayList<>();
        int publisherId,categoryId,directorId;
        Person[] actors;
        int index;
        while (rs.next()) {
            productInfo = new FilmInfo();
            productInfo.setNumberOfProduct(0);
            productInfo.setProductInfoId(rs.getInt("productInfoId"));
            productInfo.setTitle(rs.getString("title"));
            productInfo.setDescription(rs.getString("description"));
            productInfo.setReleaseDate(LocalDate.parse(rs.getString("releaseDate")));
            productInfo.setCurrentSalePrice(rs.getDouble("currentSalePrice"));
            productInfo.setRating(rs.getDouble("rating"));
            productInfo.setTimeLimit(LocalTime.parse(rs.getString("timeLimit")));
            //gan cho actors
            actorsId.clear();
            for (String idString: rs.getString("actorsId").split("_")){
                actorsId.add(Integer.parseInt(idString));
            }
            int count = actorsId.size();
            actors = new Person[count];

            for (Person person: readOnlyCache.getPeople()){
                index = actorsId.indexOf(person.getPersonId());
                if (index > -1){
                    actors[index] = person;
                    person.increaseTimesToBeReferenced();
                    count--;
                    if(count == 0) break;
                }
            }
            productInfo.setActors(new ArrayList<>(Arrays.asList(actors)));
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
            //gan cho director
            directorId = rs.getInt("directorId");
            for (var p: readOnlyCache.getPeople()){
                if (p.getPersonId() == directorId){
                    productInfo.setDirector(p);
                    p.increaseTimesToBeReferenced();
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

    public boolean insert(FilmInfo filmInfo) {
        if(filmInfo.getProductInfoId() != 0) return false;
        String sql = "INSERT INTO filmInfos(title, description, categoryId,releaseDate,currentSalePrice,publisherId,rating,awards,directorId,actorsId,timeLimit ) VALUES(?,?,?,DATE(?),?,?,?,?,?,?,?)";
        StringBuilder stringBuilder = new StringBuilder();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, filmInfo.getTitle());
            pstmt.setString(2, filmInfo.getDescription());
            pstmt.setInt(3,filmInfo.getCategory().getCategoryId());
            pstmt.setString(4, filmInfo.getReleaseDate().toString());
            pstmt.setDouble(5,filmInfo.getCurrentSalePrice());
            pstmt.setInt(6, filmInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, filmInfo.getRating());
            
            pstmt.setInt(9, filmInfo.getDirector().getPersonId());
            
            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : filmInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //actors
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : filmInfo.getActors()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(10, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _
            
            
            
            pstmt.setString(11, filmInfo.getTimeLimit().toString());

            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Creating filmInfo failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                filmInfo.setProductInfoId(generatedKeys.getInt(1));
            } else throw new Exception("Creating filmInfo failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        filmInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : filmInfo.getActors()){
            m.increaseTimesToBeReferenced();
        }
        filmInfo.getCategory().increaseTimesToBeReferenced();
        filmInfo.getDirector().increaseTimesToBeReferenced();
        list.add(filmInfo);
        return true;
    }
    public boolean update(FilmInfo filmInfo) {
        for (var x : filmInfo.getActors()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in filmInfo.getActors() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(filmInfo.getCategory())){
            System.err.println("Category which is output of filmInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(filmInfo.getPublisher())){
            System.err.println("Publisher which is output of filmInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPeople().contains(filmInfo.getDirector())){
            System.err.println("People which is output of filmInfo.getDirector() is not in DbAdapter's cache");
            return false;
        }
        if(!list.contains(filmInfo))
        {
            System.err.println("filmInfo is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE filmInfos SET title=?, description=?, categoryId=?,releaseDate=DATE(?),currentSalePrice=?,publisherId=?,rating=?,awards=?,directorId=?,actorsId=?,timeLimit=?  WHERE productInfoId=?";
        StringBuilder stringBuilder = new StringBuilder();
        int oldPublisherId = 0, oldCategoryId = 0, oldDirectorId = 0;
        ArrayList<Integer> oldActorsId = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {
            //Get old value
            ResultSet rs = stmt.executeQuery("SELECT categoryId, publisherId, directorId, actorsId FROM filmInfos WHERE productInfoId=" + filmInfo.getProductInfoId());
            if(rs.next()){
                oldCategoryId = rs.getInt(1);
                oldPublisherId = rs.getInt(2);
                oldDirectorId = rs.getInt(3);
                for (String idString: rs.getString(4).split("_")){
                    oldActorsId.add(Integer.parseInt(idString));
                }
            }
            //Update
            pstmt.setString(1, filmInfo.getTitle());
            pstmt.setString(2, filmInfo.getDescription());
            pstmt.setInt(3,filmInfo.getCategory().getCategoryId());
            pstmt.setString(4, filmInfo.getReleaseDate().toString());
            pstmt.setDouble(5,filmInfo.getCurrentSalePrice());
            pstmt.setInt(6, filmInfo.getPublisher().getPublisherId());
            pstmt.setDouble(7, filmInfo.getRating());
            pstmt.setInt(9, filmInfo.getDirector().getPersonId());

            //award
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(String a : filmInfo.getAward()){
                stringBuilder.append(a);
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(8, stringBuilder.toString());//1 String cac ten award,ngan cach boi dau _

            //actors
            stringBuilder.delete(0, stringBuilder.length());//clear stringBuilder
            for(Person person : filmInfo.getActors()){
                stringBuilder.append(person.getPersonId());
                stringBuilder.append('_');
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());//xoá dấu _ ở cuối
            pstmt.setString(10, stringBuilder.toString());//1 String cac contributorId,ngan cach boi dau _
            
            pstmt.setString(11, filmInfo.getTimeLimit().toString());
            pstmt.setInt(12,filmInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("filmInfo (ID = " + filmInfo.getProductInfoId() + ") does not exist in \"filmInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        //Decrease reference count

        //actors
        int count = oldActorsId.size();
        for (Person person: readOnlyCache.getPeople()){
            if (oldActorsId.contains(person.getPersonId())){
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
        
        //director
        for (var p: readOnlyCache.getPeople()){
            if (p.getPersonId() == oldDirectorId){
                try{p.decreaseTimesToBeReferenced();}
                catch (Exception e){
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            }
        }
        
        //Increase reference count
        filmInfo.getPublisher().increaseTimesToBeReferenced();
        for(var m : filmInfo.getActors()){
            m.increaseTimesToBeReferenced();
        }
        filmInfo.getCategory().increaseTimesToBeReferenced();
        filmInfo.getDirector().increaseTimesToBeReferenced();
        return true;
    }
    public boolean delete(FilmInfo filmInfo) {
        if(filmInfo.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this filmInfo.");
            return false;
        }
        for (var x : filmInfo.getActors()){
            if(!readOnlyCache.getPeople().contains(x)){
                System.err.println("One person in filmInfo.getMusicians() is not in DbAdapter's cache");
                return false;
            }
        }
        if(!readOnlyCache.getCategories().contains(filmInfo.getCategory())){
            System.err.println("Category which is output of filmInfo.getCategory() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPublishers().contains(filmInfo.getPublisher())){
            System.err.println("Publisher which is output of filmInfo.getPublisher() is not in DbAdapter's cache");
            return false;
        }
        if(!readOnlyCache.getPeople().contains(filmInfo.getDirector())){
            System.err.println("Person which is output of filmInfo.getDirector() is not in DbAdapter's cache");
            return false;
        }
        
        int index = list.indexOf(filmInfo);
        if(index < 0){
            System.err.println("filmInfo is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM filmInfos WHERE productInfoId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmInfo.getProductInfoId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("FilmInfo (ID = " + filmInfo.getProductInfoId() + ") does not exist in \"filmInfos\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        //When Success
        try {
            filmInfo.getPublisher().decreaseTimesToBeReferenced();
            for(var m : filmInfo.getActors()){
                m.decreaseTimesToBeReferenced();
            }
            filmInfo.getCategory().decreaseTimesToBeReferenced();
            filmInfo.getDirector().decreaseTimesToBeReferenced();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.remove(index, index + 1);
        return true;
    }
}
