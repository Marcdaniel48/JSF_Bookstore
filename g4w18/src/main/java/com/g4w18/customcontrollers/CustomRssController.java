/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.controllers.RssJpaController;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 1331680
 */
public class CustomRssController implements Serializable {

    @Inject
    private RssJpaController rssController;

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    public void create() throws RollbackFailureException, Exception {

    }
}
