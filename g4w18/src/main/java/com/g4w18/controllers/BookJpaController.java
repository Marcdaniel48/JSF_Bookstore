/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import java.util.ArrayList;
import java.util.Collection;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.Review;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author 1331680
 */
@Named
@RequestScoped
public class BookJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Book book) throws RollbackFailureException, Exception {
        if (book.getAuthorCollection() == null) {
            book.setAuthorCollection(new ArrayList<Author>());
        }
        if (book.getInvoiceDetailCollection() == null) {
            book.setInvoiceDetailCollection(new ArrayList<InvoiceDetail>());
        }
        if (book.getReviewCollection() == null) {
            book.setReviewCollection(new ArrayList<Review>());
        }
        try {
            utx.begin();
            Collection<Author> attachedAuthorCollection = new ArrayList<Author>();
            for (Author authorCollectionAuthorToAttach : book.getAuthorCollection()) {
                authorCollectionAuthorToAttach = em.getReference(authorCollectionAuthorToAttach.getClass(), authorCollectionAuthorToAttach.getAuthorId());
                attachedAuthorCollection.add(authorCollectionAuthorToAttach);
            }
            book.setAuthorCollection(attachedAuthorCollection);
            Collection<InvoiceDetail> attachedInvoiceDetailCollection = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailCollectionInvoiceDetailToAttach : book.getInvoiceDetailCollection()) {
                invoiceDetailCollectionInvoiceDetailToAttach = em.getReference(invoiceDetailCollectionInvoiceDetailToAttach.getClass(), invoiceDetailCollectionInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailCollection.add(invoiceDetailCollectionInvoiceDetailToAttach);
            }
            book.setInvoiceDetailCollection(attachedInvoiceDetailCollection);
            Collection<Review> attachedReviewCollection = new ArrayList<Review>();
            for (Review reviewCollectionReviewToAttach : book.getReviewCollection()) {
                reviewCollectionReviewToAttach = em.getReference(reviewCollectionReviewToAttach.getClass(), reviewCollectionReviewToAttach.getReviewId());
                attachedReviewCollection.add(reviewCollectionReviewToAttach);
            }
            book.setReviewCollection(attachedReviewCollection);
            em.persist(book);
            for (Author authorCollectionAuthor : book.getAuthorCollection()) {
                authorCollectionAuthor.getBookCollection().add(book);
                authorCollectionAuthor = em.merge(authorCollectionAuthor);
            }
            for (InvoiceDetail invoiceDetailCollectionInvoiceDetail : book.getInvoiceDetailCollection()) {
                Book oldBookIdOfInvoiceDetailCollectionInvoiceDetail = invoiceDetailCollectionInvoiceDetail.getBookId();
                invoiceDetailCollectionInvoiceDetail.setBookId(book);
                invoiceDetailCollectionInvoiceDetail = em.merge(invoiceDetailCollectionInvoiceDetail);
                if (oldBookIdOfInvoiceDetailCollectionInvoiceDetail != null) {
                    oldBookIdOfInvoiceDetailCollectionInvoiceDetail.getInvoiceDetailCollection().remove(invoiceDetailCollectionInvoiceDetail);
                    oldBookIdOfInvoiceDetailCollectionInvoiceDetail = em.merge(oldBookIdOfInvoiceDetailCollectionInvoiceDetail);
                }
            }
            for (Review reviewCollectionReview : book.getReviewCollection()) {
                Book oldBookIdOfReviewCollectionReview = reviewCollectionReview.getBookId();
                reviewCollectionReview.setBookId(book);
                reviewCollectionReview = em.merge(reviewCollectionReview);
                if (oldBookIdOfReviewCollectionReview != null) {
                    oldBookIdOfReviewCollectionReview.getReviewCollection().remove(reviewCollectionReview);
                    oldBookIdOfReviewCollectionReview = em.merge(oldBookIdOfReviewCollectionReview);
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

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Book persistentBook = em.find(Book.class, book.getBookId());
            Collection<Author> authorCollectionOld = persistentBook.getAuthorCollection();
            Collection<Author> authorCollectionNew = book.getAuthorCollection();
            Collection<InvoiceDetail> invoiceDetailCollectionOld = persistentBook.getInvoiceDetailCollection();
            Collection<InvoiceDetail> invoiceDetailCollectionNew = book.getInvoiceDetailCollection();
            Collection<Review> reviewCollectionOld = persistentBook.getReviewCollection();
            Collection<Review> reviewCollectionNew = book.getReviewCollection();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetail invoiceDetailCollectionOldInvoiceDetail : invoiceDetailCollectionOld) {
                if (!invoiceDetailCollectionNew.contains(invoiceDetailCollectionOldInvoiceDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetail " + invoiceDetailCollectionOldInvoiceDetail + " since its bookId field is not nullable.");
                }
            }
            for (Review reviewCollectionOldReview : reviewCollectionOld) {
                if (!reviewCollectionNew.contains(reviewCollectionOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewCollectionOldReview + " since its bookId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Author> attachedAuthorCollectionNew = new ArrayList<Author>();
            for (Author authorCollectionNewAuthorToAttach : authorCollectionNew) {
                authorCollectionNewAuthorToAttach = em.getReference(authorCollectionNewAuthorToAttach.getClass(), authorCollectionNewAuthorToAttach.getAuthorId());
                attachedAuthorCollectionNew.add(authorCollectionNewAuthorToAttach);
            }
            authorCollectionNew = attachedAuthorCollectionNew;
            book.setAuthorCollection(authorCollectionNew);
            Collection<InvoiceDetail> attachedInvoiceDetailCollectionNew = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailCollectionNewInvoiceDetailToAttach : invoiceDetailCollectionNew) {
                invoiceDetailCollectionNewInvoiceDetailToAttach = em.getReference(invoiceDetailCollectionNewInvoiceDetailToAttach.getClass(), invoiceDetailCollectionNewInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailCollectionNew.add(invoiceDetailCollectionNewInvoiceDetailToAttach);
            }
            invoiceDetailCollectionNew = attachedInvoiceDetailCollectionNew;
            book.setInvoiceDetailCollection(invoiceDetailCollectionNew);
            Collection<Review> attachedReviewCollectionNew = new ArrayList<Review>();
            for (Review reviewCollectionNewReviewToAttach : reviewCollectionNew) {
                reviewCollectionNewReviewToAttach = em.getReference(reviewCollectionNewReviewToAttach.getClass(), reviewCollectionNewReviewToAttach.getReviewId());
                attachedReviewCollectionNew.add(reviewCollectionNewReviewToAttach);
            }
            reviewCollectionNew = attachedReviewCollectionNew;
            book.setReviewCollection(reviewCollectionNew);
            book = em.merge(book);
            for (Author authorCollectionOldAuthor : authorCollectionOld) {
                if (!authorCollectionNew.contains(authorCollectionOldAuthor)) {
                    authorCollectionOldAuthor.getBookCollection().remove(book);
                    authorCollectionOldAuthor = em.merge(authorCollectionOldAuthor);
                }
            }
            for (Author authorCollectionNewAuthor : authorCollectionNew) {
                if (!authorCollectionOld.contains(authorCollectionNewAuthor)) {
                    authorCollectionNewAuthor.getBookCollection().add(book);
                    authorCollectionNewAuthor = em.merge(authorCollectionNewAuthor);
                }
            }
            for (InvoiceDetail invoiceDetailCollectionNewInvoiceDetail : invoiceDetailCollectionNew) {
                if (!invoiceDetailCollectionOld.contains(invoiceDetailCollectionNewInvoiceDetail)) {
                    Book oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail = invoiceDetailCollectionNewInvoiceDetail.getBookId();
                    invoiceDetailCollectionNewInvoiceDetail.setBookId(book);
                    invoiceDetailCollectionNewInvoiceDetail = em.merge(invoiceDetailCollectionNewInvoiceDetail);
                    if (oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail != null && !oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail.equals(book)) {
                        oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail.getInvoiceDetailCollection().remove(invoiceDetailCollectionNewInvoiceDetail);
                        oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail = em.merge(oldBookIdOfInvoiceDetailCollectionNewInvoiceDetail);
                    }
                }
            }
            for (Review reviewCollectionNewReview : reviewCollectionNew) {
                if (!reviewCollectionOld.contains(reviewCollectionNewReview)) {
                    Book oldBookIdOfReviewCollectionNewReview = reviewCollectionNewReview.getBookId();
                    reviewCollectionNewReview.setBookId(book);
                    reviewCollectionNewReview = em.merge(reviewCollectionNewReview);
                    if (oldBookIdOfReviewCollectionNewReview != null && !oldBookIdOfReviewCollectionNewReview.equals(book)) {
                        oldBookIdOfReviewCollectionNewReview.getReviewCollection().remove(reviewCollectionNewReview);
                        oldBookIdOfReviewCollectionNewReview = em.merge(oldBookIdOfReviewCollectionNewReview);
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
                Integer id = book.getBookId();
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getBookId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InvoiceDetail> invoiceDetailCollectionOrphanCheck = book.getInvoiceDetailCollection();
            for (InvoiceDetail invoiceDetailCollectionOrphanCheckInvoiceDetail : invoiceDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailCollectionOrphanCheckInvoiceDetail + " in its invoiceDetailCollection field has a non-nullable bookId field.");
            }
            Collection<Review> reviewCollectionOrphanCheck = book.getReviewCollection();
            for (Review reviewCollectionOrphanCheckReview : reviewCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Review " + reviewCollectionOrphanCheckReview + " in its reviewCollection field has a non-nullable bookId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Author> authorCollection = book.getAuthorCollection();
            for (Author authorCollectionAuthor : authorCollection) {
                authorCollectionAuthor.getBookCollection().remove(book);
                authorCollectionAuthor = em.merge(authorCollectionAuthor);
            }
            em.remove(book);
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

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Book.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Book findBook(Integer id) {
        return em.find(Book.class, id);
    }

    public int getBookCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Book> rt = cq.from(Book.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
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
    
//    public Book test(int id){
//        TypedQuery<Book> query = em.createNamedQuery("Book.findByBookId", Book.class);
//        query.setParameter("bookId", id);
//        Book book = query.getSingleResult();
//        return book;
//    }

}
