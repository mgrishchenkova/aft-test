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
import redmine.model.user.User;

import java.time.temporal.ChronoUnit;

public class TestCase3 {
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

    @Test(description = "Получение пользователей. Пользователь без прав администратора")
    public void testGet() {
        // 1. Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2
        String uri = String.format("User/%d.json", user1.getId());
        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Assert.assertEquals(response.getStatusCode(), 200);
        UserDTO userDto = response.getBody(UserDTO.class);
        Assert.assertEquals(userDto.getUser().getId(), user1.getId());
        Assert.assertEquals(userDto.getUser().getLogin(), user1.getLogin());
        Assert.assertEquals(userDto.getUser().getAdmin(), user1.getAdmin());
        Assert.assertEquals(userDto.getUser().getFirstname(), user1.getFirstname());
        Assert.assertEquals(userDto.getUser().getLastname(), user1.getLastname());
        Assert.assertEquals(userDto.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS).toString(), user1.getCreated_on().toLocalDateTime().format(User.formatter));
        Assert.assertEquals(userDto.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS).toString(), user1.getLast_login_on().toLocalDateTime().format(User.formatter));
        Assert.assertEquals(userDto.getUser().getApi_key(), user1.getApi_key());
        //  2. Отправить запрос GET на получения пользователя из п.3, используя ключ API из п.2
        String uriUserTo = String.format("User/%d.json", user2.getId());
        Response rs = apiClient.request(new RestRequest(uriUserTo, Methods.GET, null, null, null));
        Assert.assertEquals(rs.getStatusCode(), 200);
        UserDTO userDTO = rs.getBody(UserDTO.class);
        Assert.assertEquals(userDTO.getUser().getId(), user2.getId());
        Assert.assertEquals(userDTO.getUser().getLogin(), user2.getLogin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), user2.getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), user2.getLastname());
        Assert.assertEquals(userDTO.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS).toString(), user2.getCreated_on().toLocalDateTime().format(User.formatter));
        Assert.assertEquals(userDTO.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS).toString(), user2.getLast_login_on().toLocalDateTime().format(User.formatter));
        Assert.assertNull(userDTO.getUser().getApi_key());
        Assert.assertNull(userDTO.getUser().getAdmin());

    }
}
