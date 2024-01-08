package com.iche.xpresspayapi.exceptions;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException(String message) {
        super(message);
    }
}


