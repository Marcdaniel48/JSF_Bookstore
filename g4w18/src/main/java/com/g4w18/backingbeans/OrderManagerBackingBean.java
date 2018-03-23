package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Juan Sebastian Ramirez
 */

@Named
@RequestScoped
public class OrderManagerBackingBean implements Serializable{
    
    @Inject
    private CustomMasterInvoiceController masterInvoiceController;
    
    private List<MasterInvoice> allMasterInvoices;
    
    public List<MasterInvoice> getAllMasterInvoices(){
        if(allMasterInvoices == null){
            allMasterInvoices = masterInvoiceController.findMasterInvoiceEntities();
        }
        return allMasterInvoices;
    }
    
}
