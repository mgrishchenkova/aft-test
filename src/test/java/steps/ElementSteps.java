package steps;

import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.Manager.Context;
import redmine.ui.pages.CucumberPageObjectHelper;
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

    @И("Заполнить поля: \"Пользователь\", \"Имя\", \"Фамилия\", \"Email\"  для создания нового пользователя {string}")
    public void createNewUser(String stashId) {
        WebElement user = CucumberPageObjectHelper.getElementBy("Пользователи", "Пользователь");
        String userName=StringGenerator.stringRandom(11, StringGenerator.ENGLISH);
        user.sendKeys(userName);
        WebElement firstName = CucumberPageObjectHelper.getElementBy("Пользователи", "Имя");
        firstName.sendKeys("Mary" + StringGenerator.stringRandom(11, StringGenerator.ENGLISH));
        WebElement lastName = CucumberPageObjectHelper.getElementBy("Пользователи", "Фамилия");
        lastName.sendKeys(StringGenerator.stringRandom(16, StringGenerator.ENGLISH));
        WebElement email = CucumberPageObjectHelper.getElementBy("Пользователи", "Email");
        email.sendKeys(StringGenerator.email());
        Context.getStash().put(stashId,userName);


    }
}
