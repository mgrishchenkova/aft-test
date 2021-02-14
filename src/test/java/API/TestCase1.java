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
import redmine.model.dto.UserCreatingError;
import redmine.model.dto.UserDTO;
import redmine.model.dto.UserInfo;
import redmine.model.user.Users;
import redmine.util.StringGenerator;

import static redmine.util.GsonHelper.getGson;

public class TestCase1 {
    private Users user;
    private ApiClient apiClient;

//TODO дописать обрезку даты
    @BeforeMethod
    public void testPrerequisite() {

        user = new Users();
        user.setAdmin(true);
        user.generate();
        apiClient = new RestApiClient(user);

        //ВЫНЕСТИ В МЕТОД!!!
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApikey(), user.getCreated_on(), user.getUpdated_on());
        String emailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
                user.getId(), user.getEmail(), true, true, user.getCreated_on(), user.getUpdated_on());

    }

    @Test
    public void testPostUser() {
        UserInfo userInfo = new UserInfo()
                .setLogin(StringGenerator.stringRandom(8, StringGenerator.ENGLISH))
                .setFirstname(StringGenerator.stringRandom(8, StringGenerator.ENGLISH))
                .setLastname(StringGenerator.stringRandom(8, StringGenerator.ENGLISH))
                .setMail(StringGenerator.email())
                .setPassword("1qaz@WSX");
        UserDTO createUser = new UserDTO()
                .setUser(userInfo);
        String body = getGson().toJson(createUser);

        //1. Отправить запрос POST на создание пользователя
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        UserDTO userDTO = rs.getBody(UserDTO.class);
        //Проверки к п.1
        Assert.assertEquals(rs.getStatusCode(), 201);
        Assert.assertNotNull(userDTO.getUser().getId());
        Assert.assertEquals(userDTO.getUser().getLogin(), createUser.getUser().getLogin());
        Assert.assertEquals(userDTO.getUser().getAdmin(), createUser.getUser().getAdmin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), createUser.getUser().getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), createUser.getUser().getLastname());
        Assert.assertEquals(userDTO.getUser().getMail(), createUser.getUser().getMail());
        //Assert.assertEquals(userDTO.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS),createUser.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS));
        //Assert.assertEquals(userDTO.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS),createUser.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS));
        Assert.assertEquals(userDTO.getUser().getStatus(), createUser.getUser().getStatus());
        System.out.println("Завершен 1ый тест");

        //Отправить запрос POST на создание пользователя повторно с тем же телом запроса
        Response rsDubl = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        UserCreatingError errors = getGson().fromJson(rsDubl.getBody().toString(), UserCreatingError.class);
        Assert.assertEquals(rsDubl.getStatusCode(), 422);
        Assert.assertEquals(errors.getErrors().get(0), "Email уже существует");
        Assert.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        System.out.println("Завершен 2ой тест");

        //Отправить запрос POST на создание пользователя повторно с тем же телом запроса, при этом изменив "email" на невалидный, а "password" - на строку из 4 символов
        createUser.setUser(userInfo.setMail("hello").setPassword("1wjd"));
        String body1 = getGson().toJson(createUser);
        Response rs1 = apiClient.request(new RestRequest("users.json", Methods.POST, null, body1, null));
        UserCreatingError errorRs1 = getGson().fromJson(rs1.getBody().toString(), UserCreatingError.class);
        Assert.assertEquals(rs1.getStatusCode(), 422);
        Assert.assertEquals(errorRs1.getErrors().get(0), "Email имеет неверное значение");
        Assert.assertEquals(errorRs1.getErrors().get(1), "Пользователь уже существует");
        Assert.assertEquals(errorRs1.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");

        //4. Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1
        String mail = userDTO.getUser().getMail();
        String password = userDTO.getUser().getPassword();
        createUser.setUser(userInfo.setStatus(1).setMail(mail).setPassword(password));
        String body2 = getGson().toJson(createUser);
        String uri = String.format("users/%d.json", userDTO.getUser().getId());
        System.out.println("__________________________________");
        Response responsePut = apiClient.request(new RestRequest(uri, Methods.PUT, null, body2, null));
        Assert.assertEquals(responsePut.getStatusCode(), 204);
        Users createUserDB = UserRequest.getUser(user);
        Assert.assertEquals((createUserDB.getStatus().toString()), "1");
        System.out.println("прошел 4ый тест");

        //5. Отправить запрос GET на получение пользователя

        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Assert.assertEquals(response.getStatusCode(), 200);
        UserDTO userDto = response.getBody(UserDTO.class);
        System.out.println("пошел 5ый тест");
        //Проверки к п.1

        Assert.assertNotNull(userDto.getUser().getId());
        Assert.assertEquals(userDto.getUser().getLogin(), createUser.getUser().getLogin());
        Assert.assertEquals(userDto.getUser().getAdmin(), createUser.getUser().getAdmin());
        Assert.assertEquals(userDto.getUser().getFirstname(), createUser.getUser().getFirstname());
        Assert.assertEquals(userDto.getUser().getLastname(), createUser.getUser().getLastname());
        Assert.assertEquals(userDto.getUser().getMail(), createUser.getUser().getMail());
        //Assert.assertEquals(userDTO.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS),createUser.getUser().getCreated_on().truncatedTo(ChronoUnit.SECONDS));
        //Assert.assertEquals(userDTO.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS),createUser.getUser().getLast_login_on().truncatedTo(ChronoUnit.SECONDS));
        Assert.assertEquals(userDto.getUser().getStatus(), createUser.getUser().getStatus());

        //6. Отправить запрос DELETE на удаление пользователя
        Response deleteUser = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Assert.assertEquals(deleteUser.getStatusCode(), 204);
        Users removeUser = UserRequest.getUser(user);
        //Assert.assertNull(removeUser);

        //7. Отправить запрос DELETE на удаление пользователя (повторно)
        Response delUser = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Assert.assertEquals(delUser.getStatusCode(), 404);
    }
}
