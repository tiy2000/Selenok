package base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage<T extends BasePage> {


    // ===== Working with WebDriver instance =====

    static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected static WebDriver getDriver() {
        return driverThreadLocal.get();
    }



    // ===== Waiting elements and page conditions =====

    private int defaultTimeOutInSeconds = 15;
    private WebDriverWait wait = new WebDriverWait(getDriver(), defaultTimeOutInSeconds);

    public int getDefaultTimeOutInSeconds() {
        return defaultTimeOutInSeconds;
    }

    public T setDefaultTimeOutInSeconds(int defaultTimeOutInSeconds) {
        this.defaultTimeOutInSeconds = defaultTimeOutInSeconds;
        return (T)this;
    }

    public WebElement waitElement(By by) throws TimeoutException {
        System.out.println("BasePage.waitElement ENTER, BY: " + by.toString());

        WebElement element = wait
                .withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));

        System.out.println("BasePage.waitElement EXIT");
        return element;
    }

    public WebElement waitElement(ExpectedCondition<WebElement> condition) throws TimeoutException {
        System.out.println("BasePage.waitByCondition ENTER, BY: " + condition.toString());

        WebElement element = wait
                .withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(condition);

        System.out.println("BasePage.waitByCondition EXIT");
        return element;
    }

    public T waitCondition(ExpectedCondition<WebElement> condition) throws TimeoutException {
        System.out.println("BasePage.waitCondition ENTER, BY: " + condition.toString());

        wait.withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(condition);

        System.out.println("BasePage.waitCondition EXIT");
        return (T)this;
    }



    // ===== Working with Web elements =====

    public WebElement findElement(By by) {
        return waitElement(by);
    }

    protected T sendKeys(By by, CharSequence...charSequences) {
        findElement(by).sendKeys(charSequences);
        return (T)this;
    }

    protected T clearAndFill(By by, CharSequence... charSequences) {
        WebElement element = findElement(by);
        element.clear();
        element.sendKeys(charSequences);
        return (T)this;
    }

    public T enterInputValue(By by, CharSequence... charSequences) {
        return clearAndFill(by, charSequences);
    }

    public T click(By by) {
        findElement(by).click();
        return (T)this;
    }

    public T selectByValue(By by, String value) {
        Select select = new Select(findElement(by));
        select.selectByValue(value);
        return (T)this;
    }

    public T selectByIndex(By by, int index) {
        Select select = new Select(findElement(by));
        select.selectByIndex(index);
        return (T)this;
    }



    // ===== Right page validation =====

    private ExpectedCondition<WebElement> rightPageCondition;

    protected ExpectedCondition<WebElement> getRightPageCondition() {
        return rightPageCondition;
    }

    protected T setRightPageCondition(ExpectedCondition<WebElement> rightPageCondition) {
        this.rightPageCondition = rightPageCondition;
        return (T)this;
    }

    public boolean isRightPage() throws InvalidUsageOrConfig {
        if (rightPageCondition != null) {
            try {
                waitCondition(rightPageCondition);
            } catch (TimeoutException e) {
                return false;
            }
        } else {
            // Need to throw exception!
            throw new InvalidUsageOrConfig("Call isRightPage() requires the rightPageCondition, but it was'nt assigned");
        }
        return true;
    }

    public T validateIsRightPage() throws InvalidPageStateException {
        System.out.println("BasePage.validateIsRightPage ENTER");
        if (!isRightPage()) {
            System.out.println("BasePage.validateIsRightPage EXIT (FAIL)");
            throw new InvalidPageStateException(makeMessage());
        }
        System.out.println("BasePage.validateIsRightPage EXIT (PASS)");
        return (T)this;
    }

    public T assertRightPage() throws AssertionError {
        System.out.println("BasePage.assertRightPage ENTER");
        if (!isRightPage()) {
            System.out.println("BasePage.assertRightPage ENTER (FAIL)");
            throw new AssertionError(makeMessage());
        }
        System.out.println("BasePage.assertRightPage ENTER (PASS)");
        return (T)this;
    }

    private String makeMessage() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Current page is not '")
                .append(this.getClass().getSimpleName())
                .append("' since the rightPageCondition has not match\n")
                .append("rightPageCondition: ")
                .append(rightPageCondition.toString())
                .append("\ncurrent url: ")
                .append(getDriver().getCurrentUrl())
                .append("\n");
        return sb.toString();
    }



    // ===== Opening pages =====

    private static String BASE_URL;
    private String pagePath;

    public T openPage() {
        getDriver().get(getFullPagePath());
        return (T)this;
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

    protected void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    protected static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

}
