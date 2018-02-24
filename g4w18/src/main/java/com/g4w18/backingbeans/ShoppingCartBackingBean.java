/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.ShoppingCart;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
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
    
    public List<Book> getShoppingCartBooks()
    {
        List<Book> list = cart.getShoppingCartBooks();
        
        return list;
    }
    
    public void removeFromCart(Book book)
    {
        cart.removeFromCart(book);
    }
    
    public void addToCart(Book book)
    {
        log.info("Adding to cart");
        
        cart.addToCart(book);
    }
}
