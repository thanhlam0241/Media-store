package com.shopkeeper.vu.funtions;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.mediaone.models.Bill;
import com.shopkeeper.minh.models.ImportBill;
import com.shopkeeper.minh.models.OtherBill;
import com.shopkeeper.minh.models.StaffBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;

public class Accountant {
    ObservableList<XYChart.Data<String, Double>> listImportBillDay;
    ObservableList<XYChart.Data<String, Double>> listImportBillMonth;
    ObservableList<XYChart.Data<String, Double>> listImportBillQuarter;
    ObservableList<XYChart.Data<String, Double>> listImportBillYear;
    ObservableList<XYChart.Data<String, Double>> listImportProductInfoDay;
    ObservableList<XYChart.Data<String, Double>> listImportProductInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listImportProductInfoQuarter;
    ObservableList<XYChart.Data<String, Double>> listImportProductInfoYear;
    ObservableList<XYChart.Data<String, Double>> listSaleBillDay;
    ObservableList<XYChart.Data<String, Double>> listSaleBillMonth;
    ObservableList<XYChart.Data<String, Double>> listSaleBillQuarter;
    ObservableList<XYChart.Data<String, Double>> listSaleBillYear;
    ObservableList<XYChart.Data<String, Double>> listImportMusicInfoYear;
    ObservableList<XYChart.Data<String, Double>> listImportMusicInfoDay;
    ObservableList<XYChart.Data<String, Double>> listImportMusicInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listImportMusicInfoQuater;

    ObservableList<XYChart.Data<String, Double>> listImportBookInfoYear;
    ObservableList<XYChart.Data<String, Double>> listImportBookInfoDay;
    ObservableList<XYChart.Data<String, Double>> listImportBookInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listImportBookInfoQuater;
    ObservableList<XYChart.Data<String, Double>> listImportFilmInfoYear;
    ObservableList<XYChart.Data<String, Double>> listImportFilmInfoDay;
    ObservableList<XYChart.Data<String, Double>> listImportFilmInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listImportFilmInfoQuater;

    ObservableList<XYChart.Data<String, Double>> listSaleMusicInfoYear;
    ObservableList<XYChart.Data<String, Double>> listSaleMusicInfoDay;
    ObservableList<XYChart.Data<String, Double>> listSaleMusicInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listSaleMusicInfoQuater;

    ObservableList<XYChart.Data<String, Double>> listSaleBookInfoYear;
    ObservableList<XYChart.Data<String, Double>> listSaleBookInfoDay;
    ObservableList<XYChart.Data<String, Double>> listSaleBookInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listSaleBookInfoQuater;
    ObservableList<XYChart.Data<String, Double>> listSaleFilmInfoYear;
    ObservableList<XYChart.Data<String, Double>> listSaleFilmInfoDay;
    ObservableList<XYChart.Data<String, Double>> listSaleFilmInfoMonth;
    ObservableList<XYChart.Data<String, Double>> listSaleFilmInfoQuater;

    ObservableList<XYChart.Data<String, Double>> listStaffCostsStatisticsDay;
    ObservableList<XYChart.Data<String, Double>> listStaffCostsStatisticsMonth;
    ObservableList<XYChart.Data<String, Double>> listStaffCostsStatisticsQuarter;
    ObservableList<XYChart.Data<String, Double>> listStaffCostsStatisticsYear;
    ObservableList<XYChart.Data<String, Double>> listSpaceCostsStatisticsDay;
    ObservableList<XYChart.Data<String, Double>> listSpaceCostsStatisticsMonth;
    ObservableList<XYChart.Data<String, Double>> listSpaceCostsStatisticsQuarter;
    ObservableList<XYChart.Data<String, Double>> listSpaceCostsStatisticsYear;
    ObservableList<XYChart.Data<String, Double>> listTranspotationCostsStatisticsDay;
    ObservableList<XYChart.Data<String, Double>> listTranspotationCostsStatisticsMonth;
    ObservableList<XYChart.Data<String, Double>> listTranspotationCostsStatisticsQuarter;
    ObservableList<XYChart.Data<String, Double>> listTranspotationCostsStatisticsYear;
    ObservableList<XYChart.Data<String, Double>> listBillFromDateToDateDay;
    ObservableList<XYChart.Data<String, Double>> listBillFromDateToDateMonth;
    ObservableList<XYChart.Data<String, Double>> listBillFromDateToDateYear;
    ObservableList<XYChart.Data<String, Double>> listBillFromDateToDateQuarter;
    ObservableList<XYChart.Data<String, Double>> listBillProfitFromDateToDateDay;
    ObservableList<XYChart.Data<String, Double>> listBillProfitFromDateToDateMonth;
    ObservableList<XYChart.Data<String, Double>> listBillProfitFromDateToDateQuarter;
    ObservableList<XYChart.Data<String, Double>> listBillProfitFromDateToDateYear;
    ObservableList<XYChart.Data<String, Double>> listCostsStatisticsOtherDay;
    ObservableList<XYChart.Data<String, Double>> listCostsStatisticsOtherMonth;

