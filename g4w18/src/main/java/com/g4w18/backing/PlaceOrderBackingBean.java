/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.controllers.ShoppingCart;
import com.g4w18.controllers.TaxJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Tax;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
    
    @Inject InvoiceDetailJpaController invoiceJpaController;
    
    @Inject MasterInvoiceJpaController masterInvoiceJpaController;
    
    @Inject ShoppingCart shoppingCart;
    
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
    
    public String createInvoice() throws Exception
    {
        Client user = getCurrentClient();
        Tax tax = taxJpaController.findTaxByProvince(user.getProvince());
        List<Book> books = shoppingCart.getShoppingCartBooks();
        
        
        MasterInvoice master = new MasterInvoice();
        master.setClientId(user);
        master.setSaleDate(Date.valueOf(LocalDate.now()));
        master.setGrossValue(new BigDecimal(shoppingCart.getSubtotal()));
        
        double rate = 1;
        rate += tax.getGstRate().doubleValue() /100;
        rate += tax.getPstRate().doubleValue() /100;
        rate += tax.getHstRate().doubleValue() /100;
        double net = Math.round((shoppingCart.getSubtotal() * rate) * 100.) / 100.;
        
        master.setNetValue(new BigDecimal(net));
        masterInvoiceJpaController.create(master);
        
        for(Book book : books)
        {
            InvoiceDetail invoice = new InvoiceDetail();
            
            invoice.setInvoiceId(master);
            invoice.setBookId(book);
            
            if(book.getSalePrice().doubleValue() > 0)
                invoice.setBookPrice(book.getSalePrice());
            else
                invoice.setBookPrice(book.getListPrice());
            
            invoice.setGstRate(tax.getGstRate());
            invoice.setHstRate(tax.getHstRate());
            invoice.setPstRate(tax.getPstRate());
            
            invoiceJpaController.create(invoice);
        }
        
        return "/index";
    }
    
}
