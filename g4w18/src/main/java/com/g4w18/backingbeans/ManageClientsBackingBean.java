/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomClientJpaController;
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
 * Backing bean responsible for interacting with the Client Management page by allowing it to display Client records, as well as edit them.
 * 
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class ManageClientsBackingBean implements Serializable
{
    // Used to retrieve and edit Client records from the Client table of the bookstore database.
    @Inject
    private CustomClientJpaController clientJpaController;
    
    // Used to retrieve the total gross value of the purchases of each client.
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceJpaController;
    
    // Used to contain all Client records in a List.
    private List<Client> clients;
    
    /**
     * Getter method. Returns a Client JPA controller.
     * @return clientJpaController
     */
    public CustomClientJpaController getClientJpaController()
    {
        return clientJpaController;
    }
    
    /**
     * Getter method. Returns a Master Invoice JPA controller.
     * @return 
     */
    public CustomMasterInvoiceJpaController getMasterInvoiceJpaController()
    {
        return masterInvoiceJpaController;
    }
    
    /**
     * Getter method. Returns a list of all Client records of bookstore database.
     * @return 
     */
    public List<Client> getClients()
    {
        if(clients == null)
            clients = clientJpaController.findClientEntities();
        return clients;
    }
    
    /**
     * Allows the user to edit a Client record.
     * 
     * @param event
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void onEditClient(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        DataTable dataTable = (DataTable) (event.getSource());
        Client updatedClient = (Client)(dataTable.getRowData());
        clientJpaController.edit(updatedClient);
    }
    
    /**
     * Retrieves the total purchase gross value of a client.
     * @param clientId
     * @return 
     */
    public double getTotalValueOfPurchases(int clientId)
    {
        List<MasterInvoice> clientMasterInvoices = masterInvoiceJpaController.findMasterInvoicesByClientId(clientId);
        double totalValueOfPurchases = 0;
        
        for(MasterInvoice masterInvoice : clientMasterInvoices)
        {
            totalValueOfPurchases += masterInvoice.getGrossValue().doubleValue();
        }
        
        return totalValueOfPurchases;
    }
}
