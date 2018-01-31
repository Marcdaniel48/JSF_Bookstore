package com.g4w18;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-01-30T15:05:03")
@StaticMetamodel(Books.class)
public class Books_ { 

    public static volatile SingularAttribute<Books, Integer> pageNumber;
    public static volatile SingularAttribute<Books, String> isbnNumber;
    public static volatile SingularAttribute<Books, BigDecimal> salePrice;
    public static volatile SingularAttribute<Books, Date> inventoryDate;
    public static volatile SingularAttribute<Books, String> format;
    public static volatile SingularAttribute<Books, String> description;
    public static volatile SingularAttribute<Books, String> title;
    public static volatile SingularAttribute<Books, Integer> bookId;
    public static volatile SingularAttribute<Books, String> genre;
    public static volatile SingularAttribute<Books, String> publisher;
    public static volatile SingularAttribute<Books, Date> publicationDate;
    public static volatile SingularAttribute<Books, BigDecimal> wholesalePrice;
    public static volatile SingularAttribute<Books, BigDecimal> listPrice;

}