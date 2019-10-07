package core;

import core.exceptions.InvalidUsageOrConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
    static final Class<?> getWebDriverClass() {
        // Config files will read here...
        String webDriverClassName = "org.openqa.selenium.chrome.ChromeDriver";
        try {
            return Class.forName(webDriverClassName);
        } catch (ClassNotFoundException e) {
            throw new InvalidUsageOrConfig("Can't find the class of WebDriver: " + e.getMessage());
        }
    }
    //endregion

    //region ===== Reading configuration =====

    static {
        readConfig();
    }

    private static void readConfig() {
        // TODO: Need to implement!
    }
    //endregion

}
