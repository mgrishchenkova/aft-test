package steps;

import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.ui.pages.CucumberPageObjectHelper;

public class ElementSteps {
    @И("На странице {string} нажать на элемент {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.click();
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName, String text) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.sendKeys(text);
    }
}
