/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1331680
 */
public class MasterInvoiceJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(MasterInvoice masterInvoice) throws RollbackFailureException, Exception {
        if (masterInvoice.getInvoiceDetailList() == null) {
            masterInvoice.setInvoiceDetailList(new ArrayList<>());
        }
        try {
            utx.begin();
            Client clientId = masterInvoice.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getClientId());
                masterInvoice.setClientId(clientId);
            }
            List<InvoiceDetail> attachedInvoiceDetailList = new ArrayList<>();
            for (InvoiceDetail invoiceDetailListInvoiceDetailToAttach : masterInvoice.getInvoiceDetailList()) {
                invoiceDetailListInvoiceDetailToAttach = em.getReference(invoiceDetailListInvoiceDetailToAttach.getClass(), invoiceDetailListInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailList.add(invoiceDetailListInvoiceDetailToAttach);
            }
            masterInvoice.setInvoiceDetailList(attachedInvoiceDetailList);
            em.persist(masterInvoice);
            if (clientId != null) {
                clientId.getMasterInvoiceList().add(masterInvoice);
                clientId = em.merge(clientId);
            }
            for (InvoiceDetail invoiceDetailListInvoiceDetail : masterInvoice.getInvoiceDetailList()) {
                MasterInvoice oldInvoiceIdOfInvoiceDetailListInvoiceDetail = invoiceDetailListInvoiceDetail.getInvoiceId();
                invoiceDetailListInvoiceDetail.setInvoiceId(masterInvoice);
                invoiceDetailListInvoiceDetail = em.merge(invoiceDetailListInvoiceDetail);
                if (oldInvoiceIdOfInvoiceDetailListInvoiceDetail != null) {
                    oldInvoiceIdOfInvoiceDetailListInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListInvoiceDetail);
                    oldInvoiceIdOfInvoiceDetailListInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailListInvoiceDetail);
                }
            }
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
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
            List<InvoiceDetail> invoiceDetailListOld = persistentMasterInvoice.getInvoiceDetailList();
            List<InvoiceDetail> invoiceDetailListNew = masterInvoice.getInvoiceDetailList();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetail invoiceDetailListOldInvoiceDetail : invoiceDetailListOld) {
                if (!invoiceDetailListNew.contains(invoiceDetailListOldInvoiceDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetail " + invoiceDetailListOldInvoiceDetail + " since its invoiceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getClientId());
                masterInvoice.setClientId(clientIdNew);
            }
            List<InvoiceDetail> attachedInvoiceDetailListNew = new ArrayList<>();
            for (InvoiceDetail invoiceDetailListNewInvoiceDetailToAttach : invoiceDetailListNew) {
                invoiceDetailListNewInvoiceDetailToAttach = em.getReference(invoiceDetailListNewInvoiceDetailToAttach.getClass(), invoiceDetailListNewInvoiceDetailToAttach.getDetailId());
                attachedInvoiceDetailListNew.add(invoiceDetailListNewInvoiceDetailToAttach);
            }
            invoiceDetailListNew = attachedInvoiceDetailListNew;
            masterInvoice.setInvoiceDetailList(invoiceDetailListNew);
            masterInvoice = em.merge(masterInvoice);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getMasterInvoiceList().remove(masterInvoice);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getMasterInvoiceList().add(masterInvoice);
                clientIdNew = em.merge(clientIdNew);
            }
            for (InvoiceDetail invoiceDetailListNewInvoiceDetail : invoiceDetailListNew) {
                if (!invoiceDetailListOld.contains(invoiceDetailListNewInvoiceDetail)) {
                    MasterInvoice oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail = invoiceDetailListNewInvoiceDetail.getInvoiceId();
                    invoiceDetailListNewInvoiceDetail.setInvoiceId(masterInvoice);
                    invoiceDetailListNewInvoiceDetail = em.merge(invoiceDetailListNewInvoiceDetail);
                    if (oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail != null && !oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail.equals(masterInvoice)) {
                        oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListNewInvoiceDetail);
                        oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail);
                    }
                }
            }
            utx.commit();
        } catch (IllegalOrphanException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
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
            List<InvoiceDetail> invoiceDetailListOrphanCheck = masterInvoice.getInvoiceDetailList();
            for (InvoiceDetail invoiceDetailListOrphanCheckInvoiceDetail : invoiceDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MasterInvoice (" + masterInvoice + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailListOrphanCheckInvoiceDetail + " in its invoiceDetailList field has a non-nullable invoiceId field.");
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
        } catch (IllegalOrphanException | NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
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
