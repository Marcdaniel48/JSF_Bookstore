/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.controllers;

import com.g4w18.entities.Client;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
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
    private ClientJpaController clientJpaController;
    
    private String username;
    private String password;
    
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
    
    public void login()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        FacesMessage message;
        boolean loggedIn = false;
        
        Client client = clientJpaController.findClientByCredentials(username, password);
        System.out.println("Username:\t"+username+"\tPassword:\t"+password);
        
        if (client != null) 
        {
            System.out.println("nice login");
            loggedIn = true;
        } 
        else 
        {
            System.out.println("unlucky fail");
            loggedIn = false;
        }
        
        session.setAttribute("Logged in to bookstore", loggedIn);
    }
}
