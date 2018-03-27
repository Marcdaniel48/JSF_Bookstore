/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.entities.Book;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marc-Daniel
 */
public class CustomQueries implements Serializable {
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public BookWithTotalSales findBookWithTotalSalesByDetail(int invoiceId)
    {
        Query query = em.createNativeQuery("Select b.* from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where i.detail_Id = ?1", Book.class).setParameter(1, invoiceId);
        
        List<Book> books = query.getResultList();
        
        if (!books.isEmpty())
        {
            BookWithTotalSales bookWithTotalSales = new BookWithTotalSales();
            bookWithTotalSales.setBook(books.get(0));
            
            query = em.createNativeQuery("Select max(sale_date) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where i.detail_Id = ?1 and i.book_id = ?2")
                    .setParameter(1, invoiceId).setParameter(2, books.get(0).getBookId());
            Timestamp lastSoldDate = (Timestamp) query.getSingleResult();
            bookWithTotalSales.setLastSoldDate(lastSoldDate);
            
            query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate + pst_rate + hst_rate)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where i.detail_Id = ?1 group by b.isbn_number").setParameter(1, invoiceId);
            BigDecimal totalSalesForBook = (BigDecimal) query.getSingleResult();
            bookWithTotalSales.setTotalSales(totalSalesForBook);
            
            return bookWithTotalSales;
        }
        return null;
    }
    
}
