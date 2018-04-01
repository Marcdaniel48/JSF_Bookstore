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
public abstract class TotalSalesBean 
{
    private Timestamp lastRecordedSale;
    private BigDecimal totalSales;

    public Timestamp getLastRecordedSale() {
        return lastRecordedSale;
    }

    public void setLastRecordedSale(Timestamp lastRecordedSale) {
        this.lastRecordedSale = lastRecordedSale;
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
