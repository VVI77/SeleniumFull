package ru.stqa.training.selenium.Less11Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Vladimir on 27.03.2017.
 */
public class CartPage extends Page {

    @FindBy(css = "div#cart a.link")
    public WebElement cartLink;

    @FindBy(css = "li.shortcut")
    public List<WebElement> itemSet;

    @FindBy(css = "li.shortcut")
    public WebElement firstItem;

    @FindBy(name = "remove_cart_item")
    public WebElement removeBtn;

    public CartPage (WebDriver driver) {
        super(driver);
    }

    public void open(){
        cartLink.click();
    }

    public void clearCart(){
        int count = itemSet.size();
        for (int i=count; i>1; i--)
            deleteOneItem();
        removeBtn.click();
        implicitlyWaitOff();
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("table.dataTable"))));
        implicitlyWaitOn();
    }

    protected void deleteOneItem() {
        int itemCount = itemSet.size();
        firstItem.click();
        removeBtn.click();
        implicitlyWaitOff();
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                By.cssSelector("table.dataTable td.item"), itemCount));
        implicitlyWaitOn();
    }
}
