package steps;

import cucumber.api.java.bg.И;
import org.testng.Assert;
import redmine.ui.pages.CucumberPageObjectHelper;
import redmine.util.BrowseUtils;

public class AssertionSteps {

    @И("Будут отображены элементы {string}")
    public void assertProjectElementIsDisplayed(String fieldName) {
        Assert.assertTrue(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy(fieldName)));
    }


    @И("Не отображены элементы {string}")
    public void notAssertProjectElementIsDisplayed(String fieldName) {
        Assert.assertFalse(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy(fieldName)));
    }

    @И("Будет отображена ошибка {string}")
    public void errors(String fieldName) {
        Assert.assertTrue(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy(fieldName)));

    }
}
