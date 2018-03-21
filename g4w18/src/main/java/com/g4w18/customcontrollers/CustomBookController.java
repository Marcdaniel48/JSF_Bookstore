/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
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
 * @author Jephtia, Salman, Sebastian
 */
public class CustomBookController implements Serializable {

    @Inject
    private BookJpaController bookController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Book book) throws RollbackFailureException, Exception {
        bookController.edit(book);
    }

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        bookController.edit(book);
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        bookController.destroy(id);
    }

    public List<Book> findBookEntities() {
        return bookController.findBookEntities();
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return bookController.findBookEntities(maxResults, firstResult);
    }

    public Book findBook(Integer id) {
        return bookController.findBook(id);
    }

    public int getBookCount() {
        return bookController.getBookCount();
    }

    public List<Book> getBooksOnSale()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root);
        cq.where(cb.gt(root.get("salePrice"), 0));
        
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

    public List<String> getGenres() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.distinct(true);
        cq.select(root.get("genre"));

        Query genres = em.createQuery(cq);
        return genres.getResultList();
    }

    public List<Book> findBooksByGenre(String genre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Book> book = cq.from(Book.class);
        cq.select(book).where(cb.equal(book.get("genre"), genre));
        TypedQuery<Book> query = em.createQuery(cq);
        List<Book> toReturn = query.getResultList();
        return toReturn;
    }

    /**
     * Get books from the database with the title provided
     *
     * @param title name of the book that is being searched
     * @return List of books found with the title
     */
    public List<Book> findBooksByTitleSpecific(String title) {
        List<Book> findBookByTitle = em.createQuery("Select b from Book b where b.title =?1")
                .setParameter(1, title)
                .getResultList();

        return findBookByTitle;
    }

    /**
     * Get books from the database with the title provided(doesn't need to match
     * whole)
     *
     * @param title name of the book that is being searched
     * @return List of books found with the title
     */
    public List<Book> findBooksByTitle(String title) {
        List<Book> findBookByTitle = em.createQuery("Select b from Book b where b.title LIKE ?1 order by b.title asc")
                .setParameter(1, title + "%")
                .getResultList();

        return findBookByTitle;
    }

    /**
     * Get books from the database with the isbn provided(must match whole)
     *
     * @param isbn number of the book the user is searching for
     * @return Book found with the isbn
     */
    public List<Book> findBookByAuthor(String author) {
        List<Book> findBookByAuthor = em.createQuery("Select b from Book b where b.isbnNumber = ?1")
                .setParameter(1, author)
                .getResultList();

        return findBookByAuthor;
    }

    /**
     * Get books from the database with the isbn provided(must match whole)
     *
     * @param isbn number of the book the user is searching for
     * @return Book found with the isbn
     */
    public List<Book> findBookByIsbn(String isbn) {
        List<Book> findBookByIsbn = em.createQuery("Select b from Book b where b.isbnNumber = ?1")
                .setParameter(1, isbn)
                .getResultList();

        return findBookByIsbn;
    }

    /**
     * Get books from the database with the author provided.
     *
     * @param publisher of the book we are finding
     * @return books found with the associated publisher
     */
    public List<Book> findBooksByPublisher(String publisher) {
        List<Book> findBookByPublisher = em.createQuery("Select b from Book b where b.publisher = ?1")
                .setParameter(1, publisher)
                .getResultList();

        return findBookByPublisher;
    }

    /**
     * Get publishers from the database with the publisher provided(doesn't need
     * to match whole)
     *
     * @param title publisher that is being searched
     * @return List of books found with the publisher
     */
    public List<Book> findDistinctPublisher(String publisher) {
        List<Book> findPublisher = em.createQuery("Select distinct(b.publisher) from Book b where b.publisher LIKE ?1 order by b.publisher asc")
                .setParameter(1, publisher + "%")
                .getResultList();

        return findPublisher;
    }

    /**
     * Get a List of Book objects comprised only of Books which their Sale Price
     * is bigger than 0.
     *
     * @return List of books on sale.
     */
    //Author: Sebastian
    public List<Book> findBooksOnSale() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Book> book = cq.from(Book.class);
        cq.select(book).where(cb.gt(book.get("salePrice"), 0));
        TypedQuery<Book> query = em.createQuery(cq);
        List<Book> toReturn = query.getResultList();
        return toReturn;
    }

    /**
     * Get a List of Book objects comprised only of Books which their Sale Price
     * is equal to 0.
     *
     * @return List of books not on sale.
     */
    //Author: Sebastian
    public List<Book> findBooksNotSale() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Book> book = cq.from(Book.class);
        cq.select(book).where(cb.equal(book.get("salePrice"), 0));
        TypedQuery<Book> query = em.createQuery(cq);
        List<Book> toReturn = query.getResultList();
        return toReturn;
    }
    
    public List<Book> getRecommendedBooks(String[] genres)
    {   
        String whereIN = "";
        
        for(String genre : genres)
            whereIN += "'" + genre + "',";
        
        //Remove last comma
        whereIN = whereIN.substring(0, whereIN.length() - 1);
        
        Query query = em.createNativeQuery("select * from book where genre in (" + whereIN + ") order by rand() limit 10", Book.class);
        
        List<Book> books = (List<Book>)query.getResultList();
        
        return books;
    }
}
