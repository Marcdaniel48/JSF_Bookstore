package com.g4w18;

import com.g4w18.MasterInvoices;
import com.g4w18.Reviews;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-06T18:40:58")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, String> country;
    public static volatile SingularAttribute<Users, String> address2;
    public static volatile SingularAttribute<Users, String> city;
    public static volatile CollectionAttribute<Users, MasterInvoices> masterInvoicesCollection;
    public static volatile SingularAttribute<Users, String> address1;
    public static volatile SingularAttribute<Users, String> companyName;
    public static volatile SingularAttribute<Users, String> postalCode;
    public static volatile SingularAttribute<Users, String> title;
    public static volatile CollectionAttribute<Users, Reviews> reviewsCollection;
    public static volatile SingularAttribute<Users, Integer> userId;
    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> province;
    public static volatile SingularAttribute<Users, String> cellphone;
    public static volatile SingularAttribute<Users, Boolean> isManager;
    public static volatile SingularAttribute<Users, String> homeTelephone;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> username;

}