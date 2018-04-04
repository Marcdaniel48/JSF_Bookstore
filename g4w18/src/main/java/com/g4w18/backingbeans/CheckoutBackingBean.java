package com.g4w18.backingbeans;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.InvoiceDetailJpaController;
import com.g4w18.controllers.MasterInvoiceJpaController;
import com.g4w18.controllers.TaxJpaController;
import com.g4w18.custombeans.CreditCard;
import com.g4w18.customcontrollers.ShoppingCart;
import com.g4w18.entities.Book;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import com.g4w18.entities.Tax;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * A backing bean that is used to interact with the pages and forms that handle the checkout process of the bookstore.
 * 
 * @author Marc-Daniel
 */
@Named("checkoutBacking")
@RequestScoped
public class CheckoutBackingBean implements Serializable
{
    // Contains the entered credit card information of the user in the payment information page.
    private CreditCard creditCard;
    
    // The entered shipping address of the user in the payment information page.
    private String shippingAddress;
    
    // Provides the month and year options of the credit card expiration drop-down lists.
    private static Collection<SelectItem> monthOptions;
    private static Collection<SelectItem> yearOptions;
    
    // Used to get the Client object of the currently logged-in user.
    @Inject private ClientJpaController clientJpaController;
    // Used to get the current user's tax values according to his current province.
    @Inject private TaxJpaController taxJpaController;
    // Used to create invoice details when the user places his order.
    @Inject private InvoiceDetailJpaController invoiceJpaController;
    // Used to create a master invoice when the user places his order.
    @Inject private MasterInvoiceJpaController masterInvoiceJpaController;
    // Used to reset the user's shopping cart when the user places his order.
    @Inject private ShoppingCart shoppingCart;
    
    /**
     * Getter method. Returns shipping address.
     * @return shippingAddress
     */
    public String getShippingAddress()
    {
        return shippingAddress;
    }
    
    /**
     * Setter method. Sets shipping address.
     * @param shippingAddress 
     */
    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }
    
    /**
     * Getter method. Returns credit card.
     * @return creditCard
     */
    public CreditCard getCreditCard() 
    {
        if (creditCard == null) {
            creditCard = new CreditCard();
        }
        return creditCard;
    }
    
    /**
     * Getter method. Returns credit card expiration month drop-down list options.
     * @return 
     */
    public Collection<SelectItem> getMonthOptions()
    {   
        return monthOptions;
    }
    
    /**
     * Getter method. Returns credit card expiration year drop-down list options.
     * @return 
     */
    public Collection<SelectItem> getYearOptions()
    {
        return yearOptions;
    }
    
    /**
     * Returns a Client object containing the information of the currently logged-in user.
     * @return 
     */
    public Client getCurrentClient()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String currentUsername = (String) (session.getAttribute("username"));
        
        return clientJpaController.findClientByUsername(currentUsername);
    }
    
    /**
     * Returns the overall tax rate (GST+PST+HST) of the currently logged-in user.
     * @return 
     */
    public double getTaxRate()
    {
        Client user = getCurrentClient();
        
        Tax tax = taxJpaController.findTaxByProvince(user.getProvince());
        
        return tax.getOverallTaxRate().doubleValue();
    }
    
    /**
     * Returns the last 4 characters of a String.
     * It's purpose in regards to the checkout process is to display the last 4 characters of the entered credit card number of the user.
     * @param creditCardNumber
     * @return 
     */
    public String last4Characters(String creditCardNumber)
    {
        return creditCardNumber.substring(creditCardNumber.length() - 4);
    }
    
    /**
     * Takes in a String (that can be casted into a double), casts it into a double, and rounds the double value.
     * It's purpose in regards to the checkout process is to round a price.
     * @param price
     * @return 
     */
    public double roundDouble(String price)
    {
        double roundedPrice = Double.valueOf(price);
        return Math.round(roundedPrice * 100.) / 100.;
    }
    
    /**
     * When the user is ready to play his order, a master invoice for the order is created and inserted into the MasterInvoice table.
     * An InvoiceDetail record is also inserted into its own table, for each book that has been purchased by the user.
     * Once the invoices have been made, empty the client's shopping cart.
     * 
     * @return
     * @throws Exception 
     */
    public String createInvoice() throws Exception
    {
        // Retrieves the current user's client information, then uses that information to retrieve the user's tax percentages.
        Client user = getCurrentClient();
        Tax tax = taxJpaController.findTaxByProvince(user.getProvince());
        
        List<Book> books = shoppingCart.getShoppingCartBooks();
        
        // Creates a Master Invoice with the current users ID, the current date and time, and gross value of the books that the user wants to purchase.
        MasterInvoice master = new MasterInvoice();
        master.setClientId(user);
        master.setSaleDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        master.setGrossValue(new BigDecimal(shoppingCart.getSubtotal()));
        
        // Calculates the net value of the user's purchase, then sets that value into the newly created MasterInvoice object.
        Double taxIncludedPercentage = tax.getOverallTaxRate().add(BigDecimal.ONE).doubleValue();
        double net = Math.round((shoppingCart.getSubtotal() * taxIncludedPercentage) * 100.) / 100.;
        master.setNetValue(new BigDecimal(net));
        
        // Inserts the information stored in the MasterInvoice object and inserts it into the appropriate table of the bookstore database.
        masterInvoiceJpaController.create(master);
        
        // For each book, create an InvoiceDetail object with the appropriate information, then insert it into the InvoiceDetail table of the bookstore database.
        for(Book book : books)
        {
            InvoiceDetail invoice = new InvoiceDetail();
            
            invoice.setInvoiceId(master);
            invoice.setBookId(book);
            
            if(book.getSalePrice().doubleValue() > 0)
                invoice.setBookPrice(book.getSalePrice());
            else
                invoice.setBookPrice(book.getListPrice());
            
            invoice.setGstRate(tax.getGstRate());
            invoice.setHstRate(tax.getHstRate());
            invoice.setPstRate(tax.getPstRate());
            
            invoiceJpaController.create(invoice);
        }
        
        // Once the purchase has been complete, empty the user's shopping cart
        shoppingCart.emptyShoppingCart();
        
        return "/index";
    }
    
    // Contains the possible values of the user's credit card's expiration month and year.
    static
    {
        monthOptions = new ArrayList<>();
        yearOptions = new ArrayList<>();
        
        // 1 to 12, because there are 12 months in a year.
        for(int i = 1; i < 13; i++)
        {
            monthOptions.add(new SelectItem(i));
        }
        
        // The user can select an expiration year of up to 21 years from the current year.
        LocalDateTime currentDate = LocalDateTime.now();
        for(int i = currentDate.getYear(); i < currentDate.getYear()+21; i++)
        {
            yearOptions.add(new SelectItem(i));
        }
    }
}
