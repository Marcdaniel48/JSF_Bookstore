/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Book;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

/**
 *
 * @author Marc-Daniel
 */
public class MasterBookInvoice implements Serializable
{
    private Book book;
    private Timestamp lastSoldDate;
    private BigDecimal totalSales;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
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
