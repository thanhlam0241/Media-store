package com.shopkeeper.linh.windowfactories.feedback;

import com.shopkeeper.linh.models.Feedback;

import java.util.Comparator;

public class FeedbackListComparators {
    private Comparator<Feedback> titleAscending = new Comparator<Feedback>() {
        @Override
        public int compare(Feedback o1, Feedback o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };
    public Comparator<Feedback> getTitleAscending(){
        return titleAscending;
    }
    private Comparator<Feedback> titleDescending = new Comparator<Feedback>() {
        @Override
        public int compare(Feedback o1, Feedback o2) {
            return o2.getTitle().compareTo(o1.getTitle());
        }
    };
    public Comparator<Feedback> getTitleDescending(){
        return titleDescending;
    }
    private Comparator<Feedback> timeAscending = new Comparator<Feedback>() {
        @Override
        public int compare(Feedback o1, Feedback o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    };
    public Comparator<Feedback> getTimeAscending(){
        return timeAscending;
    }
    private Comparator<Feedback> timeDescending = new Comparator<Feedback>() {
        @Override
        public int compare(Feedback o1, Feedback o2) {
            return o2.getTime().compareTo(o1.getTime());
        }
    };
    public Comparator<Feedback> getTimeDescending(){
        return timeDescending;
    }
}
