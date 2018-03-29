package com.g4w18.selenium;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Jephthia
 */
public class HomepageTest
{
    private WebDriver driver;
    
    @Test
    public void sdas() throws InterruptedException
    {
        System.setProperty("webdriver.chrome.driver", "C:\\dev\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        driver.get("http://localhost:8080/g4w18/index.xhtml");
        
        Thread.sleep(1000);
        
        WebElement loginBox = driver.findElement(By.className("loginform:login-username"));
        loginBox.sendKeys("test@test.com");

        //register-password
        Thread.sleep(1000);
        WebElement passwordBox = driver.findElement(By.id("loginform:login-password"));
        passwordBox.sendKeys("password");

//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
//        robot.delay(2000);
        
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    
//    @After
//	public void tearDown() throws Exception {
//		this.driver.quit();
//	}
}