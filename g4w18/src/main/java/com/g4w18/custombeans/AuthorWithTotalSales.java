/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Author;

/**
 *
 * @author Marc-Daniel
 */
public class AuthorWithTotalSales extends TotalSalesBean
{
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
