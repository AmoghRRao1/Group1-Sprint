package com.Group1.Sprint.Exceptions;

public class UserDetailsMissingException extends RuntimeException{
    public UserDetailsMissingException(String message)
    {
        super(message);
    }
}
