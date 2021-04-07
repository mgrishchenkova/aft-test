package steps;

import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.Manager.Context;
import redmine.ui.help.CucumberPageObjectHelper;
import redmine.util.StringGenerator;

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

    @И("Заполнить поля: \"Пользователь\", \"Имя\", \"Фамилия\", \"randomEmail\"  для создания нового пользователя {string}")
    public void createNewUser(String stashId) {
        WebElement user = CucumberPageObjectHelper.getElementBy("Пользователи", "Пользователь");
        String userName=StringGenerator.randomString(11, StringGenerator.ENGLISH);
        user.sendKeys(userName);
        WebElement firstName = CucumberPageObjectHelper.getElementBy("Пользователи", "Имя");
        firstName.sendKeys("Mary" + StringGenerator.randomString(11, StringGenerator.ENGLISH));
        WebElement lastName = CucumberPageObjectHelper.getElementBy("Пользователи", "Фамилия");
        lastName.sendKeys(StringGenerator.randomString(16, StringGenerator.ENGLISH));
        WebElement randomEmail = CucumberPageObjectHelper.getElementBy("Пользователи", "randomEmail");
        randomEmail.sendKeys(StringGenerator.randomEmail());
        Context.getStash().put(stashId,userName);


    }
}
