package core;

import core.annotations.BaseUrl;
import core.annotations.PageId;
import core.annotations.PagePath;
import core.exceptions.InvalidPageStateException;
import core.exceptions.InvalidUsageOrConfig;
import core.url.PageUrl;
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

    protected static WebDriver getDriver() {
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
        parseBaseUrlAnnotation();
        parsePagePathAnnotation();
        parsePageIdAnnotation();
    }

    private void parseBaseUrlAnnotation() {
        String baseUrl = (String) ReflectionUtils.getAnnotatedFieldValue(this, BaseUrl.class);
        if (baseUrl != null) pageUrl.setBaseUrl(baseUrl);
    }

    private void parsePagePathAnnotation() {
        String pagePath = (String) ReflectionUtils.getAnnotatedFieldValue(this, PagePath.class);
        if (pagePath != null) pageUrl.setPagePath(pagePath);
    }

    private void parsePageIdAnnotation() {
        Field pageIdField = ReflectionUtils.findAnnotatedField(this.getClass(), PageId.class);
        if (pageIdField == null) return;

        if (pageIdField.getType().equals(By.class)) {
            parsePageIdAnnotationWithLocatorField(pageIdField);
        } else if (pageIdField.getType().equals(ExpectedCondition.class)) {
            rightPageCondition = ReflectionUtils.getFieldValue(pageIdField, this, ExpectedCondition.class);
        } else {
            throw new InvalidUsageOrConfig("@PageId annotation should be applied to By or ExpectedCondition field type only");
        }
    }

    private void parsePageIdAnnotationWithLocatorField(Field pageIdField) {
        PageId pageIdAnnotation = pageIdField.getAnnotation(PageId.class);
        PageId.Condition condition = pageIdAnnotation.condition();
        switch (condition) {
            case ELEMENT_PRESENTED:
                By pageIdLocator = ReflectionUtils.getFieldValue(pageIdField, this, By.class);
                if (pageIdLocator != null)
                    rightPageCondition = ExpectedConditions.presenceOfElementLocated(pageIdLocator);
                break;
            default:
                throw new InvalidUsageOrConfig("Unsupported condition in @PageId");
        }
    }

    //endregion


    //region ===== Waiting elements and page conditions =====

    private int defaultTimeOutInSeconds = 15;
    private WebDriverWait wait = null;

    public WebDriverWait getWait() {
        if (wait == null) wait = new WebDriverWait(getDriver(), defaultTimeOutInSeconds);
        return wait;
    }

    public int getDefaultTimeOutInSeconds() {
        return defaultTimeOutInSeconds;
    }

    public T setDefaultTimeOutInSeconds(int defaultTimeOutInSeconds) {
        this.defaultTimeOutInSeconds = defaultTimeOutInSeconds;
        return (T) this;
    }


    public WebElement waitElement(By by) throws TimeoutException {
        WebElement element = getWait()
                .withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }

    public WebElement waitElement(ExpectedCondition<WebElement> condition) throws TimeoutException {
        WebElement element = getWait()
                .withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(condition);
        return element;
    }

    public T waitCondition(ExpectedCondition<WebElement> condition) throws TimeoutException {
        getWait().withTimeout(Duration.ofSeconds(defaultTimeOutInSeconds))
                .until(condition);
        return (T) this;
    }
    //endregion


    //region ===== Working with Web elements =====

    public WebElement findElement(By by) {
        return waitElement(by);
    }

    public T enterInputValue(By by, CharSequence... charSequences) {
        WebElement element = waitElement(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
        element.sendKeys(charSequences);
        return (T) this;
    }

    public T enterInputValue(ExpectedCondition<WebElement> condition, CharSequence... charSequences) {
        WebElement element = waitElement(condition);
        element.clear();
        element.sendKeys(charSequences);
        return (T) this;
    }

    public String getInputValue(By by) {
        WebElement element = waitElement(ExpectedConditions.visibilityOfElementLocated(by));
        return element.getAttribute("value");
    }

    public String getInputValue(ExpectedCondition<WebElement> condition) {
        WebElement element = waitElement(condition);
        return element.getAttribute("value");
    }

    public T click(By by) {
        waitElement(ExpectedConditions.elementToBeClickable(by)).click();
        return (T) this;
    }

    public T click(ExpectedCondition<WebElement> condition) {
        waitElement(condition).click();
        return (T) this;
    }

    public Select getSelect(By by) {
        return new Select(waitElement(ExpectedConditions.visibilityOfElementLocated(by)));
    }

    public Select getSelect(ExpectedCondition<WebElement> condition) {
        return new Select(waitElement(condition));
    }

    public T selectByValue(By by, String value) {
        Select select = getSelect(by);
        select.selectByValue(value);
        return (T) this;
    }

    public T selectByValue(ExpectedCondition<WebElement> condition, String value) {
        Select select = getSelect(condition);
        select.selectByValue(value);
        return (T) this;
    }

    public T selectByIndex(By by, int index) {
        Select select = getSelect(by);
        select.selectByIndex(index);
        return (T) this;
    }

    public T selectByIndex(ExpectedCondition<WebElement> condition, int index) {
        Select select = getSelect(condition);
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
            throw new InvalidUsageOrConfig("Call isRightPage() requires the rightPageCondition, but it's not assigned");
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
