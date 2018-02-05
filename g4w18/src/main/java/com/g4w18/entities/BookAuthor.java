/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
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

/**
 *
 * @author Marc-Daniel
 */
@Entity
@Table(name = "book_author", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "BookAuthor.findAll", query = "SELECT b FROM BookAuthor b")
    , @NamedQuery(name = "BookAuthor.findByBookAuthorId", query = "SELECT b FROM BookAuthor b WHERE b.bookAuthorId = :bookAuthorId")})
public class BookAuthor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BOOK_AUTHOR_ID")
    private Integer bookAuthorId;
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")
    @ManyToOne(optional = false)
    private Book bookId;
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")
    @ManyToOne(optional = false)
    private Author authorId;

    public BookAuthor() {
    }

    public BookAuthor(Integer bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public Integer getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(Integer bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookAuthorId != null ? bookAuthorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookAuthor)) {
            return false;
        }
        BookAuthor other = (BookAuthor) object;
        if ((this.bookAuthorId == null && other.bookAuthorId != null) || (this.bookAuthorId != null && !this.bookAuthorId.equals(other.bookAuthorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.BookAuthor[ bookAuthorId=" + bookAuthorId + " ]";
    }
    
}
