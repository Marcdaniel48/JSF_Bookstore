package backing;

import com.g4w18.controllers.CustomBookController;
import com.g4w18.entities.Book;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 * @source https://www.primefaces.org/showcase/ui/data/datatable/edit.xhtml
 */
@Named("inventoryBean")
@RequestScoped
public class InventoryBackingBean implements Serializable
{
    @Inject
    private CustomBookController bookController;
    
    public List<Book> getBooks()
    {
        return bookController.findBookEntities();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ""+((Book) event.getObject()).getBookId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}