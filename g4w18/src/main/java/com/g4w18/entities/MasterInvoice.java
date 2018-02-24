/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marc-Daniel
 */
@Entity
@Table(name = "master_invoice", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "MasterInvoice.findAll", query = "SELECT m FROM MasterInvoice m")
    , @NamedQuery(name = "MasterInvoice.findByInvoiceId", query = "SELECT m FROM MasterInvoice m WHERE m.invoiceId = :invoiceId")
    , @NamedQuery(name = "MasterInvoice.findBySaleDate", query = "SELECT m FROM MasterInvoice m WHERE m.saleDate = :saleDate")
    , @NamedQuery(name = "MasterInvoice.findByNetValue", query = "SELECT m FROM MasterInvoice m WHERE m.netValue = :netValue")
    , @NamedQuery(name = "MasterInvoice.findByGrossValue", query = "SELECT m FROM MasterInvoice m WHERE m.grossValue = :grossValue")})
public class MasterInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "INVOICE_ID")
    private Integer invoiceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "NET_VALUE")
    private BigDecimal netValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GROSS_VALUE")
    private BigDecimal grossValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private List<InvoiceDetail> invoiceDetailList;
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @ManyToOne(optional = false)
    private Client clientId;

    public MasterInvoice() {
    }

    public MasterInvoice(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public MasterInvoice(Integer invoiceId, Date saleDate, BigDecimal netValue, BigDecimal grossValue) {
        this.invoiceId = invoiceId;
        this.saleDate = saleDate;
        this.netValue = netValue;
        this.grossValue = grossValue;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public List<InvoiceDetail> getInvoiceDetailList() {
        return invoiceDetailList;
    }

    public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceId != null ? invoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterInvoice)) {
            return false;
        }
        MasterInvoice other = (MasterInvoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.MasterInvoice[ invoiceId=" + invoiceId + " ]";
    }

}