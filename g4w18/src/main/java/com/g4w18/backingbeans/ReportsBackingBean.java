package com.g4w18.backingbeans;

import com.g4w18.custombeans.AuthorWithTotalSales;
import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ClientWithTotalSales;
import com.g4w18.custombeans.PublisherWithTotalSales;
import com.g4w18.custombeans.ReportSelector;
import com.g4w18.custombeans.TotalSalesBean;
import com.g4w18.customcontrollers.ReportQueries;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * A backing bean that is used to interact with the pages and forms relating to the reports feature of the manager site.
 * 
 * @author Marc-Daniel
 */
@Named
@ViewScoped
public class ReportsBackingBean implements Serializable
{
    // Used to query the total sales by book, client, author, publisher.
    @Inject
    private ReportQueries reportQueries;
    
    // List of total sales beans which contain the information that the selected report will display.
    private List<BookWithTotalSales> booksWithTotalSales;
    private List<ClientWithTotalSales> clientsWithTotalSales;
    private List<AuthorWithTotalSales> authorsWithTotalSales;
    private List<PublisherWithTotalSales> publishersWithTotalSales;
    
    // Contains the type of report that the user wants to request, as well as the dates of the sales for the requested report.
    @Inject
    private ReportSelector reportSelector;
    
    // Provides report type options for the report type drop-down list, for when the user wishes to request a report.
    private static Collection<SelectItem> reportOptions;
    
    /**
     * Getter method. Returns a bean that contains the type of report + dates that the user wants to request.
     * @return 
     */
    public ReportSelector getReportSelector()
    {
        if(reportSelector == null)
            reportSelector = new ReportSelector();
        return reportSelector;
    }
    
    /**
     * Setter method for the report selector.
     * @param reportSelector 
     */
    public void setReportSelector(ReportSelector reportSelector)
    {
        this.reportSelector = reportSelector;
    }
    
    /**
     * Navigates to the appropriate report page, depending on the type of report that the user wants to see.
     * @return 
     */
    public String goToReportPage()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reportTypeAndDates", reportSelector);
        
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
    
    /**
     * Retrieves in a list, the total sales per book information between the dates that the user has specified.
     * @return 
     */
    public List<BookWithTotalSales> getBooksWithTotalSales()
    {
        if(reportSelector.getFirstDate() == null || reportSelector.getSecondDate() == null)
            reportSelector = (ReportSelector)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reportTypeAndDates");

        if(booksWithTotalSales == null)
        {
            booksWithTotalSales = reportQueries.findBooksWithTotalSalesBetweenDates(reportSelector.getFirstDate(), reportSelector.getSecondDate());
        }
        
        return booksWithTotalSales;
    }
    
    /**
     * Retrieves in a list, the total sales per client information between the dates that the user has specified.
     * @return 
     */
    public List<ClientWithTotalSales> getClientsWithTotalSales()
    {
        if(reportSelector.getFirstDate() == null || reportSelector.getSecondDate() == null)
            reportSelector = (ReportSelector)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reportTypeAndDates");

        if(clientsWithTotalSales == null)
        {
            clientsWithTotalSales = reportQueries.findClientsWithTotalSalesBetweenDates(reportSelector.getFirstDate(), reportSelector.getSecondDate());
        }
        
        return clientsWithTotalSales;
    }
    
    /**
     * Retrieves in a list, the total sales per author information between the dates that the user has specified.
     * @return 
     */
    public List<AuthorWithTotalSales> getAuthorsWithTotalSales()
    {
        if(reportSelector.getFirstDate() == null || reportSelector.getSecondDate() == null)
            reportSelector = (ReportSelector)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reportTypeAndDates");

        if(authorsWithTotalSales == null)
        {
            authorsWithTotalSales = reportQueries.findAuthorsWithTotalSalesBetweenDates(reportSelector.getFirstDate(), reportSelector.getSecondDate());
        }
        
        return authorsWithTotalSales;
    }
    
    /**
     * Retrieves in a list, the total sales per publisher information between the dates that the user has specified.
     * @return 
     */
    public List<PublisherWithTotalSales> getPublishersWithTotalSales()
    {
        if(reportSelector.getFirstDate() == null || reportSelector.getSecondDate() == null)
            reportSelector = (ReportSelector)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reportTypeAndDates");

        if(publishersWithTotalSales == null)
        {
            publishersWithTotalSales = reportQueries.findPublishersWithTotalSalesBetweenDates(reportSelector.getFirstDate(), reportSelector.getSecondDate());
        }
        
        return publishersWithTotalSales;
    }
    
    /**
     * Given a list of total sales beans, return the summed up total sales.
     * @return 
     */
    public BigDecimal getTotalSales(List<TotalSalesBean> totalSalesList)
    {
        BigDecimal totalSales = BigDecimal.ZERO;
        for(TotalSalesBean totalSalesBean : totalSalesList)
        {
            totalSales = totalSales.add(totalSalesBean.getTotalSalesRounded());
        }
        
        return totalSales;
    }
    
    /**
     * Getter method. Returns the report type options for the report type drop-down list.
     * @return 
     */
    public Collection<SelectItem> getReportOptions() 
    {
        return reportOptions;
    }

    // Contains the possible report types that the user may request.
    static 
    {
        reportOptions = new ArrayList<>();
        reportOptions.add(new SelectItem("Total Sales"));
        reportOptions.add(new SelectItem("Sales by Client"));
        reportOptions.add(new SelectItem("Sales by Author"));
        reportOptions.add(new SelectItem("Sales by Publisher"));
    }
}