/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
public class ShoppingCart 
{
    public static void addToCart(int bookId)
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        if(session!=null)
        {
            if(session.getAttribute("loggedIn") != null)
            {
                List<Integer> bookIds = new ArrayList<Integer>();
                
                if((List<Integer>)session.getAttribute("shoppingCart") != null)
                {
                    bookIds = (List<Integer>)session.getAttribute("shoppingCart");
                }
                
                if(!bookIds.contains(bookId))
                {
                    bookIds.add(bookId);
                    session.setAttribute("shoppingCart", bookIds);
                }
            }
        }
    }
    
    public static void removeFromCart(int bookId)
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        if(session!=null)
        {
            if(session.getAttribute("loggedIn") != null)
            {
                if((List<Integer>)session.getAttribute("shoppingCart") != null)
                {
                    List<Integer> bookIds = (List<Integer>) session.getAttribute("shoppingCart");

                    if(bookIds.contains(bookId))
                    {
                        bookIds.remove(bookId);
                        session.setAttribute("shoppingCart", bookIds);
                    }
                }
            }
        }
    }
    
}
