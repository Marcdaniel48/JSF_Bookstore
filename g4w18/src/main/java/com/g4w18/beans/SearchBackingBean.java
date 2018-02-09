package com.g4w18.beans;

import com.g4w18.controllers.BookJpaController;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.beans.DropDownSearchBean;
import com.g4w18.beans.SearchBean;
import com.g4w18.entities.Book;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;


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
        
        searchOptions.put("Book","Book");
        searchOptions.put("Title","Title");
        searchOptions.put("Author","Author");
        searchOptions.put("ISBN","ISBN");
        searchOptions.put("Publisher","Publisher");
    }
    
    //private SearchBean searchBean;   
    //private DropDownSearchBean dropDownSearchBean;
    
    public String searchTitle(){
       
        logger.log(Level.INFO, "HEEEEEEEERE "+ searchTerm + "WOOOO "+ searchOption );
       //List<Book> books = bookJpaController.findBooksByTitle(searchBean.getSearchTerm());
       List<Book> books = bookJpaController.findBooksByTitle(searchTerm);
       logger.log(Level.INFO, "HEEEEEEEERE DATATATA : "+ books.size() );
      
       
       if(books.size() > 1)
           return "resultPlus";
       else
           return "resultOne";
    }
    
    public List<Book> getMultipleBooks()
    {
        List<Book> books = bookJpaController.findBooksByTitle(searchTerm);
        
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
