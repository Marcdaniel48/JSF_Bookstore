/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Book;
import com.g4w18.entities.Author;
import com.g4w18.entities.BookAuthor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Marc-Daniel
 */
public class BookAuthorJpaController implements Serializable {

    public BookAuthorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BookAuthor bookAuthor) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Book bookId = bookAuthor.getBookId();
            if (bookId != null) {
                bookId = em.getReference(bookId.getClass(), bookId.getBookId());
                bookAuthor.setBookId(bookId);
            }
            Author authorId = bookAuthor.getAuthorId();
            if (authorId != null) {
                authorId = em.getReference(authorId.getClass(), authorId.getAuthorId());
                bookAuthor.setAuthorId(authorId);
            }
            em.persist(bookAuthor);
            if (bookId != null) {
                bookId.getBookAuthorList().add(bookAuthor);
                bookId = em.merge(bookId);
            }
            if (authorId != null) {
                authorId.getBookAuthorList().add(bookAuthor);
                authorId = em.merge(authorId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BookAuthor bookAuthor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BookAuthor persistentBookAuthor = em.find(BookAuthor.class, bookAuthor.getBookAuthorId());
            Book bookIdOld = persistentBookAuthor.getBookId();
            Book bookIdNew = bookAuthor.getBookId();
            Author authorIdOld = persistentBookAuthor.getAuthorId();
            Author authorIdNew = bookAuthor.getAuthorId();
            if (bookIdNew != null) {
                bookIdNew = em.getReference(bookIdNew.getClass(), bookIdNew.getBookId());
                bookAuthor.setBookId(bookIdNew);
            }
            if (authorIdNew != null) {
                authorIdNew = em.getReference(authorIdNew.getClass(), authorIdNew.getAuthorId());
                bookAuthor.setAuthorId(authorIdNew);
            }
            bookAuthor = em.merge(bookAuthor);
            if (bookIdOld != null && !bookIdOld.equals(bookIdNew)) {
                bookIdOld.getBookAuthorList().remove(bookAuthor);
                bookIdOld = em.merge(bookIdOld);
            }
            if (bookIdNew != null && !bookIdNew.equals(bookIdOld)) {
                bookIdNew.getBookAuthorList().add(bookAuthor);
                bookIdNew = em.merge(bookIdNew);
            }
            if (authorIdOld != null && !authorIdOld.equals(authorIdNew)) {
                authorIdOld.getBookAuthorList().remove(bookAuthor);
                authorIdOld = em.merge(authorIdOld);
            }
            if (authorIdNew != null && !authorIdNew.equals(authorIdOld)) {
                authorIdNew.getBookAuthorList().add(bookAuthor);
                authorIdNew = em.merge(authorIdNew);
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
                Integer id = bookAuthor.getBookAuthorId();
                if (findBookAuthor(id) == null) {
                    throw new NonexistentEntityException("The bookAuthor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            BookAuthor bookAuthor;
            try {
                bookAuthor = em.getReference(BookAuthor.class, id);
                bookAuthor.getBookAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookAuthor with id " + id + " no longer exists.", enfe);
            }
            Book bookId = bookAuthor.getBookId();
            if (bookId != null) {
                bookId.getBookAuthorList().remove(bookAuthor);
                bookId = em.merge(bookId);
            }
            Author authorId = bookAuthor.getAuthorId();
            if (authorId != null) {
                authorId.getBookAuthorList().remove(bookAuthor);
                authorId = em.merge(authorId);
            }
            em.remove(bookAuthor);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BookAuthor> findBookAuthorEntities() {
        return findBookAuthorEntities(true, -1, -1);
    }

    public List<BookAuthor> findBookAuthorEntities(int maxResults, int firstResult) {
        return findBookAuthorEntities(false, maxResults, firstResult);
    }

    private List<BookAuthor> findBookAuthorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookAuthor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BookAuthor findBookAuthor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BookAuthor.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookAuthorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookAuthor> rt = cq.from(BookAuthor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
