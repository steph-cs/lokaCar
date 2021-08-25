package com.web.lokacar.exceptions;

public class EmailExistsException extends Exception {

    public EmailExistsException(String msg){
        super(msg);
    }


    private static final long serialVersionUID = 1L;
}
