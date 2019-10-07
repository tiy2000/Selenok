package com.google;

import com.google.pages.GoogleHomePage;
import core.BaseTest;
import core.annotations.WebDriverAutoInstancingByMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@WebDriverAutoInstancingByMethod
public class GoogleTest extends BaseTest {

    @Test
    public void testSearchPositive() {
        openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField("Java")
                .clickSearchButton()
                .assertIsRightPage();
    }

    @Test
    public void testSearchFieldValueIsApplied() {
        String searchText = "Java";
        String enteredText = openNewPage(GoogleHomePage.class)
                .validateIsRightPage()
                .enterSearchTextField(searchText)
                .getInputValue(GoogleHomePage.searchTextField);

        assertEquals(enteredText, searchText);
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
