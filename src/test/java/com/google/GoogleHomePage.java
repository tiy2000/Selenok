package com.google;

import core.annotations.PageId;
import core.annotations.PagePath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
