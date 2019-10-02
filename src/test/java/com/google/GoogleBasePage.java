package com.google;

import core.BasePage;
import core.annotations.BaseUrl;

public class GoogleBasePage<T extends GoogleBasePage<T>> extends BasePage<GoogleBasePage<T>> {

    @BaseUrl
    private final static String baseUrl = "http://google.com";

}
