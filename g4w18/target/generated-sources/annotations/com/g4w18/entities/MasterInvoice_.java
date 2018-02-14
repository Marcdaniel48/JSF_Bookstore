package com.g4w18.entities;

import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-14T06:16:49")
@StaticMetamodel(MasterInvoice.class)
public class MasterInvoice_ { 

    public static volatile SingularAttribute<MasterInvoice, Client> clientId;
    public static volatile SingularAttribute<MasterInvoice, BigDecimal> netValue;
    public static volatile SingularAttribute<MasterInvoice, BigDecimal> grossValue;
    public static volatile ListAttribute<MasterInvoice, InvoiceDetail> invoiceDetailList;
    public static volatile SingularAttribute<MasterInvoice, Integer> invoiceId;
    public static volatile SingularAttribute<MasterInvoice, Date> saleDate;

}