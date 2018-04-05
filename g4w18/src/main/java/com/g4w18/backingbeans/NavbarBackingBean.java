package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.LoginController;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * @author Jephthia
 */
@Named
@SessionScoped
public class NavbarBackingBean implements Serializable
{
    @Inject
    private CustomClientController clientJpaController;
    
    @Inject
    private LoginController loginController;
    
    private boolean isUserLoggedIn = false;
    private String username = null;
    
    private static Logger logger = Logger.getLogger(NavbarBackingBean.class.getName());
    
    public boolean isUserLoggedIn()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        if(session == null)
            return false;
        
        Object loggedInAttr = session.getAttribute("loggedIn");
        
        if(loggedInAttr == null)
            return false;
        
        isUserLoggedIn = (boolean)loggedInAttr;
        
        logger.log(Level.INFO, LocalDateTime.now() + " >>> loggedIn: {0}", isUserLoggedIn);
        
        return isUserLoggedIn;
    }
    
    public String getUsername()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        username = (String)session.getAttribute("username");
//        username = loginController.getUsername();

        logger.log(Level.INFO, LocalDateTime.now() + " >>> username: {0}", username);
        
        return username;
    }
    
    public boolean isManager()
    {
        logger.log(Level.INFO, LocalDateTime.now() + " isManager() {0}", getUsername());
        
        Client client = clientJpaController.findClientByUsername(getUsername());
        
        return client.getIsManager();
    }
    
    public String logout()
    {
        loginController.logout();
        logger.log(Level.INFO, LocalDateTime.now() + " >>> LOGOUT TEST");
        
        return "index";
    }
}