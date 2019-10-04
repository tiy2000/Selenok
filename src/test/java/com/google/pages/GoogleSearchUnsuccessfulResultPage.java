package com.google.pages;

import core.annotations.PageId;
import org.openqa.selenium.By;

public class GoogleSearchUnsuccessfulResultPage extends GoogleBasePage<GoogleSearchUnsuccessfulResultPage> {

    @PageId
    public static final By pageId = By.xpath("//p[@role=\"heading\"]");

}
