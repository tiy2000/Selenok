package core;

import core.exceptions.InvalidUsageOrConfig;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class TestEnvironment {

    //region ===== Working with WebDriver instance =====
    static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    static WebDriver getDriver() {
        if (!isDriverCreated()) throw new InvalidUsageOrConfig("Web driver instance wasn't created");
        return driverThreadLocal.get();
    }

    static boolean isDriverCreated() {
        return driverThreadLocal.get() != null;
    }

    static void removeDriver() {
        driverThreadLocal.remove();
    }
    //endregion


    //region ===== Determine WebDriver class =====

    private static final String DRIVER_CLASS_NAME_PROPERTY = "driver.className";

    static Class<?> getWebDriverClass() {
        String webDriverClassName = getProperty(DRIVER_CLASS_NAME_PROPERTY);
        if (webDriverClassName == null) {
            throw new InvalidUsageOrConfig("Class name of WebDriver is not specified");
        }
        try {
            return Class.forName(webDriverClassName);
        } catch (Exception e) {
            throw new InvalidUsageOrConfig("Can't find the class of WebDriver: " + e.getMessage());
        }
    }
    //endregion

    //region ===== Configuration =====

    private static final String CONFIG_FILE = "/config.properties";
    private static Properties customProperties = new Properties();

    static {
        readConfig();
    }

    public static void readConfig() {
        InputStream resourceAsStream = TestEnvironment.class.getResourceAsStream(CONFIG_FILE);
        if (resourceAsStream != null) {
            try {
                readCustomProperties(resourceAsStream);
            } catch (IOException ignored) {
            }
        }
    }

    public static void readCustomProperties(URL url) throws IOException {
        readCustomProperties(new File(url.getPath()));
    }

    public static void readCustomProperties(File file) throws IOException {
        readCustomProperties(new FileInputStream(file));
    }

    public static void readCustomProperties(InputStream stream) throws IOException {
        customProperties.load(stream);
    }

    public static String getProperty(String propertyName) {
        String property = System.getProperty(propertyName);
        if (property != null) return property;

        return customProperties.getProperty(propertyName);
    }
    //endregion

}
