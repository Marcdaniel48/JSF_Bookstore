/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.entities.Banner;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    public List<Book> getBooksOnSale()
    {
        Query findBooksOnSale = em.createNamedQuery("Book.findOnSale");
        List<Book> books = findBooksOnSale.getResultList();
        return books;
    }
    
    public List<Book> getMostRecentBooks()
    {
        //Query findRecentBooks = em.createNamedQuery("Book.findMostRecentBooks");

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
