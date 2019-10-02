package core;

import org.openqa.selenium.WebDriver;

public class PageBuilder<T extends BasePage<T>> {

    private T page;

    public static <T2 extends BasePage<T2>> PageBuilder<T2> createPageBuilder(Class<T2> pageClass) throws InvalidUsageOrConfig {
        T2 page = createPageInstance(pageClass);
        return new PageBuilder<>(page);
    }

    private static <T2 extends BasePage<T2>> T2 createPageInstance(Class<T2> pageClass) throws InvalidUsageOrConfig {
        try {
            T2 page = pageClass.newInstance();
            return page;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidUsageOrConfig("Can't create instance of " + pageClass.getName() + " class (" + e.getMessage() + ")");
        }
    }

    PageBuilder(T page) {
        this.page = page;
    }

    public PageBuilder<T> setBaseUrl(String baseUrl) {
        page.pageUrl.setBaseUrl(baseUrl);
        return this;
    }

    public PageBuilder<T> setPagePath(String pagePath) {
        page.pageUrl.setPagePath(pagePath);
        return this;
    }

    public PageBuilder<T> setPathParam(String name, String value) {
        page.pageUrl.setPathParam(name, value);
        return this;
    }

    public PageBuilder<T> setQueryParam(String name, String value) {
        page.pageUrl.setQueryParam(name, value);
        return this;
    }

    public PageBuilder<T> debugPrintUrl() {
        System.out.println(page.pageUrl.getUrl());
        return this;
    }

    private WebDriver getDriver() {
        return TestEnvironment.getDriver();
    }

    public T getPage() {
        return page;
    }

    public T openPage() throws InvalidUsageOrConfig {
        getDriver().get(page.pageUrl.getUrl());
        return page;
    }
}
