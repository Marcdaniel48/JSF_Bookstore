package com.g4w18.customcontrollers;

import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
@SessionScoped
public class ShoppingCart implements Serializable
{
    private List<Book> shoppingCartBooks;
    
    public void addToCart(Book book)
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        shoppingCartBooks = (List<Book>)(session.getAttribute("shoppingCart"));
        if(shoppingCartBooks == null)
        {
            shoppingCartBooks = new ArrayList<>();
        }
        
        if(!shoppingCartBooks.contains(book))
        {
            shoppingCartBooks.add(book);
            session.setAttribute("shoppingCart", shoppingCartBooks);
        }
    }

    public void removeFromCart(Book book)
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        shoppingCartBooks = (List<Book>)(session.getAttribute("shoppingCart"));
        if(shoppingCartBooks == null)
        {
            shoppingCartBooks = new ArrayList<>();
        }
        
        if(shoppingCartBooks.contains(book))
        {
            shoppingCartBooks.remove(book);
            session.setAttribute("shoppingCart", shoppingCartBooks);
        }
    }

    public List<Book> getShoppingCartBooks()
    {
        if(shoppingCartBooks == null)
        {
            shoppingCartBooks = new ArrayList<>();
        }
        
        return shoppingCartBooks;
    }
    
    public void setShoppingCartBooks(List<Book> shoppingCartBooks)
    {
        this.shoppingCartBooks = shoppingCartBooks;
    }
    
    public double getSubtotal()
    {
        double sum = 0;
        
        for(Book book : shoppingCartBooks)
        {
            if(book.getSalePrice().doubleValue() > 0)
                sum += book.getSalePrice().doubleValue();
            else
                sum += book.getListPrice().doubleValue();
        }
        
        return sum;
    }
    
    public void emptyShoppingCart()
    {
        shoppingCartBooks = new ArrayList<>();
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("shoppingCart", shoppingCartBooks);
    }

}
