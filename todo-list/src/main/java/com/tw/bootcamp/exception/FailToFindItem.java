package com.tw.bootcamp.exception;

public class FailToFindItem extends Exception {
    public FailToFindItem(String message){
        super(message);
    }

    public FailToFindItem(String message, Throwable cause){
        super(message, cause);
    }
}
