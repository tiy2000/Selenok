package core.url;

import core.exceptions.InvalidUsageOrConfig;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUrl {


    //region Base URL and Page Path support
    private String baseUrl = "";
    private String pagePath = null;

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
    //endregion


    //region URL parameters support
    private Map<String, UrlParameter> pathParams = new LinkedHashMap<>();
    private Map<String, UrlParameter> queryParams = new LinkedHashMap<>();

    public PageUrl addFixedPathPart(String name) {
        pathParams.put(name, null);
        return this;
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
            throw new IllegalArgumentException("Path param '" + name + "' doesn't exists");
        pathParams.get(name).setValue(value);
        return this;
    }

    public PageUrl setQueryParam(String name, String value) {
        if (!queryParams.containsKey(name))
            throw new IllegalArgumentException("Query param '" + name + "' doesn't exists");
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
            if (param != null && !param.optional && param.value == null) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(param.name);
            }
        }
    }

    private void makePathParamsString(StringBuilder sb) {
        for (Map.Entry<String, UrlParameter> entry : pathParams.entrySet()) {
            UrlParameter param = entry.getValue();
            if (param == null) {
                ensureEndsWithSlash(sb);
                sb.append(entry.getKey());
            } else {
                if (param.getValue() != null) {
                    ensureEndsWithSlash(sb);
                    sb.append(param.getValue());
                }
            }
        }
    }

    private void ensureEndsWithSlash(StringBuilder sb) {
        if (sb.charAt(sb.length() - 1) != '/') sb.append("/");
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
                sb.append(param.name).append("=").append(param.getValue());
            }
        }
    }
    //endregion


    //region Getting complete URL
    private void checkBaseUrlAndParams() {
        if (baseUrl.isEmpty()) throw new IllegalArgumentException("Base URL is not specified");
        if (pagePath == null) throw new InvalidUsageOrConfig("Page path is not specified");
        checkParamsSpecified();
    }

    public String generateUrl() {
        checkBaseUrlAndParams();
        StringBuilder sb = new StringBuilder(baseUrl);

        if (baseUrl.endsWith("/")) sb.delete(sb.length() - 1, sb.length());
        if (!pagePath.startsWith("/")) sb.append("/");
        sb.append(pagePath);

        makePathParamsString(sb);
        makeQueryParamsString(sb);

        return sb.toString();
    }
    //endregion
}
