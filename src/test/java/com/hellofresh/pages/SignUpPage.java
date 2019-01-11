package com.hellofresh.pages;

import base.annotations.PageId;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage extends HelloFreshBasePage<SignUpPage> {

    @PageId
    public static final By FIRST_NAME_INPUT = By.id("first-name-input");
    public static final By LAST_NAME_INPUT = By.id("last-name-input");
    public static final By EMAIL_INPUT = By.id("email-input");
    public static final By PASSWORD_INPUT = By.id("password-input");
    public static final By MONTH_INPUT = By.id("month-input");
    public static final By DAY_INPUT = By.id("day-input");
    public static final By YEAR_INPUT = By.id("year-input");
    public static final By REGISTRATION_BUTTON = By.id("register-button");
    public static final By GENDER_SELECT_INPUT = By.id("gender-input");


    public SignUpPage() {
//        setRightPageCondition(ExpectedConditions.presenceOfElementLocated(FIRST_NAME_INPUT));

//        pageIdLocator = FIRST_NAME_INPUT;
    }

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

    @Step
    public InvitePage clickRegistrationButton() {
        click(REGISTRATION_BUTTON);
        return new InvitePage();
    }

    @Step
    public SignUpPage enterFirstName(String firstName) {
        enterInputValue(FIRST_NAME_INPUT, firstName);
        return this;
    }

    @Step
    public SignUpPage enterLastName(String lastName) {
        enterInputValue(LAST_NAME_INPUT, lastName);
        return this;
    }

    @Step
    public SignUpPage enterFillEmail(String email) {
        enterInputValue(EMAIL_INPUT, email);
        return this;
    }

    @Step
    public SignUpPage enterPassword(String password) {
        enterInputValue(PASSWORD_INPUT, password);
        return this;
    }

    @Step
    public SignUpPage enterMonth(String month) {
        enterInputValue(MONTH_INPUT, month);
        return this;
    }

    @Step
    public SignUpPage enterDay(String day) {
        enterInputValue(DAY_INPUT, day);
        return this;
    }

    @Step
    public SignUpPage enterYear(String year) {
        enterInputValue(YEAR_INPUT, year);
        return this;
    }

    @Step
    public SignUpPage selectGender(String gender) {
        selectByValue(GENDER_SELECT_INPUT, gender);
        return this;
    }

}
