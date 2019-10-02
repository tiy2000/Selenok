package core;

import core.annotations.BaseUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.time.Duration;

public abstract class BasePage<T extends BasePage<T>> {

    public BasePage() {
        parseAnnotations();
    }


    //region ===== Working with WebDriver instance =====

//    static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected static WebDriver getDriver() {
//        return driverThreadLocal.get();
        return TestEnvironment.getDriver();
    }
    //endregion


    //region ===== Opening pages =====

    protected final PageUrl pageUrl = new PageUrl();

    public String getUrl() {
        return pageUrl.getUrl();
    }

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


    //region ===== Working with annotations =====
    private void parseAnnotations() {
//        AnnotationParser parser = new AnnotationParser();
//        parser.parse(this);
//        pageUrl.setPagePath(parser.pagePath);
//        rightPageCondition = parser.rightPageCondition;

        Field baseUrlField = AnnotationFinder.findAnnotatedField(this.getClass(), BaseUrl.class);
        if (baseUrlField != null) {
//            System.out.println(baseUrlField.getName());
            try {
                baseUrlField.setAccessible(true);
                String baseUrl = (String) baseUrlField.get(this);
//                System.out.println(baseUrl);
                pageUrl.setBaseUrl(baseUrl);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //endregion


    //region ===== Waiting elements and page conditions =====

    private int defaultTimeOutInSeconds = 15;
    private WebDriverWait wait = new WebDriverWait(getDriver(), defaultTimeOutInSeconds);

    public int getDefaultTimeOutInSeconds() {
        return defaultTimeOutInSeconds;
    }

    public T setDefaultTimeOutInSeconds(int defaultTimeOutInSeconds) {
        this.defaultTimeOutInSeconds = defaultTimeOutInSeconds;
        return (T) this;
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
        return (T) this;
    }
    //endregion


    //region ===== Working with Web elements =====

    public WebElement findElement(By by) {
        return waitElement(by);
    }

//    private T sendKeys(By by, CharSequence... charSequences) {
//        findElement(by).sendKeys(charSequences);
//        return (T) this;
//    }
//
//    private T clearAndFill(By by, CharSequence... charSequences) {
//        WebElement element = waitElement(ExpectedConditions.visibilityOfElementLocated(by));
//        element.clear();
//        element.sendKeys(charSequences);
//        return (T) this;
//    }

    public T enterInputValue(By by, CharSequence... charSequences) {
        WebElement element = waitElement(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
        element.sendKeys(charSequences);
        return (T) this;
    }

    public T click(By by) {
        waitElement(ExpectedConditions.elementToBeClickable(by)).click();
        return (T) this;
    }

    public T selectByValue(By by, String value) {
        Select select = new Select(waitElement(ExpectedConditions.visibilityOfElementLocated(by)));
        select.selectByValue(value);
        return (T) this;
    }

    public T selectByIndex(By by, int index) {
        Select select = new Select(waitElement(ExpectedConditions.visibilityOfElementLocated(by)));
        select.selectByIndex(index);
        return (T) this;
    }
    //endregion


    //region ===== Right page validation =====

    private ExpectedCondition<WebElement> rightPageCondition;

    protected ExpectedCondition<WebElement> getRightPageCondition() {
        return rightPageCondition;
    }

    protected T setRightPageCondition(ExpectedCondition<WebElement> rightPageCondition) {
        this.rightPageCondition = rightPageCondition;
        return (T) this;
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
        return (T) this;
    }

    public T assertIsRightPage() throws AssertionError {
        System.out.println("BasePage.assertIsRightPage ENTER");
        if (!isRightPage()) {
            System.out.println("BasePage.assertIsRightPage EXIT (FAIL)");
            throw new AssertionError(makeMessage());
        }
        System.out.println("BasePage.assertIsRightPage EXIT (PASS)");
        return (T) this;
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
    //endregion

    //region ===== Assertions =====
    public T assertThan(ExpectedCondition expectedCondition) throws AssertionError {
        try {
            waitElement(expectedCondition);
        } catch (TimeoutException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Element condition violation\n")
                    .append("Expected: ")
                    .append(expectedCondition.toString())
                    .append("\n")
                    .append("Actual: ")
                    .append(e.getMessage())
                    .append("\n");
            throw new AssertionError(sb.toString());
        }
        return (T) this;
    }
    //endregion


    //region ===== Syntax sugar =====
    public T testScenario() {
        return (T) this;
    }

    public T then() {
        return (T) this;
    }

    public T when() {
        return (T) this;
    }

    public T and() {
        return (T) this;
    }
    //endregion

}
