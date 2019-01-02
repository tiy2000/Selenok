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
        System.out.println("*** Before opening LoginPage");
        loginPage.openPage();
        System.out.println("*** After opening LoginPage");
        return loginPage;
    }

    public SignUpPage clickRegisterUserLink() {
        getDriver().findElement(REGISTER_USER_LINK).click();
        return new SignUpPage();
    }


}
