package com.g4w18.entities;

import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Review;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-09T15:46:00")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile SingularAttribute<Client, String> country;
    public static volatile ListAttribute<Client, Review> reviewList;
    public static volatile SingularAttribute<Client, Integer> clientId;
    public static volatile SingularAttribute<Client, String> address2;
    public static volatile SingularAttribute<Client, String> city;
    public static volatile SingularAttribute<Client, String> address1;
    public static volatile SingularAttribute<Client, String> companyName;
    public static volatile SingularAttribute<Client, String> postalCode;
    public static volatile SingularAttribute<Client, String> title;
    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> password;
    public static volatile ListAttribute<Client, MasterInvoice> masterInvoiceList;
    public static volatile SingularAttribute<Client, String> province;
    public static volatile SingularAttribute<Client, String> cellphone;
    public static volatile SingularAttribute<Client, Boolean> isManager;
    public static volatile SingularAttribute<Client, String> homeTelephone;
    public static volatile SingularAttribute<Client, String> email;
    public static volatile SingularAttribute<Client, String> username;

}