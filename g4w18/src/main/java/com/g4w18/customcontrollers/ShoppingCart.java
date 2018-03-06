package com.g4w18.customcontrollers;

import com.g4w18.backingbeans.BookDetailsBackingBean;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
public class ShoppingCart implements Serializable {

    private List<Book> books;
    private HttpSession session;

    public ShoppingCart() {
        System.out.println("calling you");
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        books = new ArrayList<>();

        if ((List<Book>) session.getAttribute("shoppingCart") != null) {
            books = (List<Book>) session.getAttribute("shoppingCart");
        }
    }
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public void addToCart(Book book) {
        log.info("1one");
        if (session != null) {
            log.info("two");
            if (session.getAttribute("loggedIn") != null) {
                log.info("three");
                log.info(book.getTitle());
                log.info(books.contains(book)+"");
                if (!books.contains(book)) {
                    log.info("four");
                    log.info(book.getTitle());
                    books.add(book);
                    session.setAttribute("shoppingCart", books);
                }
            }
        }
    }

    public void removeFromCart(Book book) {
        if (session != null) {
            if (session.getAttribute("loggedIn") != null) {
                if ((List<Book>) session.getAttribute("shoppingCart") != null) {
                    if (books.contains(book)) {
                        books.remove(book);
                        session.setAttribute("shoppingCart", books);
                    }
                }
            }
        }
    }

    public List<Book> getShoppingCartBooks() {
        return books;
    }

}
