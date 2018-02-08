package com.g4w18.entities;

import com.g4w18.entities.Book;
import com.g4w18.entities.MasterInvoice;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-07T19:54:20")
@StaticMetamodel(InvoiceDetail.class)
public class InvoiceDetail_ { 

    public static volatile SingularAttribute<InvoiceDetail, BigDecimal> gstRate;
    public static volatile SingularAttribute<InvoiceDetail, BigDecimal> pstRate;
    public static volatile SingularAttribute<InvoiceDetail, BigDecimal> hstRate;
    public static volatile SingularAttribute<InvoiceDetail, Integer> detailId;
    public static volatile SingularAttribute<InvoiceDetail, MasterInvoice> invoiceId;
    public static volatile SingularAttribute<InvoiceDetail, BigDecimal> bookPrice;
    public static volatile SingularAttribute<InvoiceDetail, Book> bookId;

}