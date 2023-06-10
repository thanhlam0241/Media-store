package com.shopkeeper.linh.windowfactories.feedback;

import com.shopkeeper.linh.models.Feedback;
import com.shopkeeper.linh.windowfactories.utilities.CustomUnmodifiableObservableList;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class FeedbackObservableList extends CustomUnmodifiableObservableList<Feedback> {
    private FeedbackListComparators comparators;
    public FeedbackObservableList(ObservableList<Feedback> feedbacks) {
        super();
        comparators = new FeedbackListComparators();
        comparator = comparators.getTimeAscending();
        setSource(feedbacks);
        order = FeedbackListOrder.TimeAscending;
        isAscend = true;
    }
    private FeedbackListOrder order;
    public void setOrder(FeedbackListOrder newOrder){
        if(newOrder == order) return;
        switch (newOrder){
            case TimeAscending -> {
                comparator = comparators.getTimeAscending();
                isAscend = true;
            }
            case TimeDescending -> {
                comparator = comparators.getTimeDescending();
                isAscend = false;
            }
            case TitleAscending -> {
                comparator = comparators.getTitleAscending();
                isAscend = true;
            }
            case TitleDescending -> {
                comparator = comparators.getTitleDescending();
                isAscend = false;
            }
        }
        if((newOrder == FeedbackListOrder.TimeDescending && order == FeedbackListOrder.TimeAscending)
        || (newOrder == FeedbackListOrder.TimeAscending && order == FeedbackListOrder.TimeDescending)
        || (newOrder == FeedbackListOrder.TitleDescending && order == FeedbackListOrder.TitleAscending)
        || (newOrder == FeedbackListOrder.TitleAscending && order == FeedbackListOrder.TitleDescending)){
            Feedback temp;
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
    public FeedbackListOrder getOrder(){
        return order;
    }
}
