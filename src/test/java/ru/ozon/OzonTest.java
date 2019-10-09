package ru.ozon;

import core.BaseTest;
import core.annotations.WebDriverAutoInstancingByMethod;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static ru.ozon.OzonCategoryPage.*;

@WebDriverAutoInstancingByMethod
public class OzonTest extends BaseTest {

    @Test
    public void testSelect() throws InterruptedException {
        OzonCategoryPage page = openNewPage(OzonCategoryPage.class)
                .validateIsRightPage();

        Assert.assertEquals("Популярные", page.getSelectedOption(SELECT_2).getAttribute("text"));

//        List<WebElement> elements = getDriver().findElements(OzonCategoryPage.SELECT_1);
//        System.out.println(elements.size());

//        Thread.sleep(3000);

//        Select select = page.getSelect(ExpectedConditions.presenceOfElementLocated(OzonCategoryPage.SELECT_1));
//        List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
//        System.out.println(allSelectedOptions.size());
//        WebElement option = allSelectedOptions.get(0);
//        System.out.println(option.getTagName());
//        System.out.println("'" + option.getText() + "'");
//        System.out.println("'" + option.getAttribute("data-index") + "'");
//        System.out.println("'" + option.getAttribute("value") + "'");
//        System.out.println("'" + option.getAttribute("text") + "'");
//        System.out.println("'" + option.getAttribute("index") + "'");

//        System.out.println(page.getSelectedOption(ExpectedConditions.presenceOfElementLocated(SELECT_1))
//                .getAttribute("text"));
    }
}
