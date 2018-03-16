package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Handle manager ability to add, edit and remove books. 
 * 
 * @author Salman Haidar
 */
@Named("managerBookHandling")
@SessionScoped
public class ManagerBookBackingBean implements Serializable {
    
    private Logger logger = Logger.getLogger(SearchBackingBean.class.getName());
    //Manager's search term
    private String searchBook;
    
    private String message=null;
    
    private Book book;
    
    private boolean render=true;
    private boolean renderEdit=false;
    
    @Inject
    private CustomBookController bookJpaController;
    
    public Book getBook()
    {
        if(book == null)
        {
            book = new Book();
        }
        return book;
    }
    
    /**
     * Edit book.
     * @return Return to the same page.
     * @throws Exception 
     */
    public String editBook() throws Exception {
        bookJpaController.edit(book);
        message = "The book was updated.";
        return "null";
    }
    
    /**
     * Search for the book the manager wants. It nothing found, stay in the same page
     * and display the message.
     * @return Page to go to
     */
    public String search()
    {
        int result = 0;
        logger.log(Level.INFO, "inside of SEARCH HEREHERE: "+ result );
        result = getBooksByTitle().size();
        
        if(result == 0)
        {
            logger.log(Level.INFO, "inside of SEARCH of 0: "+ result );
            message = "fail nothing was returned";
            return null;
        }
        else
        {
            logger.log(Level.INFO, "INSIDE of SEARCH of more than 0: "+ result );
            return "resultEdit";
        }
    }
    
    /**
     * Add book to database
     * @return Website to move to 
     * @throws Exception 
     */
    public String createBook() throws Exception
    {
        logger.log(Level.INFO, "INSIDE OF CREATE BOOK");
        List<Book> bookResult = bookJpaController.findBookByIsbn(book.getIsbnNumber());
        logger.log(Level.INFO, book.getIsbnNumber());
        logger.log(Level.INFO, "RESULTS OF BOOKS FOUND WITH THEW ISBN"+ bookResult.size());
        if(bookResult.size() == 0)
        {
            logger.log(Level.INFO, "INSIDE OF CREATE BOOK 1 IF +++++");
            message="The book was created!";
            bookJpaController.create(book);
            return "managerBookHandler.xhtml";
        }
        else
        {
            logger.log(Level.INFO, "INSIDE OF BOOK CREATE IF 0----");
            message="Book already exists!";
            return "managerBookHandler.xhtml";
        }
        
        
    }
    
    /**
     * Get books with the title provided by the user.
     * 
     * @return List of books found
     */
    public List<Book> getBooksByTitle()
    {
        List<Book> books = bookJpaController.findBooksByTitle(searchBook);
        
        return books;
    }
    
    public void setSearchBook(String searchBook)
    {
        this.searchBook = searchBook;
    }
    
    public String getSearchBook()
    {
        return searchBook;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void showEditForm()
    {
        renderEdit = true;
        render = false;
    }
    
    public void hideEditForm()
    {
        renderEdit = false;
        render = true;
    }
    
    public boolean getRender()
    {
        return render;
    }
    
    public boolean getRenderForm()
    {
        return renderEdit;
    }
    
    public void setRender(boolean render)
    {
        logger.log(Level.INFO, "setRender: "+ render );
        this.render = render;
    }
    
    public void setRenderForm(boolean renderEdit)
    {
        logger.log(Level.INFO, "setRenderForm: "+ renderEdit );
        this.renderEdit = renderEdit;
    }
    
}
