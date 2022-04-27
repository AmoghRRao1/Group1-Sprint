package com.Group1.Sprint.Models;

public class JWTresponse {
    String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public JWTresponse(String token) {
        Token = token;
    }

    public JWTresponse() {
    }

}
