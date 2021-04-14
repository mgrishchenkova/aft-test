package redmine.ui.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.help.CucumberName;

@CucumberName("Пользователи>>Новый Пользователь")
public class NewUser extends AbstractPage {
    @CucumberName("Логин")
    @FindBy(xpath = "//input[@id='user_login']")
    private WebElement userLogin;

}
