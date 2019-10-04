package core;

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


    //region ===== Working with WebDriver instance =====

    private void setDriver(WebDriver driver) {
        TestEnvironment.setDriver(driver);
    }

    private void removeDriver() {
        TestEnvironment.removeDriver();
    }

    protected static WebDriver getDriver() {
        return TestEnvironment.getDriver();
    }

    protected static boolean isDriverCreated() {
        return TestEnvironment.isDriverCreated();
    }


    protected void tearDownDriver() {
        if (isDriverCreated()) {
            getDriver().quit();
            removeDriver();
        }
    }

    protected void initializeWebDriver() {
        // There will be configuration reading here...
        WebDriver driver = new ChromeDriver();
        setDriver(driver);
    }

    //region ----- Auto tearDown settings -----
    public enum AutoTearDown {
        NONE, AFTER_METHOD, AFTER_CLASS
    }

    private AutoTearDown autoTearDown = AutoTearDown.NONE;

    protected void initializeWebDriverWithAutoTearDown(AutoTearDown autoTearDown) {
        this.autoTearDown = autoTearDown;
        initializeWebDriver();
    }

    protected void initializeWebDriverWithAutoTearDown() {
        this.autoTearDown = determineAutoTearDown();
        initializeWebDriver();
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
        } catch (NoSuchMethodException e) {
        }
        return method;
    }
    //endregion
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

    protected <T extends BasePage<T>> PageBuilder<T> preparePage(Class<T> pageClass) throws InvalidUsageOrConfig {
        return PageBuilder.createPageBuilder(pageClass);
    }

    protected <T extends BasePage<T>> T openNewPage(Class<T> pageClass) throws InvalidUsageOrConfig {
        return preparePage(pageClass).openPage();
    }
    //endregion


    //region ===== Syntax sugar =====

    protected BaseTest given() {
        return this;
    }
    //endregion


    //region ===== FOR DEBUG ONLY =====


//    @BeforeClass
//    public void init() {
//        initializeWebDriverWithAutoTearDown();
//    }
//
//    @Test
//    public void test() {
//        step1();
//        step2();
//    }
//
//    @Step
//    private void step1() {
//        step11();
//        step12();
//    }
//
//    @Step
//    private void step11() {
//
//    }
//
//    @Step
//    private void step12() {
//
//    }
//
//    @Step
//    private void step2() {
//
//    }

    //endregion

}
