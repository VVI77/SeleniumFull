/*Сделайте сценарий, который выполняет следующие действия в учебном приложении litecart.
1) входит в панель администратора http://localhost/litecart/admin
2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
3) для каждой страницы проверяет наличие заголовка (то есть элемента с тегом h1)*/
package ru.stqa.training.selenium;

/**
 * Created by Vladimir on 03.03.2017.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


public class Less04Task07 {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        //***************** Login *********************
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();


        int linksCount =  driver.findElements(By.cssSelector("ul#box-apps-menu li#app-")).size();
        int docsCount;
        String pageName;
        WebElement row, link;
        for (int i=1; i<=linksCount; i++)  {
            link = refreshPage(driver, i);
            pageName = link.findElement(By.xpath(".//span[@class='name']")).getText();
            link.click();
            link = refreshPage(driver, i);
            docsCount = link.findElements(By.xpath("./ul[@class='docs']/li[@id]")).size();
            if (docsCount > 0) {
                for (int j=1; j<=docsCount; j++) {
                    link = refreshPage(driver, i);
                    row = link.findElement(By.xpath("./ul[@class='docs']/li[@id][" + j + "]"));
                    pageName = row.findElement(By.xpath(".//span[@class='name']")).getText();
                    row.click();
                    checkHeader(driver, pageName);
                    Thread.sleep(250);
                }
            }
            else {
                checkHeader(driver, pageName);
            }
            Thread.sleep(500);
        }
    }

    private WebElement refreshPage(WebDriver wd, int i){
        WebElement row = wd.findElement(By.id("box-apps-menu"));
        WebElement link = row.findElement(By.xpath("./li[@id='app-'][" + i + "]"));
        return link;
    }

    private void checkHeader(WebDriver d, String pageName){
        String h1;
        String res = "*** The page " + pageName;
        if ( isElementPresent(d, By.xpath(".//td[@id='content']/h1")) ) {
            h1 = d.findElement(By.xpath(".//td[@id='content']/h1")).getText();
            res += " have a header " + h1 + ".";
        }
        else
            res += " not have header h1";

        System.out.println(res);
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
