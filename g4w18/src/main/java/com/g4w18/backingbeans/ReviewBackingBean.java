package com.g4w18.backingbeans;

import com.g4w18.controllers.ReviewJpaController;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.Map;
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
 * @author Ken
 */
@Named
@RequestScoped
public class ReviewBackingBean implements Serializable {

    @Inject
    private ReviewJpaController reviewJpaController;

    private Review review;

    private Logger log = Logger.getLogger(BookBackingBean.class.getName());

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
        review.setBookId(bookId);
        reviewJpaController.create(review);
        return null;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
