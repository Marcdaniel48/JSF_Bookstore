/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.InvoiceDetail;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 1331680
 */
public class CustomInvoiceDetailController implements Serializable {

    @Inject
    private InvoiceDetailJpaController invoiceDetailController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(InvoiceDetail invoiceDetail) throws Exception {
        invoiceDetailController.create(invoiceDetail);
    }

    public void edit(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {
        invoiceDetailController.edit(invoiceDetail);
    }

    public void destroy(Integer id) throws RollbackFailureException, Exception {
        invoiceDetailController.destroy(id);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return invoiceDetailController.findInvoiceDetailEntities();
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int firstResult, int maxResult) {
        return invoiceDetailController.findInvoiceDetailEntities(firstResult, maxResult);
    }
    
    public InvoiceDetail findInvoiceDetail (Integer id){
        return invoiceDetailController.findInvoiceDetail(id);
    }
    
    public int getInvoiceDetailCount(){
        return invoiceDetailController.getInvoiceDetailCount();
    }
}
