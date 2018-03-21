/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomRssController;
import com.g4w18.entities.Rss;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1331680
 */
@Named
@ViewScoped
public class RssManagerBackingBean implements Serializable {

    @Inject
    private CustomRssController rssController;

    private List<Rss> rssList;
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public List<Rss> getRssList() {
        if (rssList == null) {
            rssController.findRssEntities();
        }
        return rssList;
    }
}
