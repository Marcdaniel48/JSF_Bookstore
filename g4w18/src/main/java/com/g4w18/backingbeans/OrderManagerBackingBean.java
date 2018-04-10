package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.customcontrollers.CustomInvoiceDetailController;
import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.entities.Book;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.util.Messages;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * Backing bean in charge of the functionality of the orders.xhtml page in the
 * management part of the site.
 *
 * @author Sebastian Ramirez
 */
@Named
@ViewScoped
public class OrderManagerBackingBean implements Serializable {

    @Inject
    private CustomMasterInvoiceController masterInvoiceController;
    @Inject
    private CustomInvoiceDetailController invoiceDetailController;
    @Inject
    private CustomBookController bookController;

    private List<MasterInvoice> allMasterInvoices;
    private MasterInvoice selectedInvoice;
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method for the allMasterInvoices variable. If the variable is
     * null, it will retrieve all the Master Invoice records from the database.
     *
     * @return
     */
    public List<MasterInvoice> getAllMasterInvoices() {
        if (allMasterInvoices == null) {
            allMasterInvoices = masterInvoiceController.findMasterInvoiceEntities();
        }
        return allMasterInvoices;
    }

    /**
     * Getter method for the selectedInvoice class variable.
     *
     * @return
     */
    public MasterInvoice getSelectedInvoice() {
//        LOGGER.log(Level.INFO, "getDetails called");
//        for (InvoiceDetail id : details) {
//            LOGGER.log(Level.INFO, "detail: {0}", id.getDetailId());
//        }
        return selectedInvoice;
    }

    /**
     * Setter method for the selectedInvoice class variable.
     *
     * @param selectedInvoice
     */
    public void setSelectedInvoice(MasterInvoice selectedInvoice) {
        LOGGER.log(Level.INFO, "setSelectedInvoice called");
        this.selectedInvoice = selectedInvoice;
    }

    /**
     * The onRowEditMasterInvoice method updates any record edited in the
     * datatable by updating the values of the entry in the database.
     *
     * @param event
     * @return
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public String onRowEditMasterInvoice(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        LOGGER.log(Level.INFO, "onRowEditMasterInvoice called");
        MasterInvoice editedMasterInvoice = (MasterInvoice) event.getObject();
        setInvoiceDetailsStatus(editedMasterInvoice);
        masterInvoiceController.edit(editedMasterInvoice);
        Messages.addMessage("managerRSSEdit");
        return null;
    }
    
    /**
     * The setInvoiceDetails
     * @param masterInvoice
     * @throws Exception 
     */
    private void setInvoiceDetailsStatus(MasterInvoice masterInvoice) throws Exception {
        if (!masterInvoice.getAvailable()) {
            for (InvoiceDetail invoiceDetail : masterInvoice.getInvoiceDetailList()) {
                invoiceDetail.setAvailable(masterInvoice.getAvailable());
                invoiceDetailController.edit(invoiceDetail);
            }
        }
    }

    public String onRowEditInvoiceDetail(RowEditEvent event) throws Exception {
        LOGGER.log(Level.INFO, "onRowEditInvoiceDetail called");
        InvoiceDetail editedDetail = (InvoiceDetail) event.getObject();
        Book newBook = bookController.findUniqueBookByISBN(editedDetail.getBookId().getIsbnNumber());
        editedDetail.setBookId(newBook);
        invoiceDetailController.edit(editedDetail);
        Messages.addMessage("managerRSSEdit");
        return null;
    }

}
