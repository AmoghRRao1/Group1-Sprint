package com.Group1.Sprint.Exceptions;

public class MatchDoesNotExistException extends RuntimeException{
    public MatchDoesNotExistException(String message)
    {
        super(message);
    }
}