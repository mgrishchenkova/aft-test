package redmine.api.implementations;

import io.restassured.http.Header;
import lombok.Getter;
import redmine.api.interfaces.Response;
import redmine.util.GsonHelper;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class RestResponse implements Response {
    private int statusCode;
    Map<String, String> Headers;
    Object body;

    public RestResponse(int statusCode, Map<String, String> headers, Object body) {
        this.statusCode = statusCode;
        this.Headers = headers;
        this.body = body;
    }

    public RestResponse(io.restassured.response.Response restAssuredResponse) {
        this.statusCode = restAssuredResponse.getStatusCode();
        this.Headers = restAssuredResponse.getHeaders().asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
        this.body = restAssuredResponse.getBody().asString();
    }


    @Override
    public <T> T getBody(Class<T> clazz) {
         return GsonHelper.getGson().fromJson(body.toString(), clazz);
    }
}
