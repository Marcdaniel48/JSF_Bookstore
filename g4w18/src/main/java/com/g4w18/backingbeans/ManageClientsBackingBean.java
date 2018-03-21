/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class ManageClientsBackingBean implements Serializable
{
    @Inject
    private ClientJpaController clientJpaController;
    
    private List<Client> clients;
    
    public ClientJpaController getClientJpaController()
    {
        return clientJpaController;
    }
    
    public List<Client> getClients()
    {
        if(clients == null)
            clients = clientJpaController.findClientEntities();
        return clients;
    }
    
    public void onEditClient(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        DataTable dataTable = (DataTable) (event.getSource());
        Client updatedClient = (Client)(dataTable.getRowData());
        clientJpaController.edit(updatedClient);
    }
}
