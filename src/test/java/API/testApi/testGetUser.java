package API.testApi;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.RoleDto;
import redmine.model.user.Users;

public class testGetUser {
    private Users user;
    private ApiClient apiClient;

    @BeforeMethod
    public void prepareFixtures() {
        user = new Users().generate();

        apiClient = new RestApiClient(user);
    }

    @Test
    public void getUsers() {
        String uri = String.format("users/%d.json", user.getId());
        Request request = new RestRequest(uri, Methods.GET, null, null, null);
        Response response = apiClient.request(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        RoleDto roleDto = response.getBody(RoleDto.class);


    }
}
