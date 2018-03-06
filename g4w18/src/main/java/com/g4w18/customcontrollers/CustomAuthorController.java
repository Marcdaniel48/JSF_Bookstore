/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author 1331680
 */
public class CustomAuthorController implements Serializable
{
    @Inject
    private AuthorJpaController authorController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(Author author) throws RollbackFailureException, Exception
    {
        authorController.create(author);
    }

    public void edit(Author author) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        authorController.edit(author);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        authorController.destroy(id);
    }

    public List<Author> findAuthorEntities()
    {
        return authorController.findAuthorEntities();
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult)
    {
        return authorController.findAuthorEntities(maxResults, firstResult);
    }

    public Author findAuthor(Integer id)
    {
        return authorController.findAuthor(id);
    }

    public int getAuthorCount()
    {
        return authorController.getAuthorCount();
    }
    
    /**
     * Get list of author names with the name provided
     * @param authorName provided by user
     * @return List of authors found with the param
     */
    public List<Author> findAuthor(String authorName)
    {
        List<Author> findAuthorByName = em.createQuery("Select a from Author a where CONCAT(a.firstName,' ',a.lastName) LIKE ?1")
                .setParameter(1, authorName + "%")
                .getResultList();
        
        return findAuthorByName;
    }
}
