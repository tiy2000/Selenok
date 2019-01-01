package base;

import org.openqa.selenium.WebDriver;

public class BasePage {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();




    // ===== Working with WebDriver instance =====

    static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    static void resetDriver() {
        driverThreadLocal.remove();
    }

    static public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    static public boolean isDriverCreated() {
        return getDriver() != null;
    }


}
