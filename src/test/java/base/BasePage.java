package base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected int defaultTimeOutInSeconds = 15;
    WebDriverWait wait = new WebDriverWait(getDriver(), defaultTimeOutInSeconds);

    protected By pageIdLocator = null;

    private static String BASE_URL;
    private String pagePath;



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



    // ===== Waiting for elements

    public WebElement waitElement(By by) throws TimeoutException {
        WebElement element = wait.until(driver -> driver.findElement(by));
        return element;
    }

    public void waitElementDisplayed(By by) throws TimeoutException {
        wait.until(driver -> driver.findElement(by).isDisplayed());
    }



    // Right page validation

    public boolean isRightPage() {
        if (pageIdLocator != null) {
            try {
                waitElement(pageIdLocator);
            } catch (TimeoutException e) {
                return false;
            }
        }
        return true;
    }

    public void validateIsRightPage() {
        if (!isRightPage()) {
            throw new InvalidPageStateException(makeMessage().toString());
        }
    }

    public void assertRightPage() {
        if (!isRightPage()) {
            throw new AssertionError(makeMessage().toString());
        }
    }

    private StringBuilder makeMessage() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Page ")
                .append(this.getClass().getSimpleName())
                .append(" is not loaded\n")
                .append("since the element with locator\n")
                .append(pageIdLocator.toString())
                .append("\nnot found.");
        return sb;
    }



    // ===== Opening pages =====

    public void openPage() {
        getDriver().get(getFullPagePath());
    }

    public String getFullPagePath() {
        String fullPath = getBaseUrl();
        if (!fullPath.endsWith("/") && !getPagePath().endsWith("/")) {
            fullPath += "/";
        }
        return fullPath + getPagePath();
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

}
