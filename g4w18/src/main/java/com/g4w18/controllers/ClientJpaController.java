/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Review;
import java.util.ArrayList;
import java.util.Collection;
import com.g4w18.entities.MasterInvoice;
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
 * @author 1331680
 */
@Named
@RequestScoped
public class ClientJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Client client) throws RollbackFailureException, Exception {
        if (client.getReviewCollection() == null) {
            client.setReviewCollection(new ArrayList<Review>());
        }
        if (client.getMasterInvoiceCollection() == null) {
            client.setMasterInvoiceCollection(new ArrayList<MasterInvoice>());
        }
        try {
            utx.begin();
            Collection<Review> attachedReviewCollection = new ArrayList<Review>();
            for (Review reviewCollectionReviewToAttach : client.getReviewCollection()) {
                reviewCollectionReviewToAttach = em.getReference(reviewCollectionReviewToAttach.getClass(), reviewCollectionReviewToAttach.getReviewId());
                attachedReviewCollection.add(reviewCollectionReviewToAttach);
            }
            client.setReviewCollection(attachedReviewCollection);
            Collection<MasterInvoice> attachedMasterInvoiceCollection = new ArrayList<MasterInvoice>();
            for (MasterInvoice masterInvoiceCollectionMasterInvoiceToAttach : client.getMasterInvoiceCollection()) {
                masterInvoiceCollectionMasterInvoiceToAttach = em.getReference(masterInvoiceCollectionMasterInvoiceToAttach.getClass(), masterInvoiceCollectionMasterInvoiceToAttach.getInvoiceId());
                attachedMasterInvoiceCollection.add(masterInvoiceCollectionMasterInvoiceToAttach);
            }
            client.setMasterInvoiceCollection(attachedMasterInvoiceCollection);
            em.persist(client);
            for (Review reviewCollectionReview : client.getReviewCollection()) {
                Client oldClientIdOfReviewCollectionReview = reviewCollectionReview.getClientId();
                reviewCollectionReview.setClientId(client);
                reviewCollectionReview = em.merge(reviewCollectionReview);
                if (oldClientIdOfReviewCollectionReview != null) {
                    oldClientIdOfReviewCollectionReview.getReviewCollection().remove(reviewCollectionReview);
                    oldClientIdOfReviewCollectionReview = em.merge(oldClientIdOfReviewCollectionReview);
                }
            }
            for (MasterInvoice masterInvoiceCollectionMasterInvoice : client.getMasterInvoiceCollection()) {
                Client oldClientIdOfMasterInvoiceCollectionMasterInvoice = masterInvoiceCollectionMasterInvoice.getClientId();
                masterInvoiceCollectionMasterInvoice.setClientId(client);
                masterInvoiceCollectionMasterInvoice = em.merge(masterInvoiceCollectionMasterInvoice);
                if (oldClientIdOfMasterInvoiceCollectionMasterInvoice != null) {
                    oldClientIdOfMasterInvoiceCollectionMasterInvoice.getMasterInvoiceCollection().remove(masterInvoiceCollectionMasterInvoice);
                    oldClientIdOfMasterInvoiceCollectionMasterInvoice = em.merge(oldClientIdOfMasterInvoiceCollectionMasterInvoice);
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

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Client persistentClient = em.find(Client.class, client.getClientId());
            Collection<Review> reviewCollectionOld = persistentClient.getReviewCollection();
            Collection<Review> reviewCollectionNew = client.getReviewCollection();
            Collection<MasterInvoice> masterInvoiceCollectionOld = persistentClient.getMasterInvoiceCollection();
            Collection<MasterInvoice> masterInvoiceCollectionNew = client.getMasterInvoiceCollection();
            List<String> illegalOrphanMessages = null;
            for (Review reviewCollectionOldReview : reviewCollectionOld) {
                if (!reviewCollectionNew.contains(reviewCollectionOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewCollectionOldReview + " since its clientId field is not nullable.");
                }
            }
            for (MasterInvoice masterInvoiceCollectionOldMasterInvoice : masterInvoiceCollectionOld) {
                if (!masterInvoiceCollectionNew.contains(masterInvoiceCollectionOldMasterInvoice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MasterInvoice " + masterInvoiceCollectionOldMasterInvoice + " since its clientId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Review> attachedReviewCollectionNew = new ArrayList<Review>();
            for (Review reviewCollectionNewReviewToAttach : reviewCollectionNew) {
                reviewCollectionNewReviewToAttach = em.getReference(reviewCollectionNewReviewToAttach.getClass(), reviewCollectionNewReviewToAttach.getReviewId());
                attachedReviewCollectionNew.add(reviewCollectionNewReviewToAttach);
            }
            reviewCollectionNew = attachedReviewCollectionNew;
            client.setReviewCollection(reviewCollectionNew);
            Collection<MasterInvoice> attachedMasterInvoiceCollectionNew = new ArrayList<MasterInvoice>();
            for (MasterInvoice masterInvoiceCollectionNewMasterInvoiceToAttach : masterInvoiceCollectionNew) {
                masterInvoiceCollectionNewMasterInvoiceToAttach = em.getReference(masterInvoiceCollectionNewMasterInvoiceToAttach.getClass(), masterInvoiceCollectionNewMasterInvoiceToAttach.getInvoiceId());
                attachedMasterInvoiceCollectionNew.add(masterInvoiceCollectionNewMasterInvoiceToAttach);
            }
            masterInvoiceCollectionNew = attachedMasterInvoiceCollectionNew;
            client.setMasterInvoiceCollection(masterInvoiceCollectionNew);
            client = em.merge(client);
            for (Review reviewCollectionNewReview : reviewCollectionNew) {
                if (!reviewCollectionOld.contains(reviewCollectionNewReview)) {
                    Client oldClientIdOfReviewCollectionNewReview = reviewCollectionNewReview.getClientId();
                    reviewCollectionNewReview.setClientId(client);
                    reviewCollectionNewReview = em.merge(reviewCollectionNewReview);
                    if (oldClientIdOfReviewCollectionNewReview != null && !oldClientIdOfReviewCollectionNewReview.equals(client)) {
                        oldClientIdOfReviewCollectionNewReview.getReviewCollection().remove(reviewCollectionNewReview);
                        oldClientIdOfReviewCollectionNewReview = em.merge(oldClientIdOfReviewCollectionNewReview);
                    }
                }
            }
            for (MasterInvoice masterInvoiceCollectionNewMasterInvoice : masterInvoiceCollectionNew) {
                if (!masterInvoiceCollectionOld.contains(masterInvoiceCollectionNewMasterInvoice)) {
                    Client oldClientIdOfMasterInvoiceCollectionNewMasterInvoice = masterInvoiceCollectionNewMasterInvoice.getClientId();
                    masterInvoiceCollectionNewMasterInvoice.setClientId(client);
                    masterInvoiceCollectionNewMasterInvoice = em.merge(masterInvoiceCollectionNewMasterInvoice);
                    if (oldClientIdOfMasterInvoiceCollectionNewMasterInvoice != null && !oldClientIdOfMasterInvoiceCollectionNewMasterInvoice.equals(client)) {
                        oldClientIdOfMasterInvoiceCollectionNewMasterInvoice.getMasterInvoiceCollection().remove(masterInvoiceCollectionNewMasterInvoice);
                        oldClientIdOfMasterInvoiceCollectionNewMasterInvoice = em.merge(oldClientIdOfMasterInvoiceCollectionNewMasterInvoice);
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
                Integer id = client.getClientId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getClientId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Review> reviewCollectionOrphanCheck = client.getReviewCollection();
            for (Review reviewCollectionOrphanCheckReview : reviewCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Review " + reviewCollectionOrphanCheckReview + " in its reviewCollection field has a non-nullable clientId field.");
            }
            Collection<MasterInvoice> masterInvoiceCollectionOrphanCheck = client.getMasterInvoiceCollection();
            for (MasterInvoice masterInvoiceCollectionOrphanCheckMasterInvoice : masterInvoiceCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the MasterInvoice " + masterInvoiceCollectionOrphanCheckMasterInvoice + " in its masterInvoiceCollection field has a non-nullable clientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Client.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Client findClient(Integer id) {
        return em.find(Client.class, id);
    }

    public int getClientCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Client> rt = cq.from(Client.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
