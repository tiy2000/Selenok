package com.google.pages;

import core.annotations.PageId;
import org.openqa.selenium.By;

public class GoogleSearchResultPage extends GoogleBasePage<GoogleSearchResultPage> {

    @PageId
    private final static By resultStats = By.id("resultStats");

}
