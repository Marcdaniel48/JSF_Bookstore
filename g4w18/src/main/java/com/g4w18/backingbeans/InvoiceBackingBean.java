package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 *
 * @author 1331680
 */
@Named
@ViewScoped
public class InvoiceBackingBean implements Serializable{

    @Inject
    private MasterInvoiceJpaController masterInvoiceJpaController;
    @Inject
    private ClientJpaController clientJpaController;

    private MasterInvoice masterInvoice;
    private Client client;
    private List<Book> purchasedBooks;
    private Email email;

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
        //log.log(Level.INFO, "getClient called");
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

    private void getClientBooks() {
        masterInvoice.getInvoiceDetailList().stream()
                .forEach(item -> purchasedBooks.add(item.getBookId()));
    }

    private void createEmail() {
        email = Email.create().from("sramirezdawson2017@gmail.com")
                .to("booktopiag4w18@gmail.com")
                .subject("Hi").addText("Hello world");
    }

    public String sendEmail() {
        log.log(Level.INFO,"sendMail called");
        //log.debug("ADDRESS: "+this.userEmailAddress);
        //log.debug("PASSWORD: "+this.userEmailAddress);
        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create("smtp.gmail.com")
                .authenticateWith("sramirezdawson2017@gmail.com",
                        "catsLoveFood");

        // Display Java Mail debug conversation with the server
        smtpServer.debug(false);
        // Like a file we open the session, send the message and close the
        // session
        try ( // A session is the object responsible for communicating with the server
                SendMailSession session = smtpServer.createSession()) {
            // Like a file we open the session, send the message and close the
            // session
            session.open();
            createEmail();
            session.sendMail(email);
        }
        return null;
    }

}
