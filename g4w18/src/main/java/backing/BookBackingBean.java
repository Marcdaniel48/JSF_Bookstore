package backing;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1430047
 */
@Named("theBooks")
@SessionScoped
public class BookBackingBean implements Serializable
{
    @Inject
    private BookJpaController bookController;

    /**
     * Get list of books on sale
     *
     * @return
     */
    public List<Book> getBooksOnSale() {

        return bookController.getBooksOnSale();
    }

    /**
     * Get 3 most recent books
     *
     * @return
     */
    public List<Book> getMostRecentBooks() {

        return bookController.getMostRecentBooks();
    }
}