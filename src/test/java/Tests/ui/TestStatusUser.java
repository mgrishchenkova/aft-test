package Tests.ui;

import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.model.user.Users;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.Pages;

public class TestStatusUser {
    @Test
    public void testErrors(){
        Users user = new Users().setStatus(2).generate();
        Manager.openPage("login");
        Pages.getPage(LoginPage.class).login(user.getLogin(), user.getPassword());

    }
}
