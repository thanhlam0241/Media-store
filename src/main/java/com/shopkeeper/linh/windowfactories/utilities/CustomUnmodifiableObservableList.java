package com.shopkeeper.linh.windowfactories.utilities;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CustomUnmodifiableObservableList<E> implements ObservableList<E> {
    protected boolean isAscend;
    //override if you dont want to use this algorithm
    protected void sortItems(){
        items.sort(comparator);
    }
    //change comparator you need to resort items
    protected Comparator<E> comparator;
    protected ObservableList<E> items;
    private Collection<E> source;
    private final ListChangeListener<E> listener;
    protected CustomUnmodifiableObservableList(){
        items = FXCollections.observableArrayList();
        source = null;
        comparator = null;
        listener = new ListChangeListener<E>() {
            @Override
            public void onChanged(Change<? extends E> c) {
                while (c.next()){
                    if(c.wasReplaced()) {
                        System.out.println("CustomUnmodifiableObservableList cannot track the change when source was Replaced.");

                    }
                    else if(c.wasPermutated()) {
                        System.out.println("CustomUnmodifiableObservableList cannot track the change when source was Permutated.");

                    }
                    else if(c.wasUpdated()) {
                        System.out.println("CustomUnmodifiableObservableList cannot track the change when source was Updated.");

                    }
                    else if(c.wasAdded()) {
                        if(isAscend){
                            for(int i = c.getFrom(); i < c.getTo(); i++){
                                var item =  c.getList().get(i);
                                int j = items.size() - 1;
                                for(; j > -1 ; j--){
                                    if(comparator.compare(items.get(j), item) <= 0){
                                        items.add(j + 1, item);
                                        break;
                                    }
                                }
                                if(j == -1){
                                    items.add(j + 1, item);
                                }
                            }
                        }
                        else{
                            for(int i = c.getFrom(); i < c.getTo(); i++){
                                var item =  c.getList().get(i);
                                int j = 0;
                                for(; j < items.size(); j--){
                                    if(comparator.compare(items.get(j), item) >= 0){
                                        items.add(j, item);
                                        break;
                                    }
                                }
                                if(j == items.size()){
                                    items.add(j, item);
                                }
                            }
                        }
                    }
                    else if(c.wasRemoved()) {
                        for(var removeItem : c.getRemoved()){
                            items.remove(removeItem);
                        }
                    }
                }
            }
        };
    }
    public void setSource(Collection<E> source){
        if(this.source == source) return;
        if(this.source != null){
            ((ObservableList<E>)this.source).removeListener(listener);
        }
        this.source = source;
        this.items.clear();
        this.items.addAll(source);
        this.items.sort(comparator);
        if(source instanceof ObservableList<E>){
            ((ObservableList<E>)this.source).addListener(listener);
        }
    }

    //region ObservableList implements
    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        items.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        items.remove(listener);
    }

    @Override
    public boolean addAll(E... elements) {
        throw new RuntimeException("CustomObservableList can not addAll()");
    }

    @Override
    public boolean setAll(E... elements) {
        throw new RuntimeException("CustomObservableList can not setAll()");
    }

    @Override
    public boolean setAll(Collection<? extends E> col) {
        throw new RuntimeException("CustomObservableList can not setAll()");
    }

    @Override
    public boolean removeAll(E... elements) {
        throw new RuntimeException("CustomObservableList can not removeAll()");
    }

    @Override
    public boolean retainAll(E... elements) {
        throw new RuntimeException("CustomObservableList can not retainAll()");
    }

    @Override
    public void remove(int from, int to) {
        throw new RuntimeException("CustomObservableList can not retainAll()");
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return items.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(E e) {
        throw new RuntimeException("CustomObservableList can not add(E e)");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("CustomObservableList can not remove(Object o)");
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        throw new RuntimeException("CustomObservableList can not containsAll()");
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        throw new RuntimeException("CustomObservableList can not addAll()");
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        throw new RuntimeException("CustomObservableList can not addAll()");
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new RuntimeException("CustomObservableList can not removeAll()");
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new RuntimeException("CustomObservableList can not retainAll()");
    }

    @Override
    public void clear() {
        throw new RuntimeException("CustomObservableList can not clear()");
    }

    @Override
    public E get(int index) {
        return items.get(index);
    }

    @Override
    public E set(int index, E element) {
        throw new RuntimeException("CustomObservableList can not set()");
    }

    @Override
    public void add(int index, E element) {
        throw new RuntimeException("CustomObservableList can not add()");
    }

    @Override
    public E remove(int index) {
        throw new RuntimeException("CustomObservableList can not remove()");
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return items.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return items.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return items.listIterator(index);
    }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return items.subList(fromIndex, toIndex);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        items.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        items.removeListener(listener);
    }
    //endregion
}
