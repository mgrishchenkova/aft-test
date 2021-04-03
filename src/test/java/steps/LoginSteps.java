package steps;

import cucumber.api.java.bg.И;
import redmine.Manager.Context;
import redmine.Manager.Manager;
import redmine.model.user.Users;
import redmine.ui.pages.LoginPage;

import static redmine.ui.pages.Pages.getPage;

public class LoginSteps {

    @И("Открыт браузер на главной странице")
    public void openPage() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        Manager.openPage("login");
    }

    @И("Авторизоваться пользователем {string}")
    public void createAdmin(String userStashId) {
        Users user = Context.getStash().get(userStashId, Users.class);
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());

    }


}
