package com.hellofresh.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage extends HelloFreshBasePage {


    public static final By FIRST_NAME_INPUT = By.id("first-name-input");
    public static final By LAST_NAME_INPUT = By.id("last-name-input");
    public static final By EMAIL_INPUT = By.id("email-input");
    public static final By PASSWORD_INPUT = By.id("password-input");
    public static final By MONTH_INPUT = By.id("month-input");
    public static final By DAY_INPUT = By.id("day-input");
    public static final By YEAR_INPUT = By.id("year-input");
    public static final By REGISTRATION_BUTTON = By.id("register-button");

    public InvitePage fillRegistrationFields() {
//        driver.findElement(By.id("gender-input")).findElement(By.cssSelector("[id='gender-input'] :nth-child(2)")).click();
        Select select = new Select(getDriver().findElement(By.id("gender-input")));
        select.selectByValue("male");

        getDriver().findElement(FIRST_NAME_INPUT).sendKeys("Alex");
        getDriver().findElement(LAST_NAME_INPUT).sendKeys("Bow");
        getDriver().findElement(EMAIL_INPUT).sendKeys("qwerty56@nono.to");
        getDriver().findElement(PASSWORD_INPUT).sendKeys("12345678");
        getDriver().findElement(MONTH_INPUT).sendKeys("12");
        getDriver().findElement(DAY_INPUT).sendKeys("20");
        getDriver().findElement(YEAR_INPUT).sendKeys("2000");

//        getDriver().findElement(REGISTRATION_BUTTON).submit();
        getDriver().findElement(REGISTRATION_BUTTON).click();

        return new InvitePage();

    }
}
