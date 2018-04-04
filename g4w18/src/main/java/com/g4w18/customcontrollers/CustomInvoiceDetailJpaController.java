package com.g4w18.customcontrollers;

import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Custom InvoiceDetail JPA controller used to access and manipulate the InvoiceDetail records of the database.
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
    
    /**
     * Returns the invoice details for the given master invoice ID.
     * @param masterInvoice
     * @return 
     */
    public List<InvoiceDetail> findInvoicesByMasterInvoice(MasterInvoice masterInvoice)
    {
        List<InvoiceDetail> invoices = em.createQuery("Select i from InvoiceDetail i where i.invoiceId = ?1").setParameter(1, masterInvoice).getResultList();
        return invoices;
    }
}
