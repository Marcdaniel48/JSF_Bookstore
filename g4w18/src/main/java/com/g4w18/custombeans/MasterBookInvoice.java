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

/**
 *
 * @author Marc-Daniel
 */
public class MasterBookInvoice implements Serializable
{
    private Book book;
    private MasterInvoice master;
    private InvoiceDetail invoice;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public MasterInvoice getMaster() {
        return master;
    }

    public void setMaster(MasterInvoice master) {
        this.master = master;
    }

    public InvoiceDetail getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDetail invoice) {
        this.invoice = invoice;
    }
    
}
