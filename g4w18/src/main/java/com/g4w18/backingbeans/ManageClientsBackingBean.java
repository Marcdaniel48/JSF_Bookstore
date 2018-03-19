/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class ManageClientsBackingBean implements Serializable
{
    @Inject
    private ClientJpaController clientJpaController;
    
    public ClientJpaController getClientJpaController()
    {
        return clientJpaController;
    }
}
