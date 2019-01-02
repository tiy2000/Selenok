package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;

@Listeners(ScreenshotListener.class)
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
        System.out.println("BaseTest.setDriver ENTER");
        driverThreadLocal.set(driver);
        BasePage.setDriver(driver);
        System.out.println("BaseTest.setDriver EXIT");
    }

    private void resetDriver() {
        System.out.println("BaseTest.resetDriver ENTER");
        driverThreadLocal.remove();
        BasePage.resetDriver();
        System.out.println("BaseTest.resetDriver EXIT");
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static boolean isDriverCreated() {
        return getDriver() != null;
    }


    public void tearDownDriver() {
        System.out.println("BaseTest.tearDownDriver ENTER");
        if (isDriverCreated()) {
            getDriver().quit();
            resetDriver();
        }
        System.out.println("BaseTest.tearDownDriver EXIT");
    }

    public void createChromeWebDriver(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
        WebDriver driver = new ChromeDriver();
        setDriver(driver);
    }


    // ===== Getting screenshot =====

    public static byte[] getScreenShotAsBytes() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }

    public static File getScreenShotAsFiles() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        } catch (SessionNotCreatedException e) {
//            logger.error(String.format("Selenium screenshot capture failed: %s", e.getMessage()));
//            e.printStackTrace();
        }
        return null;
    }


}
