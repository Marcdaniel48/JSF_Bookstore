/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.InvoiceDetail;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Marc-Daniel
 */
public class CustomInvoiceDetailJpaController implements Serializable
{
    @Inject
    private InvoiceDetailJpaController invoiceJpaController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {
        invoiceJpaController.create(invoiceDetail);
    }

    public void edit(InvoiceDetail invoiceDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        invoiceJpaController.edit(invoiceDetail);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        invoiceJpaController.destroy(id);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return invoiceJpaController.findInvoiceDetailEntities();
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return invoiceJpaController.findInvoiceDetailEntities(maxResults, firstResult);
    }

    public InvoiceDetail findInvoiceDetail(Integer id) {
        return invoiceJpaController.findInvoiceDetail(id);
    }

    public int getInvoiceDetailCount() {
        return invoiceJpaController.getInvoiceDetailCount();
    }
    
    public List<InvoiceDetail> findInvoicesByMasterInvoice(int invoiceId)
    {
        List<InvoiceDetail> invoices = em.createQuery("Select i from InvoiceDetail i where i.invoiceId = ?1").setParameter(1, invoiceId).getResultList();
        return invoices;
    }
}
