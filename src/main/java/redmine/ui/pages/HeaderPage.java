package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import redmine.Manager.Manager;

/**
 * Страница заголовка начальной страницы
 */
public class HeaderPage {

    //TODO хом. проекты, логгедас

    @FindBy(xpath = "//a[@class=\"home\"]")
    private WebElement home;

    @FindBy(xpath = "//a[@class=\"projects\"]")
    private WebElement projects;

    @FindBy(xpath = "//div[@id=\"loggedas\"]")
    private WebElement loggedasElement;

    public HeaderPage() {

        PageFactory.initElements(Manager.driver(), this);
    }

    public String loggedAs(){
        return loggedasElement.getText();
    }

}
