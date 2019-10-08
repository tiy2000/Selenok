package com.google.pages;

import core.BasePage;
import core.annotations.BaseUrl;

public class GoogleBasePage<T extends GoogleBasePage<T>> extends BasePage<T> {

    @BaseUrl
    private final static String GOOGLE_BASE_URL = "http://google.com";

}
