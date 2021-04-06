package Tests.ui;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.model.user.Users;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

public class NyFistTest {

    Users user;

    @BeforeMethod
    public void driver(){
        user = new Users().setStatus(1).generate();

        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApi_key(), user.getCreated_on(), user.getUpdated_on());
        String emailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
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
