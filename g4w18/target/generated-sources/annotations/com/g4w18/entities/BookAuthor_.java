package com.g4w18.entities;

import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-07T19:54:20")
@StaticMetamodel(BookAuthor.class)
public class BookAuthor_ { 

    public static volatile SingularAttribute<BookAuthor, Author> authorId;
    public static volatile SingularAttribute<BookAuthor, Integer> bookAuthorId;
    public static volatile SingularAttribute<BookAuthor, Book> bookId;

}