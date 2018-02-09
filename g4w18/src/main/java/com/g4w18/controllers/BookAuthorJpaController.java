
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
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
/**
 *
 * @author Salman Haidar
 */
@Named
@SessionScoped
public class BookAuthorJpaController implements Serializable {

    public BookAuthorJpaController() {
     
    }
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;
    
    

    

    public void create(BookAuthor bookAuthor) throws RollbackFailureException, Exception {
       
        try {
            utx.begin();
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
        } 
    }

    public void edit(BookAuthor bookAuthor) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
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
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();
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
        }
    }

    public List<BookAuthor> findBookAuthorEntities() {
        return findBookAuthorEntities(true, -1, -1);
    }

    public List<BookAuthor> findBookAuthorEntities(int maxResults, int firstResult) {
        return findBookAuthorEntities(false, maxResults, firstResult);
    }
    
    
    
    private List<BookAuthor> findBookAuthorEntities(boolean all, int maxResults, int firstResult) {
       
        
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookAuthor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        
    }

    public BookAuthor findBookAuthor(Integer id) {
        
            return em.find(BookAuthor.class, id);
        
    }

    public int getBookAuthorCount() {
       
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookAuthor> rt = cq.from(BookAuthor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
       
    }
    
}
