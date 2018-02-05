/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controls;

import com.g4w18.controls.exceptions.NonexistentEntityException;
import com.g4w18.controls.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Book;
import com.g4w18.entities.InvoiceDetail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Marc-Daniel
 */
public class InvoiceDetailJpaController implements Serializable {

    public InvoiceDetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MasterInvoice invoiceId = invoiceDetail.getInvoiceId();
            if (invoiceId != null) {
                invoiceId = em.getReference(invoiceId.getClass(), invoiceId.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceId);
            }
            Book bookId = invoiceDetail.getBookId();
            if (bookId != null) {
                bookId = em.getReference(bookId.getClass(), bookId.getBookId());
                invoiceDetail.setBookId(bookId);
            }
            em.persist(invoiceDetail);
            if (invoiceId != null) {
                invoiceId.getInvoiceDetailList().add(invoiceDetail);
                invoiceId = em.merge(invoiceId);
            }
            if (bookId != null) {
                bookId.getInvoiceDetailList().add(invoiceDetail);
                bookId = em.merge(bookId);
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

    public void edit(InvoiceDetail invoiceDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InvoiceDetail persistentInvoiceDetail = em.find(InvoiceDetail.class, invoiceDetail.getDetailId());
            MasterInvoice invoiceIdOld = persistentInvoiceDetail.getInvoiceId();
            MasterInvoice invoiceIdNew = invoiceDetail.getInvoiceId();
            Book bookIdOld = persistentInvoiceDetail.getBookId();
            Book bookIdNew = invoiceDetail.getBookId();
            if (invoiceIdNew != null) {
                invoiceIdNew = em.getReference(invoiceIdNew.getClass(), invoiceIdNew.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceIdNew);
            }
            if (bookIdNew != null) {
                bookIdNew = em.getReference(bookIdNew.getClass(), bookIdNew.getBookId());
                invoiceDetail.setBookId(bookIdNew);
            }
            invoiceDetail = em.merge(invoiceDetail);
            if (invoiceIdOld != null && !invoiceIdOld.equals(invoiceIdNew)) {
                invoiceIdOld.getInvoiceDetailList().remove(invoiceDetail);
                invoiceIdOld = em.merge(invoiceIdOld);
            }
            if (invoiceIdNew != null && !invoiceIdNew.equals(invoiceIdOld)) {
                invoiceIdNew.getInvoiceDetailList().add(invoiceDetail);
                invoiceIdNew = em.merge(invoiceIdNew);
            }
            if (bookIdOld != null && !bookIdOld.equals(bookIdNew)) {
                bookIdOld.getInvoiceDetailList().remove(invoiceDetail);
                bookIdOld = em.merge(bookIdOld);
            }
            if (bookIdNew != null && !bookIdNew.equals(bookIdOld)) {
                bookIdNew.getInvoiceDetailList().add(invoiceDetail);
                bookIdNew = em.merge(bookIdNew);
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
                Integer id = invoiceDetail.getDetailId();
                if (findInvoiceDetail(id) == null) {
                    throw new NonexistentEntityException("The invoiceDetail with id " + id + " no longer exists.");
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
            InvoiceDetail invoiceDetail;
            try {
                invoiceDetail = em.getReference(InvoiceDetail.class, id);
                invoiceDetail.getDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoiceDetail with id " + id + " no longer exists.", enfe);
            }
            MasterInvoice invoiceId = invoiceDetail.getInvoiceId();
            if (invoiceId != null) {
                invoiceId.getInvoiceDetailList().remove(invoiceDetail);
                invoiceId = em.merge(invoiceId);
            }
            Book bookId = invoiceDetail.getBookId();
            if (bookId != null) {
                bookId.getInvoiceDetailList().remove(invoiceDetail);
                bookId = em.merge(bookId);
            }
            em.remove(invoiceDetail);
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

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return findInvoiceDetailEntities(true, -1, -1);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return findInvoiceDetailEntities(false, maxResults, firstResult);
    }

    private List<InvoiceDetail> findInvoiceDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InvoiceDetail.class));
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

    public InvoiceDetail findInvoiceDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InvoiceDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InvoiceDetail> rt = cq.from(InvoiceDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
