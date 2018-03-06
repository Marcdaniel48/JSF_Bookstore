/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Marc-Daniel
 */
public class CustomClientController implements Serializable
{
    @Inject
    private ClientJpaController clientController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(Client client) throws RollbackFailureException, Exception
    {
        clientController.create(client);
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception
    {
        clientController.edit(client);
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception
    {
        clientController.destroy(id);
    }

    public List<Client> findClientEntities()
    {
        return clientController.findClientEntities();
    }

    public List<Client> findClientEntities(int maxResults, int firstResult)
    {
        return clientController.findClientEntities(maxResults, firstResult);
    }

    public Client findClient(Integer id)
    {
        return clientController.findClient(id);
    }

    public int getClientCount()
    {
        return clientController.getClientCount();
    }
    
    public List<Client> findClientByUsername(String username)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);
        cq.select(clientRoot).where(cb.equal(clientRoot.get("username"), username));
        TypedQuery<Client> query = em.createQuery(cq);
        List<Client> existingClients = query.getResultList();

        return existingClients;
    }

    public List<Client> findClientByEmail(String email)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);
        cq.select(clientRoot).where(cb.equal(clientRoot.get("email"), email));
        TypedQuery<Client> query = em.createQuery(cq);
        List<Client> existingClients = query.getResultList();

        return existingClients;
    }

    public Client findClientByCredentials(String username, String password)
    {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByCredentials", Client.class);
        query.setParameter(1, username);
        query.setParameter(2, password);
        List<Client> clients = query.getResultList();
        if (!clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
    }
}
