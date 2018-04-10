package com.g4w18.custombeans;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Salman Haidar
 */
public class TopSellersResultBean {
    
    private String title;
    private String isbn;
    private BigDecimal totalCost;
    private BigDecimal totalSales;
    private BigDecimal totalProfit;

    public TopSellersResultBean(String title, String isbn, BigDecimal totalCost, BigDecimal totalSales, BigDecimal totalProfit) {
        this.title = title;
        this.isbn = isbn;
        this.totalCost = totalCost.setScale(2, RoundingMode.CEILING);
        this.totalSales = totalSales.setScale(2, RoundingMode.CEILING);
        this.totalProfit = totalProfit.setScale(2, RoundingMode.CEILING);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    
    
}
