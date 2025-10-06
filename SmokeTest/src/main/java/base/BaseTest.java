package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Executable;
import java.time.Duration;
import java.util.List;

public class BaseTest{

    protected WebDriver driver;


    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.xe.gr/");
    }

    @AfterClass
    public void tearDown(){
        if (driver != null) driver.quit();
    }
}