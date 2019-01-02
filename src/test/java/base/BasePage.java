package base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private int defaultTimeOutInSeconds = 15;
    private WebDriverWait wait = new WebDriverWait(getDriver(), getDefaultTimeOutInSeconds());

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

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static boolean isDriverCreated() {
        return getDriver() != null;
    }



    // ===== Waiting for elements

    public WebElement waitElement(By by) throws TimeoutException {
        System.out.println("BasePage.waitElement ENTER, BY: " + by.toString());
        WebElement element = wait.until(driver -> driver.findElement(by));
        System.out.println("BasePage.waitElement EXIT");
        return element;
    }

    public void waitElementDisplayed(By by) throws TimeoutException {
        wait.until(driver -> driver.findElement(by).isDisplayed());
    }

    public int getDefaultTimeOutInSeconds() {
        return defaultTimeOutInSeconds;
    }

    public void setDefaultTimeOutInSeconds(int defaultTimeOutInSeconds) {
        this.defaultTimeOutInSeconds = defaultTimeOutInSeconds;
    }




    // ===== Working with Web elements =====

    protected WebElement findElement(By by) {
        WebElement element = waitElement(by);
        return element;
//        return getDriver().findElement(by);
    }

    protected void sendKeys(By by, CharSequence...charSequences) {
        findElement(by).sendKeys(charSequences);
    }

    protected void clearAndFill(By by, CharSequence... charSequences) {
        WebElement element = findElement(by);
        element.clear();
        element.sendKeys(charSequences);
    }

    protected void click(By by) {
        findElement(by).click();
    }

    protected void selectByValue(By by, String value) {
        Select select = new Select(findElement(by));
        select.selectByValue(value);
    }

    protected void selectByIndex(By by, int index) {
        Select select = new Select(findElement(by));
        select.selectByIndex(index);
    }



    // ===== Right page validation =====

    public boolean isRightPage() {
        if (pageIdLocator != null) {
            try {
                waitElement(pageIdLocator);
            } catch (TimeoutException e) {
                return false;
            }
        } else {
            // Need to throw exception!
            throw new RuntimeException("Call isRightPage() requires the pageIdLocator, but it was'nt assigned");
        }
        return true;
    }

    public void validateIsRightPage() {
        System.out.println("BasePage.validateIsRightPage ENTER");
        if (!isRightPage()) {
            System.out.println("BasePage.validateIsRightPage EXIT (FAIL)");
            throw new InvalidPageStateException(makeMessage().toString());
        }
        System.out.println("BasePage.validateIsRightPage EXIT (PASS)");
    }

    public void assertRightPage() {
        System.out.println("BasePage.assertRightPage ENTER");
        if (!isRightPage()) {
            System.out.println("BasePage.assertRightPage ENTER (FAIL)");
            throw new AssertionError(makeMessage().toString());
        }
        System.out.println("BasePage.assertRightPage ENTER (PASS)");
    }

    private StringBuilder makeMessage() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Current page is not '")
                .append(this.getClass().getSimpleName())
                .append("' since the pageIdElement has not found\n")
                .append("pageIdElement locator: ")
                .append(pageIdLocator.toString())
                .append("\ncurrent url: ")
                .append(getDriver().getCurrentUrl())
                .append("\n");
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
