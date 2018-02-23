/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
<<<<<<< HEAD:g4w18/src/main/java/com/g4w18/entities/Author.java
=======
import java.util.Collection;
>>>>>>> 0507d7671f35fa61c1c7b3313788726a5dce35e9:g4w18/src/main/java/com/g4w18/entities/Author.java
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

<<<<<<< HEAD:g4w18/src/main/java/com/g4w18/entities/Author.java
/**
 *
 * @author 1430047
 */
=======
>>>>>>> 0507d7671f35fa61c1c7b3313788726a5dce35e9:g4w18/src/main/java/com/g4w18/entities/Author.java
@Entity
@Table(name = "author", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
    , @NamedQuery(name = "Author.findByAuthorId", query = "SELECT a FROM Author a WHERE a.authorId = :authorId")
    , @NamedQuery(name = "Author.findByFirstName", query = "SELECT a FROM Author a WHERE a.firstName = :firstName")
    , @NamedQuery(name = "Author.findByLastName", query = "SELECT a FROM Author a WHERE a.lastName = :lastName")})
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AUTHOR_ID")
    private Integer authorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    @ManyToMany(mappedBy = "authorList")
    private List<Book> bookList;

    public Author() {
    }

    public Author(Integer authorId) {
        this.authorId = authorId;
    }

    public Author(Integer authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorId != null ? authorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.authorId == null && other.authorId != null) || (this.authorId != null && !this.authorId.equals(other.authorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.Author[ authorId=" + authorId + " ]";
    }

}
