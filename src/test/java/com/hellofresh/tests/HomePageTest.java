package com.hellofresh.tests;

import base.BaseTest;
import com.hellofresh.pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @BeforeMethod
    public void init() {
//        createChromeWebDriver(AutoTearDown.NONE);
        createChromeWebDriver(AutoTearDown.AFTER_METHOD);
    }

//    @AfterMethod
//    public void tearDown() {
//        tearDownDriver();
//    }

    @Test
    public void homePageTest() {
//        HomePage homePage = new HomePage();
//        homePage.openPage();

        HomePage homePage = HomePage.openNewHomePage();

        homePage.validateIsRightPage();
//        homePage.assertRightPage();



    }


}
