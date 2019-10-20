package core;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class PageObjectDebugUtils {

    public static void printDebugInfo(BasePage<?> page) {
        System.out.println("===== DEBUG INFORMATION =====================================");
        System.out.println("Page class: " + page.getClass().getName());
        System.out.println("Page baseUrl: " + page.pageUrl.getBaseUrl());
        System.out.println("Page basePath: " + page.pageUrl.getPagePath());
        System.out.println("Page pageUrl: " + page.pageUrl.generateUrl());
        System.out.println("Right page condition: " + page.getRightPageCondition());
        System.out.println("-------------------------------------------------------------");

        Arrays.stream(page.getClass().getDeclaredFields()).forEach(field -> printWebElementFieldInfo(page, field));

        System.out.println("=============================================================");
    }

    private static void printWebElementFieldInfo(BasePage<?> page, Field field) {
        Object fieldValue = ReflectionUtils.getFieldValue(field, page);

        if (field.getType().equals(By.class)) {
            System.out.println("Field: " + field.getName() + " : " + fieldValue);
            List<WebElement> elements = page.getDriver().findElements((By) fieldValue);
            System.out.println("  " + (elements.size() == 0 ? "*** " : "") + "Number of elements: " + elements.size());
            elements.forEach(PageObjectDebugUtils::printWebElement);
        } else if (field.getType().equals(ExpectedCondition.class)) {
            System.out.println("Field: " + field.getName() + " : " + fieldValue);
            ExpectedCondition condition = (ExpectedCondition) fieldValue;
            try {
                WebElement element = page.waitElement(condition);
                System.out.println("  Element by condition found [" + condition + "]");
                printWebElement(element);
            } catch (TimeoutException e) {
                System.out.println("  *** Element by condition NOT found [" + condition + "]");
            }
        } else {
//            System.out.println("Field: " + field.getName() + " : " + fieldValue);
        }
    }

    private static void printWebElement(WebElement element) {
        System.out.println("    " + element.getTagName());
    }

}
