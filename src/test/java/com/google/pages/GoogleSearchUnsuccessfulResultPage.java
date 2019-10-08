package com.google.pages;

import core.annotations.PageId;
import org.openqa.selenium.By;

public class GoogleSearchUnsuccessfulResultPage extends GoogleBasePage<GoogleSearchUnsuccessfulResultPage> {

    @PageId
    public static final By PAGE_ID = By.xpath("//p[@role=\"heading\"]");

}
