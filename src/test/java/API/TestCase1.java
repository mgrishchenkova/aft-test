package API;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Response;
import redmine.model.dto.UserDTO;
import redmine.model.dto.UserInfo;
import redmine.model.user.Users;

import static redmine.util.GsonHelper.getGson;

public class TestCase1 {
    private Users user;
    ApiClient apiClient;

    @BeforeMethod
    public void testPrerequisite() {

        user = new Users();
        user.setAdmin(true);
        user.generate();
        apiClient = new RestApiClient(user);

    }

    @Test
    public void testPostUser() {
        UserDTO user = new UserDTO()
                .setUser(new UserInfo()
                        .setLogin("uqweewee").setFirstname("g")
                        .setLastname("dfbsdfb")
                        .setMail("mail")
                        .setPassword("1qaz@WSX")
                );
        String body = getGson().toJson(user);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Assert.assertEquals(rs.getStatusCode(), 201);
    }
}
