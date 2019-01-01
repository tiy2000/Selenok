package com.hellofresh.pages;

import org.openqa.selenium.By;

public class LoginPage extends HelloFreshBasePage {

    public static final By REGISTER_USER_LINK = By.id("register-user-link");

    public LoginPage() {
        setPagePath("customer/account/login");
        pageIdLocator = REGISTER_USER_LINK;
    }

    public static LoginPage openNewPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        return loginPage;
    }

    public SignUpPage clickRegisterUserLink() {
        getDriver().findElement(REGISTER_USER_LINK).click();
        return new SignUpPage();
    }


}
