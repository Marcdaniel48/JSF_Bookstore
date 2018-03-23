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
import com.g4w18.util.Messages;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
        FacesContext fc = FacesContext.getCurrentInstance();
        String message = fc.getApplication().getResourceBundle(fc, "msgs").getString("managerRSSEdit");
        FacesMessage msg = new FacesMessage(message);
        fc.addMessage(null, msg);
        return null;
    }
}
