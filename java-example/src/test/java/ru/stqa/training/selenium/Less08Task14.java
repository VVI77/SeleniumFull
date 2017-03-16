/*Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне.
Сценарий должен состоять из следующих частей:
1) зайти в админку
2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
3) открыть на редактирование какую-нибудь страну или начать создание новой
4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и
открываются в новом окне, именно это и нужно проверить.
Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank". Но в этом упражнении
требуется именно кликнуть по ссылке, чтобы она открылась в новом окне, потом переключиться в новое окно,
закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.
Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.
*/
package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Set;


/**
 * Created by Vladimir on 14.03.2017.
 */
public class Less08Task14 extends TestBase{

    @Test
    public void mainTest() throws InterruptedException {
        //***************** Login *********************
        driver.get("http://localhost/litecart/admin/");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        searchAndClick(By.cssSelector("ul#box-apps-menu li#app-"), "Countries");
        choiseFromListAndClick(By.cssSelector("tr.row td[style]"));
        clickAndOpenAllWindows(By.cssSelector("i.fa-external-link"));
    }

    public ExpectedCondition<String> anyWindowOtherThan (Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver d) {
                Set<String> handles = d.getWindowHandles();
                handles.removeAll(oldWindows);
                return  handles.size()>0 ? handles.iterator().next() : null;
            }
        };
    }

    private void clickAndOpenAllWindows (By locator) {
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();
        List<WebElement> list = driver.findElements(locator);
        for (WebElement we:list) {
            we.click();
            openAndCloseNewWindow(oldWindows);
            driver.switchTo().window(mainWindow);
        }
    }

    private void openAndCloseNewWindow(Set<String> oldWindows) {
        implicitlyWaitOff();
        String newWindow = wait.until(anyWindowOtherThan(oldWindows));
        driver.switchTo().window(newWindow);
        String title = driver.getTitle();
        if (title.contains("Wikipedia"))
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
        else
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button#acct_btn")));
        implicitlyWaitOn();
        driver.close();
    }

}
