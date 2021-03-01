package Tests.rest;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import redmine.Property;

import static io.restassured.RestAssured.given;

public class test {

    @Test
    public void restRequestTest() {
        given().baseUri(Property.getStringProperties("url"))
                .contentType(ContentType.JSON)
                .request(Method.GET, "roles.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
}
