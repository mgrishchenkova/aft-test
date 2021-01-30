package redmine.api.implementations;

import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.Users;

import java.util.Objects;

public class RestApiClient implements ApiClient {
    String token;
    public void ResApiClient(Users user){
        Objects.requireNonNull(user,"Пользователь должен быть создан");
        Objects.requireNonNull(user.getApiKey(), "У пользователя должен быть ключ Api_key");

    }
    @Override
    public Response request(Request request) {
        return null;
    }
}
