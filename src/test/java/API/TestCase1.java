package API;

import org.testng.annotations.BeforeMethod;
import redmine.model.user.Users;

public class TestCase1 {
    @BeforeMethod
    public void testPrerequisite(){
        //Заведен пользователь в системе с правами администратора

        Users users = new Users();
        users.setAdmin(true);
        users.generate();

        //У пользователя есть доступ к API и ключ API
    }
}
