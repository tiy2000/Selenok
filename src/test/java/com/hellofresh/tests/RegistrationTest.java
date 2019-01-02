package com.hellofresh.tests;

import base.BaseTest;
import com.hellofresh.pages.InvitePage;
import com.hellofresh.pages.LoginPage;
import com.hellofresh.pages.SignUpPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

//    @BeforeClass
//    public void initBeforeClass() {
//        createChromeWebDriver(AutoTearDown.AFTER_CLASS);
//    }

    @BeforeMethod
    public void initBeforeMethod() {
        createChromeWebDriver(AutoTearDown.AFTER_METHOD);
    }

    @Test
    public void directOpeningLoginPageTest() {
        System.out.println("RegistrationTest.directOpeningLoginPageTest ENTER");
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.assertRightPage();
        System.out.println("RegistrationTest.directOpeningLoginPageTest EXIT");
    }

    @Test(dependsOnMethods = "directOpeningLoginPageTest",enabled = false)
    public void badRegistrationTest() {
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.validateIsRightPage();

        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
        signUpPage.validateIsRightPage();

        InvitePage invitePage = signUpPage.fillRegistrationFields();

        invitePage.assertRightPage();

    }

    @Test(dependsOnMethods = "directOpeningLoginPageTest")
    public void goodRegistrationTest() {
        System.out.println("RegistrationTest.goodRegistrationTest ENTER");
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

        System.out.println("RegistrationTest.goodRegistrationTest EXIT");
    }

}
