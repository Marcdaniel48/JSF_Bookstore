/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Author;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Marc-Daniel
 */
public class AuthorWithTotalSales 
{
    private Author author;
    private Timestamp lastSoldDate;
    private BigDecimal totalSales;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }
    
    public BigDecimal getTotalSalesRounded()
    {
        return totalSales.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
    
    public Timestamp getLastSoldDate()
    {
        return lastSoldDate;
    }

    public void setLastSoldDate(Timestamp lastSoldDate) {
        this.lastSoldDate = lastSoldDate;
    }
}
