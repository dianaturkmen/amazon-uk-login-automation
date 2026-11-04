package com.qa.amazon.utils;

import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties; any key can be overridden with -Dkey=value. */
public final class ConfigReader {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) throw new IllegalStateException("config.properties not found on classpath");
            PROPS.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConfigReader() { }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key).trim());
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key).trim());
    }
}
