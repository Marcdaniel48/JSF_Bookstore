package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.ClientJpaController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.customcontrollers.CustomClientJpaController;
import com.g4w18.controllers.ReviewJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * This is a backing bean. It is used when a page or form must interact with
 * beans that are not managed such as entity beans. In this example the entity
 * bean for Inventory will be manually loaded with data for the example page.
 *
 * @author Ken
 */
@Named
@ViewScoped
public class BookDetailsBackingBean implements Serializable {

    @Inject
    private CustomBookController customBookController;
    @Inject
    private ReviewJpaController reviewJpaController;
    @Inject
    private CustomClientJpaController clientJpaController;

    private Book book;
    private Review review;
    private Client client;
    private List<Book> recommendedBooks;
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public Book getBook() {
        if (book == null) {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int id = Integer.parseInt(params.get("id"));
            book = customBookController.findBook(id);
            
            storeBookCookie(book);
        }
        return book;
    }
    
    /**
     * Saves the genre of the book that was viewed by the user
     * in a cookie so that we are able to give recommended
     * books to the user based on what they've looked at.
     * @param book
     */
    private void storeBookCookie(Book book)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = context.getExternalContext().getRequestCookieMap();
        
        Cookie genresCookie = (Cookie)cookies.get("VisitedGenres");
        
        String genres = "";
        
        if(genresCookie != null)
            genres = genresCookie.getValue();

        //if this book's genre isn't already there, add it
        if(!genres.contains(book.getGenre()))
            genres += book.getGenre() + ",";
        
        context.getExternalContext().addResponseCookie("VisitedGenres", genres, null);
    }

    public List<Book> getRecommendedBooks() {
        if (recommendedBooks == null) {
            List<Book> booksByGenre = customBookController.findBooksByGenre(book.getGenre());
            booksByGenre.remove(book);
            Collections.shuffle(booksByGenre);
            recommendedBooks = booksByGenre.subList(0, 6);
        }
        return recommendedBooks;
    }

    public int getRating() {
        int averageRating = 0;
        Collection<Review> reviews = book.getReviewList();
        int size = reviews.size();
        log.log(Level.INFO, "Reviews size: {0}", reviews.size());
        if (size > 0) {
            for (Review r : reviews) {
                averageRating = averageRating + r.getRating();
            }
            return averageRating / size;
        }
        log.log(Level.INFO, "Rating: {0}", averageRating);
        return averageRating;
    }

    public Review getReview() {
        log.log(Level.INFO, "getReview() called");
        if (review == null) {
            review = new Review();
        }
        return review;
    }

    public Client getClient() {
        if (client == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            String username = (String) session.getAttribute("username");
            if (username != null) {
                client = clientJpaController.findClientByUsername(username);
                log.log(Level.INFO, "Client found, name: {0}", client.getFirstName() + "");
            } else {
                client = new Client();
                client.setFirstName("Guest");
            }
        }
        return client;
    }

    public String createReview() throws Exception {
        if (client.getFirstName().equals("Guest")) {
//            FacesMessage loginMessage = new FacesMessage("Please login before leaving a review");
//            loginMessage.setSeverity(FacesMessage.SEVERITY_INFO);
//            FacesContext.getCurrentInstance().addMessage("", loginMessage);
            return "login.xhtml";
        }
        for (Review r : client.getReviewList()) {
            if (Objects.equals(r.getBookId().getBookId(), book.getBookId())) {
//                FacesMessage loginMessage = new FacesMessage("You already reviewed this book");
//                loginMessage.setSeverity(FacesMessage.SEVERITY_INFO);
//                FacesContext.getCurrentInstance().addMessage("", loginMessage);
                return null;
            }
        }
        log.log(Level.INFO, "Book is: {0}", book.getBookId() + "");
        log.log(Level.INFO, "Review is: {0}", review);
        review.setBookId(book);
        review.setClientId(client);
        review.setApprovalStatus(false);
        reviewJpaController.create(review);
        return null;
    }
}
