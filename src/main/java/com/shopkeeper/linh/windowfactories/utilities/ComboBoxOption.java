package com.shopkeeper.linh.windowfactories.utilities;

import java.security.InvalidParameterException;

public class ComboBoxOption<T> {
    public static <K> ComboBoxOption<K> SelectAllOption(String displayName){
        ComboBoxOption<K> x = new ComboBoxOption<>();
        x._displayName = displayName;
        x._value = null;
        return x;
    }
    public static <K> boolean isSelectAllOption(ComboBoxOption<K> option){
        return option._value == null;
    }
    private T _value;
    private String _displayName;
    private ComboBoxOption() {


    }
    public ComboBoxOption(T value, String displayName) throws InvalidParameterException{
        if(value == null) throw new InvalidParameterException("value is not null");
        if(displayName == null) throw new InvalidParameterException("displayName is not null");
        _value = value;
        _displayName = displayName;
    }
    public T getValue(){
        return _value;
    }

    @Override
    public String toString() {
        return _displayName;
    }
}
