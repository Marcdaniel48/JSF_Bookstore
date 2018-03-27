/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.custombeans.MasterBookInvoice;
import com.g4w18.custombeans.ReportSelector;
import com.g4w18.customcontrollers.CustomInvoiceDetailJpaController;
import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
import com.g4w18.customcontrollers.CustomQueries;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class ReportsBackingBean implements Serializable
{
    @Inject
    private ReportSelector reportSelector;
    
    @Inject
    private CustomInvoiceDetailJpaController invoiceJpaController;
    
    @Inject
    private CustomMasterInvoiceJpaController masterJpaController;
    
    @Inject
    private CustomQueries customJpa;

    public CustomQueries getCustomJpa() 
    {
        return customJpa;
    }

    public ReportSelector getReportSelector() 
    {
        return reportSelector;
    }
    
    public String goToReportPage()
    {
        switch(reportSelector.getReportType())
        {
            case "Total Sales":
                return "totalSales.xhtml";
            case "Sales by Client":
                break;
            case "Sales by Author":
                break;
            case "Sales by Publisher":
                break;
            default:
                break;
        }
        
        return "";
    }
    
    public List<MasterBookInvoice> getBooksWithTotalSales()
    {
        List<MasterBookInvoice> booksWithTotalSales = new ArrayList<>();
        
        List<MasterInvoice> masterInvoicesBetweenDates = masterJpaController
                .findMasterInvoicesBetweenDates(Date.from(LocalDate.of(2017, Month.MARCH, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.of(2019, Month.MARCH, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        for(MasterInvoice master : masterInvoicesBetweenDates)
        {
            for(InvoiceDetail invoice : master.getInvoiceDetailList())
            {
                booksWithTotalSales.add(customJpa.findBookWithTotalSalesByDetail(invoice.getDetailId()));
            }
        }
        
        return booksWithTotalSales;
    }
    
    private static Collection<SelectItem> reportOptions;
    public Collection<SelectItem> getReportOptions() {
        return reportOptions;
    }
    
    static 
    {
        reportOptions = new ArrayList<>();
        reportOptions.add(new SelectItem("Total Sales"));
        reportOptions.add(new SelectItem("Sales by Client"));
        reportOptions.add(new SelectItem("Sales by Author"));
        reportOptions.add(new SelectItem("Sales by Publisher"));
    }
}
