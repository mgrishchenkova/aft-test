package steps;

import cucumber.api.java.bg.И;
import redmine.Manager.Context;
import redmine.Manager.Manager;
import redmine.model.user.User;
import redmine.ui.page.LoginPage;

import static redmine.ui.page.Page.getPage;

public class LoginSteps {

    @И("Открыт браузер на главной странице")
    public void openPage() {
        Manager.openPage("");
    }

    @И("Авторизоваться пользователем {string}")
    public void createAdmin(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
    }


}
