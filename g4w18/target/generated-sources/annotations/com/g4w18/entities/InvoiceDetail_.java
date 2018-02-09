package com.g4w18.entities;

import com.g4w18.entities.Book;
import com.g4w18.entities.MasterInvoice;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-09T15:46:00")
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