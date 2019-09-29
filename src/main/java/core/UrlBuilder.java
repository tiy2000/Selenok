package core;

public interface UrlBuilder {

    void setBaseUrl(String url);

    void setRelativePath(String path);

    void setAbsoluteUrl(String url);

    UrlBuilder addPathParam(String name);

    UrlBuilder addOptionalPathParam(String name, String defaultValue);

    UrlBuilder addQueryParam(String name);

    UrlBuilder addOptionalQueryParam(String name, String defaultValue);

}
