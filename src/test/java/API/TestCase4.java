package API;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Manager.Manager;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Response;
import redmine.db.UserRequests;
import redmine.model.user.User;

public class TestCase4 {
    private User user1;
    private User user2;
    private ApiClient apiClient;

    @BeforeMethod
    public void createUser() {
        user1 = new User().generate();
        user2 = new User().generate();
        apiClient = new RestApiClient(user1);
        //ВЫНЕСТИ В МЕТОД!!!
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user1.getId(), "api", user1.getApi_key(), user1.getCreated_on(), user1.getUpdated_on());
        String randomEmailAdd = "INSERT INTO public.randomEmail_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user1.getId(), user1.getMail(), true, true, user1.getCreated_on(), user1.getUpdated_on());


    }

    @Test(description = " Удаление пользователей. Пользователь без прав администратора")
    public void deleteUser() {
        String uri = String.format("User/%d.json", user2.getId());
        Response response = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Assert.assertEquals(response.getStatusCode(), 403);
        User redDBUs = UserRequests.getUser(user2);

        String uriUsr = String.format("User/%d.json", user1.getId());
        Response rs = apiClient.request(new RestRequest(uriUsr, Methods.DELETE, null, null, null));
        Assert.assertEquals(response.getStatusCode(), 403);
        User redDBUser = UserRequests.getUser(user1);
    }
}
