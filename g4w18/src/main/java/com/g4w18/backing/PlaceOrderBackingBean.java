/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.TaxJpaController;
import com.g4w18.entities.Client;
import com.g4w18.entities.Tax;
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
    
    @Inject TaxJpaController taxJpaController;
    
    public Client getCurrentClient()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String currentUsername = (String) (session.getAttribute("username"));
        
        return clientJpaController.findClientByUsername(currentUsername);
    }
    
    public double getTaxRate()
    {
        Client user = getCurrentClient();
        
        Tax tax = taxJpaController.findTaxByProvince(user.getProvince());
        
        double taxRate = 0;
        
        taxRate += tax.getGstRate().doubleValue() /100;
        taxRate += tax.getPstRate().doubleValue() /100;
        taxRate += tax.getHstRate().doubleValue() /100;
        
        return taxRate;
    }
    
    public String last4Characters(String creditCardNumber)
    {
        return creditCardNumber.substring(creditCardNumber.length()-4);
    }  
    
    public double roundDouble(String price)
    {
        double roundedPrice = Double.valueOf(price);
        return Math.round(roundedPrice * 100.) / 100.;
    }
    
}
