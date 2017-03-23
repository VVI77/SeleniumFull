package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Vladimir on 22.03.2017.
 */
public class Less10Task17 extends TestBase {
    @Test
    public void mainTest() throws InterruptedException {
        //***************** Login *********************
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //***************** click on Catalog button ********
        searchAndClick(By.cssSelector("ul#box-apps-menu li#app-"), "Catalog");
        //***************** click on Categories link ********
        driver.findElement(By.xpath(".//table[@class='dataTable']//td[3]/a")).click();
        //***************** main function ********
        workWithPage(By.xpath(".//table[@class='dataTable']//td[3]/img"));
}

    private  void workWithPage(By locator) {
        int countItem = driver.findElements(locator).size();
        WebElement firstImg = driver.findElement(locator);

        String first = firstImg.findElement(By.xpath("../..")).getAttribute("rowIndex");
        int firstItem = Integer.parseInt(first) + 1;
        clearBrowserLog();
        consistentClickItem(firstItem, countItem);
    }

    private void consistentClickItem(int firstItem, int countItem) {
        WebElement currentItem;
        for (int i=firstItem; i<countItem + firstItem; i++) {
            currentItem = driver.findElement(By.xpath(".//table[@class='dataTable']//tr[" + i +"]/td[3]/a"));
            System.out.println("************* Item " + currentItem.getAttribute("text"));
            currentItem.click();
            implicitlyWaitOff();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
            implicitlyWaitOn();
            driver.findElement(By.name("cancel")).click();
            printBrowserLog();
        }
    }

    private void printBrowserLog () {
        List<LogEntry> logList = driver.manage().logs().get("browser").getAll();
        if (logList.size()!= 0) {
            for (LogEntry l : logList)
                System.out.println(l);
        }
        else
            System.out.println("The Log has not new messeges!");
        System.out.println();
    }

    private void clearBrowserLog () {
        driver.manage().logs().get("browser").getAll();
    }

}
