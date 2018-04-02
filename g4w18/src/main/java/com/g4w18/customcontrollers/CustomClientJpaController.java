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
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Marc-Daniel
 */
public class CustomClientJpaController implements Serializable
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
    
        public Client findClientByUsername(String username)
    {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByUsername", Client.class);
        query.setParameter("username", username);
        List<Client> clients = query.getResultList();
        if (!clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
    }

    public Client findClientByEmail(String email)
    {
        TypedQuery<Client> query = em.createNamedQuery("Client.findByEmail", Client.class);
        query.setParameter("email", email);
        List<Client> clients = query.getResultList();
        if (!clients.isEmpty()) {
            return clients.get(0);
        }
        return null;
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
