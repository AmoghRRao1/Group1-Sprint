package com.Group1.Sprint.Exceptions;

public class TournamentAlreadyExistsException extends RuntimeException{
    public TournamentAlreadyExistsException(String message)
    {
        super(message);
    }
}
