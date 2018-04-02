/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Marc-Daniel
 */
public class CustomMasterInvoiceJpaController implements Serializable
{
    @Inject
    private MasterInvoiceJpaController masterInvoiceJpaController;
    
    @Inject
    private CustomClientJpaController clientJpaController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(MasterInvoice masterInvoice) throws RollbackFailureException, Exception {
        masterInvoiceJpaController.create(masterInvoice);
    }

    public void edit(MasterInvoice masterInvoice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        masterInvoiceJpaController.edit(masterInvoice);
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        masterInvoiceJpaController.destroy(id);
    }

    public List<MasterInvoice> findMasterInvoiceEntities() {
        return masterInvoiceJpaController.findMasterInvoiceEntities();
    }

    public List<MasterInvoice> findMasterInvoiceEntities(int maxResults, int firstResult) {
        return masterInvoiceJpaController.findMasterInvoiceEntities(maxResults, firstResult);
    }

    public MasterInvoice findMasterInvoice(Integer id) {
        return masterInvoiceJpaController.findMasterInvoice(id);
    }

    public int getMasterInvoiceCount() {
        return masterInvoiceJpaController.getMasterInvoiceCount();
    }
    
    public List<MasterInvoice> findMasterInvoicesByClientId(int clientId)
    {
        TypedQuery<MasterInvoice> query = em.createNamedQuery("MasterInvoice.findByClientId", MasterInvoice.class);
        Client client = clientJpaController.findClient(clientId);
        query.setParameter("clientId", client);
        List<MasterInvoice> masterInvoices = query.getResultList();
        return masterInvoices;
    }
}
