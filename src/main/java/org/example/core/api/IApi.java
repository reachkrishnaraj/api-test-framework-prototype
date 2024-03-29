package org.example.core.api;

import org.example.model.HttpMethod;
import org.example.model.ValidationType;

import java.util.Map;

public interface IApi<R> {

    IApi initialize(String url, HttpMethod method);

    IApi withOAuth2(String token);

    IApi withBasicAuth(String username, String password);

    IApi withBearerToken(String token);

    IApi withHeaders(Map<String, String> headers);

    IApi withReqBody(String body);

    IApi withFormParam(String key, String val);

    IApi withQueryParam(String key, String val);

    R doCall();

    Object assertStatusCode(int code);

    Object doAssert(ValidationType validationType, String key, Object value);

    Object doResponseSchemaValidation(String schemaPath);

    default void markFailed(String expected, String present) {
        throw new AssertionError("Does not contain the expected\t " + expected + "\n but had\t" + present);
    }

    default void markFailed(String message) {throw new AssertionError(message);}

    Map<String, String> getRespHeaders();

    String getResponseAsString();

}
