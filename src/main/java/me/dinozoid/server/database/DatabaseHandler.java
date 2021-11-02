package me.dinozoid.server.database;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseHandler {

    private Connection connection;
    private Statement statement;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public void openConnection() {
        System.out.println("Attempting to establish MongoDB connection...");
        ConnectionString connectionString = new ConnectionString("mongodb+srv://atlasAdmin:ysP0hU4QQqu3cW63@cluster0.y8xkq.mongodb.net/Strife?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("Strife");
        mongoCollection = mongoDatabase.getCollection("users");
        System.out.println("A MongoDB connection has been established!");
    }

    public void closeConnection() {
        if(mongoClient != null)
            mongoClient.close();
        System.out.println("MongoDB has been shutdown...");
    }

    public Document getUserByUID(String uid) {
        return mongoCollection.find(Filters.eq("uid", uid)).first();
    }

}
