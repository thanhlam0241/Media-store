package com.shopkeeper.linh.windowfactories.utilities;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ComboBoxOptionList<E> implements ObservableList<ComboBoxOption<E>> {
    private ObservableList<ComboBoxOption<E>> items;
    private final ListChangeListener<E> listener = new ListChangeListener<E>() {
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
                    for(int i = c.getFrom(); i < c.getTo(); i++){
                        var item =  c.getList().get(i);
                        items.add(i + 1, new ComboBoxOption<>(item, getTitle(item)));
                    }
                }
                else if(c.wasRemoved()) {
                    var index = c.getTo();
                    if(c.getFrom() != index) throw new RuntimeException("Algorithm does not support this situation.");
                    items.remove(index + 1, index + items.size() - c.getList().size());
                }
            }
        }
    };
    public ComboBoxOptionList(ObservableList<E> list){
        items = FXCollections.observableArrayList();
        items.add(ComboBoxOption.SelectAllOption("Chọn tất cả"));
        for(var x : list){
            items.add(new ComboBoxOption<>(x, getTitle(x)));
        }
        list.addListener(listener);
    }
    protected abstract String getTitle(E obj);
    //region ObservableList implements
    @Override
    public void addListener(ListChangeListener<? super ComboBoxOption<E>> listener) {
        items.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super ComboBoxOption<E>> listener) {
        items.remove(listener);
    }

    @Override
    public boolean addAll(ComboBoxOption<E>... elements) {
        throw new RuntimeException("CustomObservableList can not addAll()");
    }

    @Override
    public boolean setAll(ComboBoxOption<E>... elements) {
        throw new RuntimeException("CustomObservableList can not setAll()");
    }

    @Override
    public boolean setAll(Collection<? extends ComboBoxOption<E>> col) {
        throw new RuntimeException("CustomObservableList can not setAll()");
    }

    @Override
    public boolean removeAll(ComboBoxOption<E>... elements) {
        throw new RuntimeException("CustomObservableList can not removeAll()");
    }

    @Override
    public boolean retainAll(ComboBoxOption<E>... elements) {
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
    public Iterator<ComboBoxOption<E>> iterator() {
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
    public boolean add(ComboBoxOption<E> e) {
        throw new RuntimeException("CustomObservableList can not add(ComboBoxOption<E> e)");
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
    public boolean addAll(@NotNull Collection<? extends ComboBoxOption<E>> c) {
        throw new RuntimeException("CustomObservableList can not addAll()");
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends ComboBoxOption<E>> c) {
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
    public ComboBoxOption<E> get(int index) {
        return items.get(index);
    }

    @Override
    public ComboBoxOption<E> set(int index, ComboBoxOption<E> element) {
        throw new RuntimeException("CustomObservableList can not set()");
    }

    @Override
    public void add(int index, ComboBoxOption<E> element) {
        throw new RuntimeException("CustomObservableList can not add()");
    }

    @Override
    public ComboBoxOption<E> remove(int index) {
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
    public ListIterator<ComboBoxOption<E>> listIterator() {
        return items.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<ComboBoxOption<E>> listIterator(int index) {
        return items.listIterator(index);
    }

    @NotNull
    @Override
    public List<ComboBoxOption<E>> subList(int fromIndex, int toIndex) {
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
