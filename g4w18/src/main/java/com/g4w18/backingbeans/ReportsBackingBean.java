/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ReportSelector;
import com.g4w18.customcontrollers.CustomMasterInvoiceJpaController;
import com.g4w18.customcontrollers.CustomQueries;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private ReportSelector reportSelector;

    @Inject
    private CustomMasterInvoiceJpaController masterJpaController;
    
    @Inject
    private CustomQueries customJpa;
    
    private List<BookWithTotalSales> booksWithTotalSales;
    
    public ReportSelector getReportSelector()
    {
        if(reportSelector == null)
            reportSelector = new ReportSelector();
        return reportSelector;
    }
    
    public String goToReportPage()
    {
        switch(reportSelector.getReportType())
        {
            case "Total Sales":
                return "totalSales";
            case "Sales by Client":
                return "totalSalesByClient";
            case "Sales by Author":
                return "totalSalesByAuthor";
            case "Sales by Publisher":
                return "totalSalesByPublisher";
            default:
                break;
        }
        
        return "";
    }
    
    public List<BookWithTotalSales> getBooksWithTotalSales()
    {
        if(booksWithTotalSales == null)
        {
            booksWithTotalSales = new ArrayList<>();
            
            List<MasterInvoice> masterInvoicesBetweenDates = masterJpaController.findMasterInvoiceEntities();

            for(MasterInvoice master : masterInvoicesBetweenDates)
            {
                for(InvoiceDetail invoice : master.getInvoiceDetailList())
                {
                    booksWithTotalSales.add(customJpa.findBookWithTotalSalesByDetail(invoice.getDetailId()));
                }
            }
        }
        
        return booksWithTotalSales;
    }
    
    private static Collection<SelectItem> reportOptions;
    static 
    {
        reportOptions = new ArrayList<>();
        reportOptions.add(new SelectItem("Total Sales"));
        reportOptions.add(new SelectItem("Sales by Client"));
        reportOptions.add(new SelectItem("Sales by Author"));
        reportOptions.add(new SelectItem("Sales by Publisher"));
    }
    
    public Collection<SelectItem> getReportOptions() 
    {
        return reportOptions;
    }
}
