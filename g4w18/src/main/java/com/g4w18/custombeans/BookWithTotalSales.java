/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Book;
import java.io.Serializable;

/**
 *
 * @author Marc-Daniel
 */
public class BookWithTotalSales extends TotalSalesBean implements Serializable
{
    private Book book;

    public Book getBook() {
        return book;
    } 
    
    public void setBook(Book book) {
        this.book = book;
    } 
}
