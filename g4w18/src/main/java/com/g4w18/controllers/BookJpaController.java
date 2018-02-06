/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Book;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.InvoiceDetail;
import java.util.ArrayList;
import java.util.List;
import com.g4w18.entities.Review;
import com.g4w18.entities.BookAuthor;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
        if (book.getInvoiceDetailList() == null) {
            book.setInvoiceDetailList(new ArrayList<>());
        }
        if (book.getReviewList() == null) {
            book.setReviewList(new ArrayList<>());
        }
        if (book.getBookAuthorList() == null) {
            book.setBookAuthorList(new ArrayList<>());
        }
        try {
            utx.begin();
            List<InvoiceDetail> attachedInvoiceDetailList = new ArrayList<>();
            book.getInvoiceDetailList().stream().map((invoiceDetailListInvoiceDetailToAttach) -> em.getReference(invoiceDetailListInvoiceDetailToAttach.getClass(), invoiceDetailListInvoiceDetailToAttach.getDetailId())).forEachOrdered((invoiceDetailListInvoiceDetailToAttach) -> {
                attachedInvoiceDetailList.add(invoiceDetailListInvoiceDetailToAttach);
            });
            book.setInvoiceDetailList(attachedInvoiceDetailList);
            List<Review> attachedReviewList = new ArrayList<>();
            book.getReviewList().stream().map((reviewListReviewToAttach) -> em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewId())).forEachOrdered((reviewListReviewToAttach) -> {
                attachedReviewList.add(reviewListReviewToAttach);
            });
            book.setReviewList(attachedReviewList);
            List<BookAuthor> attachedBookAuthorList = new ArrayList<>();
            book.getBookAuthorList().stream().map((bookAuthorListBookAuthorToAttach) -> em.getReference(bookAuthorListBookAuthorToAttach.getClass(), bookAuthorListBookAuthorToAttach.getBookAuthorId())).forEachOrdered((bookAuthorListBookAuthorToAttach) -> {
                attachedBookAuthorList.add(bookAuthorListBookAuthorToAttach);
            });
            book.setBookAuthorList(attachedBookAuthorList);
            em.persist(book);
            book.getInvoiceDetailList().forEach((invoiceDetailListInvoiceDetail) -> {
                Book oldBookIdOfInvoiceDetailListInvoiceDetail = invoiceDetailListInvoiceDetail.getBookId();
                invoiceDetailListInvoiceDetail.setBookId(book);
                invoiceDetailListInvoiceDetail = em.merge(invoiceDetailListInvoiceDetail);
                if (oldBookIdOfInvoiceDetailListInvoiceDetail != null) {
                    oldBookIdOfInvoiceDetailListInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListInvoiceDetail);
                    oldBookIdOfInvoiceDetailListInvoiceDetail = em.merge(oldBookIdOfInvoiceDetailListInvoiceDetail);
                }
            });
            book.getReviewList().forEach((reviewListReview) -> {
                Book oldBookIdOfReviewListReview = reviewListReview.getBookId();
                reviewListReview.setBookId(book);
                reviewListReview = em.merge(reviewListReview);
                if (oldBookIdOfReviewListReview != null) {
                    oldBookIdOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldBookIdOfReviewListReview = em.merge(oldBookIdOfReviewListReview);
                }
            });
            book.getBookAuthorList().forEach((bookAuthorListBookAuthor) -> {
                Book oldBookIdOfBookAuthorListBookAuthor = bookAuthorListBookAuthor.getBookId();
                bookAuthorListBookAuthor.setBookId(book);
                bookAuthorListBookAuthor = em.merge(bookAuthorListBookAuthor);
                if (oldBookIdOfBookAuthorListBookAuthor != null) {
                    oldBookIdOfBookAuthorListBookAuthor.getBookAuthorList().remove(bookAuthorListBookAuthor);
                    oldBookIdOfBookAuthorListBookAuthor = em.merge(oldBookIdOfBookAuthorListBookAuthor);
                }
            });
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Book persistentBook = em.find(Book.class, book.getBookId());
            List<InvoiceDetail> invoiceDetailListOld = persistentBook.getInvoiceDetailList();
            List<InvoiceDetail> invoiceDetailListNew = book.getInvoiceDetailList();
            List<Review> reviewListOld = persistentBook.getReviewList();
            List<Review> reviewListNew = book.getReviewList();
            List<BookAuthor> bookAuthorListOld = persistentBook.getBookAuthorList();
            List<BookAuthor> bookAuthorListNew = book.getBookAuthorList();
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
            for (BookAuthor bookAuthorListOldBookAuthor : bookAuthorListOld) {
                if (!bookAuthorListNew.contains(bookAuthorListOldBookAuthor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookAuthor " + bookAuthorListOldBookAuthor + " since its bookId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
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
            List<BookAuthor> attachedBookAuthorListNew = new ArrayList<BookAuthor>();
            for (BookAuthor bookAuthorListNewBookAuthorToAttach : bookAuthorListNew) {
                bookAuthorListNewBookAuthorToAttach = em.getReference(bookAuthorListNewBookAuthorToAttach.getClass(), bookAuthorListNewBookAuthorToAttach.getBookAuthorId());
                attachedBookAuthorListNew.add(bookAuthorListNewBookAuthorToAttach);
            }
            bookAuthorListNew = attachedBookAuthorListNew;
            book.setBookAuthorList(bookAuthorListNew);
            book = em.merge(book);
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
            for (BookAuthor bookAuthorListNewBookAuthor : bookAuthorListNew) {
                if (!bookAuthorListOld.contains(bookAuthorListNewBookAuthor)) {
                    Book oldBookIdOfBookAuthorListNewBookAuthor = bookAuthorListNewBookAuthor.getBookId();
                    bookAuthorListNewBookAuthor.setBookId(book);
                    bookAuthorListNewBookAuthor = em.merge(bookAuthorListNewBookAuthor);
                    if (oldBookIdOfBookAuthorListNewBookAuthor != null && !oldBookIdOfBookAuthorListNewBookAuthor.equals(book)) {
                        oldBookIdOfBookAuthorListNewBookAuthor.getBookAuthorList().remove(bookAuthorListNewBookAuthor);
                        oldBookIdOfBookAuthorListNewBookAuthor = em.merge(oldBookIdOfBookAuthorListNewBookAuthor);
                    }
                }
            }
            utx.commit();
        } catch (IllegalOrphanException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
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
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailListOrphanCheckInvoiceDetail + " in its invoiceDetailList field has a non-nullable bookId field.");
            }
            List<Review> reviewListOrphanCheck = book.getReviewList();
            for (Review reviewListOrphanCheckReview : reviewListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Review " + reviewListOrphanCheckReview + " in its reviewList field has a non-nullable bookId field.");
            }
            List<BookAuthor> bookAuthorListOrphanCheck = book.getBookAuthorList();
            for (BookAuthor bookAuthorListOrphanCheckBookAuthor : bookAuthorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the BookAuthor " + bookAuthorListOrphanCheckBookAuthor + " in its bookAuthorList field has a non-nullable bookId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(book);
            utx.commit();
        } catch (IllegalOrphanException | NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Book> findBookEntities() {
        List<Book> lb = findBookEntities(true, -1, -1);
        System.out.println("Length = " + lb.size());
        return lb;
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
