package com.prometheus.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties configFile;

    static{
        try{
            FileInputStream fileInputStream = new FileInputStream("configuration.properties");
            configFile = new Properties();
            configFile.load(fileInputStream);
            fileInputStream.close();
        }catch (IOException e){
            System.out.println("Properties file was NOT loaded");
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return configFile.getProperty(key);
    }
//    private static final Properties CONFIG = new Properties();
//
//    static {
//        final String NAME = "src/configuration.properties";
//        try {
//            // Debug: where is the classpath root?
//            ClassLoader cl = ConfigurationReader.class.getClassLoader();
//            System.out.println("[Config] classpath root = " + cl.getResource("."));
//
//            // Try class loader of this class
//            InputStream is = cl.getResourceAsStream(NAME);
//            URL foundAt = cl.getResource(NAME);
//
//            // Fallback: context class loader (some runners use this)
//            if (is == null) {
//                ClassLoader ctx = Thread.currentThread().getContextClassLoader();
//                if (foundAt == null) System.out.println("[Config] trying context classloader…");
//                is = ctx.getResourceAsStream(NAME);
//                if (foundAt == null) foundAt = ctx.getResource(NAME);
//            }
//
//            System.out.println("[Config] " + NAME + " URL = " + foundAt);
//
//            if (is == null) {
//                throw new IllegalStateException(NAME + " not found on classpath");
//            }
//
//            CONFIG.load(is);
//            is.close();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to load configuration.properties", e);
//        }
//    }
//
//    public static String getProperty(String key) {
//        return CONFIG.getProperty(key);
//    }
}