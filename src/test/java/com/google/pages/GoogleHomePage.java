package com.google.pages;

import core.annotations.PageId;
import org.openqa.selenium.By;

public class GoogleHomePage extends GoogleBasePage<GoogleHomePage> {

    @PageId
    public static final By searchTextField = By.name("q");
    public static final By searchButton = By.xpath("(//input[@name=\"btnK\"])[2]");

    public GoogleHomePage enterSearchTextField(String text) {
        enterInputValue(searchTextField, text);
        return this;
    }

    public GoogleSearchResultPage clickSearchButton() {
        click(searchButton);
        return createPage(GoogleSearchResultPage.class);
    }

    public GoogleSearchUnsuccessfulResultPage clickSearchButtonUnsuccessful() {
        click(searchButton);
        return createPage(GoogleSearchUnsuccessfulResultPage.class);
    }

}
