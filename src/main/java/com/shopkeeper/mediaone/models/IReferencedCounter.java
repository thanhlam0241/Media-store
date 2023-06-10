package com.shopkeeper.mediaone.models;

public interface IReferencedCounter {
    //Count times anything reference to this object in SQL (the same as in cache)
    //Don't accept to delete when countTimesToBeReferenced() > 0
    int countTimesToBeReferenced();
    //Increase countTimesToBeReferenced() by one when a object have just reference to this object
    //This object cannot do it automatically, please call this method when you make any object reference to this object
    void increaseTimesToBeReferenced();
    //Decrease countTimesToBeReferenced() by one when a object that referenced to this object has removed this reference
    //This object cannot do it automatically, please call this method when you make any object reference to this object
    void decreaseTimesToBeReferenced() throws Exception;
}
