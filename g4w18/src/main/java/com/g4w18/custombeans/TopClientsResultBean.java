package com.g4w18.custombeans;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Salman Haidar
 */
public class TopClientsResultBean {
    private String username;
    private BigDecimal grossValue;
    private BigDecimal totalCost;
    private BigDecimal totalSales;
    private BigDecimal totalProfit;

    public TopClientsResultBean(String username, BigDecimal grossValue, BigDecimal totalCost, BigDecimal totalSales, BigDecimal totalProfit) {
        this.username = username;
        this.grossValue = grossValue.setScale(2, RoundingMode.CEILING);
        this.totalCost = totalCost.setScale(2, RoundingMode.CEILING);
        this.totalSales = totalSales.setScale(2, RoundingMode.CEILING);
        this.totalProfit = totalProfit.setScale(2, RoundingMode.CEILING);
    }
    
    
    
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }
}
