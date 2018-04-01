package com.g4w18.customvalidators;

import com.g4w18.backingbeans.BookDetailsBackingBean;
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
 * This custom validator ensures that the date entered by either a client or the
 * manager is after the start of the Unix time epoch used by the database in
 * order to avoid Database exceptions and before the current date because for
 * the purposes of this project it does not make sense to enter a date in the
 * future.
 *
 * @author Sebastian Ramirez
 */
@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator {

    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * The overridden validate method is necessary for the implementation of the
     * Validator interface. It calls the rangeCheck method to ensure that the
     * value of the date entered is between the allowed values. If it is not, it
     * throws a ValidatorException and displays a message informing the manager
     * or user that the date entered is wrong.
     *
     * @param context The FacesContext object
     * @param component The component in which the value was entered
     * @param value The incoming value from the view
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOGGER.log(Level.INFO, "VALIDATOR CALLED");
        if (value == null) {
            LOGGER.log(Level.INFO, "value was null");
            return;
        }
        Date toCheck = (Date) value;
        if (rangeCheck(toCheck)) {
            LOGGER.log(Level.INFO, "rangeCheck true");
            FacesMessage message = com.g4w18.util.Messages.getMessage(
                    "com.g4w18.bundles.messages", "validationDateRange", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * The rangeCheck checks if a Date object is in incorrect range. Returns
     * true if it is incorrect range. Returns false if it is in the allowed
     * range.
     *
     * @param date
     * @return boolean if the date is in incompatible range.
     */
    private boolean rangeCheck(Date date) {
        Date earliestDate = new Date(0);
        Date currentDate = new Date();
        return date.before(earliestDate) || date.after(currentDate);
    }

}
