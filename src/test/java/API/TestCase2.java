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
import redmine.model.dto.UserDTO;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;

import static redmine.util.GsonHelper.getGson;

public class TestCase2 {
    private User user;
    private ApiClient apiClient;

    @BeforeMethod
    public void addUser(){
        user=new User();
        user.generate();
        apiClient = new RestApiClient(user);
        //ВЫНЕСТИ В МЕТОД!!!
        String addToken="INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(),"api",user.getApi_key(),user.getCreated_on(),user.getUpdated_on());
        String randomEmailAdd="INSERT INTO public.randomEmail_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user.getId(),user.getMail(),true,true, user.getCreated_on(),user.getUpdated_on());

    }
    @Test(description = "Создание пользователя. Пользователь без прав администратора")
    public void createUserNotAdmin(){
        UserDTO userDTO=new UserDTO().setUser(new UserInfo());
        String body = getGson().toJson(userDTO);
        Response rs = apiClient.request(new RestRequest("User.json", Methods.POST, null, body, null));
        Assert.assertEquals(rs.getStatusCode(),403);

    }
}
