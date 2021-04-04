package Tests.ui;

import org.testng.Assert;
import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.model.user.Users;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.Pages;
import redmine.util.BrowseUtils;

import static redmine.ui.pages.Pages.getPage;

public class TestStatusUser {
    @Test
    public void testErrors(){
        Users user = new Users().setStatus(1).setAdmin(true).generate();
        Manager.openPage("login");
        Pages.getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).homePage));

    }
}
