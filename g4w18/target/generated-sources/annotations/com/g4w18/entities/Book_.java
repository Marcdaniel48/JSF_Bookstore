package com.g4w18.entities;

import com.g4w18.entities.BookAuthor;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.Review;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-14T06:16:49")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile ListAttribute<Book, Review> reviewList;
    public static volatile SingularAttribute<Book, Integer> pageNumber;
    public static volatile SingularAttribute<Book, String> isbnNumber;
    public static volatile SingularAttribute<Book, BigDecimal> salePrice;
    public static volatile SingularAttribute<Book, Date> inventoryDate;
    public static volatile ListAttribute<Book, InvoiceDetail> invoiceDetailList;
    public static volatile SingularAttribute<Book, String> format;
    public static volatile SingularAttribute<Book, String> description;
    public static volatile SingularAttribute<Book, String> title;
    public static volatile SingularAttribute<Book, Integer> bookId;
    public static volatile ListAttribute<Book, BookAuthor> bookAuthorList;
    public static volatile SingularAttribute<Book, String> genre;
    public static volatile SingularAttribute<Book, String> publisher;
    public static volatile SingularAttribute<Book, Date> publicationDate;
    public static volatile SingularAttribute<Book, BigDecimal> wholesalePrice;
    public static volatile SingularAttribute<Book, BigDecimal> listPrice;

}