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
import com.g4w18.entities.*;
import java.util.ArrayList;
import java.util.List;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.Review;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1430047
 */
@Named
@RequestScoped
public class BookJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Book book) throws RollbackFailureException, Exception {
        if (book.getAuthorList() == null) {
            book.setAuthorList(new ArrayList<Author>());
        }
        if (book.getInvoiceDetailList() == null) {
            book.setInvoiceDetailList(new ArrayList<InvoiceDetail>());
        }
        if (book.getReviewList() == null) {
            book.setReviewList(new ArrayList<Review>());
        }

        try {
            utx.begin();
            List<Author> attachedAuthorList = new ArrayList<Author>();
            for (Author authorListAuthorToAttach : book.getAuthorList()) {
                authorListAuthorToAttach = em.getReference(authorListAuthorToAttach.getClass(), authorListAuthorToAttach.getAuthorId());
                attachedAuthorList.add(authorListAuthorToAttach);
            }
            book.setAuthorList(attachedAuthorList);
            List<InvoiceDetail> attachedInvoiceDetailList = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailListInvoiceDetailToAttach : book.getInvoiceDetailList()) {
                invoiceDetailListInvoiceDetailToAttach = em.getReference(invoiceDetailListInvoiceDetailToAttach.getClass(), invoiceDetailListInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailList.add(invoiceDetailListInvoiceDetailToAttach);
            }
            book.setInvoiceDetailList(attachedInvoiceDetailList);
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : book.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            book.setReviewList(attachedReviewList);
            em.persist(book);
            for (Author authorListAuthor : book.getAuthorList()) {
                authorListAuthor.getBookList().add(book);
                authorListAuthor = em.merge(authorListAuthor);
            }
            for (InvoiceDetail invoiceDetailListInvoiceDetail : book.getInvoiceDetailList()) {
                Book oldBookIdOfInvoiceDetailListInvoiceDetail = invoiceDetailListInvoiceDetail.getBookId();
                invoiceDetailListInvoiceDetail.setBookId(book);
                invoiceDetailListInvoiceDetail = em.merge(invoiceDetailListInvoiceDetail);
                if (oldBookIdOfInvoiceDetailListInvoiceDetail != null) {
                    oldBookIdOfInvoiceDetailListInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListInvoiceDetail);
                    oldBookIdOfInvoiceDetailListInvoiceDetail = em.merge(oldBookIdOfInvoiceDetailListInvoiceDetail);
                }
            }
            for (Review reviewListReview : book.getReviewList()) {
                Book oldBookIdOfReviewListReview = reviewListReview.getBookId();
                reviewListReview.setBookId(book);
                reviewListReview = em.merge(reviewListReview);
                if (oldBookIdOfReviewListReview != null) {
                    oldBookIdOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldBookIdOfReviewListReview = em.merge(oldBookIdOfReviewListReview);
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
            List<Author> authorListOld = persistentBook.getAuthorList();
            List<Author> authorListNew = book.getAuthorList();
            List<InvoiceDetail> invoiceDetailListOld = persistentBook.getInvoiceDetailList();
            List<InvoiceDetail> invoiceDetailListNew = book.getInvoiceDetailList();
            List<Review> reviewListOld = persistentBook.getReviewList();
            List<Review> reviewListNew = book.getReviewList();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetail invoiceDetailListOldInvoiceDetail : invoiceDetailListOld) {
                if (!invoiceDetailListNew.contains(invoiceDetailListOldInvoiceDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetail " + invoiceDetailListOldInvoiceDetail + " since its bookId field is not nullable.");
                }
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewListOldReview + " since its bookId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Author> attachedAuthorListNew = new ArrayList<Author>();
            for (Author authorListNewAuthorToAttach : authorListNew) {
                authorListNewAuthorToAttach = em.getReference(authorListNewAuthorToAttach.getClass(), authorListNewAuthorToAttach.getAuthorId());
                attachedAuthorListNew.add(authorListNewAuthorToAttach);
            }
            authorListNew = attachedAuthorListNew;
            book.setAuthorList(authorListNew);
            List<InvoiceDetail> attachedInvoiceDetailListNew = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailListNewInvoiceDetailToAttach : invoiceDetailListNew) {
                invoiceDetailListNewInvoiceDetailToAttach = em.getReference(invoiceDetailListNewInvoiceDetailToAttach.getClass(), invoiceDetailListNewInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailListNew.add(invoiceDetailListNewInvoiceDetailToAttach);
            }
            invoiceDetailListNew = attachedInvoiceDetailListNew;
            book.setInvoiceDetailList(invoiceDetailListNew);
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewId());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            book.setReviewList(reviewListNew);
            book = em.merge(book);
            for (Author authorListOldAuthor : authorListOld) {
                if (!authorListNew.contains(authorListOldAuthor)) {
                    authorListOldAuthor.getBookList().remove(book);
                    authorListOldAuthor = em.merge(authorListOldAuthor);
                }
            }
            for (Author authorListNewAuthor : authorListNew) {
                if (!authorListOld.contains(authorListNewAuthor)) {
                    authorListNewAuthor.getBookList().add(book);
                    authorListNewAuthor = em.merge(authorListNewAuthor);
                }
            }
            for (InvoiceDetail invoiceDetailListNewInvoiceDetail : invoiceDetailListNew) {
                if (!invoiceDetailListOld.contains(invoiceDetailListNewInvoiceDetail)) {
                    Book oldBookIdOfInvoiceDetailListNewInvoiceDetail = invoiceDetailListNewInvoiceDetail.getBookId();
                    invoiceDetailListNewInvoiceDetail.setBookId(book);
                    invoiceDetailListNewInvoiceDetail = em.merge(invoiceDetailListNewInvoiceDetail);
                    if (oldBookIdOfInvoiceDetailListNewInvoiceDetail != null && !oldBookIdOfInvoiceDetailListNewInvoiceDetail.equals(book)) {
                        oldBookIdOfInvoiceDetailListNewInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListNewInvoiceDetail);
                        oldBookIdOfInvoiceDetailListNewInvoiceDetail = em.merge(oldBookIdOfInvoiceDetailListNewInvoiceDetail);
                    }
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Book oldBookIdOfReviewListNewReview = reviewListNewReview.getBookId();
                    reviewListNewReview.setBookId(book);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldBookIdOfReviewListNewReview != null && !oldBookIdOfReviewListNewReview.equals(book)) {
                        oldBookIdOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldBookIdOfReviewListNewReview = em.merge(oldBookIdOfReviewListNewReview);
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
            List<InvoiceDetail> invoiceDetailListOrphanCheck = book.getInvoiceDetailList();
            for (InvoiceDetail invoiceDetailListOrphanCheckInvoiceDetail : invoiceDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailListOrphanCheckInvoiceDetail + " in its invoiceDetailList field has a non-nullable bookId field.");
            }
            List<Review> reviewListOrphanCheck = book.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable bookId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Author> authorList = book.getAuthorList();
            for (Author authorListAuthor : authorList) {
                authorListAuthor.getBookList().remove(book);
                authorListAuthor = em.merge(authorListAuthor);
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
}
