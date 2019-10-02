package com.ultimateqa.courses;

import core.BasePage;

public class CustomBasePage extends BasePage<CustomBasePage> {

//    @PageUrl
//    static final String BASE_URL = "https://courses.ultimateqa.com/";

    public CustomBasePage() {
        super();
//        setBaseUrl("https://courses.ultimateqa.com/");
//        setPagePath("");
        pageUrl.setBaseUrl("https://courses.ultimateqa.com/")
                .setPagePath("/")
                .addPathParam("user")
                .addFixedPathPart("part")
                .addOptionalPathParam("doc")
//                .addQueryParam("p1")
                .addOptionalQueryParam("p2");
    }

    public void m1() {

    }
}
