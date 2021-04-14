package redmine.ui.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.help.CucumberName;

/**
 * Страница заголовка начальной страницы
 */
@CucumberName("Заголовок")
public class HeaderPage extends redmine.ui.page.AbstractPage {

    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//h2[text()='Домашняя страница']")
    public WebElement homePage;

    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//a[@class=\"home\"]")
    public WebElement home;

    @CucumberName("Проекты")
    @FindBy(xpath = "//a[@class=\"projects\"]")
    public WebElement projects;

    @CucumberName("Вошли как")
    @FindBy(xpath = "//div[@id=\"loggedas\"]")
    public static WebElement loggedAsElement;

    @CucumberName("Моя страница")
    @FindBy(xpath = "//a[@class=\"my-page\"]")
    public WebElement myPage;

    @CucumberName("Администрирование")
    @FindBy(xpath = "//a[@class=\"administration\"]")
    public WebElement administration;

    @CucumberName("Помощь")
    @FindBy(xpath = "//a[@class=\"help\"]")
    public WebElement help;

    @CucumberName("Моя учётная запись")
    @FindBy(xpath = "//a[@class=\"my-account\"]")
    public WebElement myAccount;

    @CucumberName("Выйти")
    @FindBy(xpath = "//a[@class=\"logout\"]")
    public WebElement logout;

    @CucumberName("Поиск")
    @FindBy(xpath = "//a[@href=\"/search\"]")
    public WebElement search;

    @CucumberName("Регистрация")
    @FindBy(xpath = "//a[@class=\"register\"]")
    public WebElement register;

    @CucumberName("Войти")
    @FindBy(xpath = "//a[@class=\"login\"]")
    public WebElement login;


}
