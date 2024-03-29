package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum HttpMethod {

    GET, PUT, POST, DELETE, PATCH, OPTIONS, HEAD, TRACE, CONNECT;

    @JsonCreator
    public static HttpMethod fromString(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
