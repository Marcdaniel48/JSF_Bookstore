package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Juan Sebastian Ramirez
 */
@Named
@ViewScoped
public class OrderManagerBackingBean implements Serializable {

    @Inject
    private CustomMasterInvoiceController masterInvoiceController;

    private List<MasterInvoice> allMasterInvoices;
    private MasterInvoice masterInvoice;
    private List<InvoiceDetail> details;
    private final FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public List<MasterInvoice> getAllMasterInvoices() {
        if (allMasterInvoices == null) {
            allMasterInvoices = masterInvoiceController.findMasterInvoiceEntities();
        }
        return allMasterInvoices;
    }

    public MasterInvoice getMasterInvoice() {

        return masterInvoice;
    }

    /**
     * Setter method for the toDelete variable. It is needed for the
     * setPropertyActionListener tag in the view to pass the attribute to be
     * handled to the backing bean.
     *
     * @param masterInvoice The RSS entry to be deleted.
     */
    public void setMasterInvoice(MasterInvoice masterInvoice) {
        LOGGER.log(Level.INFO, "masterInvoice setter called");
        LOGGER.log(Level.INFO, "masterInvoice ID {0}", masterInvoice.getInvoiceId());
        this.masterInvoice = masterInvoice;
    }

    public List<InvoiceDetail> getDetails() {
//        LOGGER.log(Level.INFO, "getDetails called");
//        for (InvoiceDetail id : details) {
//            LOGGER.log(Level.INFO, "detail: {0}", id.getDetailId());
//        }
        if(details == null){
            LOGGER.log(Level.INFO, "mf details is null");
        }
        return details;
    }

    public void setDetails(List<InvoiceDetail> invoiceDetails) {
        LOGGER.log(Level.INFO, "setDetails called");
        for (InvoiceDetail id : invoiceDetails) {
            LOGGER.log(Level.INFO, "incoming details: {0}", id.getDetailId());
        }
        this.details = invoiceDetails;
        for (InvoiceDetail id : details) {
            LOGGER.log(Level.INFO, "set details: {0}", id.getDetailId());
        }
    }

    /**
     * The onRowEdit method updates any record edited in the datatable by
     * updating the values of the entry in the database.
     *
     * @param event
     * @return
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public String onRowEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        MasterInvoice editedMasterInvoice = (MasterInvoice) event.getObject();
        masterInvoiceController.edit(editedMasterInvoice);
        addMessage("managerRSSEdit");
        return null;
    }

    /**
     * The addMessage method simplifies and reduces redundancy of code when
     * displaying a message is necessary.
     *
     * @param key The string key in the messages bundle.
     */
    private void addMessage(String key) {
        String message = context.getApplication().getResourceBundle(context, "msgs").getString(key);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        context.addMessage(null, msg);
    }

}
