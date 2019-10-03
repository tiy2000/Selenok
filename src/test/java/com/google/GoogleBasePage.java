package com.google;

import core.BasePage;
import core.annotations.BaseUrl;
import core.annotations.PagePath;

public class GoogleBasePage<T extends GoogleBasePage<T>> extends BasePage<T> {

    @BaseUrl
    private final static String baseUrl = "http://google.com";

}
