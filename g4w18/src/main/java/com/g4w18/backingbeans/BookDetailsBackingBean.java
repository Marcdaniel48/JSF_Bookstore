package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomReviewController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.Review;
import com.g4w18.util.Messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * Backing bean in charge of the functionality of the bookDetail page.
 *
 * @author Sebastian Ramirez
 */
@Named
@ViewScoped
public class BookDetailsBackingBean implements Serializable {

    @Inject
    private CustomBookController customBookController;
    @Inject
    private CustomReviewController reviewController;
    @Inject
    private CustomClientController clientJpaController;

    private Book book;
    private Review review;
    private List<Review> approvedReviews;
    private Client client;
    private int averageRating;
    private List<Book> recommendedBooks;
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method for the book class variable. If book is null, it will
     * retrieve the id from the request parameter map and find the book with
     * that id.
     *
     * @author Sebastian Ramirez
     * @return The book to display.
     */
    public Book getBook() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (book == null) {
            int id = Integer.parseInt(params.get("id"));
            book = customBookController.findBook(id);
            storeBookCookie(book);
        }
        return book;
    }

    /**
     * @author Jephthia Saves the genre of the book that was viewed by the user
     * in a cookie so that we are able to give recommended books to the user
     * based on what they've looked at.
     * @param book
     */
    private void storeBookCookie(Book book) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookies = context.getExternalContext().getRequestCookieMap();

        Cookie genresCookie = (Cookie) cookies.get("VisitedGenres");

        String genres = "";

        if (genresCookie != null) {
            genres = genresCookie.getValue();
        }

        //if this book's genre isn't already there, add it
        if (!genres.contains(book.getGenre())) {
            genres += book.getGenre() + ",";
        }

        context.getExternalContext().addResponseCookie("VisitedGenres", genres, null);
    }

    /**
     * Getter method for the recommendedBooks class variable. If the variable is
     * null, it will retrieve the recommended books by genre from the
     * controller.
     *
     * @author Sebastian Ramirez
     * @return The list of recommended books.
     */
    public List<Book> getRecommendedBooks() {
        if (recommendedBooks == null) {
            List<Book> booksByGenre = customBookController.findBooksByGenre(book.getGenre());
            booksByGenre.remove(book);
            Collections.shuffle(booksByGenre);
            recommendedBooks = booksByGenre.subList(0, 6);
        }
        return recommendedBooks;
    }

    /**
     * Getter method for the approvedReviews class variable. If the variable is
     * null, it will retrieve the approved reviews from the controller.
     *
     * @author Sebastian Ramirez
     * @return
     */
    public List<Review> getApprovedReviews() {
        if (approvedReviews == null) {
            List<Review> bookReviews = new ArrayList<>();
            book.getReviewList().stream().filter((r) -> (r.getApprovalStatus()))
                    .forEachOrdered((r) -> {
                        bookReviews.add(r);
                    });
            approvedReviews = bookReviews;
        }
        return approvedReviews;
    }

    /**
     * Getter method for the averageRating variable. It collects the rating from
     * all the reviews of an specific book and calculates the average.
     *
     * @author Sebastian Ramirez
     * @return The average rating for an specific book.
     */
    public int getRating() {
        averageRating = 0;
        Collection<Review> reviews = book.getReviewList();
        int size = reviews.size();
        LOGGER.log(Level.INFO, "Reviews size: {0}", reviews.size());
        if (size > 0) {
            for (Review r : reviews) {
                averageRating = averageRating + r.getRating();
            }
            return averageRating / size;
        }
        LOGGER.log(Level.INFO, "Rating: {0}", averageRating);
        return averageRating;
    }

    /**
     * Getter method for the review class variable. The review class variable is
     * used for the creation of a new review. If the variable is null it will
     * create a new variable object to contain the variable created.
     *
     * @author Sebastian Ramirez
     * @return
     */
    public Review getReview() {
        LOGGER.log(Level.INFO, "getReview() called");
        if (review == null) {
            review = new Review();
        }
        return review;
    }

    /**
     * Getter method for the client class variable. If the variable is null and
     * the user is not registered it will create a new Client named guest. If
     * the user is registered it will retrieve it from the session object.
     *
     * @return The client leaving a review.
     */
    public Client getClient() {
        if (client == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            String username = (String) session.getAttribute("username");
            if (username != null) {
                client = clientJpaController.findClientByUsername(username);
                LOGGER.log(Level.INFO, "Client found, name: {0}",
                        client.getFirstName() + "");
            } else {
                client = new Client();
                client.setFirstName("Guest");
            }
        }
        return client;
    }

    /**
     * This method creates a new review. If the user is a guest, it will display
     * a message suggesting the user to login and not create the review. If the
     * user is logged in, it will create a new review object ONLY if the user
     * has not reviewed the book already.
     *
     * @return
     * @throws Exception
     */
    public String createReview() throws Exception {
        if (client.getFirstName().equals("Guest")) {
//            FacesMessage loginMessage = new FacesMessage("Please login before leaving a review");
//            loginMessage.setSeverity(FacesMessage.SEVERITY_INFO);
//            FacesContext.getCurrentInstance().addMessage("", loginMessage);
            Messages.addMessage("pleaseLoginBeforeReviewin");
            return null;
        }
        for (Review r : client.getReviewList()) {
            if (Objects.equals(r.getBookId().getBookId(), book.getBookId())) {
//                FacesMessage loginMessage = new FacesMessage("You already reviewed this book");
//                loginMessage.setSeverity(FacesMessage.SEVERITY_INFO);
//                FacesContext.getCurrentInstance().addMessage("", loginMessage);
                Messages.addMessage("existingReview");
                return null;
            }
        }
//        LOGGER.log(Level.INFO, "Book is: {0}", book.getBookId() + "");
//        LOGGER.log(Level.INFO, "Review is: {0}", review);
        Date currentDate = new Date();
        review.setReviewDate(currentDate);
        review.setBookId(book);
        review.setClientId(client);
        review.setApprovalStatus(false);
        reviewController.create(review);
        Messages.addMessage("reviewSuccesful");
        return null;
    }
}
