package com.iche.xpresspayapi.exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
