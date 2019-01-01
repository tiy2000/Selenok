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
    public void badRegistrationTest() {
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.validateIsRightPage();

        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
        InvitePage invitePage = signUpPage.fillRegistrationFields();

        invitePage.assertRightPage();

    }

    @Test
    public void goodRegistrationTest() {
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.validateIsRightPage();

        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
        signUpPage.validateIsRightPage();

        signUpPage
                .selectGender("male")
                .clearAndFillFirstName("Alex")
                .clearAndFillLastName("Bow")
                .clearAndFillEmail("qwerty57@nono.to")
                .clearAndFillPassword("12345678")
                .clearAndFillMonth("12")
                .clearAndFillDay("20")
                .clearAndFillYear("2000");

        InvitePage invitePage = signUpPage.clickRegistrationButton();

        invitePage.assertRightPage();

    }

}
