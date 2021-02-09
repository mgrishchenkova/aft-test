package API;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.interfaces.ApiClient;
import redmine.model.user.Users;

public class TestCase3 {
    private Users user1;
    private Users user2;
    private ApiClient apiClient;

    @BeforeMethod
    public void createUser(){
        user1 = new Users();
        user2 =new Users();
        user1.generate();
        user2.generate();
    }

    @Test
    public  void testGet(){

    }
}
