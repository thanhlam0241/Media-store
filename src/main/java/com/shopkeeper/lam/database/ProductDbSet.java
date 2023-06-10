package com.shopkeeper.lam.database;
import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;
import com.shopkeeper.lam.models.*;
import java.sql.*;
public class ProductDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Product> list;
    public ProductDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Product> products){
        this.conn = conn;
        this.list = products;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE products (");
        sqlBuilder.append("productId      INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append("productInfoId  INTEGER NOT NULL,");
        sqlBuilder.append("productInfoType  TEXT NOT NULL,");
        sqlBuilder.append("state          TEXT    NOT NULL,");
        sqlBuilder.append("importBillId   TEXT    NOT NULL,");
        sqlBuilder.append("saleBillId     TEXT    NOT NULL,");
        sqlBuilder.append("importCost     DOUBLE  NOT NULL,");
        sqlBuilder.append("saleValue      DOUBLE  NOT NULL,");
        sqlBuilder.append("trialFilename  TEXT    NOT NULL,");
        sqlBuilder.append("placement      TEXT    NOT NULL");
        sqlBuilder.append(");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_products_productId ON products(productId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void load() throws Exception{
        String sql = "SELECT productId, productInfoId, productInfoType, state, importBillId, saleBillId, importCost, saleValue,trialFilename,placement FROM products";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
        Product product;
        while (rs.next()) {
            product = new Product();
            product.setProductId(rs.getInt("productId"));
            product.setState(ProductState.valueOf(rs.getString("state")));

            //ProductInfo
            int productInfoId = rs.getInt("productInfoId");
            switch (rs.getString("productInfoType")){
                case "Music":
                    for(var x : readOnlyCache.getMusicInfos()){
                        if(x.getProductInfoId() == productInfoId){
                            if(product.getState()==ProductState.READY){x.setNumberOfProduct(x.getNumberOfProduct() + 1);}
                            product.setProductInfo(x);
                            x.increaseTimesToBeReferenced();
                            break;
                        }
                    }
                    break;
                case "Book":
                    for(var x : readOnlyCache.getBookInfos()){
                        if(x.getProductInfoId() == productInfoId){
                            if(product.getState()==ProductState.READY){x.setNumberOfProduct(x.getNumberOfProduct() + 1);}
                            product.setProductInfo(x);
                            x.increaseTimesToBeReferenced();
                            break;
                        }
                    }
                    break;
                case "Film":
                    for(var x : readOnlyCache.getFilmInfos()){
                        if(x.getProductInfoId() == productInfoId){
                            if(product.getState()==ProductState.READY){x.setNumberOfProduct(x.getNumberOfProduct() + 1);}
                            product.setProductInfo(x);
                            x.increaseTimesToBeReferenced();
                            break;
                        }
                    }
                    break;
            }
            //importBill
            int importBillId = rs.getInt("importBillId");
            for(var b : readOnlyCache.getImportBills()){
                if(b.getBillId() == importBillId){
                    product.setImportBill(b);
                    break;
                }
            }
            //SaleBill
            int saleBillId = rs.getInt("saleBillId");

            for(var b : readOnlyCache.getSaleBills()){
                if(b.getBillId() == saleBillId){
                    product.setSaleBill(b);
                    break;
                }
            }
            //numberOfProduct


            product.setSaleValue(rs.getDouble("saleValue"));
            product.setImportCost(rs.getDouble("importCost"));
            product.setTrialFilename(rs.getString("trialFilename"));
            product.setPlacement(rs.getString("placement"));
            list.add(product);
        }

        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(Product product) {
        if(product.getProductId() != 0) return false;
        if(product.getProductInfo() instanceof  MusicInfo){
            if(!readOnlyCache.getMusicInfos().contains(product.getProductInfo())){
                System.err.println("MusicInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  FilmInfo){
            if(!readOnlyCache.getFilmInfos().contains(product.getProductInfo())){
                System.err.println("FilmInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  BookInfo){
            if(!readOnlyCache.getBookInfos().contains(product.getProductInfo())){
                System.err.println("BookInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        if(product.getSaleBill() != null && !readOnlyCache.getSaleBills().contains(product.getSaleBill())){
            System.err.println("SaleBill which is output of product.getSaleBill() is not in DbAdapter's cache");
            return false;
        }
        if(product.getImportBill() != null && !readOnlyCache.getImportBills().contains(product.getImportBill())){
            System.err.println("ImportBill which is output of product.getImportBill() is not in DbAdapter's cache");
            return false;
        }
        String sql = "INSERT INTO products(productInfoId, state, importBillId, saleBillId, importCost,saleValue, trialFilename,placement,productInfoType) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, product.getProductInfo().getProductInfoId());
            pstmt.setString(2, product.getState().toString());
            pstmt.setInt(3, product.getImportBill() != null ? product.getImportBill().getBillId() : 0);
            pstmt.setInt(4, product.getSaleBill() != null ? product.getSaleBill().getBillId() : 0);
            pstmt.setDouble(5, product.getImportCost());
            pstmt.setDouble(6, product.getSaleValue());
            pstmt.setString(7, product.getTrialFilename());
            pstmt.setString(8, product.getPlacement());
            if(product.getProductInfo() instanceof  MusicInfo){
                pstmt.setString(9, "Music");
            }
            else if(product.getProductInfo() instanceof  FilmInfo){
                pstmt.setString(9, "Film");
            }
            else if(product.getProductInfo() instanceof  BookInfo){
                pstmt.setString(9, "Book");
            }
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Creating product failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setProductId(generatedKeys.getInt(1));
            }
            else throw new Exception("Creating product failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //Success
        product.getProductInfo().increaseTimesToBeReferenced();
        list.add(product);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Product product) {
        if(product.getProductInfo() instanceof  MusicInfo){
            if(!readOnlyCache.getMusicInfos().contains(product.getProductInfo())){
                System.err.println("MusicInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  FilmInfo){
            if(!readOnlyCache.getFilmInfos().contains(product.getProductInfo())){
                System.err.println("FilmInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  BookInfo){
            if(!readOnlyCache.getBookInfos().contains(product.getProductInfo())){
                System.err.println("BookInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        if(product.getSaleBill() != null && !readOnlyCache.getSaleBills().contains(product.getSaleBill())){
            System.err.println("SaleBill which is output of product.getSaleBill() is not in DbAdapter's cache");
            return false;
        }
        if(product.getImportBill() != null && !readOnlyCache.getImportBills().contains(product.getImportBill())){
            System.err.println("ImportBill which is output of product.getImportBill() is not in DbAdapter's cache");
            return false;
        }
        if(!list.contains(product))
        {
            System.err.println("product is not in DbAdapter's cache");
            return false;
        }
        String sql = "UPDATE products SET productInfoId=?,state=?,importBillId=?,saleBillId=?,importCost=?,saleValue=?,trialFilename=?,placement=?,productInfoType=? WHERE productId=?";
        int oldImportBillId=0,oldSaleBillId=0, oldProductInfoId = 0;
        String oldProductInfoType ="";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement()) {
            //Get old
            ResultSet rs = stmt.executeQuery("SELECT productInfoId,importBillId,saleBillId,productInfoType FROM products WHERE productId=" + product.getProductId());
            if(rs.next()){
                oldProductInfoId = rs.getInt(1);
                oldImportBillId = rs.getInt(2);
                oldSaleBillId = rs.getInt(3);
                oldProductInfoType = rs.getString(4);
            }
            //Update
            pstmt.setLong(1, product.getProductInfo().getProductInfoId());
            pstmt.setString(2, product.getState().toString());
            pstmt.setInt(3, product.getImportBill() != null ? product.getImportBill().getBillId() : 0);
            pstmt.setInt(4, product.getSaleBill() != null ? product.getSaleBill().getBillId() : 0);
            pstmt.setDouble(5, product.getImportCost());
            pstmt.setDouble(6, product.getSaleValue());
            pstmt.setString(7, product.getTrialFilename());
            pstmt.setString(8, product.getPlacement());
            if(product.getProductInfo() instanceof  MusicInfo){
                pstmt.setString(9, "Music");
            }
            else if(product.getProductInfo() instanceof  FilmInfo){
                pstmt.setString(9, "Film");
            }
            else if(product.getProductInfo() instanceof  BookInfo){
                pstmt.setString(9, "Book");
            }
            pstmt.setInt(10, product.getProductId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Product (ID = " + product.getProductId() + ") does not exist in \"products\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When Success
        //Decrease reference count
        switch (oldProductInfoType){
            case "Music":
                for(var x : readOnlyCache.getMusicInfos()){
                    if(x.getProductInfoId() == oldProductInfoId){
                        try {
                            x.decreaseTimesToBeReferenced();
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                            return false;
                        }
                        break;
                    }
                }
                break;
            case "Book":
                for(var x : readOnlyCache.getBookInfos()){
                    if(x.getProductInfoId() == oldProductInfoId){
                        try {
                            x.decreaseTimesToBeReferenced();
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                            return false;
                        }
                        break;
                    }
                }
                break;
            case "Film":
                for(var x : readOnlyCache.getFilmInfos()){
                    if(x.getProductInfoId() == oldProductInfoId){
                        try {
                            x.decreaseTimesToBeReferenced();
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                            return false;
                        }
                        break;
                    }
                }
                break;
        }
        //Increase reference count
        product.getProductInfo().increaseTimesToBeReferenced();
        return true;
    }
    //Return true if success, otherwise return false
    public boolean deleteProduct(Product product) {
        if(product.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this product.");
            return false;
        }
        if(product.getProductInfo() instanceof  MusicInfo){
            if(!readOnlyCache.getMusicInfos().contains(product.getProductInfo())){
                System.err.println("MusicInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  FilmInfo){
            if(!readOnlyCache.getFilmInfos().contains(product.getProductInfo())){
                System.err.println("FilmInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }
        else if(product.getProductInfo() instanceof  BookInfo){
            if(!readOnlyCache.getBookInfos().contains(product.getProductInfo())){
                System.err.println("BookInfo which is output of product.getProductInfo() is not in DbAdapter's cache");
                return false;
            }
        }

        int index = list.indexOf(product);
        if(index < 0){
            System.err.println("product is not in DbAdapter's cache");
            return false;
        }
        String sql = "DELETE FROM products WHERE productId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getProductId());
            int affected = pstmt.executeUpdate();
            if(affected == 0) throw new Exception("Product (ID = " + product.getProductId() + ") does not exist in \"products\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When Success
        try {
            product.getProductInfo().decreaseTimesToBeReferenced();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        list.remove(index, index + 1);
        return true;
    }

}
