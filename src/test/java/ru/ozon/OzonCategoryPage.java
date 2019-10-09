package ru.ozon;

import core.annotations.PageId;
import core.annotations.PagePath;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OzonCategoryPage extends OzonBasePage<OzonCategoryPage> {

    @PagePath
    public static final String PATH = "/category/televizory-so-smart-tv/";

    public static final By SELECT_1 = By.className("hidden-select");

    @PageId
    public static final ExpectedCondition SELECT_2 = ExpectedConditions.presenceOfElementLocated(SELECT_1);

}
