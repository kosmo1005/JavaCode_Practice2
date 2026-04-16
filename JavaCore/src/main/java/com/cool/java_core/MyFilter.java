package com.cool.java_core;



public class MyFilter {

    public <T> T[] customFilter (T[] array, Filter filter){
        for (T element : array) {
            filter.apply(element);
        }
        return array;
    }
}
