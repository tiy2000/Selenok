package core;

public interface UrlBuilder {

    UrlBuilder setBaseUrl(String url);

    UrlBuilder setRelativePath(String path);

    UrlBuilder setAbsoluteUrl(String url);

    UrlBuilder addPathParam(String name);

    UrlBuilder addOptionalPathParam(String name, String defaultValue);

    UrlBuilder addQueryParam(String name);

    UrlBuilder addOptionalQueryParam(String name, String defaultValue);

}
