/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Author;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.BookAuthor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Salman Haidar
 */
@Named
@SessionScoped
public class AuthorJpaController implements Serializable {

    public AuthorJpaController() {
    }
    
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

    public void create(Author author) throws RollbackFailureException, Exception {
        if (author.getBookAuthorList() == null) {
            author.setBookAuthorList(new ArrayList<BookAuthor>());
        }
        try {
            utx.begin();
            List<BookAuthor> attachedBookAuthorList = new ArrayList<BookAuthor>();
            for (BookAuthor bookAuthorListBookAuthorToAttach : author.getBookAuthorList()) {
                bookAuthorListBookAuthorToAttach = em.getReference(bookAuthorListBookAuthorToAttach.getClass(), bookAuthorListBookAuthorToAttach.getBookAuthorId());
                attachedBookAuthorList.add(bookAuthorListBookAuthorToAttach);
            }
            author.setBookAuthorList(attachedBookAuthorList);
            em.persist(author);
            for (BookAuthor bookAuthorListBookAuthor : author.getBookAuthorList()) {
                Author oldAuthorIdOfBookAuthorListBookAuthor = bookAuthorListBookAuthor.getAuthorId();
                bookAuthorListBookAuthor.setAuthorId(author);
                bookAuthorListBookAuthor = em.merge(bookAuthorListBookAuthor);
                if (oldAuthorIdOfBookAuthorListBookAuthor != null) {
                    oldAuthorIdOfBookAuthorListBookAuthor.getBookAuthorList().remove(bookAuthorListBookAuthor);
                    oldAuthorIdOfBookAuthorListBookAuthor = em.merge(oldAuthorIdOfBookAuthorListBookAuthor);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(Author author) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        try {
            utx.begin();
            Author persistentAuthor = em.find(Author.class, author.getAuthorId());
            List<BookAuthor> bookAuthorListOld = persistentAuthor.getBookAuthorList();
            List<BookAuthor> bookAuthorListNew = author.getBookAuthorList();
            List<String> illegalOrphanMessages = null;
            for (BookAuthor bookAuthorListOldBookAuthor : bookAuthorListOld) {
                if (!bookAuthorListNew.contains(bookAuthorListOldBookAuthor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookAuthor " + bookAuthorListOldBookAuthor + " since its authorId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<BookAuthor> attachedBookAuthorListNew = new ArrayList<BookAuthor>();
            for (BookAuthor bookAuthorListNewBookAuthorToAttach : bookAuthorListNew) {
                bookAuthorListNewBookAuthorToAttach = em.getReference(bookAuthorListNewBookAuthorToAttach.getClass(), bookAuthorListNewBookAuthorToAttach.getBookAuthorId());
                attachedBookAuthorListNew.add(bookAuthorListNewBookAuthorToAttach);
            }
            bookAuthorListNew = attachedBookAuthorListNew;
            author.setBookAuthorList(bookAuthorListNew);
            author = em.merge(author);
            for (BookAuthor bookAuthorListNewBookAuthor : bookAuthorListNew) {
                if (!bookAuthorListOld.contains(bookAuthorListNewBookAuthor)) {
                    Author oldAuthorIdOfBookAuthorListNewBookAuthor = bookAuthorListNewBookAuthor.getAuthorId();
                    bookAuthorListNewBookAuthor.setAuthorId(author);
                    bookAuthorListNewBookAuthor = em.merge(bookAuthorListNewBookAuthor);
                    if (oldAuthorIdOfBookAuthorListNewBookAuthor != null && !oldAuthorIdOfBookAuthorListNewBookAuthor.equals(author)) {
                        oldAuthorIdOfBookAuthorListNewBookAuthor.getBookAuthorList().remove(bookAuthorListNewBookAuthor);
                        oldAuthorIdOfBookAuthorListNewBookAuthor = em.merge(oldAuthorIdOfBookAuthorListNewBookAuthor);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = author.getAuthorId();
                if (findAuthor(id) == null) {
                    throw new NonexistentEntityException("The author with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<BookAuthor> bookAuthorListOrphanCheck = author.getBookAuthorList();
            for (BookAuthor bookAuthorListOrphanCheckBookAuthor : bookAuthorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Author (" + author + ") cannot be destroyed since the BookAuthor " + bookAuthorListOrphanCheckBookAuthor + " in its bookAuthorList field has a non-nullable authorId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(author);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Author> findAuthorEntities() {
        return findAuthorEntities(true, -1, -1);
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult) {
        return findAuthorEntities(false, maxResults, firstResult);
    }

    private List<Author> findAuthorEntities(boolean all, int maxResults, int firstResult) {
        
     
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Author.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        
    }

    public Author findAuthor(Integer id) {
       
            return em.find(Author.class, id);
        
    }

    public int getAuthorCount() {
    
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Author> rt = cq.from(Author.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        
    }
    
}
