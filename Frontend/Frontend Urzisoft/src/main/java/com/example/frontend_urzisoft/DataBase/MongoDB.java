package com.example.frontend_urzisoft.DataBase;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public void connect() {
        //Need to ask for the connection string
        String connectionString = "mongodb://localhost:27017";
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("leaderboard-Urzisoft");
        this.collection = database.getCollection("Leaderboard");

        System.out.println("Connected to MongoDB");
    }

    public void insertDocument(Document doc) {
        if (this.collection != null)
        {
            collection.createIndex(new Document("user",1),new IndexOptions().unique(true));
            try {
                this.collection.insertOne(doc);
                System.out.println("Document inserted");
            } catch (Exception e) {
                System.out.println("Document already exists");
            }

        }
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



