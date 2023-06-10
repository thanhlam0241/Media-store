package com.shopkeeper.linh.windowfactories.customer;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListComparators;
import com.shopkeeper.linh.windowfactories.feedback.FeedbackListOrder;
import com.shopkeeper.linh.windowfactories.utilities.CustomUnmodifiableObservableList;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class CustomerObservableList  extends CustomUnmodifiableObservableList<Customer> {
    private Comparator<Customer> nameAscending = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private Comparator<Customer> nameDescending = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o2.getName().compareTo(o1.getName());
        }
    };
    private Comparator<Customer> locationAscending = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.getDefaultLocation().compareTo(o2.getDefaultLocation());
        }
    };
    private Comparator<Customer> locationDescending = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o2.getDefaultLocation().compareTo(o1.getDefaultLocation());
        }
    };
    public CustomerObservableList(ObservableList<Customer> customers) {
        super();
        comparator = nameAscending;
        setSource(customers);
        order = CustomerListOrder.NameAscending;
        isAscend = true;
    }
    private CustomerListOrder order;
    public void setOrder(CustomerListOrder newOrder){
        if(newOrder == order) return;
        switch (newOrder){
            case NameDescending -> {
                comparator = nameDescending;
                isAscend = false;
            }
            case NameAscending -> {
                comparator = nameAscending;
                isAscend = true;
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
        if((newOrder == CustomerListOrder.NameDescending && order == CustomerListOrder.NameAscending)
                || (newOrder == CustomerListOrder.NameAscending && order == CustomerListOrder.NameDescending)
                || (newOrder == CustomerListOrder.LocationDescending && order == CustomerListOrder.LocationAscending)
                || (newOrder == CustomerListOrder.LocationAscending && order == CustomerListOrder.LocationDescending)){
            Customer temp;
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
    public CustomerListOrder getOrder(){
        return order;
    }
}
