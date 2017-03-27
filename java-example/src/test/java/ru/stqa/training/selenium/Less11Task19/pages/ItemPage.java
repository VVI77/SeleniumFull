package ru.stqa.training.selenium.Less11Task19.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Vladimir on 27.03.2017.
 */
public class ItemPage extends Page {

    @FindBy(css = "div#cart span.quantity")
    private WebElement itemsInCart;

    @FindBy(css = "td.options option")
    private List<WebElement> selectMenu;

    @FindBy(name="add_cart_product")
    private WebElement addBtn;

    public ItemPage  (WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        By locator = By.cssSelector("div#cart span.quantity");
        String itemCount = itemsInCart.getText();
        Integer next = Integer.parseInt(itemCount) + 1;
        itemCount = next.toString();
        if (!isElementNotPresent(driver, By.cssSelector("td.options")) ) {
            int count = selectMenu.size();
            choiseFromSelect(By.cssSelector("select[name='options[Size]'"), count, false);
        }
        implicitlyWaitOff();
        addBtn.click();
        wait.until(ExpectedConditions.textToBe(locator, itemCount));
        String quantity = itemsInCart.getText();
        Assert.assertTrue(quantity.equals(itemCount));
        implicitlyWaitOn();
    }
}
