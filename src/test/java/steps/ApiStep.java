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

import static redmine.util.GsonHelper.getGson;

public class ApiStep {
    @И("У пользователя {string} есть доступ к API и ключ API")
    public void generateApiKey(String stashIdUser) {
        User user = Context.getStash().get(stashIdUser, User.class);
        String addToken = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";
        String apiKey = user.getApi_key();

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(), "api", user.getApi_key(), user.getCreated_on(), user.getUpdated_on());
        String randomEmailAdd = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(randomEmailAdd,
                user.getId(), user.getMail(), true, true, user.getCreated_on(), user.getUpdated_on());
        Context.getStash().put("api_key", apiKey);
    }


    @Если("Отправить POST - запрос {string}-ом  на создание пользователя {string}")
    public void postCreateUser(String stashIdUser, String create_user) {
        String password = StringGenerator.randomString(7, StringGenerator.ENGLISH);
        String email = StringGenerator.randomEmail();
        Context.getStash().put("pass",password);
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
        UserDTO create_user = Context.getStash().get(stashIdCreateUser,UserDTO.class);
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
        UserDTO create_user = Context.getStash().get(stashId,UserDTO.class);
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
        User user = Context.getStash().get(stashId,User.class);
        Assert.assertEquals(userDTO.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), user.getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), user.getLastname());

    }

    @И("Тело ответа содержит данные пользователя1 {string}")
    public void dataBodyResponseUser1(String stashId) {
        Response response = Context.getStash().get("last_response", Response.class);
        UserDTO userDTO = response.getBody(UserDTO.class);
        User user = Context.getStash().get(stashId,User.class);
        Assert.assertEquals(userDTO.getUser().getLogin(), user.getLogin());
        Assert.assertEquals(userDTO.getUser().getFirstname(), user.getFirstname());
        Assert.assertEquals(userDTO.getUser().getLastname(), user.getLastname());

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
        Boolean result =false;
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
        Context.getStash().put("last_response",response);
    }

    @И("В базе данных присутствует информацию о пользователе {string}")
    public void resultSelectBD(String stashId) {
        User user1 = Context.getStash().get(stashId, User.class);
        User redDBUs = UserRequests.getUser(user1);
        Assert.assertNotNull(redDBUs);


    }

    @Если("Отправить запрос POST {string}-ом на создание пользователя {string} повторно с некорректными параметрами email и password")
    public void restPostIncorrectData(String stashIdUser,String stashIdCreateUser) {
        UserInfo userInfo=Context.getStash().get("user_info",UserInfo.class);
        UserDTO create_user = new UserDTO()
                .setUser(userInfo);
        create_user.setUser(userInfo.setMail("hello").setPassword("1112"));
        String body = getGson().toJson(create_user);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient= new RestApiClient(user);
        Response rs = apiClient.request(new RestRequest("users.json", Methods.POST, null, body, null));
        Context.getStash().put("last_response",rs);

    }

    @Если("Отправить запрос PUT {string}-ом на изменение пользователя {string} изменив поле status = {int}")
    public void resPut(String stashIdUser, String stashIdCreateUser, int valueStatus) {
        String password = Context.getStash().get("pass", String.class);
        String email= Context.getStash().get("email",String.class);
        UserInfo userInfo=Context.getStash().get("user_info",UserInfo.class);
        UserDTO create_user = new UserDTO()
                .setUser(userInfo);
        create_user.setUser(userInfo.setPassword(password).setMail(email).setStatus(valueStatus));
        UserDTO userDTO=Context.getStash().get("userDTO", UserDTO.class);
        String body = getGson().toJson(create_user);
        Integer id = userDTO.getUser().getId();
        String uri = String.format("users/%d.json", id);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient= new RestApiClient(user);
        Response rs = apiClient.request(new RestRequest(uri, Methods.PUT, null, body, null));
        Context.getStash().put("last_response",rs);
    }

    @И("В базе данных присутствует информация о пользователе {string} и status={string}")
    public void dbInfoStatus(String stashIdUser, String status) {
        UserDTO user = Context.getStash().get(stashIdUser, UserDTO.class);
        String login =user.getUser().getLogin();
       // User createUserDB = UserRequests.getUser(user);
      //  Integer status1 = createUserDB.getStatus();
        System.out.println();



    }

    @Если("Отправить запрос GET-{string}-ом на получение пользователя {string}")
    public void restGetCreateUser(String stashIdUser, String stashId) {
        UserDTO userDTO=Context.getStash().get("userDTO", UserDTO.class);
        Integer id = userDTO.getUser().getId();
        String uri = String.format("users/%d.json", id);
        User user = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Response response = apiClient.request(new RestRequest(uri, Methods.GET, null, null, null));
        Context.getStash().put("last_response", response);
    }

    @Если("Отправить запрос DELETE-{string}-ом  на удаление пользователя {string}")
    public void deleteCreateUser(String stashIdUser, String stashIdCreateUser) {
        UserDTO userDTO=Context.getStash().get("userDTO", UserDTO.class);
        Integer id = userDTO.getUser().getId();
        User user2 = Context.getStash().get(stashIdUser, User.class);
        ApiClient apiClient = new RestApiClient(user2);
        String uri = String.format("users/%d.json", id);
        Response response = apiClient.request(new RestRequest(uri, Methods.DELETE, null, null, null));
        Context.getStash().put("last_response",response);
    }
}
