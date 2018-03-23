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
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author 1331680
 */
@Named
@RequestScoped
public class RssManagerBackingBean implements Serializable {

    @Inject
    private CustomRssController rssController;

    private List<Rss> rssList;
    private List<Rss> filteredRss;
    private Rss toDelete;
    private final FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public List<Rss> getRssList() {
        if (rssList == null) {
//            LOGGER.log(Level.INFO, "List was null");
            rssList = rssController.findRssEntities();
        }
        return rssList;
    }

    public List<Rss> getFilteredRss() {
        return filteredRss;
    }

    public void setFilteredRss(List<Rss> filteredRss) {
        this.filteredRss = filteredRss;
    }

    public String onRowEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        Rss editedRss = (Rss) event.getObject();
        rssController.edit(editedRss);
        addMessage("managerRSSEdit");
        return null;
    }

    public String delete() throws Exception {
        if (rssController.getRssCount() > 1) {
            rssController.destroy(toDelete.getRssId());
            addMessage("managerDeleteSuccessful");
        } else {
            addMessage("managerCantDelete");
        }
        return null;
    }

    private void addMessage(String key) {
        String message = context.getApplication().getResourceBundle(context, "msgs").getString(key);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        context.addMessage(null, msg);
    }

    public Rss getToDelete() {
        return toDelete;
    }

    public void setToDelete(Rss rss) {
        this.toDelete = rss;
    }
}
