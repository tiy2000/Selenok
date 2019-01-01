package com.hellofresh.tests;

import base.BaseTest;
import com.hellofresh.pages.InvitePage;
import com.hellofresh.pages.LoginPage;
import com.hellofresh.pages.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @BeforeMethod
    public void init() {
        createChromeWebDriver(AutoTearDown.AFTER_METHOD);
    }

    @Test
    public void directOpeningLoginPageTest() {
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.assertRightPage();
    }

    @Test
    public void registrationTest() {
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.validateIsRightPage();

        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
        InvitePage invitePage = signUpPage.fillRegistrationFields();

        invitePage.assertRightPage();

    }

}
