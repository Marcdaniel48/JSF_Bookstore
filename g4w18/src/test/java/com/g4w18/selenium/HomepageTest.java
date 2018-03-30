package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.awt.Robot;
import java.util.List;
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
 * @author Jephthia
 */
public class HomepageTest
{
    private WebDriver driver;
    
    @Before
    public void init()
    {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }
    
    @Test
    public void testClickOnBook() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/login.xhtml");
        Thread.sleep(5000);
        
        WebElement username = driver.findElement(By.id("loginForm:username"));
        username.sendKeys("jlouis");
        
        WebElement password = driver.findElement(By.id("loginForm:password"));
        password.sendKeys("booktopia");
        
        WebElement submit = driver.findElement(By.id("loginForm:submit"));
        submit.click();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.titleIs("BookList"));
        
        Thread.sleep(5000);
        
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        Thread.sleep(8000);
        
        WebElement recommendations = driver.findElement(By.id("recent-books"));
        List<WebElement> books = recommendations.findElements(By.className("book"));
        
        books.get(0).click();
        
        wait.until(ExpectedConditions.titleIs("Book Details"));
        
        Thread.sleep(5000);
    }
    
    @After
    public void clean()
    {
        driver.quit();
    }
}