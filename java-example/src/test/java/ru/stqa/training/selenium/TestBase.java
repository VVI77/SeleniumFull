package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vladimir on 15.03.2017.
 */
public class TestBase {
    public WebDriver driver;
    public WebDriverWait wait;
    protected int timeout = 5;

    @Before
    public void start() {
        driver = new ChromeDriver();
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
