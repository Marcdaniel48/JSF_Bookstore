/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.custombeans;

import com.g4w18.entities.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Marc-Daniel
 */
public class ClientWithTotalSales implements Serializable
{
    private Client client;
    private Timestamp lastBoughtDate;
    private BigDecimal totalSales;
}
