/*Сделайте сценарий для добавления товаров в корзину и удаления товаров из корзины.
    1) открыть главную страницу
    2) открыть первый товар из списка
    3) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
    4) подождать, пока счётчик товаров в корзине обновится
    5) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности
       в корзине было 3 единицы товара
    6) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
    7) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
*/
package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vladimir on 12.03.2017.
 */
public class Less07Task13 {
    private WebDriver driver;
    private WebDriverWait wait;
    private int timeout = 3;

    @Before
    public void start() {
        driver = new ChromeDriver();
        implicitlyWaitOn();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");
        for (int i = 1; i<=3; i++) {
            addItemToCart();
            driver.findElement(By.id("logotype-wrapper")).click();
        }
        driver.findElement(By.cssSelector("div#cart a.link")).click();
        clearCart();
        Thread.sleep(2000);
}

    private void addItemToCart() {
        WebElement quantity = driver.findElement(By.cssSelector("div#cart span.quantity"));
        By locator = By.cssSelector("div#cart span.quantity");
        String itemCount = quantity.getText();
        Integer next = Integer.parseInt(itemCount) + 1;
        itemCount = next.toString();
        driver.findElement(By.cssSelector("div.content div.name")).click();
        //Size
        if (!isElementNotPresent(driver, By.cssSelector("td.options")) ) {
            int count = driver.findElements(By.cssSelector("td.options option")).size();
            choiseFromSelect(By.cssSelector("select[name='options[Size]'"), count, false);
        }
        implicitlyWaitOff();
        driver.findElement(By.name("add_cart_product")).click();
        wait.until(ExpectedConditions.textToBe(locator, itemCount));
        quantity = driver.findElement(locator);
        Assert.assertTrue(quantity.getText().equals(itemCount));
        implicitlyWaitOn();
    }

    private void choiseFromSelect(By locator, int size, boolean isFirst) {
        Select menu = new Select(driver.findElement(locator));
        int index = (int)(Math.random()*size);
        if (!isFirst && index==0) index++;
        menu.selectByIndex(index);
    }

    private void clearCart(){
        int count = driver.findElements(By.cssSelector("li.shortcut")).size();
        for (int i=count; i>1; i--) {
            driver.findElement(By.cssSelector("li.shortcut")).click();
            driver.findElement(By.name("remove_cart_item")).click();
            implicitlyWaitOff();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                       By.cssSelector("table.dataTable td.item"), i));
            implicitlyWaitOn();
        }
        driver.findElement(By.name("remove_cart_item")).click();
        implicitlyWaitOff();
        wait.until(ExpectedConditions.stalenessOf(
                driver.findElement(By.cssSelector("table.dataTable"))));
        implicitlyWaitOn();
    }

    private void implicitlyWaitOn() {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    private void implicitlyWaitOff() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    boolean isElementNotPresent(WebDriver driver, By locator) {
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
