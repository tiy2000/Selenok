package core;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PageUrlTest {

    @Test
    public void testPageUrlCreation() {
        PageUrl pageUrl = new PageUrl("http://domain.com");
        pageUrl
                .setRelativePath("dir1/dir2")
                .addPathParam("user");

    }
}