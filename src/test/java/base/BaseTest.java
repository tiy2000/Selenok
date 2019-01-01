package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public enum AutoTearDown {NONE, AFTER_METHOD, AFTER_CLASS}
    private AutoTearDown autoTearDown = AutoTearDown.NONE;



    // ===== Working with WebDriver instance =====

    public AutoTearDown getAutoTearDown() {
        return autoTearDown;
    }

    public void setAutoTearDown(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
    }

    @AfterClass
    protected void autoTearDownAfterClass() {
        if (autoTearDown == AutoTearDown.AFTER_CLASS) {
            System.out.println("BaseTest.autoTearDownAfterClass");
            tearDownDriver();
        }
    }

    @AfterMethod
    protected void autoTearDownAfterMethod() {
        if (autoTearDown == AutoTearDown.AFTER_METHOD) {
            System.out.println("BaseTest.autoTearDownAfterMethod");
            tearDownDriver();
        }
    }

    private void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
        BasePage.setDriver(driver);
    }

    private void resetDriver() {
        driverThreadLocal.remove();
        BasePage.resetDriver();
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public boolean isDriverCreated() {
        return getDriver() != null;
    }


    public void tearDownDriver() {
        if (isDriverCreated()) {
            getDriver().quit();
            resetDriver();
        }
    }

    public void createChromeWebDriver(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
        WebDriver driver = new ChromeDriver();
        setDriver(driver);
    }

}
