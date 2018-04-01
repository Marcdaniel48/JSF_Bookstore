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
public class PublisherWithTotalSales extends TotalSalesBean
{
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
