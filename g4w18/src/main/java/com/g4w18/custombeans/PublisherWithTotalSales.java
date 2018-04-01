/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Marc-Daniel
 */
public class PublisherWithTotalSales 
{
    private String publisher;
    private Timestamp lastSoldDate;
    private BigDecimal totalSales;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Timestamp getLastSoldDate() {
        return lastSoldDate;
    }

    public void setLastSoldDate(Timestamp lastSoldDate) {
        this.lastSoldDate = lastSoldDate;
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
    
}
