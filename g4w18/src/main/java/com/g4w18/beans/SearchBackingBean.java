package com.g4w18.beans;

import com.g4w18.controllers.BookJpaController;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.entities.Book;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.Size;


/**
 *
 * @author Salman Haidar
 */
@Named("searchBackingBean")
@RequestScoped
public class SearchBackingBean implements Serializable {
    
    private Logger logger = Logger.getLogger(SearchBackingBean.class.getName());
    //User's search term
    private String searchTerm;
    //Dropdown search option that is chosen
    private String searchOption;
    
    @Inject
    private BookJpaController bookJpaController;
    
    //Drop down search options
    private static Map<String,Object> searchOptions;
    static{
        searchOptions = new LinkedHashMap<String,Object>();
        
        searchOptions.put("Title","Title");
        searchOptions.put("Author","Author");
        searchOptions.put("ISBN","ISBN");
        searchOptions.put("Publisher","Publisher");
    }
    
    
    /**
     * Check which page we should go to with the count returned from the 
     * appropriate dropdown method chosen.
     * 
     * @return Which page to go to 
     */
    public String search(){
       
        int result = 0;
        
        switch(searchOption)
        {
            case "Title":
               result = getBooksByTitle().size();
                break;
                
            case "Author":
                //author method goes here
                break;
                
            case "ISBN":
               result = getBookByIsbn().size();
                break;
                
            case "Publisher":
                result = getBooksByPublisher().size();
                break;
            default:
                //nothing to display so stay on same page
                result = 0;
                break;
        }
        
       logger.log(Level.INFO, "SEARCH TERM USED: "+ searchTerm + "DROPDOWN OPTION CHOICE: "+ searchOption );
       logger.log(Level.INFO, "NUMBER OF RESULTS RETURNED: "+ result );
      
       if(result > 1)
           return "resultPlus";
       else if(result == 1)
           return "resultOne";
       else
           return null;
    }
    
    public List<Book> getBooks()
    {
        List<Book> userBooks = null;
        
        switch(searchOption)
        {
            case "Title":
               userBooks = getBooksByTitle();
                break;
                
            case "Author":
                //author method goes here
                break;
                
            case "ISBN":
               userBooks = getBookByIsbn();
                break;
                
            case "Publisher":
                userBooks = getBooksByPublisher();
                break;
        }
        return userBooks;
    }
    
    /**
     * 
     * @return 
     */
    public List<Book> getBooksByTitle()
    {
        List<Book> books = bookJpaController.findBooksByTitle(searchTerm);
        
        return books;
    }
    
    /**
     * 
     * @return 
     */
    public List<Book> getBookByIsbn()
    {
        List<Book> book = bookJpaController.findBookByIsbn(searchTerm);
        
        return book;
    }
    
    /**
     * 
     * @return 
     */
    public List<Book> getBooksByPublisher()
    {
        List<Book> books = bookJpaController.findBooksByPublisher(searchTerm);
        
        return books;
    }
    
    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm(){
        return searchTerm;
    }
    
    public Map<String,Object> getSearchOptionValue() {
		return searchOptions;
    }
    
    public String getSearchOption()
    {
        return searchOption;
    }
    
    public void setSearchOption(String searchOption)
    {
        this.searchOption = searchOption;
    }
    
}
