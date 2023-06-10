package com.shopkeeper.linh.database;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.models.FeedbackAbout;
import com.shopkeeper.linh.models.FeedbackType;
import com.shopkeeper.linh.models.Staff;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class FeedbackDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Feedback> list;
    public FeedbackDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Feedback> saleBills){
        this.conn = conn;
        this.list = saleBills;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE feedbacks (");
        sqlBuilder.append("feedbackId  INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("title                 TEXT        NOT NULL,");
        sqlBuilder.append("description           TEXT        NOT NULL,");
        sqlBuilder.append("feedbackAbout         TEXT        NOT NULL,");
        sqlBuilder.append("feedbackType          TEXT        NOT NULL,");
        sqlBuilder.append("productTargetId       TEXT        NOT NULL,");
        sqlBuilder.append("productInfoType       TEXT        NOT NULL,");
        sqlBuilder.append("productInfoTargetId   TEXT        NOT NULL,");
        sqlBuilder.append("productInfoRating     INTEGER     NOT NULL,");
        sqlBuilder.append("staffTargetId         INTEGER     NOT NULL,");
        sqlBuilder.append("isUseful              BOOLEAN     NOT NULL,");
        sqlBuilder.append("time                  DATETIME    NOT NULL,");
        sqlBuilder.append("isRead                BOOLEAN     NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_feedbacks_feedbackId ON feedbacks(feedbackId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT feedbackId, title, description, feedbackAbout, feedbackType, productTargetId, productInfoTargetId, productInfoRating, staffTargetId, isUseful, time, productInfoType, isRead FROM feedbacks";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Feedback feedback;
        long id;
        while (rs.next()) {
            feedback = new Feedback();
            feedback.setFeedbackId(rs.getLong("feedbackId"));
            feedback.setTitle(rs.getString("title"));
            feedback.setDescription(rs.getString("description"));
            feedback.setFeedbackAbout(FeedbackAbout.valueOf(rs.getString("feedbackAbout")));
            feedback.setFeedbackType(FeedbackType.valueOf(rs.getString("feedbackType")));
            switch (feedback.getFeedbackAbout()){
                case Staff:
                    id = rs.getLong("staffTargetId");
                    for(var x : readOnlyCache.getStaffs()){
                        if(id == x.getStaffId()){
                            feedback.setStaffTarget(x);
                            x.increaseTimesToBeReferenced();
                            break;
                        }
                    }

                    break;
                case ProductInfo:
                    id = rs.getLong("productInfoTargetId");
                    switch (rs.getString("productInfoType")){
                        case "Music":
                            for(var x : readOnlyCache.getMusicInfos()){
                                if(x.getProductInfoId() == id){
                                    feedback.setProductInfoTarget(x);
                                    x.increaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                        case "Book":
                            for(var x : readOnlyCache.getBookInfos()){
                                if(x.getProductInfoId() == id){
                                    feedback.setProductInfoTarget(x);
                                    x.increaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                        case "Film":
                            for(var x : readOnlyCache.getFilmInfos()){
                                if(x.getProductInfoId() == id){
                                    feedback.setProductInfoTarget(x);
                                    x.increaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                    }
                    feedback.setProductInfoRating(rs.getInt("productInfoRating"));
                    break;
                case  Product:
                    id = rs.getLong("productTargetId");
                    for(var x : readOnlyCache.getProducts()){
                        if(id == x.getProductId()){
                            feedback.setProductTarget(x);
                            x.increaseTimesToBeReferenced();
                            break;
                        }
                    }

                    break;
                case Service:
                    //Do nothing
                    break;
            }
            feedback.setIsUseful(rs.getBoolean("isUseful"));
            feedback.setTime(LocalDate.parse(rs.getString("time")));
            feedback.setRead(rs.getBoolean("isRead"));
            list.add(feedback);
        }

        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(Feedback feedback) {
        if(feedback.getFeedbackId() != 0) return false;
        switch (feedback.getFeedbackAbout()){
            case Staff:
                Staff staff;
                try {
                    staff = feedback.getStaffTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getStaffs().contains(staff)){
                    System.err.println("Staff which is output of feedback.getStaffTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case ProductInfo:
                ProductInfo p;
                try {
                    p = feedback.getProductInfoTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(p instanceof MusicInfo){
                    if(!readOnlyCache.getMusicInfos().contains(p)){
                        System.err.println("MusicInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof FilmInfo){
                    if(!readOnlyCache.getFilmInfos().contains(p)){
                        System.err.println("FilmInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof BookInfo){
                    if(!readOnlyCache.getBookInfos().contains(p)){
                        System.err.println("BookInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                break;
            case  Product:
                Product product;
                try {
                    product = feedback.getProductTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getProducts().contains(product)){
                    System.err.println("Product which is output of feedback.getProductTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        String sql = "INSERT INTO feedbacks(title, description, feedbackAbout, feedbackType, productTargetId, productInfoTargetId, productInfoRating, staffTargetId, isUseful, time, productInfoType, isRead) VALUES(?,?,?,?,?,?,?,?,?,DATE(?),?, false)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, feedback.getTitle());
            pstmt.setString(2, feedback.getDescription());
            pstmt.setString(3, feedback.getFeedbackAbout().toString());
            pstmt.setString(4, feedback.getFeedbackType().toString());
            switch (feedback.getFeedbackAbout()){
                case Staff:
                    pstmt.setInt(5, 0);//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, feedback.getStaffTarget().getStaffId());//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
                case ProductInfo:
                    pstmt.setInt(5, 0);//productTarget
                    pstmt.setLong(6, feedback.getProductInfoTarget().getProductInfoId());//productInfoTarget
                    pstmt.setInt(7, feedback.getProductInfoRating());//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    //productInfoType
                    if(feedback.getProductInfoTarget() instanceof  MusicInfo){
                        pstmt.setString(11, "Music");
                    }
                    else if(feedback.getProductInfoTarget() instanceof  FilmInfo){
                        pstmt.setString(11, "Film");
                    }
                    else if(feedback.getProductInfoTarget() instanceof  BookInfo){
                        pstmt.setString(11, "Book");
                    }
                    break;
                case  Product:
                    pstmt.setLong(5, feedback.getProductTarget().getProductId());//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
                case Service:
                    pstmt.setLong(5, 0);//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
            }
            pstmt.setBoolean(9, feedback.getIsUseful());
            pstmt.setString(10, feedback.getTime().toString());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating feedback failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                feedback.setFeedbackId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating feedback failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        switch (feedback.getFeedbackAbout()){
            case Staff:
                try {
                    feedback.getStaffTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case ProductInfo:
                try {
                    feedback.getProductInfoTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case  Product:
                try {
                    feedback.getProductTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        list.add(feedback);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Feedback feedback) {
        switch (feedback.getFeedbackAbout()){
            case Staff:
                Staff staff;
                try {
                    staff = feedback.getStaffTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getStaffs().contains(staff)){
                    System.err.println("Staff which is output of feedback.getStaffTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case ProductInfo:
                ProductInfo p;
                try {
                    p = feedback.getProductInfoTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(p instanceof MusicInfo){
                    if(!readOnlyCache.getMusicInfos().contains(p)){
                        System.err.println("MusicInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof FilmInfo){
                    if(!readOnlyCache.getFilmInfos().contains(p)){
                        System.err.println("FilmInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof BookInfo){
                    if(!readOnlyCache.getBookInfos().contains(p)){
                        System.err.println("BookInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                break;
            case  Product:
                Product product;
                try {
                    product = feedback.getProductTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getProducts().contains(product)){
                    System.err.println("Product which is output of feedback.getProductTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        if(!list.contains(feedback))
        {
            System.err.println("feedback is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE feedbacks SET title=?, description=?, feedbackAbout=?, feedbackType=?, productTargetId=?, productInfoTargetId=?, productInfoRating=?, staffTargetId=?, isUseful=?, time=?, productInfoType=? WHERE feedbackId=?";

        long id = 0;
        String productInfoType = "";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {
            //Get old
            ResultSet rs;
            switch (feedback.getFeedbackAbout()){
                case Staff:
                    rs = stmt.executeQuery("SELECT staffTargetId FROM feedbacks WHERE feedbackId=" + feedback.getFeedbackId());
                    if(rs.next()){
                        id = rs.getLong("staffTargetId");
                    }
                    break;
                case ProductInfo:
                    rs = stmt.executeQuery("SELECT productInfoTargetId,productInfoType FROM feedbacks WHERE feedbackId=" + feedback.getFeedbackId());

                    if(rs.next()){
                        id = rs.getLong("productInfoTargetId");
                        productInfoType = rs.getString("productInfoType");
                    }
                    break;
                case  Product:
                    rs = stmt.executeQuery("SELECT productTargetId FROM feedbacks WHERE feedbackId=" + feedback.getFeedbackId());
                    if(rs.next()){
                        id = rs.getLong("productTargetId");
                    }
                    break;
                case Service:
                    //Do nothing
                    break;
            }
            //Update
            pstmt.setString(1, feedback.getTitle());
            pstmt.setString(2, feedback.getDescription());
            pstmt.setString(3, feedback.getFeedbackAbout().toString());
            pstmt.setString(4, feedback.getFeedbackType().toString());
            switch (feedback.getFeedbackAbout()){
                case Staff:
                    pstmt.setInt(5, 0);//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, feedback.getStaffTarget().getStaffId());//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
                case ProductInfo:
                    pstmt.setInt(5, 0);//productTarget
                    pstmt.setLong(6, feedback.getProductInfoTarget().getProductInfoId());//productInfoTarget
                    pstmt.setInt(7, feedback.getProductInfoRating());//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    //productInfoType
                    if(feedback.getProductInfoTarget() instanceof  MusicInfo){
                        pstmt.setString(11, "Music");
                    }
                    else if(feedback.getProductInfoTarget() instanceof  FilmInfo){
                        pstmt.setString(11, "Film");
                    }
                    else if(feedback.getProductInfoTarget() instanceof  BookInfo){
                        pstmt.setString(11, "Book");
                    }
                    break;
                case  Product:
                    pstmt.setLong(5, feedback.getProductTarget().getProductId());//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
                case Service:
                    pstmt.setLong(5, 0);//productTarget
                    pstmt.setString(6, "");//productInfoTarget
                    pstmt.setInt(7, 0);//productInfoRating
                    pstmt.setLong(8, 0);//staffTarget
                    pstmt.setString(11, "");//productInfoType
                    break;
            }
            pstmt.setBoolean(9, feedback.getIsUseful());
            pstmt.setString(10, feedback.getTime().toString());
            pstmt.setLong(12, feedback.getFeedbackId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Feedback (ID = " + feedback.getFeedbackId() + ") does not exist in \"feedbacks\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When Success
        //Decrease reference count
        try {
            switch (feedback.getFeedbackAbout()){
                case Staff:
                    for(var x : readOnlyCache.getStaffs()){
                        if(id == x.getStaffId()){
                            x.decreaseTimesToBeReferenced();
                            break;
                        }
                    }
                    break;
                case ProductInfo:
                    switch (productInfoType){
                        case "Music":
                            for(var x : readOnlyCache.getMusicInfos()){
                                if(x.getProductInfoId() == id){
                                    x.decreaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                        case "Book":
                            for(var x : readOnlyCache.getBookInfos()){
                                if(x.getProductInfoId() == id){
                                    x.decreaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                        case "Film":
                            for(var x : readOnlyCache.getFilmInfos()){
                                if(x.getProductInfoId() == id){
                                    x.decreaseTimesToBeReferenced();
                                    break;
                                }
                            }
                            break;
                        default:
                            throw new Exception("productInfoType is invalid or EMPTY.");
                    }
                    break;
                case  Product:
                    for(var x : readOnlyCache.getProducts()){
                        if(id == x.getProductId()){
                            x.decreaseTimesToBeReferenced();
                            break;
                        }
                    }

                    break;
                case Service:
                    //Do nothing
                    break;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Increase reference count
        switch (feedback.getFeedbackAbout()){
            case Staff:
                try {
                    feedback.getStaffTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case ProductInfo:
                try {
                    feedback.getProductInfoTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case  Product:
                try {
                    feedback.getProductTarget().increaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        return true;
    }
    //Return true if success, otherwise return false
    public boolean delete(Feedback feedback) {
        switch (feedback.getFeedbackAbout()){
            case Staff:
                Staff staff;
                try {
                    staff = feedback.getStaffTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getStaffs().contains(staff)){
                    System.err.println("Staff which is output of feedback.getStaffTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case ProductInfo:
                ProductInfo p;
                try {
                    p = feedback.getProductInfoTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(p instanceof MusicInfo){
                    if(!readOnlyCache.getMusicInfos().contains(p)){
                        System.err.println("MusicInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof FilmInfo){
                    if(!readOnlyCache.getFilmInfos().contains(p)){
                        System.err.println("FilmInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                else if(p instanceof BookInfo){
                    if(!readOnlyCache.getBookInfos().contains(p)){
                        System.err.println("BookInfo which is output of feedback.getProductInfoTarget() is not in DbAdapter's cache");
                        return false;
                    }
                }
                break;
            case  Product:
                Product product;
                try {
                    product = feedback.getProductTarget();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                if(!readOnlyCache.getProducts().contains(product)){
                    System.err.println("Product which is output of feedback.getProductTarget() is not in DbAdapter's cache");
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        int index = list.indexOf(feedback);
        if(index < 0){
            System.err.println("feedback is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM feedbacks WHERE feedbackId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, feedback.getFeedbackId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Feedback (ID = " + feedback.getFeedbackId() + ") does not exist in \"feedbacks\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When Success
        switch (feedback.getFeedbackAbout()){
            case Staff:
                try {
                    feedback.getStaffTarget().decreaseTimesToBeReferenced();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case ProductInfo:
                try {
                    feedback.getProductInfoTarget().decreaseTimesToBeReferenced();;
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case  Product:
                try {
                    feedback.getProductTarget().decreaseTimesToBeReferenced();;
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                    return false;
                }
                break;
            case Service:
                //Do nothing
                break;
        }
        list.remove(index, index + 1);
        return true;
    }
    public boolean removeAllUnusefulFeedback() {
        String sql = "DELETE FROM feedbacks WHERE isUseful = false";

        try (Statement pstmt = conn.createStatement()) {
            pstmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        int i = list.size() - 1;
        Feedback item;
        while(i > - 1){
            item = list.get(i);
            if(!item.getIsUseful()){
                list.remove(i, i + 1);
            }
            i--;
        }
        return true;
    }
    public boolean updateIsUseful(Feedback feedback) {
        String sql = "UPDATE feedbacks SET isUseful=" + feedback.getIsUseful() + " WHERE feedbackId=" + feedback.getFeedbackId();

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean markAsRead(Feedback feedback) {
        if(feedback == null) return false;
        if(feedback.isRead()) return true;
        feedback.setRead(true);
        String sql = "UPDATE feedbacks SET isRead=true WHERE feedbackId=" + feedback.getFeedbackId();

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
