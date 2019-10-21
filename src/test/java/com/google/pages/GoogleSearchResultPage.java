package com.google.pages;

import core.annotations.PageId;
import core.annotations.PagePath;
import org.openqa.selenium.By;

public class GoogleSearchResultPage extends GoogleBasePage<GoogleSearchResultPage> {

    @PagePath(loadable = true)
    private final static String PAGE_PATH = "search";

    @PageId
    private final static By PAGE_ID = By.id("resultStats");

    public GoogleSearchResultPage() {
        pageUrl.addOptionalQueryParam("q");
    }
}
