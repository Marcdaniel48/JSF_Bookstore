package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Jephthia
 */
public class NavbarTest
{
    private static Logger logger = Logger.getLogger(NavbarTest.class.getName());
    private WebDriver driver;
    
    @Before
    public void init()
    {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
    }
    
    /**
     * Tests that clicking on the login button brings you to the Login page,
     * that clicking on the logo brings you to the home page, and that clicking
     * on the cart logo brings you to the cart page.
     */
    @Test
    public void testLinks() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        Thread.sleep(8000);

        WebDriverWait wait = new WebDriverWait(driver, 10);        
        
        WebElement login = driver.findElement(By.id("loginLink"));
        login.click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement username = driver.findElement(By.id("loginForm:username"));
        username.sendKeys("jlouis");
        
        WebElement password = driver.findElement(By.id("loginForm:password"));
        password.sendKeys("booktopia");
        
        WebElement submit = driver.findElement(By.id("loginForm:submit"));
        submit.click();
        wait.until(ExpectedConditions.titleIs("Book List"));
        
        WebElement home = driver.findElement(By.id("homeLink"));
        home.click();
        wait.until(ExpectedConditions.titleIs("Home"));
        
        WebElement cart = driver.findElement(By.id("cartLink"));
        cart.click();
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
    }
    
    @Test
    public void testUsernameDropdown() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/login.xhtml");
        Thread.sleep(8000);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        WebElement username = driver.findElement(By.id("loginForm:username"));
        username.sendKeys("jlouis");
        
        WebElement password = driver.findElement(By.id("loginForm:password"));
        password.sendKeys("booktopia");
        
        WebElement submit = driver.findElement(By.id("loginForm:submit"));
        submit.click();
        wait.until(ExpectedConditions.titleIs("Book List"));
        
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        Thread.sleep(8000);
        
        WebElement navbarUsername = driver.findElement(By.id("navbarUsername"));
        navbarUsername.click();
        Thread.sleep(3000);
        
        //WebElement downloads = driver.findElement(By.id("navbarDropdownForm:downloadsLink"));
        //downloads.click();
        //wait.until(ExpectedConditions.titleIs("Downloads"));
        
        //open dropdown again
        //navbarUsername = driver.findElement(By.id("navbarUsername"));
        //navbarUsername.click();
        //Thread.sleep(3000);
        
        WebElement manager = driver.findElement(By.id("navbarDropdownForm:managerLink"));
        manager.click();
        wait.until(ExpectedConditions.titleIs("Management Home"));

        Thread.sleep(5000);
        
        //open dropdown again
        navbarUsername = driver.findElement(By.id("navbarUsername"));
        navbarUsername.click();
        Thread.sleep(3000);
        
        WebElement logout = driver.findElement(By.id("navbarDropdownForm:logoutLink"));
        logout.click();
        
        wait.until(ExpectedConditions.titleIs("Home"));
        
        WebElement login = driver.findElement(By.id("loginLink"));
        login.click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
    }
    
    @After
    public void clean()
    {
        driver.quit();
    }
}