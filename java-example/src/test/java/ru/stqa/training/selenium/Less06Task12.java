/*Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).
Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product",
заполнить поля с информацией о товаре и сохранить.
Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке
Prices можно не добавлять.
Переключение между вкладками происходит не мгновенно, поэтому после переключения можно сделать небольшую паузу
(о том, как делать более правильные ожидания, будет рассказано в следующих занятиях).
Картинку с изображением товара нужно уложить в репозиторий вместе с кодом.
При этом указывать в коде полный абсолютный путь к файлу плохо, на другой машине работать не будет.
Надо средствами языка программирования преобразовать относительный путь в абсолютный.
После сохранения товара нужно убедиться, что он появился в каталоге (в админке).
Клиентскую часть магазина можно не проверять.
*/
package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vladimir on 09.03.2017.
 */
public class Less06Task12 {
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

        //***************** page Catalog *********************
        searchAndClick("ul#box-apps-menu li#app-", "Catalog");
        Thread.sleep(1000);

        //***************** button Add New Product *********************
        searchAndClick("td#content a.button", "Add New Product");
        Thread.sleep(1000);

        String newItem = "White Pelican";

        String relativePath = "./src/test/resources/pelican.jpg";
        Path filePath = Paths.get(relativePath);
        String absolutePath = filePath.normalize().toAbsolutePath().toString();

        //******* filling General
        fillTabGeneral(newItem, absolutePath);

        //******* filling Information
        searchAndClick("div.tabs li", "Information");
        Thread.sleep(1000);
        fillTabInformation();

        //******** filling Prices
        searchAndClick("div.tabs li", "Prices");
        Thread.sleep(1000);
        fillTabPrices();

        //Save
        driver.findElement(By.cssSelector("button[name=save]")).click();
        Thread.sleep(1000);

        checkNewItem(newItem);

    }

    private void fillTabGeneral(String item, String path){
        //Name
        driver.findElement(By.name("name[en]")).sendKeys(item);
        //Status
        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();
        //Code
        driver.findElement(By.name("code")).sendKeys("rp001");
        //Categories
        driver.findElement(By.cssSelector("input[type=checkbox][value='0']")).click();
        driver.findElement(By.cssSelector("input[type=checkbox][value='1']")).click();
        //Quantity
        driver.findElement(By.name("quantity")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE );
        driver.findElement(By.name("quantity")).sendKeys("50");
        //Upload file
        driver.findElement(By.name("new_images[]")). sendKeys(path);
    }

    private void fillTabInformation() {
        //Manufacrurer
        Select manufact = new Select(driver.findElement(By.name("manufacturer_id")));
        manufact.selectByVisibleText("ACME Corp.");
        //Short Description
        driver.findElement(By.name("short_description[en]")).sendKeys("Pelican is a large water bird.");
        //Description
        driver.findElement(By.className("trumbowyg-editor")).sendKeys("They are characterised by " +
                "a long beak and a large throat pouch used for catching prey and draining water from the scooped up " +
                "contents before swallowing. They have predominantly pale plumage, the exceptions being the brown and " +
                "Peruvian pelicans. The bills, pouches and bare facial skin of all species become brightly " +
                "coloured before the breeding season. The eight living pelican species have a patchy global distribution, " +
                "ranging latitudinally from the tropics to the temperate zone, though they are absent from interior " +
                "South America as well as from polar regions and the open ocean.");
    }

    private void fillTabPrices() {
        // Purchase Price
        driver.findElement(By.name("purchase_price")).sendKeys(Keys.CONTROL + "a" + Keys.DELETE );
        driver.findElement(By.name("purchase_price")).sendKeys("11");
        Select curr_code = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        curr_code.selectByVisibleText("Euros");
        // Price
        driver.findElement(By.name("prices[USD]")).sendKeys("17");
    }

    private void checkNewItem(String item) {
        String res = "not";
        String name;
        WebElement root = driver.findElement(By.cssSelector("table.dataTable tbody"));
        List<WebElement> list = root.findElements(By.xpath(".//tr/td[3]/a"));
        for (WebElement we : list) {
            name = we.getText();
            if (name.equals(item) ) {
                res = "successfully";
                break;
            }
        }
        System.out.println("New item " + item + " " + res + " added");
    }

    private void searchAndClick(String linkList, String text) {
        List<WebElement> list = driver.findElements(By.cssSelector(linkList));
        String name;
        for (WebElement we : list) {
            name = we.getText();
            if (name.equals(text) ) {
                we.click();
                break;
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
