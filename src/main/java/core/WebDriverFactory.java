package core;

import core.exceptions.InvalidUsageOrConfig;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    private static final String DRIVER_NAME_PROPERTY = "driver.name";
    private static final String DRIVER_CLASS_NAME_PROPERTY = "driver.className";

    public static WebDriver createNewWebDriverInstance() {
        Class<?> webDriverClass = getWebDriverClass();
        try {
            return (WebDriver) webDriverClass.newInstance();
        } catch (Exception e) {
            throw new InvalidUsageOrConfig("Can't create instance of WebDriver: " + e.getMessage());
        }
    }

    private static Class<?> getWebDriverClass() {
        String webDriverClassName = getWebDriverClassName();
        try {
            return Class.forName(webDriverClassName);
        } catch (ClassNotFoundException e) {
            throw new InvalidUsageOrConfig("Can't find the class of WebDriver [" + webDriverClassName + "]: " + e.getMessage());
        }
    }

    private static String getWebDriverClassName() {
        String driverName = TestEnvironment.getProperty(DRIVER_NAME_PROPERTY);
        if (driverName != null) return getWebDriverClassNameByDriverName(driverName);

        String className = TestEnvironment.getProperty(DRIVER_CLASS_NAME_PROPERTY);
        if (className == null) throw new InvalidUsageOrConfig("Class of WebDriver is not specified");

        return className;
    }

    private static String getWebDriverClassNameByDriverName(String driverName) {
        switch (driverName.toUpperCase()) {
            case "CHROME":
                return "org.openqa.selenium.chrome.ChromeDriver";
            case "OPERA":
                return "org.openqa.selenium.opera.OperaDriver";
            case "FIREFOX":
                return "org.openqa.selenium.firefox.FirefoxDriver";
            case "SAFARI":
                return "org.openqa.selenium.safari.SafariDriver";
            case "EDGE":
                return "org.openqa.selenium.edge.EdgeDriver";
            case "IE":
            case "INTERNET EXPLORER":
            case "INTERNETEXPLORER":
                return "org.openqa.selenium.ie.InternetExplorerDriver";
            default:
                throw new InvalidUsageOrConfig("Invalid driver name '" + driverName + "'");
        }
    }
}
