package core;

public class PageBuilder<T extends BasePage<T>> {

    private T page;

    public static <T2 extends BasePage<T2>> PageBuilder<T2> createPage(Class<T2> pageClass) {
        T2 page = createPageInstance(pageClass);
        return new PageBuilder<>(page);
    }

    private static <T2 extends BasePage<T2>> T2 createPageInstance(Class<T2> pageClass) {
        try {
            T2 page = pageClass.newInstance();
            return page;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Can't create instance of " + pageClass.getName() + " class");
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

    public T openPage() {
        page.openPage();
        return page;
    }
}
