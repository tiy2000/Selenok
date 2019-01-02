package com.hellofresh.pages;

import base.BasePage;

public class HelloFreshBasePage extends BasePage {

    static {
        setBaseUrl("https://www.hellofresh.com");
    }

    public HelloFreshBasePage() {
        setDefaultTimeOutInSeconds(5);
    }
}
