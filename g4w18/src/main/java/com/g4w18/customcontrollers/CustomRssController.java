package com.g4w18.customcontrollers;

import com.g4w18.controllers.RssJpaController;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Rss;
import com.g4w18.entities.Rss_;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The CustomRssController class is responsible to encapsule the functionality
 * of the RssJpaController class as well as containing custom query methods.
 *
 * @author Sebastian Ramirez
 */
public class CustomRssController implements Serializable {

    @Inject
    private RssJpaController rssController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    /**
     * This method is just a call to the RssJpaController method called create.
     * It does the same with the exception of checking if the incoming RSS
     * object has been set as active. If it has been set as active, all the
     * other RSS entries will be set as inactive.
     *
     * @param rss
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void create(Rss rss) throws RollbackFailureException, Exception {
        activeCheck(rss);
        rssController.create(rss);
    }

    /**
     * This method is just a call to the RssJpaController method called edit. It
     * does the same with the exception of checking if the incoming RSS object
     * has been set as active. If it has been set as active, all the other RSS
     * entries will be set as inactive.
     *
     * @param rss
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void edit(Rss rss) throws RollbackFailureException, Exception {
        Rss currentActive = findActiveRss();
        if (rss.equals(currentActive)) {
            if (!rss.getIsActive()) {
                setFirstAsActive();
            }
        } else {
            activeCheck(rss);
        }
        rssController.edit(rss);
    }

    /**
     * This method is called by the view when deleting an specific Rss entry.
     *
     * @param id
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void destroy(Integer id) throws RollbackFailureException, Exception {
        if (findRss(id).getIsActive()) {
            setFirstAsActive();
        }
        rssController.destroy(id);
    }

    public List<Rss> findRssEntities() {
        return rssController.findRssEntities();
    }

    public List<Rss> findRssEntities(int maxResults, int firstResult) {
        return rssController.findRssEntities(maxResults, firstResult);
    }

    public Rss findRss(Integer id) {
        return rssController.findRss(id);
    }

    public int getRssCount() {
        return rssController.getRssCount();
    }

    /**
     * This method searches for the RSS in the database and return the RSS that
     * is active.
     *
     * @return A single RSS object which is the current active one.
     */
    public Rss findActiveRss() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rss> cq = cb.createQuery(Rss.class);
        Root<Rss> rss = cq.from(Rss.class);
        cq.select(rss).where(cb.equal(rss.get(Rss_.isActive), true));
        TypedQuery<Rss> query = em.createQuery(cq);
        return query.getSingleResult();
    }

    /**
     * This method searches the DB for the RSS entries that are currently not
     * active
     *
     * @return A list of RSS objects which are currently not active.
     */
    public List<Rss> findInactiveRss() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rss> cq = cb.createQuery(Rss.class);
        Root<Rss> rss = cq.from(Rss.class);
        cq.select(rss).where(cb.equal(rss.get(Rss_.isActive), false));
        TypedQuery<Rss> query = em.createQuery(cq);
        return query.getResultList();
    }

    /**
     * This method sets the current RSS entry as inactive. Only one RSS should
     * be active at a time. This method ensures that when an RSS is specified as
     * active, the current one become inactive.
     *
     * @param rssId
     * @throws Exception
     */
    private void setCurrentAsInactive() throws Exception {
        Rss currentActive = findActiveRss();
        currentActive.setIsActive(false);
        rssController.edit(currentActive);
    }

    /**
     * Simple method that will check if the incoming RSS entry, be it a new one
     * or modified one, has been set as active. If it has, it will call the
     * appropriate methods to ensure that the current active RSS is set as
     * inactive.
     *
     * @param rss
     * @throws Exception
     */
    private void activeCheck(Rss rss) throws Exception {
        if (rss.getIsActive()) {
            setCurrentAsInactive();
        }
    }

    /**
     * This method sets the first found inactive RSS entry and sets it as
     * active.
     *
     * @throws Exception
     */
    private void setFirstAsActive() throws Exception {
        Rss toBeActive = findInactiveRss().get(0);
        toBeActive.setIsActive(true);
        rssController.edit(toBeActive);
    }

}
