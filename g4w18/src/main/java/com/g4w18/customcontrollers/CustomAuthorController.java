/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.entities.Author;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author 1331680
 */
public class CustomAuthorController implements Serializable
{
    @Inject
    private AuthorJpaController authorController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
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
