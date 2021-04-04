package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@CucumberName("Проекты")
public class ProjectsPage extends AbstractPage {
    @CucumberName("Проекты")
    @FindBy(xpath = "//h2[text()='Проекты']")
    public WebElement projects;

    @CucumberName("Список проектов")
    @FindBy(xpath = "//li[@class='root']")
    public List<WebElement> projectsList;


}
