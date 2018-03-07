/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.controllers.BookJpaController;
import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastien, Jephthia
 */
@Named
@RequestScoped
public class BookListBackingBean  implements Serializable{

    @Inject
    private CustomAuthorController authorController;
    @Inject
    private CustomBookController bookController;

    private Author author;
    //Temporary, only used to display the list of all books in bookList
    private List<Book> allBooks;
    private List<Book> authorBooks;
    private Logger log = Logger.getLogger(getClass().getName());

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

    public Collection<Book> getAuthorBooks() {
        if (authorBooks == null) {
            authorBooks = author.getBookList();
        }
        return authorBooks;
    }

    public List<Book> getAllBooks() {
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String genre = params.get("genre");
        
        if(genre != null && !"".equals(genre))
            return bookController.findBooksByGenre(genre);
        
        log.log(Level.INFO, genre);
        
        if (allBooks == null) {
            allBooks = bookController.findBookEntities();
        }
        
        
        return allBooks;
    }
}