    ObservableList<XYChart.Data<String, Double>> listCostsStatisticsOtherQuarter;

    ObservableList<XYChart.Data<String, Double>> listCostsStatisticsOtherYear;

    ObservableList<PieChart.Data> listBill;
    ObservableList<PieChart.Data> listProductinfo;
    ObservableList<Bill> listBillFromDateToDate;
    ObservableList<Bill> listBillCostFromDateToDate;
    ObservableList<Bill> listBillProfitFromDateToDate;


    public ObservableList<XYChart.Data<String, Double>> getImportStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<ImportBill> list = adapter.getAllImportBills();
        listImportBillDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listImportBillDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (ImportBill bill: list) {
                    if(d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listImportBillDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listImportBillDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listImportBillMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (ImportBill bill: list) {
                    if(d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listImportBillMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listImportBillMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listImportBillQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (ImportBill bill: list) {
                    if(d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listImportBillQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listImportBillQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listImportBillYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (ImportBill bill: list) {
                    if(bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listImportBillYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listImportBillYear;
        }
        return null;
    }
    // Imprort MusicInfor
    public ObservableList<XYChart.Data<String, Double>> getMusicImportStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        if(granularity.equals(StatisticGranularity.Day)){
            listImportMusicInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo &&a.getImportBill().getTime().equals(d)){
                        cost =cost+a.getImportCost();
                    }
                }
                listImportMusicInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listImportMusicInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listImportMusicInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo&&a.getImportBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getImportCost();
                    }
                }
                listImportMusicInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listImportMusicInfoMonth;
        } else if(granularity.equals(StatisticGranularity.Year)){
            listImportMusicInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getImportCost();
                    }
                }
                listImportMusicInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listImportMusicInfoYear;
        }
        return null;
    }
    // Import Book
    public ObservableList<XYChart.Data<String, Double>> getBookImportStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listImportBookInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listImportBookInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getImportBill().getTime().equals(d)){
                        cost =cost+a.getImportCost();
                    }
                }
                listImportBookInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listImportBookInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listImportBookInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getImportBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getImportCost();
                    }
                }
                listImportBookInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listImportBookInfoMonth;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listImportBookInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getImportCost();
                    }
                }
                listImportBookInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listImportBookInfoYear;
        }
        return null;
    }
    // Import Film
    public ObservableList<XYChart.Data<String, Double>> getFilmImportStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listImportFilmInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listImportFilmInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getImportBill().getTime().equals(d)){
                        cost =cost+a.getImportCost();
                    }
                }
                listImportFilmInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listImportFilmInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listImportFilmInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getImportBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getImportCost();
                    }
                }
                listImportFilmInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listImportFilmInfoMonth;
        } else if(granularity.equals(StatisticGranularity.Year)){
            listImportFilmInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getImportCost();
                    }
                }
                listImportFilmInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listImportFilmInfoYear;
        }
        return null;
    }
    //Sale Book
    public ObservableList<XYChart.Data<String, Double>> getBookSaleStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listSaleBookInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listSaleBookInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().equals(d)){
                        cost =cost+a.getSaleValue();
                    }
                }
                listSaleBookInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listSaleBookInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listSaleBookInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getSaleValue();
                    }
                }
                listSaleBookInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listSaleBookInfoMonth;
        } else if(granularity.equals(StatisticGranularity.Year)){
            listSaleBookInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof BookInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getSaleValue();
                    }
                }
                listSaleBookInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listSaleBookInfoYear;
        }
        return null;
    }
    // Sale Music
    public ObservableList<XYChart.Data<String, Double>> getMusicSaleStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listSaleMusicInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listSaleMusicInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().equals(d)){
                        cost =cost+a.getSaleValue();
                    }
                }
                listSaleMusicInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listSaleMusicInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listSaleMusicInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getSaleValue();
                    }
                }
                listSaleMusicInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listSaleMusicInfoMonth;
        } else if(granularity.equals(StatisticGranularity.Year)){
            listSaleMusicInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof MusicInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getSaleValue();
                    }
                }
                listSaleMusicInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listSaleMusicInfoYear;
        }
        return null;
    }
    // Sale Film
    public ObservableList<XYChart.Data<String, Double>> getFilmSaleStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listSaleFilmInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listSaleFilmInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().equals(d)){
                        cost =cost+a.getSaleValue();
                    }
                }
                listSaleFilmInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listSaleFilmInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listSaleFilmInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getMonth()==d.getMonth()&&a.getImportBill().getTime().getYear()==d.getYear()){
                        cost =cost+ a.getSaleValue();
                    }
                }
                listSaleFilmInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listSaleFilmInfoMonth;
        } else if(granularity.equals(StatisticGranularity.Year)){
            listSaleFilmInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getProductInfo() instanceof FilmInfo&&a.getSaleBill()!=null&&a.getSaleBill().getTime().getYear()==d.getYear()){
                        cost=cost+a.getSaleValue();
                    }
                }
                listSaleFilmInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listSaleFilmInfoYear;
        }
        return null;
    }

    public ObservableList<XYChart.Data<String, Double>> getSaleStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<SaleBill> list = adapter.getAllSaleBills();
        listSaleBillDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listSaleBillDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (SaleBill bill: list) {
                    if(d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listSaleBillDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listSaleBillDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listSaleBillMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (SaleBill bill: list) {
                    if(d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listSaleBillMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listSaleBillMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listSaleBillQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (SaleBill bill: list) {
                    if(d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listSaleBillQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listSaleBillQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listSaleBillYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (SaleBill bill: list) {
                    if(bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listSaleBillYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listSaleBillYear;
        }
        return null;
    }

    public ObservableList<XYChart.Data<String, Double>> getSaleStatistics(ProductInfo productInfo, LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<Product> list = adapter.getAllProducts();
        listImportProductInfoDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listImportProductInfoDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getSaleBill().getTime().equals(d)&&a.getProductInfo().equals(productInfo)){
                        cost =cost+a.getSaleValue();
                    }
                }
                listImportProductInfoDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listImportProductInfoDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listImportProductInfoMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getSaleBill().getTime().getYear()==d.getYear()&&a.getSaleBill().getTime().getMonthValue()==d.getMonthValue()&&a.getProductInfo().equals(productInfo)){
                        cost =cost+ a.getSaleValue();
                    }
                }
                listImportProductInfoMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listImportProductInfoMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listImportProductInfoQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Product a: list) {
                    if(a.getSaleBill().getTime().getMonthValue()>=d.getMonthValue()&&a.getSaleBill().getTime().getMonthValue()<=(d.getMonthValue()+2)&&a.getSaleBill().getTime().getYear()==d.getYear()&&a.getProductInfo().equals(productInfo)){
                        cost =cost+ a.getSaleValue();
                    }
                }
                listImportProductInfoQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listImportProductInfoQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listImportProductInfoYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Product a: list) {
                    if(a.getSaleBill().getTime().getYear()==d.getYear()&&a.getProductInfo().equals(productInfo)){
                        cost=cost+a.getSaleValue();
                    }
                }
                listImportProductInfoYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listImportProductInfoYear;
        }
        return null;
    }

    public ObservableList<XYChart.Data<String, Double>> getStaffCostsStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //chi phi nhan vien
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<StaffBill> list = adapter.getAllStaffBills();
        listStaffCostsStatisticsDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listStaffCostsStatisticsDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (StaffBill bill: list) {
                    if(d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listStaffCostsStatisticsDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listStaffCostsStatisticsDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listStaffCostsStatisticsMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listStaffCostsStatisticsMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listStaffCostsStatisticsMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listStaffCostsStatisticsQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Bill bill: list) {
                    if(d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listStaffCostsStatisticsQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listStaffCostsStatisticsQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listStaffCostsStatisticsYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listStaffCostsStatisticsYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listStaffCostsStatisticsYear;
        }
        return null;
    }
    public ObservableList<XYChart.Data<String, Double>> getSpaceCostsStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //chi phi mat bang
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> list = adapter.getAllOtherBills();
        listSpaceCostsStatisticsDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listSpaceCostsStatisticsDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (OtherBill bill: list) {
                    if(bill.getName().equals("Chi phí mặt bằng")&&d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listSpaceCostsStatisticsDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listSpaceCostsStatisticsDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listSpaceCostsStatisticsMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonthValue()!=toDate.getMonthValue();d=d.plusMonths(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Chi phí mặt bằng")&&d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listSpaceCostsStatisticsMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listSpaceCostsStatisticsMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listSpaceCostsStatisticsQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Chi phí mặt bằng")&&d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listSpaceCostsStatisticsQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listSpaceCostsStatisticsQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listSpaceCostsStatisticsYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Chi phí mặt bằng")&&bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listSpaceCostsStatisticsYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listSpaceCostsStatisticsYear;
        }
        return null;
    }
    public ObservableList<XYChart.Data<String, Double>> getTranspotationCostsStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //chi phi van chuyen
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> list = adapter.getAllOtherBills();
        listTranspotationCostsStatisticsDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listTranspotationCostsStatisticsDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (OtherBill bill: list) {
                    if(bill.getName().equals("Vận chuyển")&&d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listTranspotationCostsStatisticsDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listTranspotationCostsStatisticsDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listTranspotationCostsStatisticsMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonth()!=toDate.getMonth();d=d.plusMonths(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Vận chuyển")&&d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listTranspotationCostsStatisticsMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listTranspotationCostsStatisticsMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listTranspotationCostsStatisticsQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()< (toDate.getYear())||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Vận chuyển")&&d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listTranspotationCostsStatisticsQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listTranspotationCostsStatisticsQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listTranspotationCostsStatisticsYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Vận chuyển")&&bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listTranspotationCostsStatisticsYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listTranspotationCostsStatisticsYear;
        }
        return null;
    }
    public ObservableList<XYChart.Data<String, Double>> getCostsOtherStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //chi phi van chuyen
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> list = adapter.getAllOtherBills();
        listCostsStatisticsOtherDay = FXCollections.observableArrayList();
        if(granularity.equals(StatisticGranularity.Day)){
            listCostsStatisticsOtherDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (OtherBill bill: list) {
                    if(bill.getName().equals("Mặt bằng")==false&&bill.getName().equals("Vận chuyển")==false&&d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listCostsStatisticsOtherDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listCostsStatisticsOtherDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listCostsStatisticsOtherMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonth()!=toDate.getMonth();d=d.plusMonths(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Mặt bằng")==false&&bill.getName().equals("Vận chuyển")==false&&d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listCostsStatisticsOtherMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listCostsStatisticsOtherMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listCostsStatisticsOtherQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()<= (toDate.getYear()-1)||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Mặt bằng")==false&&bill.getName().equals("Vận chuyển")==false&&d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listCostsStatisticsOtherQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listCostsStatisticsOtherQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listCostsStatisticsOtherYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Bill bill: list) {
                    if(bill.getName().equals("Mặt bằng")==false&&bill.getName().equals("Vận chuyển")==false&&bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listCostsStatisticsOtherYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listCostsStatisticsOtherYear;
        }
        return null;
    }
    public ObservableList<XYChart.Data<String, Double>> getCostsStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //chi phi bo ra
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> a = adapter.getAllOtherBills();
        ObservableList<ImportBill> b= adapter.getAllImportBills();
        ObservableList<StaffBill> c = adapter.getAllStaffBills();
        listBillFromDateToDate =FXCollections.observableArrayList();
        listBillFromDateToDate.addAll(a);
        listBillFromDateToDate.addAll(b);
        listBillFromDateToDate.addAll(c);
        if(granularity.equals(StatisticGranularity.Day)){
            listBillFromDateToDateDay =FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.compareTo(toDate.plusDays(1))!=0;d=d.plusDays(1)){
                double cost =0;
                for (Bill bill: listBillFromDateToDate) {
                    if(d.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                listBillFromDateToDateDay.add(new XYChart.Data<>(d+"",cost));
            }
            return listBillFromDateToDateDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listBillFromDateToDateMonth =FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()!= toDate.getYear()||d.getMonth()!=toDate.getMonth();d=d.plusMonths(1)){
                double cost =0;
                for (Bill bill: listBillFromDateToDate) {
                    if(d.getMonth()==bill.getTime().getMonth()&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listBillFromDateToDateMonth.add(new XYChart.Data<>(d.getMonthValue()+"/"+d.getYear()+"",cost));
            }
            return listBillFromDateToDateMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listBillFromDateToDateQuarter=FXCollections.observableArrayList();
            for (LocalDate d =fromDate;d.getYear()<= (toDate.getYear()-1)||d.getMonthValue()<(toDate.getMonthValue());d=d.plusMonths(3)){
                double cost =0;
                for (Bill bill: listBillFromDateToDate) {
                    if(d.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(d.getMonthValue()+2)&&d.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                listBillFromDateToDateQuarter.add(new XYChart.Data<>("Quarter "+(d.getMonthValue()/3+1)+"/"+d.getYear(),cost));
            }
            return listBillFromDateToDateQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listBillFromDateToDateYear=FXCollections.observableArrayList();
            for(LocalDate d=fromDate;d.getYear()!=(toDate.getYear()+1);d=d.plusYears(1)){
                double cost =0;
                for (Bill bill: listBillFromDateToDate) {
                    if(bill.getTime().getYear()==d.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                listBillFromDateToDateYear.add(new XYChart.Data<>(d.getYear()+"",cost));
            }
            return listBillFromDateToDateYear;
        }
        return null;
    }
    public ObservableList<XYChart.Data<String, Double>> getRevenueStatistics(LocalDate fromDate, LocalDate toDate, StatisticGranularity granularity) throws Exception {
        //doanh thu
        var adapter = DatabaseAdapter.getDbAdapter();
        ObservableList<OtherBill> a = adapter.getAllOtherBills();
        ObservableList<ImportBill> b= adapter.getAllImportBills();
        ObservableList<StaffBill> c = adapter.getAllStaffBills();
        ObservableList<SaleBill> d = adapter.getAllSaleBills();
        listBillCostFromDateToDate =FXCollections.observableArrayList();
        listBillProfitFromDateToDate=FXCollections.observableArrayList();
        listBillCostFromDateToDate.addAll(a);
        listBillCostFromDateToDate.addAll(b);
        listBillCostFromDateToDate.addAll(c);
        listBillProfitFromDateToDate.addAll(d);
        if(granularity.equals(StatisticGranularity.Day)){
            listBillProfitFromDateToDateDay =FXCollections.observableArrayList();
            for(LocalDate ld=fromDate;ld.compareTo(toDate)!=0;ld=ld.plusDays(1)){
                double cost =0;
                double profit=0;
                for (Bill bill: listBillCostFromDateToDate) {
                    if(ld.compareTo(bill.getTime())==0){
                        cost =cost + bill.getPrice();
                    }
                }
                for(Bill bill: listBillProfitFromDateToDate){
                    if(ld.compareTo(bill.getTime())==0){
                        profit=profit+ bill.getPrice();
                    }
                }
                System.out.println(profit-cost);
                listBillProfitFromDateToDateDay.add(new XYChart.Data<>(d+"",profit-cost));
            }
            return listBillProfitFromDateToDateDay;
        }else if(granularity.equals(StatisticGranularity.Month)){
            listBillProfitFromDateToDateMonth =FXCollections.observableArrayList();
            for (LocalDate ld =fromDate;ld.getYear()!= toDate.getYear()||ld.getMonthValue()!=(toDate.getMonthValue()+1);ld=ld.plusMonths(1)){
                double cost =0;
                double profit=0;
                for (Bill bill: listBillCostFromDateToDate) {
                    if(ld.getMonth()==bill.getTime().getMonth()&&ld.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                for (Bill bill: listBillProfitFromDateToDate) {
                    if(ld.getMonth()==bill.getTime().getMonth()&&ld.getYear()==bill.getTime().getYear()){
                        profit =profit+ bill.getPrice();
                    }
                }
                listBillProfitFromDateToDateMonth.add(new XYChart.Data<>(ld.getMonthValue()+"/"+ld.getYear(),profit-cost));
            }
            return listBillProfitFromDateToDateMonth;
        } else if (granularity.equals(StatisticGranularity.Quarter)) {
            listBillProfitFromDateToDateQuarter=FXCollections.observableArrayList();
            for (LocalDate ld =fromDate;ld.getYear()<= (toDate.getYear()-1)||ld.getMonthValue()<(toDate.getMonthValue());ld=ld.plusMonths(3)){
                double cost =0;
                double profit=0;
                for (Bill bill: listBillCostFromDateToDate) {
                    if(ld.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(ld.getMonthValue()+2)&&ld.getYear()==bill.getTime().getYear()){
                        cost =cost+ bill.getPrice();
                    }
                }
                for (Bill bill: listBillProfitFromDateToDate) {
                    if(ld.getMonthValue()<=bill.getTime().getMonthValue()&&bill.getTime().getMonthValue()<=(ld.getMonthValue()+2)&&ld.getYear()==bill.getTime().getYear()){
                        profit =profit+ bill.getPrice();
                    }
                }
                listBillProfitFromDateToDateQuarter.add(new XYChart.Data<>((ld.getMonthValue()/3+1)+"/"+ld.getYear(),profit-cost));
            }
            return listBillProfitFromDateToDateQuarter;
        }else if(granularity.equals(StatisticGranularity.Year)){
            listBillProfitFromDateToDateYear=FXCollections.observableArrayList();
            for(LocalDate ld=fromDate;ld.getYear()!=(toDate.getYear()+1);ld=ld.plusYears(1)){
                double cost =0;
                double profit=0;
                for (Bill bill: listBillCostFromDateToDate) {
                    if(bill.getTime().getYear()==ld.getYear()){
                        cost=cost+bill.getPrice();
                    }
                }
                for (Bill bill: listBillProfitFromDateToDate) {
                    if(bill.getTime().getYear()==ld.getYear()){
                        profit=profit+bill.getPrice();
                    }
                }
                listBillProfitFromDateToDateYear.add(new XYChart.Data<>(ld.getYear()+"",profit-cost));
            }
            return listBillProfitFromDateToDateYear;

        }
        return null;
    }
    public ObservableList<PieChart.Data> getAllTypesOfCostsStatistics(LocalDate fromDate, LocalDate toDate) throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        listBill=FXCollections.observableArrayList();
        ObservableList<OtherBill> a = adapter.getAllOtherBills();
        ObservableList<ImportBill> b= adapter.getAllImportBills();
        ObservableList<StaffBill> c = adapter.getAllStaffBills();
        ObservableList<SaleBill> d = adapter.getAllSaleBills();
        double costSale =0;
        double costSpace =0;
        double costStaff =0;
        double costTranSport =0;
        double costImport =0;
        double costOther = 0;
        for(LocalDate ld=fromDate;ld.compareTo(toDate)!=0;ld=ld.plusDays(1) ){
            //Vận chuyển

            for (OtherBill ob:a) {
                if(ob.getName().equals("Vận chuyển")&&ob.getTime().compareTo(ld)==0){
                    costTranSport = costTranSport+ ob.getPrice();
                }
            }

            //Mặt bằng

            for (OtherBill ob:a) {
                if(ob.getName().equals("Mặt bằng")&&ob.getTime().compareTo(ld)==0){
                    costSpace = costSpace+ob.getPrice();
                }
            }

            //Chi phí nhân viên

            for (StaffBill sb:c) {
                if(sb.getTime().compareTo(ld)==0){
                    costStaff = costStaff+sb.getPrice();
                }
            }
            //Chi phí nhập hàng

            for (ImportBill ib:b) {
                if(ib.getTime().compareTo(ld)==0){
                    costImport = costImport+ib.getPrice();
                }
            }
            //Số lượng hàng bán được
            for (SaleBill sbl:d) {
                if(sbl.getTime().compareTo(ld)==0){
                    costSale = costSale+sbl.getPrice();
                }
            }
            // chi phi khac
            for(OtherBill bill :a){
                if(bill.getName().equals("Mặt bằng")==false&&bill.getName().equals("Vận chuyển")==false&&bill.getTime().compareTo(ld)==0){
                    costOther = costOther + bill.getPrice();
                }
            }
        }
        listBill.add(new PieChart.Data("Mặt bằng",costSpace));
        listBill.add(new PieChart.Data("nhân viên",costStaff));
        listBill.add(new PieChart.Data("nhập hàng",costImport));
        listBill.add(new PieChart.Data("Vận chuyển",costTranSport));
        listBill.add(new PieChart.Data("bán được",costSale));
        listBill.add(new PieChart.Data("Chi phí khác",costOther));
        return listBill;
    }
}