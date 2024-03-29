package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Environment {

    DEV, TEST, INT, SANDBOX, PROD;

    @Override
    public String toString() {
        return this.name();
    }

    @JsonCreator
    public static Environment fromString(String env) {
        for (Environment environment : Environment.values()) {
            if (environment.name().equalsIgnoreCase(env)) {
                return environment;
            }
        }
        return null;
    }
}
