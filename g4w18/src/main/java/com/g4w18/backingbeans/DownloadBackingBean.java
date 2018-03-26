package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author salma
 */

@Named("download")
@RequestScoped
public class DownloadBackingBean {
    
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceController;
    
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());
    
    public String getDownloadInfo()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String username = (String)session.getAttribute("username");
        log.log(Level.INFO, "USERNAME OF CURRENT USER: "+username);
        
        return username;
    }
    
    
}
