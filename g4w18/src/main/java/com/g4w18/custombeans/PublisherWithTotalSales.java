package com.g4w18.custombeans;

/**
 * Bean used to represent a publisher, the total sales of the publisher's books, and the last recorded sale date for one of the publisher's books.
 * The publisher's total sales and last recorded sale date are set and retrieved by extending the Total Sales bean.
 * 
 * @author Marc-Daniel
 */
public class PublisherWithTotalSales extends TotalSalesBean
{
    private String publisher;

    /**
     * Getter method. Returns the publisher.
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Setter method. Sets the publisher.
     * @param publisher 
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
