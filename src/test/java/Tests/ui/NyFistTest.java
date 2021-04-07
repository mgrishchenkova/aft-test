package Tests.ui;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.model.user.User;
import redmine.ui.page.HeaderPage;
import redmine.ui.page.LoginPage;

public class NyFistTest {

    User user;

    @BeforeMethod
    public void driver(){
        user = new User().setStatus(1).generate();

        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApi_key(), user.getCreated_on(), user.getUpdated_on());
        String randomEmailAdd = "INSERT INTO public.randomEmail_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user.getId(), user.getMail(), true, true, user.getCreated_on(), user.getUpdated_on());

 System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

    //   Manager.driver();
        Manager.openPage("login");
    }
    @Test
    public void myFirstTest() {

        new LoginPage().login(user.getLogin(), user.getPassword());
        System.out.println(new HeaderPage().loggedAs());
        //Assert.assertEquals(new HeaderPage().loggedAs(), "Вошли как " + user.getLogin());

    }

    @AfterMethod
    public void testChrome() {
        Manager.driverQuit();
    }
}
