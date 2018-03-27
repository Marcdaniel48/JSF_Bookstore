/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
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
    private CustomClientController clientJpaController;
    
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceJpaController;
    
    private List<Client> clients;
    
    public CustomClientController getClientJpaController()
    {
        return clientJpaController;
    }
    
    public CustomMasterInvoiceJpaController getMasterInvoiceJpaController()
    {
        return masterInvoiceJpaController;
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
    
    public double getTotalValueOfPurchases(int clientId)
    {
        MasterInvoice clientMasterInvoice = masterInvoiceJpaController.findMasterInvoiceByClientId(clientId);
        
        if(clientMasterInvoice == null)
            return 0;
        else
            return clientMasterInvoice.getGrossValue().doubleValue();
    }
}
