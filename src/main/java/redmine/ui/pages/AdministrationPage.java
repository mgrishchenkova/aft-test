package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@CucumberName("Администрирование")
public class AdministrationPage extends AbstractPage {

    @CucumberName("Пользователи")
    @FindBy(xpath = "//a[@class=\"icon icon-user users\"]")
    public WebElement users;

    @CucumberName("Таблица пользователей")
    @FindBy(xpath = "//table[@class=\"list users\"]")
    public WebElement usersTable;

    @CucumberName("Пользователь")
    @FindBy(xpath = "//a[text()='Пользователь']")
    public WebElement user;

    @FindBy(xpath = "//td[@class='username']")
    public List<WebElement> userName;

    @FindBy(xpath = "//td[@class=\"firstname\"]")
    private List<WebElement> firstname;

    @FindBy(xpath = "//td[@class=\"lastname\"]")
    private List<WebElement> lastname;

}
