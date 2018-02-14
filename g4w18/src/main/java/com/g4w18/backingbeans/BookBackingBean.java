package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is a backing bean. It is used when a page or form must interact with
 * beans that are not managed such as entity beans. In this example the entity
 * bean for Inventory will be manually loaded with data for the example page.
 *
 * @author Ken
 */
@Named
@RequestScoped
public class BookBackingBean implements Serializable {

    @Inject
    private BookJpaController bookJpaController;

    private Book book;
    private List<Book> books;
    private List<Book> recommendedBooks;
    private Logger log = Logger.getLogger(BookBackingBean.class.getName());

    /**
     * Client created if it does not exist.
     *
     * @return
     */
    public Book getBook() {
        if (book == null) {
            Map<String, String> params
                    = getFacesContext().getExternalContext().getRequestParameterMap();
            int id = Integer.parseInt(params.get("id"));
            book = bookJpaController.findBook(id);
        }
        return book;
    }

    public List<Book> getBooks() {
        if (books == null) {
            books = bookJpaController.findBookEntities();
        }
        return books;
    }

    public List<Book> getRecommendedBooks() {
        if (recommendedBooks == null) {
            List<Book> booksByGenre = bookJpaController.findBooksByGenre(book.getGenre());
            booksByGenre.remove(book);
            Collections.shuffle(booksByGenre);
            recommendedBooks = booksByGenre.subList(0, 6);
        }
        return recommendedBooks;
    }

    public int getRating() {
        int averageRating = 0;
        Collection<Review> reviews = book.getReviewCollection();
        int size = reviews.size();
        log.log(Level.INFO, "Reviews size: {0}", reviews.size());
        if (size > 0) {
            for (Review r : reviews) {
                averageRating = averageRating + r.getRating();
            }
        }
        log.log(Level.INFO, "Rating: {0}", averageRating);
        return averageRating/size;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
