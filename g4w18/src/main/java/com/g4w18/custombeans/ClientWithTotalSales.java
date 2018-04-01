/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Client;
import java.io.Serializable;

/**
 *
 * @author Marc-Daniel
 */
public class ClientWithTotalSales extends TotalSalesBean implements Serializable
{
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
