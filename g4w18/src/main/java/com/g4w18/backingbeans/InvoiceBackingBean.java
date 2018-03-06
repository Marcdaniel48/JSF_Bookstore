package com.g4w18.backingbeans;

import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.entities.MasterInvoice;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
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

    private Email createEmail() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
        String subject = bundle.getString("invoiceEmailSubject");
        log.log(Level.INFO, "Subject: {0}", subject);
        log.log(Level.INFO, "Destinatary: {0}", masterInvoice.getClientId().getEmail());
        return Email.create().from("sramirezdawson2017@gmail.com")
                .to(masterInvoice.getClientId().getEmail())
                .subject(subject).addHtml(viewAsHtml());
    }

    public String sendEmail() throws IOException {
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create("smtp.gmail.com")
                .authenticateWith("sramirezdawson2017@gmail.com",
                        "catsLoveFood");
        smtpServer.debug(false);
        try (SendMailSession session = smtpServer.createSession()) {
            session.open();
            createEmail();
//            session.sendMail(email);
        }
        return null;
    }

//    part of the code provided by BalusC at:
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
//        log.log(Level.INFO, "HTML: {0}", writer.toString());
        return writer.toString();
    }

}
