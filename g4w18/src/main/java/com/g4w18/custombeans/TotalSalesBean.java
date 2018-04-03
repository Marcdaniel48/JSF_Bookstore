package com.g4w18.custombeans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Abstract class used to represent a bean that holds the total sales, as well as the last recorded sale or purchase date of a particular record.
 * The record could be a book, a client, an author, or a publisher.
 * 
 * @author Marc-Daniel
 */
public abstract class TotalSalesBean 
{
    // Last record sale or purchase
    private Timestamp lastRecordedSale;
    
    // Total sales
    private BigDecimal totalSales;

    /**
     * Getter method. Returns the last recorded sale or purchase date
     * @return lastRecordedSale
     */
    public Timestamp getLastRecordedSale() {
        return lastRecordedSale;
    }

    /**
     * Setter method. Sets the last recorded sale or purchase date
     * @param lastRecordedSale 
     */
    public void setLastRecordedSale(Timestamp lastRecordedSale) {
        this.lastRecordedSale = lastRecordedSale;
    }

    /**
     * Getter method. Returns the total sales.
     * @return totalSales
     */
    public BigDecimal getTotalSales() {
        return totalSales;
    }
    
    /**
     * Rounds the total sales and returns it.
     * @return 
     */
    public BigDecimal getTotalSalesRounded()
    {
        return totalSales.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Setter method. Sets the total sales.
     * @param totalSales 
     */
    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}
