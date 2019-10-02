package com.ultimateqa.courses;

import core.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CustomBasePageTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        initializeWebDriverWithAutoTearDown();
//        initializeWebDriverWithAutoTearDown(AutoTearDown.NONE);
    }

    @Test
    public void testPreparePage() {
        preparePage(CustomBasePage.class)
                .setPathParam("user", "john")
//                .setPathParam("doc", "doc1")
//                .setQueryParam("p1", "v1")
//                .setQueryParam("p2", "v2")
                .debugPrintUrl();
//                .openPage()
//                .m1();
    }
}
