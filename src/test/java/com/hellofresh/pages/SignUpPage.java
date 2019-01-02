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
    public static final By GENDER_SELECT_INPUT = By.id("gender-input");

    public InvitePage fillRegistrationFields() {
//        driver.findElement(By.id("gender-input")).findElement(By.cssSelector("[id='gender-input'] :nth-child(2)")).click();
        Select select = new Select(getDriver().findElement(GENDER_SELECT_INPUT));
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

    public InvitePage clickRegistrationButton() {
        click(REGISTRATION_BUTTON);
        return new InvitePage();
    }

    public SignUpPage clearAndFillFirstName(String firstName) {
        clearAndFill(FIRST_NAME_INPUT, firstName);
        return this;
    }

    public SignUpPage clearAndFillLastName(String lastName) {
        clearAndFill(LAST_NAME_INPUT, lastName);
        return this;
    }

    public SignUpPage clearAndFillEmail(String email) {
        clearAndFill(EMAIL_INPUT, email);
        return this;
    }

    public SignUpPage clearAndFillPassword(String password) {
        clearAndFill(PASSWORD_INPUT, password);
        return this;
    }

    public SignUpPage clearAndFillMonth(String month) {
        clearAndFill(MONTH_INPUT, month);
        return this;
    }

    public SignUpPage clearAndFillDay(String day) {
        clearAndFill(DAY_INPUT, day);
        return this;
    }

    public SignUpPage clearAndFillYear(String year) {
        clearAndFill(YEAR_INPUT, year);
        return this;
    }

    public SignUpPage selectGender(String gender) {
        selectByValue(GENDER_SELECT_INPUT, gender);
        return this;
    }

}
