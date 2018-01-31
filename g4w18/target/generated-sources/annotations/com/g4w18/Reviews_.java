package com.g4w18;

import com.g4w18.Books;
import com.g4w18.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-01-30T15:05:03")
@StaticMetamodel(Reviews.class)
public class Reviews_ { 

    public static volatile SingularAttribute<Reviews, Boolean> approvalStatus;
    public static volatile SingularAttribute<Reviews, Date> reviewDate;
    public static volatile SingularAttribute<Reviews, String> review;
    public static volatile SingularAttribute<Reviews, Integer> rating;
    public static volatile SingularAttribute<Reviews, Integer> reviewId;
    public static volatile SingularAttribute<Reviews, Users> userId;
    public static volatile SingularAttribute<Reviews, Books> bookId;

}