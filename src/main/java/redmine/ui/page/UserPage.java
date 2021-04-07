package redmine.ui.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.help.CucumberName;

@CucumberName("Пользователи")
public class UserPage extends AbstractPage {


    @CucumberName("Пользователь")
    @FindBy(xpath = "//input[@name=\"user[login]\"]")
    public WebElement User;

    @CucumberName("Пользователи >> Новый пользователь")
    @FindBy(xpath = "//h2")
    public WebElement newUser;

    @CucumberName("Имя")
    @FindBy(xpath = "//input[@name=\"user[firstname]\"]")
    public WebElement firstName;

    @CucumberName("Фамилия")
    @FindBy(xpath = "//input[@name=\"user[lastname]\"]")
    public WebElement lastName;

    @CucumberName("randomEmail")
    @FindBy(xpath = "//input[@name=\"user[mail]\"]")
    public WebElement randomEmail;

    @CucumberName("Создание пароля")
    @FindBy(xpath = "//input[@id=\"user_generate_password\"]")
    public WebElement password;

    @CucumberName("Создать")
    @FindBy(xpath = "//input[@name=\"commit\"]")
    public WebElement create;

    @CucumberName("Пользователь <логин> создан.")
    @FindBy(xpath = "//div[text()='Пользователь ']")
    public static WebElement createUser;

    public static String createUserText(){
       return createUser.getText();
    }

}
