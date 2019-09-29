package core;

import core.annotations.PageId;
import core.annotations.PageId.Condition;
import core.annotations.PagePath;
import core.annotations.PageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.lang.reflect.Field;

public class AnnotationParser {

    ExpectedCondition rightPageCondition = null;
    String pagePath = null;
    String pageUrl = null;

    void parse(Object object) {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(PagePath.class)) {
                if (field.getType().equals(String.class)) {
                    try {
                        field.setAccessible(true);
                        pagePath = (String) field.get(object);
//                        System.out.println("pagePath = " + pagePath);
                    } catch (IllegalAccessException e) {
                    }
                }
            } else if (field.isAnnotationPresent(PageUrl.class)) {
                if (field.getType().equals(String.class)) {
                    try {
                        field.setAccessible(true);
                        pageUrl = (String) field.get(object);
//                        System.out.println("pageUrl = " + pageUrl);
                    } catch (IllegalAccessException e) {
                    }
                }
            } else if (field.isAnnotationPresent(PageId.class)) {
                try {
                    if (field.getType().equals(By.class)) {
                        PageId pageId = (PageId) field.getAnnotation(PageId.class);
                        Condition condition = pageId.condition();
                        field.setAccessible(true);
                        By by = (By) field.get(object);
                        switch (condition) {
                            case ELEMENT_PRESENTED:
                                rightPageCondition = ExpectedConditions.presenceOfElementLocated(by);
                                break;
                        }
                    } else if (field.getType().equals(ExpectedCondition.class)) {
                        field.setAccessible(true);
                        rightPageCondition = (ExpectedCondition<?>) field.get(object);
                    }
//                    System.out.println("rightPageCondition = " + rightPageCondition);
                } catch (IllegalAccessException e) {
                    System.out.println(e);
                }
            }
        }
    }

}
