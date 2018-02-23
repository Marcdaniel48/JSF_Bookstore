package backing;

import com.g4w18.controllers.BookJpaController;
import com.g4w18.controllers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 *
 * @author 1430047
 */
@Named("theBooks")
@SessionScoped
public class BookBackingBean implements Serializable
{
    @Inject
    private CustomBookController bookController;

    /**
     * Get the list of books on sale
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
    
    /**
     * Get the list of genres
     *
     * @return
     */
    public List<String> getGenres() {

        return bookController.getGenres();
    }
}