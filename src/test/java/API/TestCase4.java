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
import redmine.dataBase.UserRequest;
import redmine.model.user.Users;

public class TestCase4 {
    private Users user1;
    private Users user2;
    private ApiClient apiClient;

    @BeforeMethod
    public void createUser() {
        user1 = new Users().generate();
        user2 = new Users().generate();
        apiClient = new RestApiClient(user1);
        //ВЫНЕСТИ В МЕТОД!!!
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user1.getId(), "api", user1.getApi_key(), user1.getCreated_on(), user1.getUpdated_on());
        String emailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
                user1.getId(), user1.getMail(), true, true, user1.getCreated_on(), user1.getUpdated_on());


    }
@Test
    public void deleteUsers(){
        String uri=String.format("users/%d.json", user2.getId());
    Response response = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
    Assert.assertEquals(response.getStatusCode(), 403);
    Users redDBUs = UserRequest.getUser(user2);

    String uriUsr=String.format("users/%d.json", user1.getId());
    Response rs = apiClient.request(new RestRequest(uriUsr, Methods.DELETE, null, null, null));
    Assert.assertEquals(response.getStatusCode(), 403);
    Users redDBUsrs = UserRequest.getUser(user1);
}
}
