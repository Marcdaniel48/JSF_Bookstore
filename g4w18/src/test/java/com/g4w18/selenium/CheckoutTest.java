package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the checkout process with Selenium.
 * @author Marc-Daniel
 */
public class CheckoutTest 
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
    
    /**
     * Tests the checkout page where the user has to enter his payment information, as well as a shipping address.
     * The entered payment information will be valid, so the user should be able to proceed to the Place Order page.
     * 
     * @throws Exception 
     */
    @Test
    public void testPaymentInformationWithValidCreditCard() throws Exception
    {
         // To proceed to checkout, the user must first login. Selenium testing for the login page is done in LoginTest.java
        driver.get("http://localhost:8080/g4w18/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Sign into your account"));  
        
        WebElement usernameInputElement = driver.findElement(By.id("login_form:username"));
        usernameInputElement.clear();
        usernameInputElement.sendKeys("mdaniels");
        Thread.sleep(1500);
        
        WebElement passwordInputElement = driver.findElement(By.id("login_form:password"));
        passwordInputElement.clear();
        passwordInputElement.sendKeys("booktopia");
        Thread.sleep(1500);
        
        driver.findElement(By.id("login_form:login_button")).click();
        Thread.sleep(1500);
        
        /* 
            To proceed to checkout, the user must also not have an empty shopping cart.
            Goes to the book details page of the book with ID 1, and adds it into the shopping cart.
        */
        driver.get("http://localhost:8080/g4w18/bookDetail.xhtml?id=1");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Book Details"));
        Thread.sleep(1500);
        
        driver.findElement(By.id("add_to_cart_form:add_to_cart_button")).click();
        Thread.sleep(1500);
        
        // Goes to the shopping cart page, then clicks on the "Proceed to checkout" button, in order to navigate to the page in which the user has to enter his payment information.
        driver.get("http://localhost:8080/g4w18/shopping_cart.xhtml");
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("shopping_cart_form:checkoutButton")).click();
        Thread.sleep(1500);
        
        // The user should now be in the checkout page responsible for allowing the user to enter his payment information.
        wait.until(ExpectedConditions.titleIs("Enter payment information"));
        
        // The user must now enter a credit card name, a valid credit card number, an expiration month, an expiration year, and a shipping address.
        WebElement cardNameInputElement = driver.findElement(By.id("input_payment_information_form:card_name"));
        cardNameInputElement.clear();
        cardNameInputElement.sendKeys("Bob");
        Thread.sleep(1500);
        
        WebElement cardNumberInputElement = driver.findElement(By.id("input_payment_information_form:card_number"));
        cardNumberInputElement.clear();
        // A generated Visa card number
        cardNumberInputElement.sendKeys("4916636438513620");
        Thread.sleep(1500);
        
        WebElement expirationMonthSelectElement = driver.findElement(By.id("payment_information_form:expiration_monthInner"));
        Select expirationMonthDropdownList = new Select(expirationMonthSelectElement);
        // In the expiration month drop-down list, there should be 12 as an option. Select that month.
        expirationMonthDropdownList.selectByVisibleText("12");
        Thread.sleep(1500);
        
        WebElement expirationYearSelectElement = driver.findElement(By.id("payment_information_form:expiration_yearInner"));
        Select expirationYearDropdownList = new Select(expirationYearSelectElement);
        // In the expiration year drop-down list, there should be 2021 as an option. Select that year.
        expirationYearDropdownList.selectByVisibleText("2021");
        Thread.sleep(1500);
        
        WebElement shippingAddressInputElement = driver.findElement(By.id("input_payment_information_form:shipping_address"));
        shippingAddressInputElement.clear();
        shippingAddressInputElement.sendKeys("12345 Awesome Best Street");
        Thread.sleep(1500);
        
        // Now that the user has entered valid credit card information, as well as his shipping address, he can now continue to the Place Order page.
        driver.findElement(By.id("payment_information_form:place_order_page_button")).click();
        Thread.sleep(1500);
        
        // The user should now be in the Place Order page where he can finalize his order if he wishes to.
        wait.until(ExpectedConditions.titleIs("Place your order"));
    }
    
    /**
     * Tests the checkout page where the user has to enter his payment information, as well as a shipping address.
     * The entered payment information will be invalid, so the user should not be able to proceed to the Place Order page.
     * Instead, the user will remain in the Enter Payment Information page.
     * 
     * @throws Exception 
     */
    @Test
    public void testPaymentInformationWithInvalidCreditCard() throws Exception
    {
         // To proceed to checkout, the user must first login. Selenium testing for the login page is done in LoginTest.java
        driver.get("http://localhost:8080/g4w18/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Sign into your account"));  
        
        WebElement usernameInputElement = driver.findElement(By.id("login_form:username"));
        usernameInputElement.clear();
        usernameInputElement.sendKeys("mdaniels");
        Thread.sleep(1500);
        
        WebElement passwordInputElement = driver.findElement(By.id("login_form:password"));
        passwordInputElement.clear();
        passwordInputElement.sendKeys("booktopia");
        Thread.sleep(1500);
        
        driver.findElement(By.id("login_form:login_button")).click();
        Thread.sleep(1500);
        
        /* 
            To proceed to checkout, the user must also not have an empty shopping cart.
            Goes to the book details page of the book with ID 1, and adds it into the shopping cart.
        */
        driver.get("http://localhost:8080/g4w18/bookDetail.xhtml?id=1");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Book Details"));
        Thread.sleep(1500);
        
        driver.findElement(By.id("add_to_cart_form:add_to_cart_button")).click();
        Thread.sleep(1500);
        
        // Goes to the shopping cart page, then clicks on the "Proceed to checkout" button, in order to navigate to the page in which the user has to enter his payment information.
        driver.get("http://localhost:8080/g4w18/shopping_cart.xhtml");
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("shopping_cart_form:checkoutButton")).click();
        Thread.sleep(1500);
        
        // The user should now be in the checkout page responsible for allowing the user to enter his payment information.
        wait.until(ExpectedConditions.titleIs("Enter payment information"));
        
        // The user must now enter a credit card name, a valid credit card number, an expiration month, an expiration year, and a shipping address.
        WebElement cardNameInputElement = driver.findElement(By.id("input_payment_information_form:card_name"));
        cardNameInputElement.clear();
        cardNameInputElement.sendKeys("Bob");
        Thread.sleep(1500);
        
        WebElement cardNumberInputElement = driver.findElement(By.id("input_payment_information_form:card_number"));
        cardNumberInputElement.clear();
        // Enter an invalid credit card number.
        cardNumberInputElement.sendKeys("1234567890");
        Thread.sleep(1500);
        
        WebElement expirationMonthSelectElement = driver.findElement(By.id("payment_information_form:expiration_monthInner"));
        Select expirationMonthDropdownList = new Select(expirationMonthSelectElement);
        // In the expiration month drop-down list, there should be 12 as an option. Select that month.
        expirationMonthDropdownList.selectByVisibleText("12");
        Thread.sleep(1500);
        
        WebElement expirationYearSelectElement = driver.findElement(By.id("payment_information_form:expiration_yearInner"));
        Select expirationYearDropdownList = new Select(expirationYearSelectElement);
        // In the expiration year drop-down list, there should be 2021 as an option. Select that year.
        expirationYearDropdownList.selectByVisibleText("2021");
        Thread.sleep(1500);
        
        WebElement shippingAddressInputElement = driver.findElement(By.id("input_payment_information_form:shipping_address"));
        shippingAddressInputElement.clear();
        shippingAddressInputElement.sendKeys("12345 Awesome Best Street");
        Thread.sleep(1500);
        
        // Now that the user has entered valid credit card information, as well as his shipping address, he can now continue to the Place Order page.
        driver.findElement(By.id("payment_information_form:place_order_page_button")).click();
        Thread.sleep(1500);
        
        // Because the credit card information that the user has entered is invalid, the user will remain in the 'Enter payment information' page
        wait.until(ExpectedConditions.titleIs("Enter payment information"));
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
