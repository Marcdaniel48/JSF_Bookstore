package com.g4w18;

import com.g4w18.BookAuthors;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-01-30T15:05:03")
@StaticMetamodel(Authors.class)
public class Authors_ { 

    public static volatile SingularAttribute<Authors, String> firstName;
    public static volatile SingularAttribute<Authors, String> lastName;
    public static volatile CollectionAttribute<Authors, BookAuthors> bookAuthorsCollection;
    public static volatile SingularAttribute<Authors, Integer> authorId;

}