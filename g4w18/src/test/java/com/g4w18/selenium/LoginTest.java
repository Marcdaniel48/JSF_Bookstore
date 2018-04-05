package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the login page with Selenium.
 * 
 * @author Marc-Daniel
 */
@Ignore
public class LoginTest 
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
     * Tests the login form with a valid username and password combination.
     * 
     * @throws Exception 
     */
    @Test
    public void testLoginFormWithValidCredentials() throws Exception {
        driver.get("http://localhost:8080/g4w18/login.xhtml");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.titleIs("Sign into your account"));  
        
        Thread.sleep(1500);
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
        // If the user has sucessfully signed-in, then he will be navigated to the index page.
        wait.until(ExpectedConditions.titleIs("Welcome to Booktopia"));
    }
    
    /**
     * Tests the login form with an invalid username and password combination.
     * 
     * @throws Exception 
     */
    @Test
    public void testLoginFormWithInvalidCredentials() throws Exception {
        driver.get("http://localhost:8080/g4w18/login.xhtml");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.titleIs("Sign into your account"));  
        
        Thread.sleep(1500);
        WebElement usernameInputElement = driver.findElement(By.id("login_form:username"));
        usernameInputElement.clear();
        usernameInputElement.sendKeys("mdaniels");

        Thread.sleep(1500);
        WebElement passwordInputElement = driver.findElement(By.id("login_form:password"));
        passwordInputElement.clear();
        passwordInputElement.sendKeys("incorrectPasswordLOL");

        Thread.sleep(1500);
        driver.findElement(By.id("login_form:login_button")).click();

        Thread.sleep(1500);
        // If the user has unsucessfully attempted to sign-in, then he will remain in the login page.
        wait.until(ExpectedConditions.titleIs("Sign into your account"));
    }
    
    /**
     * Tests the registration button found in the login form.
     * 
     * @throws Exception 
     */
    @Test
    public void testGoToRegistrationButton() throws Exception {
        driver.get("http://localhost:8080/g4w18/login.xhtml");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        Thread.sleep(1500);
        wait.until(ExpectedConditions.titleIs("Sign into your account"));  
        
        Thread.sleep(1500);
        driver.findElement(By.id("login_form:go_to_registration_button")).click();

        Thread.sleep(1500);
        // If the sucessfully clicks on the working registration button, then he will be navigated to the registration page.
        wait.until(ExpectedConditions.titleIs("Register an account"));
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
