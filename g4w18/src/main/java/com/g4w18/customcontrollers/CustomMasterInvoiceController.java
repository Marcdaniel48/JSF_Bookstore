package com.g4w18.customcontrollers;

import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Rss;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Custom JPA controller used to access and manipulate the MasterInvoice records
 * of the database.
 *
 * @author Marc-Daniel
 */
public class CustomMasterInvoiceController implements Serializable {

    @Inject
    private MasterInvoiceJpaController masterInvoiceJpaController;

    @Inject
    private CustomClientController clientJpaController;

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

    /**
     * Returns a master invoice with the given client ID.
     *
     * @param clientId
     * @return
     */
    public List<MasterInvoice> findMasterInvoicesByClientId(int clientId) {
        TypedQuery<MasterInvoice> query = em.createNamedQuery("MasterInvoice.findByClientId", MasterInvoice.class);
        Client client = clientJpaController.findClient(clientId);
        query.setParameter("clientId", client);
        List<MasterInvoice> masterInvoices = query.getResultList();
        return masterInvoices;
    }

    /**
     * Get all master invoices for the specific client id.
     *
     * @param clientId
     * @return
     */
    public List<MasterInvoice> getMasterInvoiceByClientId(int clientId) {
        TypedQuery<MasterInvoice> query = em.createNamedQuery("MasterInvoice.findByClientId", MasterInvoice.class);
        Client client = clientJpaController.findClient(clientId);
        query.setParameter("clientId", client);
        List<MasterInvoice> masterInvoices = query.getResultList();

        return masterInvoices;
    }

    public MasterInvoice getMostRecentMasterInvoice(int clientId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MasterInvoice> cq = cb.createQuery(MasterInvoice.class);
        Root<MasterInvoice> masterInvoice = cq.from(MasterInvoice.class);
        cq.select(masterInvoice).orderBy(cb.desc(masterInvoice.get("saleDate")));
        TypedQuery<MasterInvoice> query = em.createQuery(cq);
        return query.setMaxResults(1).getSingleResult();
    }
}
