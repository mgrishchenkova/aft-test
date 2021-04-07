package Tests.ui;

import org.testng.Assert;
import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.model.user.User;
import redmine.ui.page.HeaderPage;
import redmine.ui.page.LoginPage;
import redmine.ui.page.Page;
import redmine.util.BrowseUtils;

import static redmine.ui.page.Page.getPage;

public class TestStatusUser {
    @Test
    public void testErrors(){
        User user = new User().setStatus(1).setAdmin(true).generate();
        Manager.openPage("login");
        Page.getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertTrue(BrowseUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).homePage));

    }
}
