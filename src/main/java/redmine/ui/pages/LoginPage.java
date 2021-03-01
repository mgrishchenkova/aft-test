package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import redmine.Manager.Manager;

/**
 * страница входа REDMINE
 */
public class LoginPage {



    @FindBy(xpath = "//input[@id=\"username\"]")
    private WebElement loginElement;

    @FindBy(xpath = "//input[@id=\"password\"]")
    private WebElement passwordElement;

    @FindBy(xpath = "//input[@id=\"login-submit\"]")
    private WebElement submit;

    public LoginPage() {

        PageFactory.initElements(Manager.driver(), this);
    }

    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submit.click();
    }
}
