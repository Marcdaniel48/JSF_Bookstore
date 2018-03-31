package com.g4w18.customcontrollers;

import com.g4w18.custombeans.TopSellersResultBean;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Salman Haidar
 */
public class CustomReportQueries {
    
    @PersistenceContext
    private EntityManager entityManager; 
    
    public List<TopSellersResultBean> getTopSellersBetween2Dates(Timestamp begin, Timestamp end)
    {
        
//         TypedQuery<CountryRegionLanguageBean> query= 
//                    entityManager.createQuery("SELECT new com.kfwebstandard.jpaqueryworld.entities.CountryRegionLanguageBean(c1.name, c1.region, c2.countrylanguagePK.language) FROM Country c1 INNER JOIN c1.countrylanguageCollection c2 ORDER BY c2.countrylanguagePK.language",CountryRegionLanguageBean.class);
//            Collection<CountryRegionLanguageBean> countries = query.getResultList();
        
//        TypedQuery<TopSellersResultBean> query =
//                entityManager.createQuery("SELECT new com.g4w18.customBeans.TopSellersResultBean(b.title,b.isbn,m.bookPrice) FROM Book b inner join b.invoiceDetailList m ");
        
        List<TopSellersResultBean> topSellers = entityManager.createNativeQuery("SELECT b.TITLE, b.ISBN_NUMBER, sum(i.BOOK_PRICE) AS TOTAL_SALES FROM book b "
                + "INNER JOIN invoice_detail i on b.BOOK_ID = i.BOOK_ID "
                + "INNER JOIN master_invoice m on m.INVOICE_ID = i.INVOICE_ID "
                + "WHERE m.SALE_DATE BETWEEN ?1 AND ?2 GROUP BY b.title ORDER BY TOTAL_SALES asc")
                .setParameter(1, begin)
                .setParameter(2, end)
                .getResultList();
        
        return topSellers;
    }
    
}
