package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Author;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.entities.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


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
        
        FacesContext contextBundle = FacesContext.getCurrentInstance();
        ResourceBundle bundle = contextBundle.getApplication().getResourceBundle(contextBundle, "msgs");
        
        switch(searchOption)
        {
            case "Title":
               result = getBooksByTitle().size();
                break;
                
            case "Author":
                result = getBooksByAuthor().size();
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
           String found = bundle.getString("searchMessageFound");
           String foundDetail = bundle.getString("searchMessageDetail");
           message =found +" "+ result +" " +foundDetail + searchTerm;
           return "resultPlus";
       }
       else if(result == 1)
       {
            try {
                
                int bookId = getSingleBookId();
                
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/bookDetail.xhtml?id="+bookId);
                
                
            } catch (IOException ex) {
                Logger.getLogger(SearchBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
       }
       else
       {    
            String messageFail = bundle.getString("searchMessageFail");
           message=messageFail +" " + searchTerm;
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
                userBooks = getBooksByAuthor();
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
     * Get id of a single book so you can navigate to that page right away.
     * @return BookId
     */
    public int getSingleBookId()
    {
        int bookId;
        switch(searchOption)
        {
            case "Title":
               bookId = getBooksByTitle().get(0).getBookId();
                break;
                
            case "Author":
                bookId = getBooksByAuthor().get(0).getBookId();
                break;
                
            case "ISBN":
               bookId = getBookByIsbn().get(0).getBookId();
                break;
                
            case "Publisher":
                bookId = getBooksByPublisher().get(0).getBookId();
                break;
            default:
                bookId = 0;
                break;
        }
        return bookId;
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
    
    /**
     * Get books for authors found.
     * @return 
     */
    public List<Book> getBooksByAuthor()
    {
        List<Author> authors = authorJpaController.findAuthor3Options(searchTerm);
        
        List<Book> allBooks = new ArrayList<Book>();
        
        int authorCount = authors.size();
        
        logger.log(Level.INFO,"---------==HOW MANY AUTHORS WITH ======-----"+  authorCount);
        
        for(int k=0;k<authorCount;k++)
        {
            List<Book> checkBooks = authors.get(k).getBookList();
            for(int l=0;l<checkBooks.size();l++)
            {
                if(checkBooks.get(l).getRemovalStatus()==false)
                {
                    allBooks.add(checkBooks.get(l));
                }
            }
        }
        
//        for(int i=0;i< authorCount;i++)
//        {
//            allBooks.addAll(authors.get(i).getBookList());
//        }
        for(int j = 0;j<allBooks.size();j++)
        {
            logger.log(Level.INFO,"BOOKS INSIDE OF AUTHOR LIST: "+ allBooks.get(j).getTitle());
        }
        
        return allBooks;
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
        List<Book> books = bookJpaController.findLikePublisher(searchTerm);
        
        return books;
    }
    
    //All methods for getting and setting information in JSF page
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