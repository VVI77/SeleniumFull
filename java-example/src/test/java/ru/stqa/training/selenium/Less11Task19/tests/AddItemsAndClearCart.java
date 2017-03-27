package ru.stqa.training.selenium.Less11Task19.tests;

import org.junit.Test;


/**
 * Created by Vladimir on 26.03.2017.
 */
public class AddItemsAndClearCart extends TestBase{

    @Test
    public void mainTest()  {
        app.mainPage.open();
        for (int i = 1; i<=3; i++) {
            app.mainPage.chooseFirstItem();
            app.itemPage.addItemToCart();
            app.itemPage.goToMainPage();
        }
        app.cartPage.open();
        app.cartPage.clearCart();
    }

}
