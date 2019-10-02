package core;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUrl {

    String baseUrl = "";
    String pagePath = "";

    Map<String, UrlParameter> pathParams = new LinkedHashMap<>();
    Map<String, UrlParameter> queryParams = new LinkedHashMap<>();

    public PageUrl() {
    }

    public PageUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public PageUrl setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public PageUrl setPagePath(String pagePath) {
        this.pagePath = pagePath;
        return this;
    }

    public String getPagePath() {
        return pagePath;
    }

    public PageUrl addPathParam(String name) {
        pathParams.put(name, new UrlParameter(name));
        return this;
    }

    public PageUrl addOptionalPathParam(String name) {
        pathParams.put(name, new UrlParameter(name, true, null));
        return this;
    }

    public PageUrl addOptionalPathParam(String name, String defaultValue) {
        pathParams.put(name, new UrlParameter(name, true, defaultValue));
        return this;
    }

    public PageUrl addQueryParam(String name) {
        queryParams.put(name, new UrlParameter(name));
        return this;
    }

    public PageUrl addOptionalQueryParam(String name) {
        queryParams.put(name, new UrlParameter(name, true, null));
        return this;
    }

    public PageUrl addOptionalQueryParam(String name, String defaultValue) {
        queryParams.put(name, new UrlParameter(name, true, defaultValue));
        return this;
    }

    public PageUrl setPathParam(String name, String value) {
        if (!pathParams.containsKey(name))
            throw new IllegalArgumentException("Path param '" + name + "doesn't exists");
        pathParams.get(name).setValue(value);
        return this;
    }

    public PageUrl setQueryParam(String name, String value) {
        if (!queryParams.containsKey(name))
            throw new IllegalArgumentException("Query param '" + name + "doesn't exists");
        queryParams.get(name).setValue(value);
        return this;
    }


    private void checkParamsSpecified() {
        StringBuilder sb = new StringBuilder();
        checkParamsSpecified(pathParams, sb);
        checkParamsSpecified(queryParams, sb);
        if (sb.length() > 0) {
            throw new IllegalArgumentException("No parameter(s) specified [" + sb.toString() + "]");
        }
    }

    private void checkParamsSpecified(Map<String, UrlParameter> params, StringBuilder sb) {
        for (UrlParameter param : params.values()) {
            if (!param.optional && param.value == null) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(param.name);
            }
        }
    }

    private void check() {
        if (baseUrl.isEmpty()) throw new IllegalArgumentException("Base URL is not specified");
        checkParamsSpecified();
    }

    private void makePathParamsString(StringBuilder sb) {
        for (UrlParameter param : pathParams.values()) {
            if (param.getValue() != null) {
                if (sb.charAt(sb.length() - 1) != '/') sb.append("/");
                sb.append(param.getValue());
            }
        }
    }

    private void makeQueryParamsString(StringBuilder sb) {
        boolean isFirst = true;
        for (UrlParameter param : queryParams.values()) {
            if (param.getValue() != null) {
                if (isFirst) {
                    sb.append("?");
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                sb.append(param.name)
                        .append("=")
                        .append(param.getValue());
            }
        }
    }

    public String getUrl() {
        check();
        StringBuilder url = new StringBuilder(baseUrl);
        if (baseUrl.endsWith("/")) url.delete(url.length() - 1, url.length());
        if (!pagePath.startsWith("/")) url.append("/");
        url.append(pagePath);
        makePathParamsString(url);
        makeQueryParamsString(url);
        return url.toString();
    }
}
