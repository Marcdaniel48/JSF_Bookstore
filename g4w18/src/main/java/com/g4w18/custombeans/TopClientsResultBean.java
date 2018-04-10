package com.g4w18.custombeans;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Salman Haidar
 */
public class TopClientsResultBean {
    private String username;
    private BigDecimal grossValue;

    public TopClientsResultBean(String username, BigDecimal grossValue) {
        this.username = username;
        this.grossValue = grossValue.setScale(2, RoundingMode.CEILING);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }
}
