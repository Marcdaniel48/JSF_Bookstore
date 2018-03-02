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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
@Named("orderBacking")
@RequestScoped
public class PlaceOrderBackingBean implements Serializable 
{
    @Inject
    private ClientJpaController clientJpaController;
    
    public Client getCurrentClient()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String currentUsername = (String) (session.getAttribute("username"));
        
        return clientJpaController.findClientByUsername(currentUsername);
    }
    
    public String last4Characters(String creditCardNumber)
    {
        return creditCardNumber.substring(creditCardNumber.length()-4);
    }    
    
}
