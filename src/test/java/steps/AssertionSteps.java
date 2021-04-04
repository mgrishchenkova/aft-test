package steps;

import cucumber.api.java.bg.И;
import org.testng.Assert;
import redmine.ui.pages.CucumberPageObjectHelper;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.ProjectsPage;
import redmine.util.BrowseUtils;

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

}
