package com.g4w18.customcontrollers;

import com.g4w18.custombeans.AuthorWithTotalSales;
import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ClientWithTotalSales;
import com.g4w18.custombeans.PublisherWithTotalSales;
import com.g4w18.custombeans.TopClientsResultBean;
import com.g4w18.custombeans.TopSellersResultBean;
import com.g4w18.custombeans.ZeroReportBean;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * JPA controller class responsible for querying and returning manager report information.
 * 
 * @author Marc-Daniel
 */
public class ReportQueries implements Serializable {
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    @Inject
    private CustomBookController bookController;
    
    private Logger logger = Logger.getLogger(ReportQueries.class.getName());
    
    /**
     * Given a range of dates, returns a list of books alongside their total sales, total cost, and last recorded sale dates.
     * 
     * @param date1
     * @param date2
     * @return 
     */
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
                
                query = em.createNativeQuery("Select (b.wholesale_price * count(i.book_id)) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.book_id = ?1 group by b.isbn_number").setParameter(1, book.getBookId());
                BigDecimal totalCostForBook = (BigDecimal) query.getSingleResult();
                bookWithTotalSales.setTotalCost(totalCostForBook);
                
                booksWithTotalSales.add(bookWithTotalSales);
            }
        }
        return booksWithTotalSales;
    }
    
    /**
     * Given a range of dates, returns a list of clients alongside their total sales, total cost, and last recorded sale dates.
     * 
     * @param date1
     * @param date2
     * @return 
     */
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
                
                query = em.createNativeQuery("Select (b.wholesale_price * count(i.book_id)) from Invoice_Detail i "
                        + "right join Master_Invoice m on i.invoice_Id = m.invoice_Id right join Client c on m.client_id = c.client_id inner join "
                        + "book b on b.book_Id = i.book_Id where c.client_id = ?1 group by c.client_id")
                        .setParameter(1, client.getClientId());
                BigDecimal totalCostForClient = (BigDecimal) query.getSingleResult();
                clientWithTotalSales.setTotalCost(totalCostForClient);
                
                clientsWithTotalSales.add(clientWithTotalSales);
            }
        }
        return clientsWithTotalSales;
    }
    
    /**
     * Given a range of dates, returns a list of authors alongside their total sales, total cost, and last recorded sale dates.
     * 
     * @param date1
     * @param date2
     * @return 
     */
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
                BigDecimal totalSalesForAuthor = (BigDecimal) query.getSingleResult();
                authorWithTotalSales.setTotalSales(totalSalesForAuthor);
                
                query = em.createNativeQuery("Select (b.wholesale_price * count(i.book_id)) from Master_Invoice m "
                        + "right join Invoice_Detail i on m.invoice_Id = i.invoice_Id right join book_author ba on i.book_id = ba.book_id "
                        + "inner join book b on ba.book_id = b.book_id "
                        + "where ba.author_id = ?1 group by ba.author_id").setParameter(1, author.getAuthorId());
                BigDecimal totalCostForAuthor = (BigDecimal) query.getSingleResult();
                authorWithTotalSales.setTotalCost(totalCostForAuthor);
                
                authorsWithTotalSales.add(authorWithTotalSales);
            }
        }
        return authorsWithTotalSales;
    }
    
    /**
     * Given a range of dates, returns a list of publishers alongside their total sales, total cost, and last recorded sale dates.
     * 
     * @param date1
     * @param date2
     * @return 
     */
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
                
                query = em.createNativeQuery("Select sum(b.wholesale_price) from book b right join Invoice_Detail i on b.book_Id = i.book_Id"
                    + " right join Master_Invoice m on i.invoice_Id = m.invoice_Id where b.publisher = ?1 group by b.publisher").setParameter(1, publisher);
                BigDecimal totalCostForPublisher = (BigDecimal) query.getSingleResult();
                publisherWithTotalSales.setTotalCost(totalCostForPublisher);
                
                publishersWithTotalSales.add(publisherWithTotalSales);
            }
        }
        return publishersWithTotalSales;
    }
    
    /**
     * Get the top sellers between two dates, must not include books that weren't sold.
     * @param begin
     * @param end
     * @return
     * @author Salman Haidar
     */
    public List<TopSellersResultBean> getTopSellersBetween2Dates(Date begin, Date end)
    {      
        Collection<Object[]> topSellers = em.createNativeQuery("SELECT b.TITLE, b.ISBN_NUMBER, sum(i.BOOK_PRICE) AS TOTAL_SALES FROM book b "
                + "INNER JOIN invoice_detail i on b.BOOK_ID = i.BOOK_ID "
                + "INNER JOIN master_invoice m on m.INVOICE_ID = i.INVOICE_ID "
                + "WHERE m.SALE_DATE BETWEEN ?1 AND ?2 GROUP BY b.title ORDER BY TOTAL_SALES desc")
                .setParameter(1, begin)
                .setParameter(2, end)
                .getResultList();
        
        List<TopSellersResultBean> selling = new ArrayList<>(topSellers.size());
        TopSellersResultBean containerTopSeller;
        
        Iterator<Object[]> iterator = topSellers.iterator();
        while(iterator.hasNext()){
            Object[] topSeller = iterator.next();
            containerTopSeller = new TopSellersResultBean((String)topSeller[0],(String)topSeller[1],(BigDecimal)topSeller[2]);
            logger.log(Level.INFO, "TITLE OF BOOK: "+ topSeller[0]);
            logger.log(Level.INFO, "ISBN OF BOOK: "+ topSeller[1]);
            logger.log(Level.INFO, "SALE OF BOOK: "+ topSeller[2]);
            selling.add(containerTopSeller);
        }
        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES: " + begin.toString() + "00000  " + end.toString());
        
        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES GET RESULT LIST SIZE: " + selling.size());
       
        return selling;
    }
    
    /**
     * Get the top clients between two dates, must not include clients that haven't bought anything.
     * @param begin
     * @param end
     * @return 
     * @author Salman Haidar
     */
    public List<TopClientsResultBean> getTopClientsBetween2Dates(Date begin, Date end)
    {      
        Collection<Object[]> topSellers = em.createNativeQuery("SELECT c.USERNAME, sum(m.GROSS_VALUE) AS TOTAL_SALES FROM client c "
                + "INNER JOIN master_invoice m on c.CLIENT_ID = m.CLIENT_ID "
                + "WHERE m.SALE_DATE BETWEEN ?1 AND ?2 GROUP BY c.USERNAME ORDER BY TOTAL_SALES desc")
                .setParameter(1, begin)
                .setParameter(2, end)
                .getResultList();
        
        List<TopClientsResultBean> selling = new ArrayList<>(topSellers.size());
        TopClientsResultBean containerTopClient;
        
        Iterator<Object[]> iterator = topSellers.iterator();
        while(iterator.hasNext()){
            Object[] topClient = iterator.next();
            containerTopClient = new TopClientsResultBean((String)topClient[0],(BigDecimal)topClient[1]);
            logger.log(Level.INFO, "CLIENT USERNAME OF BOOK: "+ topClient[0]);
            logger.log(Level.INFO, "TOTAL GROSS SALES: "+ topClient[1]);
            selling.add(containerTopClient);
        }
        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES: " + begin.toString() + "00000  " + end.toString());
        
        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES GET RESULT LIST SIZE: " + selling.size());
       
        return selling;
    }
    
    /**
     * Get books that haven't sold between two dates.
     * @param begin 
     * @param end
     * @return
     * @author Salman Haidar 
     */
    public List<ZeroReportBean> getZeroSalesBetween2Dates(Date begin, Date end)
    {     
      
        //SELECT TITLE,ISBN_NUMBER FROM book WHERE book.book_id NOT IN (select book.book_id from book  left join invoice_detail  on invoice_detail.book_id = book.book_id left join master_invoice on master_invoice.invoice_id = invoice_detail.invoice_id WHERE master_invoice.SALE_DATE BETWEEN "2018-02-12" AND "2018-02-18");
        Collection<Object[]> zeroSellers = em.createNativeQuery("SELECT b.TITLE,b.ISBN_NUMBER FROM book b "
                + "WHERE b.book_id NOT IN (select bo.book_id from book bo left join invoice_detail i on bo.book_id = i.book_id "
                + "left join master_invoice m on m.invoice_id = i.invoice_id WHERE m.SALE_DATE BETWEEN ?1 AND ?2)")
                .setParameter(1, begin)
                .setParameter(2, end)
                .getResultList();
        
        List<ZeroReportBean> zeroSeller = new ArrayList<>(zeroSellers.size());
        ZeroReportBean containerZeroReport;
        
        Iterator<Object[]> iterator = zeroSellers.iterator();
        while(iterator.hasNext()){
            Object[] zeroBook = iterator.next();
            containerZeroReport = new ZeroReportBean((String)zeroBook[0],(String)zeroBook[1]);
            logger.log(Level.INFO, "CLIENT USERNAME OF BOOK: "+ zeroBook[0]);
            logger.log(Level.INFO, "TOTAL GROSS SALES: "+ zeroBook[1]);
            zeroSeller.add(containerZeroReport);
        }
        logger.log(Level.INFO, "INSIDE OF ZERO REPORTS BETWEEN 2 DATES: " + begin.toString() + "-------  " + end.toString());
        
        logger.log(Level.INFO, "INSIDE OF ZERO REPORTS BETWEEN 2 DATES GET RESULT LIST SIZE: " + zeroSeller.size());
       
        return zeroSeller;
    }
    
    /**
     * Get stock information of the database.
     * @return all books from database
     * @author Salman Haidar
     */
    public List<Book> getStockReport()
    {
        List<Book> stockReport = bookController.findBookEntities();
        
        return stockReport;
    }
    
}
