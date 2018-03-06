/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customcontrollers;

import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.backingbeans.BookDetailsBackingBean;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class LoginController implements Serializable{
    @Inject
    private CustomClientController clientJpaController;

    private String username;
    private String password;
        private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String login()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        boolean loggedIn;

        Client client = clientJpaController.findClientByCredentials(username, password);

        if (client != null)
        {
            loggedIn = true;
        }
        else
        {
            loggedIn = false;
        }

        session.setAttribute("loggedIn", loggedIn);
        session.setAttribute("username", username);
        log.log(Level.INFO, "Username: {0}", session.getAttribute("username"));
        return "bookList.xhtml";
    }
}
