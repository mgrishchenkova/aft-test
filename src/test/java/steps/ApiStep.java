package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.Manager.Context;
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

public class ApiStep {
    @И("У пользователя есть доступ к API и ключ API")
    public void generateApiKey(String stashId) {
        Users user = Context.getStash().get(stashId, Users.class);
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApi_key(), user.getCreated_on(), user.getUpdated_on());
        String emailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
                user.getId(), user.getMail(), true, true, user.getCreated_on(), user.getUpdated_on());
    }


    @Пусть("Создан пользователь {string}")
    public void createUser(String stashId) {
        String password = StringGenerator.stringRandom(7, StringGenerator.ENGLISH);
        UserInfo userInfo = new UserInfo()
                .setAdmin(false).setPassword(password);
        UserDTO createUser = new UserDTO()
                .setUser(userInfo);
        Context.getStash().put(stashId, createUser);
    }

    @Если("Отправить POST - запрос  на создание пользователя")
    public void postCreateUser(String stashIdUser, String stashIdCreateUser, String stashIdRs) {
        Users user = Context.getStash().get(stashIdUser, Users.class);
        ApiClient apiClient = new RestApiClient(user);
        UserDTO createUser = Context.getStash().get(stashIdCreateUser, UserDTO.class);
        String body = getGson().toJson(createUser);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Context.getStash().put(stashIdRs, rs);
    }

    @То("Статус код ответа {int}")
    public void statusCodeResponse(String stashIdRs) {
        Response response= Context.getStash().get(stashIdRs, Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        Assert.assertEquals(response.getStatusCode(), 201);

    }

    @И("Тело ответа содержит данные пользователя")
    public void dataBodyResponse(String stashIdRs, String stashIdCreateUser) {
        Response response= Context.getStash().get(stashIdRs, Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        UserDTO createUser = Context.getStash().get(stashIdCreateUser, UserDTO.class);
        Assert.assertEquals(userDTO.getUser().getLogin(), createUser.getUser().getLogin());
        Assert.assertEquals(userDTO.getUser().getAdmin(), createUser.getUser().getAdmin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), createUser.getUser().getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), createUser.getUser().getLastname());
        Assert.assertEquals(userDTO.getUser().getMail(), createUser.getUser().getMail());
        Assert.assertEquals(userDTO.getUser().getStatus(), createUser.getUser().getStatus());
    }
}
