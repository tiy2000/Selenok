package core;

import core.annotations.WebDriverAutoInstancingByClass;
import core.annotations.WebDriverAutoInstancingByMethod;
import core.exceptions.InvalidUsageOrConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    public BaseTest() {
        parseAutoWebDriverInstancingLevelAnnotations();
    }

    //region ===== Working with WebDriver instance, initialize and tearDown =====
    protected void initializeWebDriver() {
        TestEnvironment.setDriver(WebDriverFactory.createNewWebDriverInstance());
    }

    protected void tearDownDriver() {
        if (isDriverCreated()) {
            getDriver().quit();
            TestEnvironment.removeDriver();
        }
    }

    protected static WebDriver getDriver() {
        return TestEnvironment.getDriver();
    }

    protected static boolean isDriverCreated() {
        return TestEnvironment.isDriverCreated();
    }
    //endregion


    //region ===== Auto initialize driver =====
    public enum WebDriverAutoInstancingMode {
        NONE, METHOD, CLASS
    }

    private WebDriverAutoInstancingMode webDriverAutoInitializeMode = WebDriverAutoInstancingMode.NONE;

    private void parseAutoWebDriverInstancingLevelAnnotations() {
        Annotation annotation = ReflectionUtils.findClassAnnotations(this.getClass(), WebDriverAutoInstancingByClass.class, WebDriverAutoInstancingByMethod.class);
        if (annotation != null) {
            if (WebDriverAutoInstancingByMethod.class.isInstance(annotation)) {
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


    //region ===== Auto tearDown driver =====
    private WebDriverAutoInstancingMode webDriverAutoTearDownMode = WebDriverAutoInstancingMode.NONE;

    protected void initializeWebDriverWithAutoTearDown(WebDriverAutoInstancingMode webDriverAutoInstancingMode) {
        this.webDriverAutoTearDownMode = webDriverAutoInstancingMode;
        initializeWebDriver();
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


    //region ===== Advanced auto tearDown technique =====
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
        throw new InvalidUsageOrConfig("initializeWebDriverWithAutoTearDown() method should be called from @BeforeClass or @BeforeMethod method");
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


    //region ===== Opening pages =====
    protected <T extends BasePage<T>> PageBuilder<T> preparePage(Class<T> pageClass) throws InvalidUsageOrConfig {
        return PageBuilder.createPageBuilder(pageClass);
    }

    protected <T extends BasePage<T>> T createPage(Class<T> pageClass) throws InvalidUsageOrConfig {
        return preparePage(pageClass).getPage();
    }

    protected <T extends BasePage<T>> T openNewPage(Class<T> pageClass) throws InvalidUsageOrConfig {
        return preparePage(pageClass).openPage();
    }
    //endregion


    //region ===== Getting screenshot =====
    @Nullable
    protected static byte[] getScreenShotAsBytes() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (SessionNotCreatedException e) {
            return null;
        }
    }

    @Nullable
    protected static File getScreenShotAsFiles() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        } catch (SessionNotCreatedException e) {
            return null;
        }
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
