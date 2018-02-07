package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
//            book = bookJpaController.test(id);
        }
        return book;
    }

    public List<Book> getBooks() {
        if(books == null){
            books = bookJpaController.findBookEntities();
        }
        return books;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
