package core;

import org.testng.annotations.Test;

public class PageUrlTest {

    @Test
    public void testPageUrlCreation() {
        PageUrl pageUrl = new PageUrl("http://domain.com");
        pageUrl
                .setPagePath("dir1/dir2")
                .addPathParam("user");

    }
}