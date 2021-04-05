package steps;

import cucumber.api.java.bg.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import redmine.Manager.Context;
import redmine.model.project.Project;
import redmine.ui.pages.CucumberPageObjectHelper;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.ProjectsPage;
import redmine.util.BrowseUtils;

import static redmine.Manager.Manager.driver;
import static redmine.ui.pages.Pages.getPage;

public class AssertionSteps {

    @И("На главной странице будут отображены элементы {string}")
    public void assertProjectElementIsDisplayed(String fieldName) {
        Assert.assertTrue(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }


    @И("На главной странице не отображены элементы {string}")
    public void notAssertProjectElementIsDisplayed(String fieldName) {
        Assert.assertFalse(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }

    @И("На странице входа отображена ошибка {string}")
    public void errors(String fieldName) {
        Assert.assertTrue(
                BrowseUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Вход в Redmine", fieldName)));

    }

    @И("Открыта страница Проекты")
    public void openPageProject() {
        Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(ProjectsPage.class).projects));
    }

    @И("Будет открыта Домашняя страница")
    public void openPageHome(){
        Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).homePage));}

    @То("Отображается проект {string}")
    public void isProjectElement(String projectStashId) {
        Project project= Context.getStash().get(projectStashId,Project.class);
        Assert.assertEquals(driver().findElement(By.xpath(String.format("//a[text()='%s']", project.getName()))).getText(),project.getName());
    }

    @И("Не отображается проект {string}")
    public void isNotProjectElement(String projectName) {
        ProjectsPage page = new ProjectsPage();
        try {
            page.projectName(projectName).isDisplayed();
            Assert.fail();
        } catch (NoSuchElementException ignored) {
        }

    }
}
