package com.hellofresh.pages;

import base.BasePage;

import java.util.concurrent.TimeUnit;

public class HelloFreshBasePage<T extends BasePage> extends BasePage<T> {

    static {
        setBaseUrl("https://www.hellofresh.com");
    }

    public HelloFreshBasePage() {
        setDefaultTimeOutInSeconds(5);
        getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//        getDriver().manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
    }
}
