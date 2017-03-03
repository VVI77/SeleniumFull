package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Vladimir on 27.02.2017.
 */
public class Less02Task03 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //*************** FireFox 47 **********************
        // старая схема:
        //FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files\\Mozilla Firefox 45\\firefox.exe"));
        //DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setCapability(FirefoxDriver.MARIONETTE, false);
        //driver = new FirefoxDriver(bin, new FirefoxProfile(), caps);
        //System.out.println(((HasCapabilities)driver).getCapabilities());

        //*************** Nightly **********************
        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Nightly\\firefox.exe"));
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(bin, new FirefoxProfile());
        System.out.println(((HasCapabilities)driver).getCapabilities().getBrowserName() + " * *********** ");



        //*************** Chrome with options **********************
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("start-fullscreen");
        //driver = new ChromeDriver(options);

        //*************** Standart **********************
        //driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);

    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        Thread.sleep(2000);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
        Thread.sleep(2000);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
