package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Страница заголовка начальной страницы
 */
@CucumberName("Заголовок")
public class HeaderPage extends AbstractPage {

    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//h2[text()='Домашняя страница']")
    public WebElement homePage;

    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//a[@class=\"home\"]") //домашняя страница
    public WebElement home;

    @CucumberName("Проекты")
    @FindBy(xpath = "//a[@class=\"projects\"]") // проекты
    public WebElement projects;

    @CucumberName("Вошли как")
    @FindBy(xpath = "//div[@id=\"loggedas\"]") //Вошли как
    public WebElement loggedasElement;

    @CucumberName("Моя страница")
    @FindBy(xpath = "//a[@class=\"my-page\"]") //моя страница
    public WebElement myPage;

    @CucumberName("Администрирование")
    @FindBy(xpath = "//a[@class=\"administration\"]") //администрирование
    public WebElement administration;

    @CucumberName("Помощь")
    @FindBy(xpath = "//a[@class=\"help\"]") //помощь
    public WebElement help;

    @CucumberName("Моя учётная запись")
    @FindBy(xpath = "//a[@class=\"my-account\"]")// Моя учётная запись
    public WebElement myAccount;

    @CucumberName("Выйти")
    @FindBy(xpath = "//a[@class=\"logout\"]") //Выйти
    public WebElement logout;

    @CucumberName("Поиск")
    @FindBy(xpath = "//a[@href=\"/search\"]") //Выйти
    public WebElement search;

    @CucumberName("Регистрация")
    @FindBy(xpath = "//a[@class=\"register\"]") //Выйти
    public WebElement register;

    @CucumberName("Войти")
    @FindBy(xpath = "//a[@class=\"login\"]") //Выйти
    public WebElement login;



    public String loggedAs() {
        return loggedasElement.getText();
    }



}
