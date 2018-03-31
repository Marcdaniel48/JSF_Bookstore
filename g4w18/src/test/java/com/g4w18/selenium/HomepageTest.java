package com.g4w18.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
public class HomepageTest
{
    private static Logger logger = Logger.getLogger(HomepageTest.class.getName());
    private WebDriver driver;
    
    @Before
    public void init()
    {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
    }
    
    @Test
    public void testRecommendations() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        Thread.sleep(8000);
        
        WebElement recent = driver.findElement(By.id("recent-books"));
        List<WebElement> books = recent.findElements(By.className("book"));
        
        books.get(0).click();
        
        wait.until(ExpectedConditions.titleIs("Book Details"));
        
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        Thread.sleep(5000);
        
        WebElement recommendations = driver.findElement(By.className("recommendations"));
        List<WebElement> recommendedBooks = recommendations.findElements(By.className("book"));
        
        recommendedBooks.get(0).click();
        
        wait.until(ExpectedConditions.titleIs("Book Details"));
    }
    
    @Test
    public void testSurvey() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        //Thread.sleep(8000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("surveyForm:surveyChoices:0")));
        //WebElement survey = driver.findElement(By.id("survey"));
        WebElement question = driver.findElement(By.id("surveyForm:surveyChoices:0"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()", question);
        
        wait.until(ExpectedConditions.elementSelectionStateToBe(question, true));
        
        WebElement submit = driver.findElement(By.id("surveyForm:submit"));
        submit.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pie")));
    }
    
    @Test
    public void testClickOnGenre()
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        List<WebElement> genres = driver.findElements(By.className("genre-link"));
        
        genres.get(0).click();
        
        wait.until(ExpectedConditions.titleIs("Book List"));
    }
    
    @Test
    public void testRSS() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        List<WebElement> links = driver.findElements(By.className("rss-link"));
        
        String url = links.get(0).getAttribute("href");
        
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        
        logger.log(Level.INFO, LocalDateTime.now() + " >>> URL: {0}", url);
        
        links.get(0).click();
        
        Thread.sleep(5000);
        
        Iterator<String> i = driver.getWindowHandles().iterator();
        
        driver.switchTo().window(i.next());

        driver.switchTo().window(i.next());
        
        Thread.sleep(5000);

        driver.navigate().refresh();
        
        Thread.sleep(5000);
        
        wait.until(ExpectedConditions.urlContains(url));
    }
    
    @Test
    public void testBanner() throws Exception
    {
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        List<WebElement> links = driver.findElements(By.className("slide-link"));
        
        String url = links.get(0).getAttribute("href");
        
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        
        logger.log(Level.INFO, LocalDateTime.now() + " >>> URL: {0}", url);
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()", links.get(0));
        
        Thread.sleep(5000);
        
        Iterator<String> i = driver.getWindowHandles().iterator();
        
        driver.switchTo().window(i.next());

        driver.switchTo().window(i.next());
        
        Thread.sleep(5000);

        driver.navigate().refresh();
        
        Thread.sleep(5000);
        
        wait.until(ExpectedConditions.urlContains(url));
    }
    
    @After
    public void clean()
    {
        driver.quit();
    }
}