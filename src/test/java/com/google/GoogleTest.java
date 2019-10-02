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
    public void testBasePage() {
        openNewPage(GoogleBasePage.class);
    }

    @Test
    public void testHomePage() {
//        openNewPage(GoogleHomePage.class);
    }
}
