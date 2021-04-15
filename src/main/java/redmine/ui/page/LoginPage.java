package redmine.ui.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.help.CucumberName;

/**
 * страница входа REDMINE
 */
@CucumberName("Вход в Redmine")
public class LoginPage extends AbstractPage {


    @FindBy(xpath = "//input[@id=\"username\"]")
    private WebElement loginElement;

    @FindBy(xpath = "//input[@id=\"password\"]")
    private WebElement passwordElement;

    @FindBy(xpath = "//input[@id=\"login-submit\"]")
    private WebElement submit;

    @CucumberName("Ваша учётная запись создана и ожидает подтверждения администратора.")
    @FindBy(xpath = " //div[@class=\"flash error\"]")
    public WebElement errors;

    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submit.click();
    }
}
