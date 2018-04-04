package com.g4w18.custombeans;

import com.g4w18.entities.Client;
import java.io.Serializable;

/**
 * Bean used to represent a client, the total sales cost of his purchases, and his last recorded purchase date.
 * The client's total sales and last recorded purchase date are set and retrieved by extending the Total Sales bean.
 * 
 * @author Marc-Daniel
 */
public class ClientWithTotalSales extends TotalSalesBean implements Serializable
{
    private Client client;

    /**
     * Getter method. Returns the client.
     * @return client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Setter method. Sets the client.
     * @param client 
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
