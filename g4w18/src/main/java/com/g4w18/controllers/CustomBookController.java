/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Banner;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 1430047
 */
public class CustomBookController implements Serializable
{
    @Inject
    private BookJpaController bookController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(Book book) throws RollbackFailureException, Exception
    {
        bookController.edit(book);
    }
    
    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception
    {
        bookController.edit(book);
    }
    
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception
    {
        bookController.destroy(id);
    }
    
    public List<Book> findBookEntities()
    {
       return bookController.findBookEntities();
    }

    public List<Book> findBookEntities(int maxResults, int firstResult)
    {
        return bookController.findBookEntities(maxResults, firstResult);
    }

    public Book findBook(Integer id)
    {
        return bookController.findBook(id);
    }

    public int getBookCount()
    {
        return bookController.getBookCount();
    }

    /**
     * Get books from the database with the title provided
     *
     * @param title name of the book that is being searched
     * @return List of books found with the title
     */
    public List<Book> findBooksByTitleSpecific(String title)
    {
        return bookController.findBooksByTitleSpecific(title);
    }

    /**
     * Get books from the database with the title provided(doesn't need to match whole)
     *
     * @param title name of the book that is being searched
     * @return List of books found with the title
     */
    public List<Book> findBooksByTitle(String title)
    {
        return bookController.findBooksByTitle(title);
    }

    /**
     * Get books from the database with the isbn provided(must match whole) 
     *
     * @param isbn number of the book the user is searching for
     * @return Book found with the isbn
     */
    public List<Book> findBookByAuthor(String author)
    {
        return bookController.findBookByAuthor(author);
    }

    /**
     * Get books from the database with the isbn provided(must match whole)
     *
     * @param isbn number of the book the user is searching for
     * @return Book found with the isbn
     */
    public List<Book> findBookByIsbn(String isbn)
    {
        return bookController.findBookByIsbn(isbn);
    }

    /**
     * Get books from the database with the author provided.
     *
     * @param publisher of the book we are finding
     * @return books found with the associated publisher
     */
    public List<Book> findBooksByPublisher(String publisher)
    {
        return bookController.findBooksByPublisher(publisher);
    }

    /**
     * Get publishers from the database with the publisher provided(doesn't need to match whole)
     *
     * @param title publisher that is being searched
     * @return List of books found with the publisher
     */
    public List<Book> findDistinctPublisher(String publisher)
    {
        return bookController.findDistinctPublisher(publisher);
    }

    public List<Book> findBooksByGenre(String genre)
    {
        return bookController.findBooksByGenre(genre);
    }
    
    public List<Book> getBooksOnSale()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        cq.select(book);
        cq.where(cb.gt(book.get("salePrice"), 0));
        
        Query q = em.createQuery(cq);
        q.setMaxResults(10);
       
        List<Book> books = q.getResultList();
        return books;
    }
    
    public List<Book> getMostRecentBooks()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("inventoryDate")));
        
        
        Query q = em.createQuery(cq);
        q.setMaxResults(3);
        
        List<Book> books = q.getResultList();
        return books;
    }
    
    public List<String> getGenres()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.distinct(true);
        cq.select(root.get("genre"));
        
        Query genres = em.createQuery(cq);
        return genres.getResultList();
    }
}
