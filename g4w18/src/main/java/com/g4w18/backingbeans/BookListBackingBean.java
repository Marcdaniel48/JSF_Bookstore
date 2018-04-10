package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing in charge of functionality for the bookList.xhtml page
 *
 * @author Sebastian, Jephthia
 */
@Named
@RequestScoped
public class BookListBackingBean implements Serializable {

    @Inject
    private CustomAuthorController authorController;
    @Inject
    private CustomBookController bookController;

    private Author author;
    //Temporary, only used to display the list of all books in bookList
    private List<Book> allBooks;
    private List<Book> booksToShow;
    private List<Book> authorBooks;
    private Logger log = Logger.getLogger(getClass().getName());
    private String genre;

    public List<Book> getBooksToShow() {
        return booksToShow;
    }

    /**
     * Client created if it does not exist.
     *
     * @return
     */
    public Author getAuthor() {
        if (author == null) {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int id = Integer.parseInt(params.get("authorId"));
            author = authorController.findAuthor(id);
        }
        return author;
    }

    /**
     * Getter method for the authorBooks class variable. If the variable is
     * null, it will assign the author's books.
     *
     * @author Sebastian Ramirez
     * @return
     */
    public Collection<Book> getAuthorBooks() {
        if (authorBooks == null) {
            authorBooks = author.getBookList();
        }
        return authorBooks;
    }

    /**
     * @author Jephthia
     * @return true if this page is supposed to display the books in the
     * specified genre, false otherwise.
     */
    public boolean byGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        genre = params.get("genre");

        return genre != null && !"".equals(genre);
    }

    /**
     * @author Jephthia
     * @return Get the top 5 selling books
     */
    public List<Book> getTopSellers() {
        List<Book> books = bookController.findBooksByGenre(genre);

        books.sort((a, b) -> Integer.compare(b.getInvoiceDetailList().size(), a.getInvoiceDetailList().size()));

        if (books.size() < 5) {
            return books;
        }

        return books.subList(0, 5);
    }

    /**
     * @author Jephthia
     * @return The books in that genre excluding the top 5 selling books
     */
    private List<Book> getRemainingBooks() {
        List<Book> books = bookController.findBooksByGenre(genre);

        books.sort((a, b) -> Integer.compare(b.getInvoiceDetailList().size(), a.getInvoiceDetailList().size()));

        if (books.size() <= 5) {
            return new ArrayList<Book>();
        }

        return books.subList(5, books.size());
    }

    /**
     * Getter method for allBooks variable. It will call getRemainingBooks if
     * byGenre returns true. If allBooks is null it will retrieve all the book
     * entities from the DB.
     *
     * @author Sebastian Ramirez
     * @return
     */
    public List<Book> getAllBooks() {
        if (byGenre()) {
            return getRemainingBooks();
        }

        if (allBooks == null) {
            allBooks = bookController.findBookEntities();
            log.log(Level.INFO, "allBooks size: {0}", allBooks.size());
        }

        return allBooks;
    }
}
