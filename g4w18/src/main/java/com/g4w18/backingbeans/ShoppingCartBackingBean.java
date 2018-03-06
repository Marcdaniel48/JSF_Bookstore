/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.ShoppingCart;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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

    public List<Book> getShoppingCartBooks()
    {
        List<Book> list = cart.getShoppingCartBooks();

        return list;
    }

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
}
