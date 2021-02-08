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
import redmine.model.user.Users;
import redmine.util.StringGenerator;

import static redmine.util.GsonHelper.getGson;

public class TestCase1 {
    private Users user;
    private ApiClient apiClient;


    @BeforeMethod
    public void testPrerequisite() {

        user = new Users();
        user.setAdmin(true);
        user.generate();
        apiClient = new RestApiClient(user);

        //ВЫНЕСТИ В МЕТОД!!!
        String addToken="INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(),"api",user.getApikey(),user.getCreated_on(),user.getUpdated_on());
        String emailAdd="INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
                user.getId(),user.getEmail(),true,true, user.getCreated_on(),user.getUpdated_on());

    }

    @Test
    public void testPostUser() {
        UserDTO createUser = new UserDTO()
                .setUser(new UserInfo()
                        .setLogin(StringGenerator.stringRandom(8,StringGenerator.ENGLISH))
                        .setFirstname(StringGenerator.stringRandom(8,StringGenerator.ENGLISH))
                        .setLastname(StringGenerator.stringRandom(8,StringGenerator.ENGLISH))
                        .setMail(StringGenerator.email())
                        .setPassword("1qaz@WSX")
                );
        String body = getGson().toJson(createUser);

        //1. Отправить запрос POST на создание пользователя
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        UserDTO userDTO = rs.getBody(UserDTO.class);
        //Проверки к п.1
        Assert.assertEquals(rs.getStatusCode(), 201);
    Assert.assertNotNull(userDTO.getUser().getId());

    }
}
