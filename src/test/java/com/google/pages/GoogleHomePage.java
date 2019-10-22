package com.google.pages;

import core.annotations.PageId;
import core.annotations.PagePath;
import org.openqa.selenium.By;

public class GoogleHomePage extends GoogleBasePage<GoogleHomePage> {

    @PagePath()
    private final static String PAGE_PATH = "/";

    @PageId
    public static final By SEARCH_TEXT_FIELD = By.name("q");
    public static final By SEARCH_BUTTON = By.xpath("(//input[@name='btnK'])[2]");

    public GoogleHomePage enterSearchTextField(String text) {
        enterInputValue(SEARCH_TEXT_FIELD, text);
        return this;
    }

    public GoogleSearchResultPage clickSearchButton() {
        click(SEARCH_BUTTON);
//        return createPage(GoogleSearchResultPage.class);
        return createPageByReturnType();
    }

    public GoogleSearchUnsuccessfulResultPage clickSearchButtonUnsuccessful() {
        click(SEARCH_BUTTON);
//        return createPage(GoogleSearchUnsuccessfulResultPage.class);
        return createPageByReturnType();
    }
}
