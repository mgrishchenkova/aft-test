package redmine.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.Manager.Manager;

import java.util.List;

@CucumberName("Проекты")
public class ProjectsPage extends AbstractPage {
    @CucumberName("Проекты")
    @FindBy(xpath = "//h2[text()='Проекты']")
    public WebElement projects;

    @CucumberName("Список проектов")
    @FindBy(xpath = "//li[@class='root']")
    public List<WebElement> projectsList;

    public WebElement projectName(String projectName) {
        return Manager.driver()
                .findElement(By.xpath("//div[@id='projects-index']//li/div[@class='root']/a[text()='" + projectName + "']"));
    }

}
