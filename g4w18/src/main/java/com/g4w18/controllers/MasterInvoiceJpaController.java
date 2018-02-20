package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;


@Named
@RequestScoped
public class MasterInvoiceJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(MasterInvoice masterInvoice) throws RollbackFailureException, Exception {
        if (masterInvoice.getInvoiceDetailCollection() == null) {
            masterInvoice.setInvoiceDetailCollection(new ArrayList<InvoiceDetail>());
        }
        try {
            utx.begin();
            Client clientId = masterInvoice.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getClientId());
                masterInvoice.setClientId(clientId);
            }
            Collection<InvoiceDetail> attachedInvoiceDetailCollection = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailCollectionInvoiceDetailToAttach : masterInvoice.getInvoiceDetailCollection()) {
                invoiceDetailCollectionInvoiceDetailToAttach = em.getReference(invoiceDetailCollectionInvoiceDetailToAttach.getClass(), invoiceDetailCollectionInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailCollection.add(invoiceDetailCollectionInvoiceDetailToAttach);
            }
            masterInvoice.setInvoiceDetailCollection(attachedInvoiceDetailCollection);
            em.persist(masterInvoice);
            if (clientId != null) {
                clientId.getMasterInvoiceList().add(masterInvoice);
                clientId = em.merge(clientId);
            }
            for (InvoiceDetail invoiceDetailCollectionInvoiceDetail : masterInvoice.getInvoiceDetailCollection()) {
                MasterInvoice oldInvoiceIdOfInvoiceDetailCollectionInvoiceDetail = invoiceDetailCollectionInvoiceDetail.getInvoiceId();
                invoiceDetailCollectionInvoiceDetail.setInvoiceId(masterInvoice);
                invoiceDetailCollectionInvoiceDetail = em.merge(invoiceDetailCollectionInvoiceDetail);
                if (oldInvoiceIdOfInvoiceDetailCollectionInvoiceDetail != null) {
                    oldInvoiceIdOfInvoiceDetailCollectionInvoiceDetail.getInvoiceDetailCollection().remove(invoiceDetailCollectionInvoiceDetail);
                    oldInvoiceIdOfInvoiceDetailCollectionInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailCollectionInvoiceDetail);
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

    public void edit(MasterInvoice masterInvoice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            MasterInvoice persistentMasterInvoice = em.find(MasterInvoice.class, masterInvoice.getInvoiceId());
            Client clientIdOld = persistentMasterInvoice.getClientId();
            Client clientIdNew = masterInvoice.getClientId();
            Collection<InvoiceDetail> invoiceDetailCollectionOld = persistentMasterInvoice.getInvoiceDetailCollection();
            Collection<InvoiceDetail> invoiceDetailCollectionNew = masterInvoice.getInvoiceDetailCollection();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetail invoiceDetailCollectionOldInvoiceDetail : invoiceDetailCollectionOld) {
                if (!invoiceDetailCollectionNew.contains(invoiceDetailCollectionOldInvoiceDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetail " + invoiceDetailCollectionOldInvoiceDetail + " since its invoiceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getClientId());
                masterInvoice.setClientId(clientIdNew);
            }
            Collection<InvoiceDetail> attachedInvoiceDetailCollectionNew = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailCollectionNewInvoiceDetailToAttach : invoiceDetailCollectionNew) {
                invoiceDetailCollectionNewInvoiceDetailToAttach = em.getReference(invoiceDetailCollectionNewInvoiceDetailToAttach.getClass(), invoiceDetailCollectionNewInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailCollectionNew.add(invoiceDetailCollectionNewInvoiceDetailToAttach);
            }
            invoiceDetailCollectionNew = attachedInvoiceDetailCollectionNew;
            masterInvoice.setInvoiceDetailCollection(invoiceDetailCollectionNew);
            masterInvoice = em.merge(masterInvoice);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getMasterInvoiceList().remove(masterInvoice);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getMasterInvoiceList().add(masterInvoice);
                clientIdNew = em.merge(clientIdNew);
            }
            for (InvoiceDetail invoiceDetailCollectionNewInvoiceDetail : invoiceDetailCollectionNew) {
                if (!invoiceDetailCollectionOld.contains(invoiceDetailCollectionNewInvoiceDetail)) {
                    MasterInvoice oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail = invoiceDetailCollectionNewInvoiceDetail.getInvoiceId();
                    invoiceDetailCollectionNewInvoiceDetail.setInvoiceId(masterInvoice);
                    invoiceDetailCollectionNewInvoiceDetail = em.merge(invoiceDetailCollectionNewInvoiceDetail);
                    if (oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail != null && !oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail.equals(masterInvoice)) {
                        oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail.getInvoiceDetailCollection().remove(invoiceDetailCollectionNewInvoiceDetail);
                        oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailCollectionNewInvoiceDetail);
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
                Integer id = masterInvoice.getInvoiceId();
                if (findMasterInvoice(id) == null) {
                    throw new NonexistentEntityException("The masterInvoice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            MasterInvoice masterInvoice;
            try {
                masterInvoice = em.getReference(MasterInvoice.class, id);
                masterInvoice.getInvoiceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The masterInvoice with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InvoiceDetail> invoiceDetailCollectionOrphanCheck = masterInvoice.getInvoiceDetailCollection();
            for (InvoiceDetail invoiceDetailCollectionOrphanCheckInvoiceDetail : invoiceDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MasterInvoice (" + masterInvoice + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailCollectionOrphanCheckInvoiceDetail + " in its invoiceDetailCollection field has a non-nullable invoiceId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientId = masterInvoice.getClientId();
            if (clientId != null) {
                clientId.getMasterInvoiceList().remove(masterInvoice);
                clientId = em.merge(clientId);
            }
            em.remove(masterInvoice);
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

    public List<MasterInvoice> findMasterInvoiceEntities() {
        return findMasterInvoiceEntities(true, -1, -1);
    }

    public List<MasterInvoice> findMasterInvoiceEntities(int maxResults, int firstResult) {
        return findMasterInvoiceEntities(false, maxResults, firstResult);
    }

    private List<MasterInvoice> findMasterInvoiceEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(MasterInvoice.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public MasterInvoice findMasterInvoice(Integer id) {
        return em.find(MasterInvoice.class, id);
    }

    public int getMasterInvoiceCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<MasterInvoice> rt = cq.from(MasterInvoice.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
