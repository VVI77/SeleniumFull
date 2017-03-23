package ru.stqa.training.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vladimir on 20.03.2017.
 */
public class Less09Test {

    @Test
    public void mainTest() throws InterruptedException, MalformedURLException {
        String path = "http://192.168.1.12:4444/wd/hub";
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        //capability.setPlatform(Platform.WINDOWS);
        WebDriver driver = new RemoteWebDriver(new URL(path), capability);
        driver.get("http://www.yandex.ru");
        capability = DesiredCapabilities.internetExplorer();
        driver = new RemoteWebDriver(new URL(path), capability);
        driver.get("http://www.google.com");
        Thread.sleep(2000);
    }
}
