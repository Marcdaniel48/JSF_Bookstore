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
@RequestScoped
public class OrderManagerBackingBean implements Serializable {

    @Inject
    private CustomMasterInvoiceController masterInvoiceController;

    private List<MasterInvoice> allMasterInvoices;
    private MasterInvoice selectedInvoice;
    private final FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    public List<MasterInvoice> getAllMasterInvoices() {
        if (allMasterInvoices == null) {
            allMasterInvoices = masterInvoiceController.findMasterInvoiceEntities();
        }
        return allMasterInvoices;
    }

    public MasterInvoice getSelectedInvoice() {
//        LOGGER.log(Level.INFO, "getDetails called");
//        for (InvoiceDetail id : details) {
//            LOGGER.log(Level.INFO, "detail: {0}", id.getDetailId());
//        }
        return selectedInvoice;
    }

    public void setSelectedInvoice(MasterInvoice selectedInvoice) {
        LOGGER.log(Level.INFO, "setSelectedInvoice called");
        this.selectedInvoice = selectedInvoice;
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
    public String onRowEditMasterInvoice(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
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
