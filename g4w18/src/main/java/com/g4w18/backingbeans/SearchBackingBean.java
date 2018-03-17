package com.g4w18.backingbeans;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.controllers.BookJpaController;
import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Author;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.entities.Book;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
    //Display in the next page how many results were found
    private String message="";
    @Inject
    private CustomBookController bookJpaController;
    
    @Inject
    private CustomAuthorController authorJpaController;
    
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
       {
           message ="We have found " + result + " books for you! You searched for: " + searchTerm;
           return "resultPlus";
       }
       else if(result == 1)
           return "resultOne";
       else
       {    
           message="We have found nothing for you ! You searched for: " + searchTerm;
           return "resultPlus";
       }
    }
    
    /**
     * For the search page, this method returns the appropriate list to display
     * books. It depends on the option chosen by the user.
     * @return Book for the specific search option. 
     */
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
     * Get books with the title provided by the user.
     * 
     * @return List of books found
     */
    public List<Book> getBooksByTitle()
    {
        List<Book> books = bookJpaController.findBooksByTitle(searchTerm);
        
        return books;
    }
    
    public List<Book> getBooksByAuthor()
    {
        List<Author> authors = authorJpaController.findAuthor(searchTerm);
        
        List<Book> books = authors.get(0).getBookList();
       
        return books;
    }
    
    /**
     * Get list of books with the specific isbn provided by the user.
     * 
     * @return List of books found with the isbn
     */
    public List<Book> getBookByIsbn()
    {
        List<Book> book = bookJpaController.findBookByIsbn(searchTerm);
        
        return book;
    }
    
    /**
     * Get list of books with the publisher provided by the user.
     * @return List of books found with the publisher
     */
    public List<Book> getBooksByPublisher()
    {
        List<Book> books = bookJpaController.findBooksByPublisher(searchTerm);
        
        return books;
    }
    
    /**
     * Get list of publisher with the specific term provided by the user.
     * @return List of publishers found
     */
    public List<Book> getPublishers(String searchTxt)
    {
        
        List<Book> publishers = bookJpaController.findDistinctPublisher(searchTerm);
        
        return  publishers;
    }
    
    /**
     * Get the list of books for all the publishers in the list.
     * @param publishers The publishers we want books from
     * @return List of books from publishers
     */
    public List<Book> getBooksForPublishers(List<Book> publishers)
    {
        int publisherCount = publishers.size();
        List<Book> allBooksFromPublishers = null;
        
        for(int i=0;i<publisherCount;i++)
        {
            allBooksFromPublishers.addAll(bookJpaController.findBooksByPublisher(publishers.get(i).getPublisher()));
        }
        return allBooksFromPublishers;
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
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}