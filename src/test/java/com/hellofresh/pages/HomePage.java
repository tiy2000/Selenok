package com.hellofresh.pages;

import base.BasePage;
import org.openqa.selenium.By;

public class HomePage extends HelloFreshBasePage {

    public HomePage() {
        setPagePath("");
        pageIdLocator = By.xpath("(//span[@data-translation-id='senf-navigation.login'])[2]");
//        pageIdLocator = By.xpath("(//span[@data-translation-id='senf-navigation.login_INVALID_LOCATOR'])[2]");
    }

    public static HomePage openNewPage() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        return homePage;
    }


}
