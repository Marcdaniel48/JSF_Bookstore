package com.g4w18.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-02-07T21:37:13")
@StaticMetamodel(Tax.class)
public class Tax_ { 

    public static volatile SingularAttribute<Tax, BigDecimal> gstRate;
    public static volatile SingularAttribute<Tax, BigDecimal> pstRate;
    public static volatile SingularAttribute<Tax, String> province;
    public static volatile SingularAttribute<Tax, Integer> taxId;
    public static volatile SingularAttribute<Tax, BigDecimal> hstRate;

}