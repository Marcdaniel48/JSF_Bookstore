/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Rss;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1331680
 */
public class RssJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Rss rss) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(rss);
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

    public void edit(Rss rss) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            rss = em.merge(rss);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rss.getRssId();
                if (findRss(id) == null) {
                    throw new NonexistentEntityException("The rss with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Rss rss;
            try {
                rss = em.getReference(Rss.class, id);
                rss.getRssId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rss with id " + id + " no longer exists.", enfe);
            }
            em.remove(rss);
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

    public List<Rss> findRssEntities() {
        return findRssEntities(true, -1, -1);
    }

    public List<Rss> findRssEntities(int maxResults, int firstResult) {
        return findRssEntities(false, maxResults, firstResult);
    }

    private List<Rss> findRssEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Rss.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Rss findRss(Integer id) {
        return em.find(Rss.class, id);
    }

    public int getRssCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Rss> rt = cq.from(Rss.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
