/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import java.io.Serializable;

/**
 *
 * @author Marc-Daniel
 */
public class CreditCard implements Serializable{
    
    private String number;
    private String name;
    private int month;
    private int year;
    
    public CreditCard(String number)
    {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public CreditCard() 
    {
        this("");
    }
    
    public void setNumber(String number)
    {
        this.number = number;
    }
    
    public String getNumber()
    {
        return number;
    }
    
    @Override
    public String toString()
    {
        return getNumber();
    }
    
}
