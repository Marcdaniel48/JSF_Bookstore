package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author salma
 */

@Named("download")
@RequestScoped
public class DownloadBackingBean {
    
    @Inject
    private CustomMasterInvoiceJpaController masterInvoiceController;
    
    @Inject
    private CustomClientController clientController;
    
    @Inject
    private CustomBookController bookController;
    
    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());
    private StreamedContent file;
    
    /**
     * Get client information from DB.
     * @return client info
     */
    public List<MasterInvoice> getClientInfo()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String username = (String)session.getAttribute("username");
        
        List<Client> client = clientController.findClientByUsername(username);
        
        log.log(Level.INFO, "USERNAME OF CURRENT USER: "+username);
        
        List<MasterInvoice> masterInvoices = masterInvoiceController.getMasterInvoiceByClientId(client.get(0).getClientId());
        
        return masterInvoices;
    }
    
    /**
     * Get the book specified from the invoice id.
     * @param bookId
     * @return 
     */
    public Book getBookInvoice(int bookId)
    {
        Book book = bookController.findBook(bookId);
        
        return book;
    }
    
    /**
     * Return file when user clicks download button.
     * @return 
     */
    public StreamedContent getFile()
    {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/book//aliceWonderLand.pdf");
        file = new DefaultStreamedContent(stream, "file/pdf", "aliceWonderland.pdf");
        
        return file;
    }
    
    
    
}
