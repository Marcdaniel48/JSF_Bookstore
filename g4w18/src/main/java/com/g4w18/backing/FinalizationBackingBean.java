/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marc-Daniel
 */
@Named("finalizationBacking")
@RequestScoped
public class FinalizationBackingBean implements Serializable
{
    private CreditCard creditCard;
    
    public CreditCard getCreditCard() {
        if (creditCard == null) {
            creditCard = new CreditCard();
        }
        return creditCard;
    }
    
    public Collection<SelectItem> getMonthOptions() {   
        return monthOptions;
    }
    
    public Collection<SelectItem> getYearOptions()
    {
        return yearOptions;
    }
    
    private static Collection<SelectItem> monthOptions;
    private static Collection<SelectItem> yearOptions;
    static
    {
        monthOptions = new ArrayList<>();
        yearOptions = new ArrayList<>();
        
        for(int i = 1; i < 13; i++)
        {
            monthOptions.add(new SelectItem(i));
        }
        
        LocalDateTime currentDate = LocalDateTime.now();
        for(int i = currentDate.getYear(); i < currentDate.getYear()+21; i++)
        {
            yearOptions.add(new SelectItem(i));
        }
    }
}
