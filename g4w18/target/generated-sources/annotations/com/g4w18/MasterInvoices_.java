package com.g4w18;

import com.g4w18.InvoiceDetails;
import com.g4w18.Users;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-09T15:20:14")
@StaticMetamodel(MasterInvoices.class)
public class MasterInvoices_ { 

    public static volatile CollectionAttribute<MasterInvoices, InvoiceDetails> invoiceDetailsCollection;
    public static volatile SingularAttribute<MasterInvoices, BigDecimal> netValue;
    public static volatile SingularAttribute<MasterInvoices, BigDecimal> grossValue;
    public static volatile SingularAttribute<MasterInvoices, Integer> invoiceId;
    public static volatile SingularAttribute<MasterInvoices, Date> saleDate;
    public static volatile SingularAttribute<MasterInvoices, Users> userId;

}