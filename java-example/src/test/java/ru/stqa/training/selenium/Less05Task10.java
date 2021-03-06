/*Сделайте сценарий, который проверяет, что при клике на товар открывается правильная страница
товара в учебном приложении litecart.
Более точно, нужно открыть главную страницу, выбрать первый товар в категории Campaigns и проверить следующее:
а) на главной странице и на странице товара совпадает текст названия товара
б) на главной странице и на странице товара совпадают цены (обычная и акционная)
в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой,
у которого в RGBa представлении одинаковые значения для каналов R, G и B)
г) акционная жирная и красная (можно считать, что "красный" цвет это такой,
у которого в RGBa представлении каналы G и B имеют нулевые значения)
(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
г) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
Необходимо убедиться, что тесты работают в разных браузерах, желательно проверить во всех трёх ключевых
браузерах (Chrome, Firefox, IE).*/
package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Vladimir on 07.03.2017.
 */
public class Less05Task10 {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");
        String [] listMainPage;
        String [] listItemPage;
        String result;

        WebElement item = driver.findElement(By.xpath(".//div[@id='box-campaigns']//li[1]"));
        listMainPage = fillList(item);
        listMainPage[0] = item.findElement(By.cssSelector("div.name")).getText();
        item.click();

        item = driver.findElement(By.cssSelector("div#box-product div.information"));
        listItemPage = fillList(item);
        listItemPage[0] = driver.findElement(By.cssSelector("div#box-product h1.title")).getText();

        System.out.println("*************** Point A ******************************");
        System.out.println("The item on Main Page: " + listMainPage[0]);
        System.out.println("The item on Item Page: " + listItemPage[0]);
        printResult(listMainPage[0], listItemPage[0], "name");

        System.out.println("*************** Point B ******************************");
        System.out.println("The regular price on Main Page: " + listMainPage[1]);
        System.out.println("The regular price on Item Page: " + listItemPage[1]);
        printResult(listMainPage[1], listItemPage[1], "regular price");

        System.out.println("The campaign price on Main Page: " + listMainPage[5]);
        System.out.println("The campaign price on Item Page: " + listItemPage[5]);
        printResult(listMainPage[5], listItemPage[5], "campaign price");

        System.out.println("*************** Point C ******************************");
        System.out.println("Main Page");
        System.out.println("Regular price (color): " + listMainPage[2]);
        System.out.println("Regular price (text decoration): " + listMainPage[3]);
        System.out.println("Campaign price (color): " + listMainPage[6]);
        System.out.println("Campaign price (weight): " + listMainPage[7]);
        System.out.println("Item Page");
        System.out.println("Regular price (color): " + listItemPage[2]);
        System.out.println("Regular price (text decoration): " + listItemPage[3]);
        System.out.println("Campaign price (color): " + listItemPage[6]);
        System.out.println("Campaign price (weight): " + listItemPage[7] + "\n");

        System.out.println("*************** Point D ******************************");
        System.out.println("Main Page");
        System.out.println("Regular price (font size):" + listMainPage[4]);
        System.out.println("Campaign price (font size): " + listMainPage[8]);
        printComp(listMainPage[8], listMainPage[4]);

        System.out.println("Item Page");
        System.out.println("Regular price (font size): " + listItemPage[4]);
        System.out.println("Campaign price (font size): " + listItemPage[8]);
        printComp(listItemPage[8], listItemPage[4]);
    }

    private String[] fillList (WebElement root) {
        String[] list = new String[9];
        list[1] = root.findElement(By.cssSelector("s.regular-price")).getText();
        list[2] = root.findElement(By.cssSelector("s.regular-price")).getCssValue("color");
        list[3] = root.findElement(By.cssSelector("s.regular-price")).getCssValue("text-decoration");
        list[4] = root.findElement(By.cssSelector("s.regular-price")).getCssValue("font-size");

        list[5] = root.findElement(By.cssSelector("strong.campaign-price")).getText();
        list[6] = root.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color");
        list[7] = root.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-weight");
        list[8] = root.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-size");

        return list;
    }

    private void printList(String[] list){
        for (String s:list)
            System.out.println(s);
        System.out.println();
    }

    private void printResult(String s1, String s2, String key) {
        String result = "The " + key + " of the items is ";
        if (s1.equals(s2) )
            result += "the same.";
        else
            result += "diferent.";
        System.out.println(result);
    }

    private void printComp(String s1, String s2) {
        String result;
        if (s1.compareTo(s2) > 0)
            result = "bigger";
        else
            result = "smaller";
        System.out.println("CampaignPrice Font " + result + " then RegularPrice Font.");
    }



    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
