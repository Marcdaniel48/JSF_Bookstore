package com.g4w18.custombeans;

import java.io.Serializable;

/**
 * Credit card bean used to store a credit card's number, name, and expiration month and year.
 * 
 * @author Marc-Daniel
 */
public class CreditCard implements Serializable{
    
    // Credit card number
    private String number;
    
    // Credit card holder name
    private String name;
    
    // Credit card expiration month and year
    private int month;
    private int year;
    
    /**
     * Empty CreditCard constructor. Instantiates a CreditCard object with an empty card number.
     */
    public CreditCard() 
    {
        this("");
    }
    
    /**
     * CreditCard constructor. Instantiates a CreditCard object with a given credit card number.
     * @param number 
     */
    public CreditCard(String number)
    {
        this.number = number;
    }

    /**
     * Getter method. Returns the credit card's holder name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method. Sets the credit card's holder name.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method. Returns the credit card's expiration month.
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter method. Sets the credit card's expiration month.
     * @param month 
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter method. Returns the credit card's expiration year.
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter method. Sets the credit card's expiration year.
     * @param year 
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Setter method. Sets the credit card's credit card number.
     * @param number 
     */
    public void setNumber(String number)
    {
        this.number = number;
    }
    
    /**
     * Getter method. Returns the credit card's credit card number.
     * @return number
     */
    public String getNumber()
    {
        return number;
    }
    
    /**
     * toString method that simply returns the credit card's information.
     * @return 
     */
    @Override
    public String toString()
    {
        return name + "," + number + "," + month + "," + year;
    }
    
}
