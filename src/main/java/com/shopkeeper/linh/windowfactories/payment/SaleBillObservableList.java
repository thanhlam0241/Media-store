package com.shopkeeper.linh.windowfactories.payment;

import com.shopkeeper.linh.models.SaleBill;
import com.shopkeeper.linh.windowfactories.utilities.CustomUnmodifiableObservableList;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class SaleBillObservableList extends CustomUnmodifiableObservableList<SaleBill> {
    private Comparator<SaleBill> titleAscending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private Comparator<SaleBill> titleDescending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o2.getName().compareTo(o1.getName());
        }
    };
    private Comparator<SaleBill> timeAscending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    };
    private Comparator<SaleBill> timeDescending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o2.getTime().compareTo(o1.getTime());
        }
    };
    private Comparator<SaleBill> priceAscending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    };
    private Comparator<SaleBill> priceDescending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return Double.compare(o2.getPrice(), o1.getPrice());
        }
    };
    private Comparator<SaleBill> customerNameAscending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o1.getCustomer().getName().compareTo(o2.getCustomer().getName());
        }
    };
    private Comparator<SaleBill> customerNameDescending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o2.getCustomer().getName().compareTo(o1.getCustomer().getName());
        }
    };
    private Comparator<SaleBill> locationAscending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o1.getLocation().compareTo(o2.getLocation());
        }
    };
    private Comparator<SaleBill> locationDescending = new Comparator<SaleBill>() {
        @Override
        public int compare(SaleBill o1, SaleBill o2) {
            return o2.getLocation().compareTo(o1.getLocation());
        }
    };
    public SaleBillObservableList(ObservableList<SaleBill> saleBills) {
        super();
        comparator = timeAscending;
        setSource(saleBills);
        order = SaleBillListOrder.TimeAscending;
        isAscend = true;
    }
    private SaleBillListOrder order;
    public void setOrder(SaleBillListOrder newOrder){
        if(newOrder == order) return;
        switch (newOrder){
            case TimeAscending -> {
                comparator = timeAscending;
                isAscend = true;
            }
            case TimeDescending -> {
                comparator = timeDescending;
                isAscend = false;
            }
            case TitleAscending -> {
                comparator = titleAscending;
                isAscend = true;
            }
            case TitleDescending -> {
                comparator = titleDescending;
                isAscend = false;
            }
            case PriceAscending -> {
                comparator = priceAscending;
                isAscend = true;
            }
            case PriceDescending -> {
                comparator = priceDescending;
                isAscend = false;
            }
            case CustomerNameAscending -> {
                comparator = customerNameAscending;
                isAscend = true;
            }
            case CustomerNameDescending -> {
                comparator = customerNameDescending;
                isAscend = false;
            }
            case LocationAscending -> {
                comparator = locationAscending;
                isAscend = true;
            }
            case LocationDescending -> {
                comparator = locationDescending;
                isAscend = false;
            }
        }
        if((newOrder == SaleBillListOrder.TimeDescending && order == SaleBillListOrder.TimeAscending)
                || (newOrder == SaleBillListOrder.TimeAscending && order == SaleBillListOrder.TimeDescending)
                || (newOrder == SaleBillListOrder.TitleDescending && order == SaleBillListOrder.TitleAscending)
                || (newOrder == SaleBillListOrder.TitleAscending && order == SaleBillListOrder.TitleDescending)
                || (newOrder == SaleBillListOrder.PriceDescending && order == SaleBillListOrder.PriceAscending)
                || (newOrder == SaleBillListOrder.PriceAscending && order == SaleBillListOrder.PriceDescending)
                || (newOrder == SaleBillListOrder.CustomerNameDescending && order == SaleBillListOrder.CustomerNameAscending)
                || (newOrder == SaleBillListOrder.CustomerNameAscending && order == SaleBillListOrder.CustomerNameDescending)
                || (newOrder == SaleBillListOrder.LocationDescending && order == SaleBillListOrder.LocationAscending)
                || (newOrder == SaleBillListOrder.LocationAscending && order == SaleBillListOrder.LocationDescending)){
            SaleBill temp;
            for(int i = items.size()/2 - 1, j = items.size() - i - 1; i > -1; i--, j++){
                temp = items.get(i);
                items.set(i, items.get(j));
                items.set(j, temp);
            }
            order = newOrder;
            return;
        }
        order = newOrder;
        sortItems();
    }
    public SaleBillListOrder getOrder(){
        return order;
    }
}
