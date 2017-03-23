package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by Vladimir on 15.03.2017.
 */
public class TestBase {
    public WebDriver driver;
    public WebDriverWait wait;
    protected int timeout = 5;

    @Before
    public void start() {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        //driver = new ChromeDriver();
        driver = new ChromeDriver(cap);
        implicitlyWaitOn();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    protected void searchAndClick(By locator, String text) {
        List<WebElement> list = driver.findElements(locator);
        String name;
        for (WebElement we : list) {
            name = we.getText();
            if (name.equals(text) ) {
                we.click();
                break;
            }
        }
    }

    protected void choiseFromListAndClick (By locator) {
        List<WebElement> list = driver.findElements(locator);
        int index = (int)(Math.random()*list.size());
        list.get(index).click();
    }



    protected void implicitlyWaitOn() {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    protected void implicitlyWaitOff() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    protected boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    protected boolean isElementNotPresent(WebDriver driver, By locator) {
        try {
            implicitlyWaitOff();
            return driver.findElements(locator).size() == 0;
        } finally {
            implicitlyWaitOn();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
