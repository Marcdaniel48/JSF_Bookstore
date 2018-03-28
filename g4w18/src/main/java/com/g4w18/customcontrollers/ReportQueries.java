/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ClientWithTotalSales;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marc-Daniel
 */
public class ReportQueries implements Serializable {
    
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
            
            query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where i.detail_Id = ?1 group by b.isbn_number").setParameter(1, invoiceId);
            BigDecimal totalSalesForBook = (BigDecimal) query.getSingleResult();
            bookWithTotalSales.setTotalSales(totalSalesForBook);
            
            return bookWithTotalSales;
        }
        return null;
    }
    
    public ClientWithTotalSales findClientWithTotalSalesByDetail(int invoiceId)
    {
        Query query = em.createNativeQuery("Select c.* from client c right join Master_Invoice m on c.client_id = m.user_id "
                + "right join Invoice_Detail i on m.invoice_id = m.invoice_id where i.detail_Id = ?1", Client.class).setParameter(1, invoiceId);
        
        List<Client> clients = query.getResultList();
        
        if (!clients.isEmpty())
        {
            ClientWithTotalSales clientWithTotalSales = new ClientWithTotalSales();
            clientWithTotalSales.setClient(clients.get(0));
            
            query = em.createNativeQuery("Select max(m.sale_date) from client c right join Master_Invoice m on c.client_id = m.user_id "
                + "right join Invoice_Detail i on m.invoice_id = m.invoice_id where i.detail_Id = ?1 and c.client_id = ?2")
                    .setParameter(1, invoiceId).setParameter(2, clients.get(0).getClientId());
            
            Timestamp lastBoughtDate = (Timestamp) query.getSingleResult();
            clientWithTotalSales.setLastBoughtDate(lastBoughtDate);
            
            query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id right join Client c on m.user_id = c.client_id where i.detail_Id = ?1 group by b.isbn_number")
                    .setParameter(1, invoiceId);
            
            BigDecimal totalSalesForClient = (BigDecimal) query.getSingleResult();
            clientWithTotalSales.setTotalSales(totalSalesForClient);
            
            return clientWithTotalSales;
        }
        return null;
    }
    
}
