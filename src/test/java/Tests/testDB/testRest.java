package Tests.testDB;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.User;

public class testRest {
    private User user;
    private ApiClient apiClient;


    @BeforeMethod
    public void prepareFixtures() {
        User user = new User().generate();
        apiClient= new RestApiClient(user);
    }

    @Test
    public void testRoleGet() {
        Request request = new RestRequest("roles.json", Methods.GET, null, null, null);
        Response response = apiClient.request(request);
        Assert.assertEquals(response.getStatusCode(),200);

        Assert.assertTrue(response.getHeaders().containsKey("Content-Type"));
    }

}
