package base;

import org.openqa.selenium.WebDriver;

public class TestEnvironment {

    //region ===== Working with WebDriver instance =====

    static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    //endregion


}
