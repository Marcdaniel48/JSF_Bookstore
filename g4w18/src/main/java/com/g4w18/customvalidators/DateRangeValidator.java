/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.customvalidators;

import com.g4w18.backingbeans.BookDetailsBackingBean;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author 1331680
 */
@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator {

    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOGGER.log(Level.INFO, "VALIDATOR CALLED");
        if (value == null) {
            LOGGER.log(Level.INFO, "value was null");
            return;
        }
        if (value instanceof Date) {
            LOGGER.log(Level.INFO, "value was instance of LDT");
        }
        Date toCheck = (Date) value;
        if (rangeCheck(toCheck)) {
            LOGGER.log(Level.INFO, "rangeCheck true");
        }
        FacesMessage message = com.g4w18.util.Messages.getMessage(
                "com.g4w18.bundles.messages", "validationDateRange", null);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    private boolean rangeCheck(Date date) {
        Date earliestDate = new Date(0);
        Date currentDate = new Date();
        return 
    }

}
