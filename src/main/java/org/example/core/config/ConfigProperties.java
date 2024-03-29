package org.example.core.config;

import org.example.model.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final String CONFIG_FILE_PATH_TMPL = "%s/config_%s.properties";
    private static final String CONFIG_DIR = "src/test/resources/config";

    private final String configFilePath;

    private final Properties props;

    public ConfigProperties(Environment env) {
        this.configFilePath = String.format(CONFIG_FILE_PATH_TMPL, CONFIG_DIR, env.toString());
        this.props = new Properties();
        try (InputStream input = new FileInputStream(this.configFilePath)) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPropertyValue(String key) {
        return props.getProperty(key);
    }


}
