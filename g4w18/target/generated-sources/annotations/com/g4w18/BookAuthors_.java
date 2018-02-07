package com.g4w18;

import com.g4w18.Authors;
import com.g4w18.Books;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-06T18:40:58")
@StaticMetamodel(BookAuthors.class)
public class BookAuthors_ { 

    public static volatile SingularAttribute<BookAuthors, Authors> authorId;
    public static volatile SingularAttribute<BookAuthors, Integer> bookAuthorId;
    public static volatile SingularAttribute<BookAuthors, Books> bookId;

}