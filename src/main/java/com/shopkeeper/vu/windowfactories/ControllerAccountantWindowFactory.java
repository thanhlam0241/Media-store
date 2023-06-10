package com.shopkeeper.vu.windowfactories;

import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.lam.models.ProductInfo;
import com.shopkeeper.vu.funtions.Accountant;
import com.shopkeeper.vu.funtions.StatisticGranularity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAccountantWindowFactory implements Initializable {
@FXML
    private ComboBox comboBox_pick;
    @FXML
    private LineChart lineChart;

    @FXML
    private ComboBox comboBox_stye;
    @FXML
    private Label lb_banhang;
    @FXML
    private Label lb_nhaphang;
    @FXML
    private Label lb_matbang;
    @FXML
    private Label lb_vanchuyen;
    @FXML
    private Label lb_loinhuan;
    @FXML
    private Label lb_nhanvien;
    @FXML
    private Label lb_chiphikhac;
    @FXML
    private PieChart tk_all;
    @FXML
    private DatePicker date_day1;
    @FXML
    private DatePicker date_day2;
    @FXML
    private DatePicker date_day3;
    @FXML
    private DatePicker date_day4;
    @FXML
    private ComboBox cb_pick2;
    @FXML
    private ComboBox cb_type2;
    @FXML
    private ComboBox cb_info;
    @FXML
    private LineChart lineChart2;
    @FXML
    private PieChart pc22;
    @FXML
    private Label lb_musicsale;
    @FXML
    private Label lb_booksale;
    @FXML
    private Label lb_filmsale;
    @FXML
    private Label lb_musicimport;
    @FXML
    private Label lb_bookimport;
    @FXML
    private Label lb_filmimport;
    XYChart.Series sr2;


    XYChart.Series sr;
    private  String[] pick = {"Ngày", "Tháng","Quý","Năm"};
    private  String[] pick2 = {"Ngày", "Tháng","Năm"};
    private  String[] stye ={"Chi phí khác","Bán ra","Nhập hàng","Chi phí vận chuyển","Chi phí mặt bằng","Lương nhân viên","Lợi nhuận"};
    private String[] ty ={"Bán ra","Nhập vào"};
    private String[] loai ={"Music","Book","Film"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox_stye.getItems().addAll(stye);
        cb_pick2.getItems().addAll(pick2);


        comboBox_pick.getItems().addAll(pick);
        cb_info.getItems().addAll(loai);
        comboBox_stye.getSelectionModel().selectFirst();
        cb_type2.getItems().addAll(ty);
        cb_info.getSelectionModel().selectFirst();
        cb_type2.getSelectionModel().selectFirst();
        cb_pick2.getSelectionModel().selectFirst();
        comboBox_pick.getSelectionModel().selectFirst();

    }
    public  void analysis() throws Exception {
        LocalDate day1 = date_day1.getValue();
        LocalDate day2 = date_day2.getValue();
        if(day1.isAfter(day2)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vui lòng chọn lại ngày", ButtonType.APPLY);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.show();
        }
        else {
            String chon = String.valueOf(comboBox_pick.getValue()) ;
            String chonkieu = String.valueOf(comboBox_stye.getValue());
            Accountant a = new Accountant();
            //Tính theo ngày
            if(chon.equals("Ngày")&&chonkieu.equals("Bán ra")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listsaleday =FXCollections.observableArrayList();
                listsaleday=a.getSaleStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for (XYChart.Data<String, Double> b: listsaleday) {
                    cost =b.getYValue()+cost;
                    sr.getData().add(b);
                }
                lb_banhang.setText("Tổng bán ra là : " + cost +"đồng");
                sr.setName("Bán ra");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Nhập hàng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listimportday =FXCollections.observableArrayList();
                listimportday=a.getImportStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for (XYChart.Data<String, Double> b: listimportday) {
                    cost =cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhaphang.setText("Tổng nhập hàng là :"+ cost +"đồng");
                sr.setName("Nhập hàng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Lương nhân viên")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> liststaffcost =FXCollections.observableArrayList();
                liststaffcost=a.getStaffCostsStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for (XYChart.Data<String, Double> b: liststaffcost) {
                    cost = cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhanvien.setText("Tổng lương nhân viên : "+ cost+ "đồng");
                sr.setName("Lương nhân viên");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Chi phí khác")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listcostother =FXCollections.observableArrayList();
                listcostother =a.getCostsOtherStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for(XYChart.Data<String, Double> b :listcostother){
                    cost = cost +b.getYValue();
                    sr.getData().add(b);
                }
                lb_chiphikhac.setText("Chi phí khác : "+ cost +" đồng");
                sr.setName("Chi phí khác");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Lợi nhuận")){
                //sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listprofit = FXCollections.observableArrayList();
                listprofit=a.getRevenueStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for (XYChart.Data<String, Double> b: listprofit) {
                    cost = cost +b.getYValue();
                    //sr.getData().add(b);
                }
                lb_loinhuan.setText("Lợi nhận của cửa hàng : "+cost+"đồng");
                //sr.setName("Lợi nhuận");
                //lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Chi phí mặt bằng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listspace =FXCollections.observableArrayList();
                listspace=a.getSpaceCostsStatistics(day1,day2,StatisticGranularity.Day);
                double cost =0;
                for (XYChart.Data<String, Double> b: listspace) {
                    sr.getData().add(b);
                    cost = cost +b.getYValue();
                }
                lb_matbang.setText("Chi phí mặt bằng : "+cost+"đồng");
                sr.setName("Chi phí mặt bằng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Ngày")&&chonkieu.equals("Chi phí vận chuyển")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listtransport =FXCollections.observableArrayList();
                listtransport=a.getTranspotationCostsStatistics(day1,day2,StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listtransport) {
                    sr.getData().add(b);
                    cost =cost+b.getYValue();
                }
                lb_vanchuyen.setText("Chi phí vận chuyển: "+cost+"đồng");
                sr.setName("Chi phí vận chuyển");
                lineChart.getData().add(sr);
            }
            // Tính theo tháng
            if(chon.equals("Tháng")&&chonkieu.equals("Bán ra")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listsaleday =FXCollections.observableArrayList();
                listsaleday=a.getSaleStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for (XYChart.Data<String, Double> b: listsaleday) {
                    cost =b.getYValue()+cost;
                    sr.getData().add(b);
                }
                lb_banhang.setText("Tổng bán ra là : " + cost +"đồng");
                sr.setName("Bán ra");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Nhập hàng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listimportday =FXCollections.observableArrayList();
                listimportday=a.getImportStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for (XYChart.Data<String, Double> b: listimportday) {
                    cost =cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhaphang.setText("Tổng nhập hàng là :"+ cost +"đồng");
                sr.setName("Nhập hàng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Lương nhân viên")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> liststaffcost =FXCollections.observableArrayList();
                liststaffcost=a.getStaffCostsStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for (XYChart.Data<String, Double> b: liststaffcost) {
                    cost = cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhanvien.setText("Tổng lương nhân viên : "+ cost+ "đồng");
                sr.setName("Lương nhân viên");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Lợi nhuận")){
                //sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listprofit = FXCollections.observableArrayList();
                listprofit=a.getRevenueStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for (XYChart.Data<String, Double> b: listprofit) {
                    cost = cost +b.getYValue();
                    //sr.getData().add(b);
                }
                lb_loinhuan.setText("Lợi nhận của cửa hàng: "+cost+"đồng");
                //sr.setName("Lợi nhuận");
                //lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Chi phí mặt bằng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listspace =FXCollections.observableArrayList();
                listspace=a.getSpaceCostsStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for (XYChart.Data<String, Double> b: listspace) {
                    sr.getData().add(b);
                    cost = cost +b.getYValue();
                }
                lb_matbang.setText("Chi phí mặt bằng : "+cost+"đồng");
                sr.setName("Chi phí mặt bằng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Chi phí vận chuyển")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listtransport =FXCollections.observableArrayList();
                listtransport=a.getTranspotationCostsStatistics(day1,day2,StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listtransport) {
                    sr.getData().add(b);
                    cost =cost+b.getYValue();
                }
                lb_vanchuyen.setText("Chi phí vận chuyển : "+cost+"đồng");
                sr.setName("Chi phí vận chuyển");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Tháng")&&chonkieu.equals("Chi phí khác")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listcostother =FXCollections.observableArrayList();
                listcostother =a.getCostsOtherStatistics(day1,day2,StatisticGranularity.Month);
                double cost =0;
                for(XYChart.Data<String, Double> b :listcostother){
                    cost = cost +b.getYValue();
                    sr.getData().add(b);
                }
                lb_chiphikhac.setText("Chi phí khác :"+ cost +" đồng");
                sr.setName("Chi phí khác");
                lineChart.getData().add(sr);
            }
            //Tính theo quý
            if(chon.equals("Quý")&&chonkieu.equals("Bán ra")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listsaleday =FXCollections.observableArrayList();
                listsaleday=a.getSaleStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for (XYChart.Data<String, Double> b: listsaleday) {
                    cost =b.getYValue()+cost;
                    sr.getData().add(b);
                }
                lb_banhang.setText("Tổng bán ra là : " + cost +"đồng");
                sr.setName("Bán ra");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Nhập hàng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listimportday =FXCollections.observableArrayList();
                listimportday=a.getImportStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for (XYChart.Data<String, Double> b: listimportday) {
                    cost =cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhaphang.setText("Tổng nhập hàng là :"+ cost +"đồng");
                sr.setName("Nhập hàng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Lương nhân viên")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> liststaffcost =FXCollections.observableArrayList();
                liststaffcost=a.getStaffCostsStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for (XYChart.Data<String, Double> b: liststaffcost) {
                    cost = cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhanvien.setText("Tổng lương nhân viên : "+ cost+ "đồng");
                sr.setName("Lương nhân viên");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Lợi nhuận")){
                //sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listprofit = FXCollections.observableArrayList();
                listprofit=a.getRevenueStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for (XYChart.Data<String, Double> b: listprofit) {
                    cost = cost +b.getYValue();
                    //sr.getData().add(b);
                }
                lb_loinhuan.setText("Lợi nhận của cửa hàng: "+cost+"đồng");
                //sr.setName("Lợi nhuận");
                //lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Chi phí mặt bằng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listspace =FXCollections.observableArrayList();
                listspace=a.getSpaceCostsStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for (XYChart.Data<String, Double> b: listspace) {
                    sr.getData().add(b);
                    cost = cost +b.getYValue();
                }
                lb_matbang.setText("Chi phí mặt bằng : "+cost+"đồng");
                sr.setName("Chi phí mặt bằng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Chi phí vận chuyển")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listtransport =FXCollections.observableArrayList();
                listtransport=a.getTranspotationCostsStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listtransport) {
                    sr.getData().add(b);
                    cost =cost+b.getYValue();
                }
                lb_vanchuyen.setText("Chi phí vận chuyển : "+cost+"đồng");
                sr.setName("Chi phí vận chuyển");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Quý")&&chonkieu.equals("Chi phí khác")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listcostother =FXCollections.observableArrayList();
                listcostother =a.getCostsOtherStatistics(day1,day2,StatisticGranularity.Quarter);
                double cost =0;
                for(XYChart.Data<String, Double> b :listcostother){
                    cost = cost +b.getYValue();
                    sr.getData().add(b);
                }
                lb_chiphikhac.setText("Chi phí khác :"+ cost +" đồng");
                sr.setName("Chi phí khác");
                lineChart.getData().add(sr);
            }
            //Tính theo năm
            if(chon.equals("Năm")&&chonkieu.equals("Bán ra")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listsaleday =FXCollections.observableArrayList();
                listsaleday=a.getSaleStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for (XYChart.Data<String, Double> b: listsaleday) {
                    cost =b.getYValue()+cost;
                    sr.getData().add(b);
                }
                lb_banhang.setText("Tổng bán ra là : " + cost +"đồng");
                sr.setName("Bán ra");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Nhập hàng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listimportday =FXCollections.observableArrayList();
                listimportday=a.getImportStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for (XYChart.Data<String, Double> b: listimportday) {
                    cost =cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhaphang.setText("Tổng nhập hàng là :"+ cost +"đồng");
                sr.setName("Nhập hàng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Lương nhân viên")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> liststaffcost =FXCollections.observableArrayList();
                liststaffcost=a.getStaffCostsStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for (XYChart.Data<String, Double> b: liststaffcost) {
                    cost = cost+b.getYValue();
                    sr.getData().add(b);
                }
                lb_nhanvien.setText("Tổng lương nhân viên : "+ cost+ "đồng");
                sr.setName("Lương nhân viên");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Lợi nhuận")){
                //sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listprofit = FXCollections.observableArrayList();
                listprofit=a.getRevenueStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for (XYChart.Data<String, Double> b: listprofit) {
                    cost = cost +b.getYValue();
                    //sr.getData().add(b);
                }
                lb_loinhuan.setText("Lợi nhận của cửa hàng : "+cost+"đồng");
                //sr.setName("Lợi nhuận");
                //lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Chi phí mặt bằng")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listspace =FXCollections.observableArrayList();
                listspace=a.getSpaceCostsStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for (XYChart.Data<String, Double> b: listspace) {
                    sr.getData().add(b);
                    cost = cost +b.getYValue();
                }
                lb_matbang.setText("Chi phí mặt bằng: "+cost+"đồng");
                sr.setName("Chi phí mặt bằng");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Chi phí vận chuyển")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listtransport =FXCollections.observableArrayList();
                listtransport=a.getTranspotationCostsStatistics(day1,day2,StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listtransport) {
                    sr.getData().add(b);
                    cost =cost+b.getYValue();
                }
                lb_vanchuyen.setText("Chi phí vận chuyển: "+cost+"đồng");
                sr.setName("Chi phí vận chuyển");
                lineChart.getData().add(sr);
            }
            if(chon.equals("Năm")&&chonkieu.equals("Chi phí khác")){
                sr= new XYChart.Series();
                ObservableList<XYChart.Data<String, Double>> listcostother =FXCollections.observableArrayList();
                listcostother =a.getCostsOtherStatistics(day1,day2,StatisticGranularity.Year);
                double cost =0;
                for(XYChart.Data<String, Double> b :listcostother){
                    cost = cost +b.getYValue();
                    sr.getData().add(b);
                }
                lb_chiphikhac.setText("Chi phí khác : "+ cost +"đồng");
                sr.setName("Chi phí khác");
                lineChart.getData().add(sr);
            }
            lineChart.setAnimated(false);
        }
    }
    public void listAll() {
        LocalDate day1 = date_day1.getValue();
        LocalDate day2 = date_day2.getValue();
        Accountant a = new Accountant();
        ObservableList<PieChart.Data> list1;
        try {
            list1 = a.getAllTypesOfCostsStatistics(day1,day2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tk_all.setData(list1);
        tk_all.setTitle("Biểu đồ của cửa hàng media");
    }
    public void delete(){
        comboBox_stye.getSelectionModel().selectFirst();
        comboBox_pick.getSelectionModel().selectFirst();
        lb_vanchuyen.setText(null);
        lb_nhanvien.setText(null);
        lb_matbang.setText(null);
        lb_banhang.setText(null);
        lb_nhaphang.setText(null);
        lb_loinhuan.setText(null);
        lb_chiphikhac.setText(null);
        lineChart.getData().clear();
        tk_all.getData().clear();
        tk_all.setTitle(null);
    }
    public void analysis1() throws Exception {
        LocalDate day3 = date_day3.getValue();
        LocalDate day4 = date_day4.getValue();
        if(day4.isBefore(day4)){
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Vui lòng chọn lại ngày",ButtonType.APPLY);
            a.setHeaderText(null);
            a.show();
        }else {
            Accountant a = new Accountant();
            String chon1 = String.valueOf(cb_pick2.getValue());
            String kieu1 = String.valueOf(cb_type2.getValue());
            String loai = String.valueOf(cb_info.getValue());
            if(chon1.equals("Ngày")&&kieu1.equals("Bán ra")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicsaleday =FXCollections.observableArrayList();
                listmusicsaleday = a.getMusicSaleStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicsaleday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Bán");
                lineChart2.getData().add(sr2);
                lb_musicsale.setText("Số music bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Ngày")&&kieu1.equals("Bán ra")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBooksaleday = FXCollections.observableArrayList();
                listBooksaleday = a.getBookSaleStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBooksaleday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Bán");
                lineChart2.getData().add(sr2);
                lb_booksale.setText("Số Book bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Ngày")&&kieu1.equals("Bán ra")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmsaleday = FXCollections.observableArrayList();
                listFilmsaleday = a.getFilmSaleStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmsaleday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Bán");
                lineChart2.getData().add(sr2);
                lb_filmsale.setText("Số Film bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Ngày")&&kieu1.equals("Nhập vào")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicimportday =FXCollections.observableArrayList();
                listmusicimportday = a.getMusicImportStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicimportday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Nhập");
                lineChart2.getData().add(sr2);
                lb_musicimport.setText("Số music nhập vào là : "+ cost +" đồng");

            }
            if(chon1.equals("Ngày")&&kieu1.equals("Nhập vào")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBookimportday = FXCollections.observableArrayList();
                listBookimportday = a.getBookImportStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBookimportday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Nhập");
                lineChart2.getData().add(sr2);
                lb_bookimport.setText("Số Book nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Ngày")&&kieu1.equals("Nhập vào")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmimportday = FXCollections.observableArrayList();
                listFilmimportday = a.getFilmImportStatistics(day3, day4, StatisticGranularity.Day);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmimportday) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Nhập");
                lineChart2.getData().add(sr2);
                lb_filmimport.setText("Số Film nhập vào là : "+ cost +" đồng");
            }
            // Thang

            if(chon1.equals("Tháng")&&kieu1.equals("Bán ra")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicsalemonth =FXCollections.observableArrayList();
                listmusicsalemonth = a.getMusicSaleStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicsalemonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Bán");
                lineChart2.getData().add(sr2);
                lb_musicsale.setText("Số music bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Tháng")&&kieu1.equals("Bán ra")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBooksalemonth = FXCollections.observableArrayList();
                listBooksalemonth = a.getBookSaleStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBooksalemonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Bán");
                lineChart2.getData().add(sr2);
                lb_booksale.setText("Số Book bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Tháng")&&kieu1.equals("Bán ra")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmsalemonth = FXCollections.observableArrayList();
                listFilmsalemonth = a.getFilmSaleStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmsalemonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Bán");
                lineChart2.getData().add(sr2);
                lb_filmsale.setText("Số Film bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Tháng")&&kieu1.equals("Nhập vào")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicimportmonth =FXCollections.observableArrayList();
                listmusicimportmonth = a.getMusicImportStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicimportmonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Nhập");
                lineChart2.getData().add(sr2);
                lb_musicimport.setText("Số music nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Tháng")&&kieu1.equals("Nhập vào")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBookimportmonth = FXCollections.observableArrayList();
                listBookimportmonth = a.getBookImportStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBookimportmonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Nhập");
                lineChart2.getData().add(sr2);
                lb_bookimport.setText("Số Book nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Tháng")&&kieu1.equals("Nhập vào")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmimportmonth = FXCollections.observableArrayList();
                listFilmimportmonth = a.getFilmImportStatistics(day3, day4, StatisticGranularity.Month);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmimportmonth) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Nhập");
                lineChart2.getData().add(sr2);
                lb_filmimport.setText("Số Film nhập vào là : "+ cost +" đồng");
            }
            // Quy

            if(chon1.equals("Quý")&&kieu1.equals("Bán ra")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicsalequater =FXCollections.observableArrayList();
                listmusicsalequater = a.getMusicSaleStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicsalequater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Bán");
                lineChart2.getData().add(sr2);
                lb_musicsale.setText("Số music bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Quý")&&kieu1.equals("Bán ra")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBooksalequater = FXCollections.observableArrayList();
                listBooksalequater = a.getBookSaleStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBooksalequater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Bán");
                lineChart2.getData().add(sr2);
                lb_booksale.setText("Số Book bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Quý")&&kieu1.equals("Bán ra")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmsalequater = FXCollections.observableArrayList();
                listFilmsalequater = a.getFilmSaleStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmsalequater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Bán");
                lineChart2.getData().add(sr2);
                lb_filmsale.setText("Số Film bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Quý")&&kieu1.equals("Nhập vào")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicimportquater = FXCollections.observableArrayList();
                listmusicimportquater = a.getMusicImportStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicimportquater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Nhập");
                lineChart2.getData().add(sr2);
                lb_musicimport.setText("Số music nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Quý")&&kieu1.equals("Nhập vào")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBookimportquater = FXCollections.observableArrayList();
                listBookimportquater = a.getBookImportStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBookimportquater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Nhập");
                lineChart2.getData().add(sr2);
                lb_bookimport.setText("Số Book nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Quý")&&kieu1.equals("Nhập vào")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmimportquater = FXCollections.observableArrayList();
                listFilmimportquater = a.getFilmImportStatistics(day3, day4, StatisticGranularity.Quarter);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmimportquater) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Nhập");
                lineChart2.getData().add(sr2);
                lb_filmimport.setText("Số Film nhập vào là : "+ cost +" đồng");
            }
            // Nam
            if(chon1.equals("Năm")&&kieu1.equals("Bán ra")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicsaleyear =FXCollections.observableArrayList();
                listmusicsaleyear = a.getMusicSaleStatistics(day3, day4, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicsaleyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Bán");
                lineChart2.getData().add(sr2);
                lb_musicsale.setText("Số music bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Năm")&&kieu1.equals("Bán ra")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBooksaleyear = FXCollections.observableArrayList();
                listBooksaleyear = a.getBookSaleStatistics(day3, day4, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBooksaleyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Bán");
                lineChart2.getData().add(sr2);
                lb_booksale.setText("Số Book bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Năm")&&kieu1.equals("Bán ra")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmsaleyear = FXCollections.observableArrayList();
                listFilmsaleyear = a.getFilmSaleStatistics(day3, day3, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmsaleyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Bán");
                lineChart2.getData().add(sr2);
                lb_filmsale.setText("Số Film bán được là : "+ cost +" đồng");
            }
            if(chon1.equals("Năm")&&kieu1.equals("Nhập vào")&&loai.equals("Book")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listBookimportyear = FXCollections.observableArrayList();
                listBookimportyear = a.getBookImportStatistics(day3, day4, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listBookimportyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Book Nhập");
                lineChart2.getData().add(sr2);
                lb_bookimport.setText("Số Book nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Năm")&&kieu1.equals("Nhập vào")&&loai.equals("Film")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listFilmimportyear = FXCollections.observableArrayList();
                listFilmimportyear = a.getFilmImportStatistics(day3, day4, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listFilmimportyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Film Nhập");
                lineChart2.getData().add(sr2);
                lb_filmimport.setText("Số Film nhập vào là : "+ cost +" đồng");
            }
            if(chon1.equals("Năm")&&kieu1.equals("Nhập vào")&&loai.equals("Music")){
                sr2 = new XYChart.Series();
                ObservableList<XYChart.Data<String , Double>> listmusicimportyear =FXCollections.observableArrayList();
                listmusicimportyear = a.getMusicImportStatistics(day3, day4, StatisticGranularity.Year);
                double cost = 0;
                for (XYChart.Data<String, Double> b: listmusicimportyear) {
                    cost = cost + b.getYValue();
                    sr2.getData().add(b);
                }
                sr2.setName("Music Nhập");
                lineChart2.getData().add(sr2);
                lb_musicimport.setText("Số music nhập vào là : "+ cost +" đồng");
            }
            lineChart2.setAnimated(false);
        }
    }
    public void delete1(){
        lb_musicsale.setText(null);
        lb_musicimport.setText(null);
        lb_bookimport.setText(null);
        lb_booksale.setText(null);
        lb_filmimport.setText(null);
        lb_filmsale.setText(null);
        lineChart2.getData().clear();
        sr2.getData().clear();
    }
}
