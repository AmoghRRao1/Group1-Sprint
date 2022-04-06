package com.Group1.Sprint.Exceptions;

public class TournamentDoesNotExistException extends RuntimeException{
    public TournamentDoesNotExistException(String message)
    {
        super(message);
    }
}