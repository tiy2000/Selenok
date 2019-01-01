package com.hellofresh.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage extends HelloFreshBasePage {


    public InvitePage fillRegistrationFields() {
//        driver.findElement(By.id("gender-input")).findElement(By.cssSelector("[id='gender-input'] :nth-child(2)")).click();
        Select select = new Select(getDriver().findElement(By.id("gender-input")));
        select.selectByValue("male");
        getDriver().findElement(By.id("first-name-input")).sendKeys("Alex");
        getDriver().findElement(By.id("last-name-input")).sendKeys("Bow");
        getDriver().findElement(By.id("email-input")).sendKeys("qwerty56@nono.to");
        getDriver().findElement(By.id("password-input")).sendKeys("12345678");
        getDriver().findElement(By.id("month-input")).sendKeys("12");
        getDriver().findElement(By.id("day-input")).sendKeys("20");
        getDriver().findElement(By.id("year-input")).sendKeys("2000");

        getDriver().findElement(By.id("register-button")).submit();

        return new InvitePage();

    }
}
