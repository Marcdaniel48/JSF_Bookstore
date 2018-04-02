/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomClientJpaController;
import com.g4w18.customcontrollers.CustomInvoiceDetailJpaController;
import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
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
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named
@SessionScoped
public class ShoppingCartBackingBean implements Serializable
{
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    @Inject
    private ShoppingCart cart;
    
    @Inject
    private LoginController login;
    
    @Inject
    private CustomClientJpaController clientJpaController;
    
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceJpaController;
    
    @Inject
    private CustomInvoiceDetailJpaController invoiceDetailJpaController;
    

    public ShoppingCart getShoppingCart()
    {
        return cart;
    }

    public BigDecimal getPrice(Book book)
    {
        System.out.println(book.toString());
        if(book.getSalePrice() != null)
            return book.getSalePrice();

        return book.getListPrice();
    }
    
    public String proceedToCheckout()
    {
        if(cart.getShoppingCartBooks().isEmpty())
        {
            return "";
        }
        else if(login == null || !login.getLoggedIn())
        {
            return "login.xhtml";
        }
        
        return "authenticated/finalization.xhtml";
    }
    
    public boolean isBookBoughtAlready(Book book)
    {
        if(login.getLoggedIn())
        {
            Client user = clientJpaController.findClientByUsername(login.getUsername());
            List<MasterInvoice> masterInvoicesOfUser = masterInvoiceJpaController.findMasterInvoicesByClientId(user.getClientId());
            
            
            for(MasterInvoice mi : masterInvoicesOfUser)
            {
                for(InvoiceDetail invoice : invoiceDetailJpaController.findInvoicesByMasterInvoice(mi))
                {
                    if(Objects.equals(invoice.getBookId().getBookId(), book.getBookId()))
                        return true;
                }
            }
            
        }
        return false;
    }
}
