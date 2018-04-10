package com.g4w18.customcontrollers;

import com.g4w18.controllers.ReviewJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Review;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Jephthia
 */
public class CustomReviewController implements Serializable
{
    @Inject
    private ReviewJpaController reviewController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create(Review review) throws RollbackFailureException, Exception
    {
        reviewController.create(review);
    }

    public void edit(Review review) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        reviewController.edit(review);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        reviewController.destroy(id);
    }

    public List<Review> findReviewEntities()
    {
        return reviewController.findReviewEntities();
    }

    public List<Review> findReviewEntities(int maxResults, int firstResult)
    {
        return reviewController.findReviewEntities(maxResults, firstResult);
    }

    public Review findReview(Integer id)
    {
        return reviewController.findReview(id);
    }

    public int getReviewCount()
    {
        return reviewController.getReviewCount();
    }
    
    /**
     * @author Jephthia
     * @return A list of all the approved reviews
     */
    public List<Review> getApprovedReviews()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> review = cq.from(Review.class);
        cq.select(review);
        cq.where(cb.isTrue(review.get("approvalStatus")));
        
        Query genres = em.createQuery(cq);
        return genres.getResultList();
    }
    
    /**
     * @author Jephthia
     * @return A list of all the reviews that have yet to be approved
     */
    public List<Review> getNotApprovedReviews()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> review = cq.from(Review.class);
        cq.select(review);
        cq.where(cb.isFalse(review.get("approvalStatus")));
        
        Query genres = em.createQuery(cq);
        return genres.getResultList();
    }
}