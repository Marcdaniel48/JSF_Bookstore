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
import java.util.ArrayList;
import java.util.Date;
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
    
    public List<BookWithTotalSales> findBooksWithTotalSalesBetweenDates(Date date1, Date date2)
    {
        List<BookWithTotalSales> booksWithTotalSales = new ArrayList<>();
        
        Query query = em.createNativeQuery("Select b.* from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where m.sale_date between ?1 and ?2", Book.class)
                .setParameter(1, date1).setParameter(2, date2);
        
        List<Book> books = query.getResultList();
        
        if (!books.isEmpty())
        {
            for(Book book : books)
            {
                BookWithTotalSales bookWithTotalSales = new BookWithTotalSales();
                bookWithTotalSales.setBook(book);

                query = em.createNativeQuery("Select max(sale_date) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.book_id = ?1").setParameter(1, book.getBookId());
                Timestamp lastSoldDate = (Timestamp) query.getSingleResult();
                bookWithTotalSales.setLastSoldDate(lastSoldDate);

                query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.book_id = ?1 group by b.isbn_number").setParameter(1, book.getBookId());
                BigDecimal totalSalesForBook = (BigDecimal) query.getSingleResult();
                bookWithTotalSales.setTotalSales(totalSalesForBook);
                
                booksWithTotalSales.add(bookWithTotalSales);
            }
        }
        return booksWithTotalSales;
    }
    
    public List<ClientWithTotalSales> findClientsWithTotalSalesBetweenDates(Date date1, Date date2)
    {
        List<ClientWithTotalSales> clientsWithTotalSales = new ArrayList<>();
        
        Query query = em.createNativeQuery("Select c.* from client c right join Master_Invoice m on c.client_id = m.client_id where m.sale_date between ?1 and ?2", Client.class)
                .setParameter(1, date1).setParameter(2, date2);
        
        List<Client> clients = query.getResultList();
        
        if (!clients.isEmpty())
        {
            for(Client client : clients)
            {
                ClientWithTotalSales clientWithTotalSales = new ClientWithTotalSales();
                clientWithTotalSales.setClient(client);

                query = em.createNativeQuery("Select max(m.sale_date) from client c right join Master_Invoice m on c.client_id = m.client_id where c.client_id = ?1")
                        .setParameter(1, client.getClientId());
                Timestamp lastBoughtDate = (Timestamp) query.getSingleResult();
                clientWithTotalSales.setLastBoughtDate(lastBoughtDate);

                query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from Invoice_Detail i "
                        + "right join Master_Invoice m on i.invoice_Id = m.invoice_Id right join Client c on m.client_id = c.client_id where c.client_id = ?1 group by c.client_id")
                        .setParameter(1, client.getClientId());
                BigDecimal totalSalesForClient = (BigDecimal) query.getSingleResult();
                clientWithTotalSales.setTotalSales(totalSalesForClient);
                
                clientsWithTotalSales.add(clientWithTotalSales);
            }
        }
        return clientsWithTotalSales;
    }
    
}
