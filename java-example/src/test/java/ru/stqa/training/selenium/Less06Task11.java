/*Сделайте сценарий для регистрации нового пользователя в учебном приложении litecart
(не в админке, а в клиентской части магазина).
Сценарий должен состоять из следующих частей:
1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало
с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
3) повторный вход в только что созданную учётную запись,
4) и ещё раз выход.
В качестве страны выбирайте United States, штат произвольный. При этом формат индекса -- пять цифр.
Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
Проверки можно никакие не делать, только действия -- заполнение полей, нажатия на кнопки и ссылки.
Если сценарий дошёл до конца, то есть созданный пользователь смог выполнить вход и выход --
значит создание прошло успешно.*/
package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Vladimir on 08.03.2017.
 */
public class Less06Task11 {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void mainTest() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");

        String email = "damendola@ptrts.com";
        String password = "245869";

        createAccount(email, password);
        Thread.sleep(1000);

        logout();
        Thread.sleep(1000);

        login(email, password);
        Thread.sleep(1000);

        logout();
        Thread.sleep(1000);
    }

    private void createAccount(String email, String password) {
        driver.findElement(By.cssSelector("form[name='login_form'] table tr:last-child")).click();
        driver.findElement(By.name("firstname")).sendKeys("Danny");
        driver.findElement(By.name("lastname")).sendKeys("Amendola");
        driver.findElement(By.name("address1")).sendKeys("Tverskaya shtrasse");
        driver.findElement(By.name("postcode")).sendKeys("02121");
        driver.findElement(By.name("city")).sendKeys("Boston");
        //Country
        Select country = new Select(driver.findElement(By.name("country_code")));
        country.selectByVisibleText("United States");
        //State
        Select zone = new Select(driver.findElement(By.cssSelector("select[name='zone_code']")));
        zone.selectByVisibleText("Massachusetts");
        //*****
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("+18452245869");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.name("create_account")).click();
    }

    private void login(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }

    private void logout() {
        driver.findElement(By.cssSelector("div#box-account div.content li:last-child a")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
