package com.hellofresh.tests;

import base.BaseTest;
import com.hellofresh.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @BeforeMethod
    public void init() {
//        initializeWebDriverWithAutoTearDown(AutoTearDown.NONE);
//        initializeWebDriverWithAutoTearDown(AutoTearDown.AFTER_METHOD);
        initializeWebDriverWithAutoTearDown();
    }

//    @AfterMethod
//    public void tearDown() {
//        tearDownDriver();
//    }

    @Test
    public void homePageTest() {
        HomePage homePage = HomePage.openNewPage();

//        homePage.validateIsRightPage();
        homePage.assertIsRightPage();
    }


}
