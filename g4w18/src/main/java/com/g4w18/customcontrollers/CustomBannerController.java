package com.g4w18.customcontrollers;

import com.g4w18.controllers.BannerJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Banner;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Jephthia
 */
public class CustomBannerController implements Serializable
{
    @Inject
    private BannerJpaController bannerController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
    public void create(Banner banner) throws RollbackFailureException, Exception
    {
        bannerController.create(banner);
    }

    public void edit(Banner banner) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        bannerController.edit(banner);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        bannerController.destroy(id);
    }

    public List<Banner> findBannerEntities()
    {
        return bannerController.findBannerEntities();
    }

    public List<Banner> findBannerEntities(int maxResults, int firstResult)
    {
        return bannerController.findBannerEntities(maxResults, firstResult);
    }

    public Banner findBanner(Integer id)
    {
        return bannerController.findBanner(id);
    }

    public int getBannerCount()
    {
        return bannerController.getBannerCount();
    }
    
    public List<Banner> getActiveBanners()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Banner> cq = cb.createQuery(Banner.class);
        Root<Banner> banner = cq.from(Banner.class);
        cq.select(banner);
        
        Query genres = em.createQuery(cq);
        return genres.getResultList();
    }
}