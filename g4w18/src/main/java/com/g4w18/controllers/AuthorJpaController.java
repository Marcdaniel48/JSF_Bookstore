/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Author;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Book;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1331680
 */
@Named
@RequestScoped
public class AuthorJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Author author) throws RollbackFailureException, Exception {
        if (author.getBookCollection() == null) {
            author.setBookCollection(new ArrayList<Book>());
        }
        try {
            utx.begin();
            Collection<Book> attachedBookCollection = new ArrayList<Book>();
            for (Book bookCollectionBookToAttach : author.getBookCollection()) {
                bookCollectionBookToAttach = em.getReference(bookCollectionBookToAttach.getClass(), bookCollectionBookToAttach.getBookId());
                attachedBookCollection.add(bookCollectionBookToAttach);
            }
            author.setBookCollection(attachedBookCollection);
            em.persist(author);
            for (Book bookCollectionBook : author.getBookCollection()) {
                bookCollectionBook.getAuthorCollection().add(author);
                bookCollectionBook = em.merge(bookCollectionBook);
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

    public void edit(Author author) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Author persistentAuthor = em.find(Author.class, author.getAuthorId());
            Collection<Book> bookCollectionOld = persistentAuthor.getBookCollection();
            Collection<Book> bookCollectionNew = author.getBookCollection();
            Collection<Book> attachedBookCollectionNew = new ArrayList<Book>();
            for (Book bookCollectionNewBookToAttach : bookCollectionNew) {
                bookCollectionNewBookToAttach = em.getReference(bookCollectionNewBookToAttach.getClass(), bookCollectionNewBookToAttach.getBookId());
                attachedBookCollectionNew.add(bookCollectionNewBookToAttach);
            }
            bookCollectionNew = attachedBookCollectionNew;
            author.setBookCollection(bookCollectionNew);
            author = em.merge(author);
            for (Book bookCollectionOldBook : bookCollectionOld) {
                if (!bookCollectionNew.contains(bookCollectionOldBook)) {
                    bookCollectionOldBook.getAuthorCollection().remove(author);
                    bookCollectionOldBook = em.merge(bookCollectionOldBook);
                }
            }
            for (Book bookCollectionNewBook : bookCollectionNew) {
                if (!bookCollectionOld.contains(bookCollectionNewBook)) {
                    bookCollectionNewBook.getAuthorCollection().add(author);
                    bookCollectionNewBook = em.merge(bookCollectionNewBook);
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            Collection<Book> bookCollection = author.getBookCollection();
            for (Book bookCollectionBook : bookCollection) {
                bookCollectionBook.getAuthorCollection().remove(author);
                bookCollectionBook = em.merge(bookCollectionBook);
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
