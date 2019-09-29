package base;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageUrl implements UrlBuilder {

    String baseUrl;
    String relativePath = "";
    String absoluteUrl = "";

    Map<String, UrlParameter> pathParams = new LinkedHashMap<>();
    Map<String, UrlParameter> queryParams = new LinkedHashMap<>();

    public PageUrl() {
    }

    public PageUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    @Override
    public UrlBuilder addPathParam(String name) {
        pathParams.put(name, new UrlParameter(name));
        return this;
    }

    @Override
    public UrlBuilder addOptionalPathParam(String name, String defaultValue) {
        pathParams.put(name, new UrlParameter(name, true, defaultValue));
        return this;
    }

    @Override
    public UrlBuilder addQueryParam(String name) {
        queryParams.put(name, new UrlParameter(name));
        return this;
    }

    @Override
    public UrlBuilder addOptionalQueryParam(String name, String defaultValue) {
        queryParams.put(name, new UrlParameter(name, true, defaultValue));
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


    public String getUrl() {
        return null;
    }
}
