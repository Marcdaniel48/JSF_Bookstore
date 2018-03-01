/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backing;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validates credit card numbers using the Luhn test
 * 
 * @author Marc-Daniel
 */
public class CreditCardValidator implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String cardNumber = (String) value;
        int sum = 0;

        for (int i = cardNumber.length() - 1; i >= 0; i -= 2) 
        {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
            
            if (i > 0) 
            {
                int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
                
                if (d > 9) 
                {
                    d -= 9;
                }
                
                sum += d;
            }
        }
        
        if (!(sum % 10 == 0)) 
        {
        /*    FaceMessage msg = com.g4w18.
            FacesMessage message = com.kfwebstandard.util.Messages.getMessage("com.kenfogel.bundles.messages", "badLuhnCheck", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);*/
        }
    }
    
}
