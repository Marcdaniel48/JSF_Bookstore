package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.component.datatable.DataTable;
import static org.primefaces.component.focus.Focus.PropertyKeys.context;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 * Handle manager ability to add, edit and remove books. 
 * 
 * @author Salman Haidar
 */
@Named("managerBookHandling")
@SessionScoped
public class ManagerBookBackingBean implements Serializable {
    
    
    @Inject
    private CustomBookController bookJpaController;
    
    @Inject
    private CustomAuthorController authorJPAController;
    
    private Logger logger = Logger.getLogger(SearchBackingBean.class.getName());
    //Manager's search term
    private String searchBook;
    
    private String message=null;
    
    
    private Book book;
    private List<String>  authorList;
    
    private List<Author> newBookAuthors;
    
    private List<Book> books;
    
    private String authors;
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
    
    public void clear(){
    book.setIsbnNumber("");
    book.setTitle("");
    book.setGenre("");
    book.setListPrice(BigDecimal.ZERO);
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
        List<Book> bookResult = bookJpaController.findBookByIsbnSpecific(book.getIsbnNumber());
        logger.log(Level.INFO,"BOOK ISBN THAT WILL BE ADDED TO DB "+ book.getIsbnNumber());
        logger.log(Level.INFO, "RESULTS OF BOOKS FOUND WITH THEW ISBN"+ bookResult.size());
        if(bookResult.size() == 0)
        {
            
             
            List<Author> existingBookAuthors = prepareAuthors();
            List<Author> exist = checkIfAuthorExists(existingBookAuthors);
            book.setAuthorList(exist);
            
            
            
            message="The book was created!";
            bookJpaController.create(book);
            createAuthors(newBookAuthors,book);
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            addMessage("managerCreateBook");
            clear();
            return null;
            
        }
        else
        {
            logger.log(Level.INFO, "INSIDE OF BOOK CREATE IF 0 NOT CREATE BOOK----");
            message="Book already exists!";
            addMessage("managerIsbnExist");
            return "managerBookHandler.xhtml";
        }
         
    }
    
    /**
     * Create new authors and assign the new book to them.
     * @param newAuthors
     * @param book
     * @throws Exception 
     */
    public void createAuthors(List<Author> newAuthors,Book book) throws Exception
    {
        int size = newAuthors.size();
        List<Book> bookForAuthors = new ArrayList();
        
        bookForAuthors.add(book);
        
        for(int i = 0 ; i < size; i++)
        {
            newAuthors.get(i).setBookList(bookForAuthors);
            authorJPAController.create(newAuthors.get(i));  
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
    
    /**
     * Check that the authors provided follow the proper format and are validly written.
     * @param fc
     * @param uic
     * @param value 
     */
    public void validateAuthors(FacesContext fc, UIComponent uic, Object value)
    {
        this.authors = (String)value;
        authorList = Arrays.asList(authors.split(","));
        
        int size = authorList.size();
        
        logger.log(Level.INFO, "SIZE OF AUTHOR LIST=== " + size);
        
        if(size%2!=0)
        {
            String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidBookAuthorFormat");
            throw new ValidatorException(new FacesMessage(validationMessage));
        }
        
        for(int i = 0;i<size;i++)
        {
            if(authorList.get(i) == "" || !authorList.get(i).matches("^[a-zA-Z,.' ]*$"))
            {
                String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidBookAuthorFormatOfNames");
                throw new ValidatorException(new FacesMessage(validationMessage));
            }
        }
    }
    
    /**
     * Create a list of authors and check if they exist already or not.
     */
    public List<Author> prepareAuthors()
    {
        
        List<Author> authorObjects = new ArrayList();
        int size = this.authorList.size();
        Author author = null;
        
        
        for(int j = 0; j<authorList.size();j++)
        {
           logger.log(Level.INFO, "CHECK WHATS INSIDE OF AUTHORS LISDT PLS: " + authorList.get(j));
        }
        
        for(int i = 0 ;i<size;i++)
        {
            logger.log(Level.INFO, "INDEX OF FOR " + i);
            if(i%2==0)
            {
                author = new Author();
                author.setFirstName(authorList.get(i));
                logger.log(Level.INFO, "IN FIRST NAME ADD   " +authorList.get(i));
            }
            else
            {
                author.setLastName(authorList.get(i));
                authorObjects.add(author);
                logger.log(Level.INFO, "IN LAST NAME ADD     "+authorList.get(i));
            }
            
        }
        return authorObjects;
    }
    
    /**
     * Check if the supplied author exists already in the database.
     * @param author 
     */
    public List<Author> checkIfAuthorExists(List<Author> authorsToCheck)
    {
        List<Author> existence = new ArrayList();
        List<Author> check = new ArrayList();
        newBookAuthors = new ArrayList();
        for(int j = 0; j<authorsToCheck.size();j++)
        {
           logger.log(Level.INFO, "CHECK WHATS INSIDE OF AUTHORS TOCHECK: " + authorsToCheck.get(j).getFirstName() + " " + authorsToCheck.get(j).getLastName());
        }
        
        int size = authorsToCheck.size();
        logger.log(Level.INFO, "CHECK SIZE OF AUTHORS TO CHECK : " + size);
        for(int i = 0; i<size;i++)
        {
           logger.log(Level.INFO, "CHECK EXISITING AUTHORS ADD : " + authorsToCheck.get(i).getFirstName());
           check  = authorJPAController.findAuthor(authorsToCheck.get(i).getFirstName()+ " " + authorsToCheck.get(i).getLastName());
           
           if(check.size()==1)
           {
               logger.log(Level.INFO, "CHECK WHATS INSIDE OF CHECCKKKK : " + check.get(0).getFirstName()+ check.get(0).getLastName());
               existence.add(check.get(0));    
           }
           else
           {
               newBookAuthors.add(authorsToCheck.get(i));
               for(int y = 0; y<newBookAuthors.size();y++)
               {
                    logger.log(Level.INFO, "CHECK WHATS INSIDE OF NEW AUTHORS PLS: " + newBookAuthors.get(y).getFirstName() + " " + newBookAuthors.get(y).getLastName());
               }
           }
        }
        
        for(int k = 0; k<existence.size();k++)
        {
           logger.log(Level.INFO, "CHECK WHATS INSIDE OF EXISTENCE: " + existence.get(k).getFirstName() + " " + existence.get(k).getLastName() + " " + existence.get(k).getAuthorId());
        }
        
        return existence;
    }
    
    /**
     * The addMessage method simplifies and reduces redundancy of code when
     * displaying a message is necessary.
     *
     * @param key The string key in the messages bundle.
     */
    private void addMessage(String key) {
        
        FacesContext context = FacesContext.getCurrentInstance();

        String message = context.getApplication().getResourceBundle(context, "msgs").getString(key);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        context.addMessage(null, msg);
    }

    
    //Getters and setters to get information from user
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
    
    public Map<String,Object> getGenreOptionValue() {
	
        return genreOptions;
    }
    
    public List<String> getFormats()
    {
        return formats;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
    
    
    
    
}
