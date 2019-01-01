package com.hellofresh.pages;

import base.BasePage;
import org.openqa.selenium.By;

public class HomePage extends HelloFreshBasePage {

    public HomePage() {
        setPagePath("");
        pageIdLocator = By.xpath("(//span[@data-translation-id='senf-navigation.login'])[2]");
    }


}
