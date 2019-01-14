package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    public enum AutoTearDown {NONE, AFTER_METHOD, AFTER_CLASS}

    private AutoTearDown autoTearDown = AutoTearDown.NONE;


    //region ===== Working with WebDriver instance =====

    private void setDriver(WebDriver driver) {
//        System.out.println("BaseTest.setDriver ENTER");
        BasePage.driverThreadLocal.set(driver);
//        System.out.println("BaseTest.setDriver EXIT");
    }

    private void removeDriver() {
//        System.out.println("BaseTest.removeDriver ENTER");
        BasePage.driverThreadLocal.remove();
//        System.out.println("BaseTest.removeDriver EXIT");
    }

    protected static WebDriver getDriver() {
        return BasePage.getDriver();
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

    private void initializeWebDriverViaConfig() {
        // There will be configuration reading here...
        WebDriver driver = new ChromeDriver();
        setDriver(driver);
    }

    protected void initializeWebDriverWithAutoTearDown(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
        initializeWebDriverViaConfig();
    }

    protected void initializeWebDriverWithAutoTearDown() {
        this.autoTearDown = determineAutoTearDown();
        initializeWebDriverViaConfig();
    }

    private AutoTearDown determineAutoTearDown() {
        Method caller = getCallerMethod();
        if (caller.isAnnotationPresent(BeforeClass.class)) {
            return AutoTearDown.AFTER_CLASS;
        }
        if (caller.isAnnotationPresent(BeforeMethod.class)) {
            return AutoTearDown.AFTER_METHOD;
        }
        throw new InvalidUsageOrConfig("createWebDriver() method should be called from @BeforeClass or @BeforeMethod method");
    }

    public Method getCallerMethod() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        Method method = null;
        try {
            method = this.getClass().getMethod(caller.getMethodName());
        } catch (NoSuchMethodException e) {}
        return method;
    }
    //endregion


    //region ===== Auto tearDown driver =====

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
    //endregion


    //region ===== Getting screenshot =====

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
    //endregion


    //region ===== Opening pages =====

    public  <T extends BasePage> T openNewPage(Class<? extends BasePage> pageClass) throws InvalidUsageOrConfig {
        T newPage = null;
        try {
            newPage = (T) pageClass.newInstance();
            newPage.openPage();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidUsageOrConfig(e.getMessage());
        }
        return newPage;
    }
    //endregion


    //region ===== Syntax sugar =====

    protected BaseTest given() {
        return this;
    }
    //endregion


//    @BeforeClass
//    public void init() {
//        createChromeWebDriver();
//    }
//
//    @Test
//    public void test() {
//
//    }

}
