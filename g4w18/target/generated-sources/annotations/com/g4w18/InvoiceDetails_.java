package com.g4w18;

import com.g4w18.Books;
import com.g4w18.MasterInvoices;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-07T17:54:07")
@StaticMetamodel(InvoiceDetails.class)
public class InvoiceDetails_ { 

    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> gstRate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> pstRate;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> hstRate;
    public static volatile SingularAttribute<InvoiceDetails, Integer> detailId;
    public static volatile SingularAttribute<InvoiceDetails, MasterInvoices> invoiceId;
    public static volatile SingularAttribute<InvoiceDetails, BigDecimal> bookPrice;
    public static volatile SingularAttribute<InvoiceDetails, Books> bookId;

}