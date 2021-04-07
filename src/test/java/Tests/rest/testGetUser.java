package Tests.rest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.RoleDTO;
import redmine.model.user.User;

public class testGetUser {
    private User user;
    private ApiClient apiClient;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();

        apiClient = new RestApiClient(user);
    }

    @Test
    public void getUser() {
        String uri = String.format("User/%d.json", user.getId());
        Request request = new RestRequest(uri, Methods.GET, null, null, null);
        Response response = apiClient.request(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        RoleDTO roleDto = response.getBody(RoleDTO.class);


    }
}
