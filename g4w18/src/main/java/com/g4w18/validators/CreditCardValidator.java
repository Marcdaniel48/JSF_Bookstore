/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validates credit card numbers using the Luhn test
 * 
 * @author Marc-Daniel
 */
@FacesValidator("creditCardValidator")
public class CreditCardValidator implements Validator
{

    /**
     * Checks to see if an entered credit card number is actually a valid credit card number.
     * If the entered credit card number is invalid, then throw a ValidatorException with an appropriate message.
     * 
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String cardNumber = (String) value;
        int sum = 0;
        
        if(!cardNumber.matches("^[0-9]+$"))
        {
            FacesMessage msg = com.g4w18.util.Messages.getMessage("com.g4w18.bundles.messages", "emptyCreditCard", null);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

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
            FacesMessage msg = com.g4w18.util.Messages.getMessage("com.g4w18.bundles.messages", "invalidCreditCard", null);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
