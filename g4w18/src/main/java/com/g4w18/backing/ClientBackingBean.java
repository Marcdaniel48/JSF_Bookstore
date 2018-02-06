/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.entities.Client;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named("clientBacking")
@RequestScoped
public class ClientBackingBean implements Serializable
{
    @Inject
    private ClientJpaController clientJpaController;
    
    private Client client;
    
    


    public Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String createClient() throws Exception {
        client.setCountry("Canada");
        clientJpaController.create(client);
        return "login.xhtml";
    }
    
}
