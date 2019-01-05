package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;

@Listeners(ScreenshotListener.class)
public abstract class BaseTest {

//    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public enum AutoTearDown {NONE, AFTER_METHOD, AFTER_CLASS}
    private AutoTearDown autoTearDown = AutoTearDown.NONE;



    // ===== Working with WebDriver instance =====

    private void setDriver(WebDriver driver) {
        System.out.println("BaseTest.setDriver ENTER");
//        driverThreadLocal.set(driver);
//        BasePage.setDriver(driver);
        BasePage.driverThreadLocal.set(driver);
        System.out.println("BaseTest.setDriver EXIT");
    }

    private void removeDriver() {
        System.out.println("BaseTest.removeDriver ENTER");
//        driverThreadLocal.remove();
//        BasePage.removeDriver();
        BasePage.driverThreadLocal.remove();
        System.out.println("BaseTest.removeDriver EXIT");
    }

    protected static WebDriver getDriver() {
        return BasePage.getDriver();
//        return driverThreadLocal.get();
    }

    protected static boolean isDriverCreated() {
        return getDriver() != null;
    }


    protected void tearDownDriver() {
        System.out.println("BaseTest.tearDownDriver ENTER");
        if (isDriverCreated()) {
            getDriver().quit();
            removeDriver();
        }
        System.out.println("BaseTest.tearDownDriver EXIT");
    }

    protected void createChromeWebDriver(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
        WebDriver driver = new ChromeDriver();
        setDriver(driver);
    }



    // ===== Auto tearDown driver =====

    protected AutoTearDown getAutoTearDown() {
        return autoTearDown;
    }

    protected void setAutoTearDown(AutoTearDown autoTearDown) {
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




    // ===== Getting screenshot =====

    protected static byte[] getScreenShotAsBytes() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }

    protected static File getScreenShotAsFiles() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }


}
