package com.g4w18.controllers;

import com.g4w18.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
@ViewScoped
public class ShoppingCart implements Serializable
{
    private List<Book> books;
    private HttpSession session;

    @Inject
    private BookJpaController control;
    
    public ShoppingCart()
    {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        books = new ArrayList<>();

        if((List<Book>)session.getAttribute("shoppingCart") != null)
        {
            books = (List<Book>)session.getAttribute("shoppingCart");
        }
    }
    public void addToCart(Book book)
    {
        if(session!=null)
        {
            if(session.getAttribute("loggedIn") != null)
            {
                if(!books.contains(book))
                {
                    books.add(book);
                    session.setAttribute("shoppingCart", books);
                }
            }
        }
    }

    public void removeFromCart(Book book)
    {
        if(session!=null)
        {
            if(session.getAttribute("loggedIn") != null)
            {
                if((List<Book>)session.getAttribute("shoppingCart") != null)
                {
                    if(books.contains(book))
                    {
                        books.remove(book);
                        session.setAttribute("shoppingCart", books);
                    }
                }
            }
        }
    }

    public List<Book> getShoppingCartBooks()
    {
        return books;
    }

}
