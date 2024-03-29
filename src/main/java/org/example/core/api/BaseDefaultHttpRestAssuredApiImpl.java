package org.example.core.api;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.model.HttpMethod;
import org.example.model.ValidationType;

import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

public class BaseDefaultHttpRestAssuredApiImpl implements IApi<io.restassured.response.Response> {

    RequestSpecification reqSpec;
    HttpMethod method;
    String url;
    io.restassured.response.Response resp;
    RequestSpecBuilder reqSpecBuilder;

    final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(
                    ValidationConfiguration.newBuilder()
                            .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();

    public static BaseDefaultHttpRestAssuredApiImpl builder() {
        return new BaseDefaultHttpRestAssuredApiImpl();
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl initialize(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
        reqSpecBuilder = new RequestSpecBuilder().setBaseUri(url);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withOAuth2(String token) {
        reqSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withBasicAuth(String username, String password) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(username);
        authScheme.setPassword(password);
        reqSpecBuilder.setAuth(authScheme);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withBearerToken(String token) {
        reqSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withHeaders(Map<String, String> headers) {
        reqSpecBuilder.addHeaders(headers);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withReqBody(String body) {
        reqSpecBuilder.setBody(body);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withFormParam(String key, String val) {
        reqSpecBuilder.addFormParam(key, val);
        return this;
    }

    @Override
    public BaseDefaultHttpRestAssuredApiImpl withQueryParam(String key, String val) {
        reqSpecBuilder.addQueryParam(key, val);
        return this;
    }

    @Override
    public Response doCall() {
        reqSpec = reqSpecBuilder.build();
        switch (method) {
            case GET:
                resp = reqSpec.given().filter(new AllureRestAssured()).get(url);
                break;
            case POST:
                resp = reqSpec.given().filter(new AllureRestAssured()).post(url);
                break;
            case PUT:
                resp = reqSpec.given().filter(new AllureRestAssured()).put(url);
                break;
            case DELETE:
                resp = reqSpec.given().filter(new AllureRestAssured()).delete(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method");
        }
        return resp;
    }

    @Override
    public ValidatableResponse assertStatusCode(int code) {
        return resp.then().assertThat().statusCode(code);
    }

    @Override
    public ValidatableResponse doAssert(ValidationType validationType, String key, Object value) {
        switch (validationType) {
            case IS_EQUALS:
                return resp.then().body(key, equalTo(value));

            case IS_NOT_EQUAL:
                return resp.then().body(key, not(equalTo(value)));

            case IS_EMPTY:
                return resp.then().body(key, empty());

            case IS_NOT_EMPTY:
                return resp.then().body(key, not(emptyArray()));

            case IS_NOT_NULL:
                return resp.then().body(key, notNullValue());

            case HAS_STRING:
                return  resp.then().body(key, containsString((String)value));

            case SIZE:
                return resp.then().body(key, hasSize((int)value));

            default:
                throw new IllegalArgumentException("Unsupported validation type");
        }
    }


    @Override
    public Object doResponseSchemaValidation(String schemaPath) {
        return resp.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
    }

    @Override
    public Map<String, String> getRespHeaders(){
        return resp.getHeaders().asList().stream().collect(Collectors.toMap(Header::getName, Header::getValue));
    }

    @Override
    public String getResponseAsString() {
        return resp.body().asString();
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

}