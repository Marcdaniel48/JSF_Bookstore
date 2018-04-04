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
 * Controller class responsible for handling the login and logout feature of the bookstore website.
 * 
 * @author Marc-Daniel
 */
@Named
@SessionScoped
public class LoginController implements Serializable{
    // Used to retrieve the Client record with the given username and password combination.
    @Inject
    private CustomClientController clientJpaController;
    
    // Once the user has logged in, used to check if the shopping cart contains any books that the user has already purchased.
    @Inject
    private ShoppingCart shoppingCart;
    
    // Used to find all purchases of the user. Works with the injected shopping cart to remove any books that the user has already purchased.
    @Inject
    private CustomMasterInvoiceController masterInvoiceJpaController;
    
    /* 
        Used to find all the purchased books of the user. 
        Works with the injected shopping cart and master invoice JPA controller to remove any books that the user has already purchased.
    */
    @Inject
    private CustomInvoiceDetailController invoiceDetailJpaController;

    private String username;
    private String password;
    private boolean loggedIn;
    
    // Logger
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method. Returns the username of the current user.
     * @return username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Setter method. Sets the username of the current user.
     * @param username 
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Setter method. Sets the password of the current user.
     * @param password 
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    /**
     * Getter method. Returns the password of the current user.
     * @return password
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * Getter method. Returns the login status of the current user as a boolean.
     * @return loggedIn
     */
    public boolean getLoggedIn()
    {
        return loggedIn;
    }
    
    /**
     * Setter method. Sets the login status of the current user as a boolean.
     * @param loggedIn 
     */
    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    /**
     * Uses the current user's entered username and password combination in order to login.
     * Login is done by setting a few HTTP session attributes.
     * 
     * @return 
     */
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
        
        return "index";
    }

    /**
     * Logs the user out.
     * Logout is done by setting the HTTP session attributes that have been used to login the user to null.
     */
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
