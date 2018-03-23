/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomRssController;
import com.g4w18.entities.Rss;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Sebastian Ramirez
 */
@Named
@RequestScoped
public class RssManagerBackingBean implements Serializable {

    @Inject
    private CustomRssController rssController;

    private List<Rss> rssList;
    private List<Rss> filteredRss;
    private Rss toDelete;
    private Rss newRss;
    private final FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method for the RSS list variable.
     *
     * @return All the RSS entries in the database.
     */
    public List<Rss> getRssList() {
        if (rssList == null) {
//            LOGGER.log(Level.INFO, "List was null");
            rssList = rssController.findRssEntities();
        }
        return rssList;
    }

    /**
     * Getter method for the Filtered RSS objects.
     *
     * @return the Filtered Rss objects used by PrimeFace's datatable.
     */
    public List<Rss> getFilteredRss() {
        return filteredRss;
    }

    /**
     * Setter method for the Filtered RSS objects.
     *
     * @param filteredRss the RSS objects filtered by Primeface's datatable.
     */
    public void setFilteredRss(List<Rss> filteredRss) {
        this.filteredRss = filteredRss;
    }

    /**
     * The onRowEdit method updates any record edited in the datatable by
     * updating the values of the entry in the database.
     *
     * @param event
     * @return
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public String onRowEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        Rss editedRss = (Rss) event.getObject();
        rssController.edit(editedRss);
        addMessage("managerRSSEdit");
        return null;
    }

    /**
     * The delete method deletes the appropriate RSS entry from the database in
     * accordance to the RSS object toDelete passed by the view to the backing
     * bean. If there are no more than 1 RSS objects in the database, this
     * method will not delete the last record and inform the manager that at
     * least one RSS entry should be kept in order to not break the sites main
     * display.
     *
     * @return null This method will cause the view to return to itself.
     * @throws Exception
     */
    public String delete() throws Exception {
        if (rssController.getRssCount() > 1) {
            rssController.destroy(toDelete.getRssId());
            addMessage("managerDeleteSuccessful");
        } else {
            addMessage("managerCantDelete");
        }
        return null;
    }

    /**
     * The addMessage method simplifies and reduces redundancy of code when
     * displaying a message is necessary.
     *
     * @param key The string key in the messages bundle.
     */
    private void addMessage(String key) {
        String message = context.getApplication().getResourceBundle(context, "msgs").getString(key);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        context.addMessage(null, msg);
    }

    /**
     * Getter method for the toDelete variable.
     *
     * @return The entry in a row to be deleted.
     */
    public Rss getToDelete() {
        return toDelete;
    }

    public Rss getNewRss() {
        if (newRss == null) {
            newRss = new Rss();
        }
        return newRss;
    }

    public String createRss() throws Exception {
        rssController.create(newRss);
        addMessage("managerRSSNewSuccess");
        return null;
    }

    /**
     * Setter method for the toDelete variable. It is needed for the
     * setPropertyActionListener tag in the view to pass the attribute to be
     * handled to the backing bean.
     *
     * @param rss The RSS entry to be deleted.
     */
    public void setToDelete(Rss rss) {
        this.toDelete = rss;
    }
}
