package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Salman Haidar
 */
@Named("downloadx")
@RequestScoped
public class DownloadBackingBean implements Serializable {

    @Inject
    private CustomMasterInvoiceController masterInvoiceController;

    @Inject
    private CustomClientController clientController;

    @Inject
    private CustomBookController bookController;

    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());
    private StreamedContent file;

    //Display in the next page a message if there are no downloads to show.
    private String message = "";

    /**
     * Get client information from DB.
     *
     * @return client info
     */
    public List<MasterInvoice> getClientInfo() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String username = (String) session.getAttribute("username");

        Client client = clientController.findClientByUsername(username);

        log.log(Level.INFO, "USERNAME OF CURRENT USER: " + username);

        List<MasterInvoice> masterInvoices = client.getMasterInvoiceList();

        log.log(Level.INFO, "After masterInvoices Retrieved");

        if (masterInvoices.size() == 0) {
            message = "You have nothing to download, please start buying books!";
            return masterInvoices;
        } else {
            return masterInvoices;
        }

    }

    /**
     * Get the book specified from the invoice id.
     *
     * @param bookId
     * @return
     */
    public Book getBookInvoice(int bookId) {
        Book book = bookController.findBook(bookId);

        return book;
    }

    /**
     * Return file when user clicks download button.
     *
     * @return
     */
    public StreamedContent getFile() {
        return file;
    }

    @PostConstruct
    public void init() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/book/aliceWonderland.pdf");
        file = new DefaultStreamedContent(stream, "application/pdf", "aliceWonderland.pdf");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
