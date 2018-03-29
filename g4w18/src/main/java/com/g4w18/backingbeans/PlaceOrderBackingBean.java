/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.customcontrollers.ShoppingCart;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @Inject private ClientJpaController clientJpaController;
    
    @Inject private TaxJpaController taxJpaController;
    
    @Inject private InvoiceDetailJpaController invoiceJpaController;
    
    @Inject private MasterInvoiceJpaController masterInvoiceJpaController;
    
    @Inject private ShoppingCart shoppingCart;
    
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
        
        return tax.getOverallTaxRate().doubleValue();
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
        master.setSaleDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        master.setGrossValue(new BigDecimal(shoppingCart.getSubtotal()));
        
        Double taxIncludedPercentage = tax.getOverallTaxRate().add(BigDecimal.ONE).doubleValue();
        double net = Math.round((shoppingCart.getSubtotal() * taxIncludedPercentage) * 100.) / 100.;
        
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
