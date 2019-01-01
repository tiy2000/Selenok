package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public enum AutoTearDown {NONE, AFTER_METHOD, AFTER_CLASS}
    private AutoTearDown autoTearDown = AutoTearDown.NONE;




    public AutoTearDown getAutoTearDown() {
        return autoTearDown;
    }

    public void setAutoTearDown(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
    }

    @AfterClass
    void autoTearDownAfterClass() {
        if (autoTearDown == AutoTearDown.AFTER_CLASS) {
            System.out.println("BaseTest.autoTearDownAfterClass");
            tearDownDriver();
        }
    }

    @AfterMethod
    void autoTearDownAfterMethod() {
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





    @BeforeMethod
    public void debugBeforeMethod() {
        createChromeWebDriver(AutoTearDown.AFTER_METHOD);
    }

    @Test
    public void debugTest() {
//        createChromeWebDriver(AutoTearDown.AFTER_METHOD);
        System.out.println(getDriver());
        System.out.println(BasePage.getDriver());
        tearDownDriver();
        System.out.println(getDriver());
        System.out.println(BasePage.getDriver());
    }

}
