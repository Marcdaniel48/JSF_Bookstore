/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomClientJpaController;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named("clientBacking")
@ViewScoped
public class ClientBackingBean implements Serializable
{
    @Inject
    private CustomClientJpaController clientJpaController;
    
    private Client client;
    
    


    public Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String createClient() throws Exception 
    {
        client.setUsername(client.getUsername().toLowerCase());
        client.setEmail(client.getEmail().toLowerCase());
        clientJpaController.create(client);
        return "login.xhtml";
    }
    
    public void validateExistingUsername(FacesContext fc, UIComponent c, Object value) 
    {   
        if (clientJpaController.findClientByUsername(((String) value).toLowerCase()) == null) 
        {
            String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidExistingEmail");
            throw new ValidatorException(new FacesMessage(validationMessage));
        }   
    }
    
    public void validateExistingEmail(FacesContext fc, UIComponent c, Object value) 
    {
        if (clientJpaController.findClientByEmail(((String) value).toLowerCase()) == null) 
        {
            String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidExistingEmail");
            throw new ValidatorException(new FacesMessage(validationMessage));
        }
    }
    
    
    private static Collection<SelectItem> titleOptions;
    public Collection<SelectItem> getTitleOptions() {
        return titleOptions;
    }
    
    private static Collection<SelectItem> provinceOptions;
    public Collection<SelectItem> getProvinceOptions() {
        return provinceOptions;
    }
    
    private static Collection<SelectItem> isManagerOptions;
    public Collection<SelectItem> getIsManagerOptions() {
        return isManagerOptions;
    }
    
    static 
    {
        titleOptions = new ArrayList<SelectItem>();
        titleOptions.add(new SelectItem(null, "Select a title", "", false, false, true));
        titleOptions.add(new SelectItem("Mr."));
        titleOptions.add(new SelectItem("Ms."));
        titleOptions.add(new SelectItem("Dr."));
        titleOptions.add(new SelectItem("Sir"));
        titleOptions.add(new SelectItem("Lord"));
        titleOptions.add(new SelectItem("Lady"));
        
        provinceOptions = new ArrayList<SelectItem>();
        provinceOptions.add(new SelectItem(null, "Select a province", "", false, false, true));
        provinceOptions.add(new SelectItem("AB"));
        provinceOptions.add(new SelectItem("BC"));
        provinceOptions.add(new SelectItem("MB"));
        provinceOptions.add(new SelectItem("NB"));
        provinceOptions.add(new SelectItem("NL"));
        provinceOptions.add(new SelectItem("NS"));
        provinceOptions.add(new SelectItem("NT"));
        provinceOptions.add(new SelectItem("NU"));
        provinceOptions.add(new SelectItem("ON"));
        provinceOptions.add(new SelectItem("PE"));
        provinceOptions.add(new SelectItem("QC"));
        provinceOptions.add(new SelectItem("SK"));
        provinceOptions.add(new SelectItem("YT"));
        
        isManagerOptions = new ArrayList<SelectItem>();
        isManagerOptions.add(new SelectItem(Boolean.TRUE));
        isManagerOptions.add(new SelectItem(Boolean.FALSE));
    }
}
