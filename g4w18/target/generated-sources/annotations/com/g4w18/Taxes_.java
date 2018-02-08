package com.g4w18;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-07T17:54:06")
@StaticMetamodel(Taxes.class)
public class Taxes_ { 

    public static volatile SingularAttribute<Taxes, BigDecimal> gstRate;
    public static volatile SingularAttribute<Taxes, BigDecimal> pstRate;
    public static volatile SingularAttribute<Taxes, String> province;
    public static volatile SingularAttribute<Taxes, Integer> taxId;
    public static volatile SingularAttribute<Taxes, BigDecimal> hstRate;

}