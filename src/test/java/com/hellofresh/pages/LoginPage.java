package com.hellofresh.pages;

import base.annotations.PageId;
import base.annotations.PagePath;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends HelloFreshBasePage<LoginPage> {

    @PagePath
    private static final String PATH = "customer/account";

    @PageId
    public static final By REGISTER_USER_LINK = By.id("register-user-link");

    public LoginPage() {
//        setPagePath("customer/account/login");
//        setRightPageCondition(ExpectedConditions.presenceOfElementLocated(REGISTER_USER_LINK));

//        pageIdLocator = REGISTER_USER_LINK;
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
