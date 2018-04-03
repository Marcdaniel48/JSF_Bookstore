package com.g4w18.custombeans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;

/**
 * Bean used in conjunction with the page and backing bean responsible for allowing the manager to request and view a report.
 * Holds the report type of the report that the manager would like to view, as well as the range of dates that the report is for.
 * Ex: The manager would like to view the total sales per publisher between the March 1st and April 2nd.
 * 
 * @author Marc-Daniel
 */
@ManagedBean
public class ReportSelector implements Serializable
{
    // Type of the requested report.
    private String reportType;
    
    // The range of dates that the requested report is for. 
    private Date firstDate;
    private Date secondDate;
    
    /**
     * Getter method. Returns the report type.
     * @return reportType
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Setter method. Sets the report type.
     * @param reportType 
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Getter method. Returns the start date for the report's date range.
     * @return 
     */
    public Date getFirstDate() {
        return firstDate;
    }

    /**
     * Setter method. Sets the start date for the report's date range.
     * @param firstDate 
     */
    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    /**
     * Getter method. Returns the end date for the report's date range.
     * @param secondDate 
     */
    public Date getSecondDate() {
        return secondDate;
    }

    /**
     * Setter method. Sets the end date for the report's date range.
     * @param secondDate 
     */
    public void setSecondDate(Date secondDate) {
        this.secondDate = secondDate;
    }
}
