/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.custombeans.AuthorWithTotalSales;
import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ClientWithTotalSales;
import com.g4w18.custombeans.PublisherWithTotalSales;
import com.g4w18.entities.Author;
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
        
        Query query = em.createNativeQuery("Select distinct b.* from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where m.sale_date between ?1 and ?2", Book.class)
                .setParameter(1, date1).setParameter(2, date2);
        
        List<Book> books = query.getResultList();
        
        if (!books.isEmpty())
        {
            for(Book book : books)
            {
                BookWithTotalSales bookWithTotalSales = new BookWithTotalSales();
                bookWithTotalSales.setBook(book);

                query = em.createNativeQuery("Select max(m.sale_date) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.book_id = ?1").setParameter(1, book.getBookId());
                Timestamp lastSoldDate = (Timestamp) query.getSingleResult();
                bookWithTotalSales.setLastRecordedSale(lastSoldDate);

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
        
        Query query = em.createNativeQuery("Select distinct c.* from client c right join Master_Invoice m on c.client_id = m.client_id where m.sale_date between ?1 and ?2", Client.class)
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
                clientWithTotalSales.setLastRecordedSale(lastBoughtDate);

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
    
    public List<AuthorWithTotalSales> findAuthorsWithTotalSalesBetweenDates(Date date1, Date date2)
    {
        List<AuthorWithTotalSales> authorsWithTotalSales = new ArrayList<>();
        
        Query query = em.createNativeQuery("Select distinct a.* from author a right join book_author ba on a.author_id = ba.author_id right join Invoice_Detail i on ba.book_id = i.book_id "
                + "right join Master_Invoice m on i.invoice_id = m.invoice_id where m.sale_date between ?1 and ?2", Author.class)
                .setParameter(1, date1).setParameter(2, date2);
        
        List<Author> authors = query.getResultList();
        
        if (!authors.isEmpty())
        {
            for(Author author : authors)
            {
                AuthorWithTotalSales authorWithTotalSales = new AuthorWithTotalSales();
                authorWithTotalSales.setAuthor(author);

                query = em.createNativeQuery("Select max(m.sale_date) from Master_Invoice m right join Invoice_Detail i on m.invoice_id = i.invoice_id"
                    + " right join book_author ba on i.book_id = ba.book_id where ba.author_id = ?1").setParameter(1, author.getAuthorId());
                Timestamp lastSoldDate = (Timestamp) query.getSingleResult();
                authorWithTotalSales.setLastRecordedSale(lastSoldDate);

                query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from Master_Invoice m "
                        + "right join Invoice_Detail i on m.invoice_Id = i.invoice_Id right join book_author ba on i.book_id = ba.book_id "
                        + "where ba.author_id = ?1 group by ba.author_id").setParameter(1, author.getAuthorId());
                BigDecimal totalSalesForBook = (BigDecimal) query.getSingleResult();
                authorWithTotalSales.setTotalSales(totalSalesForBook);
                
                authorsWithTotalSales.add(authorWithTotalSales);
            }
        }
        return authorsWithTotalSales;
    }
    
    public List<PublisherWithTotalSales> findPublishersWithTotalSalesBetweenDates(Date date1, Date date2)
    {
        List<PublisherWithTotalSales> publishersWithTotalSales = new ArrayList<>();
        
        Query query = em.createNativeQuery("Select distinct b.publisher from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where m.sale_date between ?1 and ?2")
                .setParameter(1, date1).setParameter(2, date2);
        
        List<String> publishers = query.getResultList();
        
        if (!publishers.isEmpty())
        {
            for(String publisher : publishers)
            {
                PublisherWithTotalSales publisherWithTotalSales = new PublisherWithTotalSales();
                publisherWithTotalSales.setPublisher(publisher);

                query = em.createNativeQuery("Select max(m.sale_date) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.publisher = ?1").setParameter(1, publisher);
                Timestamp lastSoldDate = (Timestamp) query.getSingleResult();
                publisherWithTotalSales.setLastRecordedSale(lastSoldDate);

                query = em.createNativeQuery("Select sum(i.book_price * (1 + gst_rate/100.0 + pst_rate/100.0 + hst_rate/100.0)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.publisher = ?1 group by b.publisher").setParameter(1, publisher);
                BigDecimal totalSalesForPublisher = (BigDecimal) query.getSingleResult();
                publisherWithTotalSales.setTotalSales(totalSalesForPublisher);
                
                publishersWithTotalSales.add(publisherWithTotalSales);
            }
        }
        return publishersWithTotalSales;
    }
    
}
