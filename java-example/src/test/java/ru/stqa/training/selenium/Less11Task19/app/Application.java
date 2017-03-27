package ru.stqa.training.selenium.Less11Task19.app;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.Less11Task19.pages.CartPage;
import ru.stqa.training.selenium.Less11Task19.pages.ItemPage;
import ru.stqa.training.selenium.Less11Task19.pages.MainPage;

/**
 * Created by Vladimir on 26.03.2017.
 */
public class Application {
    private WebDriver driver;
    private WebDriverWait wait;
    public MainPage mainPage;
    public CartPage cartPage;
    public ItemPage itemPage;

    public Application() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        mainPage = new MainPage(driver);
        cartPage = new CartPage(driver);
        itemPage = new ItemPage(driver);
    }

    @After
    public void quit() {
        driver.quit();
    }

}
