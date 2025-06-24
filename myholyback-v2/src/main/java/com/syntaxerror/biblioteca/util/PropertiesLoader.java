package com.syntaxerror.biblioteca.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private final Properties props = new Properties();

    public PropertiesLoader(String filename) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo: " + filename);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo: " + filename, e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
