package com.g4w18.custombeans;

import java.math.BigDecimal;

/**
 *
 * @author Salman Haidar
 */
public class TopSellersResultBean {
    
    private String title;
    private String isbn;
    private BigDecimal totalSales;

    public TopSellersResultBean(String title, String isbn, BigDecimal totalSales) {
        this.title = title;
        this.isbn = isbn;
        this.totalSales = totalSales;
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
    
    
    
}
