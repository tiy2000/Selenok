package core;

import org.openqa.selenium.WebDriver;

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


}
