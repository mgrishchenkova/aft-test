package API;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Manager.Context;
import redmine.Manager.Manager;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Response;
import redmine.db.UserRequests;
import redmine.model.dto.UserCreatingError;
import redmine.model.dto.UserDTO;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import redmine.util.StringGenerator;

import static redmine.util.GsonHelper.getGson;

public class TestCase1 {
    private User user;
    private ApiClient apiClient;


    @BeforeMethod
    //@Description("Заведение пользователя в системе с правами админа и ключем API_KEY")
    public void testPrerequisite() {

        user = new User().setAdmin(true).generate();
        apiClient = new RestApiClient(user);

        //TODO ВЫНЕСТИ В МЕТОД!!!
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApi_key(), user.getCreated_on(), user.getUpdated_on());
        String randomEmailAdd = "INSERT INTO public.randomEmail_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user.getId(), user.getMail(), true, true, user.getCreated_on(), user.getUpdated_on());

    }

    @Test(description = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void testPostUser() {

        String password=StringGenerator.randomString(7,StringGenerator.ENGLISH);
        String email=StringGenerator.randomEmail();
        Context.getStash().put("pass", password);
        Context.getStash().put("email", email);
        UserInfo userInfo = new UserInfo()
                .setAdmin(false).setPassword(password).setMail(email);
        UserDTO createUser = new UserDTO()
                .setUser(userInfo);
        String body = getGson().toJson(createUser);

        //1. Отправить запрос POST на создание пользователя
        Response rs = apiClient.request(new RestRequest("User.json", Methods.POST, null, body, null));
        UserDTO userDTO = rs.getBody(UserDTO.class);
        //Проверки к п.1
        Assert.assertEquals(rs.getStatusCode(), 201);
        Assert.assertNotNull(userDTO.getUser().getId());
        Assert.assertEquals(userDTO.getUser().getLogin(), createUser.getUser().getLogin());
        Assert.assertEquals(userDTO.getUser().getAdmin(), createUser.getUser().getAdmin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), createUser.getUser().getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), createUser.getUser().getLastname());
        Assert.assertEquals(userDTO.getUser().getMail(), createUser.getUser().getMail());
        Assert.assertEquals(userDTO.getUser().getStatus(), createUser.getUser().getStatus());
        System.out.println("Завершен 1ый тест");

        //Отправить запрос POST на создание пользователя повторно с тем же телом запроса
        Response rsDubl = apiClient.request(new RestRequest("User.json", Methods.POST, null, body, null));
        UserCreatingError errors = getGson().fromJson(rsDubl.getBody().toString(), UserCreatingError.class);
        Assert.assertEquals(rsDubl.getStatusCode(), 422);
        Assert.assertEquals(errors.getErrors().get(0), "randomEmail уже существует");
        Assert.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        System.out.println("Завершен 2ой тест");

        //Отправить запрос POST на создание пользователя повторно с тем же телом запроса, при этом изменив "randomEmail" на невалидный, а "password" - на строку из 4 символов
        createUser.setUser(userInfo.setMail("hello").setPassword("1wjd"));
        String body1 = getGson().toJson(createUser);
        Response rs1 = apiClient.request(new RestRequest("User.json", Methods.POST, null, body1, null));
        UserCreatingError errorRs1 = getGson().fromJson(rs1.getBody().toString(), UserCreatingError.class);
        Assert.assertEquals(rs1.getStatusCode(), 422);
        Assert.assertEquals(errorRs1.getErrors().get(0), "randomEmail имеет неверное значение");
        Assert.assertEquals(errorRs1.getErrors().get(1), "Пользователь уже существует");
        Assert.assertEquals(errorRs1.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");

        //4. Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1
        String mail = userDTO.getUser().getMail();
        createUser.setUser(userInfo.setStatus(1).setMail(mail).setPassword(password));
        String body2 = getGson().toJson(createUser);
        String uri = String.format("User/%d.json", userDTO.getUser().getId());
        System.out.println("__________________________________");
        Response responsePut = apiClient.request(new RestRequest(uri, Methods.PUT, null, body2, null));
        Assert.assertEquals(responsePut.getStatusCode(), 204);
        User createUserDB = UserRequests.getUser(user);
        Assert.assertEquals((createUserDB.getStatus().toString()), "1");


        //5. Отправить запрос GET на получение пользователя

        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Assert.assertEquals(response.getStatusCode(), 200);
        UserDTO userDto = response.getBody(UserDTO.class);

        //Проверки к п.5

        Assert.assertNotNull(userDto.getUser().getId());
        Assert.assertEquals(userDto.getUser().getLogin(), createUser.getUser().getLogin());
        Assert.assertEquals(userDto.getUser().getAdmin(), createUser.getUser().getAdmin());
        Assert.assertEquals(userDto.getUser().getFirstname(), createUser.getUser().getFirstname());
        Assert.assertEquals(userDto.getUser().getLastname(), createUser.getUser().getLastname());
        Assert.assertEquals(userDto.getUser().getMail(), createUser.getUser().getMail());
        Assert.assertEquals(userDto.getUser().getStatus(), createUser.getUser().getStatus());

        //6. Отправить запрос DELETE на удаление пользователя
        Response deleteUser = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Assert.assertEquals(deleteUser.getStatusCode(), 204);
        User removeUser = UserRequests.getUser(user);
        //Assert.assertNull(removeUser);

        //7. Отправить запрос DELETE на удаление пользователя (повторно)
        Response delUser = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Assert.assertEquals(delUser.getStatusCode(), 404);
    }
}
