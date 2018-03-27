package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 * Handle manager ability to add, edit and remove books. 
 * 
 * @author Salman Haidar
 */
@Named("managerBookHandling")
@RequestScoped
public class ManagerBookBackingBean implements Serializable {
    
    
    @Inject
    private CustomBookController bookJpaController;
    private Logger logger = Logger.getLogger(SearchBackingBean.class.getName());
    //Manager's search term
    private String searchBook;
    
    private String message=null;
    
    private Book book;
    
    private List<Book> books;
    
    private boolean render=true;
    private boolean renderEdit=false;
    private List<String> formats;
    
    private UploadedFile uploadedImage;
    
    //Genredown search options
    private static Map<String,Object> genreOptions;
    static{
        genreOptions = new LinkedHashMap<String,Object>();
        
        genreOptions.put("Fantasy","Fantasy");
        genreOptions.put("Mystery","Mystery");
        genreOptions.put("Science Fiction","Science Fiction");
        genreOptions.put("Romance","Romance");
        genreOptions.put("Biographies","Biographies");
    }
    
    @PostConstruct
    public void init()
    {
        formats = new ArrayList<String>();
        
        formats.add("PDF");
        formats.add("MOBI");
        formats.add("EPUB");
    }
    
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
    public String editBook(Book bookForm) throws Exception {
        logger.log(Level.INFO, "WHATS INSIDE OF BOOK: " + bookForm.getTitle());
        logger.log(Level.INFO, "WHATS INSIDE OF BOOK: " + bookForm.getDescription());
        bookJpaController.edit(bookForm);
        message = "The book was updated.";
        return "null";
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
     * Get all books from database.
     * 
     * @return List of books found
     */
    public List<Book> getBooks()
    {
        if(books == null){
            books = bookJpaController.findBookEntities();
        }
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
    
    public Map<String,Object> getGenreOptionValue() {
	
        return genreOptions;
    }
    /**
     * Add book cover to resources image folder.
     * @param event Image to be added.
     * @throws IOException 
     */
    public void fileUploadHandler(FileUploadEvent event) throws IOException
    {
        logger.log(Level.INFO, "INSIDE OF FILE UPLOAD HANDLER");
        
        uploadedImage = event.getFile();
        Path folder = Paths.get("/resources/images");
        String filename = book.getIsbnNumber();
        String extension = FilenameUtils.getExtension(uploadedImage.getFileName());
        logger.log(Level.INFO, "EXTENSION SHOW PLS " + extension + "   -----FOLDER PATHG::::" + folder.toString());
        Path file = Files.createTempFile(folder, filename +"hi.", extension);
        
        //File imageFile = new File("/resources/images/"+book.getIsbnNumber());
        
        
        
        //logger.log(Level.INFO, "PATH FILEEEEEEEEEEEEE" + file.toString());
        
        try(InputStream input = uploadedImage.getInputstream())
        {
            Files.copy(input, file);
        }
    }
    
    /**
     * Get edited content from table and save it in the database.
     * @param event
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void onBookEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        logger.log(Level.INFO, "onRowEdit Called");
        FacesMessage msg = new FacesMessage("Book edited!", ((Book) event.getObject()).getIsbnNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        Book updatedBook = (Book)(event.getObject());
        bookJpaController.edit(updatedBook);
        
        
    }
    
    /**
     * Cancel the editing of row.
     * @param event 
     */
    public void onBookEditCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Book edit cancelled!", ((Book) event.getObject()).getIsbnNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<String> getFormats()
    {
        return formats;
    }
}
