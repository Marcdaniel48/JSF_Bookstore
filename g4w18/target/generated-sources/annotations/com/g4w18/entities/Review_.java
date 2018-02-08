package com.g4w18.entities;

import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-07T19:54:20")
@StaticMetamodel(Review.class)
public class Review_ { 

    public static volatile SingularAttribute<Review, Boolean> approvalStatus;
    public static volatile SingularAttribute<Review, Client> clientId;
    public static volatile SingularAttribute<Review, Date> reviewDate;
    public static volatile SingularAttribute<Review, String> review;
    public static volatile SingularAttribute<Review, Integer> rating;
    public static volatile SingularAttribute<Review, Integer> reviewId;
    public static volatile SingularAttribute<Review, Book> bookId;

}