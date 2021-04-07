package steps;

import cucumber.api.java.bg.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.Manager.Context;
import redmine.Manager.Manager;
import redmine.cucumber.ParametersValidator;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.page.*;
import redmine.ui.help.CucumberPageObjectHelper;
import redmine.util.BrowseUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.ui.page.Page.getPage;

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

    @И("Будет открыта страница {string}")
    public void openPageProject(String pageName) {
        switch (pageName) {
            case "Проекты":
                Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(ProjectsPage.class).projects));
                break;
            case "Домашняя страница":
                Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).homePage));
                break;
            case "Администрирование":
                Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(AdministrationPage.class).adminPage));
                break;
            case "Пользователи >> Новый пользователь":
                Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(UserPage.class).newUser));
                break;
        }
    }


    @То("Отображается проект {string}")
    public void isProjectElement(String projectStashId) {
        Project project = Context.getStash().get(projectStashId, Project.class);
        Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(ProjectsPage.class).projectName(project.getName())));

        //Assert.assertTrue(driver().findElement(By.xpath(String.format("//a[text()='%s']", project.getName()))).getText());
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

    @И("На странице {string} отображается элемент {string}")
    public void assertFieldIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertTrue(
                BrowseUtils.isElementCurrentlyPresent(element)
        );
    }

    @И("На странице \"Пользователи\" отображается элемент Пользователь {string} создан.")
    public void assertFieldUserLogin(String stashId) {
        User user = (User) Context.getStash().get(stashId);
        String element = String.format("Пользователь %s создан.", user.getLogin());
        String elementForm = UserPage.createUserText();
        Assert.assertEquals(element, elementForm);
    }

    @И("На главной странице пользователя {string} элемент Вошли как имеет текст {string}")
    public void assertFieldLoggedAS(String stashIdUser, String cucu) {
        User user = Context.getStash().get(stashIdUser, User.class);
        String loggedAsForm = String.format("Вошли как %s", user.getLogin());
        String loggedas = ParametersValidator.replaceCucumberVariables(cucu);

        Assert.assertEquals(loggedas, loggedAsForm);

    }

    @И("Таблица с пользователями отсортирована по {string} пользователей по {string}")
    public void sortUser(String sortName, String typeSort) {
        if (sortName == "логину" && typeSort == "возрастанию") {
            List<String> listPage = getPage(AdministrationPage.class).userName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }
        if (sortName == "логину" && typeSort == "убыванию") {
            List<String> listPage = getPage(AdministrationPage.class).userName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }
        if (sortName == "имени" && typeSort == "возрастанию") {
            List<String> listPage = getPage(AdministrationPage.class).firstName
                    .stream()
                    .map(WebElement::getText)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }
        if (sortName == "имени" && typeSort == "убыванию") {
            List<String> listPage = getPage(AdministrationPage.class).firstName
                    .stream()
                    .map(WebElement::getText)
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }
        if (sortName == "фамилии" && typeSort == "возрастанию") {
            List<String> listPage = getPage(AdministrationPage.class).lastName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }
        if (sortName == "фамилии" && typeSort == "убыванию") {
            List<String> listPage = getPage(AdministrationPage.class).lastName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Assert.assertEquals(listPage, listPageort);
        }


    }

    @И("Таблица с пользователями не отсортирована по {string}")
    public void notSorted(String nameElement) {
        if (nameElement == "фамилии") {
            List<String> listPage = getPage(AdministrationPage.class).lastName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            Assert.assertNotEquals(listPage, listPageort);
        }
        if (nameElement == "имени") {
            List<String> listPage = getPage(AdministrationPage.class).firstName
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> listPageort = listPage.stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Assert.assertNotEquals(listPage, listPageort);
        }
    }

    @То("В базе данных появилась в таблице User появилась запись с данными пользователями {string}")
    public void addBD(String stashId) {
        String user = (String) Context.getStash().get(stashId);
        String query = String.format("select * from User inner join randomEmail_addresses  on User.id=randomEmail_addresses.user_id where login='%s'", user);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Map<String, Object> dbUser = result.get(0);
        Assert.assertEquals(dbUser.get("login"), user);


    }
}
