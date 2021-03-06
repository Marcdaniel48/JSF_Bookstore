package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 *
 * @author Jephthia Louis
 */
@Named("theBooks")
@SessionScoped
public class BookBackingBean implements Serializable
{
    @Inject
    private CustomBookController bookController;
    
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Get the list of books on sale
     *
     * @return
     */
    public List<Book> getBooksOnSale()
    {
        return bookController.getBooksOnSale();
    }

    /**
     * Get 3 most recent books
     *
     * @return
     */
    public List<Book> getMostRecentBooks()
    {
        return bookController.getMostRecentBooks();
    }
    
    /**
     * Get the list of genres
     *
     * @return
     */
    public List<String> getGenres()
    {
        return bookController.getGenres();
    }
    
    /**
     * @return A list of recommended books based on which books the user visited
     */
    public List<Book> getRecommendedBooks()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = context.getExternalContext().getRequestCookieMap();
        
        Cookie genresCookie = (Cookie)cookies.get("VisitedGenres");
        
        String[] genres = genresCookie.getValue().split(",");

        List<Book> books = bookController.getRecommendedBooks(genres);
        
        logger.log(Level.INFO, books.toString());
        
        return books;
    }
    
    /**
     * @return Checks if this user should be shown book recommendations
     */
    public boolean hasRecommendations()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = context.getExternalContext().getRequestCookieMap();
        
        Cookie genresCookie = (Cookie)cookies.get("VisitedGenres");
        
        return genresCookie != null;
    }
}
