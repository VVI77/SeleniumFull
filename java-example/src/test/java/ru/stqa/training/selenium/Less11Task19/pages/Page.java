package ru.stqa.training.selenium.Less11Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private int timeout = 3;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void open(By selector){
        driver.findElement(selector).click();
    }

    public void open(){}

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

    protected void choiseFromSelect(By locator, int size, boolean isFirst) {
        Select menu = new Select(driver.findElement(locator));
        int index = (int)(Math.random()*size);
        if (!isFirst && index==0) index++;
        menu.selectByIndex(index);
    }

    public void goToMainPage() {
        driver.findElement(By.id("logotype-wrapper")).click();
    }
}
