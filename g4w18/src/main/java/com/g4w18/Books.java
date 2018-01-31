/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Marc-Daniel
 */
@Entity
@Table(name = "books", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b")
    , @NamedQuery(name = "Books.findByBookId", query = "SELECT b FROM Books b WHERE b.bookId = :bookId")
    , @NamedQuery(name = "Books.findByIsbnNumber", query = "SELECT b FROM Books b WHERE b.isbnNumber = :isbnNumber")
    , @NamedQuery(name = "Books.findByTitle", query = "SELECT b FROM Books b WHERE b.title = :title")
    , @NamedQuery(name = "Books.findByPublisher", query = "SELECT b FROM Books b WHERE b.publisher = :publisher")
    , @NamedQuery(name = "Books.findByPublicationDate", query = "SELECT b FROM Books b WHERE b.publicationDate = :publicationDate")
    , @NamedQuery(name = "Books.findByPageNumber", query = "SELECT b FROM Books b WHERE b.pageNumber = :pageNumber")
    , @NamedQuery(name = "Books.findByGenre", query = "SELECT b FROM Books b WHERE b.genre = :genre")
    , @NamedQuery(name = "Books.findByFormat", query = "SELECT b FROM Books b WHERE b.format = :format")
    , @NamedQuery(name = "Books.findByWholesalePrice", query = "SELECT b FROM Books b WHERE b.wholesalePrice = :wholesalePrice")
    , @NamedQuery(name = "Books.findByListPrice", query = "SELECT b FROM Books b WHERE b.listPrice = :listPrice")
    , @NamedQuery(name = "Books.findBySalePrice", query = "SELECT b FROM Books b WHERE b.salePrice = :salePrice")
    , @NamedQuery(name = "Books.findByInventoryDate", query = "SELECT b FROM Books b WHERE b.inventoryDate = :inventoryDate")})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BOOK_ID")
    private Integer bookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "ISBN_NUMBER")
    private String isbnNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PUBLISHER")
    private String publisher;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PUBLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAGE_NUMBER")
    private int pageNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "GENRE")
    private String genre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FORMAT")
    private String format;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "WHOLESALE_PRICE")
    private BigDecimal wholesalePrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIST_PRICE")
    private BigDecimal listPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALE_PRICE")
    private BigDecimal salePrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INVENTORY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inventoryDate;

    public Books() {
    }

    public Books(Integer bookId) {
        this.bookId = bookId;
    }

    public Books(Integer bookId, String isbnNumber, String title, String publisher, Date publicationDate, int pageNumber, String genre, String description, String format, BigDecimal wholesalePrice, BigDecimal listPrice, BigDecimal salePrice, Date inventoryDate) {
        this.bookId = bookId;
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.pageNumber = pageNumber;
        this.genre = genre;
        this.description = description;
        this.format = format;
        this.wholesalePrice = wholesalePrice;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.inventoryDate = inventoryDate;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.Books[ bookId=" + bookId + " ]";
    }
    
}
