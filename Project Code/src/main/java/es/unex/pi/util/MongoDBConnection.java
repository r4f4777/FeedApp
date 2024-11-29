package es.unex.pi.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import es.unex.pi.model.User;

import java.time.Instant;

import org.bson.Document;

public class MongoDBConnection implements AutoCloseable {
    
    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; // Change as needed
    private static final String DATABASE_NAME = "analyticsDB"; // Set your database name here
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Constructor to initialize the MongoDB connection
    public MongoDBConnection() {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    // Method to register a vote in MongoDB
    public void registerVote(int pollId, int voteOptionId, Integer userId) {
        MongoCollection<Document> collection = database.getCollection("votes");

        Document voteDocument = new Document("pollId", pollId)
                .append("voteOptionId", voteOptionId)
                .append("userId", userId)
                .append("timestamp", Instant.now()); // Using Instant for timestamp

        try {
            collection.insertOne(voteDocument);
            System.out.println("Vote registered successfully in MongoDB.");
        } catch (Exception e) {
            System.err.println("Error inserting vote into MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Method to register an user in MongoDB
    public void registerUser(User user) {
        MongoCollection<Document> collection = database.getCollection("users");

        Document doc = new Document("id", user.getId())
        		.append("username", user.getUsername())
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("createdAt", System.currentTimeMillis()); // Fecha de creación
        
        collection.insertOne(doc);
    }
    
    // Method to register a poll in MongoDB
    public void registerPoll(int pollId, Integer userId, String question) {
        MongoCollection<Document> collection = database.getCollection("polls");

        Document pollDocument = new Document("pollId", pollId)
                .append("userId", userId)
                .append("question", question)
                .append("timestamp", System.currentTimeMillis()); // Timestamp para la fecha de creación

        collection.insertOne(pollDocument);
    }

    // Implementing the close method to close the MongoDB client
    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
