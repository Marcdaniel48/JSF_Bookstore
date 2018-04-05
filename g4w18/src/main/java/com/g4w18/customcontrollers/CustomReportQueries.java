package com.g4w18.customcontrollers;

import com.g4w18.custombeans.TopClientsResultBean;
import com.g4w18.custombeans.TopSellersResultBean;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Salman Haidar
 */
public class CustomReportQueries {
    
    @PersistenceContext
    private EntityManager entityManager; 
    
    private Logger logger = Logger.getLogger(CustomReportQueries.class.getName());
    
    /**
     * Get the top sellers between two dates, must not include books that weren't sold.
     * @param begin
     * @param end
     * @return 
     */
    public List<TopSellersResultBean> getTopSellersBetween2Dates(Timestamp begin, Timestamp end)
    {      
        Collection<Object[]> topSellers = entityManager.createNativeQuery("SELECT b.TITLE, b.ISBN_NUMBER, sum(i.BOOK_PRICE) AS TOTAL_SALES FROM book b "
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
     */
    public List<TopClientsResultBean> getTopClientsBetween2Dates(Timestamp begin, Timestamp end)
    {      
        Collection<Object[]> topSellers = entityManager.createNativeQuery("SELECT c.USERNAME, sum(m.GROSS_VALUE) AS TOTAL_SALES FROM client c "
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
    
//    public List<TopClientsResultBean> getZeroSalesBetween2Dates(Timestamp begin, Timestamp end)
//    {     
////        Collection<Object[]> topSellers = entityManager.createNativeQuery("SELECT c.USERNAME, sum(m.GROSS_VALUE) AS TOTAL_SALES FROM client c "
////                + "INNER JOIN master_invoice m on c.CLIENT_ID = m.CLIENT_ID "
////                + "WHERE m.SALE_DATE BETWEEN ?1 AND ?2 GROUP BY c.USERNAME ORDER BY TOTAL_SALES desc")
//        
//        //select title,isbn_number from book where book_id NOT IN (select book.book_id from book left join invoice_detail on book.book_id = invoice_detail.book_id left join master_invoice on invoice_detail.invoice_id = master_invoice.invoice_id WHERE SALE_DATE BETWEEN "2018-02-01" AND "2018-04-19" );
//        Collection<Object[]> zeroSellers = entityManager.createNativeQuery("SELECT b.TITLE,b.ISBN_NUMBER FROM book b "
//                + "WHERE b.book_id NOT IN (select b.book_id from book b left join invoice_detail i on b.book_id = i.book_id "
//                + "left join master_invoice m on m.invoice_id = i.invoice_id WHERE m.SALE_DATE BETWEEN ?1 AND ?2");
//        
//        Iterator<Object[]> iterator = zeroSellers.iterator();
//        while(iterator.hasNext()){
//            Object[] topClient = iterator.next();
//            containerTopClient = new TopClientsResultBean((String)topClient[0],(BigDecimal)topClient[1]);
//            logger.log(Level.INFO, "CLIENT USERNAME OF BOOK: "+ topClient[0]);
//            logger.log(Level.INFO, "TOTAL GROSS SALES: "+ topClient[1]);
//            selling.add(containerTopClient);
//        }
//        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES: " + begin.toString() + "00000  " + end.toString());
//        
//        logger.log(Level.INFO, "INSIDE OF TOP SELLERS BETWEEN 2 DATES GET RESULT LIST SIZE: " + selling.size());
//       
//        return selling;
//        }
    
}
