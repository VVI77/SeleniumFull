package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Vladimir on 24.03.2017.
 */
public class Less10Test {
    public static final String USERNAME = "vladimir520";
    public static final String AUTOMATE_KEY = "Ax4qCpoNSAoM3C34sJzy";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    @Test
    public void mainTest() throws InterruptedException, MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("platform", "ANY");
        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("build", "First build");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
        driver.get("http://www.nfl.com");
        //WebElement element = driver.findElement(By.name("q"));

        //element.sendKeys("BrowserStack");
        //element.submit();

        System.out.println(driver.getTitle());
        driver.quit();
    }
}
