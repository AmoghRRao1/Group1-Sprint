package com.Group1.Sprint.Models;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "bidder")
public class BidderModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int bidderId;

    @Column(name="password")
    public String password;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;

    @Column(name="points")
    private int points=0;

    public BidderModel(){}
    public BidderModel(Map<String,String> details)
    {
        if(details.get("email")!=null)
        {
            this.email = details.get("email");
        }
        if(details.get("password")!=null)
        {
            this.password = details.get("password");
        }
        this.name = details.get("name");
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
