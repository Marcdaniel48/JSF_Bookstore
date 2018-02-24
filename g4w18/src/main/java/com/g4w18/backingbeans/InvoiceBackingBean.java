package com.g4w18.backingbeans;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 1331680
 */
@Named
@RequestScoped
public class InvoiceBackingBean {

    @Inject
    private MasterInvoiceJpaController masterInvoiceJpaController;
    @Inject
    private ClientJpaController clientJpaController;

    private MasterInvoice masterInvoice;
    private Client client;
    private List<Book> purchasedBooks;
    private int taxes;

    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public MasterInvoice getMasterInvoice() {
//        log.log(Level.INFO,"getMasterInvoice called");
        if (masterInvoice == null) {
//            log.info("masterInvoice null");
            masterInvoice = masterInvoiceJpaController.findMasterInvoice(1);
        }
        return masterInvoice;
    }

    public Client getClient() {
        log.log(Level.INFO, "getClient called");
        if (client == null) {
            client = findClient();
        }
        return client;
    }

    public List<Book> getPurchasedBooks() {
        if (purchasedBooks == null) {
            purchasedBooks = new ArrayList<>();
            getClientBooks();
            return purchasedBooks;
        }
        return purchasedBooks;
    }

    private Client findClient() {
        log.log(Level.INFO, "findClient called");
//        String username = (String) FacesContext.getCurrentInstance()
//                .getExternalContext().getSessionMap().get("username");
//        return clientJpaController.findClientByUsername(username).get(0x0);
        return clientJpaController.findClient(10);
    }
    
    private void getClientBooks(){
        masterInvoice.getInvoiceDetailList().stream()
                .forEach(item->purchasedBooks.add(item.getBookId()));
    }
}
