package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.testng.Assert;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.util.GsonHelper.getGson;

public class ApiSteps {
    @И("У пользователя {string} есть доступ к API и ключ API")
    public void generateApiKey(String stashIdUser) {
        User user = Context.getStash().get(stashIdUser, User.class);
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";
        String apiKey = user.getApiKey();

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApiKey(), user.getCreatedOn(), user.getUpdatedOn());
        String randomEmailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user.getId(), user.getMail(), true, true, user.getCreatedOn(), user.getUpdatedOn());
        Context.getStash().put("api_key", apiKey);
    }


    @Если("Отправить POST - запрос {string}-ом  на создание пользователя {string}")
    public void postCreateUser(String stashIdUser, String create_user) {
        String password = StringGenerator.randomString(7, StringGenerator.ENGLISH);
        String email = StringGenerator.randomEmail();
        Context.getStash().put("pass", password);
        Context.getStash().put("email", email);
        UserInfo userInfo = new UserInfo()
                .setAdmin(false).setPassword(password).setMail(email);
        Context.getStash().put("user_info", userInfo);
        UserDTO createUser = new UserDTO()
                .setUser(userInfo);
        Context.getStash().put(create_user, createUser);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String body = getGson().toJson(createUser);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Context.getStash().put("last_response", rs);
        UserDTO userDTO = rs.getBody(UserDTO.class);
        Context.getStash().put("userDTO", userDTO);


    }

    @Если("Отправить повторный POST - запрос {string}-ом  на создание пользователя {string}")
    public void postCreateUserDubl(String stashIdUser, String stashIdCreateUser) {
        UserDTO create_user = Context.getStash().get(stashIdCreateUser, UserDTO.class);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String body = getGson().toJson(create_user);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Context.getStash().put("last_response", rs);


    }

    @То("Статус код ответа {int}")
    public void statusCodeResponse(int statusCode) {
        Response response = Context.getStash().get("last_response", Response.class);
        // UserDTO userDTO = response.getBody(UserDTO.class);
        Assert.assertEquals(response.getStatusCode(), statusCode);

    }

    @И("Тело ответа содержит данные пользователя {string}")
    public void dataBodyResponse(String stashId) {
        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO user = response.getBody(UserDTO.class);
        UserDTO create_user = Context.getStash().get(stashId, UserDTO.class);
        Assert.assertEquals(create_user.getUser().getLogin(), user.getUser().getLogin());
        Assert.assertEquals(create_user.getUser().getAdmin(), user.getUser().getAdmin());
        Assert.assertEquals(create_user.getUser().getFirstname(), user.getUser().getFirstname());
        Assert.assertEquals(create_user.getUser().getLastname(), user.getUser().getLastname());
        //Assert.assertEquals(userDTO.getUser().getMail(), user.getMail());
        //Assert.assertEquals(userDTO.getUser().getStatus(), user.getStatus());

    }

    @И("Тело ответа содержит данные пользователя2 {string}")
    public void dataBodyResponseUser2(String stashId) {
        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        User user = Context.getStash().get(stashId, User.class);
        Assert.assertEquals(userDTO.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(userDTO.getUser().getLastname(), user.getLastName());

    }

    @И("Тело ответа содержит данные пользователя1 {string}")
    public void dataBodyResponseUser1(String stashId) {
        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        User user = Context.getStash().get(stashId, User.class);
        Assert.assertEquals(userDTO.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), user.getFirstName());
        Assert.assertEquals(userDTO.getUser().getLastname(), user.getLastName());

    }


    @И("Содержится ошибка {string}")
    public void errorsrandomEmail(String textErrors) {
        Response rs = Context.getStash().get("last_response", Response.class);
        UserCreatingError error = getGson().fromJson(rs.getBody().toString(), UserCreatingError.class);
        switch (textErrors) {
            case "Email уже существует":
                Assert.assertEquals(error.getErrors().get(0), textErrors);
                break;
            case "Пользователь уже существует":
                Assert.assertEquals(error.getErrors().get(1), textErrors);
                break;
            case "Пароль недостаточной длины (не может быть меньше 8 символа)":
                Assert.assertEquals(error.getErrors().get(2), textErrors);
                break;
            default:
                Assert.assertEquals(error.getErrors().get(0), textErrors);
        }

    }

    @Если("Отправить запрос GET на получение пользователя1 {string}")
    public void restGet(String stashId) {
        User user1 = Context.getStash().get(stashId, User.class);
        String uri = String.format("users/%d.json", user1.getId());
        ApiClient apiClient = new RestApiClient(user1);
        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Context.getStash().put("last_response", response);
    }

    @Если("Отправить запрос GET на получение пользователя2 {string}  используя ключ API пользователя1 {string}")
    public void restGet(String stashIdUser2, String stashIdUser1) {
        User user1 = Context.getStash().get(stashIdUser1, User.class);
        User user2 = Context.getStash().get(stashIdUser2, User.class);
        String uri = String.format("users/%d.json", user2.getId());
        ApiClient apiClient = new RestApiClient(user1);
        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Context.getStash().put("last_response", response);
    }


    @И("В ответе присутствуют поля с параметрами \"admin\": false, \"api_key\": \"ключ API из предусловия\"")
    public void resultGetUser() {

        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        String apiKey = (String) Context.getStash().get("api_key");
        Assert.assertEquals(userDTO.getUser().getApi_key(), apiKey);
        Boolean result = false;
        Assert.assertEquals(userDTO.getUser().getAdmin(), result);


    }

    @И("В ответе отсутствуют поля с параметрами admin и api_key")
    public void resultGetUsers() {
        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        Assert.assertNull(userDTO.getUser().getApi_key());
        Assert.assertNull(userDTO.getUser().getAdmin());
    }

    @Если("Отправить запрос DELETE на удаление пользователя {string} используя ключ пользователя {string}")
    public void deleteUser1(String stashIdUser2, String stashIdUser1) {

        User user1 = Context.getStash().get(stashIdUser1, User.class);
        User user2 = Context.getStash().get(stashIdUser2, User.class);
        ApiClient apiClient = new RestApiClient(user1);
        String uri = String.format("users/%d.json", user2.getId());
        Response response = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Context.getStash().put("last_response", response);
    }

    @И("В базе данных присутствует информацию о пользователе {string}")
    public void resultSelectBD(String stashId) {
        User user1 = Context.getStash().get(stashId, User.class);
        User redDBUs = UserRequests.getUser(user1);
        Assert.assertNotNull(redDBUs);


    }

    @И("Выполнить запрос в БД по пользователю {string}")
    public void resultSelectDB(String stashId) {
        UserDTO userDTO = Context.getStash().get(stashId, UserDTO.class);
        String query = String.format("select *from users where login='%s'", userDTO.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        List<User> userDB = result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin("");
                    user.setLogin((String) map.get("login"));
                    user.setFirstName((String) map.get("firstname"));
                    user.setLastName((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus((Integer) map.get("status"));
                    user.setHashedPassword((String) map.get("hashed_password"));
                    user.setSalt((String) map.get("salt"));
                    user.setLastLoginOn((Timestamp) map.get("last_login_on"));
                    user.setLanguage((String) map.get("language"));
                    user.setAuthSourceId((Integer) map.get("auth_source_id"));
                    user.setCreatedOn((Timestamp) map.get("created_on"));
                    user.setUpdatedOn((Timestamp) map.get("updated_on"));
                    user.setType((String) map.get("type"));
                    user.setIdentityUrl((String) map.get("identity_url"));
                    user.setMailNotification((String) map.get("mail_notification"));
                    user.setMustChangePasswd((Boolean) map.get("must_change_passwd"));
                    user.setPasswdChangedOn((Timestamp) map.get("passwd_changed_on"));
                    return user;

                }).collect(Collectors.toList());

        Context.getStash().put("userDB", userDB);

    }

    @Если("Отправить запрос POST {string}-ом на создание пользователя {string} повторно с некорректными параметрами email и password")
    public void restPostIncorrectData(String stashIdUser, String stashIdCreateUser) {
        UserInfo userInfo = Context.getStash().get("user_info", UserInfo.class);
        UserDTO create_user = new UserDTO()
                .setUser(userInfo);
        create_user.setUser(userInfo.setMail("hello").setPassword("1112"));
        String body = getGson().toJson(create_user);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Context.getStash().put("last_response", rs);

    }

    @Если("Отправить запрос PUT {string}-ом на изменение пользователя {string} изменив поле status = {int}")
    public void resPut(String stashIdUser, String stashIdCreateUser, int valueStatus) {
        String password = Context.getStash().get("pass", String.class);
        String email = Context.getStash().get("email", String.class);
        UserInfo userInfo = Context.getStash().get("user_info", UserInfo.class);
        UserDTO create_user = new UserDTO()
                .setUser(userInfo);
        create_user.setUser(userInfo.setPassword(password).setMail(email).setStatus(valueStatus));
        UserDTO userDTO = Context.getStash().get("userDTO", UserDTO.class);
        String body = getGson().toJson(create_user);
        Integer id = userDTO.getUser().getId();
        String uri = String.format("users/%d.json", id);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Response rs = apiClient.request(new RestRequest(uri, Methods.PUT, null, body, null));
        Context.getStash().put("last_response", rs);
    }

    @И("В базе данных присутствует информация о пользователе {string} и status={string}")
    public void dbInfoStatus(String stashIdUser, String status) {
        UserDTO user = Context.getStash().get(stashIdUser, UserDTO.class);
        String login = user.getUser().getLogin();

    }

    @Если("Отправить запрос GET-{string}-ом на получение пользователя {string}")
    public void restGetCreateUser(String stashIdUser, String stashId) {
        UserDTO userDTO = Context.getStash().get("userDTO", UserDTO.class);
        Integer id = userDTO.getUser().getId();
        String uri = String.format("users/%d.json", id);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Context.getStash().put("last_response", response);
    }

    @Если("Отправить запрос DELETE-{string}-ом  на удаление пользователя {string}")
    public void deleteCreateUser(String stashIdUser, String stashIdCreateUser) {
        UserDTO userDTO = Context.getStash().get("userDTO", UserDTO.class);
        Integer id = userDTO.getUser().getId();
        User user2 = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user2);
        String uri = String.format("users/%d.json", id);
        Response response = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Context.getStash().put("last_response", response);
    }

    @И("Значения поля status={int}")
    public void selectStatus(int status) {
        List<User> user = Context.getStash().get("userDB", ArrayList.class);
        int st = user.get(0).getStatus();
        Assert.assertEquals(st, status);


    }

    @И("В БД отсутсвует информация о пользоателе {string}")
    public void selectDbUser(String stashIdUser) {
        UserDTO userDTO = Context.getStash().get("userDTO", UserDTO.class);
        String query = String.format("select *from users where login='%s'", userDTO.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 0);
    }
}
