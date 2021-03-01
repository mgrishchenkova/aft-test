package redmine.api.interfaces;

import io.qameta.allure.Step;

public interface ApiClient {
   @Step("Отправка API запроса")
   Response request(Request request);
}
