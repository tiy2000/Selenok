package core;

public class UrlParameter {

    String name;
    boolean optional = false;
    String value = null;
    String defaultValue = null;

    public UrlParameter(String name) {
        this.name = name;
    }

    public UrlParameter(String name, boolean optional, String defaultValue) {
        this.name = name;
        this.optional = optional;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
