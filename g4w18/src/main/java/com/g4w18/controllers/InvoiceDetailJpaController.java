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
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Book;
import com.g4w18.entities.InvoiceDetail;
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
 * @author 1430047
 */
@Named
@RequestScoped
public class InvoiceDetailJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {

        try {
            utx.begin();
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
        }
    }

    public void edit(InvoiceDetail invoiceDetail) throws NonexistentEntityException, RollbackFailureException, Exception {

        try {
            utx.begin();
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
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {

        try {
            utx.begin();
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
        }
    }

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return findInvoiceDetailEntities(true, -1, -1);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return findInvoiceDetailEntities(false, maxResults, firstResult);
    }

    private List<InvoiceDetail> findInvoiceDetailEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(InvoiceDetail.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public InvoiceDetail findInvoiceDetail(Integer id) {

        return em.find(InvoiceDetail.class, id);

    }

    public int getInvoiceDetailCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<InvoiceDetail> rt = cq.from(InvoiceDetail.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }
    
}
