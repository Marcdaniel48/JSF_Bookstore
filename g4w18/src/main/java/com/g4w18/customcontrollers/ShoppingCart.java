package com.g4w18.customcontrollers;

import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Class responsible for accessing and manipulating the bookstore website's shopping cart.
 * 
 * @author Marc-Daniel
 */
@SessionScoped
public class ShoppingCart implements Serializable
{
    // The list of books that the shopping cart contains.
    private List<Book> shoppingCartBooks;
    
    /**
     * Adds a given book to the shopping cart.
     * If the book that's trying to be added is already in the shopping cart, do nothing.
     * 
     * @param book 
     */
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

    /**
     * Removes a given book from the shopping cart.
     * @param book 
     */
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

    /**
     * Getter method. Returns a list of the books that the shopping cart contains.
     * @return shoppingCartBooks
     */
    public List<Book> getShoppingCartBooks()
    {
        if(shoppingCartBooks == null)
        {
            shoppingCartBooks = new ArrayList<>();
        }
        
        return shoppingCartBooks;
    }
    
    /**
     * Setter method. Sets a list of books that the shopping cart will contain.
     * @param shoppingCartBooks 
     */
    public void setShoppingCartBooks(List<Book> shoppingCartBooks)
    {
        this.shoppingCartBooks = shoppingCartBooks;
    }
    
    /**
     * This method goes through every book in the shopping cart and sums up the subtotal for each of them.
     * Returns that subtotal.
     * 
     * @return 
     */
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
    
    /**
     * Empties the shopping cart.
     */
    public void emptyShoppingCart()
    {
        shoppingCartBooks = new ArrayList<>();
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("shoppingCart", shoppingCartBooks);
    }

}
