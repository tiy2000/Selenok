package com.google;

import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GoogleTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        initializeWebDriverWithAutoTearDown();
//        initializeWebDriverWithAutoTearDown(AutoTearDown.NONE);
    }

    @Test
    public void testBasePage() {
        openNewPage(GoogleBasePage.class);
//        List<WebElement> elements = getDriver().findElements(By.xpath("//p[@role=\"heading\"]"));
//        System.out.println(elements.size());
    }

    @Test
    public void testSearchPositive() {
        openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField("Java")
                .clickSearchButton()
                .validateIsRightPage();
//        List<WebElement> elements = getDriver().findElements(By.xpath("//p[@role=\"heading\"]"));
//        System.out.println(elements.size());
    }

    @Test
    public void testSearchNegative() {
        openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField("kfjldjs;fkjg;sgs")
                .clickSearchButtonUnsuccessful()
                .validateIsRightPage();

//        List<WebElement> elements = getDriver().findElements(By.xpath("//p[@role=\"heading\" and contains(text(),'ничего не найдено')]"));
//        List<WebElement> elements = getDriver().findElements(By.xpath("//p[@role=\"heading\"]"));
//        System.out.println(elements.size());
//        System.out.println(elements.get(0).getText());
//        System.out.println(elements.get(1).getText());
    }
}
