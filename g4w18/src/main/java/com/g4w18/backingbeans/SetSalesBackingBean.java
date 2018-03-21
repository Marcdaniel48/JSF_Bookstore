package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * This class is responsible for backing a list of books on sale coming from the
 * database.
 *
 * @author Sebastian Ramirez
 */
@Named
@ViewScoped
public class SetSalesBackingBean implements Serializable {

    @Inject
    private CustomBookController customBookController;

    private List<Book> allBooks;
    private static final Logger LOGGER = Logger.getLogger(BookDetailsBackingBean.class.getName());

    /**
     * Getter method for booksNotOnSale If booksNotOnSale is null, it will
     * retrieve a list of book objects which are on sale.
     *
     * @return List of books on sale.
     */
    public List<Book> getAllBooks() {
        if (allBooks == null) {
            allBooks = customBookController.findBookEntities();
            LOGGER.log(Level.INFO, "allBooks size: {0}", allBooks.size());
        }
        return allBooks;
    }

    public String onRowEdit(RowEditEvent event) throws NonexistentEntityException, RollbackFailureException, Exception {
        Book editedBook = (Book) event.getObject();
        customBookController.edit(editedBook);
        return null;
    }

    public boolean filterBySalePrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        String valueText = (value == null) ? null : value.toString().trim();
        LOGGER.log(Level.INFO, "filter text {0}", filterText);
        LOGGER.log(Level.INFO, "value text {0}", valueText);
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }
        BigDecimal selectItem = (BigDecimal) filter;
        BigDecimal selectValue = (BigDecimal) value;
//        LOGGER.log(Level.INFO, "select item {0}", selectItem.toString());
//        LOGGER.log(Level.INFO, "select value {0}", selectValue.toString());
//        LOGGER.log(Level.INFO, "BOOLEAN: {0}", selectItem.intValue() == 0);
        if (selectItem.doubleValue() == 0) {
            return selectValue.doubleValue() == 0;
        } else {
            return selectValue.doubleValue() != 0;
        }
    }

}
