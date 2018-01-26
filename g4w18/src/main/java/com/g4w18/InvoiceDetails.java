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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 1331680
 */
@Entity
@Table(name = "invoice_details", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "InvoiceDetails.findAll", query = "SELECT i FROM InvoiceDetails i")
    , @NamedQuery(name = "InvoiceDetails.findByDetailId", query = "SELECT i FROM InvoiceDetails i WHERE i.detailId = :detailId")
    , @NamedQuery(name = "InvoiceDetails.findByBookPrice", query = "SELECT i FROM InvoiceDetails i WHERE i.bookPrice = :bookPrice")
    , @NamedQuery(name = "InvoiceDetails.findByGstRate", query = "SELECT i FROM InvoiceDetails i WHERE i.gstRate = :gstRate")
    , @NamedQuery(name = "InvoiceDetails.findByPstRate", query = "SELECT i FROM InvoiceDetails i WHERE i.pstRate = :pstRate")
    , @NamedQuery(name = "InvoiceDetails.findByHstRate", query = "SELECT i FROM InvoiceDetails i WHERE i.hstRate = :hstRate")})
public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DETAIL_ID")
    private Integer detailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOK_PRICE")
    private BigDecimal bookPrice;
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
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID")
    @ManyToOne(optional = false)
    private MasterInvoices invoiceId;
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")
    @ManyToOne(optional = false)
    private Books bookId;

    public InvoiceDetails() {
    }

    public InvoiceDetails(Integer detailId) {
        this.detailId = detailId;
    }

    public InvoiceDetails(Integer detailId, BigDecimal bookPrice, BigDecimal gstRate, BigDecimal pstRate, BigDecimal hstRate) {
        this.detailId = detailId;
        this.bookPrice = bookPrice;
        this.gstRate = gstRate;
        this.pstRate = pstRate;
        this.hstRate = hstRate;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
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

    public MasterInvoices getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(MasterInvoices invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Books getBookId() {
        return bookId;
    }

    public void setBookId(Books bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceDetails)) {
            return false;
        }
        InvoiceDetails other = (InvoiceDetails) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.InvoiceDetails[ detailId=" + detailId + " ]";
    }
    
}
