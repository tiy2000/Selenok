package com.google.pages;

import core.annotations.PageId;
import core.annotations.PagePath;
import core.url.PageUrl;
import org.openqa.selenium.By;

public class GoogleSearchResultPage extends GoogleBasePage<GoogleSearchResultPage> {

    @PagePath(templates = PageUrlTemplate.class)
    private final static String PAGE_PATH = "search";

    @PageId
    private final static By PAGE_ID = By.id("resultStats");


    public static class PageUrlTemplate implements PageUrl.Template {

        @Override
        public void apply(PageUrl pageUrl) {
            pageUrl.addOptionalQueryParam("q");
        }
    }
}

