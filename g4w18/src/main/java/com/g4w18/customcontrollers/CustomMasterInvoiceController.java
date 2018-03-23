package com.g4w18.customcontrollers;

import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sebastian Ramirez
 */
public class CustomMasterInvoiceController implements Serializable {

    @Inject
    private MasterInvoiceJpaController masterInvoiceController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(MasterInvoice masterInvoice) throws Exception {
        masterInvoiceController.create(masterInvoice);
    }

    public void edit(MasterInvoice masterInvoice) throws NonexistentEntityException, RollbackFailureException, Exception {
        masterInvoiceController.edit(masterInvoice);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        masterInvoiceController.destroy(id);
    }

    public List<MasterInvoice> findMasterInvoiceEntities() {
        return masterInvoiceController.findMasterInvoiceEntities();
    }

    public List<MasterInvoice> findMasterInvoiceEntities(int maxResults, int firstResult) {
        return masterInvoiceController.findMasterInvoiceEntities(maxResults, firstResult);
    }

    public MasterInvoice findMasterInvoice(Integer id) {
        return masterInvoiceController.findMasterInvoice(id);
    }

    public int getMasterInvoiceCount() {
        return masterInvoiceController.getMasterInvoiceCount();
    }

}
