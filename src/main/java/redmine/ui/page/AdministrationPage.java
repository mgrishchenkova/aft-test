package redmine.ui.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.help.CucumberName;

import java.util.List;

@CucumberName("Администрирование")
public class AdministrationPage extends AbstractPage {

    @CucumberName("Пользователи")
    @FindBy(xpath = "//a[@class=\"icon icon-user User\"]")
    public WebElement User;

    @CucumberName("Таблица пользователей")
    @FindBy(xpath = "//table[@class=\"list User\"]")
    public WebElement UserTable;

    @CucumberName("Пользователь")
    @FindBy(xpath = "//a[text()='Пользователь']")
    public WebElement user;

    @FindBy(xpath = "//td[@class='username']")
    public List<WebElement> userName;

    @FindBy(xpath = "//td[@class=\"firstname\"]")
    public List<WebElement> firstName;

    @FindBy(xpath = "//td[@class=\"lastname\"]")
    public List<WebElement> lastName;

    @CucumberName("Имя")
    @FindBy(xpath = "//a[text()='Имя']")
    public WebElement firstNameElement;

    @CucumberName("Фамилия")
    @FindBy(xpath = "//a[text()='Фамилия']")
    public WebElement lastNameElement;

    @CucumberName("Страница Администрирование")
    @FindBy(xpath = "//h2")
    public WebElement adminPage;

    @CucumberName("Новый пользователь")
    @FindBy(xpath = "//a[@class=\"icon icon-add\"]")
    public WebElement newUser;



}
