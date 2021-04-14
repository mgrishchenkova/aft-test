package redmine.api.implementations;

import io.qameta.allure.Allure;
import lombok.Getter;
import redmine.Property;
import redmine.api.interfaces.Methods;
import redmine.api.interfaces.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class RestRequest implements Request {
    private String uri;
    private Methods methods;
    private Map<String, String> parameters;
    private Object body;
    private Map<String, String> headers;

    public RestRequest(String uri, Methods methods, Map<String, String> parameters, Object body, Map<String, String> headers) {
        Allure.step("Отправка API запроса");
        Objects.requireNonNull(uri, "в запросе должен быть uri");
        Objects.requireNonNull(methods, "Не указан метод!");
        String baseUri = Property.getStringProperties("host");
        this.uri = baseUri + uri;
        this.methods = methods;
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        this.parameters = parameters;
        this.body = body;
        if (headers == null) {
            headers = new HashMap<>();
        }
        this.headers = headers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(methods).append(" ").append(uri);
        if (parameters.size() > 0) {
            sb.append("?");
            parameters.forEach((key, value) -> sb.append(key).append("=").append(value).append("&"));
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(System.lineSeparator());
        headers.forEach((key, value) -> sb.append(key).append("=").append(value).append(System.lineSeparator()));
        sb.append(System.lineSeparator());
        if (body != null) {
            sb.append(body.toString());
        }
        return sb.toString();
    }
}
