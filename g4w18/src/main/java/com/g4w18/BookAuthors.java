/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18;

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
 * @author 1331680
 */
@Entity
@Table(name = "book_authors", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "BookAuthors.findAll", query = "SELECT b FROM BookAuthors b")
    , @NamedQuery(name = "BookAuthors.findByBookAuthorId", query = "SELECT b FROM BookAuthors b WHERE b.bookAuthorId = :bookAuthorId")})
public class BookAuthors implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BOOK_AUTHOR_ID")
    private Integer bookAuthorId;
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")
    @ManyToOne(optional = false)
    private Books bookId;
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")
    @ManyToOne(optional = false)
    private Authors authorId;

    public BookAuthors() {
    }

    public BookAuthors(Integer bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public Integer getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(Integer bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public Books getBookId() {
        return bookId;
    }

    public void setBookId(Books bookId) {
        this.bookId = bookId;
    }

    public Authors getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Authors authorId) {
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
        if (!(object instanceof BookAuthors)) {
            return false;
        }
        BookAuthors other = (BookAuthors) object;
        if ((this.bookAuthorId == null && other.bookAuthorId != null) || (this.bookAuthorId != null && !this.bookAuthorId.equals(other.bookAuthorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.BookAuthors[ bookAuthorId=" + bookAuthorId + " ]";
    }
    
}
