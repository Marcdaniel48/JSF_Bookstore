/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import com.g4w18.controllers.ShoppingCart;
import com.g4w18.entities.Book;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named
@RequestScoped
public class ShoppingCartBackingBean 
{
    @Inject
    private ShoppingCart cart;
    
    public List<Book> getShoppingCartBooks()
    {
        List<Book> list = cart.getShoppingCartBooks();
        System.out.println("Size is: " + list.size());
        return list;
    }
    
    public void removeFromCart(Book book)
    {
        cart.removeFromCart(book);
    }
}
