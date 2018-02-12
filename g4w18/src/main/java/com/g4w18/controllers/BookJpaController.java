
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
public class BookJpaController implements Serializable {

    public BookJpaController() {
        
    }
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

   

    public void create(Book book) throws RollbackFailureException, Exception {
        if (book.getInvoiceDetailList() == null) {
            book.setInvoiceDetailList(new ArrayList<InvoiceDetail>());
        }
        if (book.getReviewList() == null) {
            book.setReviewList(new ArrayList<Review>());
        }
        if (book.getBookAuthorList() == null) {
            book.setBookAuthorList(new ArrayList<BookAuthor>());
        }
        try {
            utx.begin();
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
            List<BookAuthor> attachedBookAuthorList = new ArrayList<BookAuthor>();
            for (BookAuthor bookAuthorListBookAuthorToAttach : book.getBookAuthorList()) {
                bookAuthorListBookAuthorToAttach = em.getReference(bookAuthorListBookAuthorToAttach.getClass(), bookAuthorListBookAuthorToAttach.getBookAuthorId());
                attachedBookAuthorList.add(bookAuthorListBookAuthorToAttach);
            }
            book.setBookAuthorList(attachedBookAuthorList);
            em.persist(book);
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
            for (BookAuthor bookAuthorListBookAuthor : book.getBookAuthorList()) {
                Book oldBookIdOfBookAuthorListBookAuthor = bookAuthorListBookAuthor.getBookId();
                bookAuthorListBookAuthor.setBookId(book);
                bookAuthorListBookAuthor = em.merge(bookAuthorListBookAuthor);
                if (oldBookIdOfBookAuthorListBookAuthor != null) {
                    oldBookIdOfBookAuthorListBookAuthor.getBookAuthorList().remove(bookAuthorListBookAuthor);
                    oldBookIdOfBookAuthorListBookAuthor = em.merge(oldBookIdOfBookAuthorListBookAuthor);
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
            List<BookAuthor> bookAuthorListOrphanCheck = book.getBookAuthorList();
            for (BookAuthor bookAuthorListOrphanCheckBookAuthor : bookAuthorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the BookAuthor " + bookAuthorListOrphanCheckBookAuthor + " in its bookAuthorList field has a non-nullable bookId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
    
    /**
     * Get books from the database with the title provided(doesn't need to match whole)
     * 
     * @param title name of the book that is being searched
     * @return List of books found with the title
     */
    public List<Book> findBooksByTitle(String title)
    {
        List<Book> findBookByTitle = em.createQuery("Select b from Book b where b.title LIKE ?1 order by b.title asc")
                .setParameter(1, title + "%")
                .getResultList();
        
        return findBookByTitle;
    }
    
    /**
     * Get books from the database with the isbn provided(must match whole) 
     * 
     * @param isbn number of the book the user is searching for
     * @return Book found with the isbn
     */
    public List<Book> findBookByIsbn(String isbn)
    {
        List<Book> findBookByIsbn = em.createQuery("Select b from Book b where b.isbnNumber = ?1")
                .setParameter(1, isbn)
                .getResultList();
        
        return findBookByIsbn;
    }
    
    /**
     * Get books from the database with the author provided.
     * 
     * @param publisher of the book we are finding 
     * @return books found with the associated publisher
     */
    public List<Book> findBooksByPublisher(String publisher)
    {
        List<Book> findBookByPublisher = em.createQuery("Select b from Book b where b.publisher = ?1")
                .setParameter(1, publisher)
                .getResultList();
        
        return findBookByPublisher;
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
