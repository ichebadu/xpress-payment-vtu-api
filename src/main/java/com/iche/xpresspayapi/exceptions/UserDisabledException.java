package com.iche.xpresspayapi.exceptions;

public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String accountIsDisabled){
        super(accountIsDisabled);
    }
}
