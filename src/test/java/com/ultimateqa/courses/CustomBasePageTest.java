package com.ultimateqa.courses;

import core.BasePage;
import core.BaseTest;
import core.PageBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static core.BasePage.preparePage;

public class CustomBasePageTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        initializeWebDriverWithAutoTearDown();
//        initializeWebDriverWithAutoTearDown(AutoTearDown.NONE);
    }

    @Test
    public void test1() {
//        openNewPage(CustomBasePage.class);
//        CustomBasePage basePage = preparePage(CustomBasePage.class)
//                .get();
//        System.out.println(basePage.getClass());

        PageBuilder.createPage(CustomBasePage.class)
//                .setPathParam("user", "john")
//                .setPathParam("doc", "doc1")
//                .setQueryParam("p1", "v1")
//                .setQueryParam("p2", "v2")
//                .debugPrintUrl();
                .openPage()
                .m1();

    }
}
