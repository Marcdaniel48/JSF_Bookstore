package com.g4w18.backingbeans;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
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
public class AuthorBackingBean implements Serializable {

    @Inject
    private AuthorJpaController authorJpaController;

    private Author author;
    private Collection<Book> authorBooks;
    private Logger log = Logger.getLogger(BookBackingBean.class.getName());

    /**
     * Client created if it does not exist.
     *
     * @return
     */
    public Author getAuthor() {
        if (author == null) {
            Map<String, String> params
                    = getFacesContext().getExternalContext().getRequestParameterMap();
            int id = Integer.parseInt(params.get("authorId"));
            author = authorJpaController.findAuthor(id);
        }
        return author;
    }

    public Collection<Book> getAuthorBooks() {
        if (authorBooks == null) {
            authorBooks = author.getBookCollection();
        }
        return authorBooks;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
