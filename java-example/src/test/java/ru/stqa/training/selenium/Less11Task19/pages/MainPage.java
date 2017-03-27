package ru.stqa.training.selenium.Less11Task19.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Vladimir on 27.03.2017.
 */
public class MainPage extends Page{

    @FindBy(css = "div.content div.name")
    public WebElement firstItem;

   public MainPage (WebDriver driver) {
       super(driver);
   }
    @Override
    public void open(){
        driver.get("http://localhost/litecart/en/");
    }

    public void chooseFirstItem() {
        firstItem.click();
    }

}
