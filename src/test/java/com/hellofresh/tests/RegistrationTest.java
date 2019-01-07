package com.hellofresh.tests;

import base.BaseTest;
import com.hellofresh.pages.InvitePage;
import com.hellofresh.pages.LoginPage;
import com.hellofresh.pages.SignUpPage;
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

//    @Test(dependsOnMethods = "directOpeningLoginPageTest")
//    public void badRegistrationTest() {
//        LoginPage loginPage = LoginPage.openNewPage();
//        loginPage.validateIsRightPage();
//
//        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
//        signUpPage.validateIsRightPage();
//
//        InvitePage invitePage = signUpPage.fillRegistrationFields();
//
////        invitePage.assertRightPage();
//        invitePage.validateIsRightPage();
//
//    }

//    @Test(dependsOnMethods = "directOpeningLoginPageTest")
//    @Test()
    public void goodRegistrationTest() {
        System.out.println("RegistrationTest.goodRegistrationTest ENTER");
        LoginPage loginPage = LoginPage.openNewPage();
        loginPage.validateIsRightPage();

        SignUpPage signUpPage = loginPage.clickRegisterUserLink();
        signUpPage.validateIsRightPage();

        signUpPage
                .selectGender("male")
                .enterFirstName("Alex")
                .enterLastName("Bow")
                .enterFillEmail("qwerty57@nono.to")
                .enterPassword("12345678")
                .enterMonth("12")
                .enterDay("20")
                .enterYear("2000");

        InvitePage invitePage = signUpPage.clickRegistrationButton();

        invitePage.assertRightPage();

        System.out.println("RegistrationTest.goodRegistrationTest EXIT");
    }

    @Test()
    public void bddRegistrationTest() {
        System.out.println("RegistrationTest.goodRegistrationTest ENTER");

        LoginPage.openNewPage()
                .validateIsRightPage()
                .clickRegisterUserLink()
                    // Working with SignUpPage
                    .validateIsRightPage()
                    .selectGender("male")
                    .enterFirstName("Alex")
                    .enterLastName("Bow")
                    .enterFillEmail("qwerty57@nono.to")
                    .enterPassword("12345678")
                    .enterMonth("12")
                    .enterDay("20")
                    .enterYear("2000")
                    .clickRegistrationButton()
                        // Working with InvitePage
                        .assertRightPage();

        System.out.println("RegistrationTest.goodRegistrationTest EXIT");
    }

    @Test()
    public void autoPageOpenRegistrationTest() {
        System.out.println("RegistrationTest.autoPageOpenRegistrationTest ENTER");

        given()
                .<LoginPage>openNewPage(LoginPage.class)
                .validateIsRightPage()
                .clickRegisterUserLink()
                    // Working with SignUpPage
                    .validateIsRightPage()
                    .selectGender("male")
                    .enterFirstName("Alex")
                    .enterLastName("Bow")
                    .enterFillEmail("qwerty57@nono.to")
                    .enterPassword("12345678")
                    .enterMonth("12")
                    .enterDay("20")
                    .enterYear("2000")
                    .clickRegistrationButton()
                        // Working with InvitePage
                        .assertRightPage();

        System.out.println("RegistrationTest.autoPageOpenRegistrationTest EXIT");
    }

}
