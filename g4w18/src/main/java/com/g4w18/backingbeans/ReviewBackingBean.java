package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.ClientJpaController;
import com.g4w18.customcontrollers.CustomReviewController;
import com.g4w18.controllers.ReviewJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is a backing bean. It is used when a page or form must interact with
 * beans that are not managed such as entity beans. In this example the entity
 * bean for Inventory will be manually loaded with data for the example page.
 *
 * @author Sebastien, Jephthia
 */
@Named
@RequestScoped
public class ReviewBackingBean implements Serializable {

    @Inject
    private ReviewJpaController reviewJpaController;
    
    @Inject
    private CustomReviewController reviewController;
    
    @Inject
    private BookJpaController bookJpaController;
    
    @Inject
    private ClientJpaController clientJpaController;

    private Review review;

    private Logger logger = Logger.getLogger(BookBackingBean.class.getName());

    /**
     * Client created if it does not exist.
     *
     * @return
     */
    public Review getReview() {
        if (review == null) {
            review = new Review();
        }
        return review;
    }

    public String createReview() throws Exception {
        Map<String, String> params
                = getFacesContext().getExternalContext().getRequestParameterMap();
        int bookId = Integer.parseInt(params.get("bookId"));
        Book book = bookJpaController.findBook(bookId);
        review.setBookId(book);
        Client client = clientJpaController.findClient(5);
        review.setClientId(client);
        reviewJpaController.create(review);
        return null;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    /**
     * @author Jephthia
     * @return All the reviews that have been approved
     */
    public List<Review> getApprovedReviews()
    {
        return reviewController.getApprovedReviews();
    }
    
    /**
     * @author Jephthia
     * @return All the reviews that haven't been approved yet
     */
    public List<Review> getNotApprovedReviews()
    {
        return reviewController.getNotApprovedReviews();
    }
    
    /**
     * @author Jephthia
     * Sets the approval status to true
     * and saves it to the database
     * @param review 
     */
    public void approve(Review review)
    {
        try
        {
            review.setApprovalStatus(true);
            reviewController.edit(review);
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @author Jephthia
     * Removes the review from the database
     * @param review 
     */
    public void disapprove(Review review)
    {
        try
        {
            reviewController.destroy(review.getReviewId());
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @author Jephthia
     * Sets the approval status to false to put it back
     * in pending mode.
     * @param review 
     */
    public void removeApproval(Review review)
    {
        try
        {
            review.setApprovalStatus(false);
            reviewController.edit(review);
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
