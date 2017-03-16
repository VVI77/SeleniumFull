/*Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении litecart на главной странице.
Стикеры -- это полоски в левом верхнем углу изображения товара, на которых написано New или Sale или что-нибудь другое.
Сценарий должен проверять, что у каждого товара имеется ровно один стикер.
Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.*/
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
import java.util.concurrent.TimeUnit;

public class Less04Task08 {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        //***************** Login *********************
        driver.get("http://localhost/litecart/en/");

        String[] boxType = {"box-most-popular", "box-campaigns", "box-latest-products"};
        String locator, itemName, stickerName;
        int itemsCount, stickerCount;
        WebElement item;

        for (String box : boxType) {
            System.out.println("*** " + box + " ***");
            locator = ".//div[@class='middle']//div[@id='" + box + "']//li[@class]";
            itemsCount = driver.findElements(By.xpath(locator)).size();
            if (itemsCount > 0) {
                for (int i = 1; i <= itemsCount; i++) {
                    item = driver.findElement(By.xpath(locator + "[" + i + "]"));
                    stickerCount = item.findElements(By.xpath(".//div[@class='image-wrapper']/div[@title]")).size();
                    itemName = item.findElement(By.xpath(".//div[@class='name']")).getText();
                    if (stickerCount == 1) {
                        stickerName = item.findElement(By.xpath(".//div[@title]")).getText();
                        System.out.println("*** ALL RIGHT!!! The item " + itemName + " has sticker " + stickerName.toUpperCase());
                    }
                    else if (stickerCount > 1)
                        System.out.println("*** ERROR!!! The item " + itemName + " has MORE than one sticker");
                    else
                        System.out.println("*** ERROR!!! The item " + itemName + " WITHOUT sticker");
                }
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
