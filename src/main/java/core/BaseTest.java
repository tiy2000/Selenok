package core;

import core.annotations.WebDriverAutoInstancingByClass;
import core.annotations.WebDriverAutoInstancingByMethod;
import core.exceptions.InvalidUsageOrConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    public BaseTest() {
        parseAutoWebDriverInstancingLevelAnnotations();
    }

    //region ===== Working with WebDriver instance =====

    protected void tearDownDriver() {
        if (isDriverCreated()) {
            getDriver().quit();
            removeDriver();
        }
    }

    protected void initializeWebDriver() {
        setDriver(WebDriverFactory.createNewWebDriverInstance());
    }

    //region ----- Storing the reference to WebDriver instance into TestEnvironment -----
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
    //endregion

    //endregion


    //region ===== Initialize driver =====

    public enum WebDriverAutoInstancingMode {
        NONE, METHOD, CLASS
    }

    //region ---- Auto Initialize driver -----

    private WebDriverAutoInstancingMode webDriverAutoInitializeMode = WebDriverAutoInstancingMode.NONE;

    private void parseAutoWebDriverInstancingLevelAnnotations() {
        Annotation annotation = ReflectionUtils.findClassAnnotations(this.getClass(), WebDriverAutoInstancingByClass.class, WebDriverAutoInstancingByMethod.class);
        if (annotation != null) {
            if (annotation.getClass().equals(WebDriverAutoInstancingByMethod.class)) {
                webDriverAutoInitializeMode = WebDriverAutoInstancingMode.METHOD;
            } else {
                webDriverAutoInitializeMode = WebDriverAutoInstancingMode.CLASS;
            }
        }
    }

    @BeforeClass
    protected void autoInitializeBeforeClass() {
        if (webDriverAutoInitializeMode == WebDriverAutoInstancingMode.CLASS) {
            initializeWebDriverWithAutoTearDown(WebDriverAutoInstancingMode.CLASS);
        }
    }

    @BeforeMethod
    protected void autoInitializeBeforeMethod() {
        if (webDriverAutoInitializeMode == WebDriverAutoInstancingMode.METHOD) {
            initializeWebDriverWithAutoTearDown(WebDriverAutoInstancingMode.METHOD);
        }
    }

    //endregion

    //region ----- Auto tearDown settings -----

    private WebDriverAutoInstancingMode webDriverAutoTearDownMode = WebDriverAutoInstancingMode.NONE;

    protected void initializeWebDriverWithAutoTearDown(WebDriverAutoInstancingMode webDriverAutoInstancingMode) {
        this.webDriverAutoTearDownMode = webDriverAutoInstancingMode;
        initializeWebDriver();
    }

    protected void initializeWebDriverWithAutoTearDown() {
        this.webDriverAutoTearDownMode = determineAutoTearDown();
        initializeWebDriver();
    }

    private WebDriverAutoInstancingMode determineAutoTearDown() {
        Method caller = getCallerMethod();
        if (caller.isAnnotationPresent(BeforeClass.class)) {
            return WebDriverAutoInstancingMode.CLASS;
        }
        if (caller.isAnnotationPresent(BeforeMethod.class)) {
            return WebDriverAutoInstancingMode.METHOD;
        }
        throw new InvalidUsageOrConfig("createWebDriver() method should be called from @BeforeClass or @BeforeMethod method");
    }

    private Method getCallerMethod() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        Method method = null;
        try {
            method = this.getClass().getMethod(caller.getMethodName());
        } catch (NoSuchMethodException ignored) {
        }
        return method;
    }
    //endregion
    //endregion


    //region ===== Auto tearDown driver =====

    protected WebDriverAutoInstancingMode getWebDriverAutoTearDownMode() {
        return webDriverAutoTearDownMode;
    }

    protected void setWebDriverAutoTearDownMode(WebDriverAutoInstancingMode webDriverAutoTearDownMode) {
        this.webDriverAutoTearDownMode = webDriverAutoTearDownMode;
    }

    @AfterClass
    protected void autoTearDownAfterClass() {
        if (webDriverAutoTearDownMode == WebDriverAutoInstancingMode.CLASS) {
            System.out.println("BaseTest.autoTearDownAfterClass");
            tearDownDriver();
        }
    }

    @AfterMethod
    protected void autoTearDownAfterMethod() {
        if (webDriverAutoTearDownMode == WebDriverAutoInstancingMode.METHOD) {
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
    protected BaseTest testScenario() {
        return this;
    }

    protected BaseTest given() {
        return this;
    }

    protected BaseTest when() {
        return this;
    }

    protected BaseTest then() {
        return this;
    }

    protected BaseTest and() {
        return this;
    }
    //endregion

}
