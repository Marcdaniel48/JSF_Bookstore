/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.backingbeans.BookDetailsBackingBean;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
@Named
@SessionScoped
public class LoginController implements Serializable
{
    @Inject
    private CustomClientJpaController clientJpaController;
    
    @Inject
    private ShoppingCart shoppingCart;
    
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceJpaController;
    
    @Inject
    private CustomInvoiceDetailJpaController invoiceDetailJpaController;

    private String username;
    private String password;
    private boolean loggedIn;
    
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public boolean getLoggedIn()
    {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public String login()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        Client user = clientJpaController.findClientByCredentials(username, password);

        if (user != null)
        {
            loggedIn = true;
        }
        else
        {
            loggedIn = false;
            username = null;
            return "";
        }

        session.setAttribute("loggedIn", loggedIn);
        session.setAttribute("username", username);
        log.log(Level.INFO, "Username: {0}", session.getAttribute("username"));
        
        // If the shopping cart contains items before the user has logged in, check if any books in the shopping cart has already been bought by the user.
        // If so, remove the book from the shopping cart.
        if(!shoppingCart.getShoppingCartBooks().isEmpty())
        {
            List<MasterInvoice> masterInvoicesOfUser = masterInvoiceJpaController.findMasterInvoicesByClientId(user.getClientId());

            for(MasterInvoice mi : masterInvoicesOfUser)
            {
                for(InvoiceDetail invoice : invoiceDetailJpaController.findInvoicesByMasterInvoice(mi))
                {
                    if(shoppingCart.getShoppingCartBooks().contains(invoice.getBookId()))
                        shoppingCart.getShoppingCartBooks().remove(invoice.getBookId());
                }
            }
        }
        
        return "bookList.xhtml";
    }

    public void logout()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("username", null);
        session.setAttribute("loggedIn", null);
        
        username = null;
        password = null;
        loggedIn = false;
    }
}
