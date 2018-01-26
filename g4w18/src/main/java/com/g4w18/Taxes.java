/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 1331680
 */
@Entity
@Table(name = "taxes", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Taxes.findAll", query = "SELECT t FROM Taxes t")
    , @NamedQuery(name = "Taxes.findByTaxId", query = "SELECT t FROM Taxes t WHERE t.taxId = :taxId")
    , @NamedQuery(name = "Taxes.findByProvince", query = "SELECT t FROM Taxes t WHERE t.province = :province")
    , @NamedQuery(name = "Taxes.findByGstRate", query = "SELECT t FROM Taxes t WHERE t.gstRate = :gstRate")
    , @NamedQuery(name = "Taxes.findByPstRate", query = "SELECT t FROM Taxes t WHERE t.pstRate = :pstRate")
    , @NamedQuery(name = "Taxes.findByHstRate", query = "SELECT t FROM Taxes t WHERE t.hstRate = :hstRate")})
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TAX_ID")
    private Integer taxId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PROVINCE")
    private String province;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "GST_RATE")
    private BigDecimal gstRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PST_RATE")
    private BigDecimal pstRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HST_RATE")
    private BigDecimal hstRate;

    public Taxes() {
    }

    public Taxes(Integer taxId) {
        this.taxId = taxId;
    }

    public Taxes(Integer taxId, String province, BigDecimal gstRate, BigDecimal pstRate, BigDecimal hstRate) {
        this.taxId = taxId;
        this.province = province;
        this.gstRate = gstRate;
        this.pstRate = pstRate;
        this.hstRate = hstRate;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigDecimal getGstRate() {
        return gstRate;
    }

    public void setGstRate(BigDecimal gstRate) {
        this.gstRate = gstRate;
    }

    public BigDecimal getPstRate() {
        return pstRate;
    }

    public void setPstRate(BigDecimal pstRate) {
        this.pstRate = pstRate;
    }

    public BigDecimal getHstRate() {
        return hstRate;
    }

    public void setHstRate(BigDecimal hstRate) {
        this.hstRate = hstRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxId != null ? taxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxes)) {
            return false;
        }
        Taxes other = (Taxes) object;
        if ((this.taxId == null && other.taxId != null) || (this.taxId != null && !this.taxId.equals(other.taxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.Taxes[ taxId=" + taxId + " ]";
    }
    
}
