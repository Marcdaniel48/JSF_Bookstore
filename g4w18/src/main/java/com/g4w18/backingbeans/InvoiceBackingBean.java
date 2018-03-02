package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.MasterInvoice;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
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
public class InvoiceBackingBean implements Serializable {

    @Inject
    private MasterInvoiceJpaController masterInvoiceJpaController;

    private MasterInvoice masterInvoice;
    private Email email;

    private int taxes;

    private Logger log = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public MasterInvoice getMasterInvoice() {
//        log.log(Level.INFO,"getMasterInvoice called");
        if (masterInvoice == null) {
            log.info("masterInvoice was null");
            masterInvoice = masterInvoiceJpaController.findMasterInvoice(1);
            log.log(Level.INFO, "{0}", masterInvoice.getInvoiceId());
        }
        return masterInvoice;
    }

    private void createEmail() throws IOException {
        email = Email.create().from("sramirezdawson2017@gmail.com")
                .to("booktopiag4w18@gmail.com")
                .subject("Hi").addHtml(viewAsHtml());
    }

    public String sendEmail() throws IOException {
//        log.log(Level.INFO, html);
//        log.log(Level.INFO,"sendMail called");
//        //log.debug("ADDRESS: "+this.userEmailAddress);
//        //log.debug("PASSWORD: "+this.userEmailAddress);
//        // Create am SMTP server object
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

//    Code provided by BalusC at 
//    https://stackoverflow.com/questions/16965229/is-there-a-way-to-get-the-generated-html-as-a-string-from-a-uicomponent-object
    private String viewAsHtml() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIComponent component = root.findComponent("printable");
        ResponseWriter originalWriter = context.getResponseWriter();
        StringWriter writer = new StringWriter();
        try {
            context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, "text/html", "UTF-8"));
            component.encodeAll(context);
        } finally {
            if (originalWriter != null) {
                context.setResponseWriter(originalWriter);
            }
        }
        return writer.toString();
    }

}
