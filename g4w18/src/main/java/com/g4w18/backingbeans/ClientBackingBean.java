package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * A backing bean that is used to interact with the pages and forms that wish to access and/or display information stored in the Client records 
 * from the bookstore database. 
 * Registration, Login, as well as Client Management use this backing bean.
 * 
 * @author Marc-Daniel
 */
@Named("clientBacking")
@ViewScoped
public class ClientBackingBean implements Serializable
{
    // This backing bean will mainly use the following Client JPA controller in order to register a new client.
    @Inject
    private CustomClientController clientJpaController;
    
    // Logger
    private Logger log = Logger.getLogger(getClass().getName());
    
    // A Client object that will be used by the bookstore user to contain the Client values that the user wants to either register or login with.
    private Client client;
    
    /* 
        When creating or editing a Client, instead of entering the title, province, manager status of the client in regular input fields,
        they're values will be selected through dropdown select lists.
        The following fields will be used to contain the possible options of those select fields.
    */
    private static Collection<SelectItem> titleOptions;
    private static Collection<SelectItem> provinceOptions;
    private static Collection<SelectItem> isManagerOptions;

    /**
     * Getter method. Returns client.
     * @return client
     */
    public Client getClient() {
        if (client == null) 
        {
            log.log(Level.INFO, "Client client is null. Instantiating client as a new Client.");
            client = new Client();
        }
        
        return client;
    }

    /**
     * This method uses the values stored in this backing bean's Client object in order to create a new Client record into
     * the bookstore's database, which will in turn register a new client account.
     * 
     * @return
     * @throws Exception 
     */
    public String createClient() throws Exception 
    {
        client.setUsername(client.getUsername().toLowerCase());
        client.setEmail(client.getEmail().toLowerCase());
        clientJpaController.create(client);
        
        log.log(Level.INFO, "Client JPA Controller has sucessfully created a new client.");
        return "login";
    }
    
    /**
     * Checks the database to see if a Client record with the username that the current user wants to register with already exists.
     * If so, then throw a ValidatorException telling the user that the username is already in use.
     * @param fc
     * @param c
     * @param value 
     */
    public void validateExistingUsername(FacesContext fc, UIComponent c, Object value) 
    {   
        if (clientJpaController.findClientByUsername(((String) value).toLowerCase()) != null) 
        {
            String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidExistingUsername");
            throw new ValidatorException(new FacesMessage(validationMessage));
        }   
    }
    
    /**
     * Checks the database to see if a Client record with the email address that the current user wants to register with already exists.
     * If so, then throw a ValidatorException telling the user that the email address is already in use.
     * @param fc
     * @param c
     * @param value 
     */
    public void validateExistingEmail(FacesContext fc, UIComponent c, Object value) 
    {
        if (clientJpaController.findClientByEmail(((String) value).toLowerCase()) != null) 
        {
            String validationMessage = ResourceBundle.getBundle("com.g4w18.bundles.messages").getString("invalidExistingEmail");
            throw new ValidatorException(new FacesMessage(validationMessage));
        }
    }
    
    /**
     * Getter method. Returns title drop-down list options.
     * @return titleOptions
     */
    public Collection<SelectItem> getTitleOptions() {
        return titleOptions;
    }
    
    /**
     * Getter method. Returns province drop-down list options.
     * @return provinceOptions
     */
    public Collection<SelectItem> getProvinceOptions() {
        return provinceOptions;
    }
    
    /**
     * Getter method. Returns 'is manager' drop-down list options.
     * @return isManagerOptions
     */
    public Collection<SelectItem> getIsManagerOptions() {
        return isManagerOptions;
    }
    
    // Contains the possible values that a client's title, province, and manager status may be set to.
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
