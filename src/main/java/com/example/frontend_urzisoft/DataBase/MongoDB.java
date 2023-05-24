package com.example.frontend_urzisoft.DataBase;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public void connect() {
        //Need to ask for the connection string
        String connectionString = "mongodb+srv://testUser:testPass123@urzisoft.8s0ynmh.mongodb.net/?retryWrites=true&w=majority";
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("leaderboard-Urzisoft");
        this.collection = database.getCollection("Leaderboard");

       // System.out.println("Connected to MongoDB");
    }

    public boolean checkUser(String user) {
        long count = collection.countDocuments(eq("user", user));

        return count > 0;
    }







    public void insertDocument(Document doc)
    {
        collection.insertOne(doc);
    }


    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void closeConnection() {
        if (this.mongoClient != null)
        {
            this.mongoClient.close();
        }
    }

    public static void main(String args[])
    {
        MongoDB mongoDB = new MongoDB();
        mongoDB.connect();


    }


}



