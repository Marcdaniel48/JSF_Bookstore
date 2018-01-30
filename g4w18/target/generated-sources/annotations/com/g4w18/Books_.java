package com.g4w18;

import com.g4w18.BookAuthors;
import com.g4w18.InvoiceDetails;
import com.g4w18.Reviews;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T14:29:20")
@StaticMetamodel(Books.class)
public class Books_ { 

    public static volatile CollectionAttribute<Books, InvoiceDetails> invoiceDetailsCollection;
    public static volatile SingularAttribute<Books, Integer> pageNumber;
    public static volatile SingularAttribute<Books, String> isbnNumber;
    public static volatile SingularAttribute<Books, BigDecimal> salePrice;
    public static volatile SingularAttribute<Books, String> imagePath;
    public static volatile SingularAttribute<Books, Date> inventoryDate;
    public static volatile SingularAttribute<Books, String> format;
    public static volatile SingularAttribute<Books, String> description;
    public static volatile CollectionAttribute<Books, BookAuthors> bookAuthorsCollection;
    public static volatile SingularAttribute<Books, String> title;
    public static volatile CollectionAttribute<Books, Reviews> reviewsCollection;
    public static volatile SingularAttribute<Books, Integer> bookId;
    public static volatile SingularAttribute<Books, String> genre;
    public static volatile SingularAttribute<Books, String> publisher;
    public static volatile SingularAttribute<Books, Date> publicationDate;
    public static volatile SingularAttribute<Books, BigDecimal> wholesalePrice;
    public static volatile SingularAttribute<Books, BigDecimal> listPrice;

}