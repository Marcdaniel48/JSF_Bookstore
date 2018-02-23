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


    public ShoppingCart()
    {
        System.out.println("calling you");
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        books = new ArrayList<>();

        if((List<Book>)session.getAttribute("shoppingCart") != null)
        {
            books = (List<Book>)session.getAttribute("shoppingCart");
        }

        //DELETE THESE - SAMPLE DATA
        Book sample1 = new Book(1, "ISBN###", "Title of book", "Publisher of book", Date.valueOf(LocalDate.now()), 324, "Genre of book", "description of book", "format of book", new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigDecimal(9.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), Date.valueOf(LocalDate.now()));
        Book sample2 = new Book(2, "ISBN###", "Another books", "Publisher of book", Date.valueOf(LocalDate.now()), 324, "Genre of book", "description of book", "format of book", new BigDecimal(4.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigDecimal(4.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigDecimal(4.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), Date.valueOf(LocalDate.now()));
        addToCart(sample1);
        addToCart(sample2);
    }

    public void addToCart(Book book)
    {
        if(session!=null)
        {
            if(session.getAttribute("loggedIn") != null)
            {
                if(!books.contains(book))
                {System.out.println("calling him");
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
