package com.iche.xpresspayapi.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String message){
        super(message);
    }
}
