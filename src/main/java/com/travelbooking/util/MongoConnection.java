package com.travelbooking.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton MongoDB connection manager.
 * Reads config from config.properties on classpath.
 */
public class MongoConnection {

    private static MongoClient client;
    private static MongoDatabase database;
    private static final Object LOCK = new Object();

    private MongoConnection() {}

    public static MongoDatabase getDatabase() {
        if (database == null) {
            synchronized (LOCK) {
                if (database == null) {
                    Properties props = new Properties();
                    try (InputStream is = MongoConnection.class.getClassLoader()
                            .getResourceAsStream("config.properties")) {
                        if (is != null) {
                            props.load(is);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load config.properties", e);
                    }

                    String fullUri = props.getProperty("mongodb.uri", "").trim();
                    String dbName = props.getProperty("mongodb.database", "travelbooking");

                    String uri;
                    if (!fullUri.isEmpty()) {
                        uri = fullUri;
                    } else {
                        String host = props.getProperty("mongodb.host", "localhost");
                        String port = props.getProperty("mongodb.port", "27017");
                        uri = "mongodb://" + host + ":" + port;
                    }
                    client = MongoClients.create(uri);
                    database = client.getDatabase(dbName);
                }
            }
        }
        return database;
    }

    public static void close() {
        if (client != null) {
            client.close();
            client = null;
            database = null;
        }
    }
}
