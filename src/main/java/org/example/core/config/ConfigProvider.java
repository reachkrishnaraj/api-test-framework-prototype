package org.example.core.config;

import org.apache.commons.lang3.StringUtils;
import org.example.model.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigProvider {

    private final static Map<Environment, ConfigProperties> instances = new HashMap<>();

    public static ConfigProperties get() {
        Environment env = Optional.ofNullable(System.getProperty("envName"))
                .filter(StringUtils::isNotBlank)
                .map(Environment::fromString)
                .filter(e -> e != null)
                .orElse(Environment.INT);

        if (!instances.containsKey(env)) {
            instances.put(env, new ConfigProperties(env));
        }
        return instances.get(env);
    }

    public static String getOrderApiBaseUri() {
        return get().getPropertyValue("order.uri");
    }

    public static String getRabbitMQConnectionUri() {
        return get().getPropertyValue("rabbitmq.connection.uri");
    }

    public static String getDatabaseConnectionUri(){
        return get().getPropertyValue("database.connection.uri");
    }

    public static String getDatabaseUsername(){
        return get().getPropertyValue("database.username");
    }

    public static String getDatabasePassword(){
        return get().getPropertyValue("database.password");
    }

    public static String getDatabaseDriver(){
        return get().getPropertyValue("database.driver");
    }

}
