package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests shopping cart functionality, as well the payment information form when going through the
 * checkout process, using Selenium testing.
 * @author Marc-Daniel
 */
public class ShoppingCartTest 
{
    private WebDriver driver;
    
    @Before
    public void setUp() {
        // Normally an executable that matches the browser you are using must
        // be in the classpath. The webdrivermanager library by Boni Garcia
        // downloads the required driver and makes it available
        ChromeDriverManager.getInstance().setup();

        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
    }
    
    @Test
    public void testAddingBookToShoppingCart() throws Exception
    {
        // Goes to the book details page of the book with ID 1, in order to access the "Add to Cart" button for that book.
        driver.get("http://localhost:8080/g4w18/bookDetail.xhtml?id=1");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Book Details"));
        
        Thread.sleep(1500);
        // Add the book to the shopping cart.
        driver.findElement(By.id("add_to_cart_form:add_to_cart_button")).click();
        
        Thread.sleep(1500);
        // Once the book has been added to the shopping cart, go to the shopping cart page.
        driver.get("http://localhost:8080/g4w18/shopping_cart.xhtml");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        Thread.sleep(1500);
        // Gets all the elements within all divs with class 'book_title', assuming that the shopping cart isn't empty.
        List<WebElement> bookTitleElements = driver.findElements(By.className("book_title"));
        /*
            The element of bookTitleElements at index 0 should contain the title of the book that has just been added to the shopping cart.
            The book with ID 1 has the title "Harry Potter and the Philosopher's Stone".
        */
        wait.until(ExpectedConditions.textToBePresentInElement(bookTitleElements.get(0), "Harry Potter and the Philosopher's Stone"));
    }
    
    @Test
    public void testRemovingBookFromShoppingCart() throws Exception
    {
        // Goes to the book details page of the book with ID 1, in order to access the "Add to Cart" button for that book.
        driver.get("http://localhost:8080/g4w18/bookDetail.xhtml?id=1");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Book Details"));
        
        Thread.sleep(1500);
        driver.findElement(By.id("add_to_cart_form:add_to_cart_button")).click();
        
        Thread.sleep(1500);
        // Once the book has been added to the shopping cart, go to the shopping cart page.
        driver.get("http://localhost:8080/g4w18/shopping_cart.xhtml");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        Thread.sleep(1500);
        // Remove the added book from the shopping cart. Because there may possibly be multiple books in the shopping cart, make sure to click the remove button at index 0.
        driver.findElements(By.className("remove_from_cart_buttons")).get(0).findElement(By.tagName("button")).click();
        
        // Because the added book has been removed, there should be no elements with class name "book_title"
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("book_title"), 0));
    }
    
    /**
     * Once a test is complete, close the browser.
     */
    @After
    public void clean()
    {
        driver.quit();
    }
}
