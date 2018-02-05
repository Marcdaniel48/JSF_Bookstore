package com.g4w18.entities;

import com.g4w18.entities.BookAuthor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-04T20:12:06")
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile SingularAttribute<Author, String> firstName;
    public static volatile SingularAttribute<Author, String> lastName;
    public static volatile ListAttribute<Author, BookAuthor> bookAuthorList;
    public static volatile SingularAttribute<Author, Integer> authorId;

}