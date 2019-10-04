package com.google;

import core.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        initializeWebDriverWithAutoTearDown();
//        initializeWebDriverWithAutoTearDown(AutoTearDown.NONE);
    }

    @Test
    public void testSearchPositive() {
        openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField("Java")
                .clickSearchButton()
                .assertIsRightPage();
    }

    @Test
    public void testSearchNegative() {
        openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField("kfjldjs;fkjg;sgs")
                .clickSearchButtonUnsuccessful()
                .assertIsRightPage();
    }
}
