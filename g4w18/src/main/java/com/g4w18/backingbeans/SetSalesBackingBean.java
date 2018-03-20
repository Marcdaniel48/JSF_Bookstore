package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * This class is responsible for backing a list of books on sale coming from the
 * database.
 *
 * @author Sebastian Ramirez
 */
@Named
@ViewScoped
public class SetSalesBackingBean implements Serializable {

    @Inject
    private CustomBookController customBookController;

    private List<Book> booksOnSale;
    private List<Book> booksNotOnSale;
    private List<Book> allBooks;
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method for booksOnSale If booksOnSale is null, it will retrieve a
     * list of book objects which are on sale.
     *
     * @return List of books on sale.
     */
    public List<Book> getBooksOnSale() {
        if (booksOnSale == null) {
            booksOnSale = customBookController.findBooksOnSale();
        }
        return booksOnSale;
    }

    /**
     * Getter method for booksNotOnSale If booksNotOnSale is null, it will
     * retrieve a list of book objects which are on sale.
     *
     * @return List of books on sale.
     */
    public List<Book> getBooksNotOnSale() {
        if (booksNotOnSale == null) {
            booksNotOnSale = customBookController.findBooksNotSale();
        }
        return booksNotOnSale;
    }

    /**
     * Getter method for booksNotOnSale If booksNotOnSale is null, it will
     * retrieve a list of book objects which are on sale.
     *
     * @return List of books on sale.
     */
    public List<Book> getAllBooks() {
        if (allBooks == null) {
            allBooks = customBookController.findBookEntities();
            log.log(Level.INFO, "allBooks size: {0}", allBooks.size());
        }
        return allBooks;
    }

    public String onRowEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        Book editedBook = (Book) event.getObject();
        customBookController.edit(editedBook);
        return null;
    }

}
