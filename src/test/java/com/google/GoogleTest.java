package com.google;

import com.google.pages.GoogleHomePage;
import com.google.pages.GoogleSearchResultPage;
import com.google.pages.GoogleSearchUnsuccessfulResultPage;
import core.BaseTest;
import core.annotations.WebDriverAutoInstancingByMethod;
import core.exceptions.InvalidUsageOrConfig;
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
                .getInputValue(GoogleHomePage.SEARCH_TEXT_FIELD);

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

    @Test
    public void testSearchWithQueryParam() {
        preparePage(GoogleSearchResultPage.class)
                .setQueryParam("q", "Java")
                .openPage()
                .assertIsRightPage();
    }

    @Test(expectedExceptions = InvalidUsageOrConfig.class)
    public void testTryOpenPageWithoutPagePathSpecified() {
        openNewPage(GoogleSearchUnsuccessfulResultPage.class)
                .validateIsRightPage();
    }
}
