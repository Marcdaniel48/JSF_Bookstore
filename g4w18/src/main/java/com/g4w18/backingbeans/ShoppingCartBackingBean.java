package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomInvoiceDetailController;
import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.customcontrollers.LoginController;
import com.g4w18.customcontrollers.ShoppingCart;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * A backing bean that is used to interact with the pages that wish to access the user's ShoppingCart.
 * 
 * @author Marc-Daniel
 */
@Named
@SessionScoped
public class ShoppingCartBackingBean implements Serializable
{
    // Used to access the shopping cart
    @Inject
    private ShoppingCart cart;
    
    // Used to retrieve the login status of the user
    @Inject
    private LoginController login;
    
    // Used to allow accessing of the current user's client information (assuming that the user is logged in).
    @Inject
    private CustomClientController clientJpaController;
    
    // Used to allow accessing of the current user's master invoices.
    @Inject
    private CustomMasterInvoiceController masterInvoiceJpaController;
    
    // Used to allow accessing of the current user's invoice details.
    @Inject
    private CustomInvoiceDetailController invoiceDetailJpaController;
    
    /**
     * Getter method. Returns the shopping cart.
     * 
     * @return 
     */
    public ShoppingCart getShoppingCart()
    {
        return cart;
    }

    /**
     * Method that takes in a Book object, and returns its price.
     * If the book is on sale, then return that sale price, but if the book isn't on sale, return the book's list price.
     * 
     * @param book
     * @return 
     */
    public BigDecimal getPrice(Book book)
    {
        if(book.getSalePrice() != null)
            return book.getSalePrice();

        return book.getListPrice();
    }
    
    /**
     * Navigates the user to the checkout pages, but if the shopping cart is empty, then do nothing.
     * 
     * @return 
     */
    public String proceedToCheckout()
    {
        if(cart.getShoppingCartBooks().isEmpty())
        {
            return "";
        }
        
        return "proceedToCheckout";
    }
    
    /**
     * Takes in a Book object and checks to see if the user has already bought said book or if said book is in shopping cart, and returns an appropriate boolean.
     * Used in the book details page to determine if the "Add to cart" button should be displayed or not..
     * 
     * @param book
     * @return 
     */
    public boolean isBookBoughtOrInCartAlready(Book book)
    {
        if(login.getLoggedIn())
        {
            Client user = clientJpaController.findClientByUsername(login.getUsername());
            List<MasterInvoice> masterInvoicesOfUser = masterInvoiceJpaController.findMasterInvoicesByClientId(user.getClientId());
            
            for(MasterInvoice mi : masterInvoicesOfUser)
            {
                // Goes through every book purchase of the current user.
                for(InvoiceDetail invoice : invoiceDetailJpaController.findInvoicesByMasterInvoice(mi))
                {
                    // If the user has already bought 'book', then disable the "Add to cart" button.
                    if(Objects.equals(invoice.getBookId().getBookId(), book.getBookId()))
                        return true;
                }
            }
        }
        
        if(!cart.getShoppingCartBooks().isEmpty())
        {
            if(cart.getShoppingCartBooks().contains(book))
                return true;
        }
        
        return false;
    }
}
