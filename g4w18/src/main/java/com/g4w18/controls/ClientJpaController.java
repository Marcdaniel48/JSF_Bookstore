/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controls;

import com.g4w18.controls.exceptions.IllegalOrphanException;
import com.g4w18.controls.exceptions.NonexistentEntityException;
import com.g4w18.controls.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.g4w18.entities.Review;
import java.util.ArrayList;
import java.util.List;
import com.g4w18.entities.MasterInvoice;
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
 * @author Marc-Daniel
 */
public class ClientJpaController implements Serializable {
    @Resource
    private UserTransaction utx = null;
    
    @PersistenceContext(unitName = "com.g4w18_g4w18_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public void create(Client client) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(client);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }
    
}
