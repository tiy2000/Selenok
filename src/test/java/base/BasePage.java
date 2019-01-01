package base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    WebDriverWait wait;
    protected int defaultTimeOutInSeconds = 15;



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





}
